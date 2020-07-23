package server;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailSender extends Thread {
    public static final String SERVER_MAIL_ADDRESS = "aryanfromteam34@gmail.com";
    public static final String SERVER_MAIL_PASSWORD = "Aryan@Team34";

    private final String receipt;
    private final String subject;
    private final String content;

    public MailSender(String receipt, String subject, String content) {
        this.receipt = receipt;
        this.subject = subject;
        this.content = content;
    }

    public void send() {
        this.start();
    }

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");
            Session session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SERVER_MAIL_ADDRESS, SERVER_MAIL_PASSWORD);
                }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SERVER_MAIL_ADDRESS, "Aryan from Team34"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receipt));
            message.setSubject(subject);
            message.setText(content);
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.err.println("Message failed to send");
        }
    }
}