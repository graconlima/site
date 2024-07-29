/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prova;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet(name = "AreaAluno", urlPatterns = {"/AreaAluno"})
public class AreaAluno extends HttpServlet {

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
            matricula = requisicao.getParameter("matricula");
            sessao = requisicao.getSession();
            if(!sessao.getAttribute("matricula").equals(matricula)){
                escritor.println("Fazer Login...");
                resposta.sendRedirect(requisicao.getContextPath()+"/Login");
            }else{
                sessao = requisicao.getSession(true);//pode usar para gravar atributos de sessao
                escritor = resposta.getWriter();
                matricula = requisicao.getParameter("matricula");
                ResultSet rsPA = cbd.recuperarProvasAluno(matricula);
                
                escritor.println("<html><head><link rel=\"stylesheet\" href=\""+requisicao.getContextPath()+"/styles/estiloTelasIniciais11.css \" /></head><body>");

                sessao.setAttribute("HoraEntrada", new Date());
                escritor.println("<div class=\"telaEscolhaProva\" >");
                escritor.println("<form action=\""+requisicao.getContextPath()+"/Prova\" method=\"get\">");

                escritor.println("INDIQUE A PROVA:<br /> <select name=\"idProva\" size=\"3\"/><br />");
                while(rsPA.next()){
                    escritor.println("<option value=\""+rsPA.getInt("idProva")+"\">");
                    escritor.println(rsPA.getInt("idProva")+" - AVALIACAO "+rsPA.getInt("numAvaliacao")+" DA AULA "+rsPA.getString("etapa")+" COM "+rsPA.getInt("qtdQuestoes")+" QUESTOES.");
                    escritor.println("</option>");
                }
                escritor.println("</select><br />");                
                escritor.println("<input type=\"hidden\" name=\"matricula\" value=\""+matricula+"\"/>");//quando envia nao memoriza tem de mandar como parametro
                escritor.println("<input type=\"submit\">");
                escritor.println("</form>");
                escritor.println("</div>");

                //escritor.println("<form action=\"/AreaAluno?matricula="+matricula+"&gravar=false \" method=\"post\"><input type=\"submit\" value=\"ATUALIZAR MEU CADASTRO...\"></form>");
                
                escritor.println("<div class=\"atualizar\">Hora de entrada; "+sessao.getAttribute("HoraEntrada"));
                escritor.println("<form action="+requisicao.getContextPath()+"/CadastroAluno method=\"get\">"
                        
                        + "<input type=\"hidden\" name=matricula value="+matricula+">"
                        + "<input type=\"submit\" value=\"ATUALIZAR MEU CADASTRO...\"></form></div>");
                escritor.println("<div class=\"acertos\">");

                //provas respondidas
                escritor.println("<h4>Provas respondidas</h4>");
                ResultSet rsPR = cbd.recuperarProvasRespondidasAluno(matricula);

                escritor.println("<table border=\"1\">");
                escritor.println("<tr><th>MATRICULA</th><th>PROVA</th><th>ACERTOS NA PROVA OBJETIVA</th></tr>");

                while(rsPR.next()){
                    escritor.println("<td>"+rsPR.getString("matricula")+"</td>");
                    escritor.println("<td>"+rsPR.getString("idProva")+"</td>");
                    escritor.println("<td>"+cbd.acertosPorProva(rsPR.getString("matricula"), rsPR.getString("idProva"))+"</td>");
                    escritor.println("</tr>");
                }
                escritor.println("</table></div>");
                
                //Ranking
                escritor.println("<div class=\"ranking\">");
                escritor.println("<h4>Ranking</h4>");
                ResultSet rsRanking = cbd.criaRanking();

                escritor.println("<table class=\"ranking\"border=\"0\">");
                escritor.println("<tr><th align=\"centre\">MATRICULA</th><th align=\"centre\">ACERTOS</th></tr>");

                while(rsRanking.next()){
                    escritor.println("<td align=\"centre\">"+rsRanking.getString("matricula")+"</td>");
                    escritor.println("<td align=\"centre\">"+rsRanking.getString("count(idProva)")+"</td>");
                    //escritor.println("<td>-</td>");
                    escritor.println("</tr>");
                }
                escritor.println("</table></div>");
                
                escritor.println("</table></div></body></html>");
            }
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AreaAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){

    }
}
