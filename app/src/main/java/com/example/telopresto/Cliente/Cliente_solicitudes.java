package com.example.telopresto.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.telopresto.R;
import com.example.telopresto.TI.agregar_equipo_usaurioti;
import com.example.telopresto.TI.listadoEquiposUsuario;
import com.example.telopresto.dto.Equipo;
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

public class Cliente_solicitudes extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    RecyclerView recyclerView;
    listaSolicitudesAdapter listaSolicitudesAdapter;
    FirebaseDatabase firebaseDatabase;
    ArrayList<Solicitud> solicitudes;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_solicitudes);
        setBottomNavigationView();

        Button btnAgregar = (Button) findViewById(R.id.agregarSolicitudesBtn);

        btnAgregar.setOnClickListener(view -> {

            Intent intent = new Intent(Cliente_solicitudes.this, agregarSolicitud.class);
            startActivity(intent);
        });


        firebaseDatabase = FirebaseDatabase.getInstance();

        solicitudes = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("usuario").child(user.getUid());


        databaseReference.child("solicitudes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    Solicitud solicitud = snapshot1.getValue(Solicitud.class);
                    solicitudes.add(solicitud);
                }
                recyclerView = findViewById(R.id.rv_solicitudes);
                recyclerView.setLayoutManager(new LinearLayoutManager(Cliente_solicitudes.this));
                recyclerView.setAdapter(listaSolicitudesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listaSolicitudesAdapter = new listaSolicitudesAdapter(this, solicitudes);


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
                        startActivity(new Intent(Cliente_solicitudes.this, Cliente_lista.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.solicitudes_cliente:
                        return true;
                    case R.id.reservas_menu:
                        startActivity(new Intent(Cliente_solicitudes.this, Cliente_reserva.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}