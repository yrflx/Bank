package bank;

import Telas.TelaPrincipal;
import Model.Banco;
import Model.Conta;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorNotificacoes implements Runnable {

    private ExecutorService executor;

    private ServerSocket server;
 
    private boolean inicializado;
    private boolean executando;
    public static int users;
    
 
    final int PORTA = 2626;


    private TelaPrincipal tela;
    
    public ServidorNotificacoes(TelaPrincipal tela) {
        executor = Executors.newCachedThreadPool();
        executando=true;
        this.tela = tela; 
        
    }
   
    public void fechar() throws IOException{
        
        executando = false;
        
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
                
                Socket socket = server.accept(); //bloqueia a execucao até que se tenha conexao com cliente;
                                
                AcessoFixo acesso = new AcessoFixo(socket);
                
                executor.execute(acesso);
                
            }
            catch (SocketTimeoutException e){
                // ignorar
            }
            catch(Exception e){
               // tela.printar("Error:" + e);
                System.out.println(e+"---x");
                break;
            }

        }
        
        
        try {
            executor.shutdown();
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

}
