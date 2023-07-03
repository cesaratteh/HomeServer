package com.eve.notifier;

import com.eve.config.AppConfig;
import com.eve.config.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailNotifier implements Notifier {

    private final String FROM_EMAIL = AppConfig.EMAIL_NOTIFIER_FROM_EMAIL;
    private final String FROM_EMAIL_PASSWORD = AppConfig.EMAIL_NOTIFIER_FROM_EMAIL_PASSWORD;
    private final String TO_EMAIL = AppConfig.EMAIL_NOTIFIER_TO_EMAIL;

    @Override
    public void notify(final String title, final String body, final String url) {
        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "587");

            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(FROM_EMAIL, FROM_EMAIL_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(TO_EMAIL));
            message.setSubject(title);
            message.setText(body + " \n " + url);

            Transport.send(message);
            LoggerFactory.getLogger(this.getClass()).info("Email sent successfully!");
        } catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error("EmailNotifier failed to notify ", e);
        }
    }
}
