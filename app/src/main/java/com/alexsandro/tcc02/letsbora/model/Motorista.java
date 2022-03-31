package com.alexsandro.tcc02.letsbora.model;

public class Motorista extends Usuario{

    private String cnh;
    private String validade;

    //construtor
    public Motorista() {
    }

    //getter and setters Begin
    public String getCnh() {
        return cnh;
    }

    public void setCnh(String cnh) {
        this.cnh = cnh;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }
    //getter and setters End
}
