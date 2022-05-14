package com.alexsandro.tcc02.letsbora.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alexsandro.tcc02.letsbora.R;
import com.alexsandro.tcc02.letsbora.config.ConfiguracaoFirebase;
import com.alexsandro.tcc02.letsbora.helper.Permissoes;
import com.alexsandro.tcc02.letsbora.helper.UsuarioFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private String[] permissoes = new String[] {
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Permissoes.validarPermissoes(permissoes, this,1);

        /* autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
//        autenticacao.signOut(); */
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int permissaoResultado : grantResults){
            if (permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermicao();

            }
        }
    }

    public void alertaValidacaoPermicao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}