package com.example.telopresto;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.telopresto.dto.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Login_principal extends AppCompatActivity {

    //definimos las variables del firebase auth

    GoogleSignInClient googleSignInClient;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    EditText codigo,password;
    Button iniciar,registrarse;


    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                startActivity(new Intent(Login_principal.this,Cliente_lista.class));
                finish();
            }else{
                firebaseAuth.signOut();
                Toast.makeText(Login_principal.this,"Por favor, Verifique su correo, Revise spam",Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        codigo = findViewById(R.id.codigoInputEditText);
        password = findViewById(R.id.passwordInputEditText);
        iniciar = findViewById(R.id.iniciarSesionBtn);
        registrarse = findViewById(R.id.registrarbutton);









        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth =  FirebaseAuth.getInstance();
        firebaseAuth.setLanguageCode("es-419");
        databaseReference = firebaseDatabase.getReference();

        if(firebaseAuth.getCurrentUser() != null){
            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                startActivity(new Intent(Login_principal.this,Cliente_lista.class));
                finish();
            }else{
                firebaseAuth.signOut();
                Toast.makeText(Login_principal.this,"Por favor, Verifique su correo, Revise spam",Toast.LENGTH_SHORT).show();
            }

        }else{
            ((Button) findViewById(R.id.registrarbutton)).setOnClickListener(view -> {
                startActivity(new Intent(Login_principal.this,signIn.class));
            });
            ((Button) findViewById(R.id.iniciarSesionBtn)).setOnClickListener(view -> {
                EditText correo = findViewById(R.id.codigoInputEditText);
                EditText contrasena = findViewById(R.id.passwordInputEditText);
                if(correo.getText().toString().trim().isEmpty()){
                    correo.setError("No dejar vacio");
                    correo.requestFocus();
                }else if (contrasena.getText().toString().trim().isEmpty()){
                    contrasena.setError("No dejar vacio");
                    contrasena.requestFocus();
                }else{
                    firebaseAuth.signInWithEmailAndPassword(correo.getText().toString(),contrasena.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                firebaseAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                            startActivity(new Intent(Login_principal.this,Cliente_lista.class));
                                            finish();
                                        }else{
                                            firebaseAuth.getCurrentUser().sendEmailVerification();
                                            Toast.makeText(Login_principal.this, "Se el ha enviado nuevamente la verificación por correo, por favor verifique", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(Login_principal.this, "Verifique sus datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            ((Button) findViewById(R.id.GoogleBtn)).setOnClickListener(view -> {
                Intent intent = googleSignInClient.getSignInIntent();
                signInLauncher.launch(intent);
            });
        }

    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>(){
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                onSignInResult(result);
            }
        }
    });

    private void onSignInResult(ActivityResult result){
        Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
        try {
            GoogleSignInAccount account = accountTask.getResult();
            firebaseAuthenticationFinal(account);
        }catch (Exception e){
            Log.d("Exception",e.getMessage());
        }
    }

    private void firebaseAuthenticationFinal(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(task.getResult().getAdditionalUserInfo().isNewUser()){
                        databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).setValue(new Usuario(task.getResult().getAdditionalUserInfo().getUsername(),task.getResult().getAdditionalUserInfo().getUsername(),firebaseAuth.getCurrentUser().getEmail())).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Login_principal.this, "Se crea una cuenta Google", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(Login_principal.this, "Error crea una cuenta Google", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(Login_principal.this, "Ingresa con google correctamente", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(Login_principal.this,Cliente_lista.class));
                    finish();
                }else{
                    Toast.makeText(Login_principal.this, "Error utilizando credenciales", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void abrirSignIn(View view) {
        Intent intent = new Intent(this, signIn.class);
        startActivity(intent);
    }

    public void abrirForgetPassword(View view) {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
    }



}




