package chubyqc.gaeDistributed.server.network.messages;

import org.json.JSONObject;

public abstract class Message {

	protected static final String PREFIX_GET = "get";
	protected static final String PREFIX_IS = "is";
	public static final String KEY_METHOD = "method";

	private JSONObject _json;
	
	protected Message() {
	}
	
	protected Message(JSONObject json) {
		_json = json;
	}
	
	public void setJSON(JSONObject json) {
		_json = json;
	}
	
	protected JSONObject getJSON() {
		return _json;
	}
}
