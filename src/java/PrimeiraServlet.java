import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/PrimeiraServlet")//so faltava isso
public class PrimeiraServlet extends HttpServlet{
    
    
    public void service(HttpServletRequest requisicao, HttpServletResponse resposta) throws IOException, ServletException{
        PrintWriter pw = resposta.getWriter();
        pw.println("<h1>"+requisicao.getParameter("c1")+"</h1>");
        pw.println("<h1>"+requisicao.getParameter("c3")+"</h1>");
        pw.println("<h1>"+requisicao.getParameter("c4")+"</h1>");
        pw.println("<h1>"+requisicao.getParameter("c2")+"</h1>");

        OutraSerial os =  new OutraSerial();
        String sbt = os.receber();
        pw.println("LIDO BT: "+sbt);
        //pw.println("<h1> RR </h1>");
    }
}
