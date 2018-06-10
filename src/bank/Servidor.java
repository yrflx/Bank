package bank;


import Telas.TelaPrincipal;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Servidor implements Runnable {

    private ExecutorService executor;

    private ServerSocket server;
 
    private boolean inicializado;
    private boolean executando;
    
    final int PORTA = 2525;

    private TelaPrincipal tela;
    
    public Servidor(TelaPrincipal tela) {
        executor = Executors.newCachedThreadPool();
        executando=true;
        this.tela = tela; 
          
    }
   
    public void fechar() throws IOException{
        
        executando = false;
        tela.printar("Servidor desativado\n\n" );
    }

    public void run(){

        try {
            server = new ServerSocket(PORTA);
            tela.printar("Aguardando Conexoes na porta: " +PORTA );
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (executando){
            try{
                server.setSoTimeout(2500);
                
                Socket socket = server.accept(); 
                
                tela.printar("conexao estabelecida");
                
                Acesso acesso = new Acesso(socket, tela);
                
                executor.execute(acesso);
                
            }
            catch (SocketTimeoutException e){
                // ignorar
            }
            catch(Exception e){
               
                System.out.println(e+"---x");
                break;
            }

        }
        
        
        try {
            executor.shutdown();
            server.close();
        } catch (IOException ex) {
           
        }
        

    }

}
