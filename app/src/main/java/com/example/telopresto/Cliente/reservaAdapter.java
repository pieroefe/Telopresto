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
import com.example.telopresto.TI.Solicitudes_detalle;
import com.example.telopresto.dto.Equipo;
import com.example.telopresto.dto.Solicitud;

import java.util.ArrayList;

public class reservaAdapter extends RecyclerView.Adapter<reservaAdapter.myViewHolder> {

    Context context;
    ArrayList<Solicitud> list;

    public reservaAdapter(Context context, ArrayList<Solicitud> list) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.rv_solicitud_aceptada, parent, false);
        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull reservaAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position) {


        Solicitud e= list.get(position);
        holder.solicitud = e;
        TextView tipoText = holder.itemView.findViewById(R.id.textTipoSoli3);
        TextView tiempoText = holder.itemView.findViewById(R.id.tiempoSolicitudNum3);
        TextView estado = holder.itemView.findViewById(R.id.estadoSolicitud3);
      //  String id = e.getId();

        tipoText.setText(e.getTipo());
        tiempoText.setText(e.getTiempoDeSolicitud());
        estado.setText(e.getEstado());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
