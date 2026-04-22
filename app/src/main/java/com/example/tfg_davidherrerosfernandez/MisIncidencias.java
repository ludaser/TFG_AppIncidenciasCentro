package com.example.tfg_davidherrerosfernandez;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MisIncidencias extends AppCompatActivity {

    TextView tvWelcome;
    RecyclerView recyclerView;

    AppDatabase db;
    int usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mis_incidencias);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 🔙 BOTÓN VOLVER
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, Hub.class));
            finish();
        });

        // 📦 BD
        db = AppDatabase.getDatabase(this);

        // 🔐 SESIÓN
        SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
        usuarioId = prefs.getInt("usuarioId", -1);

        if (usuarioId == -1) {
            Toast.makeText(this, "Sesión no válida", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 👤 USUARIO
        Usuario usuario = db.usuarioDao().getById(usuarioId);
        String nombre = (usuario != null) ? usuario.nombre : "Usuario";

        tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText("¡Hola, " + nombre + "!");

        // 📋 RECYCLER
        recyclerView = findViewById(R.id.recyclerIncidencias);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 📊 DATOS
        List<Incidencia> lista = db.incidenciaDao().getByUsuario(usuarioId);

        // ⭐ AQUÍ VA EL IF (ANTES DEL ADAPTER)
        if (lista == null || lista.isEmpty()) {
            Toast.makeText(this, "No tienes incidencias", Toast.LENGTH_SHORT).show();
        }

        // 🔗 ADAPTER
        IncidenciaAdapter adapter = new IncidenciaAdapter(this, lista);
        recyclerView.setAdapter(adapter);
    }
}