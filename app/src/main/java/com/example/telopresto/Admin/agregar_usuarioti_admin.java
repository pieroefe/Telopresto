package com.example.telopresto.Admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.telopresto.R;
import com.example.telopresto.dto.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;


public class agregar_usuarioti_admin extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference().child("usuario");
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    Uri imageUri;






    Button btn_agregarTI,btn_foto;
    TextInputLayout correo,password,verifyPassword;


    ActivityResultLauncher<Intent> openImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result ->{
        if(result.getResultCode()== Activity.RESULT_OK){
            imageUri=result.getData().getData();
        }
    });





    boolean verifyPasswordValido = true;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuarioti_admin);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Button btn_foto = findViewById(R.id.btn_foto);
        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subirImagen();
            }
        });





    }

    public void agregarTI (View view){

        TextInputLayout correo = findViewById(R.id.til_correo);
        TextInputLayout password = findViewById(R.id.til_contra);
        TextInputLayout verifyPassword = findViewById(R.id.til_confirm);
        Button btn_agregarTI = findViewById(R.id.btn_agregar_usuarioti2);
        Button btn_foto = findViewById(R.id.btn_foto);

        correo.setErrorEnabled(false);
        password.setErrorEnabled(false);
        verifyPassword.setErrorEnabled(false);



        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
        String emailPattern = "[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+";
        boolean correoValido = true;
        if (correo.getEditText().getText().toString() != null && !correo.getEditText().getText().toString().equals("")) {
            System.out.println("correo aqui");
            System.out.println("el correo es " + correo.getEditText().getText().toString() + "asi que nose");
            boolean b = correo.getEditText().getText().toString().trim().matches("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+");
            System.out.println(b);
            if (correo.getEditText().getText().toString().trim().matches("[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+")) {
                System.out.println("clogro");

            } else {
                System.out.println("correo vacio");
                System.out.println("el correo es " + correo.getEditText().getText().toString() + "asi que xd");
                correo.setError("Ingrese un correo válido");
                correoValido = false;

            }
        } else {
            correo.setError("Ingrese un correo");
            correoValido = false;
            correo.setErrorEnabled(true);
        }

        boolean passwordValido = true;
        System.out.println(password.getEditText().getText().toString());
        boolean b1 = (password.getEditText().getText().toString() != null);
        boolean b2 = (password.getEditText().getText().toString().trim().equals(""));
        System.out.println(b1);
        System.out.println(b2);
        if (password.getEditText().getText().toString() != null && !password.getEditText().getText().toString().trim().equals("")) {

        } else {
            password.setError("Ingrese una contraseña");
            passwordValido = false;
            password.setErrorEnabled(true);
        }

        if (verifyPassword.getEditText().getText().toString() != null && !verifyPassword.getEditText().getText().toString().trim().equals("")) {


        } else {
            verifyPassword.setError("Debe verificar su contraseña");
            verifyPasswordValido = false;
            verifyPassword.setErrorEnabled(true);
        }

        String filename= "";
        boolean fileValido = true;


        if(imageUri!=null){
            /*
            String[] path= imageUri.toString().split("/");
            filename = path[path.length-1];
            StorageReference imageReference = storageReference.child("img/"+filename);
            imageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                    Log.d("msg","Archivo Subido correctamente")).addOnFailureListener(e->Log.d("msg","Error",e.getCause()));*/

        }else{

            Toast.makeText( agregar_usuarioti_admin.this, "Debe subir una foto para el usuario TI", Toast.LENGTH_SHORT).show();
            System.out.println("mala imagen");

            fileValido = false;
        }

        System.out.println("booleanos");


        System.out.println("correo valido" + correoValido);
        System.out.println("passvalido" +passwordValido);
        System.out.println("vervalido" + verifyPasswordValido);
        System.out.println("filevalido" + fileValido);






        if (correoValido && passwordValido && verifyPasswordValido && fileValido) {
            Log.d("task", "Registro valido");

            firebaseAuth.createUserWithEmailAndPassword(correo.getEditText().getText().toString().trim(), password.getEditText().getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("task", "Registro exitoso");
                        String key = databaseReference.push().getKey();
                        databaseReference.child(key).child("correo").setValue(correo.getEditText().getText().toString().trim());
                        databaseReference.child(key).child("rol").setValue("usuarioTI");


                        //Para la creacion de la imagen


                        String[] path= imageUri.toString().split("/");
                        String filename = path[path.length-1];
                        StorageReference imageReference = storageReference.child("img/"+filename);
                        imageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                                Log.d("msg","Archivo Subido correctamente")).addOnFailureListener(e->Log.d("msg","Error",e.getCause()));
                        //Guardo el valor en la base de datos

                        //databaseReference.child(key).setValue(key);


                        databaseReference.child(key).child("key").setValue(key);







                        Intent intent = new Intent(agregar_usuarioti_admin.this, listadoUsuarioAdmin.class);

                        startActivity(intent);


                    } else {
                        Toast.makeText( agregar_usuarioti_admin.this, "Error: Este correo ya esta siendo utilizado", Toast.LENGTH_SHORT).show();
                        Log.d("task", "Error en el momento de registro - " + task.getException().getMessage());
                    }
                }
            });
        }












    }

    public void subirImagen(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        openImageLauncher.launch(intent);
    }







    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuarioti_admin);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mfirestore = FirebaseFirestore.getInstance();

        TextInputLayout correo =findViewById(R.id.til_correo);
        TextInputLayout password = findViewById(R.id.til_contra);
        TextInputLayout verifyPassword = findViewById(R.id.til_confirm);
        Button btn_agregarTI = findViewById(R.id.btn_agregar_usuarioti2);

        btn_agregarTI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correoTI = correo.getEditText().getText().toString().trim();
                String passwordTI = password.getEditText().getText().toString().trim();
                String confirmTI = verifyPassword.getEditText().getText().toString().trim();
                String rol = null; 


                if(correoTI.isEmpty() && passwordTI.isEmpty() && confirmTI.isEmpty() ){
                    Toast.makeText(getApplicationContext(),"Debe ingresar los datos", Toast.LENGTH_SHORT);

                }else{
                    postUsuarioTI(correoTI,passwordTI,confirmTI,rol);


                }


            }

            private void postUsuarioTI(String correoTI, String passwordTI, String confirmTI,String rol) {
                Map<String,Object> map = new HashMap<>();
                
                map.put("correoTI", correoTI);
                map.put("passwordTI", passwordTI);
                map.put("confirmTI", confirmTI);
                map.put("usuarioTI", rol);
                



                mfirestore.collection("usuariosTI").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(getApplicationContext(),"El usuario TI fue creado exitosamente", Toast.LENGTH_SHORT);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error al ingresar", Toast.LENGTH_SHORT);

                    }
                });







            }
        });








    }*/



    /*public void agregarTI(View view) {



        TextInputLayout correo =findViewById(R.id.til_correo);
        TextInputLayout password = findViewById(R.id.til_contra);
        TextInputLayout verifyPassword = findViewById(R.id.til_confirm);


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z.]+";
        boolean correoValido = true;
        if (correo.getEditText().getText().toString() != null && !correo.getEditText().getText().toString().equals("")) {
            System.out.println("correo aqui");
            System.out.println("el correo es " + correo.getEditText().getText().toString() + "asi que nose");
            if (!correo.getEditText().getText().toString().matches(emailPattern)) {
                System.out.println("correo vacio");
                System.out.println("el correo es " + correo.getEditText().getText().toString() + "asi que xd");
                correo.setError("Ingrese un correo válido");
                correoValido = false;
            } else {
                System.out.println("clogro");
                correo.setErrorEnabled(false);
            }
        } else {
            correo.setError("Ingrese un correo");
            correoValido = false;
        }

        boolean passwordValido = true;
        if (password.getEditText().getText().toString() != null && password.getEditText().getText().toString().equals("")) {
            password.setErrorEnabled(false);
        } else {
            password.setError("Ingrese una contraseña");
            passwordValido = false;
        }

        if (verifyPassword.getEditText().getText().toString() != null && !verifyPassword.getEditText().getText().toString().equals("")) {

            verifyPassword.setErrorEnabled(false);
        } else {
            verifyPassword.setError("Debe verificar su contraseña");
            verifyPasswordValido = false;
        }

        if (correoValido && passwordValido && verifyPasswordValido) {
            Log.d("task", "Registro valido");

            firebaseAuth.createUserWithEmailAndPassword(correo.getEditText().getText().toString(), password.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d("task", "Registro exitoso");
                        String key = databaseReference.push().getKey();
                        databaseReference.child(key).child("correo").setValue(correo.getEditText().getText().toString());
                        databaseReference.child(key).child("rol").setValue("usuarioTI");
                        databaseReference.child(key).child("key").setValue(key);
                        Intent intent = new Intent(agregar_usuarioti_admin.this, listadoUsuarioAdmin.class);

                        startActivity(intent);


                    } else {
                        Toast.makeText( agregar_usuarioti_admin.this, "Error: Este correo ya esta siendo utilizado", Toast.LENGTH_SHORT).show();
                        Log.d("task", "Error en el momento de registro - " + task.getException().getMessage());
                    }
                }
            });
        }
    }*/























    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationAdmmin);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.usuarios_menu);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.usuarios_menu:
                        startActivity(new Intent(agregar_usuarioti_admin.this, listadoUsuarioAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reportes:
                        startActivity(new Intent(agregar_usuarioti_admin.this, reportesAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.alumno_menu:
                        startActivity(new Intent(agregar_usuarioti_admin.this, listadoAlumnoAdmin.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void golistaTI(View view) {
        Intent intent = new Intent(this, listadoUsuarioAdmin.class);
        startActivity(intent);
    }




}