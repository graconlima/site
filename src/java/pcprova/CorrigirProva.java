package pcprova;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CorrigirProva", urlPatterns = {"/CorrigirProva"})
public class CorrigirProva extends HttpServlet {

    ConectaBD cbd = new ConectaBD();
    
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
                   
        
        String prova = requisicao.getParameter("idProva");
        String matricula = requisicao.getParameter("matricula");
        
        if(prova == null || (prova.equals("idProva") || prova.equals("")) && (matricula == null || matricula.equals("matricula") || matricula.equals(""))){
            try {
                ResultSet rsPNR = cbd.recuperarTodasProvasRespondidas();
                PrintWriter escritor = resposta.getWriter();

                escritor.println("<html><head></head><body>INDIQUE A PROVA:<br /> <textarea style=\"resize:none\" name=\"provasnaorespondidas\" rows=\"7\" cols=\"70\">");

                    while(rsPNR.next()){
                        escritor.println("PROVA: "+rsPNR.getInt("idProva")+", Aluno: "+rsPNR.getString("matricula"));
                    }
                escritor.println("</textarea>");

                escritor.println("<form action=\"/site/CorrigirProva\" method=\"get\">");
                escritor.println("<input type=\"text\" name=\"idProva\" value=\"idProva\">");
                escritor.println("<input type=\"text\" name=\"matricula\" value=\"matricula\">");

                escritor.println("<input type=\"submit\">");
                escritor.println("</form></body></html>");

            } catch (SQLException ex) {
                Logger.getLogger(CorrigirProva.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CorrigirProva.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        int nota = cbd.corrigirProvaObjetivaAluno(prova, matricula);
        ResultSet rs = cbd.consultarRespostasAluno(prova, matricula);
        
        try{        
            PrintWriter escritor = resposta.getWriter();
            escritor.println("<html><head></head><body>");
            escritor.println("<form action=\"/site/CorrigirProva\" method=\"post\">");
            escritor.println("<div>Nota da prova objetiva: "+nota+"</div>");
            int cQuestoes = 0;
            while(rs.next()){
            
                String rA = rs.getString("respostaAlternativa");
                String rJ = rs.getString("respostaJustificativa");
                String idQ = rs.getString("idQuestao");
                ResultSet rsAux = cbd.recuperarQuestao(idQ);
                while(rsAux.next()){
                    String questao = rsAux.getString("descricao");
                    escritor.println("<div>Questao: "+questao
                            + "</div>");
                    escritor.println("<div>RespostaAlternativa "+rA
                            + "</div>");
                    escritor.println("<div>RespostaJustificativa "+rJ
                            + "</div>");
                }
                escritor.println("<input type=\"radio\" name=\"correcaoJustificativa"+cQuestoes+"\" value=\"true\"> Acertou!");
                escritor.println("<input type=\"radio\" name=\"correcaoJustificativa"+cQuestoes+"\" value=\"false\"> Errou!");
                escritor.println("<input type=\"hidden\" name=\"idQuestao"+cQuestoes+"\" value=\""+idQ+"\" />");
                cQuestoes++;
            }
            
            escritor.println("<input type=\"hidden\" name=\"QTDQuestoes\" value=\""+cQuestoes+"\" />");
            escritor.println("<input type=\"hidden\" name=\"idProva\" value=\""+prova+"\" />");
            escritor.println("<input type=\"hidden\" name=\"matricula\" value=\""+matricula+"\" />");
            escritor.println("<input type=\"submit\" />");
            escritor.println("</form>");
            escritor.println("</body></html>");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){
    
        int cQuestoes = Integer.parseInt(requisicao.getParameter("QTDQuestoes"));
        
        for(int i = 0; i<cQuestoes ;i++){
        
            String[] correcao = requisicao.getParameterValues("correcaoJustificativa"+i);
            String idQuestao = requisicao.getParameter("idQuestao"+i);
            String idProva = requisicao.getParameter("idProva");
            String mat = requisicao.getParameter("matricula");
            
            cbd.corrigirJustificativaAluno(correcao[0], idQuestao, idProva, mat);
        }
        
        cbd.marcarProvaRespondida(requisicao.getParameter("idProva"), requisicao.getParameter("matricula"));
    }
    
}
