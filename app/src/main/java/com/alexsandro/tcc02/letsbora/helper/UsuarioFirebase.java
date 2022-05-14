package com.alexsandro.tcc02.letsbora.helper;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alexsandro.tcc02.letsbora.activity.MotoristaActivity;
import com.alexsandro.tcc02.letsbora.activity.PassageiroActivity;
import com.alexsandro.tcc02.letsbora.activity.RelatoriosActivity;
import com.alexsandro.tcc02.letsbora.config.ConfiguracaoFirebase;
import com.alexsandro.tcc02.letsbora.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual() {
        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return usuario.getCurrentUser();
    }

    public static boolean atualizarNomeUsuario(String nome){

        try {

            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nome)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (!task.isSuccessful() ){
                        Log.d("Perfil", "Erro ao atualizar nome de perfil.");
                    }
                }
            });

            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    //public static void redirecionaUsuarioLogado(final Activity activity, String identificadorUsuario ){
    public static void redirecionaUsuarioLogado(final Activity activity ){

        FirebaseUser user = getUsuarioAtual();

        if (user != null){ //verifica se usuário foi autenticado para poder logar
            DatabaseReference usuarioMotorista = ConfiguracaoFirebase.getFirebaseDatabase()
                    .child("usuarios")
                    .child("MOTORISTA")
                    .child(getIdentificadorUsuario());
            usuarioMotorista.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                        Usuario usuario = dataSnapshot.getValue(Usuario.class);
                        String tipoUsuario = usuario.getTipo();


                        if (tipoUsuario.equals("MOTORISTA")) {
                            Intent i = new Intent(activity, MotoristaActivity.class);
                            activity.startActivity(i);
                        } else if(!tipoUsuario.equals("MOTORISTA")){
                            DatabaseReference usuarioPassageiro = ConfiguracaoFirebase.getFirebaseDatabase()
                                    .child("usuarios")
                                    .child("PASSAGEIRO")
                                    .child(getIdentificadorUsuario());
                            usuarioPassageiro.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    Usuario usuarioPassageiro = snapshot.getValue(Usuario.class); //aqui01
                                    String tipoUsuarioPassageiro = usuarioPassageiro.getTipo(); //aqui01

                                    if(tipoUsuarioPassageiro.equals("PASSAGEIRO")){
                                        Intent i = new Intent(activity, PassageiroActivity.class);
                                        activity.startActivity(i);
                                    } else if (!tipoUsuarioPassageiro.equals("PASSAGEIRO")){
                                        DatabaseReference usuarioADM = ConfiguracaoFirebase.getFirebaseDatabase()
                                                .child("usuarios")
                                                .child("ADMINISTRADOR") //Aqui
                                                .child(getIdentificadorUsuario());
                                        usuarioADM.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Usuario usuarioAdm = snapshot.getValue(Usuario.class); //aqui01
                                                String tipoUsuarioAdm = usuarioAdm.getTipo();

                                                if(tipoUsuarioAdm.equals("ADMINISTRADOR")){
                                                    Intent i = new Intent(activity, RelatoriosActivity.class);
                                                    activity.startActivity(i);
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });



        }

    }


    public static String getIdentificadorUsuario(){

        return getUsuarioAtual().getUid();
    }



}
