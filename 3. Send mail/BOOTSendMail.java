package in.cdacnoida.dava.util;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import in.cdacnoida.dava.transactions.BOOTSentEmailDetailsRepository;

@Component
public class BOOTSendMail {
	private BOOTAccessPaths bOOTAccessPaths;

	@Autowired
	public BOOTSendMail(BOOTAccessPaths bOOTAccessPaths) {
		this.bOOTAccessPaths = bOOTAccessPaths;
	}

	@Autowired
	BOOTSentEmailDetailsRepository bOOTSentEmailDetailsRepository;
	
	@Value("${ADMIN}")
	private String admin;

	public void sendGemReportEmail(String DownloadId) {
		String PROJECT_URL = bOOTAccessPaths.getPROJECT_URL();
		String latestDownloadId = DownloadId;
		// bOOTCRSGemReportHistoryRepository.getLatestValidDownloadId();
		StringBuffer strUrl = new StringBuffer();
		strUrl.append("Dear Sir,<br/><br/>");
		strUrl.append("Please find the below url to get the latest Report.<br/><br/>");
		strUrl.append(PROJECT_URL + "GEMReport.do?hmode=DGMXXXVVXXXFile&id=" + DataEncoder.encode(latestDownloadId));
		strUrl.append("<br/><br/>");
		ArrayList<String> al = new ArrayList<String>();
		al.add(admin);
		// al.add("helpdesk@bis.gov.in");
		// al.add("its@bis.gov.in");
		for (int i = 0; i < al.size(); i++) {
			try {
				TransferToMailServer(al.get(i), "Report from BIS : ", strUrl.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void TransferToMailServer(String mail, String subj, String emailMsgTxt) throws MessagingException {
		// String hostname = bOOTAccessPaths.getSMTP_HOST_NAME();
		// String username1 = bOOTAccessPaths.getSMTP_AUTH_USER();
		// String password1 = bOOTAccessPaths.getSMTP_AUTH_PWD();
		String from = bOOTAccessPaths.getEmailFromAddress();
		String ccMail = bOOTAccessPaths.getEmailCC();
		String emailSubjectTxt = subj;
		emailMsgTxt = emailMsgTxt
				+ "<br/>For details information on BIS, consult the e-BIS Portal (www.manakonline. in) <br/> Please use BIS CARE APP for verification of ISI-marked goods and hallmarked gold jewellery.";
		postMail(mail, emailSubjectTxt, emailMsgTxt, from, ccMail);
	}

	public void postMail(String recipients, String subject, String message, String from, String strccMail)
			throws MessagingException {
		Integer count = bOOTSentEmailDetailsRepository.getNextMailCounter();
		bOOTSentEmailDetailsRepository.insertEmailDetails(recipients, subject, message, count);
		String SMTP_HOST_NAME = bOOTAccessPaths.getSMTP_HOST_NAME();
		String SMTP_PORT = bOOTAccessPaths.getSMTP_PORT();
		Properties props = new Properties();
		boolean debug = true;
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", SMTP_PORT);
		// SecurityManager security = System.getSecurityManager();
		Authenticator auth = new BOOTSendMail.SMTPAuthenticator();
		Session session = Session.getInstance(props, auth);
		session.setDebug(debug);
		Message msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);
		InternetAddress addressTo = new InternetAddress(recipients);
		InternetAddress addresscc = new InternetAddress(strccMail);
		msg.setRecipient(Message.RecipientType.TO, addressTo);
		msg.setRecipient(Message.RecipientType.CC, addresscc);
		msg.setSubject(subject);
		msg.setContent(message, "text/html");
		Transport.send(msg);
		bOOTSentEmailDetailsRepository.updateEmailDetails(count);
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = bOOTAccessPaths.getSMTP_AUTH_USER();
			String password = bOOTAccessPaths.getSMTP_AUTH_PWD();
			return new PasswordAuthentication(username, password);
		}
	}
}
