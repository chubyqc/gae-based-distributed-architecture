package chubyqc.gaeDistributed.client.network;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.net.ssl.SSLContext;

import chubyqc.gaeDistributed.client.Client;
import chubyqc.gaeDistributed.server.network.messages.incoming.MessageFactory;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

public class ComManager extends chubyqc.gaeDistributed.server.network.ComManager {
	
	private String _serverAddress;
	
	public ComManager(MessageFactory factory, String serverAddress) {
		super(factory);
		_serverAddress = serverAddress;
	}
	
	public void send(OutgoingMessage message) {
		send(_serverAddress, message);
	}
	
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
					try {
						HttpServer server = createServer();
						server.createContext("/", new HttpHandler() {
							
							@Override
							public void handle(HttpExchange content) throws IOException {
								String body = read(content.getRequestBody());
								content.sendResponseHeaders(200, -1);
								content.close();
								receive(body, content.getRemoteAddress().getHostName(),
										null);
							}
						});
						server.start();
						System.out.println("Client started");
						Client.getInstance().login();
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(1);
					}
			}
			
		}).start();
	}
	
	private HttpServer createServer() throws Exception {
		HttpsServer server = HttpsServer.create(new InetSocketAddress(443), 0);
		server.setHttpsConfigurator(new HttpsConfigurator(SSLContext.getInstance("TLS")));
		
		return server;
	}
}
