package chubyqc.gaeDistributed.client.network.messages.incoming;

import java.io.IOException;

import chubyqc.gaeDistributed.server.network.messages.Keys;
import chubyqc.gaeDistributed.server.network.messages.incoming.IncomingMessage;

public class LaunchTorrent extends IncomingMessage {

	private static final String COMMAND_TRANSMISSION = "transmission";
	private static final String COMMAND_LAUNCH = "transmission-remote -a %s";
	
	public void execute() {
		String url = getUrl();
		System.out.println(url);
		try {
			Runtime.getRuntime().exec(COMMAND_TRANSMISSION);
			Runtime.getRuntime().exec(String.format(COMMAND_LAUNCH, url)).exitValue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getUrl() {
		return getAttribute(Keys.LAUNCHTORRENT_URL);
	}
}
