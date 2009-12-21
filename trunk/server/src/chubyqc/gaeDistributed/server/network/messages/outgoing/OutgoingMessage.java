package chubyqc.gaeDistributed.server.network.messages.outgoing;

import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;

import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.network.messages.Message;

public abstract class OutgoingMessage extends Message {
	
	protected OutgoingMessage() {
		super(new JSONObject());
		setMethod();
	}
	
	private void setMethod() {
		try {
			getJSON().put(KEY_METHOD, getClass().getSimpleName());
		} catch (JSONException e) {
			Logger.getInstance().fatal(e);
		}
	}

	public void populateJSON() {
		for (Method method : getClass().getMethods()) {
			if (method.getName().startsWith(PREFIX_GET) &&
					method.getDeclaringClass() != Object.class) {
				try {
					setAttribute(method.getName(), method.invoke(this));
				} catch (Exception e) {
					Logger.getInstance().error(e);
				}
			}
		}
	}
	
	protected void setAttribute(String key, Object value) {
		try {
			getJSON().put(key, value);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return getJSON().toString();
	}
}
