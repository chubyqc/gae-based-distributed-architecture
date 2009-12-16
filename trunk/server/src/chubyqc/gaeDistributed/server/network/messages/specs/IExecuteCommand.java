package chubyqc.gaeDistributed.server.network.messages.specs;

import org.json.JSONObject;

public interface IExecuteCommand {
	String getName();
	
	JSONObject getParameters();
}
