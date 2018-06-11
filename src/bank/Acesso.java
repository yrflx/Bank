package bank;

import Telas.TelaPrincipal;
import Model.Agencia;
import Model.Banco;
import Model.Cliente;
import Model.Conta;
import Model.RandomCode;
import Model.Saque;
import Model.Tranferencia;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executor;

public class Acesso implements Runnable {

    private Socket socket;

    private BufferedReader in;
    private PrintStream out;

    private InputStreamReader input;
    private OutputStream output;

    private boolean inicializado;
    private boolean executando;

    private TelaPrincipal tela;
    
    
    
    public Acesso(Socket socket, TelaPrincipal tela) throws Exception{
        this.socket = socket;
        this.executando = true;
        
        this.tela = tela;
        
        open();
       
        //ipCliente = socket.getInetAddress().getHostName();
       
      

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
        
        while(executando){
            try{
                    
                    socket.setSoTimeout(2500);
                    String mensagem = in.readLine();
                   
                     if(mensagem.equals("FIM")|| mensagem.equals("")  ){
                        close();
                        break;
                    }
                   

                    switch (mensagem){
                        
                        case "cadastro": cadastroCliente(); break;

                        case "login": loginCliente(); break;

                        case "cadastrarConta": cadastrarConta(); break;

                        case "dadosMain":  dadosMain(); break;

                        case "recebeContas": listarContas(); break;

                        case "randomCode": randomCode(); break;

                        case "receberHistoricoSaques": historicoSaques(); break;
                        
                        case "receberHistoricoTransferencia": historicoTransferencia(); break;
                      
                        case "realizarTransferencia" : realizarTransferencia(); break;
                        
                        case "removerConta" : removerConta(); break;
                        
                        case "removerCliente" : removerCliente(); break;

                        case "loginBanco" : loginBanco(); break;
                        
                        case "cadastroBanco" : cadastroBanco(); break;
                        
                        case "dadosAgencia" : dadosAgencia(); break;
                        
                        case "cadastroAgencia" : cadastroAgencia(); break;
                        
                        case "removeAgencia" : removeAgencia(); break;
                        
                        case "listaContasPorAgencia" : listaContasPorAgencia(); break;
                        
                        case "validaCode" : validaCode(); break;
                       
                        case "recebeSaldo" : recebeSaldo(); break;
                        
                        case "realizaSaque" : realizaSaque(); break;
                        
                        case "deadCode" : deadCode(); break;
                    }
                
                
            }catch(Exception e){
                
                
            }finally{
                close();
            }
            
        }
        
        
    }

    /* metodos acesso pelo app */
    
    public void cadastroCliente() throws IOException{

        String valor = in.readLine();

        String[] cad = valor.split("&&");

        String nome = cad[0];
        String telefone = cad[1];
        String nascimento = cad[2];

        String email = cad[3];
        String senha = cad[4];

        Date dataNascimento = new Date();

        dataNascimento.setDate(11);
        dataNascimento.setMonth(9);
        dataNascimento.setYear(1996);


        Cliente cli = new Cliente(nome,email,senha,
                dataNascimento, dataNascimento );

        Boolean result = Serializar.cadastrarCliente(cli);
        if(result){
            tela.printar("Novo cliente cadastrado: " + nome);
        }else{
             tela.printar("Nao foi possivel realizar o cadastro.");
        }
       
        out.println(String.valueOf(result));
        

    }

    public void loginCliente() throws IOException{

        String valor = in.readLine();
        
        String[] log = valor.split("&&");
        String email = log[0];
        String senha = log[1];

        Boolean result = Serializar.loginCliente(email, senha);
        
       if(result){
            tela.printar("Login realizado por: " + email);
        }else{
             tela.printar("Nao foi possivel realizar o Login.");
        }
        
        out.println(String.valueOf(result));

    }
 
    public void cadastrarConta() throws IOException {

        String enviar = "";
        String valor = in.readLine();

        String[] retorno = valor.split("&&");
        // 0 - numero
        // 1 - limite
        // 2 - saldo
        // 3 - banco
        // 4 - agencia
        // 5 - cliente

        
        Agencia agencia = new Agencia();
        Banco banco = new Banco();
        Double limite = Double.parseDouble(retorno[1]);
        Double saldo = Double.parseDouble(retorno[2]);

        Boolean result = false;
    
        if(Serializar.buscarBanco(retorno[3])==null){
            enviar = "Banco Nao Encontrado";
            
            
        }else if(Serializar.buscarAgencia(retorno[3],retorno[4])==null){
            enviar = "Agencia Nao Encontrada";
            
        }else{

            Cliente cli = Serializar.buscarCliente(retorno[5]);
          
            banco = Serializar.buscarBanco(retorno[3]);
            agencia = Serializar.buscarAgencia(retorno[3], retorno[4]);
            
            Conta conta = new Conta(retorno[0],agencia,banco,limite, saldo, cli );
            
            result = Serializar.cadastrarConta(conta);
            enviar = String.valueOf(result);
           
            if(result){
                tela.printar("Nova conta cadastrada: " + retorno[0] + " por " + cli.getNome());
            }
           
        }
        
        if(!result){
            tela.printar("Nao foi possivel realizar o cadastro.");
        }
        
        
 
        out.println(enviar);

    }
    
    public void listarContas() throws IOException{

        String valor = in.readLine();

        Cliente cli = Serializar.buscarCliente(valor);

        String enviar = "";


        ArrayList<Conta> contas = null;
        contas  = Serializar.listarContas();

        if(contas!=null){
            for(Conta c : contas){
                if(c.getCliente().getEmail().equals(cli.getEmail())){
                    enviar+= c.getNumero() + "&&";
                }
                
            }
            if(enviar.length() > 0 ){
                    enviar = enviar.substring (0, enviar.length() - 2);
                }
        }
        tela.printar(enviar);
        out.println(enviar);
       

    }

    public void dadosMain() throws IOException{

        String valor = in.readLine();
        System.out.println("VALOOOR:" + valor);
        String[] retorno = valor.split("&&");
        String email = "";
        String numeroConta = "";

        if(retorno.length==2){
            email = retorno[0];
            numeroConta = retorno[1];
        }else{
            return;
        }

        Cliente cliente = null;
        cliente = Serializar.buscarCliente(email);

        String enviar = "";
        enviar+= cliente.getNome() + "&&" + cliente.getEmail();
        out.println(enviar);

        Conta conta = null;
        conta = Serializar.buscarConta(numeroConta);
        
        enviar = "";
        enviar += conta.getNumero() +"&&"+ conta.getAgencia().getNumero() +"&&"+
                conta.getSaldo() +"&&"+ conta.getLimite();
        
        out.println(enviar);
        

    }
    
    public void randomCode() throws IOException{
        String valor = in.readLine();
       
        String[] retorno = valor.split("&&");
        String code = "";
        String contaNum = "";
        
         if(retorno.length==2){
            code = retorno[0];
            contaNum = retorno[1];
        }else{
            return;
        }
        
        String enviar = "";
        
        Conta conta = Serializar.buscarConta(contaNum);
        if(conta==null){
            enviar = "false";
        }else{
           RandomCode random = new RandomCode(Integer.parseInt(code), conta);
          
           if(Serializar.setCode(random)){
               enviar = "true";
               tela.printar("Codigo de acesso gerado.");
           }else{
               enviar = "false";
           }
        }
        out.println(enviar);
        
         
    }
    
    public void historicoSaques() throws IOException{
        
        String valor = in.readLine();
        
        Conta conta = Serializar.buscarConta(valor);
        
        ArrayList<Saque> saques = new ArrayList<>();
        saques  = conta.getSaques();
       
        String enviar = "";
        
        for(Saque saq : saques){
           
            
            Double val = saq.getValor();
            Calendar calendar = saq.getData();
             
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int ano = calendar.get(Calendar.YEAR);
            
            enviar += val;
            enviar +="##";
            
            enviar += ano +"/"+ mes +"/"+ dia; 
            enviar +="&&";

        }
        if(enviar.length() >0){
            enviar = enviar.substring(0, enviar.length() -2);
        }
        
        out.println(enviar);
        
        
    }
    
    public void historicoTransferencia() throws IOException{
        
        String valor = in.readLine();
       
        ArrayList<Tranferencia> transferencias = new ArrayList<>();
        transferencias = Serializar.buscarTranferencia(valor);
        String enviar = "";
        
        System.out.println("qtnd:" + transferencias.size());
        
        for(Tranferencia transf : transferencias){
            Double val = transf.getValor();
            Calendar calendar = transf.getDataTransferecia();
             
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int ano = calendar.get(Calendar.YEAR);
            
            
            enviar += val;
            enviar +="##";
            
            enviar +=transf.getEmissor().getNumero();
            enviar +="##";
            
            enviar +=transf.getReceptor().getNumero();
            enviar +="##";
            
            enviar += ano +"/"+ mes +"/"+ dia; 
            enviar +="&&";

        }
        if(enviar.length() >0){
            enviar = enviar.substring(0, enviar.length() -2);
        }
        
        out.println(enviar);
        
        
    }
    
    public void realizarTransferencia() throws IOException{
        
       String get = in.readLine();
       
       String[] quebrado = get.split("&&");
       
       String enviar = "";
       
       Conta contaEmissor = Serializar.buscarConta(quebrado[0]);
       Double valor = Double.parseDouble(quebrado[1]);
       Conta contaReceptor = Serializar.buscarConta(quebrado[2]);
      
       Boolean tranfRealizada = false;
        
       
        if(contaEmissor ==null || contaReceptor==null){
            enviar = "false";
        }else{
            
            if(contaEmissor.getSaldo()< valor){
                enviar = "false";
            }else{
                contaEmissor.setSaldo(contaEmissor.getSaldo() - valor);
                contaReceptor.setSaldo(contaReceptor.getSaldo()+valor);
                
                Serializar.editarConta(contaEmissor);
                Serializar.editarConta(contaReceptor);
                Tranferencia tranferencia = new Tranferencia(contaEmissor, contaReceptor, valor);
                tranfRealizada = Serializar.realizarTranferencia(tranferencia);
                enviar = String.valueOf(tranfRealizada);
                
            }
            
            if(tranfRealizada){
                tela.printar("Transferencia realizadada com sucesso.");
            }else{
                tela.printar("Nao foi possivel realizar a transferencia.");
            }
            out.println(enviar);
              
        }
            
    }
    
    public void removerCliente() throws IOException{

        String email = in.readLine();
        
        Boolean result = Serializar.removerCliente(email);
        if(result){
            tela.printar("Cliente removido: " + email);
        }else{
             tela.printar("Nao foi possivel remover o cliente.");
        }
       
        out.println(String.valueOf(result));
        
    }
    
    public void removerConta()  throws IOException{

        String conta = in.readLine();
        
        Boolean result = Serializar.removerConta(conta);
        if(result){
            tela.printar("Conta removida: " + conta);
        }else{
             tela.printar("Nao foi possivel remover a conta.");
        }
       
        out.println(String.valueOf(result));
        
    }
    
    /* metodos acesso genciamento banco */
    
    public void loginBanco() throws IOException {
         
        String dados = in.readLine();
        String[] quebra = dados.split("&&");
        
      
        String nome = quebra[0];
        String senha = quebra[1];
        
        
        Boolean result = Serializar.loginBanco(nome, senha);
      
        if(result){
            
            tela.printar("Login no Banco realizado");
            out.println("true");
        }else{
            tela.printar("Login no Banco n realizado");
            out.println("false");
        }
       
        
    }
    
    public void cadastroBanco() throws IOException {
       
        
        
        String dados = in.readLine();
        String[] quebra = dados.split("&&");
        
      
        String nome = quebra[0];
        String senha = quebra[1];
        
        Banco banco = new Banco(nome, senha);
        
        Boolean result = Serializar.cadastrarBanco(banco);
       
       
        if(result){
            
            tela.printar("Cadastro no Banco realizado");
            out.println("true");
        }else{
            tela.printar("Cadastro no Banco nao realizado");
            out.println("false");
        }
       
        
    }
    
    public void dadosAgencia() throws IOException{
        
        String dados = in.readLine();
        
        ArrayList<Agencia> agencias = new ArrayList();
        
        agencias = Serializar.listarAgencias(dados);
        
        String enviar = "";
        
        for(Agencia ag : agencias ){
            enviar+=ag.getNumero();
            enviar+="&&";
            
        }
        if(enviar.length() > 0 ){
            enviar = enviar.substring (0, enviar.length() - 2);
        }
        
        out.print(enviar);
        
        
    }
    
    public void cadastroAgencia() throws IOException{
          
        System.out.println("cadastroAgencia");
        
        String valor = in.readLine();
        Boolean result = false;
        
        String[] quebra = valor.split("&&");
        
        String b = quebra[0];
        String numero = quebra[1];
        
        Banco banco = new Banco();
        
        banco = Serializar.buscarBanco(quebra[0]);
        
        if(banco!=null){
          
            
            ArrayList<Agencia> agencias = new ArrayList<>();
            
            if(banco.getAgencias()!=null){
                agencias = banco.getAgencias();
            }
            Boolean existe = false;
            
            for(Agencia ag : agencias){
                
                if(numero.equals(ag.getNumero())){
                    existe = true;
                }
                
            }
            
            if(!existe){
                Agencia agencia = new Agencia(numero);
                agencias.add(agencia);
                banco.setAgencias(agencias);
                if(Serializar.editarBanco(banco)){
                    result = true;
                }
                
            }
             
        }
        System.out.println("cadastroAgencia" + String.valueOf(result));

        out.print(String.valueOf(result));
    }
    
    public void removeAgencia() throws IOException{
          
   
        String valor = in.readLine();
        Boolean result = false;
        
        String[] quebra = valor.split("&&");
        
        String b = quebra[0];
        String numero = quebra[1];
        
        Banco banco = new Banco();
        
        banco = Serializar.buscarBanco(quebra[0]);
       
        if(banco!=null){
            
           
            ArrayList<Agencia> agencias = new ArrayList<>();
            
            if(banco.getAgencias()!=null){
                agencias = banco.getAgencias();
            }
            Boolean existe = false;
            
            for(Agencia ag : agencias){
                
                if(numero.equals(ag.getNumero())){
                    
                    existe = true;
                    agencias.remove(ag);
                    banco.setAgencias(agencias);
                    break;
                }
                
            }
             
            if(existe){
               
                if(Serializar.editarBanco(banco)){
                    result = true;
                }
                
            }
             
        }
      

        out.print(String.valueOf(result));
    }

    public void listaContasPorAgencia() throws IOException{

        String valor = in.readLine();

        ArrayList<Conta> contas = null;
        contas  = Serializar.listarContas();
        
        String enviar = "";
        
        if(contas!=null){
            for(Conta c : contas){
                if(c.getAgencia().equals(valor)){
                    enviar+= c.getNumero() + "&&";
                }
                
            }
            if(enviar.length() > 0 ){
                    enviar = enviar.substring (0, enviar.length() - 2);
             }
        }
        
        out.println(enviar);
       

    }
    
    
    /* metodos acesso caixa eletronico */
    
    public void validaCode() throws IOException{

        String valor = in.readLine();
        
        String enviar = "false";
        
        Conta conta;
        
        conta = Serializar.validaCode(Integer.parseInt(valor));
        
        if(conta!=null){
            enviar = conta.getNumero();
        }
    
        out.println(enviar);
       

    }

    public void recebeSaldo() throws IOException{
        String numeroConta = in.readLine();
                  
        String enviar;
                     
        Conta conta = null;
        conta = Serializar.buscarConta(numeroConta);
            
        enviar = "";
        enviar = String.valueOf(conta.getSaldo());  
                    
        out.println(enviar);    
    }
    
    public void realizaSaque() throws IOException{
        
        String valor = in.readLine();
        
        String[] quebra = valor.split("&&");
        
        String numeroConta = quebra[0];
        String saque = quebra[1];
        
        Boolean result = false;
        
        Conta conta = Serializar.buscarConta(numeroConta);
        
        if(conta.getSaldo()>=Double.parseDouble(saque)){
            conta.setSaldo( conta.getSaldo() - Double.parseDouble(saque));
            
            Calendar data = Calendar.getInstance();
            Saque saq = new Saque(Double.parseDouble(saque),data);
            
            conta.setSaques(saq);
            
            if(Serializar.editarConta(conta));
            result = true;
        }
        
        out.print(String.valueOf(result));
    }
    
    public void deadCode() throws IOException{
        
        String valor = in.readLine();
        
        Serializar.deadCode(Integer.parseInt(valor));
        
    }

}
