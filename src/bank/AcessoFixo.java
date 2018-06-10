
package bank;

import Model.Cliente;
import Model.Conta;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author yurif
 */
public class AcessoFixo implements Runnable{

    private Socket socket;

    private BufferedReader in;
    private PrintStream out;

    private InputStreamReader input;
    private OutputStream output;

    private boolean inicializado;
    private boolean executando;
    
    
    public AcessoFixo(Socket socket) throws Exception{
        this.socket = socket;
        this.executando = true;
          
        open();
        
    }
     
    private void open (){
        try {
            input = new InputStreamReader(socket.getInputStream());
            out = new PrintStream(socket.getOutputStream());
            in  = new BufferedReader(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void close(){
       
 
       
        if(in != null){
            try{
                input.close();
                in.close();
                out.close();

            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        if(out != null){
            try{
                out.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        try{
            socket.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        in     = null;
        out    = null;
        socket = null;


        inicializado = false;
        executando   = false;


    }

     
    @Override
    public void run() {
        try{
            while(executando){
                String mensagem = in.readLine();
                
                if(mensagem.equals("FIM")|| mensagem.equals("")  ){
                        break;
                }
                if(mensagem.equals("service")){
                    //tratamento do sistema de notificacoes do app
                    String numeroConta = in.readLine();
                  
                    String enviar = "";

                    
                    
                    Conta conta = null;
                    conta = Serializar.buscarConta(numeroConta);

                    
                    
                    enviar = "";
                    enviar = String.valueOf(conta.getSaldo());  
                    
                    out.println(enviar);    
                }
                
            }
            
        }catch(Exception e){
            close();
        }
        
        
    }
    
    
    
}
