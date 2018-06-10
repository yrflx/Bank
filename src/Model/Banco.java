package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by yurif on 25/04/2018.
 */

public class Banco implements Serializable{

    private String nome;


    private String senha;

    private ArrayList<Agencia> agencias;


    public Banco(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }

    public Banco() {
     
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    

    public ArrayList<Agencia> getAgencias() {
        return agencias;
    }

    public void setAgencias(ArrayList<Agencia> agencias) {
        this.agencias = agencias;
    }

    
}
