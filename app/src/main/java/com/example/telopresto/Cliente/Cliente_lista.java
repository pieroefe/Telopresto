package com.example.telopresto.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.telopresto.Admin.agregar_usuarioti_admin;
import com.example.telopresto.ListadoEquiposAdmin;
import com.example.telopresto.R;
import com.example.telopresto.listadoAlumnoAdmin;
import com.example.telopresto.listadoUsuarioAdmin;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Cliente_lista extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_lista);
        setBottomNavigationView();
    }

    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationAdmmin);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.equipo_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.usuarios_menu:
                        startActivity(new Intent(Cliente_lista.this, listadoUsuarioAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.equipo_menu:
                        startActivity(new Intent(Cliente_lista.this, ListadoEquiposAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reservas_menu:
                        startActivity(new Intent(Cliente_lista.this, listadoAlumnoAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


}