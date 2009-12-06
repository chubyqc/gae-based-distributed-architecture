package chubyqc.gaeDistributed.server.network.messages.incoming;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.network.messages.Message;

public abstract class MessageFactory {
	
	private Map<String, MessageCreator> _creators;
	
	protected MessageFactory(Class<? extends IncomingMessage<?>>... messageTypes) {
		_creators = new HashMap<String, MessageCreator>();
		
		addCreators(messageTypes);
	}
	
	private void addCreators(Class<? extends IncomingMessage<?>>[] messageTypes) {
		for (Class<? extends IncomingMessage<?>> type : messageTypes) {
			addCreator(new MessageCreator(type));
		}
	}
	
	public IncomingMessage<?> create(String messageAsJSON) {
		try {
			JSONObject json = new JSONObject(messageAsJSON);
			return _creators.get(json.get(Message.KEY_METHOD)).create(json);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public IncomingMessage<?> create(InputStream jsonAsStream) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(jsonAsStream));
		StringBuilder body = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			body.append(line);
		}
		return create(body.toString()); 
	}
	
	private void addCreator(MessageCreator creator) {
		_creators.put(creator.getId(), creator);
	}

	protected static class MessageCreator {
		
		private Class<? extends IncomingMessage<?>> _messageType;
		
		public MessageCreator(Class<? extends IncomingMessage<?>> messageType) {
			_messageType = messageType;
		}
		
		private IncomingMessage<?> create(JSONObject json) {
			try {
				IncomingMessage<?> message = _messageType.newInstance();
				message.initJSON(json);
				return message;
			} catch (Exception e) {
				Logger.getInstance().fatal(e);
			}
			return null;
		}
		
		protected String getId() {
			return _messageType.getSimpleName();
		}
	}
}
