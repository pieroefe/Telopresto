package com.example.telopresto.TI;

import android.content.Context;
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

public class listadoEquiposUsuarioAdapter extends RecyclerView.Adapter<listadoEquiposUsuarioAdapter.ActividadViewHolder> {

    Context context;
    ArrayList<Equipo> list;

    public listadoEquiposUsuarioAdapter(Context context, ArrayList<Equipo> list){
        this.context = context;
        this.list = list;
    }

    public class ActividadViewHolder extends RecyclerView.ViewHolder{
        TextView tipo, stock;
        Button btneditar, btnEliminar;

        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);
            btneditar = itemView.findViewById(R.id.edita_btn_equipo);
            btnEliminar=itemView.findViewById(R.id.btn_eliminar_equipo);
            tipo=itemView.findViewById(R.id.textStock);
            stock=itemView.findViewById(R.id.textEquipo);

            //TODO IMPLEMENTAR IMAGE ID
        }
    }


    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_equipos_usuario, parent, false);
        return new ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listadoEquiposUsuarioAdapter.ActividadViewHolder holder, int position) {
        Equipo equipo = new Equipo();
        holder.stock.setText(equipo.getStock());
        holder.tipo.setText(equipo.getTipo());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
