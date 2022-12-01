package com.example.telopresto.TI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class agregar_equipo_usaurioti extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    EditText nombreDispositivoUsuario, caracteristicasDispositivo, incluyeDispositivo, stockDispositivo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo_usaurioti);

        firebaseDatabase = FirebaseDatabase.getInstance();
        nombreDispositivoUsuario = findViewById(R.id.nombreDispositivoUsuario);
        caracteristicasDispositivo = findViewById(R.id.cracateristicasDispositivo);
        incluyeDispositivo = findViewById(R.id.incluyeDispositivoUsario);
        stockDispositivo = findViewById(R.id.stockUsuario);

    }

    public void guardarEquipo(View view){
        // 1. se obtiene la referencia a la db
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = firebaseDatabase.getReference();
        DatabaseReference refequipos = ref.child("equipos");

        Equipo equipo = new Equipo();
        equipo.setNombreDispositivo(nombreDispositivoUsuario.getText().toString());
        equipo.setCaracteristicas(caracteristicasDispositivo.getText().toString());
        equipo.setIncluye(incluyeDispositivo.getText().toString());

    }

    public void cancelar(View view){
        Intent intent = new Intent(agregar_equipo_usaurioti.this, listadoEquiposUsuario.class);
        startActivity(intent);
    }







}