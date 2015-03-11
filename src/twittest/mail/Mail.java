/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twittest.mail;

import com.sun.mail.smtp.SMTPTransport;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JLabel;

/**
 *
 * @author youser
 */
public class Mail {

    private final Properties props;
    private Session session;
    private Message msg;
    private final MailAccount mailAccount;
    public static List<File> attachments = new ArrayList();
    public static int attachmentsCount = 0;
    private static JLabel[] attachmentLabels = {SendEMailForm.attachName1, SendEMailForm.attachName2, SendEMailForm.attachName3};

    public Mail() {
        props = System.getProperties();
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.auth", "true");
        msg = new MimeMessage(session);
        mailAccount = new MailAccount();
        mailAccount.setLogin("twittsearch20150309@gmail.com");
        mailAccount.setPasswd("20150309twittsearch");
    }

    public void sendMail(String recepient, String subject, String body) {
        try {
            session = Session.getInstance(props, null);
            msg.setFrom(new InternetAddress(mailAccount.getLogin()));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recepient, false));
            msg.setSubject(subject);
            msg.setHeader("TwitSearchTest", "Test program");
            msg.setSentDate(new Date());

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            if (attachmentsCount > 0) {
                MimeBodyPart[] attachmentBodyParts = new MimeBodyPart[attachmentsCount];
                for (int i = 0; i < attachmentBodyParts.length; i++) {
                    messageBodyPart = new MimeBodyPart();
                    String fileName = attachments.get(i).getName();
                        
                    DataSource source = new FileDataSource(attachments.get(i));
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(fileName);
                    multipart.addBodyPart(messageBodyPart);
                }
            }
            msg.setContent(multipart);

            SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
            t.connect("smtp.gmail.com", mailAccount.getLogin(), mailAccount.getPasswd());
            t.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Response: " + t.getLastServerResponse());
            t.close();
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void displayAttName() {
        for (int i = 0; i < attachmentsCount; i++) {
            attachmentLabels[i].setText(attachments.get(i).getName());
            attachmentLabels[i].setVisible(true);
        }
    }
}
