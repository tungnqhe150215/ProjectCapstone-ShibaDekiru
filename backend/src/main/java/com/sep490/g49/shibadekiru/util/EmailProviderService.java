package com.sep490.g49.shibadekiru.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


import java.util.Properties;

@Component
public class EmailProviderService {

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


        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        sendHtml(session, email, emailContact, emailSubject, body);
    }

    private void sendHtml(Session session, String email, String emailContact, String emailSubject, String body) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");

            mimeMessage.addHeader("format", "flowed");

            mimeMessage.addHeader("Content-Transfer-Encoding", "8bit");

            mimeMessage.setFrom(new InternetAddress(email, "Shiba Dekiru"));

            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailContact));


            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setTo(emailContact);
            mimeMessageHelper.setSubject(emailSubject);
            mimeMessageHelper.setText(body, true);

            Transport.send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception occurred while sending mail");
        }
    }
}
