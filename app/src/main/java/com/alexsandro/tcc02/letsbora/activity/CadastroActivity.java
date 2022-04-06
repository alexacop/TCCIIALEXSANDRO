package com.alexsandro.tcc02.letsbora.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alexsandro.tcc02.letsbora.R;
import com.alexsandro.tcc02.letsbora.config.ConfiguracaoFirebase;
import com.alexsandro.tcc02.letsbora.model.Administrador;
import com.alexsandro.tcc02.letsbora.model.Motorista;
import com.alexsandro.tcc02.letsbora.model.Passageiro;
import com.alexsandro.tcc02.letsbora.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alexsandro.tcc02.letsbora.config.ConfiguracaoFirebase;
import com.alexsandro.tcc02.letsbora.model.Usuario;
import com.google.firebase.database.DatabaseReference;


public class CadastroActivity extends AppCompatActivity {

    private TextInputEditText campoNome, campoEmail, campoSenha, campoMatricula, campoCurso, campoCNH,
            campoValidade, campoCodigo, campoInstituicao;

    private RadioGroup radioGroup; //tipo usuário
    private RadioButton radioButtonPassageiro;
    private RadioButton radioButtonMotorista;
    private RadioButton radioButtonAdm;

    private LinearLayout linearLayoutPassageiro;
    private LinearLayout linearLayoutMotorista;
    private LinearLayout linearLayoutAdm;

    private Button buttonCadastrar; //

    private List<LinearLayout> listViews;
    private List<Button> buttonViews; //

    private TipoUsuario tipoUsuario;

    private FirebaseAuth autenticacao;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        loadComponents();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();

                switch (id){
                    case R.id.radioButtonPassageiro:
                        setTipoUsuarioAtual(TipoUsuario.PASSAGEIRO);
                        hideOthersViews(linearLayoutPassageiro);
                        hideButtonViews(buttonCadastrar); //
                        break;
                    case R.id.radioButtonMotorista:
                        setTipoUsuarioAtual(TipoUsuario.MOTORISTA);
                        hideOthersViews(linearLayoutMotorista);
                        hideButtonViews(buttonCadastrar); //
                        break;
                    case R.id.radioButtonAdm:
                        setTipoUsuarioAtual(TipoUsuario.ADMINISTRADOR);
                        hideOthersViews(linearLayoutAdm);
                        hideButtonViews(buttonCadastrar); //
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void setTipoUsuarioAtual(TipoUsuario tipoUsuarioAtual){
        tipoUsuario = tipoUsuarioAtual;
    }

    private TipoUsuario getTipoUsuarioAtual(){
        return this.tipoUsuario;
    }


    private void loadComponents() {
        radioGroup = findViewById(R.id.radioGroup);

        linearLayoutPassageiro = findViewById(R.id.formulariopassageiro);
        linearLayoutMotorista = findViewById(R.id.formulariomotorista);
        linearLayoutAdm = findViewById(R.id.formularioadm);

        buttonCadastrar = findViewById(R.id.buttonCadastrar); //

        listViews = new ArrayList<LinearLayout>();
        listViews.add(linearLayoutPassageiro);
        listViews.add(linearLayoutMotorista);
        listViews.add(linearLayoutAdm);

        buttonViews = new ArrayList<Button>(); //
        buttonViews.add(buttonCadastrar);//

        radioButtonPassageiro =  findViewById(R.id.radioButtonPassageiro);
        radioButtonMotorista = findViewById(R.id.radioButtonMotorista);
        radioButtonAdm = findViewById(R.id.radioButtonAdm);

        campoNome = findViewById(R.id.editCadastroNome);
        campoEmail = findViewById(R.id.editCadastroEmail);
        campoSenha = findViewById(R.id.editCadastroSenha);
        campoMatricula = findViewById(R.id.editCadastroMatricula);
        campoCurso = findViewById(R.id.editCadastroCurso);
        campoCNH = findViewById(R.id.editCadastroCNH);
        campoValidade = findViewById(R.id.editCadastroValidade);
        campoCodigo = findViewById(R.id.editCadastroCodigo);
        campoInstituicao = findViewById(R.id.editCadastroInstituicao);

    }

    private void hideOthersViews(LinearLayout linearLayout){
       Toast.makeText(this, "---", Toast.LENGTH_LONG).show();
           Toast.makeText(this, "você está se cadastrando como:" + " " + getTipoUsuarioAtual().name(), Toast.LENGTH_SHORT).show();


        for (LinearLayout l : this.listViews){
            if(l.equals(linearLayout)){
                // show
                l.setVisibility(View.VISIBLE);
            }
            else{
                // hide
                l.setVisibility(View.GONE);
            }
        }
    }

    private void hideButtonViews (Button button) { //
        for (Button b : this.buttonViews){
            if (b.equals(button)){
                b.setVisibility(View.VISIBLE);
            }
            else{
                b.setVisibility(View.GONE);
            }
        }
    }

    public void validarCadastroUsuario( View view){

        //recuperar textos dos campos
        String textoNome = campoNome.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();
        String textoMatricula = campoMatricula.getText().toString();
        String textoCurso = campoCurso.getText().toString();
        String textoCNH = campoCNH.getText().toString();
        String textoValidade = campoValidade.getText().toString();
        String textoCodigo = campoCodigo.getText().toString();
        String textoInstituicao = campoInstituicao.getText().toString();

//        if (!textoNome.isEmpty()){ //verifica nome
//            if (!textoEmail.isEmpty()){ //verifica email
//                if (!textoSenha.isEmpty()){ //verifica senha
//                    if (!textoMatricula.isEmpty() /* tipo do usuario */){ //verifica matrícula
//                    } else {
//                        Toast.makeText(CadastroActivity.this,
//                                "Preencha o nome!",
//                                Toast.LENGTH_SHORT).show();
//                    }

                   // criando o objeto usuário
                   // fazer condições para cada tipo de usuário...////
//                    Usuario usuario = new Usuario();
//                    usuario.setNome(textoNome);
//                    usuario.setEmail(textoEmail);
//                    usuario.setSenha(textoSenha);
//                    usuario.setTipo(getTipoUsuarioAtual().tipoUsuario);
//
//                    cadastrarUsuario(usuario);


                    //daquiiiiiiiiiii

                    if (getTipoUsuarioAtual().tipoUsuario == "PASSAGEIRO") {
                        Passageiro passageiro = new Passageiro();
                        passageiro.setNome(textoNome);
                        passageiro.setEmail(textoEmail);
                        passageiro.setSenha(textoSenha);
                        passageiro.setMatricula(textoMatricula);
                        passageiro.setCurso(textoCurso);
                        passageiro.setTipo(getTipoUsuarioAtual().tipoUsuario);


                        cadastrarUsuario(passageiro);
                    }


                    if (getTipoUsuarioAtual().tipoUsuario == "MOTORISTA") {
                        Motorista motorista = new Motorista();
                        motorista.setNome(textoNome);
                        motorista.setEmail(textoEmail);
                        motorista.setSenha(textoSenha);
                        motorista.setCnh(textoCNH);
                        motorista.setValidade(textoValidade);
                        motorista.setTipo(getTipoUsuarioAtual().tipoUsuario);


                        cadastrarUsuario(motorista);
                    }


                    //aquuiiiii



//                } else {
//                    Toast.makeText(CadastroActivity.this,
//                            "Preencha a senha!",
//                            Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(CadastroActivity.this,
//                        "Preencha o email!",
//                        Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(CadastroActivity.this,
//                    "Preencha o nome!",
//                    Toast.LENGTH_SHORT).show();
//        }

    }


    public void cadastrarUsuario(Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //Recupera o ID do usuário
                    String idUsuario = task.getResult().getUser().getUid();
                    usuario.setId( idUsuario );
                    usuario.salvar();


                    Usuario usuario = new Usuario();
                    Passageiro passageiro = new Passageiro();
                    Motorista motorista = new Motorista();
                    Administrador administrador = new Administrador();

                    DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
                    DatabaseReference databaseReference = firebaseRef.child("usuarios");


//                    Map<String, Usuario> usuarios = new HashMap<>();
//
//                    databaseReference.setValue(usuarios);


                }
            }
        });

    }


//    Toast.makeText(CadastroActivity.this,
//            "Sucesso ao cadastrar Usuário!",
//             Toast.LENGTH_SHORT).show();

}
