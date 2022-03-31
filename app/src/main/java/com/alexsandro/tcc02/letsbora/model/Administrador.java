package com.alexsandro.tcc02.letsbora.model;

public class Administrador extends Usuario{

    private String codigo;
    private String instituicao;

    //construtor
    public Administrador() {
    }

    //getter and setters Begin

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    //getter and setters End
}
