package chubyqc.gaeDistributed.server.network.messages.outgoing;

import chubyqc.gaeDistributed.server.network.messages.specs.ILaunchTorrent;

public class LaunchTorrent extends OutgoingMessage {
	
	public LaunchTorrent(final String url) {
		super(new ILaunchTorrent() {
			
			@Override
			public String getURL() {
				return url;
			}
		});
	}
}
