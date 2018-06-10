/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author yurif
 */
public class RandomCode implements Serializable{
    
    private int code;
    private Conta conta;

    public RandomCode(int code, Conta conta) {
        this.code = code;
        this.conta = conta;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    
    
}
