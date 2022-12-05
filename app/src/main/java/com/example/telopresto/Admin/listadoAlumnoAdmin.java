package com.example.telopresto.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.telopresto.R;
import com.example.telopresto.dto.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class listadoAlumnoAdmin extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private List<Usuario> clienteList = new ArrayList<Usuario>();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alumno_admin);
        setBottomNavigationView();



        getClientes();




    }


    public void getClientes(){
        firebaseDatabase.getReference().child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot children : snapshot.getChildren()){
                    Usuario usuario = children.getValue(Usuario.class);
                    clienteList.add(usuario);
                }

                System.out.println("aqui la lista de clientes " + clienteList );


                Adapter_ListaClientes listaClientesAdapter = new Adapter_ListaClientes();
                listaClientesAdapter.setClienteLista(clienteList);
                listaClientesAdapter.setContext(listadoAlumnoAdmin.this);

                RecyclerView recyclerView = findViewById(R.id.rv_listaCliente);
                recyclerView.setAdapter(listaClientesAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(listadoAlumnoAdmin.this));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



        public void setBottomNavigationView(){
            bottomNavigationView = findViewById(R.id.bottomNavigationAdmmin);
            bottomNavigationView.clearAnimation();
            bottomNavigationView.setSelectedItemId(R.id.alumno_menu);
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.usuarios_menu:
                            startActivity(new Intent(listadoAlumnoAdmin.this, listadoUsuarioAdmin.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.reportes:
                            startActivity(new Intent(listadoAlumnoAdmin.this, reportesAdmin.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.alumno_menu:
                            return true;
                    }
                    return false;
                }
            });
        }

}