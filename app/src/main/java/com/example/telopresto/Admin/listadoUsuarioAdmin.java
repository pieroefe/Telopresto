package com.example.telopresto.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

public class listadoUsuarioAdmin extends AppCompatActivity {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    Adapter_ListaUsuariosTI adapter_listaUsuariosTI;

    private List<Usuario> usuariosListar = new ArrayList<Usuario>();
    private Context context;



    BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_usuario_admin);
        setBottomNavigationView();

        getItems();



    }


    public void getItems(){
        firebaseDatabase.getReference().child("usuario").orderByChild("rol").equalTo("usuarioTI").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot children : snapshot.getChildren()){
                    Usuario usuario = children.getValue(Usuario.class);

                    System.out.println("aqui la lista de clientes " + usuariosListar );
                    usuariosListar.add(usuario);

                }




                Adapter_ListaUsuariosTI listaTIAdapter = new Adapter_ListaUsuariosTI();
                listaTIAdapter.setusuarioTIList(usuariosListar);
                listaTIAdapter.setContext(listadoUsuarioAdmin.this);

                RecyclerView recyclerView = findViewById(R.id.rv_listaCliente);
                recyclerView.setAdapter(listaTIAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(listadoUsuarioAdmin.this));

                listaTIAdapter.notifyItemRangeChanged(0, usuariosListar.size());



            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }












    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_lista_usuariosti,menu);
        return true;

    }










    public void btnMenu(MenuItem item){
        Intent intent = new Intent(this, agregar_usuarioti_admin.class);
        startActivity(intent);

    }





    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationAdmmin);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.usuarios_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.usuarios_menu:
                        return true;
//                    case R.id.reportes:
//                        startActivity(new Intent(listadoUsuarioAdmin.this, reportesAdmin.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.alumno_menu:
                        startActivity(new Intent(listadoUsuarioAdmin.this, listadoAlumnoAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void accionCerrarSesion(MenuItem item){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(listadoUsuarioAdmin.this, Login_principal.class));



    }








}