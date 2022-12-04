package com.example.telopresto.Cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.telopresto.R;
import com.example.telopresto.dto.Solicitud;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class agregarSolicitud extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    Spinner spinner, spinner2;
    Solicitud solicitud = new Solicitud();


    EditText motivoText,tiempoText,cursoText, programasText, otroText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_solicitud_cliente);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref1  = firebaseDatabase.getReference("usuarioTI").child("listaEquipos");

        motivoText = findViewById(R.id.motivoText);
        tiempoText = findViewById(R.id.tiempoText);
        cursoText = findViewById(R.id.cursoText);
        programasText = findViewById(R.id.programasText);
        otroText = findViewById(R.id.otroText);
        spinner = findViewById(R.id.spinner_tipo);
        spinner2 = findViewById(R.id.spinner_marca);

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> listaTipo = new ArrayList<>();
                if (snapshot.exists()) {

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            String tipoString = snapshot1.child("tipo").getValue(String.class);
                            listaTipo.add(tipoString);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(agregarSolicitud.this, android.R.layout.simple_spinner_dropdown_item, listaTipo);

                        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        spinner.setAdapter(adapter);
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> listaMarcas = new ArrayList<>();
                if (snapshot.exists()) {
                        for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                            String tipoString = snapshot1.child("marca").getValue(String.class);
                            listaMarcas.add(tipoString);
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(agregarSolicitud.this, android.R.layout.simple_spinner_dropdown_item,listaMarcas);

                        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        spinner2.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });




    }



    public void guardarsoli(View view){

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("usuario").child(user.getUid());

        solicitud.setMotivo(motivoText.getText().toString());
        solicitud.setCurso(cursoText.getText().toString());
        solicitud.setTiempoDeSolicitud(tiempoText.getText().toString());
        solicitud.setProgramas(programasText.getText().toString());
        solicitud.setOtros(otroText.getText().toString());
        solicitud.setMarca(spinner2.getSelectedItem().toString());
        solicitud.setTipo(spinner.getSelectedItem().toString());
        solicitud.setEstado("Pendiente");


        databaseReference.child("solicitudes").push().setValue(solicitud).addOnSuccessListener(unused -> {
            Toast.makeText(agregarSolicitud.this, "Solicitud enviada", Toast.LENGTH_SHORT).show();
        });


        motivoText.setText("");
        tiempoText.setText("");
        cursoText.setText("");
        programasText.setText("");
        otroText.setText("");


    }

    public void cancelar(View view){
        Intent intent = new Intent(agregarSolicitud.this, Cliente_solicitudes.class);
        startActivity(intent);
    }











}