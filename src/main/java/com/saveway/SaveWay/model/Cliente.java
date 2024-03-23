package com.saveway.SaveWay.model;


import jakarta.persistence.Id;


public class Cliente {

    @Id
    private String cpf;

    public Cliente() {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
