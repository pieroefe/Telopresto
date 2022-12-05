package com.example.telopresto.TI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telopresto.Cliente.Cliente_detalles;
import com.example.telopresto.Cliente.agregarSolicitud;
import com.example.telopresto.R;
import com.example.telopresto.dto.Equipo;
import com.example.telopresto.dto.Solicitud;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listaSolicitudesUsuarioAdapter extends RecyclerView.Adapter<listaSolicitudesUsuarioAdapter.myViewHolder> {
    Context context;
    ArrayList<Solicitud> list;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;



    public listaSolicitudesUsuarioAdapter(Context context, ArrayList<Solicitud> list) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.rv_solicitudes_aceptar_rechazar, parent, false);
        return new myViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Solicitud e= list.get(position);
        holder.solicitud = e;
        TextView tipoText = holder.itemView.findViewById(R.id.textTipo2);
        TextView tiempoText = holder.itemView.findViewById(R.id.tiempoSolicitudNum);
        String id = e.getId();

        tipoText.setText(e.getTipo());
        tiempoText.setText(e.getTiempoDeSolicitud());
        Button btn_aceptar = holder.itemView.findViewById(R.id.button4);
        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(holder.itemView.getContext(), Solicitudes_detalle.class);
                intent.putExtra("idEquipo3", id);
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
