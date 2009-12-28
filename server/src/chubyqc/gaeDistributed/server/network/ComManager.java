package chubyqc.gaeDistributed.server.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.Session;
import chubyqc.gaeDistributed.server.network.messages.incoming.IncomingMessage;
import chubyqc.gaeDistributed.server.network.messages.incoming.MessageFactory;
import chubyqc.gaeDistributed.server.network.messages.incoming.ServerMessageFactory;
import chubyqc.gaeDistributed.server.network.messages.outgoing.NotLogon;
import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;
import chubyqc.gaeDistributed.server.users.User;
import chubyqc.gaeDistributed.server.users.UserException;

public class ComManager {	
	private static ComManager _instance = new ComManager(ServerMessageFactory.getInstance());
	public static ComManager getInstance() {
		return _instance;
	}

	private static final long serialVersionUID = 1L;
	private static final String SESSION_RESPONSE_KEY = "Set-Cookie";
	private static final String SESSION_REQUEST_KEY = "Cookie";
	private static final String SESSION_ID = "JSESSIONID";
	
	public MessageFactory _factory;
	private String _session;
	
	protected ComManager(MessageFactory factory) {
		_factory = factory;
	}
	
	protected void receive(String content, String address, Session session) {
		try {
			IncomingMessage<?> message = _factory.create(content);
			message.setAddress(address);
			message.setSession(session);
			message.execute();
		} catch (UserException e) {
			send(User.parseAddress(address), new NotLogon());
		} catch (Exception e) {
			Logger.getInstance().error(e);
		}
	}

	public void send(String address, OutgoingMessage message) {
		try {
			message.populateJSON();
			URLConnection connection = new URL(address).openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			setSession(connection);
			connection.setDoOutput(true);
			OutputStream out = connection.getOutputStream();
			out.write(message.toString().getBytes());
			out.close();
			connection.getInputStream().close();
			rememberSession(connection);
		} catch (Exception e) {
			Logger.getInstance().error(e);
		}
	}
	
	public String read(InputStream jsonAsStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(jsonAsStream));
		StringBuilder body = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			body.append(line);
		}
		jsonAsStream.close();
		return body.toString();
	}
	
	private void setSession(URLConnection connection) {
		if (_session != null) {
			connection.setRequestProperty(SESSION_REQUEST_KEY, _session);
		}
	}

	protected void rememberSession(URLConnection connection) {
		if (_session == null) {
			List<String> session = connection.getHeaderFields().get(SESSION_RESPONSE_KEY);
			if (session != null) {
				for (String key : session) {
					if (key.startsWith(SESSION_ID)) {
						_session = key;
						break;
					}
				}
			}
		}
	}
	
	public void invalidateSession() {
		_session = null;
	}
}
