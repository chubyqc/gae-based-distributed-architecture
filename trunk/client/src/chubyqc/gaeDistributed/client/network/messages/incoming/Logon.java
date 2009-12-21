package chubyqc.gaeDistributed.client.network.messages.incoming;

import chubyqc.gaeDistributed.client.Client;
import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.network.messages.incoming.IncomingMessage;
import chubyqc.gaeDistributed.server.network.messages.specs.ILogon;
import chubyqc.gaeDistributed.server.users.UserException;

public class Logon extends IncomingMessage<ILogon> {
	
	private static final String ERR_BAD_LOGIN = "Your username/password isn't valid.";
	
	@Override
	public void execute() throws UserException {
		if (getDataStore().isSuccessful()) {
			Client.getInstance().announce();
		} else {
			Logger.getInstance().fatal(ERR_BAD_LOGIN);
			System.exit(0);
		}
	}	
}
