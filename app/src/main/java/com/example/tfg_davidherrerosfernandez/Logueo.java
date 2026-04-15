package com.example.tfg_davidherrerosfernandez;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Logueo extends AppCompatActivity {

    TextInputEditText etEmail, etPassword;
    Button btnLogin;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logueo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //  AUTLOGIN (SOLO UNA VEZ)
        SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
        int usuarioId = prefs.getInt("usuarioId", -1);

        if (usuarioId != -1) {
            startActivity(new Intent(this, Hub.class));
            finish();
            return;
        }

        //  IR A REGISTRO
        TextView textoVolverRegistro = findViewById(R.id.tvVolverLogin2);
        textoVolverRegistro.setOnClickListener(v -> {
            startActivity(new Intent(Logueo.this, Registro.class));
        });

        //  INPUTS
        etEmail = findViewById(R.id.tilUsuarioEdit);
        etPassword = findViewById(R.id.tilPasswordEdit);

        //  DB
        db = AppDatabase.getDatabase(this);

        //  LOGIN
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> login());
    }

    private void login() {

        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        //  VALIDACIÓN
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario usuario = db.usuarioDao().login(email, password);

        if (usuario != null) {

            //  GUARDAR SESIÓN
            SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
            prefs.edit().putInt("usuarioId", usuario.id).apply();

            //  IR A HUB
            startActivity(new Intent(Logueo.this, Hub.class));
            finish();

        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
        }
    }
}