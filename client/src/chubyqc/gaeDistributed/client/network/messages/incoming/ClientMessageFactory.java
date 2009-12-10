package chubyqc.gaeDistributed.client.network.messages.incoming;

import chubyqc.gaeDistributed.server.network.messages.incoming.MessageFactory;

public class ClientMessageFactory extends MessageFactory {

	@SuppressWarnings("unchecked")
	public ClientMessageFactory() {
		super(ExecuteCommand.class,
				IsClientBooted.class
		);
	}
}
