package chubyqc.gaeDistributed.server.network.messages.incoming;

import chubyqc.gaeDistributed.server.CommandsFactory;
import chubyqc.gaeDistributed.server.client.commands.Command;
import chubyqc.gaeDistributed.server.client.commands.Commands;
import chubyqc.gaeDistributed.server.network.messages.specs.ISendCommands;
import chubyqc.gaeDistributed.server.users.Manager;

public class SendCommands extends IncomingMessage<ISendCommands> {

	@Override
	public void execute() {
		Manager.getInstance().setCommands(getUsername(), 
				new Commands(CommandsFactory.getInstance().create(
						Command.class, getDataStore().getCommands())));
	}
	
}
