package chubyqc.gaeDistributed.server.network.messages.outgoing;

import org.json.JSONArray;

import chubyqc.gaeDistributed.server.network.messages.specs.IExecuteCommand;

public class ExecuteCommand extends OutgoingMessage implements IExecuteCommand {
	
	private String _name;
	private JSONArray _parameters;
	
	public ExecuteCommand(String name, JSONArray parameters) {
		_name = name;
		_parameters = parameters;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public JSONArray getParameters() {
		return _parameters;
	}
}
