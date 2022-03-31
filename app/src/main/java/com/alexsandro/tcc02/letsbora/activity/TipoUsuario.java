package com.alexsandro.tcc02.letsbora.activity;

public enum TipoUsuario {

    MOTORISTA("MOTORISTA"), PASSAGEIRO("PASSAGEIRO"), ADMINISTRADOR("ADMINISTRADOR");
    public String tipoUsuario;

    TipoUsuario(String tipo){
        this.tipoUsuario = tipo;
    }

}
