package chubyqc.gaeDistributed.server.users;

public class Manager {
	private static Manager instance = new Manager();
	public static Manager getInstance() {
		return instance;
	}
	
	private Manager() {
	}
	
	public User createUser(String name, String password, String email) throws Exception {
		return new User(name, password, email);
	}
	
	public void save(User user) throws Exception {
		user.save();
	}
}
