package prova;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "CriaProva", urlPatterns = {"/CriaProva"})
public class CriaProva extends HttpServlet {

    
    private HttpSession sessao;
    private ConectaBD cbd = new ConectaBD();
    
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
        sessao = requisicao.getSession(true);//pode usar para gravar atributos de sessao
        if(sessao.isNew()){
        
            sessao.setAttribute("HoraEntrada", new Date());
        }
        
        ResultSet rs = cbd.recuperarTodasQuestoes();
        
        try {
            PrintWriter escritor = resposta.getWriter();
            escritor.println("<html><head></head><body>");
            escritor.println("Hora de entrada; "+sessao.getAttribute("HoraEntrada"));
            escritor.println("<form action=\"/CriaProva\" method=\"post\">");

            escritor.println("NUMERO DA AVALIACAO: <input type=\"text\" name=\"campoNumAvaliacao\" /><br />");
            escritor.println("ETAPA: <input type=\"text\" name=\"campoEtapa\" /><br />");
            escritor.println("ID DISCIPLINA: <input type=\"text\" name=\"campoIDDisciplina\"/><br/>");
            escritor.println("QUANTIDADE DE QUESTOES: <input type=\"text\" name=\"campoQTDQuestoes\"/><br/>");
            
            escritor.println("<input type=\"submit\" />");
            
            escritor.println("</form></body></html>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){

        String numAvaliacao = requisicao.getParameter("campoNumAvaliacao");
        String etapa = requisicao.getParameter("campoEtapa");
        String idDisciplina = requisicao.getParameter("campoIDDisciplina");
        String qtdQuestoes = requisicao.getParameter("campoQTDQuestoes");
        
        cbd.inserirProva(numAvaliacao, etapa, idDisciplina, qtdQuestoes);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
