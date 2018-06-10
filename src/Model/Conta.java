package Model;

import bank.Serializar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yurif on 25/04/2018.
 */

public class Conta implements Serializable{

    private String numero;
    private Agencia agencia;
    private Banco banco;

    private Double limite;
    private Double saldo;

    private Cliente cliente;
    
    private ArrayList<Saque> saques;
   

    public Conta(String numero, Agencia agencia, Banco banco, Double limite, Double saldo, Cliente cliente) {
        this.numero = numero;
        this.agencia = agencia;
        this.banco = banco;
        this.limite = limite;
        this.saldo = saldo;
        this.cliente = cliente;
        this.saques = new ArrayList<>();
        
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Saque> getSaques() {
        return saques;
    }

    public void setSaques(Saque saque) {
        if(saques==null){
            saques = new ArrayList<>();
        }
        this.saques.add(saque);
    }

    public Boolean realizarSaque(double valor){
        if(saldo <= valor){
            return false;
        }
        Calendar data = Calendar.getInstance();
        Saque saque = new Saque(valor, data);
        System.out.println("saldo:" + getSaldo());
        
        setSaldo(getSaldo() - valor);
        System.out.println("saldo:" + getSaldo());
        
        setSaques(saque);
        
        return true;
        
        
    }
    
    
    
   
}
