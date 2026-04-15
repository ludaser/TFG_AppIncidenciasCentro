package com.example.tfg_davidherrerosfernandez;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UsuarioDao {

    @Insert
    void insertar(Usuario usuario);

    @Query("SELECT * FROM Usuario WHERE email = :email AND password = :password LIMIT 1")
    Usuario login(String email, String password);

    @Query("SELECT * FROM Usuario WHERE id = :id")
    Usuario getById(int id);

    // ✅ AÑADIR ESTE
    @Query("SELECT * FROM Usuario WHERE email = :email LIMIT 1")
    Usuario getByEmail(String email);
}