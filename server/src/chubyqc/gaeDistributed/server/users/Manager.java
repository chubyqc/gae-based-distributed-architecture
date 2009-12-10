package chubyqc.gaeDistributed.server.users;

import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.client.commands.Commands;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;

public class Manager {
	private static Manager instance = new Manager();
	public static Manager getInstance() {
		return instance;
	}
	
	private User _user;
	
	private Manager() {
	}
	
	public User createUser(String name, String password, String email) throws Exception {
		return new User(name, password, email);
	}
	
	public void save(User user) throws Exception {
		user.save();
	}
	
	public void sendMessage(String username, OutgoingMessage message) {
		getUser(username).send(message);
	}
	
	public void setCommands(String username, Commands commands) {
		getUser(username).setCommands(commands);
	}
	
	public Commands getCommands(String username) {
		return getUser(username).getCommands();
	}
	
	private User getUser(String username) {
		if (_user == null) {
			try {
				_user = new User(username, "fdsaasdf", "chuby.qc@gmail.com");
				_user.setAddress("http://localhost:8080");
			} catch (Exception e) {
				Logger.getInstance().error(e);
			}
		}
		return _user;
	}
}
