package com.example.telopresto.TI;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telopresto.Admin.listadoUsuarioAdmin;
import com.example.telopresto.Cliente.Cliente_solicitudes_detalles;
import com.example.telopresto.Login_principal;
import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class listadoEquiposUsuario extends AppCompatActivity {

    RecyclerView recyclerView;
    listadoEquiposUsuarioAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    ArrayList<Equipo> listaEquipos;
    SearchView searchView;
    DatabaseReference ref1;
    FirebaseAuth mAuth;
    String id;
    FirebaseAuth firebaseAuth;

    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_equipos_usuarioti);
        setBottomNavigationView();

        id =  getIntent().getStringExtra("key");

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Button btnAgregar = (Button) findViewById(R.id.agregar_equipo_btn);
        searchView = findViewById(R.id.searchView);

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

        listaEquipos = new ArrayList<>();

        btnAgregar.setOnClickListener(view -> {
            Intent intent = new Intent(listadoEquiposUsuario.this, agregar_equipo_usaurioti.class);
            startActivity(intent);
        });




        ref1  = firebaseDatabase.getReference("usuarioTI").child("listaEquipos");

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot snapshot1: snapshot.getChildren()){

                    Equipo equipo = snapshot1.getValue(Equipo.class);
                    listaEquipos.add(equipo);


                }
                recyclerView = findViewById(R.id.recyclerEquipoListado);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(listadoEquiposUsuario.this));
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new listadoEquiposUsuarioAdapter(this,listaEquipos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ref1 != null){
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        listaEquipos = new ArrayList<>();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            listaEquipos.add(ds.getValue(Equipo.class));
                        }
                        adapter = new listadoEquiposUsuarioAdapter(listadoEquiposUsuario.this, listaEquipos);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(listadoEquiposUsuario.this, error.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });
        }
        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search (s);
                    return true;
                }
            });
        }
    }

    private void search(String str){
        ArrayList<Equipo> mylist = new ArrayList<>();
        for (Equipo object : listaEquipos){
            if(object.getTipo().toLowerCase(Locale.ROOT).contains(str.toLowerCase())){
                mylist.add(object);
            }
        }
        listadoEquiposUsuarioAdapter listadoEquiposUsuarioAdapter = new listadoEquiposUsuarioAdapter(listadoEquiposUsuario.this,mylist);
        recyclerView.setAdapter(listadoEquiposUsuarioAdapter);
    }


    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationUsuario);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.listado_equipos);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.listado_equipos:
                        return true;
                    case R.id.solicitudes:
                        Intent intent = new Intent(listadoEquiposUsuario.this, listaSolicitudesUsuario.class);
                        intent.putExtra("key2",id);
                        startActivity(intent);
                        return true;
                    case R.id.perfilUsuarioTI:
                        Intent intent1 = new Intent(listadoEquiposUsuario.this, PerfilUsuarioTI.class);
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
        Intent intent2 = new Intent(listadoEquiposUsuario.this, Login_principal.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent2);



    }
}