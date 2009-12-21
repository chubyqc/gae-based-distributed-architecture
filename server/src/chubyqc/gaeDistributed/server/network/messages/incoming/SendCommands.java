package chubyqc.gaeDistributed.server.network.messages.incoming;

import chubyqc.gaeDistributed.server.CommandsFactory;
import chubyqc.gaeDistributed.server.client.widgets.commands.Command;
import chubyqc.gaeDistributed.server.client.widgets.commands.Commands;
import chubyqc.gaeDistributed.server.network.messages.specs.ISendCommands;
import chubyqc.gaeDistributed.server.users.Manager;
import chubyqc.gaeDistributed.server.users.UserException;

public class SendCommands extends IncomingMessage<ISendCommands> {

	@Override
	public void execute() throws UserException {
		Manager.getInstance().setCommands(getUsername(), 
				new Commands(CommandsFactory.getInstance().create(
						Command.class, getDataStore().getCommands())));
	}
	
}
