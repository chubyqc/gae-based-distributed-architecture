package chubyqc.gaeDistributed.server.users;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import javax.cache.Cache;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import org.json.JSONObject;

import chubyqc.gaeDistributed.server.EmailManager;
import chubyqc.gaeDistributed.server.Session;
import chubyqc.gaeDistributed.server.client.widgets.commands.Commands;
import chubyqc.gaeDistributed.server.client.widgets.console.Message;
import chubyqc.gaeDistributed.server.network.ComManager;
import chubyqc.gaeDistributed.server.network.messages.outgoing.ExecuteCommand;
import chubyqc.gaeDistributed.server.network.messages.outgoing.IsClientBooted;
import chubyqc.gaeDistributed.server.network.messages.outgoing.Logon;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;
import chubyqc.gaeDistributed.server.network.messages.outgoing.SendEmail;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String INFO_LOGON = "Logged On";

	private static final String ERR_EMAIL = "Invalid email address: %s";
	private static final String ERR_PASSWORD = "Invalid password";
	private static final String ERR_USER_EXISTS = "User already exists";
	private static final String ERR_NO_ADDRESS = "No address";
	
	private static final String URL_INCOMING = "dafti/incoming";
	private static final String URL_BASE = "https://gae-dafti.appspot.com/";
	private static final String URL_FORMAT = "https://%s";
//	private static final String URL_FORMAT = "http://%s:8080";
	
	@Persistent
    @PrimaryKey
	private String _name;
	@Persistent
	private String _password;
	@Persistent
	private String _email;
	
	@NotPersistent
	private String _address;
	@NotPersistent
	private Commands _commands;
	@NotPersistent
	private Collection<Message> _messages;
	@NotPersistent
	private boolean _updated;
	
	User() {
		_messages = new LinkedList<Message>();
		_updated = false;
	}
	
	User(String name, String password, String email) throws Exception {
		this();
		_name = name;
		setEmail(email);
		setPassword(password);
	}
	
	public void remember(Session session) {
		session.setUsername(_name);
		inform(INFO_LOGON);
	}
	
	public void setAddress(String address) {
		inform(_address = parseAddress(address));
	}
	
	public static String parseAddress(String address) {
		return String.format(URL_FORMAT, address);
	}
	
	public void setCommands(Commands commands) {
		_commands = commands;
	}
	
	public Commands getCommands() {
		return _commands;
	}
	
	public void isBooted() throws Exception {
		send(new IsClientBooted());
	}
	
	public void logon() throws Exception {
		send(new Logon(true));
	}
	
	public void invoke(String commandName, Map<String, String> paramValues) throws Exception {
		send(new ExecuteCommand(commandName, new JSONObject(paramValues)));
	}
	
	private void setEmail(String email) throws Exception {
		if (!EmailManager.getInstance().isValid(email)) {
			throw new UserException(String.format(ERR_EMAIL, email));
		}
		_email = email;
	}
	
	private void setPassword(String password) throws Exception {
		validatePassword(password);
		_password = Encoder.getInstance().encrypt(password);
	}
	
	private void validatePassword(String password) throws Exception {
		if (password == null || password.length() < 5) {
			throw new UserException(String.format(ERR_PASSWORD));
		}
	}
	
	void save() throws Exception {
		GAEPersistenceManager pm = GAEPersistenceManager.getInstance();
		if (pm.contains(_name)) {
			throw new Exception(ERR_USER_EXISTS);
		}
		GAEPersistenceManager.getInstance().save(this);
		sendConfirmationEmail();
	}
	
	private void sendConfirmationEmail() {
		ComManager.getInstance().send(
				URL_BASE + URL_INCOMING, new SendEmail(_name, _email));
	}
	
	private void send(OutgoingMessage message) throws Exception {
		if (_address == null || _address.length() == 0) {
			throw new Exception(ERR_NO_ADDRESS);
		}
		ComManager.getInstance().send(_address, message);
	}
	
	public boolean isRightPassword(String password) {
		return _password.equals(Encoder.getInstance().encrypt(password));
	}

	public void inform(String message) {
		_messages.add(new Message(message));
		_updated = true;
	}
	
	@SuppressWarnings("unchecked")
	void updated(Cache cache, String prefix) {
		cache.put(prefix + _name, _updated);
	}
	
	@SuppressWarnings("unchecked")
	void commit(Cache cache, String prefix) {
		cache.put(prefix + _name, this);
	}
	
	public Message[] flushMessages() {
		Message[] messages = _messages.toArray(new Message[_messages.size()]);
		_messages.clear();
		_updated = false;
		return messages;
	}
}
