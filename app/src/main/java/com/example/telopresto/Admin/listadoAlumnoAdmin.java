package com.example.telopresto.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.telopresto.Login_principal;
import com.example.telopresto.R;
import com.example.telopresto.dto.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class listadoAlumnoAdmin extends AppCompatActivity {


    private List<Usuario> clienteList = new ArrayList<Usuario>();



    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    Adapter_ListaUsuariosTI adapter_listaUsuariosTI;


    Adapter_ListaClientes adapter_listaClientes;

    BottomNavigationView setBottom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alumno_admin);
        setBottom();



        getUsuarios();




    }


    public void getUsuarios(){
        firebaseDatabase.getReference().child("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("cant:"+ snapshot.getChildrenCount());
                for (DataSnapshot children : snapshot.getChildren()){
                    System.out.println("cant:"+ snapshot.getChildrenCount());
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



    public void setBottom(){


        setBottom = (BottomNavigationView) findViewById(R.id.bottomNavigationAdmminAlu);
        setBottom.setVisibility(View.VISIBLE);
        setBottom.clearAnimation();
        setBottom.setSelectedItemId(R.id.alumno_menu);
        setBottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.usuarios_menu:
                        startActivity(new Intent(listadoAlumnoAdmin.this, listadoUsuarioAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.reportes:
//                        startActivity(new Intent(listadoAlumnoAdmin.this, reportesAdmin.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.alumno_menu:
                        return true;
                }
                return false;
            }
        });
        setBottom.setSelectedItemId(R.id.alumno_menu);

    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_lista_usuarios,menu);
        return true;

    }



    public void accionCerrarSesionUsuarios(MenuItem item){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(listadoAlumnoAdmin.this, Login_principal.class));



    }






}