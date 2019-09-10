import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendEmail {

    private Message message        = null;
    protected  static  String   SMTP_SERVER    = null;
    protected  static  String   SMTP_Port      = null;
    protected  static  String   SMTP_AUTH_USER = null;
    protected  static  String   SMTP_AUTH_PWD  = null;
    protected  static  String   EMAIL_FROM     = null;


    public SendEmail(String Email, String thema){

        Properties properties = new Properties();
        properties.put("mail.smtp.host"               , SMTP_SERVER);
        properties.put("mail.smtp.port"               , SMTP_Port  );
        properties.put("mail.smtp.auth"               , "true"     );
        properties.put("mail.smtp.ssl.enable"         , "true"     );
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        try {
            Authenticator auth = new EmailAuthenticator(SMTP_AUTH_USER,
                    SMTP_AUTH_PWD);
            Session session = Session.getDefaultInstance(properties,auth);
            session.setDebug(false);

            InternetAddress email_from = new InternetAddress(EMAIL_FROM);
            InternetAddress email_to   = new InternetAddress(Email );
            message = new MimeMessage(session);
            message.setFrom(email_from);
            message.setRecipient(Message.RecipientType.TO, email_to);
            message.setSubject(thema);
        } catch (AddressException e) {
//            System.err.println(e.getMessage());
        } catch (MessagingException e) {
//            System.err.println(e.getMessage());
        }



    }



    public boolean sendMessage (final String text)
    {
        boolean result = false;
        try {
            Multipart mmp = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(text, "text/plain; charset=utf-8");
            mmp.addBodyPart(bodyPart);
            message.setContent(mmp);
            Transport.send(message);
            result = true;
        } catch (MessagingException e){
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return result;
    }
}
