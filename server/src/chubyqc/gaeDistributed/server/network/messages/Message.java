package chubyqc.gaeDistributed.server.network.messages;

import org.json.JSONObject;

public abstract class Message {
	
	protected static final String PREFIX_GET = "get";
	public static final String KEY_METHOD = "method";

	private JSONObject _json;
	private String _username;
	
	protected Message() {
	}
	
	protected Message(JSONObject json) {
		_json = json;
	}
	
	public void setUsername(String username) {
		_username = username;
	}
	
	protected String getUsername() {
		return _username;
	}
	
	public void setJSON(JSONObject json) {
		_json = json;
	}
	
	protected JSONObject getJSON() {
		return _json;
	}
}
