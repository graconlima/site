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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author GD
 */
@WebServlet(name = "CadastroAluno", urlPatterns = {"/CadastroAluno"})
public class CadastroAluno extends HttpServlet {

    private ConectaBD cbd = new ConectaBD();
    String matricula = "";
    String nome = "";
    String email = "";
    String senha = "";
    //String disciplina = "";
    ResultSet rs;
    public void doGet(HttpServletRequest requisicao, HttpServletResponse resposta){
    
        try {
            PrintWriter escritor = resposta.getWriter();
            String matricula = requisicao.getParameter("matricula");
            
            if(matricula == null || matricula == ""){

                matricula = "";
                nome = "";
                email = "";
                senha = "";
                //disciplina = "";
            }else{
                ResultSet rs = cbd.recuperarAlunoPorMatricula(matricula);
                while(rs.next()){
                    //matricula = rs.getString("matricula");
                    nome = rs.getString("nome");
                    email = rs.getString("email");
                    senha = rs.getString("senha");
                    //disciplina = rs.getString("iddisciplina");
                }
            }
            
            escritor.println("<html>\n" +
                        "    <head>\n" +
                        "        <title>CADASTRO ALUNO</title>\n" +
                        "        <meta charset=\"UTF-8\">\n" +
                        "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "        <link rel=\"stylesheet\" href=\""+requisicao.getContextPath()+"/styles/estiloTelasIniciais11.css\" />"+
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <div class=\"telaCadastro\">\n" +
                        "            <div class=\"formCadastro\">\n" +
                        "               <form action=\"/CadastroAluno\" method=\"post\"/>"+
                        "                MATRICULA: <input type=\"text\" name=\"matricula\" value="+matricula+" ><br />\n" +
                        "                NOME: <input type=\"text\" name=\"nome\" value="+nome+" ><br />\n" +
                        "                EMAIL: <input type=\"text\" name=\"email\" value="+email+" ><br />\n" +
                        "                SENHA: <input type=\"password\" name=\"senha\" value="+senha+" ><br />\n");
            
                        escritor.println("DISCIPLINA: <br />");
                        escritor.println("<select name=\"idDisciplina\">");
                        rs = cbd.recuperarTodasDisciplinas();
                        while(rs.next()){
                            escritor.println("<option value="+rs.getString("idDisciplina")+">"+rs.getString("nomeDisciplina")+"</option>");
                        }
                        escritor.println("</select>"+
                                
                        "            </div>\n" +
                        "            <div class=\"mensagemCadastro\" />"+requisicao.getParameter("m")+"Forne&ccedil;a as informa&ccedil;&otilde;es acima</div>"+
                        "            <input type=\"submit\" />\n" +
                        "        </form>\n" +
                        "        </div>\n" +
                        "        <div>\n" +
                        "           <form action=/CadastroAluno method=get>\n" +
                        "               Buscar Cadastro: <input type=\"text\" name=\"matricula\" value=\"Matricula\">\n" +
                        "               <input type=\"submit\">\n" +
                        "           </form>\n" +
                        "        </div>\n" +
                        "    </body>\n" +
                        "</html>");
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CadastroAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){
    
        String matricula = requisicao.getParameter("matricula");
        String nome = requisicao.getParameter("nome");
        String email = requisicao.getParameter("email");
        String senha = requisicao.getParameter("senha");
        String idDisciplina = requisicao.getParameter("idDisciplina");
        
        try {
            PrintWriter escritor = resposta.getWriter();

            ResultSet rs = cbd.recuperarAlunoPorMatricula(matricula);
            rs.last();
            int c = rs.getRow();
            rs.beforeFirst();
            
            if(c > 0){//se tiver mais de zero linhas, ja existe e deve atualizar
                escritor.println("Matricula Existe!");

                if(cbd.atualizarAluno(matricula, nome, email, senha, idDisciplina) == 2){
                    escritor.println("ALGO DEU ERRADO...");
                    resposta.sendRedirect("/CadastroAluno?m=loginIncorretoLogin");
                }else{
                    //resposta.sendRedirect("/Login");
                    escritor.println("TDU CERTO atualizando aluno...");
                }
            }else{
                escritor.println("NAO Existe Matricula!");
                if(cbd.inserirAluno(matricula, nome, email, senha, idDisciplina) == 2){
                    escritor.println("ALGO DEU ERRADO...");
                    resposta.sendRedirect("/CadastroAluno?m=loginIncorretoLogin");
                }else{
                    //resposta.sendRedirect("/Login");
                    escritor.println("TDU CERTO cadastrando aluno...");
                }
            }
            
            //verificando se a disciplina ja consta
            ResultSet rs2 = cbd.disciplinaExiste(matricula, idDisciplina);
            rs2.last();
            int c2 = rs2.getRow();
            rs.beforeFirst();
            if(c2 > 0){//ja existe
                resposta.sendRedirect("/Login");

            }else{
                System.out.println("m: "+matricula+" dis: "+ idDisciplina);
                if(cbd.inserirDisciplinaAluno(matricula, idDisciplina) == 2){
                    escritor.println("ALGO DEU ERRADO...");
                    resposta.sendRedirect("/CadastroAluno?m=loginIncorretoLogin");

                }else{
                    escritor.println("TDU CERTO cadastrando disciplina...");
                    resposta.sendRedirect("/Login");
                }
            }
            
        } catch (IOException ex) {

            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(CadastroAluno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
