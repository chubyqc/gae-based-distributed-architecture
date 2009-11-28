package chubyqc.gaeDistributed.server;

import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailManager {
	private static EmailManager _instance = new EmailManager();
	public static EmailManager getInstance() {
		return _instance;
	}

	private static final String REGX_EMAIL = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	private static final String MAIL_USERNAME = "chuby.qc@gmail.com";
	
	Pattern pattern;
	
	private EmailManager() {
		pattern = Pattern.compile(REGX_EMAIL, Pattern.CASE_INSENSITIVE);
	}
	
	public void sendEmail(String email, String subject, String content) throws AddressException, MessagingException {
		java.util.Properties props = new java.util.Properties();
        Session mailSession = Session.getDefaultInstance(props, null);
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(MAIL_USERNAME));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        msg.setSubject(subject);
        msg.setText(content);
        Transport.send(msg);
	}
	
	public boolean isValid(String email) {
		return pattern.matcher(email).matches();
	}
}
