package com.example.telopresto.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import com.example.telopresto.R;

public class agregar_usuarioti_admin extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuarioti_admin);
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
                        startActivity(new Intent(agregar_usuarioti_admin.this,listadoUsuarioAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.equipo_menu:
                        startActivity(new Intent(agregar_usuarioti_admin.this,ListadoEquiposAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.alumno_menu:
                        startActivity(new Intent(agregar_usuarioti_admin.this,listadoAlumnoAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}