
package pcprova;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CorrigeProva", urlPatterns = {"/CorrigeProva"})
public class CorrigeProva extends HttpServlet {

    public void doPost(HttpServletRequest requisicao, HttpServletResponse resposta){
    
        Enumeration atrib = requisicao.getAttributeNames();
        List listaAtrib = Collections.list(atrib);
        Enumeration param = requisicao.getParameterNames();
        List listaParam = Collections.list(param);
        
        
        try{
            PrintWriter escritor = resposta.getWriter();

            //nada configurado
            for(Object atributo: listaAtrib){

                escritor.println("atrib: "+atributo);
            }

            //esse eh o que traz as respoastas
            int nota = 0;
            /*for(int i = 0;i<listaParam.size();i++){

                escritor.println(requisicao.getParameter((String)listaParam.get(i)));
                if(requisicao.getParameter((String)listaParam.get(i)).equals(prova.getRespostas()[i])){
                    nota++;
                }
                
            }*/
            
//            escritor.println("Hora de entrada: "+sessao.getAttribute("HoraEntrada"));//recuperando o atributo da sessao
            escritor.println("Nota: "+nota);

        }catch(IOException e){
            System.out.println("Problema E/S. PrintWriter DoPost");
            e.printStackTrace();
        }
        
        //encerrando a sessao
        //sessao.invalidate();
    }
}
