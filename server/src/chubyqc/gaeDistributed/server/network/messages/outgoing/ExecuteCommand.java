package chubyqc.gaeDistributed.server.network.messages.outgoing;

import org.json.JSONObject;

import chubyqc.gaeDistributed.server.network.messages.specs.IExecuteCommand;

public class ExecuteCommand extends OutgoingMessage implements IExecuteCommand {
	
	private String _name;
	private JSONObject _parameters;
	
	public ExecuteCommand(String name, JSONObject parameters) {
		_name = name;
		_parameters = parameters;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public JSONObject getParameters() {
		return _parameters;
	}
}
