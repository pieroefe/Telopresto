package com.example.telopresto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class lista_equipos_admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_equipos_admin);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    public void usuarioTI(MenuItem menuItem) {
        Intent intent = new Intent(lista_equipos_admin.this, listado_usuarios_admin.class);
        startActivity(intent);
    }
    public void alumno(MenuItem menuItem) {
        Intent intent = new Intent(lista_equipos_admin.this, listado_alumnos_admin.class);
        startActivity(intent);
    }
}