package chubyqc.gaeDistributed.server.network.messages.incoming;

import org.json.JSONException;

import chubyqc.gaeDistributed.server.network.messages.Message;

public abstract class IncomingMessage extends Message {
	
	protected String getAttribute(String key) {
		try {
			return getJSON().getString(key);
		} catch (JSONException e) {
			return new String();
		}
	}
	
	public abstract void execute();
}
