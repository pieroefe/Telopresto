package com.example.telopresto.Admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.telopresto.R;
import com.example.telopresto.dto.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class Adapter_ListaUsuariosTI extends RecyclerView.Adapter<Adapter_ListaUsuariosTI.EventViewHolder> {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    private List<Usuario> usuariosListar;
    private Context context;

    public List<Usuario> getUsuariosTIList() {
        return usuariosListar;
    }

    public void setusuarioTIList(List<Usuario> usuariosListar) {
        this.usuariosListar = usuariosListar;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public class EventViewHolder extends RecyclerView.ViewHolder{
        Usuario usuario;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_usuarioti_lista, parent, false);
        System.out.println("aqui la lista de clientes " + usuariosListar );
        return new Adapter_ListaUsuariosTI.EventViewHolder(itemView);
    }







    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Usuario u = usuariosListar.get(position);
        ImageView imageView = holder.itemView.findViewById(R.id.iv_foto_TI);
        TextView textView = holder.itemView.findViewById(R.id.tv_nombre_usuarioti);
        Button editar = holder.itemView.findViewById(R.id.btn_edit_usuarioTi);
        Button eliminar = holder.itemView.findViewById(R.id.btn_delete_usuarioTi);

        //Editar
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),agregar_usuarioti_admin.class);
                intent.putExtra("usuariosLista", u);
                getContext().startActivity(intent);
            }
        });


        //Eliminar
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eliminarkey = u.getKey();
                databaseReference.child("usuario").child(eliminarkey).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            storageReference.child("img/"+u.getFilename()).delete();
                            Toast.makeText(getContext(),"Borrado con exito",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Borrado fallido",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        textView.setText(u.getDetalleAImprimir());
        StorageReference imageRef = storageReference.child("img/"+u.getFilename());
        Glide.with(getContext()).load(imageRef).into(imageView);








    }

    @Override
    public int getItemCount() {
        return usuariosListar.size();
    }
}
