package chubyqc.gaeDistributed.server.network;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.network.messages.incoming.IncomingMessage;
import chubyqc.gaeDistributed.server.network.messages.incoming.MessageFactory;
import chubyqc.gaeDistributed.server.network.messages.incoming.ServerMessageFactory;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;

public class ComManager {	
	private static ComManager _instance = new ComManager(ServerMessageFactory.getInstance());
	public static ComManager getInstance() {
		return _instance;
	}

	private static final long serialVersionUID = 1L;
	
	public MessageFactory _factory;
	
	protected ComManager(MessageFactory factory) {
		_factory = factory;
	}
	
	protected void receive(InputStream input, String address) {
		try {
			IncomingMessage<?> message = _factory.create(input);
			message.setAddress(address);
			message.execute();
		} catch (Exception e) {
			Logger.getInstance().error(e);
		}
	}

	public void send(String address, String username, OutgoingMessage message) {
		try {
			message.setUsername(username);
			message.populateJSON();
			URLConnection connection = new URL(address).openConnection();
			connection.setDoOutput(true);
			connection.getOutputStream().write(message.toString().getBytes());
			connection.getOutputStream().close();
			connection.getInputStream().close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}