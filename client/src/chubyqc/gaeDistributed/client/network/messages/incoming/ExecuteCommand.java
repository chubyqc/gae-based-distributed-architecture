package chubyqc.gaeDistributed.client.network.messages.incoming;

import chubyqc.gaeDistributed.client.commands.CommandsManager;
import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.network.messages.incoming.IncomingMessage;
import chubyqc.gaeDistributed.server.network.messages.specs.IExecuteCommand;

public class ExecuteCommand extends IncomingMessage<IExecuteCommand> {
	
	public void execute() {
		try {
			CommandsManager.getInstance().execute(getDataStore().getName(), getDataStore().getParameters());
		} catch (Exception e) {
			Logger.getInstance().error(e);
		}
	}
}
