package pcprova;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AreaProfessor", urlPatterns = {"/AreaProfessor"})
public class AreaProfessor extends HttpServlet {
    
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){

        try {
            PrintWriter escritor = resposta.getWriter();
            escritor.println("<html>");
            escritor.println("<head>");
            escritor.println("</head>");
            escritor.println("<body>");
            escritor.println("<p>Menu");
            escritor.println("<a href=\"/site/AdicionaPerguntaProva\">Adicionar Pergunta Prova</a>");
            escritor.println("<a href=\"/site/AdicionarProvaAluno\">Adicionar Prova Aluno</a>");
            escritor.println("<a href=\"/site/AreaAluno\">Area Aluno</a>");
            escritor.println("<a href=\"/site/CadastroAluno\">Cadastro Aluno</a>");
            escritor.println("<a href=\"/site/CorrigeProva\">Corrige Prova</a>");
            
            escritor.println("<a href=\"/site/CorrigirProva\">Corrigir Prova</a><form action=\"/site/CorrigirProva\" method=\"get\">");
            escritor.println("<input type=\"text\" name=\"matricula\" value=\"matricula\">");
            escritor.println("<input type=\"text\" name=\"idProva\" value=\"idProva\">");
            escritor.println("<input type=\"submit\">");
            escritor.println("</form>");
            
            
            escritor.println("<a href=\"/site/CriaPergunta\">Cria Pergunta</a>");
            escritor.println("<a href=\"/site/CriaProva\">Cria Prova</a>");
            escritor.println("<a href=\"/site/EmailProvaAluno\">Email Prova Aluno</a>");
            escritor.println("<a href=\"/site/Login\">Login</a>");
            escritor.println("<a href=\"/site/Prova\">Prova</a>");
            escritor.println("<a href=\"/site/VerTodosAlunos\">Ver todos alunos</a>");
            escritor.println("</p>");
            escritor.println("</body>");
            escritor.println("</html>");
        } catch (IOException ex) {

            System.err.println("Erro IO");
        }
    } 
}
