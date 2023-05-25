import java.io.File;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.Scanner;
public class main {
    public static String sendCV(String mail, String filePath) throws Exception{
        String OPT = "";
        String from = "niceuser619@gmail.com";
        String password = "fdyhslqiwzdbjjqf";
        String host = "smtp.gmail.com";
        Properties props = new Properties();

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
            protected  PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(from, password);
            }
        });

        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
            message.setSubject("CV");
            BodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Please find attached my CV.");
            BodyPart pdfBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            pdfBodyPart.setDataHandler(new DataHandler(source));
            pdfBodyPart.setFileName("CV.pdf");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textBodyPart);
            multipart.addBodyPart(pdfBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Check your email for the CV");
        }
        catch (Exception e){
            throw new RuntimeException((e));
        }
        return OPT;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the email: ");
        String MAIL = sc.nextLine();
        try{
            String x = sendCV(MAIL, "/home/mohanned/IdeaProjects/sendResume/CV/cv.pdf");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
