package Model;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * Created by yurif on 25/04/2018.
 */

public class Tranferencia implements Serializable{


    private Conta emissor;
    private Conta receptor;

    private double valor;

    private Calendar dataTransferecia;


    public Tranferencia(  Conta emissor, Conta receptor, double valor) {
      
        this.emissor = emissor;
        this.receptor = receptor;
        this.valor = valor;
        this.dataTransferecia = Calendar.getInstance();
    }


   

    public Conta getEmissor() {
        return emissor;
    }

    public void setEmissor(Conta emissor) {
        this.emissor = emissor;
    }

    public Conta getReceptor() {
        return receptor;
    }

    public void setReceptor(Conta receptor) {
        this.receptor = receptor;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Calendar getDataTransferecia() {
        return dataTransferecia;
    }

}
