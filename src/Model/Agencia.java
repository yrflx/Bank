package Model;

import java.io.Serializable;

/**
 * Created by yurif on 25/04/2018.
 */

public class Agencia implements Serializable{

    private String numero;

    public Agencia(String numero) {
        this.numero = numero;
    }

    public Agencia() {
    
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

}

