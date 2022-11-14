package email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author user
 */
public class SendEmailUsingGMailSMTP {
    public static void main(String[] args) {
        String to = "shaxzodxudoyberdiyev77@gmail.com";
        String from = "nematovjahongir68@gmail.com";
        final String username = "Jahongir Ne'matov";
        final String password = "Jahongir2002";
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls","true");
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.port","587");
        Session session;
        session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Assalomu Aleykum");
            message.setText("Nima gap!");
            Transport.send(message);
            System.out.println("Xabar muvaffaqiyatli jo`natildi");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
