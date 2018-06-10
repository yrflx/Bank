/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank;

import Model.Agencia;
import Model.Banco;
import Model.Cliente;
import Model.Conta;
import Model.RandomCode;
import Model.Tranferencia;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author yurif
 */
public class Serializar {
    
    static String PASTA = "C:\\bankFiles\\";
    static String CLIENTE_FILE = "clientes";
    static String BANCO_FILE = "bancos";
    static String CONTA_FILE = "contas";
    static String RANDOM_FILE = "randomCodes";
    static String TRANFERENCIA_FILE = "tranferencias";
     
    //CRUD CLIENTE
    public static Boolean cadastrarCliente(Cliente salvar)   {
        //recebe clientes
        ArrayList<Cliente> arrayList = new ArrayList();
        
        if(!(Serializar.recebe(CLIENTE_FILE)== null)){
             arrayList = (ArrayList<Cliente>) Serializar.recebe(CLIENTE_FILE);
             
             //percorre lista de clientes e verifica se ja existe
             for(Cliente salvo : arrayList ){
                if(salvo.getEmail().equals(salvar.getEmail())){
                    System.out.println("usuario ja existe");
                    return false;
                }     
            }  
        }
    
        arrayList.add(salvar);
        escreve(arrayList,CLIENTE_FILE);
        return true;       
    }
    
    public static Boolean removerCliente(String email){
        
        ArrayList<Cliente> arrayList = new ArrayList<>();
        if(!(Serializar.recebe(CLIENTE_FILE)==null)){
            arrayList = (ArrayList<Cliente>) Serializar.recebe(CLIENTE_FILE);
            for(Cliente c : arrayList){
                if(c.getEmail().equals(email)){
                    
                    removerContasCliente(email);
                    arrayList.remove(c);
                    
                    escreve(arrayList,CLIENTE_FILE);
                    return true;
                }
                
            }
        }
        return false;
    }
    
    public static Boolean editarCliente(Cliente editar){
       ArrayList<Cliente> arrayList = new ArrayList<>();
        if(!(Serializar.recebe(CLIENTE_FILE)==null)){
            arrayList = (ArrayList<Cliente>) Serializar.recebe(CLIENTE_FILE);
            for(Cliente c : arrayList){
                if(c.getEmail().equals(editar.getEmail())){
                    arrayList.remove(c);
                    arrayList.add(editar);
                    escreve(arrayList,CLIENTE_FILE);
                    return true;
                }
                
            }
        }
        return false;
    }
     
    public static Boolean loginCliente(String email,String senha){
        
        ArrayList<Cliente> arrayList = new ArrayList();
         
        if(!(Serializar.recebe(CLIENTE_FILE)== null)){
             arrayList = (ArrayList<Cliente>) Serializar.recebe(CLIENTE_FILE);
             
             for(Cliente salvo : arrayList ){
                if(salvo.getEmail().equals(email) && (salvo.getSenha().equals(senha)) ){
                    return true;
                }     
            }  
        }
        return false;
    }
    
    public static Cliente buscarCliente(String email){
                
        ArrayList<Cliente> arrayList = new ArrayList();
        if(!(Serializar.recebe(CLIENTE_FILE)==null)){
           arrayList = (ArrayList<Cliente>) Serializar.recebe(CLIENTE_FILE);
           
           for(Cliente cli : arrayList){
                if(cli.getEmail().equals(email)){
                   return cli;
               }         
           }
           
        }               
        
        return null;
    }
    
    public static ArrayList<Cliente> listarClientes(){
                
        ArrayList<Cliente> arrayList = new ArrayList();
        if(!(Serializar.recebe(CLIENTE_FILE)==null)){
           arrayList = (ArrayList<Cliente>) Serializar.recebe(CLIENTE_FILE); 
           
          
        }               
        
         return arrayList;
    }
    
   
    //CRUD BANCOS
    public static Boolean cadastrarBanco(Banco salvar){
        
        ArrayList<Banco> arrayList = new ArrayList();
        
        if(!(Serializar.recebe(BANCO_FILE)== null)){
             arrayList = (ArrayList<Banco>) Serializar.recebe(BANCO_FILE);
             
            
             for(Banco salvo : arrayList ){
                if(salvo.getNome().equals(salvar.getNome())){
                    System.out.println("banco ja existe");
                    return false;
                }     
            }  
        }
    
        arrayList.add(salvar);
        escreve(arrayList,BANCO_FILE);
        return true;       
    }
    
    public static Boolean removerBanco(String nome){
        
        ArrayList<Banco> arrayList = new ArrayList<>();
        if(!(Serializar.recebe(BANCO_FILE)==null)){
            arrayList = (ArrayList<Banco>) Serializar.recebe(BANCO_FILE);
            for(Banco b : arrayList){
                if(b.getNome().equals(nome)){
                    arrayList.remove(b);
                    escreve(arrayList,BANCO_FILE);
                    return true;
                }
                
            }
        }
        return false;
    }
    
    public static Boolean editarBanco(Banco editar){
       ArrayList<Banco> arrayList = new ArrayList<>();
        if(!(Serializar.recebe(BANCO_FILE)==null)){
            arrayList = (ArrayList<Banco>) Serializar.recebe(BANCO_FILE);
            for(Banco b : arrayList){
                if(b.getNome().equals(editar.getNome())){
                    arrayList.remove(b);
                    arrayList.add(editar);
                    escreve(arrayList,BANCO_FILE);
                    return true;
                }
                
            }
        }
        return false;
    }
   
    public static Banco buscarBanco(String email){
                
        ArrayList<Banco> arrayList = new ArrayList();
        if(!(Serializar.recebe(BANCO_FILE)==null)){
           arrayList = (ArrayList<Banco>) Serializar.recebe(BANCO_FILE);
           
           for(Banco b : arrayList){
                if(b.getNome().equals(email)){
                    
                   return b;
               }         
           }
           
        }               
        
        return null;
    } 
    
    public static Boolean loginBanco(String nome, String senha){
        
        ArrayList<Banco> arrayList = new ArrayList();
        if(!(Serializar.recebe(BANCO_FILE)==null)){
           arrayList = (ArrayList<Banco>) Serializar.recebe(BANCO_FILE);
           
           for(Banco b : arrayList){
                if(b.getNome().equals(nome) && b.getSenha().equals(senha) ){
                    
                   return true;
               }         
           }
           
        }               
        
        return false;
        
    }
        
    public static ArrayList<Banco> listarBancos(){
                
        ArrayList<Banco> arrayList = new ArrayList();
            if(!(Serializar.recebe(BANCO_FILE)==null)){
            arrayList = (ArrayList<Banco>) Serializar.recebe(BANCO_FILE); 
            }   
        
         return arrayList;
    }
    
    public static ArrayList<Agencia> listarAgencias(String banco){
        
        ArrayList<Agencia> arrayList = new ArrayList();
        Banco b = null;
        if(Serializar.buscarBanco(banco)==null){
          
          
           return arrayList; 
        }
        b = Serializar.buscarBanco(banco);
        arrayList = b.getAgencias();
        
        return arrayList;
    }
    
    public static Agencia buscarAgencia(String banco, String numero){
        
        System.out.println(banco +"//"+ numero);
        
        Agencia ag = null;
        
        ArrayList<Agencia> agencias = new ArrayList<>();
        
        try{
           Banco b = buscarBanco(banco);
           
           agencias = b.getAgencias();
           
            for(Agencia agencia : agencias){
                if(agencia.getNumero().equals(numero)){
                    ag = agencia;
                    System.out.println("ag for:" +ag.getNumero());
                    return ag;
                }
            }
           
           
       }catch(Exception e){
            System.err.println("errorrr" + e);
           return ag;
       }
        
       return ag;
    }
    
    
    //CRUD CONTAS
    public static Boolean cadastrarConta(Conta salvar){
        //recebe clientes
        ArrayList<Conta> arrayList = new ArrayList();
        
        if(!(Serializar.recebe(CONTA_FILE)== null)){
             arrayList = (ArrayList<Conta>) Serializar.recebe(CONTA_FILE);
             
             //percorre lista de contas e verifica se ja existe
             for(Conta salva : arrayList ){
                if(salva.getNumero().equals(salvar.getNumero())){
                    System.out.println("conta ja existe");
                    return false;
                }     
            }  
        }
    
        arrayList.add(salvar);
        escreve(arrayList,CONTA_FILE);
        return true;       
       
    }
    
    public static Boolean removerConta(String numero){
        
        ArrayList<Conta> arrayList = new ArrayList<>();
        if(!(Serializar.recebe(CONTA_FILE)==null)){
            arrayList = (ArrayList<Conta>) Serializar.recebe(CONTA_FILE);
            for(Conta c : arrayList){
                if(c.getNumero().equals(numero)){
                    arrayList.remove(c);
                    escreve(arrayList,CONTA_FILE);
                    return true;
                }
                
            }
        }
        return false;
    }
    
    public static Boolean removerContasCliente(String email){
        
        Boolean removeu = false;
        
        ArrayList<Conta> arrayList = new ArrayList<>();
        if(!(Serializar.recebe(CONTA_FILE)==null)){
            arrayList = (ArrayList<Conta>) Serializar.recebe(CONTA_FILE);
            for(Conta c : arrayList){
                if(c.getCliente().getEmail().equals(email)){
                    arrayList.remove(c);
                    removeu = true;
                    escreve(arrayList,CONTA_FILE);
                }
                
            }
        }
        return removeu;
    }
    
    public static Boolean editarConta(Conta editar){
       ArrayList<Conta> arrayList = new ArrayList<>();
        if(!(Serializar.recebe(CONTA_FILE)==null)){
            arrayList = (ArrayList<Conta>) Serializar.recebe(CONTA_FILE);
            for(Conta c : arrayList){
                if(c.getNumero().equals(editar.getNumero())){
                    arrayList.remove(c);
                    arrayList.add(editar);
                    escreve(arrayList,CONTA_FILE);
                    return true;
                }
                
            }
        }
        return false;
    }
   
    public static Conta buscarConta(String numero){
              
        
       
        ArrayList<Conta> arrayList = new ArrayList();
        if(!(Serializar.recebe(CONTA_FILE)==null)){
           arrayList = (ArrayList<Conta>) Serializar.recebe(CONTA_FILE);
           
           for(Conta c : arrayList){
                if(c.getNumero().equals(numero)){
                   
                    return c;
                    
               }         
           }
           
        }               
         System.out.println("RETURN NULO");
        return null;
        
    } 
   
    public static ArrayList<Conta> listarContas(){
                
        ArrayList<Conta> arrayList = new ArrayList();
        if(!(Serializar.recebe(CONTA_FILE)==null)){
           arrayList = (ArrayList<Conta>) Serializar.recebe(CONTA_FILE); 
           
           
        }               
        
        return arrayList;
    }
    
    
    //RANDOM CODES
    
    public static Conta validaCode(int code){
        
       ArrayList<RandomCode> arrayList;
       
       try{
           arrayList = (ArrayList<RandomCode>) Serializar.recebe(RANDOM_FILE);
           
           for(RandomCode c : arrayList){
               if(c.getCode() == code){
                   System.out.println("no if");
                   return c.getConta();
                   
               }
           }
           System.out.println("retorna nulo");
           return null;
           
       }catch(Exception e){
           return null;
       }
         
    }
    
    public static Boolean setCode(RandomCode randomCode){
        
       ArrayList<RandomCode> arrayList = new ArrayList<>();
       
       if(!(Serializar.recebe(RANDOM_FILE)==null)){
            arrayList = (ArrayList<RandomCode>) Serializar.recebe(RANDOM_FILE);
            for(RandomCode rc : arrayList){
               if(rc.getCode() == randomCode.getCode()){
                   
                   return false;
               }
           }
       }
       
       arrayList.add(randomCode);
       Serializar.escreve(arrayList, RANDOM_FILE);
       return true;
       
        
    }
       
    public static Boolean deadCode(int randomCode){
        
        ArrayList<RandomCode> arrayList = new ArrayList<>();
        
        if(!(Serializar.recebe(RANDOM_FILE)==null)){
            arrayList = (ArrayList<RandomCode>) Serializar.recebe(RANDOM_FILE);
            for(RandomCode rc : arrayList){
               if(rc.getCode() == randomCode){
                   arrayList.remove(rc);
                   Serializar.escreve(arrayList, RANDOM_FILE);
                   return true;
               }
           }
       }
      
       return false;
        
    }
    
    
    //TRANSFERENCIAS
    public static Boolean realizarTranferencia(Tranferencia tranferencia){
        //recebe clientes
        ArrayList<Tranferencia> arrayList = new ArrayList();
        
        if(!(Serializar.recebe(TRANFERENCIA_FILE)== null)){
             arrayList = (ArrayList<Tranferencia>) Serializar.recebe(TRANFERENCIA_FILE);
             
        }
    
        arrayList.add(tranferencia);
        escreve(arrayList,TRANFERENCIA_FILE);
        return true;       
       
    }
    
    public static ArrayList<Tranferencia> buscarTranferencia(String contaEmissor){
        System.out.println("bscTransferencia");
        ArrayList<Tranferencia> arrayListEnviar = new ArrayList();
        ArrayList<Tranferencia> arrayListTodas = new ArrayList();
        
        if(!(Serializar.recebe(TRANFERENCIA_FILE)==null)){
           arrayListTodas = (ArrayList<Tranferencia>) Serializar.recebe(TRANFERENCIA_FILE);
          
             
           for(Tranferencia t : arrayListTodas){
               
                if(t.getEmissor().getNumero().equals(contaEmissor)){
                   
                    arrayListEnviar.add(t);
                   
                }         
           }
           
        }               
        
        return arrayListEnviar;
    } 
    
    //metodos de escrita e leitura   
    public static void escreve(Object objeto, String caminho){
        
        try{
            
            FileOutputStream arquivoGrav = new FileOutputStream(PASTA + caminho + ".txt");

            ObjectOutputStream objGravar = new ObjectOutputStream(arquivoGrav);

            objGravar.writeObject(objeto);
            objGravar.flush();
            objGravar.close();
            arquivoGrav.flush();
            arquivoGrav.close();    
            
            System.out.println("Objeto gravado com sucesso!");

        }catch( Exception e ){

                e.printStackTrace( );

        }
           
        
    }
    
    public static Object recebe(String caminho) {
       
        try{
             Object obj = new Object();
            //Carrega o arquivo
             
            FileInputStream arquivoLeitura =  new FileInputStream(PASTA + caminho + ".txt");

            //Classe responsavel por recuperar os objetos do arquivo

            ObjectInputStream objLeitura = new ObjectInputStream(arquivoLeitura);

            obj = (Object) objLeitura.readObject();
            
            objLeitura.close();

            arquivoLeitura.close();
             return obj;
        }catch( Exception e ){
                e.printStackTrace( );
                return null;

        }
               
    } 
    
}
