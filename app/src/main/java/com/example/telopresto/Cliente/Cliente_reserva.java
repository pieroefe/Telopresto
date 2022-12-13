package com.example.telopresto.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.telopresto.Login_principal;
import com.example.telopresto.R;
import com.example.telopresto.dto.Solicitud;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cliente_reserva extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


    RecyclerView recyclerView;
    reservaAdapter reservaAdapter;
    FirebaseDatabase firebaseDatabase;
    ArrayList<Solicitud> solicitudes;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_reserva);
        setBottomNavigationView();
        id =  getIntent().getStringExtra("key2");

        firebaseDatabase = FirebaseDatabase.getInstance();

        solicitudes = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("solicitudes");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    Solicitud solicitud = snapshot1.getValue(Solicitud.class);
                    if(solicitud.getEstado().equals("Aceptado")){
                        solicitudes.add(solicitud);
                    }

                }
                recyclerView = findViewById(R.id.rv_reservas);
                recyclerView.setLayoutManager(new LinearLayoutManager(Cliente_reserva.this));
                recyclerView.setAdapter(reservaAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reservaAdapter = new reservaAdapter(this, solicitudes);




    }


    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationCliente);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.reservas_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.equipos_cliente:
                        Intent intent = new Intent(Cliente_reserva.this, Cliente_lista.class);
                        intent.putExtra("key",id);
                        startActivity(intent);
                        return true;
                    case R.id.solicitudes_cliente:
                        Intent intent1 = new Intent(Cliente_reserva.this, Cliente_solicitudes.class);
                        intent1.putExtra("key2",id);
                        startActivity(intent1);
                        return true;
                    case R.id.reservas_menu:
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
        startActivity(new Intent(Cliente_reserva.this, Login_principal.class));



    }


}