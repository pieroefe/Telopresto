package com.example.telopresto.TI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.example.telopresto.dto.Solicitud;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Solicitudes_detalle extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView cursoText, estadoText, marcaText, motivoText, otrosText, programasText,tipoText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_detalle);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("usuario").child("T60iOl8eXTSX7bVT0S4S3k0ueA73");

        String id =  getIntent().getStringExtra("idEquipo");
        cursoText = findViewById(R.id.tv_curso_edit);
        estadoText = findViewById(R.id.tv_estado_edit);
        marcaText = findViewById(R.id.tv_marca_edit);
        motivoText = findViewById(R.id.tv_motivo_edit);
        otrosText = findViewById(R.id.tv_otros_edit);
        programasText = findViewById(R.id.tv_programas_edit);
        tipoText = findViewById(R.id.tv_tipo_edit);

        databaseReference.child("solicitudes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Solicitud> listaSolicitudes = new ArrayList<>();
                if (snapshot.exists()) {

                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Solicitud solicitud = snapshot1.getValue(Solicitud.class);
                        listaSolicitudes.add(solicitud);
                    }

                    for(Solicitud soli: listaSolicitudes){

                        if(Objects.equals(soli.getId(), id)){
                            tipoText.setText(soli.getTipo());
                            marcaText.setText(soli.getMarca());
                         /*   cursoText.setText(soli.getCaracteristicas());
                            estadoText.setText(soli.getIncluye());
                            otrosText.setText(String.valueOf(soli.getStock()));
                            otrosText.setText(String.valueOf(soli.getStock()));

                          */

                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }
}