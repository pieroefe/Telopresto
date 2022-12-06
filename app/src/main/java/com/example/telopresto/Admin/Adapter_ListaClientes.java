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

public class Adapter_ListaClientes extends RecyclerView.Adapter<Adapter_ListaClientes.EventViewHolder> {

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    private List<Usuario> clienteLista;
    private Context context;

    public List<Usuario> getEventoList() {
        return clienteLista;
    }

    public void setClienteLista(List<Usuario> eventoList) {
        this.clienteLista = clienteLista;
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
    public Adapter_ListaClientes.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cliente_lista, parent, false);
        return new Adapter_ListaClientes.EventViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull Adapter_ListaClientes.EventViewHolder holder, int position) {
       //aqui va el codigo que falta de la vista


        Usuario u = clienteLista.get(position);
        ImageView imageView = holder.itemView.findViewById(R.id.iv_foto_TI);
        TextView textView = holder.itemView.findViewById(R.id.tv_nombre_usuarioti);




        textView.setText(u.getDetalleAImprimir());
        StorageReference imageRef = storageReference.child("img/"+u.getFilename());
        Glide.with(getContext()).load(imageRef).into(imageView);








    }

    @Override
    public int getItemCount() {
//        return clienteLista.size();
        return 0;
    }


}



