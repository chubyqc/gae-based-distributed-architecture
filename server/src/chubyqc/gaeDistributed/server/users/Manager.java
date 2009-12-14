package chubyqc.gaeDistributed.server.users;

import java.util.HashMap;
import java.util.Map;

import chubyqc.gaeDistributed.server.Session;
import chubyqc.gaeDistributed.server.client.states.commands.Commands;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;

public class Manager {
	private static Manager instance = new Manager();
	public static Manager getInstance() {
		return instance;
	}
	
	private static final String ERR_LOGIN_BAD = "Bad login.";
	
	private Map<String, Commands> _commands;
	
	private Manager() {
		_commands = new HashMap<String, Commands>();
	}
	
	public void login(Session session, String username, String password) throws Exception {
		User user = getUser(username);
		if (!user.isRightPassword(password)) {
			throw new Exception(ERR_LOGIN_BAD);
		}
	}
	
	public User createUser(String name, String password, String email) throws Exception {
		return new User(name, password, email);
	}
	
	public void save(User user) throws Exception {
		user.save();
	}
	
	public void sendMessage(String username, OutgoingMessage message) {
		User user = getUser(username);
		if (user != null) {
			user.send(message);
		}
	}
	
	public void setCommands(String username, Commands commands) {
		_commands.put(username, commands);
	}
	
	public Commands getCommands(Session session) {
		Commands commands = _commands.get(session.getUsername());
		return (commands == null) ? new Commands() : commands;
	}
	
	private User getUser(String username) {
		return GAEPersistenceManager.getInstance().getUser(username);
	}
}
