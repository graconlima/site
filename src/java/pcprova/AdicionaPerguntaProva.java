/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcprova;

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
@WebServlet(name = "AdicionaPerguntaProva", urlPatterns = {"/AdicionaPerguntaProva"})
public class AdicionaPerguntaProva extends HttpServlet {

    private HttpSession sessao;
    private ConectaBD cbd = new ConectaBD();
    
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
        sessao = requisicao.getSession(true);//pode usar para gravar atributos de sessao
        if(sessao.isNew()){
        
            sessao.setAttribute("HoraEntrada", new Date());
        }
        
        ResultSet rsQ = cbd.recuperarTodasQuestoes();
        ResultSet rsP = cbd.recuperarTodasProvas();
        
        try {
            PrintWriter escritor = resposta.getWriter();
            escritor.println("<html><head></head><body>");
            escritor.println("Hora de entrada; "+sessao.getAttribute("HoraEntrada"));
            escritor.println("<form action=\"/AdicionaPerguntaProva\" method=\"post\">");

            escritor.println("INDIQUE A PROVA:<br /> <select name=\"idProva\" size=\"3\"/><br />");
            while(rsP.next()){
                escritor.println("<option value=\""+rsP.getInt("idProva")+"\">");
                escritor.println(rsP.getInt("idProva")+"Avaliacao "+rsP.getInt("numAvaliacao")+"da etapa "+rsP.getString("etapa")+" com "+rsP.getInt("qtdQuestoes")+" questoes.");
                escritor.println("</option>");
            }
            escritor.println("</select><br />");
            
            escritor.println("SELECIONE AS QUESTOES DA PROVA<br />");
            
            escritor.println("<select name=\"idQuestoes\" size=\"10\" multiple>");
            while(rsQ.next()){
                escritor.println("<option value=\""+rsQ.getInt("idQuestao")+"\">");
                escritor.println(rsQ.getString("descricao"));
                escritor.println("</option>");
            }
            escritor.println("</select><br />");
            
            escritor.println("<input type=\"submit\" />");
            
            escritor.println("</form></body></html>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch(SQLException se){
        
            se.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){
        
        String idProva = requisicao.getParameter("idProva");
        String[] idQuestoes = requisicao.getParameterValues("idQuestoes");
        
        try {
            PrintWriter escritor = resposta.getWriter();
            for(int i = 0; i< idQuestoes.length;i++){
            
                escritor.println("Questao "+i+": "+idQuestoes[i]);
            }
        } catch (IOException ex) {

            ex.printStackTrace();
        }
        
        cbd.inserirQuestoesProva(idProva, idQuestoes);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}
