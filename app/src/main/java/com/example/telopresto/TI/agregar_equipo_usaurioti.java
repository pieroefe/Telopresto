package com.example.telopresto.TI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class agregar_equipo_usaurioti extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    EditText opcionOtro, caracteristicasDispositivo, incluyeDispositivo, stockDispositivo, marcaDispositivo;
    ArrayList<String> listaMarcas = new ArrayList<>();
    ArrayList<String> listaId = new ArrayList<>();


    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo_usaurioti);

        firebaseDatabase = FirebaseDatabase.getInstance();


        opcionOtro = findViewById(R.id.opcionOtro);
        caracteristicasDispositivo = findViewById(R.id.cracateristicasDispositivo);
        incluyeDispositivo = findViewById(R.id.incluyeDispositivoUsario);
        stockDispositivo = findViewById(R.id.stockUsuario);
        marcaDispositivo = findViewById(R.id.marcaequipo);
        if(listaMarcas.isEmpty()){
            listaMarcas.add("Otro");
            listaMarcas.add("Laptop");
            listaMarcas.add("Celular");
            listaMarcas.add("Tablet");
            listaMarcas.add("Monitor");
        }



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,listaMarcas);

        spinner = findViewById(R.id.spinnertipo);
        spinner.setAdapter(adapter);


    }

    public void guardarEquipo(View view) {

        DatabaseReference ref = firebaseDatabase.getReference();
        DatabaseReference refequipos = ref.child("usuarioTI");

        Equipo equipo = new Equipo();

        if (listaId.isEmpty()){
            listaId.add("0");
        }

        int contador= listaId.size()-1;
        for(int i=contador;i<listaId.size();i++){
            equipo.setId(String.valueOf(i+1));


        }
        listaId.add(String.valueOf(contador+1));

        equipo.setCaracteristicas(caracteristicasDispositivo.getText().toString());
        equipo.setIncluye(incluyeDispositivo.getText().toString());
        equipo.setStock(Integer.parseInt(stockDispositivo.getText().toString()));
        equipo.setMarca(marcaDispositivo.getText().toString());

        if(spinner.getSelectedItem().toString().equals("Otro")){
            equipo.setTipo(opcionOtro.getText().toString());
            listaMarcas.add(opcionOtro.getText().toString());

        }else{
            equipo.setTipo(spinner.getSelectedItem().toString());
        }

        refequipos.child("listaEquipos").push().setValue(equipo).addOnSuccessListener(unused -> {
            Toast.makeText(agregar_equipo_usaurioti.this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
        });


        opcionOtro.setText("");
        caracteristicasDispositivo.setText("");
        incluyeDispositivo.setText("");
        stockDispositivo.setText("");
        marcaDispositivo.setText("");        }


    public void cancelar(View view){
        Intent intent = new Intent(agregar_equipo_usaurioti.this, listadoEquiposUsuario.class);
        startActivity(intent);
    }










}