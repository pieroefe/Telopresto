package com.example.telopresto.TI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.telopresto.Cliente.Cliente_solicitudes;
import com.example.telopresto.Cliente.agregarSolicitud;
import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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

import javax.microedition.khronos.egl.EGLDisplay;

public class editar_equipo_usuarioti extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;

    FirebaseAuth firebaseAuth;

    EditText caracText, incluyeText, stocText;
    Spinner tipoSpinner, marcaSpinner;
    String id;
    ArrayList<String> listaMarcas;
    ArrayList<String> listaTipo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo_usuarioti);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref1  = firebaseDatabase.getReference("usuarioTI").child("listaEquipos");

        id =  getIntent().getStringExtra("idEquipo2");

        Log.d("msg123", id);

        tipoSpinner = findViewById(R.id.editarNombreDispositivo);
        marcaSpinner = findViewById(R.id.editarMarcaDispositivo);
        caracText = findViewById(R.id.editarCaracteristicaDispositivo);
        incluyeText = findViewById(R.id.editarIncluyeDispositivo);
        stocText = findViewById(R.id.editarStockDispositivo);

        listaTipo = new ArrayList<>();
        if(listaTipo.isEmpty()){
            listaTipo.add("Otro");
            listaTipo.add("Laptop");
            listaTipo.add("Celular");
            listaTipo.add("Tablet");
            listaTipo.add("Monitor");
        }

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String tipoString = snapshot1.child("tipo").getValue(String.class);
                        if(tipoString.equals("Laptop") || tipoString.equals("Celular")|| tipoString.equals("Tablet")||tipoString.equals("Monitor")){

                        }else{
                            listaTipo.add(tipoString);
                        }

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(editar_equipo_usuarioti.this, android.R.layout.simple_spinner_dropdown_item, listaTipo);

                    adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                    tipoSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                listaMarcas = new ArrayList<>();
                if (snapshot.exists()) {

                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String tipoString = snapshot1.child("marca").getValue(String.class);
                        listaMarcas.add(tipoString);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(editar_equipo_usuarioti.this, android.R.layout.simple_spinner_dropdown_item,listaMarcas);

                    adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                    marcaSpinner.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


    }

    public void updatear(View view){


        HashMap usuario = new HashMap();
        usuario.put("caracteristicas", caracText.getText().toString());
        usuario.put("incluye", incluyeText.getText().toString());
        usuario.put("marca", marcaSpinner.getSelectedItem().toString());
        usuario.put("stock", stocText.getText().toString());
        usuario.put("tipo",  tipoSpinner.getSelectedItem().toString());

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref1  = firebaseDatabase.getReference().child("usuarioTI").child("listaEquipos");

        ref1.child(id).updateChildren(usuario).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(editar_equipo_usuarioti.this,"Editado correctamente", Toast.LENGTH_SHORT).show();

                Intent intent2 = new Intent(editar_equipo_usuarioti.this, listadoEquiposUsuario.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
            }
        });

    }
    public void cancelar(View view){
        Intent intent = new Intent(editar_equipo_usuarioti.this, listadoEquiposUsuario.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}