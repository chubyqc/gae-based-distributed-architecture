package chubyqc.gaeDistributed.server.network.messages.outgoing;

import chubyqc.gaeDistributed.server.network.messages.specs.ISendEmail;

public class SendEmail extends OutgoingMessage implements ISendEmail {
	
	private String _email;
	
	public SendEmail(String email) {
		_email = email;
	}
	
	@Override
	public String getEmail() {
		return _email;
	}
}
