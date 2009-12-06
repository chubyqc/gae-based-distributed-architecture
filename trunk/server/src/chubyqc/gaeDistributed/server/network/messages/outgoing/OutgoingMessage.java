package chubyqc.gaeDistributed.server.network.messages.outgoing;

import java.lang.reflect.Method;

import org.json.JSONException;

import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.network.messages.Message;

public abstract class OutgoingMessage extends Message {
	
	protected OutgoingMessage() {
		initJSON();
	}

	protected OutgoingMessage(Object impl) {
		this();
		setJSONParameters(impl);
	}
	
	protected void setAttribute(String key, Object value) {
		try {
			getJSON().put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setJSONParameters(Object impl) {
		for (Method method : impl.getClass().getMethods()) {
			if (method.getName().startsWith(PREFIX_GET)) {
				try {
					setAttribute(method.getName(), method.invoke(impl));
				} catch (Exception e) {
					Logger.getInstance().error(e);
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return getJSON().toString();
	}
}
