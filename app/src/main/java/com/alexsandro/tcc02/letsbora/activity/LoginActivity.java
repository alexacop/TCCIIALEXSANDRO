package com.alexsandro.tcc02.letsbora.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alexsandro.tcc02.letsbora.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //método para direcionar para a tela de cadastro
    public void abrirTelaCadastro(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }
}