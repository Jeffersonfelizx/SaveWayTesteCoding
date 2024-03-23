package com.saveway.SaveWay.model;

import java.util.Date;

public class Transacao {
    private double valor;
    private Date dataHora;
    private String tipo;

    public Transacao(double valor, Date dataHora, String tipo) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
