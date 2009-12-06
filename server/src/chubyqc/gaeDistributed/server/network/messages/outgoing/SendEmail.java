package chubyqc.gaeDistributed.server.network.messages.outgoing;

import chubyqc.gaeDistributed.server.network.messages.specs.ISendEmail;

public class SendEmail extends OutgoingMessage {
	
	public SendEmail(final String name, final String email) {
		super(new ISendEmail() {
			
			@Override
			public String getName() {
				return name;
			}
			
			@Override
			public String getEmail() {
				return email;
			}
		});
	}
}
