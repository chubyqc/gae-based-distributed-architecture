package chubyqc.gaeDistributed.server.users;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import chubyqc.gaeDistributed.server.EmailManager;
import chubyqc.gaeDistributed.server.client.commands.Commands;
import chubyqc.gaeDistributed.server.network.ComManager;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;
import chubyqc.gaeDistributed.server.network.messages.outgoing.SendEmail;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User {

	private static final String ERR_EMAIL = "Invalid email address: %s";
	private static final String ERR_PASSWORD = "Invalid password";
	private static final String ERR_USER_EXISTS = "User already exists";
	
	public static final String URL_INCOMING = "dafti/incoming";
	public static final String URL_BASE = "http://localhost:8080/";
	
	@Persistent
    @PrimaryKey
	private String _name;
	@Persistent
	private String _password;
	@Persistent
	private String _email;
	
	private String _address;
	private Commands _commands;
	
	User(String name, String password, String email) throws Exception {
		_name = name;
		setEmail(email);
		setPassword(password);
	}
	
	public void setAddress(String address) {
		_address = address;
	}
	
	public void setCommands(Commands commands) {
		_commands = commands;
	}
	
	public Commands getCommands() {
		return _commands;
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
				URL_BASE + URL_INCOMING, _name, new SendEmail(_email));
	}
	
	public void send(OutgoingMessage message) {
		ComManager.getInstance().send(_address, _name, message);
	}
}
