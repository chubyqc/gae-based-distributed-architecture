package chubyqc.gaeDistributed.server.network.messages.incoming;

import javax.mail.MessagingException;

import chubyqc.gaeDistributed.server.EmailManager;
import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.network.messages.specs.ISendEmail;

public class SendEmail extends IncomingMessage<ISendEmail> {

	private static final String MAIL_SUBJECT = "Confirmation";
	private static final String MAIL_CONTENT = "You registered as %s on Dafti.";
	
	@Override
	public void execute() {
		try {
			EmailManager.getInstance().sendEmail(
					getDataStore().getEmail(), MAIL_SUBJECT, 
					String.format(MAIL_CONTENT, 
							getUsername()));
		} catch (MessagingException e) {
			Logger.getInstance().error(e);
		}
	}
}
