package chubyqc.gaeDistributed.client.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import chubyqc.gaeDistributed.client.Client;
import chubyqc.gaeDistributed.client.network.messages.outgoing.SendCommands;
import chubyqc.gaeDistributed.server.CommandsFactory;
import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.client.states.commands.Commands;

public class CommandsManager extends Commands {
	private static final long serialVersionUID = 1L;

	private static CommandsManager _instance = new CommandsManager(
			createCommands(getFileContent("commands")));
	public static CommandsManager getInstance() {
		return _instance;
	}
	
	private static final String KEY_COMMANDS = "commands";
	
	private JSONArray _commandsJSON;
	
	private CommandsManager(JSONArray commands) {
		super(CommandsFactory.getInstance().create(Command.class, commands));
		_commandsJSON = commands;
	}
	
	public void announce() {
		Client.getInstance().send(new SendCommands(_commandsJSON));
	}
	
	public void execute(String commandName, JSONArray parameters) throws IOException, JSONException {
		((Command)_commands.get(commandName)).execute(parameters);
	}
	
	private static JSONArray createCommands(String content) {
		if (content != null) {
			try {
				return new JSONObject(content).getJSONArray(KEY_COMMANDS);
			} catch (JSONException e) {
				Logger.getInstance().fatal(e);
			}
		}
		return new JSONArray();
	}
	
	private static String getFileContent(String path) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line = null;
			StringBuilder result = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (IOException e) {
			Logger.getInstance().fatal(e);
		}
		return null;
	}
}
