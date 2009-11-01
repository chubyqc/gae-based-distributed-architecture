package chubyqc.gaeDistributed.client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
								ClientMessageFactory.getInstance().create(getBody(content)).execute();
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
	
	private String getBody(HttpExchange content) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(content.getRequestBody()));
		StringBuilder body = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			body.append(line);
		}
		return body.toString(); 
	}
}
