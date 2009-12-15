package chubyqc.gaeDistributed.server.network.messages.incoming;

import chubyqc.gaeDistributed.server.network.messages.specs.IClientBooted;
import chubyqc.gaeDistributed.server.users.Manager;

public class ClientBooted extends IncomingMessage<IClientBooted> {

	@Override
	public void execute() throws Exception {
		Manager.getInstance().setAddress(getUsername(), getAddress());
		System.out.println("Client booted.");
	}

}
