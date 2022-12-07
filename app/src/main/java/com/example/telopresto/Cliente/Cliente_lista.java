package com.example.telopresto.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.telopresto.R;
import com.example.telopresto.TI.agregar_equipo_usaurioti;
import com.example.telopresto.TI.listadoEquiposUsuario;
import com.example.telopresto.dto.Equipo;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Cliente_lista extends AppCompatActivity {

    /*

    RecyclerView recyclerView;
    listaEquiposAdapter listaEquiposAdapter;
    FirebaseDatabase firebaseDatabase;
    ArrayList<Equipo> equipos;
    SearchView searchView;

    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_lista);
        setBottomNavigationView();


        firebaseDatabase = FirebaseDatabase.getInstance();
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        equipos = new ArrayList<>();

        DatabaseReference ref1  = firebaseDatabase.getReference("usuarioTI").child("listaEquipos");

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    Equipo equipo = snapshot1.getValue(Equipo.class);
                    equipos.add(equipo);


                }
                recyclerView = findViewById(R.id.recyclerViewEquiposListado);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(Cliente_lista.this, 2));
                recyclerView.setAdapter(listaEquiposAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listaEquiposAdapter = new listaEquiposAdapter(this, equipos);


    }





    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationCliente);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.equipos_cliente);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.equipos_cliente:
                        return true;
                    case R.id.solicitudes_cliente:
                        startActivity(new Intent(Cliente_lista.this, Cliente_solicitudes.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reservas_menu:
                        startActivity(new Intent(Cliente_lista.this, Cliente_reserva.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


         */


}