package chubyqc.gaeDistributed.server.users;

import java.util.HashMap;
import java.util.Map;

import chubyqc.gaeDistributed.server.Session;
import chubyqc.gaeDistributed.server.client.states.commands.Commands;

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
	
	public void login(Session session, String username, String password) throws Exception {
		User user = getUser(username);
		if (!user.isRightPassword(password)) {
			throw new Exception(ERR_LOGIN_BAD);
		}
		user.remember(session);
	}
	
	public User createUser(String name, String password, String email) throws Exception {
		return new User(name, password, email);
	}
	
	public void save(User user) throws Exception {
		user.save();
	}
	
	public void setCommands(String username, Commands commands) throws Exception {
		getUser(username).setCommands(commands);
	}
	
	public void isBooted(String username) throws Exception {
		getUser(username).isBooted();
	}
	
	public void setAddress(String username, String address) throws Exception {
		getUser(username).setAddress(address);
	}
	
	public Commands getCommands(String username) throws Exception {
		Commands commands = getUser(username).getCommands();
		return (commands == null) ? new Commands() : commands;
	}
	
	public void invoke(String username, String commandName, Map<String, String> paramValues) throws Exception {
		getUser(username).invoke(commandName, paramValues);
	}
	
	private User getUser(String username) throws Exception {
		if (username == null) {
			throw new Exception(ERR_USER_NOT_FOUND);
		}
		User user = _users.get(username);
		if (user == null) {
			_users.put(username, user = GAEPersistenceManager.getInstance().getUser(username));
			if (user == null) {
				throw new Exception(ERR_USER_NOT_FOUND);
			}
		}
		return user;
	}
}
