package chubyqc.gaeDistributed.client.network.messages.incoming;

import chubyqc.gaeDistributed.client.network.ComManager;
import chubyqc.gaeDistributed.client.network.messages.outgoing.ClientBooted;
import chubyqc.gaeDistributed.server.network.messages.incoming.IncomingMessage;
import chubyqc.gaeDistributed.server.network.messages.specs.IIsClientBooted;

public class IsClientBooted extends IncomingMessage<IIsClientBooted> {

	@Override
	public void execute() {
		ComManager.getInstance().send(new ClientBooted());
	}

	
}
