package chubyqc.gaeDistributed.client.network;

import java.io.IOException;
import java.net.InetSocketAddress;

import chubyqc.gaeDistributed.client.network.messages.incoming.ClientMessageFactory;
import chubyqc.gaeDistributed.client.network.messages.outgoing.ClientBooted;
import chubyqc.gaeDistributed.server.network.messages.incoming.MessageFactory;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ComManager extends chubyqc.gaeDistributed.server.network.ComManager {
	private static ComManager _instance = new ComManager(ClientMessageFactory.getInstance());
	public static ComManager getInstance() {
		return _instance;
	}
	
	private static final String SERVER_ADDRESS = "http://localhost:8181/dafti/incoming";
	
	private ComManager(MessageFactory factory) {
		super(factory);
	}
	
	public void send(OutgoingMessage message) {
		send(SERVER_ADDRESS, message);
	}
	
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
					try {
						HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
						server.createContext("/", new HttpHandler() {
							
							@Override
							public void handle(HttpExchange content) throws IOException {
								receive(content.getRequestBody());
							}
						});
						server.start();
						System.out.println("Client started");
						send(new ClientBooted());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}).start();
	}
}
