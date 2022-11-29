package com.example.telopresto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Cliente_detalles extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalles);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_cliente,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.equipos_menu:

                //startActivity(new Intent(Cliente_lista.this, Cliente_lista.this));
                Log.d("msgOptAppBar","menu equipos");
                return true;

            case R.id.solicitudes_menu:

                Log.d("msgOptAppBar","menu soli");
                return true;


            case R.id.reservas_menu:
                Log.d("msgOptAppBar","menu reservas");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }









}