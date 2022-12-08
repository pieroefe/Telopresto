package com.example.telopresto.TI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.telopresto.Cliente.Cliente_solicitudes;
import com.example.telopresto.Cliente.agregarSolicitud;
import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javax.microedition.khronos.egl.EGLDisplay;

public class editar_equipo_usuarioti extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;

    FirebaseAuth firebaseAuth;
    StorageReference storageReference;

    EditText caracText, incluyeText, stocText;
    String textCarac, textoIncluye, textoStock, textTipo, textMarca;
    Spinner tipoSpinner, marcaSpinner;
    String id;
    ArrayList<String> listaMarcas;
    ArrayList<String> listaTipo;
    ImageView imageViewDispositivoUsuario;
    ArrayList<Equipo> equipos;
    Button subirFoto;

    HashMap usuario = new HashMap();
    private static final int GALLERY_INTENT = 1;
    String urlString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo_usuarioti);



        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref1  = firebaseDatabase.getReference("usuarioTI").child("listaEquipos");

        id =  getIntent().getStringExtra("idEquipo2");


        tipoSpinner = findViewById(R.id.editarNombreDispositivo);
        marcaSpinner = findViewById(R.id.editarMarcaDispositivo);
        caracText = findViewById(R.id.editarCaracteristicaDispositivo);
        incluyeText = findViewById(R.id.editarIncluyeDispositivo);
        stocText = findViewById(R.id.editarStockDispositivo);
        imageViewDispositivoUsuario = findViewById(R.id.image_view_dispositivo);

        listaTipo = new ArrayList<>();
        // listaTipo.add("Otro");
        listaTipo.add("Laptop");
        listaTipo.add("Celular");
        listaTipo.add("Tablet");
        listaTipo.add("Monitor");


        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists ()) {
                            equipos = new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String tipoString = snapshot1.child("tipo").getValue(String.class);
                     /*   if(tipoString.equals("Laptop") || tipoString.equals("Celular") || tipoString.equals("Tablet")|| tipoString.equals("Monitor")){
                        }else{
                           listaTipo.add(tipoString);
                        }

                      */


                        Equipo equipo = snapshot1.getValue(Equipo.class);
                        equipos.add(equipo);

                        for (Equipo equipo1 : equipos ){
                            if(Objects.equals(equipo1.getId(), id)){
                                urlString = equipo1.getUrl();
                                textCarac = equipo1.getCaracteristicas();
                                System.out.println("holis amistad");
                                System.out.println(caracText);
                                textoIncluye = equipo1.getIncluye();
                                textoStock = equipo1.getStock();
                                textTipo= equipo1.getTipo();
                                textMarca = equipo1.getMarca();
                             //   Glide.with(editar_equipo_usuarioti.this).load(urlString).into(imageViewDispositivoUsuario);
                            }

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
                ArrayList<String> listaMarcas = new ArrayList<>();
                if (snapshot.exists()) {
                    for(DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String marcaString = snapshot1.child("marca").getValue(String.class);
                        listaMarcas.add(marcaString);
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


        storageReference = FirebaseStorage.getInstance().getReference();
        subirFoto = findViewById(R.id.btn_subir_foto);

        subirFoto.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_INTENT);

        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_INTENT && resultCode==RESULT_OK){

            Uri uri = data.getData();


            StorageReference filepath = storageReference.child("fotos").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                    while(!uriTask.isSuccessful());
                    if (uriTask.isSuccessful()){
                        uriTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String download_uri = uri.toString();

                                usuario.put("url", download_uri);

                                firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference ref1 = firebaseDatabase.getReference().child("usuarioTI").child("listaEquipos");
                                ref1.child(id).updateChildren(usuario).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        Toast.makeText(editar_equipo_usuarioti.this, "Se subio correctamente la foto", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                Glide.with(editar_equipo_usuarioti.this).load(download_uri).into(imageViewDispositivoUsuario);

                            }
                        });
                    }
                }
            });
        }

    }

    public void updatear(View view){


        if(!marcaSpinner.getSelectedItem().toString().isEmpty()){
            usuario.put("marca", marcaSpinner.getSelectedItem().toString());
        }else{

            usuario.put("marca", textMarca);
        }

        if(!tipoSpinner.getSelectedItem().toString().isEmpty()){
            usuario.put("tipo",  tipoSpinner.getSelectedItem().toString());
        }else{

            usuario.put("tipo", textTipo);
        }

        if(!caracText.getText().toString().isEmpty()){
            usuario.put("caracteristicas", caracText.getText().toString());
        }else{

            usuario.put("caracteristicas", textCarac);
            System.out.println(textCarac);
        }

        if(!incluyeText.getText().toString().isEmpty()){
            usuario.put("incluye", incluyeText.getText().toString());
        }else{
            usuario.put("incluye", textoIncluye);

        }


        if(!stocText.getText().toString().isEmpty()){
            usuario.put("stock", stocText.getText().toString());
        }else{
            usuario.put("stock", textoStock);
        }


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
        Glide.with(editar_equipo_usuarioti.this).load(urlString).into(imageViewDispositivoUsuario);



    }
    public void cancelar(View view){
        Intent intent = new Intent(editar_equipo_usuarioti.this, listadoEquiposUsuario.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



}