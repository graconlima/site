/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package site;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet(name = "AdicionarProvaAluno", urlPatterns = {"/AdicionarProvaAluno"})
public class AdicionarProvaAluno extends HttpServlet {

    private HttpSession sessao;
    private ConectaBD cbd = new ConectaBD();
    
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
        
        ResultSet rsA = cbd.recuperarTodosAlunos();
        
        try {
            PrintWriter escritor = resposta.getWriter();
            escritor.println("<html><head></head><body>");
            escritor.println("<form action=\"/AdicionarProvaAluno\" method=\"post\">"
                    + "Informe o cod. da disciplina: <input type=\"text\" name=\"disciplina\">"
                    + "Informe o cod. da prova: <input type=\"text\" name=\"prova\">"
                    + "<input type=\"submit\" value=\"Cadastrar provas para alunos\">");            
            escritor.println("</form></body></html>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){
                
        try {
            PrintWriter escritor = resposta.getWriter();
            escritor.println("<html><head></head><body>");
            escritor.println("<form action=\"/VerTodosAlunos\" method=\"post\">");
        //String[] v = requisicao.getParameterValues("disciplina");
        String v = requisicao.getParameter("disciplina");
        String p = requisicao.getParameter("prova");
        ResultSet rsA = null;

        if(v.equals("0") || v.equals("") || p.equals("") || p.equals("0"))
            escritor.println("Sem disciplina ou Prova");
        else
            rsA = cbd.recuperarAlunosPorDisciplina(v);

            escritor.println("<table border=\"1\">");
            int c = 0;
            while(rsA.next()){
                
                    escritor.println("<tr><td>Aluno "+c+" - "+rsA.getString("matricula")+ " - prova: "+p+"</td></tr>");                
                    cbd.inserirProvaAluno(rsA.getString("matricula"), p);
                    escritor.println("<tr><td>------------</td></tr>");
                    
                    c++;
            }                
            escritor.println("</table>");
            escritor.println("</form></body></html>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch(SQLException se){
        
            se.printStackTrace();
        }
    }
}
