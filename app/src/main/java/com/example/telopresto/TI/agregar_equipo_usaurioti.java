package com.example.telopresto.TI;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.telopresto.Cliente.Cliente_solicitudes;
import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.example.telopresto.dto.Solicitud;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class agregar_equipo_usaurioti extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    DatabaseReference ref1;

    EditText opcionOtro, caracteristicasDispositivo, incluyeDispositivo, stockDispositivo, marcaDispositivo;
    ArrayList<String> listaMarcas = new ArrayList<>();

    Spinner spinner;

    Button subirFoto;
    ImageView imageView;

    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    Equipo equipo = new Equipo();

    String id;
    StorageReference storageReference;

    private static final int GALLERY_INTENT = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo_usaurioti);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

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


        storageReference = FirebaseStorage.getInstance().getReference();
        subirFoto = findViewById(R.id.subirFotoBtn);
        imageView = findViewById(R.id.imageViewEquipos);


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
                                DatabaseReference ref = firebaseDatabase.getReference();
                                DatabaseReference refequipos = ref.child("usuarioTI").child("listaEquipos");

                                id = refequipos.push().getKey();

                                String download_uri=uri.toString();

                                    Toast.makeText(agregar_equipo_usaurioti.this, "Se subio correctamente la foto", Toast.LENGTH_SHORT).show();
                                equipo.setUrl(download_uri);

                                refequipos.child(id).setValue(equipo).addOnSuccessListener(unused -> {
                                    Glide.with(agregar_equipo_usaurioti.this).load(download_uri).into(imageView);
                                });
                            }
                        });
                    }
                }
            });
        }

    }

    public void guardarEquipo(View view) {


        HashMap usuario = new HashMap();
        usuario.put("id", id);
        usuario.put("caracteristicas", caracteristicasDispositivo.getText().toString());
        usuario.put("incluye", incluyeDispositivo.getText().toString());
        usuario.put("marca", marcaDispositivo.getText().toString());
        usuario.put("stock", stockDispositivo.getText().toString());
        usuario.put("tipo",  spinner.getSelectedItem().toString());
        if(spinner.getSelectedItem().toString().equals("Otro")){
            usuario.put("tipo", opcionOtro.getText().toString());
            listaMarcas.add(opcionOtro.getText().toString());

        }else{
            usuario.put("tipo", spinner.getSelectedItem().toString());
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref1  = firebaseDatabase.getReference().child("usuarioTI").child("listaEquipos");

        ref1.child(id).updateChildren(usuario).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(agregar_equipo_usaurioti.this, "Guardado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(agregar_equipo_usaurioti.this, listadoEquiposUsuario.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
            }
        });
    }


    public void cancelar(View view){
        Intent intent = new Intent(agregar_equipo_usaurioti.this, listadoEquiposUsuario.class);
        startActivity(intent);
    }












}