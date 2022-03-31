package com.alexsandro.tcc02.letsbora.model;

import com.alexsandro.tcc02.letsbora.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Passageiro extends Usuario{

    private String matricula;
    private String curso;

    public Passageiro() { //construtor
    }

    public void salvarPassageiro(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference usuarios = firebaseRef.child("usuarios").child(getTipo()).child(getId());

        usuarios.setValue(this);
    }

    //getter and setters Begin

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    //getter and setters End
}
