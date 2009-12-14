package chubyqc.gaeDistributed.server;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import chubyqc.gaeDistributed.server.client.states.commands.Command;

public class CommandsFactory {
	private static CommandsFactory _instance = new CommandsFactory();
	public static CommandsFactory getInstance() {
		return _instance;
	}
	
	private static final String KEY_NAME = "name";
	private static final String KEY_EXEC = "exec";
	private static final String KEY_VALUE = "value";
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
							createParameters(command.getJSONArray(KEY_PARAMETERS)));
		} catch (Exception e) {
			Logger.getInstance().fatal(e);
		}
		return null;
	}

	private static Map<String, String> createParameters(JSONArray jsonArray) throws JSONException {
		Map<String, String> parameters = new HashMap<String, String>();
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); ++i ) {
				JSONObject parameter = jsonArray.getJSONObject(0);
				parameters.put(parameter.getString(KEY_NAME), 
						parameter.getString(KEY_VALUE));
			}
		}
		return parameters;
	}
}
