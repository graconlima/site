package prova;

/*package pcprova;

import java.util.Properties;       
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="EmailProvaAluno", urlPatterns = "/EmailProvaAluno")
public class EmailProvaAluno extends HttpServlet{

    //String usuario = "graconlima@gmail.com";
    //String senha = "";
    //String host = "smtp.gmail.com";    
    
    String usuario = "graconlima@outlook.com";
    String senha = "13102004amor";
    String host = "smtp-mail.outlook.com";
    String porta = "587";
    String destino = "graconlima@gmail.com";
    private ConectaBD cbd = new ConectaBD();
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
    
        Properties p = System.getProperties();
        p.put("mail.smtp.host", host);
        p.put("mail.smtp.port", porta);
        p.put("mail.smtp.user", usuario);
        p.put("mail.smtp.auth", true);
        p.put("mail.smtp.starttls.enable", true);
        p.put("mail.smtp.ssl.trust", "*");//foi a ultima alteracao
        ///*p.put("mail.smtp.socketFactory.port", "465");  
        //p.put("mail.smtp.socketFactory.fallback", "false");  
        //p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        //p.put("mail.smtp.host", "aspmx.l.google.com");
        //p.put("mail.smtp.socketFactory.port","25");
        //p.put("mail.smtp.socketFactory.port","465");
        //p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        ///*p.put("mail.smtp.auth", new Authenticator() {
        //    @Override
        //    protected PasswordAuthentication getPasswordAuthentication() {
        //        return new PasswordAuthentication("graconlima@gmail.com", "@Dv&ntista144");
        //    }
        //});

                
        Session s = Session.getInstance(p, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, senha);
            }
        });
        
        //Session s = Session.getDefaultInstance(p);
        Message m = new MimeMessage(s);
        try {
            m.setFrom(new InternetAddress(usuario));//INTERNET
            m.setRecipient(Message.RecipientType.TO, InternetAddress.parse(emailAluno(requisicao.getParameter("matricula")))[0]);//mandando o primeiro
            m.setSubject("Extrato de prova do Aluno "+requisicao.getParameter("matricula"));
            m.setSentDate(new Date());
            m.setContent(requisicao.getParameter("dadosemail")+"\n\n Sua nota na parte objetiva foi: "+cbd.corrigirProvaObjetivaAluno(requisicao.getParameter("idProva"), requisicao.getParameter("matricula")), "text/html");//para manter o formato tem que ir Html
            //m.setText(requisicao.getParameter("dadosemail")+"\n\n Sua nota na parte objetiva foi: "+cbd.corrigirProvaObjetivaAluno(requisicao.getParameter("idProva"), requisicao.getParameter("matricula")));
            
            Transport.send(m);
            //Transport t = s.getTransport("smtp");
            //t.connect("graconlima@gmail.com", "@Dv&ntista144");
            //t.sendMessage(m, m.getAllRecipients());
            //t.close();
        } catch (AddressException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    
    public String emailAluno(String matricula){
    
        ResultSet rs = cbd.recuperarAlunoPorMatricula(matricula);
        
        String email = "";
        try {
            while(rs.next()){
                
                return rs.getString("email");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmailProvaAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return email;
    }
}
*/