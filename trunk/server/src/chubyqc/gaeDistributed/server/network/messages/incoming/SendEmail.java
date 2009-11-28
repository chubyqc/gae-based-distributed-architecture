package chubyqc.gaeDistributed.server.network.messages.incoming;

import javax.mail.MessagingException;

import chubyqc.gaeDistributed.server.EmailManager;
import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.network.messages.Keys;

public class SendEmail extends IncomingMessage {

	private static final String MAIL_SUBJECT = "Confirmation";
	private static final String MAIL_CONTENT = "You registered as %s on Dafti.";
	
	@Override
	public void execute() {
		try {
			EmailManager.getInstance().sendEmail(getEmail(), MAIL_SUBJECT, 
					String.format(MAIL_CONTENT, getName()));
		} catch (MessagingException e) {
			Logger.getInstance().error(e);
		}
	}

	private String getName() {
		return getAttribute(Keys.SENDEMAIL_NAME);
	}
	
	private String getEmail() {
		return getAttribute(Keys.SENDEMAIL_EMAIL);
	}
}
