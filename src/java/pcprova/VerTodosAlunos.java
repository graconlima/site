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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "VerTodosAlunos", urlPatterns = {"/VerTodosAlunos"})
public class VerTodosAlunos extends HttpServlet {
    private HttpSession sessao;
    private ConectaBD cbd = new ConectaBD();
    
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
        
        ResultSet rsA = cbd.recuperarTodosAlunos();
        
        try {
            PrintWriter escritor = resposta.getWriter();
            escritor.println("<html><head></head><body>");
            escritor.println("<form action=\"/site/VerTodosAlunos\" method=\"post\">");

            escritor.println("<select name=\"disciplina\">");
            escritor.println("<option value=\"0\">Todos</option>");
            escritor.println("<option value=\"1\">Sistemas Distribuidos</option>");
            escritor.println("<option value=\"2\">Dispositivos Moveis</option>");
            escritor.println("</select>");
            escritor.println("<input type=\"submit\">");
            
            escritor.println("</form></body></html>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){
        
        //String[] v = requisicao.getParameterValues("disciplina");
        String v = requisicao.getParameter("disciplina");
        ResultSet rsA;

        if(v.equals("0"))
            rsA = cbd.recuperarTodosAlunos();
        else
            rsA = cbd.recuperarAlunosPorDisciplina(v);
        
        try {
            PrintWriter escritor = resposta.getWriter();
            escritor.println("<html><head></head><body>");
            escritor.println("<form action=\"/site/VerTodosAlunos\" method=\"post\">");

            escritor.println("<table border=\"1\">");
            while(rsA.next()){
                escritor.println("<tr>");
                //escritor.println("<td> Mat.: "+rsA.getString("matricula")+" Nome: "+rsA.getString("nome")+" Email: "+rsA.getString("email")+" disciplina "+rsA.getString("iddisciplina")+"</td>");
                escritor.println("<td> Mat.: "+rsA.getString("matricula")+" Nome: "+rsA.getString("nome")+" Email: "+rsA.getString("email")+"</td>");
                escritor.println("</tr>");
            }
            escritor.println("</table>");

            escritor.println("</form></body></html>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch(SQLException se){
        
            se.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
