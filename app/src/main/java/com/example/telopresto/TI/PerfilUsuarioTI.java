package com.example.telopresto.TI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.ImageDecoder;
import android.net.TelephonyNetworkSpecifier;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.telopresto.R;
import com.example.telopresto.dto.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilUsuarioTI extends AppCompatActivity {

    ImageView imageView;
    TextView uid, tv_nombre_edit, tv_correo_edit, tv_codigo_edit;
    Button btn_editarfoto, btn_eliminar;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference ref;

    private static final String TAG = "PerfilUsuarioTI";

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario_ti);
        setBottomNavigationView();

        imageView = findViewById(R.id.imageView);
        tv_nombre_edit = findViewById(R.id.tv_nombre_edit);
        tv_correo_edit = findViewById(R.id.tv_correo_edit);
        tv_codigo_edit = findViewById(R.id.tv_codigo_edit);
        System.out.println("Steph");

        btn_editarfoto = findViewById(R.id.btn_editarfoto);
        btn_eliminar = findViewById(R.id.btn_eliminar);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        System.out.println(user.getK());
        ref = FirebaseDatabase.getInstance().getReference("usuario");

        // OBTENER DATOS DEL USUARIO
        ref.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // SI EL USUARIO EXISTE
                for(DataSnapshot children: snapshot.getChildren()){

                    Usuario usuario = children.getValue(Usuario.class);


                }
                if(snapshot.getKey().equals()){
                    //OBTENEMOS LOS DATOS DE FIREBASE
//                    String nombres = ""+snapshot.child("Nombre").getValue();
                    String codigo = ""+snapshot.child("codigo").getValue();
                    String correo = ""+snapshot.child("correo").getValue();
                    String image = ""+snapshot.child("imagen").getValue();

                    //SETEAMOS LOS DATOS EN LOS TEXTVIEW E IMAGEVIEW
//                    tv_nombre_edit.setText(nombres);
                    tv_correo_edit.setText(correo);
                    tv_codigo_edit.setText(codigo);
                    System.out.println(codigo);
                    System.out.println(correo);

                    //OBTENEMOS IMAGEN

                        Glide.with(PerfilUsuarioTI.this).load(image).into(imageView);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    //----------------------------------------------------------------------------------------------

//    public void checkCurrentUser() {
//        // [START check_current_user]
//        if (user != null) {
//            // User is signed in
//        } else {
//            // No user is signed in
//        }
//        // [END check_current_user]
//    }
//
//    public void getUserProfile() {
//        // [START get_user_profile]
//        if (user != null) {
//            // Name, email address, and profile photo Url
//            String name = user.getDisplayName();
//            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();
//
//            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();
//
//            // The user's ID, unique to the Firebase project. Do NOT use this value to
//            // authenticate with your backend server, if you have one. Use
//            // FirebaseUser.getIdToken() instead.
//        }
//        // [END get_user_profile]
//    }
//
//    public void getProviderData() {
//        // [START get_provider_data]
//        if (user != null) {
//            for (UserInfo profile : user.getProviderData()) {
//                // Id of the provider (ex: google.com)
//                String providerId = profile.getProviderId();
//
//                // UID specific to the provider
//                String uid = profile.getUid();
//
//                // Name, email address, and profile photo Url
//                String name = profile.getDisplayName();
//                String email = profile.getEmail();
//                Uri photoUrl = profile.getPhotoUrl();
//            }
//        }
//        // [END get_provider_data]
//    }
//
//    public void updateProfile() {
//        // [START update_profile]
//
//        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                .setDisplayName("Jane Q. User")
//                .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
//                .build();
//
//        user.updateProfile(profileUpdates)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "User profile updated.");
//                        }
//                    }
//                });
//        // [END update_profile]
//    }

    //----------------------------------------------------------------------------------------------

    public void setBottomNavigationView(){
        bottomNavigationView = findViewById(R.id.bottomNavigationUsuario);
        bottomNavigationView.clearAnimation();
        bottomNavigationView.setSelectedItemId(R.id.perfilUsuarioTI);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.perfilUsuarioTI:
                        return true;
                    case R.id.listado_equipos:
                        startActivity(new Intent(PerfilUsuarioTI.this, listadoEquiposUsuario.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.solicitudes:
                        startActivity(new Intent(PerfilUsuarioTI.this,listaSolicitudesUsuario.class));
                }
                return false;
            }
        });
    }
}