/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twittest.mail;

import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author youser
 */
public class Mail {

    private final Properties props;
    private Session session;
    private final MailAccount mailAccount;

    public Mail() {
        props = System.getProperties();
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.auth", "true");
        mailAccount = new MailAccount();
        mailAccount.setLogin("twittsearch20150309@gmail.com");
        mailAccount.setPasswd("20150309twittsearch");
    }

    public void sendMail(String recepient) {
        try {
            session = Session.getInstance(props, null);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mailAccount.getLogin()));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recepient, false));
            msg.setSubject("Test " + System.currentTimeMillis());
            msg.setText("Hello there !");
            msg.setHeader("TwitSearchTest", "Test program");
            msg.setSentDate(new Date());
            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
            t.connect("smtp.gmail.com", mailAccount.getLogin(), mailAccount.getPasswd());
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Response: " + t.getLastServerResponse());
            t.close();
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
