package chubyqc.gaeDistributed.server.client.commands;

import java.io.Serializable;
import java.util.Map;

public class Commands implements Serializable {
	
	private static final long serialVersionUID = 1L;
	protected Map<String, Command> _commands;
	
	Commands() {}
	
	public Commands(Map<String, Command> commands) {
		_commands = commands;
	}
	
	public void printCommands(ICommandsWriter writer) {
		for (Command command : _commands.values()) {
			writer.print(command);
		}
	}
	
}
