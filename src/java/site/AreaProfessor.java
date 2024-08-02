package site;
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
            escritor.println("<a href=\""+requisicao.getContextPath()+"/AdicionaPerguntaProva\">Adicionar Pergunta Prova</a>");
            escritor.println("<a href=\""+requisicao.getContextPath()+"/AdicionarProvaAluno\">Adicionar Prova Aluno</a>");
            escritor.println("<a href=\""+requisicao.getContextPath()+"/AreaAluno\">Area Aluno</a>");
            escritor.println("<a href=\""+requisicao.getContextPath()+"/CadastroAluno\">Cadastro Aluno</a>");
            escritor.println("<a href=\""+requisicao.getContextPath()+"/CorrigeProva\">Corrige Prova</a>");
            
            escritor.println("<a href=\""+requisicao.getContextPath()+"/CorrigirProva\">Corrigir Prova</a><form action=\"/CorrigirProva\" method=\"get\">");
            escritor.println("<input type=\"text\" name=\"matricula\" value=\"matricula\">");
            escritor.println("<input type=\"text\" name=\"idProva\" value=\"idProva\">");
            escritor.println("<input type=\"submit\">");
            escritor.println("</form>");
            
            
            escritor.println("<a href=\""+requisicao.getContextPath()+"/CriaPergunta\">Cria Pergunta</a>");
            escritor.println("<a href=\""+requisicao.getContextPath()+"/CriaProva\">Cria Prova</a>");
            escritor.println("<a href=\""+requisicao.getContextPath()+"/EmailProvaAluno\">Email Prova Aluno</a>");
            escritor.println("<a href=\""+requisicao.getContextPath()+"/Login\">Login</a>");
            escritor.println("<a href=\""+requisicao.getContextPath()+"/Prova\">Prova</a>");
            escritor.println("<a href=\""+requisicao.getContextPath()+"/VerTodosAlunos\">Ver todos alunos</a>");
            escritor.println("</p>");
            escritor.println("</body>");
            escritor.println("</html>");
        } catch (IOException ex) {

            System.err.println("Erro IO");
        }
    } 
}
