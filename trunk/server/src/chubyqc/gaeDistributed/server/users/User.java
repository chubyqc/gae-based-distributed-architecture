package chubyqc.gaeDistributed.server.users;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

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
public class User {
	
	private static final String INFO_LOGON = "Logged On";

	private static final String ERR_EMAIL = "Invalid email address: %s";
	private static final String ERR_PASSWORD = "Invalid password";
	private static final String ERR_USER_EXISTS = "User already exists";
	
	private static final String URL_INCOMING = "dafti/incoming";
	private static final String URL_BASE = "http://localhost:8181/";
	private static final String URL_FORMAT = "http://%s:8080";
	
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
	private transient Commands _commands;
	@NotPersistent
	private transient Collection<Message> _messages;
	
	User() {
		_messages = new LinkedList<Message>();
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
	
	public void isBooted() {
		send(new IsClientBooted());
	}
	
	public void logon() {
		send(new Logon(true));
	}
	
	public void invoke(String commandName, Map<String, String> paramValues) {
		send(new ExecuteCommand(commandName, new JSONObject(paramValues)));
	}
	
	private void setEmail(String email) throws Exception {
		if (!EmailManager.getInstance().isValid(email)) {
			throw new UserException(String.format(ERR_EMAIL, email));
		}
		_email = email;
	}
	
	private void setPassword(String password) throws Exception {
		_password = password;
		validatePassword();
		_password = Encoder.getInstance().encrypt(password);
	}
	
	private void validatePassword() throws Exception {
		if (_password == null || _password.length() < 5) {
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
				URL_BASE + URL_INCOMING, new SendEmail(_email));
	}
	
	private void send(OutgoingMessage message) {
		ComManager.getInstance().send(_address, message);
	}
	
	public boolean isRightPassword(String password) {
		return _password.equals(Encoder.getInstance().encrypt(password));
	}

	public void inform(String message) {
		synchronized (_messages) {
			_messages.add(new Message(message));
			_messages.notify();
		}
	}
	
	public Message[] flushMessages() {
		synchronized (_messages) {
			if (_messages.size() == 0) {
				wait(_messages);
			}
			Message[] messages = _messages.toArray(new Message[_messages.size()]);
			_messages.clear();
			return messages;
		} 
	}
	
	private void wait(Object obj) {
		try {
			obj.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
