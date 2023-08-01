package com.eve.util;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class EmailReceiver {

    private static final String GMAIL_HOST = "imap.gmail.com";
    private static final int GMAIL_PORT = 993;

    String username = "com_eve_home_server@outlook.com";
    String password = "CSRthe9805";

    private Store store;

    public EmailReceiver(String username, String password) {
//        this.username = username;
//        this.password = password;
    }

    public void init() throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.imaps.host", "imap.gmail.com");
//        properties.setProperty("mail.imaps.user", username);
//        properties.setProperty("mail.imaps.password", password);
        properties.setProperty("mail.imaps.port", "993");
        properties.setProperty("mail.imaps.auth", "true");
        properties.setProperty("mail.debug", "true");

        store = Session.getInstance(properties).getStore();
        store.connect(GMAIL_HOST, GMAIL_PORT, username, password);
    }

    public List<Message> getUnreadEmails() throws MessagingException {
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        List<Message> unreadEmails = Arrays.asList(
                inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false)));
        inbox.close(false);

        return unreadEmails;
    }

    public String getEmailBody(Message message) throws MessagingException, IOException {
        Object content = message.getContent();
        if (content instanceof String) {
            return (String) content;
        }

        return "";
    }

    public void close() throws MessagingException {
        if (store != null) {
            store.close();
        }
    }

    public static void main(String[] args) {

        String username = "";
        String password = "";

        EmailReceiver gmailReceiver = new EmailReceiver(username, password);
        try {
            gmailReceiver.init();

            List<Message> unreadEmails = gmailReceiver.getUnreadEmails();
            for (Message email : unreadEmails) {
                String emailBody = gmailReceiver.getEmailBody(email);
                System.out.println("Subject: " + email.getSubject());
                System.out.println("From: " + email.getFrom()[0]);
                System.out.println("Email Body: " + emailBody);
                System.out.println("--------------------------");
            }

            gmailReceiver.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
