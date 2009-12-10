package chubyqc.gaeDistributed.server.network.messages.specs;

import org.json.JSONArray;

public interface IExecuteCommand {
	String getName();
	
	JSONArray getParameters();
}
