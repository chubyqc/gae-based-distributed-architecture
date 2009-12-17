package chubyqc.gaeDistributed.client.commands;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class Command extends
		chubyqc.gaeDistributed.server.client.states.commands.Command {

	private static final long serialVersionUID = 1L;
	private static final String SPACE = " ";
	
	public Command(String name, String exec, Map<String, String> parameters) throws JSONException {
		super(name, exec, parameters);
	}
	
	public void execute(JSONObject parameters) throws IOException, JSONException {
		Runtime.getRuntime().exec(createCommandLine(parameters));
	}
	
	private String createCommandLine(JSONObject parameters) throws JSONException {
		StringBuilder cmdLine = new StringBuilder(_exec);
		String[] names = JSONObject.getNames(parameters);
		if (names != null) {
			for (String name : names) {
				cmdLine.append(SPACE);
				cmdLine.append(name);
				cmdLine.append(SPACE);
				cmdLine.append(parameters.get(name));
			}
		}
		return cmdLine.toString();
	}
}
