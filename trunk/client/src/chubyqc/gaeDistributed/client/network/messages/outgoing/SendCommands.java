package chubyqc.gaeDistributed.client.network.messages.outgoing;

import org.json.JSONArray;

import chubyqc.gaeDistributed.server.network.messages.outgoing.OutgoingMessage;
import chubyqc.gaeDistributed.server.network.messages.specs.ISendCommands;

public class SendCommands extends OutgoingMessage implements ISendCommands {
	
	private JSONArray _commands;
	
	public SendCommands(JSONArray commands) {
		_commands = commands;
	}

	@Override
	public JSONArray getCommands() {
		return _commands;
	}
}
