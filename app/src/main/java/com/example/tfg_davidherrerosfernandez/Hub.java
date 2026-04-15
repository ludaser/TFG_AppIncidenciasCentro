package com.example.tfg_davidherrerosfernandez;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class Hub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialCardView cardreporterapido = findViewById(R.id.cardreporterapido);

        cardreporterapido.setOnClickListener(v -> {
            Intent intent = new Intent(Hub.this, Reporte.class);
            startActivity(intent);
        });

        ImageButton btnCerrar = findViewById(R.id.botonCerrarSesion);

        btnCerrar.setOnClickListener(v -> {

            SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
            prefs.edit().clear().apply();

            startActivity(new Intent(Hub.this, Logueo.class));
            finish();
        });
    }
}