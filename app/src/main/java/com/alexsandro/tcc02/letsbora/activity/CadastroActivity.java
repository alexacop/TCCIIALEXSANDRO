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
import com.alexsandro.tcc02.letsbora.helper.UsuarioFirebase;
import com.alexsandro.tcc02.letsbora.model.Administrador;
import com.alexsandro.tcc02.letsbora.model.Motorista;
import com.alexsandro.tcc02.letsbora.model.Passageiro;
import com.alexsandro.tcc02.letsbora.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.UserProfileChangeRequest;
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
        radioGroup = findViewById(R.id.radioGroup);

    }

    private void hideOthersViews(LinearLayout linearLayout){
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
        Toast.makeText(this, "Você está se cadastrando como:" + " " + getTipoUsuarioAtual().name(),
                Toast.LENGTH_SHORT).show();
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

    if (getTipoUsuarioAtual().tipoUsuario == "PASSAGEIRO") {
        if (!textoMatricula.isEmpty() && !textoCurso.isEmpty() && !textoNome.isEmpty() &&
                !textoEmail.isEmpty() && !textoSenha.isEmpty()){ //verifica matrícula
            Passageiro passageiro = new Passageiro();
            passageiro.setNome(textoNome);
            passageiro.setEmail(textoEmail);
            passageiro.setSenha(textoSenha);
            passageiro.setMatricula(textoMatricula);
            passageiro.setCurso(textoCurso);
            passageiro.setTipo(getTipoUsuarioAtual().tipoUsuario);

            cadastrarUsuario(passageiro);
        } else {
            Toast.makeText(CadastroActivity.this,
                    "Preencha os dados do PASSAGEIRO!",
                    Toast.LENGTH_SHORT).show();
          }
    }

    if (getTipoUsuarioAtual().tipoUsuario == "MOTORISTA") {
        if (!textoCNH.isEmpty() && !textoValidade.isEmpty() && !textoNome.isEmpty() &&
                !textoEmail.isEmpty() && !textoSenha.isEmpty()) { //verifica matrícula
            Motorista motorista = new Motorista();
            motorista.setNome(textoNome);
            motorista.setEmail(textoEmail);
            motorista.setSenha(textoSenha);
            motorista.setCnh(textoCNH);
            motorista.setValidade(textoValidade);
            motorista.setTipo(getTipoUsuarioAtual().tipoUsuario);

            cadastrarUsuario(motorista);
        } else {
            Toast.makeText(CadastroActivity.this,
                    "Preencha os dados do MOTORISTA!",
                    Toast.LENGTH_SHORT).show();
        }
    }

        if (getTipoUsuarioAtual().tipoUsuario == "ADMINISTRADOR") {
            if (!textoInstituicao.isEmpty() && !textoCodigo.isEmpty() && !textoNome.isEmpty() &&
                    !textoEmail.isEmpty() && !textoSenha.isEmpty()) { //verifica se dados estão preenchidos
                Administrador administrador = new Administrador();
                administrador.setNome(textoNome);
                administrador.setEmail(textoEmail);
                administrador.setSenha(textoSenha);
                administrador.setCodigo(textoCodigo);
                administrador.setInstituicao(textoInstituicao);
                administrador.setTipo(getTipoUsuarioAtual().tipoUsuario);

                cadastrarUsuario(administrador);
            } else {
                Toast.makeText(CadastroActivity.this,
                        "Preencha os dados do ADMINISTRADOR!",
                        Toast.LENGTH_SHORT).show();
            }
        }
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
                    try {

                        //Recupera o ID do usuário
                        String idUsuario = task.getResult().getUser().getUid();
                        usuario.setId( idUsuario );
                        usuario.salvar();

                      //atualizar o nome no Userprofile
                        UsuarioFirebase.atualizarNomeUsuario(usuario.getNome());

                        if(getTipoUsuarioAtual().tipoUsuario == "PASSAGEIRO"){
                            startActivity(new Intent(CadastroActivity.this, PassageiroActivity.class));
                            finish();

                            Toast.makeText(CadastroActivity.this,
                                    "Sucesso ao cadastrar Passageiro!",
                                    Toast.LENGTH_SHORT).show();

                        } else if (getTipoUsuarioAtual().tipoUsuario == "MOTORISTA") {
                            startActivity(new Intent(CadastroActivity.this, MotoristaActivity.class));
                            finish();

                            Toast.makeText(CadastroActivity.this,
                                    "Sucesso ao cadastrar Motorista!",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            startActivity(new Intent(CadastroActivity.this, RelatoriosActivity.class));
                            finish();

                            Toast.makeText(CadastroActivity.this,
                                    "Sucesso ao cadastrar Administrador!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }

                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        excecao = "Digite uma senha com mais de 6 dígitos";
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        excecao = "Por favor, digite um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e) {
                        excecao = "Esta conta já foi cadastrada";
                    }catch (Exception e) {
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //TESTE
    public String verificaTipoUsuario() {
        if (radioButtonAdm.isSelected()) {
            return "ADMINISTRADOR";
        } else if (radioButtonMotorista.isSelected()){
            return "MOTORISTA";

        } else {
            return "PASSAGEIRO";
        }
    }
}

