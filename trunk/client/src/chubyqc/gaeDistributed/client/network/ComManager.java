package chubyqc.gaeDistributed.client.network;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import chubyqc.gaeDistributed.client.Client;
import chubyqc.gaeDistributed.server.network.messages.incoming.MessageFactory;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;

public class ComManager extends chubyqc.gaeDistributed.server.network.ComManager {
	
	private static final String KEYSTORE_ALGO = "JKS";
	private static final String KEYSTORE_PATH = "cert/mySrvKeystore";
	private static final String MANAGER_KEY = "SunX509";
	private static final String SSL_ALGO = "TLS";
	private static final String MSG_STARTED = "Client started";
	
	private String _serverAddress;
	private String _passphrase;
	
	public ComManager(MessageFactory factory, String serverAddress,
			String passphrase) {
		super(factory);
		_serverAddress = serverAddress;
		_passphrase = passphrase;
	}
	
	public void send(OutgoingMessage message) {
		send(_serverAddress, message);
	}
	
	public void start() {
		new Thread(new Runnable() {

			@Override
			public void run() {
					try {
						HttpServer server = createServer(_passphrase);
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
						System.out.println(MSG_STARTED);
						Client.getInstance().login();
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(1);
					}
			}
			
		}).start();
	}
	
	private HttpServer createServer(String passphraseStr) throws Exception {
		char[] passphrase = passphraseStr.toCharArray();
		KeyStore ks = KeyStore.getInstance(KEYSTORE_ALGO);
		ks.load(new FileInputStream(KEYSTORE_PATH), passphrase);
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(MANAGER_KEY);
		kmf.init(ks, passphrase);
		
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(MANAGER_KEY);
		   tmf.init(ks);
		   
		HttpsServer server = HttpsServer.create(new InetSocketAddress(443), 0);
		SSLContext ssl = SSLContext.getInstance(SSL_ALGO);
		ssl.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		server.setHttpsConfigurator(new HttpsConfigurator(ssl));
		
		return server;
		
//		return HttpServer.create(new InetSocketAddress(8080), 0);
	}
}
