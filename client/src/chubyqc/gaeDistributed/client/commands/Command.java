package chubyqc.gaeDistributed.client.commands;

import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Command extends
		chubyqc.gaeDistributed.server.client.commands.Command {

	private static final long serialVersionUID = 1L;
	private static final String SPACE = " ";
	private static final String KEY_NAME = "name";
	private static final String KEY_VALUE = "value";
	
	public Command(String name, String exec, Map<String, String> parameters) throws JSONException {
		super(name, exec, parameters);
	}
	
	public void execute(JSONArray parameters) throws IOException, JSONException {
		Runtime.getRuntime().exec(createCommandLine(parameters));
	}
	
	private String createCommandLine(JSONArray parameters) throws JSONException {
		StringBuilder cmdLine = new StringBuilder(_exec);
		for (int i = 0; i < parameters.length(); ++i) {
			JSONObject parameter = parameters.getJSONObject(i);
			cmdLine.append(SPACE);
			cmdLine.append(parameter.getString(KEY_NAME));
			cmdLine.append(parameter.getString(KEY_VALUE));
		}
		return cmdLine.toString();
	}
}
