package com.alexsandro.tcc02.letsbora.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alexsandro.tcc02.letsbora.R;
import com.alexsandro.tcc02.letsbora.helper.UsuarioFirebase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
    }

    //método para direcionar para a tela de login
    public void abrirTelaLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    //método para direcionar para a tela de cadastro
    public void abrirTelaCadastro(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        UsuarioFirebase.redirecionaUsuarioLogado(MainActivity.this);
    }
}