package chubyqc.gaeDistributed.server.network.messages.incoming;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import chubyqc.gaeDistributed.server.network.messages.Message;

public abstract class MessageFactory {
	
	private Map<String, IMessageCreator<? extends IncomingMessage>> _creators;
	
	protected MessageFactory(IMessageCreator<? extends IncomingMessage>[] creators) {
		_creators = new HashMap<String, IMessageCreator<?>>();
		
		addCreators(creators);
	}
	
	private void addCreators(IMessageCreator<? extends Message>[] creators) {
		for (IMessageCreator<? extends Message> creator : creators) {
			addCreator(creator);
		}
	}
	
	public IncomingMessage create(String messageAsJSON) {
		try {
			JSONObject json = new JSONObject(messageAsJSON);
			return _creators.get(json.get(Message.KEY_METHOD)).create(json);
		} catch (JSONException e) {
			return null;
		}
	}
	
	private void addCreator(IMessageCreator<? extends IncomingMessage> creator) {
		_creators.put(creator.getId(), creator);
	}

	protected static abstract class IMessageCreator<T extends IncomingMessage> {
		protected abstract T create(JSONObject json);
		
		protected String getId() {
			return ((Class<?>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getSimpleName();
		}
	}
}
