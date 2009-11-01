package chubyqc.gaeDistributed.client.messages.incoming;

import org.json.JSONObject;

import chubyqc.gaeDistributed.server.network.messages.incoming.MessageFactory;

public class ClientMessageFactory extends MessageFactory {
	private static ClientMessageFactory _instance = new ClientMessageFactory();
	public static ClientMessageFactory getInstance() {
		return _instance;
	}

	private ClientMessageFactory() {
		super(new IMessageCreator<?>[] {
				new LaunchTorrentCreator()
		});
	}

	
	private static class LaunchTorrentCreator extends IMessageCreator<LaunchTorrent> {

		public LaunchTorrent create(JSONObject json) {
			LaunchTorrent message = new LaunchTorrent();
			message.initJSON(json);
			return message;
		}
		
	}
}
