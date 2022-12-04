package com.example.telopresto.TI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telopresto.Cliente.Cliente_detalles;
import com.example.telopresto.Cliente.listaEquiposAdapter;
import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class listadoEquiposUsuarioAdapter extends RecyclerView.Adapter<listadoEquiposUsuarioAdapter.myViewHolder>{

    Context context;
    ArrayList<Equipo> list;
    FirebaseDatabase firebaseDatabase;


    public listadoEquiposUsuarioAdapter(Context context, ArrayList<Equipo> list) {
        this.context = context;
        this.list = list;

    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        Equipo equipo;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_equipos_usuario, parent, false);
        return new myViewHolder(view);

    }
    @Override
    public void onBindViewHolder(myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Equipo e= list.get(position);
        holder.equipo = e;
        TextView tipoText = holder.itemView.findViewById(R.id.textTipo2);
        TextView stockText = holder.itemView.findViewById(R.id.stock);

        tipoText.setText(e.getTipo());
        stockText.setText(String.valueOf(e.getStock()));
        String id = e.getId();

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref1  = firebaseDatabase.getReference("usuarioTI").child("listaEquipos");

        Button btn_editar = holder.itemView.findViewById(R.id.edita_btn_equipo);
        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), editar_equipo_usuarioti.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        Button btn_eliminar = holder.itemView.findViewById(R.id.btn_eliminar_equipo);
        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            //    ref1.child().removeValue();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
