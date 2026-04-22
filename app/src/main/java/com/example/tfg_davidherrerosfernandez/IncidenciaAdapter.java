package com.example.tfg_davidherrerosfernandez;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IncidenciaAdapter extends RecyclerView.Adapter<IncidenciaAdapter.ViewHolder> {

    List<Incidencia> lista;
    Context context;
    AppDatabase db;

    public IncidenciaAdapter(Context context, List<Incidencia> lista) {
        this.context = context;
        this.lista = lista;
        db = AppDatabase.getDatabase(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo, prioridad, fecha, usuario;

        public ViewHolder(View view) {
            super(view);
            titulo = view.findViewById(R.id.tvTitulo);
            prioridad = view.findViewById(R.id.tvPrioridad);
            fecha = view.findViewById(R.id.tvFecha);
            usuario = view.findViewById(R.id.tvUsuario);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_incidencia, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Incidencia i = lista.get(position);

        Usuario u = db.usuarioDao().getById(i.usuarioId);

        String nombre = (u != null) ? u.nombre : "Desconocido";

        holder.titulo.setText(i.titulo + ": " + i.descripcion);
        holder.prioridad.setText("Prioridad " + i.prioridad);
        holder.fecha.setText(i.fecha);
        holder.usuario.setText("Por: " + nombre);
    }

    @Override
    public int getItemCount() {
        return lista != null ? lista.size() : 0;
    }
}