package Model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by yurif on 28/04/2018.
 */

public class Saque implements Serializable{

    private double valor;
    private Conta conta;
    private Calendar data;

    public Saque(double valor, Calendar data) {
        this.valor = valor;
        this.data = data;

    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }
    
    
    
    
    
}
