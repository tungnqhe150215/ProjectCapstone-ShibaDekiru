package com.sep490.g49.shibadekiru.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

@Component
public class MailServiceProvider {

    @Value("${spring.mail.username}")
    private String email;

    @Value("${spring.mail.password}")
    private String password;

    public void sendEmail(String emailContact, String emailSubject, String body) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        send(session, email, emailContact, emailSubject, body);
    }


    private static void send(Session session, String fromEmail, String emailContact, String subject, String body) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "Shiba Dekiru"));
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailContact));
            message.setSubject(subject);
            message.setText(body, "UTF-8", "html");
            message.setSentDate(new Date());
            message.setContent(body, "text/html; charset=UTF-8");
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception occurred while sending mail");

        }
    }
}
