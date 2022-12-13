package com.example.telopresto.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.telopresto.Login_principal;
import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Cliente_detalles extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    BottomNavigationView bottomNavigationView;
    TextView marcaText, caracText, incluyeText, stockText, tipoText;
    ImageView imageView;
    String id, url;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalles);
        setBottomNavigationView();

        firebaseDatabase = FirebaseDatabase.getInstance();


        id =  getIntent().getStringExtra("idEquipo8");
        System.out.println(id);
        System.out.println("holaaaaaaaaaaaaaaaaaaa");
        DatabaseReference ref1  = firebaseDatabase.getReference().child("usuarioTI").child("listaEquipos");
        tipoText = findViewById(R.id.nombre_editable);
        marcaText = findViewById(R.id.tv_curso_edit);
        caracText = findViewById(R.id.tv_estado_edit);
        incluyeText = findViewById(R.id.tv_marca_edit);
        stockText = findViewById(R.id.tv_motivo_edit);
        imageView = findViewById(R.id.imageView45);


        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Equipo> listaEquipos = new ArrayList<>();
                if (snapshot.exists()) {

                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Equipo equipo = snapshot1.getValue(Equipo.class);
                        listaEquipos.add(equipo);
                    }

                    for(Equipo equipo: listaEquipos){

                        if(Objects.equals(equipo.getId(), id)){
                              tipoText.setText(equipo.getTipo());
                              marcaText.setText(equipo.getMarca());
                              caracText.setText(equipo.getCaracteristicas());
                              incluyeText.setText(equipo.getIncluye());
                              stockText.setText(String.valueOf(equipo.getStock()));
                              url = equipo.getUrl();

                              Glide.with(Cliente_detalles.this).load(url).into(imageView);

                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }

    public void solicitarEquipo(View view) {

        Intent intent = new Intent(Cliente_detalles.this, agregarSolicitud.class);
        startActivity(intent);


    }


    public void cancelar(View view){
        Intent intent = new Intent(Cliente_detalles.this, Cliente_lista.class);
        startActivity(intent);
    }


    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationCliente);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.solicitudes_cliente);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.equipos_cliente:
                        startActivity(new Intent(Cliente_detalles.this, Cliente_lista.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.solicitudes_cliente:
                        startActivity(new Intent(Cliente_detalles.this, Cliente_solicitudes.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reservas_menu:
                        startActivity(new Intent(Cliente_detalles.this, Cliente_reserva.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_lista_usuarios,menu);
        return true;

    }


    public void accionCerrarSesionUsuarios(MenuItem item){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        Intent intent2 = new Intent(Cliente_detalles.this, Login_principal.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent2);



    }







}