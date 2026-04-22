package com.example.tfg_davidherrerosfernandez;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Incidencia {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String titulo;
    public String descripcion;
    public String prioridad;
    public String fecha;

    public int usuarioId;

    public String estado;
}