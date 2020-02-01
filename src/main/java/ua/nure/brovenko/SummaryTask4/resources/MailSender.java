package ua.nure.brovenko.SummaryTask4.resources;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Mail sender
 * @author Ivan Brovenko
 */
public class MailSender {

    /**
     * Method for sending emails
     * @param toEmail entrant email
     * @param subject subjet
     * @param message message
     * @throws MessagingException if something goes wrong
     */
    public synchronized void sendEmail(String toEmail,String fromEmail,String username,String password,String subject,String message) throws MessagingException {
        Properties props = System.getProperties();

        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.port","587");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.port","465");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Session session = Session.getDefaultInstance(props,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        session.setDebug(true);
        Message mailMessage=new MimeMessage(session);
        mailMessage.setFrom(new InternetAddress(fromEmail));
        mailMessage.setRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
        mailMessage.setContent(message,"text/plain; charset=UTF-8");
        mailMessage.setSubject(subject);

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com",username,password);
        transport.sendMessage(mailMessage,mailMessage.getAllRecipients());
    }

}
