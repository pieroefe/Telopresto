package com.example.telopresto.TI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.telopresto.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class listadoEquiposUsuario extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_equipos_usuarioti);
        setBottomNavigationView();



        Button btnAgregar = (Button) findViewById(R.id.agregar_equipo_btn);

        btnAgregar.setOnClickListener(view -> {
            Intent intent = new Intent(listadoEquiposUsuario.this, agregar_equipo_usaurioti.class);
            startActivity(intent);
        });
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
                        startActivity(new Intent(listadoEquiposUsuario.this, listaSolicitudesUsuario.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }


}