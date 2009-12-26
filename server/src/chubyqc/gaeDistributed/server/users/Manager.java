package chubyqc.gaeDistributed.server.users;

import java.util.HashMap;
import java.util.Map;

import chubyqc.gaeDistributed.server.Session;
import chubyqc.gaeDistributed.server.client.widgets.commands.Commands;
import chubyqc.gaeDistributed.server.client.widgets.console.Message;

public class Manager {
	private static Manager instance = new Manager();
	public static Manager getInstance() {
		return instance;
	}
	
	private static final String ERR_LOGIN_BAD = "Bad login.";
	private static final String ERR_USER_NOT_FOUND = "User not found.";
	
	private Map<String, User> _users;
	
	private Manager() {
		_users = new HashMap<String, User>();
	}
	
	public User login(Session session, String username, String password) 
		throws UserException, Exception {
		User user = getUser(username);
		if (!user.isRightPassword(password)) {
			throw new UserException(ERR_LOGIN_BAD);
		}
		user.remember(session);
		return user;
	}
	
	public void clientLogin(Session session, String username, String password)
		throws Exception {
		login(session, username, password).logon();
	}
	
	public User createUser(String name, String password, String email) throws Exception {
		return new User(name, password, email);
	}
	
	public void save(User user) throws Exception {
		user.save();
	}
	
	public void setCommands(String username, Commands commands) throws UserException {
		getUser(username).setCommands(commands);
	}
	
	public void isBooted(String username) throws Exception {
		getUser(username).isBooted();
	}
	
	public void setAddress(String username, String address) throws UserException {
		getUser(username).setAddress(address);
	}
	
	public Commands getCommands(String username) throws Exception {
		Commands commands = getUser(username).getCommands();
		return (commands == null) ? new Commands() : commands;
	}
	
	public void invoke(String username, String commandName, Map<String, String> paramValues) throws Exception {
		getUser(username).invoke(commandName, paramValues);
	}

	public void inform(String username, String message) throws UserException {
		getUser(username).inform(message);
	}
	
	public Message[] flushMessages(String username) throws Exception {
		return getUser(username).flushMessages();
	}
	
	private User getUser(String username) throws UserException {
		if (username == null) {
			throw new UserException(ERR_USER_NOT_FOUND);
		}
		User user = _users.get(username);
		if (user == null) {
			_users.put(username, user = GAEPersistenceManager.getInstance().getUser(username));
			if (user == null) {
				throw new UserException(ERR_USER_NOT_FOUND);
			}
		}
		return user;
	}
}
