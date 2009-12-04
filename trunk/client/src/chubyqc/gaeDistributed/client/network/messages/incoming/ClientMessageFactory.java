package chubyqc.gaeDistributed.client.network.messages.incoming;

import chubyqc.gaeDistributed.server.network.messages.incoming.MessageFactory;

public class ClientMessageFactory extends MessageFactory {
	private static ClientMessageFactory _instance = new ClientMessageFactory();
	public static ClientMessageFactory getInstance() {
		return _instance;
	}

	private ClientMessageFactory() {
		super(new MessageCreator[] {
				new MessageCreator(LaunchTorrent.class)
		});
	}
}
