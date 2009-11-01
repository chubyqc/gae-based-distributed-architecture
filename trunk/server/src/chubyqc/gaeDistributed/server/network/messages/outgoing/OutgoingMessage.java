package chubyqc.gaeDistributed.server.network.messages.outgoing;

import org.json.JSONException;

import chubyqc.gaeDistributed.server.network.messages.Message;

public abstract class OutgoingMessage extends Message {

	protected OutgoingMessage() {
		initJSON();
	}
	
	protected void setAttribute(String key, String value) {
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
