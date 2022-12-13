package com.example.telopresto.TI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.telopresto.Admin.listadoUsuarioAdmin;
import com.example.telopresto.Login_principal;
import com.example.telopresto.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MotivoSolicitudRechaza extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseUser user;
    String id;

    TextInputLayout motivoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motivo_solicitud_rechaza);
        id =  getIntent().getStringExtra("idEquipo3");

        motivoInput = findViewById(R.id.motivoInput);
    }

    public void updatearMotivo(View view){

        HashMap solicitud = new HashMap();
        solicitud.put("motivo", motivoInput.getEditText().getText().toString());
     /*   solicitud.put("id", id);
        solicitud.put("curso",cursoText.getText().toString());
        solicitud.put("marca",marcaText.getText().toString());
        solicitud.put("motivo",motivoText.getText().toString());
        solicitud.put("otros",otrosText.getText().toString());
        solicitud.put("programas",programasText.getText().toString());
        solicitud.put("tipo",tipoText.getText().toString());
        solicitud.put("tiempoDeSolicitud", tiempoText.getText().toString());

      */

        databaseReference = FirebaseDatabase.getInstance().getReference().child("solicitudes").child(id);
        DatabaseReference ref1  = databaseReference;


        ref1.updateChildren(solicitud).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Intent intent2 = new Intent(MotivoSolicitudRechaza.this, listaSolicitudesUsuario.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
            }
        });



    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_lista_usuarios,menu);
        return true;

    }


    public void accionCerrarSesionUsuarios(MenuItem item){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        finish();
        Intent intent2 = new Intent(MotivoSolicitudRechaza.this, Login_principal.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent2);



    }
}