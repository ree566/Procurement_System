/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.test;

import java.util.Date;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 *
 * @author Wei.Cheng
 */
public class mailsender {

    private final String mailHost;
    private final String mailPort;
    private final String username;
    private final String password;
    private final Properties props;
    Logger logger = null;

    public mailsender() {
        mailHost = "172.20.131.52";
        mailPort = "25";
        username = "kevin@172.20.131.52";
        password = "kevin";

        props = new Properties();
        props.setProperty("mail.smtp.host", mailHost);
        props.setProperty("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", mailPort);

        BasicConfigurator.configure();
        logger = Logger.getLogger(mailsender.class);
    }

    public boolean sendMail(Class cls, String from, String to, String subject, String text) {
//        boolean b = getMessage(from, to, subject, text);
//        if (b) {
//            logger.info(cls.getName() + "send success");
//        } else {
//            logger.error(cls.getName() + "send fail");
//        }
//        return b;
        return false;
    }

    private boolean getMessage(String from, String to, String subject, String text) {
        boolean b = false;
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject, "UTF-8");
            message.setSentDate(new Date());
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(text, "text/html;charset=UTF-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);

            Transport transport = session.getTransport("smtp");
            transport.connect(mailHost, Integer.parseInt(mailPort), username, password);
            Transport.send(message);
            b = true;
        } catch (MessagingException ex) {
            logger.error(ex);
        }
        return b;
    }
}
