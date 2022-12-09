package com.example.telopresto.TI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.telopresto.Login_principal;
import com.example.telopresto.R;
import com.google.firebase.auth.FirebaseAuth;

public class MotivoSolicitudRechaza extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo_solicitud_rechaza);
    }

    public void rechazar(View view){
        Intent intent = new Intent(MotivoSolicitudRechaza.this, listaSolicitudesUsuario.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_lista_usuarios,menu);
        return true;

    }


    public void accionCerrarSesionUsuarios(MenuItem item){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MotivoSolicitudRechaza.this, Login_principal.class));



    }
}