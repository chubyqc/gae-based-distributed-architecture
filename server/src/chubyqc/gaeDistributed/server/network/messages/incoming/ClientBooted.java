package chubyqc.gaeDistributed.server.network.messages.incoming;

import chubyqc.gaeDistributed.server.network.messages.specs.IClientBooted;

public class ClientBooted extends IncomingMessage<IClientBooted> {

	@Override
	public void execute() {
		System.out.println("Client booted.");
	}

}
