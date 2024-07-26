package pcprova;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javafx.print.Collation;

@WebServlet(name = "CriaPergunta", urlPatterns = {"/CriaPergunta"})
public class CriaPergunta extends HttpServlet {
    
    private HttpSession sessao;
    private ConectaBD cbd = new ConectaBD();
    private ResultSet rs;
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
        sessao = requisicao.getSession(true);//pode usar para gravar atributos de sessao
        if(sessao.isNew()){
        
            sessao.setAttribute("HoraEntrada", new Date());
        }
        try {
            PrintWriter escritor = resposta.getWriter();
            escritor.println("<html><head><link rel=\"stylesheet\" href=\""+requisicao.getContextPath()+"/styles/estiloTelasIniciais11.css \" /></head><body>");
            escritor.println("Hora de entrada; "+sessao.getAttribute("HoraEntrada"));
            escritor.println("<div class=\"criapergunta\">");
            escritor.println("<form action=\"/site/CriaPergunta\" method=\"post\">");

            escritor.println("(QUESTAO)<br> <textarea name=\"campoPergunta\" cols=\"65\" rows=\"5\"/></textarea> <br />");
            escritor.println("a) <input type=\"text\" name=\"campoAlternativa1\" size=\"60\"/> <br />");
            escritor.println("b) <input type=\"text\" name=\"campoAlternativa2\" size=\"60\"/> <br />");
            escritor.println("c) <input type=\"text\" name=\"campoAlternativa3\" size=\"60\"/> <br />");
            escritor.println("d) <input type=\"text\" name=\"campoAlternativa4\" size=\"60\"/> <br />");
            //escritor.println("RESPOSTA ALTERNATIVA: <input type=\"text\" name=\"respostaAlternativa\"/> <br />");
            escritor.println("RESPOSTA ALTERNATIVA:");
            escritor.println("<select name=\"respostaAlternativa\">");
            escritor.println("<option value=\"A\">A</option>");
            escritor.println("<option value=\"B\">B</option>");
            escritor.println("<option value=\"C\">C</option>");
            escritor.println("<option value=\"D\">D</option>");
            escritor.println("</select><br/>");
            
            escritor.println("RESPOSTA JUSTIFICATIVA: <input type=\"text\" name=\"respostaJustificativa\" value=\"-\"/> <br />");
            //escritor.println("ID DISCIPLINA: <input type=\"text\" name=\"idDisciplina\"/> <br />");
            escritor.println("ID DISCIPLINA: ");
            escritor.println("<select name=\"idDisciplina\">");
            rs = cbd.recuperarTodasDisciplinas();
            while(rs.next()){
                escritor.println("<option value="+rs.getString("idDisciplina")+">"+rs.getString("nomeDisciplina")+"</option>");
            }
            escritor.println("</select>");

            escritor.println("<br/><input type=\"submit\" />");
            
            escritor.println("</form></body></html>");
            escritor.println("</div>");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(CriaPergunta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){

        //Enumeration param = requisicao.getParameterNames();
        //List listaParam = Collections.list(param);
        //listaParam.get(0);
        
        String questao = requisicao.getParameter("campoPergunta");
        String a1 = requisicao.getParameter("campoAlternativa1");
        String a2 = requisicao.getParameter("campoAlternativa2");
        String a3 = requisicao.getParameter("campoAlternativa3");
        String a4 = requisicao.getParameter("campoAlternativa4");
        String ra = requisicao.getParameter("respostaAlternativa");
        String rj = requisicao.getParameter("respostaJustificativa");
        String idd = requisicao.getParameter("idDisciplina");
        //System.out.println("alt: "+ra);
        cbd.inserirQuestao(questao, a1, a2, a3, a4, ra, rj,idd);        
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
