package chubyqc.gaeDistributed.server.network.messages;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Message {
	
	protected static final String PREFIX_GET = "get";
	public static final String KEY_METHOD = "method";

	private JSONObject _json;
	
	protected void initJSON() {
		_json = new JSONObject();
		try {
			_json.put(KEY_METHOD, getClass().getSimpleName());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initJSON(JSONObject json) {
		_json = json;
	}
	
	protected JSONObject getJSON() {
		return _json;
	}
}
