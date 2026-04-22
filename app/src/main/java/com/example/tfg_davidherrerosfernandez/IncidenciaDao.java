package com.example.tfg_davidherrerosfernandez;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface IncidenciaDao {

    @Insert
    void insertar(Incidencia incidencia);

    @Query("SELECT * FROM Incidencia WHERE usuarioId = :usuarioId")
    List<Incidencia> getByUsuario(int usuarioId);
}