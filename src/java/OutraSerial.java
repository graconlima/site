/*
import gnu.io.RXTXPort;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;

public class OutraSerial {

    InputStream is;
    OutputStream os;
    
    public OutraSerial() {
        try{
            RXTXPort rt = new RXTXPort("COM4");
            rt.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            is = rt.getInputStream();
            os = rt.getOutputStream();
        }catch(Exception e){
        
            e.printStackTrace();
        }
    }
    
    public void enviar(String s){
    
        try{
            os.write(0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public String receber(){
    
        byte[] b = new byte[20];
        String s = "";
        try{
            while (is.available() > 0) {  
                is.read(b);
                s = new String(b);
                System.err.println(s);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return s;
    }
}
*/