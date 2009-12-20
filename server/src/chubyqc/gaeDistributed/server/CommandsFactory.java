package chubyqc.gaeDistributed.server;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import chubyqc.gaeDistributed.server.client.widgets.commands.Command;

public class CommandsFactory {
	private static CommandsFactory _instance = new CommandsFactory();
	public static CommandsFactory getInstance() {
		return _instance;
	}
	
	private static final String KEY_NAME = "name";
	private static final String KEY_EXEC = "exec";
	private static final String KEY_PARAMETERS = "parameters";
	
	private CommandsFactory() {}
	
	public  Map<String, Command> create(Class<? extends Command> type, JSONArray commandsJSON) {
		Map<String, Command> commands = new HashMap<String, Command>();
		for (int i = 0; i < commandsJSON.length(); ++i) {
			try {
				Command command = CommandsFactory.getInstance().createCommand(
						type, commandsJSON.getJSONObject(i));
				commands.put(command.getName(), command);
			} catch (JSONException e) {
				Logger.getInstance().error(e);
			}
		}
		return commands;
	}
	
	private <T extends Command> T createCommand(Class<T> type, JSONObject command) {
		try {
			return type.getConstructor(
					String.class, String.class, Map.class).newInstance(
							command.getString(KEY_NAME), 
							command.getString(KEY_EXEC),
							createParameters(command.getJSONObject(KEY_PARAMETERS)));
		} catch (Exception e) {
			Logger.getInstance().fatal(e);
		}
		return null;
	}

	private static Map<String, String> createParameters(JSONObject jsonObject) throws JSONException {
		Map<String, String> parameters = new HashMap<String, String>();
		if (jsonObject != null) {
			String[] names = JSONObject.getNames(jsonObject);
			if (names != null) {
				for (String name : names) {
					parameters.put(name, jsonObject.getString(name));
				}
			}
		}
		return parameters;
	}
}
