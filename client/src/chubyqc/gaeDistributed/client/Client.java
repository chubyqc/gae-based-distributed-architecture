package chubyqc.gaeDistributed.client;

import chubyqc.gaeDistributed.client.commands.CommandsManager;
import chubyqc.gaeDistributed.client.network.ComManager;
import chubyqc.gaeDistributed.client.network.messages.incoming.ClientMessageFactory;
import chubyqc.gaeDistributed.client.network.messages.outgoing.Login;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;

public class Client {
	private static Client _instance;
	public static Client getInstance() {
		return _instance;
	}
	public static Client createInstance(String username, String password,
			String passphrase) {
		_instance = new Client(username, password, passphrase);
		return _instance;
	}
	
	private static final String ADDRESS = "https://gae-dafti.appspot.com/dafti/incoming";
	
	private String _username;
	private String _password;
	private ComManager _comManager;

	private Client(String username, String password, String passphrase) {
		_username = username;
		_password = password;
		_comManager = new ComManager(new ClientMessageFactory(), ADDRESS,
				passphrase);
	}
	
	public void announce() {
		CommandsManager.getInstance().announce();
	}
	
	public void login() {
		send(new Login(_username, _password));
	}
	
	void start() {
		_comManager.start();
	}
	
	public void send(OutgoingMessage message) {
		_comManager.send(message);
	}
}
