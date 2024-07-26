

import pcprova.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author GD
 */
@WebServlet(name = "Login_copia", urlPatterns = {"/Login_copia"})
public class Login_copia extends HttpServlet {

    private ConectaBD cbd = new ConectaBD();
    
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
    
        try {
            PrintWriter escritor = resposta.getWriter();
            escritor.println("<html>\n" +
                            "    <head>\n" +
                            "        <title>LOGIN</title>\n" +
                            "        <meta charset=\"UTF-8\">\n" +
                            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "        <link rel=\"stylesheet\" href=\""+requisicao.getContextPath()+"/styles/estiloTelasIniciais.css\" />"+
                            "    </head>\n" +
                            "    <body>\n" +
                            "        <div class=\"telaInicial\">\n" +
                            "            <div class=\"login\">\n" +
                            "               <form action=\"/Servlet1/Login\" method=\"post\"/>"+
                            "                LOGIN: <input type=\"text\" name=\"login\"/><br />\n" +
                            "                SENHA: <input type=\"password\" name=\"senha\"/><br />\n" +
                            "            </div>\n" +
                            "            <div class=\"mensagem\" />Forne&ccedil;a as informa&ccedil;&otilde;es acima"+ 
                            "            <a href=\"/site/AreaAluno\">AREA DO ALUNO</a>"+ 
                            "           </div>"+
                            "            <input type=\"submit\" />\n" +
                            "        </div>\n" +
                            "    </body>\n" +
                            "</html>");
        } catch (IOException ex) {
            Logger.getLogger(Login_copia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){
    
        String login = requisicao.getParameter("login");
        String senha = requisicao.getParameter("senha");
        
        try {
            PrintWriter escritor = resposta.getWriter();
                       
            if(cbd.loginAluno(login, senha)){
                escritor.println("Redirecionando...");
                resposta.sendRedirect("/site/Prova?matricula="+login);
            }else{
                escritor.println("Login incorreto...");
                resposta.sendRedirect("/site/Login");
            }
        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }
}
