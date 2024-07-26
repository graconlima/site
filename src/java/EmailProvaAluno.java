
/*
import java.util.Properties;       
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.Transport;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

//@WebServlet(name="EmailProvaAluno", urlPatterns = "/EmailProvaAluno")
public class EmailProvaAluno extends HttpServlet{

    public void doGet(){
    
        Properties p = System.getProperties();
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.socketFactory.port","465");
        //p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.auth", true);
        
        Session s = Session.getInstance(p);
        Message m = new MimeMessage(s);
        try {
            m.setFrom(new InternetAddress("graconlima@gmail.com"));//INTERNET
            m.setRecipient(Message.RecipientType.TO, InternetAddress.parse("graconlima@gmail.com")[0]);//mandando o primeiro
            m.setSubject("Extrato de prova do Aluno");
            m.setText("GABARITO");
            
            Transport.send(m);
        } catch (AddressException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}
*/