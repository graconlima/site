package pcprova;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectaBD {
   
    private Connection conexao;
    private String consulta = "select * from prova";
    private PreparedStatement st;
    private ResultSet rs;
    
    public ConectaBD(){
    
        try{
            Class.forName("com.mysql.jdbc.Driver");//precisa disso sim
            //conexao = DriverManager.getConnection("jdbc:mysql://localhost/basedeprovas","root","");
            conexao = DriverManager.getConnection("jdbc:mysql://bdtreino.mysql.database.azure.com/basedeprovas","gracon","Mysql123");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /*
    public Avaliacao consultaAvaliacao(){
            
        Avaliacao av;
        int qtdPerguntas = 1;
        String perguntas[] = new String[qtdPerguntas];
        String alternativas[][] = new String[1][4];        

        consulta = "SELECT q.descricao, q.alternativa1, q.alternativa2, q.alternativa3, q.alternativa4 FROM prova p, questoesdaprova qp, questao q where p.idProva = qp.idProva and qp.idQuestao = q.idQuestao and p.idProva = 3";
        
        try{

            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
            int c = 0;
            while(rs.next()){
            
                perguntas[c] = rs.getString("descricao");
                alternativas[c][0] = rs.getString("alternativa1");
                alternativas[c][1] = rs.getString("alternativa2");
                alternativas[c][2] = rs.getString("alternativa3");
                alternativas[c][3] = rs.getString("alternativa4");
                c++;
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        av = new Avaliacao(perguntas, alternativas, perguntas, qtdPerguntas, 4);
        
        return av;
    }*/

    public boolean loginProfessor(String login, String senha){
    
        consulta = "select * from professor p where p.matricula = '"+login+"' and p.senha = '"+senha+"'";

        try{
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
            if(rs.first()){
            
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean loginAluno(String login, String senha){
    
        consulta = "select * from aluno a where a.matricula = '"+login+"' and a.senha = '"+senha+"'";

        try{
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
            if(rs.first()){
            
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public ResultSet consultaAvaliacao(String idProva){
            
        consulta = "SELECT q.idquestao, q.descricao, q.alternativa1, q.alternativa2, q.alternativa3, q.alternativa4, p.numAvaliacao, p.etapa FROM prova p, questoesdaprova qp, questao q where p.idProva = qp.idProva and qp.idQuestao = q.idQuestao and p.idProva = '"+idProva+"'";
        
        try{

            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
            e.printStackTrace();
        }
                
        return rs;
    }
    
    public void inserirProva(String numAvaliacao, String etapa, String idDisciplina, String qtdQuestoes){
    
        String inserirProva = "insert into Prova values(null,'"+numAvaliacao+"','"+etapa+"',null, '"+idDisciplina+"', '"+qtdQuestoes+"','1')";
        
        try{
        
            st = conexao.prepareStatement(inserirProva);
            st.executeUpdate();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
    }
    
    public void inserirQuestoesProva(String idProva, String[] idQuestoes){
            
        for(int i = 0; i < idQuestoes.length;i++){
            
            String inserirQuestaoProva = "insert into questoesdaprova values('"+idQuestoes[i]+"','"+idProva+"')";
            try{
            
                st = conexao.prepareStatement(inserirQuestaoProva);
                st.executeUpdate();
            }catch(Exception e){
                
                e.printStackTrace();
            }
        }
    }
    
    public ResultSet recuperarTodasQuestoes(){
    
        String consulta = "select * from questao";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public ResultSet recuperarQuestao(String idQuestao){
    
        String consulta = "select * from questao q where q.idQuestao = '"+idQuestao+"'";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public ResultSet recuperarTodasProvas(){
    
        String consulta = "select * from prova";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public ResultSet recuperarProvasAluno(String matricula){
    
        String consulta = "select * from prova p, provadoaluno pa where pa.matricula = '"+matricula+"' and pa.idProva = p.idProva";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public int corrigirProvaObjetivaAluno(String idProva, String matricula){
    
        String consultaRespostaObjetiva = "select * from respostaaluno ra where ra.idProva = '"+idProva+"' and ra.matricula = '"+matricula+"'";

        int nota = 0;
        
        try{
            st = conexao.prepareStatement(consultaRespostaObjetiva);
            rs = st.executeQuery();
            while(rs.next()){
            
                String auxIdQuestao;
                auxIdQuestao = rs.getString("idQuestao");
                String consultaRespostaOficial = "select * from respostaoficial ro where ro.idQuestao = '"+auxIdQuestao+"'";
                st = conexao.prepareStatement(consultaRespostaOficial);
                ResultSet rsAux = st.executeQuery();

                while(rsAux.next()){//deve comecar no zero pq deu erro quando nao deu o next
                    String ROAlternativa = rsAux.getString("respostaAlternativa");
                    String RAAlternativa = rs.getString("respostaAlternativa");

                    if(ROAlternativa.equals(RAAlternativa)){
                        st = conexao.prepareStatement("update respostaaluno ra set alternativacerta=true where ra.idQuestao = '"+auxIdQuestao+"' and ra.idProva = '"+idProva+"' and ra.matricula = '"+matricula+"'");
                        st.executeUpdate();
                        nota++;
                    }else{
                        st = conexao.prepareStatement("update respostaaluno ra set alternativacerta=false where ra.idQuestao = '"+auxIdQuestao+"' and ra.idProva = '"+idProva+"' and ra.matricula = '"+matricula+"'");
                        st.executeUpdate();
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return nota;
    }
    
    public ResultSet consultarRespostasAluno(String idProva, String matricula){
        String consultaRespostaAluno = "select * from respostaaluno ra where ra.idProva = '"+idProva+"' and ra.matricula = '"+matricula+"'";

        try{
        
            st = conexao.prepareStatement(consultaRespostaAluno);
            rs = st.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public ResultSet recuperarTodosAlunos(){
    
        String consulta = "select * from aluno";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
        
    public ResultSet recuperarProvasDaDisciplina(String id){
    
        String consulta = "select * from prova where iddisciplina = '"+id+"'";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public ResultSet recuperarAlunoPorMatricula(String matricula){
    
        String consulta = "select * from aluno where matricula = '"+matricula+"'";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public boolean matriculaExiste(String matricula){
    
        String consulta = "select * from aluno where matricula = '"+matricula+"'";
        boolean existe = false;
        try{
        
            st = conexao.prepareStatement(consulta);
            existe = st.execute();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return existe;
    }
    
    public int inserirDisciplinaAluno(String matricula, String idDisciplina){

        String insereDisciplinaAluno = "insert into disciplinadoaluno values('"+matricula+"', '"+idDisciplina+"')";
        int r = 0;
        try {
            st = conexao.prepareStatement(insereDisciplinaAluno);
            r = st.executeUpdate();
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        
        return r;
    }

    public ResultSet recuperarDisciplinasAluno(String matricula){
    
        String consulta = "select * from disciplina d, disciplinadoaluno da where da.matricula = '"+matricula+"' and da.iddisciplina = d.iddisciplina";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
    
    public ResultSet disciplinaExiste(String matricula, String iddisciplina){
    
        String consulta = "select * from disciplina d, disciplinadoaluno da where da.matricula = '"+matricula+"' and da.iddisciplina ='"+iddisciplina+"'";
        boolean existe = false;
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }

/*    //modo antigo
    public ResultSet recuperarAlunosPorDisciplina(String id){
    
        String consulta = "select * from aluno where iddisciplina = "+id;
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
*/
    
/*    //antigo
    public int inserirAluno(String matricula, String nome, String email, String senha, String idDisciplina){

        String insereAluno = "insert into aluno values('"+matricula+"', '"+nome+"','"+email+"','"+senha+"','"+idDisciplina+"')";
        int r = 0;
        try {
            st = conexao.prepareStatement(insereAluno);
            r = st.executeUpdate();
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        
        return r;
    }*/
    
    public int inserirAluno(String matricula, String nome, String email, String senha, String idDisciplina){

        String insereAluno = "insert into aluno values('"+matricula+"', '"+nome+"','"+email+"','"+senha+"')";
        String insereDisciplinaAluno = "insert into disciplinadoaluno values('"+matricula+"', '"+idDisciplina+"')";

        int r = 0;
        try {
            st = conexao.prepareStatement(insereAluno);
            r = st.executeUpdate();
            
            st = conexao.prepareStatement(insereDisciplinaAluno);
            r = st.executeUpdate();
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        
        return r;
    }

    //antigo
    public int atualizarAluno(String matricula, String nome, String email, String senha, String idDisciplina){

        String insereAluno = "update aluno set nome='"+nome+"', email='"+email+"', senha='"+senha+"' where matricula='"+matricula+"'";
        int r = 0;
        try {
            st = conexao.prepareStatement(insereAluno);
            r = st.executeUpdate();
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        
        return r;
    }

    public ResultSet recuperarProvasRespondidasAluno(String matricula){
    
        String consulta = "SELECT matricula, idprova FROM respostaaluno WHERE matricula = '"+matricula+"' group by idprova";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }

    public boolean respostaExiste(String matricula, String idQuestao, String idProva){
    
        consulta = "SELECT matricula, idquestao, idprova FROM respostaaluno WHERE matricula = '"+matricula+"' and idquestao = '"+idQuestao+"' and idprova = '"+idProva+"'";

        try{
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
            if(rs.first()){
            
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    public void inserirRespostaAluno(String respostaAlternativa, String respostaJustificativa, String matricula, String idQuestao, String idProva){

        if(!respostaExiste(matricula, idQuestao, idProva)){//verifica se a resposta NAO existe
            String insereResposta = "insert into respostaaluno values(null, '"+respostaAlternativa+"',null,'"+respostaJustificativa+"',null, null,'"+matricula+"','"+idQuestao+"','"+idProva+"')";
            try {
                st = conexao.prepareStatement(insereResposta);
                st.executeUpdate();
            } catch (SQLException ex) {

                ex.printStackTrace();
            }
        }
    }
    
    public ResultSet recuperarTodasProvasRespondidas(){
    
        String consulta = "SELECT matricula, idprova FROM respostaaluno group by matricula, idprova";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }

    public void corrigirTodasProvasObjetivas(){

        try{
            st = conexao.prepareStatement("select * from provadoaluno");
            rs = st.executeQuery();
            while(rs.next()){
            
                corrigirProvaObjetivaAluno(rs.getString("idProva"), rs.getString("matricula"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /*public void corrigirJustificativaAluno(String correcao, String idQuestao, String idProva, String matricula){

        try{
            st = conexao.prepareStatement("update respostaaluno ra set justificativacerta="+correcao+" where ra.idQuestao = "+idQuestao+" and ra.idProva = "+idProva+" and ra.matricula = "+matricula);
            st.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }*/

    public void corrigirJustificativaAluno(String correcao, String idQuestao, String idProva, String matricula){

        try{
            st = conexao.prepareStatement("update respostaaluno ra set justificativacerta='"+correcao+"' where ra.idQuestao = '"+idQuestao+"' and ra.idProva = '"+idProva+"' and ra.matricula = '"+matricula+"'");
            st.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void marcarProvaRespondida(String idProva, String matricula){

        try{
            
            st = conexao.prepareStatement("update provadoaluno pa set respondida=true where pa.idProva = '"+idProva+"' and pa.matricula = '"+matricula+"'");
            st.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public int acertosPorProva(String matricula, String idProva){
    
        //String consulta = "SELECT distinct matricula, idquestao, idprova FROM respostaaluno WHERE  matricula = "+matricula+" and idprova = "+idProva;
        String consulta = "SELECT distinct matricula, idquestao, idprova FROM respostaaluno WHERE  matricula = '"+matricula+"' and idprova = '"+idProva+"' and alternativacerta = true";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            rs.last();
            System.out.println("matricula: '"+matricula+"' prova: '"+idProva+"' numero linha: '"+rs.getRow()+"'");

            return rs.getRow();
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public boolean provaJaCadastrada(String matricula, String idProva){
    
        consulta = "SELECT * FROM provadoaluno WHERE matricula = '"+matricula+"' and idprova = '"+idProva+"'";

        try{
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
            if(rs.first()){
            
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }

/*    public int inserirProvaAluno(String matricula, String idProva){

        String insereAluno = "insert into provadoaluno values('"+matricula+"', '"+idProva+"','0')";
        int r = 0;
        try {
            st = conexao.prepareStatement(insereAluno);
            r = st.executeUpdate();
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        
        return r;
    }*/
    
    public void inserirProvaAluno(String matricula, String idProva){

        if(!provaJaCadastrada(matricula, idProva)){
            String insereAluno = "insert into provadoaluno values('"+matricula+"', '"+idProva+"','0')";
            int r = 0;
            try {
                st = conexao.prepareStatement(insereAluno);
                r = st.executeUpdate();
            } catch (SQLException ex) {

                ex.printStackTrace();
            }
        }
    }
    
        
    public void inserirQuestao(String pergunta, String a1, String a2, String a3, String a4, String ra, String rj, String idd){
    
        if(!perguntaJaCadastrada(pergunta)){
            String inserirPergunta = "insert into questao values(null,'"+pergunta+"','"+a1+"','"+a2+"','"+a3+"','"+a4+"',null,'"+idd+"')";
            String inserirResposta = "insert into respostaoficial values(null,'"+ra+"','"+rj+"',null, last_insert_id())";

            try{

                st = conexao.prepareStatement(inserirPergunta);
                st.executeUpdate();

                st = conexao.prepareStatement(inserirResposta);
                st.executeUpdate();

            }catch(Exception e){

                e.printStackTrace();
            }        
        }
    }

    public boolean perguntaJaCadastrada(String pergunta){
    
        consulta = "SELECT descricao FROM questao WHERE descricao = '"+pergunta+"'";

        try{
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
            if(rs.first()){
            
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return false;
    }
    
    //a corrigir
    public void deletarRegistrosDuplicados(){
        String consulta = "delete a from respostaaluno as a, respostaaluno as b where a.idQuestao = b.idQuestao and and a.matricula = b.matricula and a.idrespostaaluno < b.idrespostaaluno";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            st.executeUpdate();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
    }
    
    public ResultSet criaRanking(){
        String consulta = "SELECT distinct matricula, count(idProva) FROM respostaaluno where idrespostaaluno in (select idrespostaaluno from respostaaluno where alternativaCerta = 1) group by matricula order by count(idProva) desc limit 0,3";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
    
/*    //modo novo
    public ResultSet recuperarAlunosPorDisciplina(String idDisciplina){
    
        String consulta = "select * from aluno a, disciplinadoaluno da where da.iddisciplina = '"+idDisciplina+"'";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }*/
    //modo novo 2
    public ResultSet recuperarAlunosPorDisciplina(String idDisciplina){
    
        String consulta = "select * from aluno where matricula in (select matricula from disciplinadoaluno where iddisciplina = '"+idDisciplina+"')";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }

    public ResultSet recuperarTodasDisciplinas(){
    
        String consulta = "select * from disciplina";
        
        try{
        
            st = conexao.prepareStatement(consulta);
            rs = st.executeQuery();
            
        }catch(Exception e){
        
            e.printStackTrace();
        }
        
        return rs;
    }
}
