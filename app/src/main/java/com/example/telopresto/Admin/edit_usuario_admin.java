package com.example.telopresto;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.telopresto.Admin.listadoAlumnoAdmin;
import com.example.telopresto.Admin.listadoUsuarioAdmin;
import com.example.telopresto.Admin.reportesAdmin;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class edit_usuario_admin extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_usuario_admin);
        setBottomNavigationView();
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
                        startActivity(new Intent(edit_usuario_admin.this, listadoUsuarioAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reportes:
                        startActivity(new Intent(edit_usuario_admin.this, reportesAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.alumno_menu:
                        startActivity(new Intent(edit_usuario_admin.this, listadoAlumnoAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
