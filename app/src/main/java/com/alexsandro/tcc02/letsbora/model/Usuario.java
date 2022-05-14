package com.alexsandro.tcc02.letsbora.model;

import com.alexsandro.tcc02.letsbora.activity.TipoUsuario;
import com.alexsandro.tcc02.letsbora.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String nome;
    private String email;
    private String tipo;
    private String senha;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuarios = firebaseRef.child("usuarios").child(getTipo()).child(getId());

        usuarios.setValue(this);
    }

    //getter and setters begin

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo(){
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    //getter and setters End


    private TipoUsuario tipoUsuario;

    public Usuario(TipoUsuario tipoUsuario){
        this.tipoUsuario = tipoUsuario;
    }
}
