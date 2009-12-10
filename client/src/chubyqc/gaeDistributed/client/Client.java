package chubyqc.gaeDistributed.client;

import chubyqc.gaeDistributed.client.network.ComManager;
import chubyqc.gaeDistributed.client.network.messages.incoming.ClientMessageFactory;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;

public class Client {
	private static Client _instance;
	public static Client getInstance() {
		return _instance;
	}
	public static Client createInstance(String serverAddress, String username) {
		_instance = new Client(serverAddress, username);
		return _instance;
	}
	
	private String _username;
	
	private ComManager _comManager;

	private Client(String serverAddress, String username) {
		_username = username;
		_comManager = new ComManager(new ClientMessageFactory(), serverAddress);
	}
	
	void start() {
		_comManager.start();
	}
	
	public void send(OutgoingMessage message) {
		_comManager.send(_username, message);
	}
}
