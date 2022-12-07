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
import com.example.telopresto.dto.Solicitud;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class listaSolicitudesAdapter extends RecyclerView.Adapter<listaSolicitudesAdapter.myViewHolder>{

    Context context;
    ArrayList<Solicitud> list;


    public listaSolicitudesAdapter(Context context, ArrayList<Solicitud> list) {
        this.context = context;
        this.list = list;

    }
    public class myViewHolder extends RecyclerView.ViewHolder{

        Solicitud solicitud;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_solicitudes, parent, false);
        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(myViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Solicitud e= list.get(position);
        holder.solicitud = e;
        TextView tipoText = holder.itemView.findViewById(R.id.textTipo2);
        TextView tiempoText = holder.itemView.findViewById(R.id.tiempoSolicitudNum);
        //TextView estado = holder.itemView.findViewById(R.id.estadoSolicitudModificable);
        tipoText.setText(e.getTipo());
        System.out.printf(e.getTipo());
        tiempoText.setText(e.getTiempoDeSolicitud());
        //estado.setText(e.getEstado());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}