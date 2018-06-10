package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;




public class Cliente implements Serializable  {

    
    private String nome;
   
    private String email;
    private String senha;

    private Date dataNascimento;
    private Date dataCadastro;

    public Cliente(String nome) {
        this.nome = nome;
    }

    public Cliente(String nome, String email, String senha, Date dataNascimento, Date dataCadastro) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = dataCadastro;
        

    }

    public Cliente(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

}
