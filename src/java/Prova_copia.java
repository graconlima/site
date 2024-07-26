

import pcprova.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Prova_copia", urlPatterns = {"/Prova_copia"})
public class Prova_copia extends HttpServlet {

    //exemplo teste da classe avaliacao
    /*String[] q = {"Q1","Q2"};
    String[][] al = {{"a1q1","a2q1","a3q1","a4q1"},{"a1q2","a2q2","a3q2","a4q2"}};
    String[] r = {"alternativa2","alternativa3"};
    int qtdq = 2;
    int qtda = 4;
    private Avaliacao prova = new Avaliacao(q, al, r, qtdq, qtda);*/
    private Avaliacao prova;
    private HttpSession sessao;
    private String idProva = "4";
    private String matricula;
    int contadorQuestoes = 0;
    private String descricaoProva = "Avaliacao";
    //bd
    ConectaBD cbd = new ConectaBD();
    
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
        try{
            sessao = requisicao.getSession(true);//pode usar para gravar atributos de sessao
            PrintWriter escritor = resposta.getWriter();
            matricula = requisicao.getParameter("matricula");
            ResultSet rsPA = cbd.recuperarProvasAluno(matricula);

            escritor.println("<html><head><link rel=\"stylesheet\" href=\""+requisicao.getContextPath()+"/styles/estiloProva22.css \" /></head><body>");

            escritor.println("Hora de entrada; "+sessao.getAttribute("HoraEntrada"));
            
            if(sessao.isNew()){

                sessao.setAttribute("HoraEntrada", new Date());
                escritor.println("<div class=\"telaEscolhaProva\" >");
                escritor.println("<form action=\"/Servlet1/Prova\" method=\"get\">");
                
                escritor.println("INDIQUE A PROVA:<br /> <select name=\"idProva\" size=\"3\"/><br />");
                while(rsPA.next()){
                    escritor.println("<option value=\""+rsPA.getInt("idProva")+"\">");
                    escritor.println(rsPA.getInt("idProva")+"Avaliacao "+rsPA.getInt("numAvaliacao")+"da etapa "+rsPA.getString("etapa")+" com "+rsPA.getInt("qtdQuestoes")+" questoes.");
                    escritor.println("</option>");
                }
                escritor.println("</select><br />");                
                escritor.println("<input type=\"hidden\" name=\"matricula\" value=\""+matricula+"\"/>");//quando envia nao memoriza tem de mandar como parametro
                escritor.println("<input type=\"submit\">");

                escritor.println("</form>");
                escritor.println("</div>");

            }else{

                //String[] idProvaV = requisicao.getParameterValues("idProva");
                idProva = requisicao.getParameter("idProva");
                //descricaoProva = requisicao.getParameterValues("idProva")[0];
                escritor.println("idProva: "+idProva);
                /*
                prova = cbd.consultaAvaliacao();

                escritor.println("<form action=\"/Servlet1/Prova\" method=\"post\">");
                for(int i = 0; i<prova.getQtdQuestoes();i++){

                   escritor.println("<h1>"+prova.getQuestoes()[i]+"</h1>");
                   for(int j = 0; j<prova.getQtdAlternativas();j++){

                       escritor.println("<input type=\"radio\" value=\"alternativa"+j+"\" name=\"campo"+i+"\" checked=\"false\" />"+prova.getAlternativas()[i][j]+"<br/>");
                   }
                }
                escritor.println("<input type=\"submit\">");

                escritor.println("</form>");*/
                
                ResultSet rs = cbd.consultaAvaliacao(idProva);
                escritor.println("       <div class=\"cabecalho\">\n" +
                            "            <div class=\"logo\"></div>\n" +
                            "            <div class=\"dnome\">ALUNO(A): </div>\n" +
                            "            <div class=\"nome\">"+matricula+"</div>\n" +
                            "            <div class=\"ddata\">DATA: </div>\n" +
                            "            <div class=\"data\">"+new Date()+"</div>\n" +
                            "            <div class=\"davaliacao\">AVALIACAO: </div>\n" +
                            "            <div class=\"avaliacao\">"+idProva+"</div>\n" +
                            "            <div class=\"dnota\">NOTA: </div>\n" +
                            "            <div class=\"nota\">0,0</div>\n" +
                            "            </div>");
                escritor.println("<form action=\"/Servlet1/Prova\" method=\"post\">");
                int i = 0;
                while(rs.next()){

                    //o campo hidden foi adicionado para colocar junto o id da prova que nao deve aparecer mas ser gravado junto no BD
                    //o campo id estava repetindo, vendo no fonte notava que o c1... se repetia indo para a mesma questao dai a necessidade de diferenciar cada um com o contador
                    escritor.println("        <div class=\"questao\">\n" +
                                    "            <div class=\"dpergunta\">(QUESTAO "+i+")</div>\n" +
                                    "            <div class=\"pergunta\">"+rs.getString("descricao")+"</div>\n" +
                                    "            <input type=\"hidden\" name=\"idQuestao"+i+"\" value=\""+rs.getString("idQuestao")+"\" /><br />"+
                                    "            <input type=\"radio\" id=\"c"+i+"1\" value=\"A\" name=\"questao"+i+"\"/><label for=\"c"+i+"1\"><span></span>"+rs.getString("alternativa1")+"</label><br />\n" +
                                    "            <input type=\"radio\" id=\"c"+i+"2\" value=\"B\" name=\"questao"+i+"\"/><label for=\"c"+i+"2\"><span></span>"+rs.getString("alternativa2")+"</label><br />\n" +
                                    "            <input type=\"radio\" id=\"c"+i+"3\" value=\"C\" name=\"questao"+i+"\"/><label for=\"c"+i+"3\"><span></span>"+rs.getString("alternativa3")+"</label><br />\n" +
                                    "            <input type=\"radio\" id=\"c"+i+"4\" value=\"D\" name=\"questao"+i+"\"/><label for=\"c"+i+"4\"><span></span>"+rs.getString("alternativa4")+"</label><br />\n" +
                                    "            <div class=\"djustificativa\">(JUSTIFICATIVA DA QUESTAO "+i+")</div>\n" +
                                    "            <div class=\"justificativa\"><textarea style=\"resize:none\" name=\"justificativaQuestao"+i+"\" rows=\"3\" cols=\"7\"></textArea></div>\n" +
                                    "        </div>");
/*
                    escritor.println("<h1>"+rs.getString("descricao")+"</h1>");

                    escritor.println("<input type=\"radio\" value=\"alternativa1\" name=\"campo"+i+"\" checked=\"false\" />"+rs.getString("alternativa1")+"<br/>");
                    escritor.println("<input type=\"radio\" value=\"alternativa2\" name=\"campo"+i+"\" checked=\"false\" />"+rs.getString("alternativa2")+"<br/>");
                    escritor.println("<input type=\"radio\" value=\"alternativa3\" name=\"campo"+i+"\" checked=\"false\" />"+rs.getString("alternativa3")+"<br/>");
                    escritor.println("<input type=\"radio\" value=\"alternativa4\" name=\"campo"+i+"\" checked=\"false\" />"+rs.getString("alternativa4")+"<br/>");
*/
                    i++;
                }
                //necessario mandar o numero de perguntas realizadas para gravar, vai como hidden
                escritor.println("<input type=\"hidden\" name=\"qtdQuestoes\" value=\""+i+"\" />");
                escritor.println("<input type=\"hidden\" name=\"idProva\" value=\""+idProva+"\" />");
                escritor.println("<input type=\"hidden\" name=\"matricula\" value=\""+requisicao.getParameter("matricula")+"\" />");
                escritor.println("<input type=\"submit\">");

                escritor.println("</form>");
            }
            
            escritor.println("</body></html>");

        }catch(Exception e){
            
            //encerrando a sessao
            sessao.invalidate();
            
            System.err.println("Problema E/S. PrintWriter...");
            e.printStackTrace();
        }
    }
    
    public void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){
    
        int qtdPerguntas = Integer.parseInt(requisicao.getParameter("qtdQuestoes"));
        String dadosEmail = "&dadosemail=matricula"+requisicao.getParameter("matricula")+", prova='"+requisicao.getParameter("idProva")+",'<br/><br/>";//concatena todos os campos em um parametro get
        PrintWriter escritor;
        try {
            escritor = resposta.getWriter();

            for(int i = 0; i < qtdPerguntas;i++){

                String[] respostaAlternativa = requisicao.getParameterValues("questao"+i);
                String justificativa = requisicao.getParameter("justificativaQuestao"+i);
                String idQuestao = requisicao.getParameter("idQuestao"+i);
                String idProva = requisicao.getParameter("idProva");
                String mat = requisicao.getParameter("matricula");
                cbd.inserirRespostaAluno(respostaAlternativa[0], justificativa, matricula, idQuestao, idProva);
                dadosEmail = dadosEmail.concat("questao="+(i+1)+", cod='"+idQuestao+"', <br/>alternativa='"+respostaAlternativa[0]+"', justificativa='"+justificativa+"'-------<br/>");
            }
            
            //encerrando a sessao
            sessao.invalidate();
            
            //enviar email com a correcao da parte objetiva
            resposta.sendRedirect("/Servlet1/EmailProvaAluno?idProva="+requisicao.getParameter("idProva")+"&matricula="+matricula+dadosEmail);
        } catch (IOException ex) {
            //encerrando a sessao
            sessao.invalidate();
            
            Logger.getLogger(Prova_copia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
