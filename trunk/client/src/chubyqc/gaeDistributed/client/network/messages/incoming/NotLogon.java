package chubyqc.gaeDistributed.client.network.messages.incoming;

import chubyqc.gaeDistributed.client.Client;
import chubyqc.gaeDistributed.server.network.ComManager;
import chubyqc.gaeDistributed.server.network.messages.incoming.IncomingMessage;
import chubyqc.gaeDistributed.server.network.messages.specs.INotLogon;

public class NotLogon extends IncomingMessage<INotLogon> {
	@Override
	public void execute() {
		ComManager.getInstance().invalidateSession();
		Client.getInstance().login();
	}
}
