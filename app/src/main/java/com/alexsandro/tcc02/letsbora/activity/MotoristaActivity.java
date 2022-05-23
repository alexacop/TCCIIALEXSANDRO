package com.alexsandro.tcc02.letsbora.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.alexsandro.tcc02.letsbora.adapter.ViagemAdapter;
import com.alexsandro.tcc02.letsbora.config.ConfiguracaoFirebase;
import com.alexsandro.tcc02.letsbora.model.Destino;
import com.alexsandro.tcc02.letsbora.model.Viagem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexsandro.tcc02.letsbora.R;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MotoristaActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private RecyclerView recyclerViagem; //recyclyrview
    private List<Viagem> viagens = new ArrayList<>();


    //componentes
    private TextView localDestino; //aquiii

    private GoogleMap mMap;
    private FirebaseAuth autenticacao;
    private LocationManager locationManager;
    private LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motorista);

        inicializarComponentes();

    }

    public void prepararViagens() {
        Viagem v = new Viagem("08:00", "Praça da Matriz Acopiara", "IFCE - Acopiara", R.drawable.igreja01, R.drawable.ifce, "Vila Martins S/N, Acopiara-CE");
        this.viagens.add(v);
//        v = new Viagem("12:00", "IFCE Acopiara", "Praça da Matriz Acopiara", R.drawable.ifce, R.drawable.igreja01);
//        this.viagens.add(v);
//
//        v = new Viagem("16:00", "Praça da Matriz Acopiara", "IFCE Acopiara", R.drawable.igreja01, R.drawable.ifce);
//        this.viagens.add(v);
//
//        v = new Viagem("18:00", "IFCE Acopiara", "Praça da Matriz Acopiara", R.drawable.ifce, R.drawable.igreja01);
//        this.viagens.add(v);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Recupera a localizacao do usuario
        recuperarLocalizacaoUsuario();
    }

    public void iniciarViagem(View view){
        Viagem viagem = viagens.get(0);
        String enderecoDestino = viagem.getEnderecoDestino();

        //String enderecoDestino = localDestino.toString(); //olhar essa chamadaaaaaa

        if (!enderecoDestino.equals("") || enderecoDestino != null){
            Address addressDestino = recuperarEndereco(enderecoDestino);
            if (addressDestino != null){

                Destino destino = new Destino();
                destino.setCidade(addressDestino.getSubAdminArea());
                destino.setBairro(addressDestino.getSubLocality());
                destino.setRua(addressDestino.getThoroughfare());
                destino.setNumero(addressDestino.getFeatureName());
                destino.setLatitude(String.valueOf(addressDestino.getLatitude()));
                destino.setLongitude(String.valueOf(addressDestino.getLongitude()));

                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Cidade: " + destino.getCidade() );
                mensagem.append("\nBairro: " + destino.getBairro() );
                mensagem.append("\nRua: " + destino.getRua() );
                mensagem.append("\nNúmero: "+ destino.getNumero());

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Confirme o seu endereço")
                        .setMessage(mensagem)
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int witch) {

                                //salvar dados da viagem no firebase

                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int witch) {

                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        } else {
            Toast.makeText(this,
                    "endereço invalido",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private Address recuperarEndereco(String endereco){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> listaEnderecos = geocoder.getFromLocationName(endereco, 1);
            if (listaEnderecos != null && listaEnderecos.size() > 0){
                Address address = listaEnderecos.get(0);

                return address;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void recuperarLocalizacaoUsuario() {

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LatLng meuLocal = new LatLng(latitude, longitude);

                mMap.clear();
                mMap.addMarker(
                        new MarkerOptions()
                                .position(meuLocal)
                                .title("Meu Local")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus))
                );
                mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(meuLocal, 20)
                );

            }
        };

        //Solicitar atualizacoes de localizacao
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    10000,
                    10,
                    locationListener
            );
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                autenticacao.signOut();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {

        return super.onSupportNavigateUp();
    }

    private void inicializarComponentes(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Iniciar um viagem");
        setSupportActionBar(toolbar);

        //inicializar os componentes
        localDestino = findViewById(R.id.localDestino); //aquiiiiii

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //recyclerview
        recyclerViagem = findViewById(R.id.recyclerViagem); //recyclerview

        //define layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViagem.setLayoutManager(layoutManager);

        //define adapter
        this.prepararViagens();
        ViagemAdapter adapter = new ViagemAdapter(viagens);
        recyclerViagem.setAdapter(adapter);

    }
}