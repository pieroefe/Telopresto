package com.example.telopresto;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class listado_usuarios_admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_usuar_admin);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_listausuario_admin, menu);
        return true;
    }

    public void equipos(MenuItem menuItem) {
        Intent intent = new Intent(listado_usuarios_admin.this, lista_equipos_admin.class);
        startActivity(intent);
    }
    public void alumno(MenuItem menuItem) {
        Intent intent = new Intent(listado_usuarios_admin.this, listado_alumnos_admin.class);
        startActivity(intent);
    }
    public void usuarioTI(MenuItem menuItem) {
        Toast.makeText(this, "Usted se encuentra en el lista de usuarios", Toast.LENGTH_SHORT).show();
    }


}
