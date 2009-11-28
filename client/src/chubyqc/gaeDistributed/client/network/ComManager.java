package chubyqc.gaeDistributed.client.network;

import java.io.IOException;
import java.net.InetSocketAddress;

import chubyqc.gaeDistributed.client.messages.incoming.ClientMessageFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ComManager {
	private static ComManager _instance = new ComManager();
	public static ComManager getInstance() {
		return _instance;
	}
	
	private ComManager() {
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
								System.out.println("Incoming request");
								ClientMessageFactory.getInstance().create(
										content.getRequestBody()).execute();
							}
						});
						server.start();
						System.out.println("Client started");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
		}).start();
	}
}
