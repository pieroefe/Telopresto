package com.example.telopresto.TI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.telopresto.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class editar_equipo_usuarioti extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo_usuarioti);
        setBottomNavigationView();
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
                        startActivity(new Intent(editar_equipo_usuarioti.this, listadoEquiposUsuario.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.solicitudes:
                        startActivity(new Intent(editar_equipo_usuarioti.this, listaSolicitudesUsuario.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}