package com.example.telopresto.Cliente;

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

import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;

import java.util.ArrayList;


public class listaEquiposAdapter extends RecyclerView.Adapter<listaEquiposAdapter.myViewHolder>{

     Context context;
    ArrayList<Equipo> list;

    public listaEquiposAdapter(Context context, ArrayList<Equipo> list) {
        this.context = context;
        this.list = list;

    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView tipo, disponibles;

        Equipo equipo;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_dispositivos_cliente, parent, false);
        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Equipo e= list.get(position);
        holder.equipo = e;
        TextView tipoText = holder.itemView.findViewById(R.id.textTipoSoli3);
        TextView stockText = holder.itemView.findViewById(R.id.textViewDisponibles);
        tipoText.setText(e.getTipo());
        stockText.setText(String.valueOf(e.getStock()));

        String id = e.getId();


        Button btn_detalles = holder.itemView.findViewById(R.id.detallesBtn);
        btn_detalles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), Cliente_detalles.class);
                intent.putExtra("idEquipo", id);
                holder.itemView.getContext().startActivity(intent);
                System.out.println(list.get((position)));
            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }



}
