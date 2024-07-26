import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JOptionPane;

/*
import org.apache.commons.net.ftp.FTPReply;  
import org.apache.commons.net.ftp.FTPClient;  
*/

public class OutroExemploAtualizacao {
/*
    private static Dao dao = new Dao();

    public static void main(String[] args) {

        FTPClient ftp = new FTPClient();  
        try {
            ftp.connect("ip_do_servidor", 21);
            if( FTPReply.isPositiveCompletion( ftp.getReplyCode() ) ) {  
                ftp.login( "usuario", "senha" ); 
                System.out.println("Conectado!");
            } else {  
                //erro ao se conectar  
                ftp.disconnect();  
                System.out.println("Conexão recusada");  
                System.exit(1);  
            }

            ftp.cwd("listadeprecos");
            ftp.cwd("update");

            String [] dirList = ftp.listNames();

            Double[] versions = new Double[dirList.length];

            for (int i = 0; i < dirList.length; i++) {
                try {
                    versions[i] = Double.parseDouble(dirList[i]);
                } catch (NumberFormatException e) {
                    versions[i] = 0.0;
                }
            }

            Arrays.sort(versions, Collections.reverseOrder());

            double versaoAtual = dao.getVersao();

            if(versions[0] > versaoAtual){

                System.out.println("Há uma nova versão!");

                ftp.cwd(String.valueOf(versions[0]));

                String[] arquivos = ftp.listNames();

                for (int i = 0; i < arquivos.length; i++) {


                    if(arquivos[i].endsWith(".sql")){
                        OutputStream output;
                        output = new FileOutputStream("C:/pastaDaAplicacao/update/" + arquivos[i]);
                        ftp.retrieveFile(arquivos[i], output);
                        output.close();

                        TextFile arquivoSql = new TextFile(new File("C:/pastaDaAplicacao/"
                                + "update/" + arquivos[i]));

                        String texto = arquivoSql.read();
                        String[] linhas = texto.split("\n");

                        for (int j = 0; j < linhas.length; j++) {
                            dao.excutaSql(linhas[j]);
                        }

                    }

                    if(arquivos[i].endsWith(".jar")){
                        OutputStream output;
                        output = new FileOutputStream("C:/pastaDaAplicacao/update/" + arquivos[i]);
                        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                        ftp.retrieveFile(arquivos[i], output);
                        output.close();

                        InputStream in = new FileInputStream("C:/pastaDaAplicacao/update/" + arquivos[i]);
                        OutputStream out = new FileOutputStream("C:/pastaDaAplicacao/aplicacao.jar");

                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }
                        in.close();
                        out.close();

                    }            

                }

                String sql = "UPDATE PUBLIC.VERSAO SET VERSAO = '" + versions[0] + "' WHERE ID = 1; ";
                dao.excutaSql(sql);

            }

            Runtime.getRuntime().exec("java -jar C:/pastaDaAplicacao/aplciacao.jar"); 




        } catch (SocketException e) {
            try {
                Runtime.getRuntime().exec("java -jar C:/pastaDaAplicacao/aplicacao.jar");
            } catch (IOException e1) {
                e1.printStackTrace();
            } 
            e.printStackTrace();
        } catch (IOException e) {
            try {
                Runtime.getRuntime().exec("java -jar C:/pastaDaAplicacao/aplicacao.jar");
            } catch (IOException e1) {
                e1.printStackTrace();
            } 
            e.printStackTrace();
        }
    }

*/
}
