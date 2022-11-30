package com.example.telopresto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class listadoAlumnoAdmin extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_alumno_admin);
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
                            startActivity(new Intent(listadoAlumnoAdmin.this,listadoUsuarioAdmin.class));
                            overridePendingTransition(0,0);
                            return true;
                        case R.id.equipo_menu:
                            startActivity(new Intent(listadoAlumnoAdmin.this,ListadoEquiposAdmin.class));
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