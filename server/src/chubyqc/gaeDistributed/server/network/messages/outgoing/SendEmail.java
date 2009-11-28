package chubyqc.gaeDistributed.server.network.messages.outgoing;

import chubyqc.gaeDistributed.server.network.messages.Keys;

public class SendEmail extends OutgoingMessage {
	
	public SendEmail(String name, String email) {
		setAttribute(Keys.SENDEMAIL_NAME, name);
		setAttribute(Keys.SENDEMAIL_EMAIL, email);
	}
}
