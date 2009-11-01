package chubyqc.gaeDistributed.server.network.messages.outgoing;

import chubyqc.gaeDistributed.server.network.messages.Keys;

public class LaunchTorrent extends OutgoingMessage {
	
	public LaunchTorrent(String url) {
		setAttribute(Keys.LAUNCHTORRENT_URL, url);
	}
}
