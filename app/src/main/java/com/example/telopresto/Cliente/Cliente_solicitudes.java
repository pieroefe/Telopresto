package com.example.telopresto.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.telopresto.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Cliente_solicitudes extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_solicitudes);
        setBottomNavigationView();
    }


    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationCliente);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.equipos_cliente);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.equipos_cliente:
                        startActivity(new Intent(Cliente_solicitudes.this, Cliente_lista.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.solicitudes_cliente:
                        return true;
                    case R.id.reservas_menu:
                        startActivity(new Intent(Cliente_solicitudes.this, Cliente_reserva.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public class ListaSolicitudes extends RecyclerView.Adapter{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}