package chubyqc.gaeDistributed.server.network.messages.incoming;

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
				message.setJSON(json);
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
