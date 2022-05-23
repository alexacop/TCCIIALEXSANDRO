package com.alexsandro.tcc02.letsbora.model;

public class Viagem {

    private String horario;
    private String localOrigen;
    private String localDestino;
    private int imagemOrigem;
    private int imagemDestino;
    private String enderecoDestino;

    public Viagem() {
    }

    public Viagem(String horario, String localOrigen, String localDestino, int imagemOrigem, int imagemDestino, String enderecoDestino) {
        this.horario = horario;
        this.localOrigen = localOrigen;
        this.localDestino = localDestino;
        this.imagemOrigem = imagemOrigem;
        this.imagemDestino = imagemDestino;
        this.enderecoDestino = enderecoDestino;
    }

    public String getEnderecoDestino() {
        return enderecoDestino;
    }

    public void setEnderecoDestino(String enderecoDestino) {
        this.enderecoDestino = enderecoDestino;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLocalOrigen() {
        return localOrigen;
    }

    public void setLocalOrigen(String localOrigen) {
        this.localOrigen = localOrigen;
    }

    public String getLocalDestino() {
        return localDestino;
    }

    public void setLocalDestino(String localDestino) {
        this.localDestino = localDestino;
    }

    public int getImagemOrigem() {
        return imagemOrigem;
    }

    public void setImagemOrigem(int imagemOrigem) {
        this.imagemOrigem = imagemOrigem;
    }

    public int getImagemDestino() {
        return imagemDestino;
    }

    public void setImagemDestino(int imagemDestino) {
        this.imagemDestino = imagemDestino;
    }
}
