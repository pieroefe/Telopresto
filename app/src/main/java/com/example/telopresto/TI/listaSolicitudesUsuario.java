package com.example.telopresto.TI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.telopresto.Admin.listadoUsuarioAdmin;
import com.example.telopresto.Cliente.Cliente_solicitudes;
import com.example.telopresto.Cliente.listaSolicitudesAdapter;
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

public class listaSolicitudesUsuario extends AppCompatActivity {


    RecyclerView recyclerView;
    listaSolicitudesUsuarioAdapter listaSolicitudesAdapter;
    FirebaseDatabase firebaseDatabase;
    ArrayList<Solicitud> solicitudes;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    BottomNavigationView bottomNavigationView;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_usuarioti);
        setBottomNavigationView();
        id = getIntent().getStringExtra("key2");


        solicitudes = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("solicitudes");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Solicitud solicitud = snapshot1.getValue(Solicitud.class);
                    solicitudes.add(solicitud);
                }
                recyclerView = findViewById(R.id.recyclerView3);
                recyclerView.setLayoutManager(new LinearLayoutManager(listaSolicitudesUsuario.this));
                recyclerView.setAdapter(listaSolicitudesAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listaSolicitudesAdapter = new listaSolicitudesUsuarioAdapter(this, solicitudes);
    }

    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationUsuario);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.solicitudes);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.listado_equipos:
                        Intent intent = new Intent(listaSolicitudesUsuario.this, listadoEquiposUsuario.class);
                        intent.putExtra("key",id);
                        startActivity(intent);
                    case R.id.solicitudes:
                        return true;

                    case R.id.perfilUsuarioTI:
                        Intent intent1 = new Intent(listaSolicitudesUsuario.this, PerfilUsuarioTI.class);
                        intent1.putExtra("key2",id);
                        startActivity(intent1);
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
        Intent intent2 = new Intent(listaSolicitudesUsuario.this, Login_principal.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent2);



    }
}