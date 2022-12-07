package com.example.telopresto.TI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.example.telopresto.dto.Solicitud;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Solicitudes_detalle extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    TextView cursoText, estadoText, marcaText, motivoText, otrosText, programasText,tipoText, tiempoText;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes_detalle);


        firebaseDatabase = FirebaseDatabase.getInstance();



        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("usuario").child(user.getUid());

        id =  getIntent().getStringExtra("idEquipo3");
        System.out.println(id);
        cursoText = findViewById(R.id.tv_curso_edit);
        estadoText = findViewById(R.id.tv_estado_edit);
        marcaText = findViewById(R.id.tv_marca_edit);
        motivoText = findViewById(R.id.tv_motivo_edit);
        otrosText = findViewById(R.id.tv_otros_edit);
        programasText = findViewById(R.id.tv_programas_edit);
        tipoText = findViewById(R.id.tv_tipo_edit);
        tiempoText = findViewById(R.id.tv_tiempo_edit);

        databaseReference.addValueEventListener(new ValueEventListener() {
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
                            cursoText.setText(soli.getCurso());
                            estadoText.setText(soli.getEstado());
                            otrosText.setText(soli.getOtros());
                            motivoText.setText(soli.getMotivo());
                            programasText.setText(soli.getProgramas());
                            tiempoText.setText(soli.getTiempoDeSolicitud());

                        }

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


    public void updatear2(View view){

        HashMap solicitud = new HashMap();
        solicitud.put("estado", "Aceptado");
     /*   solicitud.put("id", id);
        solicitud.put("curso",cursoText.getText().toString());
        solicitud.put("marca",marcaText.getText().toString());
        solicitud.put("motivo",motivoText.getText().toString());
        solicitud.put("otros",otrosText.getText().toString());
        solicitud.put("programas",programasText.getText().toString());
        solicitud.put("tipo",tipoText.getText().toString());
        solicitud.put("tiempoDeSolicitud", tiempoText.getText().toString());

      */

        databaseReference = FirebaseDatabase.getInstance().getReference().child("usuario").child(user.getUid());
        DatabaseReference ref1  = databaseReference;


        ref1.child(id).updateChildren(solicitud).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(Solicitudes_detalle.this,"Se ha aprobado la solicitud", Toast.LENGTH_SHORT).show();

                Intent intent2 = new Intent(Solicitudes_detalle.this, listaSolicitudesUsuario.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
            }
        });



    }
    public void rechazar(View view){
        Intent intent = new Intent(Solicitudes_detalle.this, solicitud_rechazada_usuaurioti.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}