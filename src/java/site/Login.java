package site;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author GD
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {
    private Avaliacao prova;
    private HttpSession sessao;
    private String idProva = "4";
    private String matricula;
    int contadorQuestoes = 0;
    private String descricaoProva = "Avaliacao";
    //bd
    ConectaBD cbd = new ConectaBD();
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
        try {
            PrintWriter escritor = resposta.getWriter();
            escritor = resposta.getWriter();
            escritor.println("<html>\n" +
                            "    <head>\n" +
                            "        <title>LOGIN</title>\n" +
                            "        <meta charset=\"UTF-8\">\n" +
                            "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                            "        <link rel=\"stylesheet\" href=\""+requisicao.getContextPath()+"/styles/estiloTelasIniciais11.css\" />"+
                            "    </head>\n" +
                            "    <body>\n" +
                            "            <div class=\"login\">\n" +
                            "               <form action=\""+requisicao.getContextPath()+"/Login\" method=\"post\"/>"+
                            "                   LOGIN: <input type=\"text\" name=\"login\"/><br />\n" +
                            "                   SENHA: <input type=\"password\" name=\"senha\"/><br />\n" +
                            "                   <input type=\"submit\" />\n" +
                            "                   <a href=\""+requisicao.getContextPath()+"/CadastroAluno\">Cadastro Aluno</a>"+ 
                            "               <div class=\"mensagem\" />Forne&ccedil;a as informa&ccedil;&otilde;es acima"+
                            "               </div>\n" + 
                            "               </form>\n" +
                            "           </div>"+
                            "    </body>\n" +
                            "</html>");
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){
    
        String login = requisicao.getParameter("login");
        String senha = requisicao.getParameter("senha");
        
        try {
            PrintWriter escritor = resposta.getWriter();

            if(cbd.loginProfessor(login, senha)){

                sessao = requisicao.getSession(true);//pode usar para gravar atributos de sessao
                sessao.setAttribute("matricula", login);
                //sessao.setMaxInactiveInterval(14400);
                escritor.println("Redirecionando...");
                resposta.sendRedirect(requisicao.getContextPath()+"/AreaProfessor");
            }else if(cbd.loginAluno(login, senha)){

                sessao = requisicao.getSession(true);//pode usar para gravar atributos de sessao
                sessao.setAttribute("matricula", login);
                sessao.setMaxInactiveInterval(14400);
                escritor.println("Redirecionando...");
                resposta.sendRedirect(requisicao.getContextPath()+"/AreaAluno?matricula="+login);
            }else{
                escritor.println("Login incorreto...");
                resposta.sendRedirect(requisicao.getContextPath()+"/Login");
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}
