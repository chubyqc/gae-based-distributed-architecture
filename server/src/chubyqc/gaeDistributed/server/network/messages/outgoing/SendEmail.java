package chubyqc.gaeDistributed.server.network.messages.outgoing;

import chubyqc.gaeDistributed.server.network.messages.specs.ISendEmail;

public class SendEmail extends OutgoingMessage implements ISendEmail {
	
	private String _email;
	private String _username;
	
	public SendEmail(String username, String email) {
		_username = username;
		_email = email;
	}
	
	@Override
	public String getUsername() {
		return _username;
	}
	
	@Override
	public String getEmail() {
		return _email;
	}
}
