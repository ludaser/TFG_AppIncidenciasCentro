package com.example.tfg_davidherrerosfernandez;

import android.content.Intent;
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

public class Registro extends AppCompatActivity {

    TextInputEditText etNombre, etEmail, etPassword, etRepetirPassword;
    Button btnRegistrar;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //  VOLVER LOGIN
        TextView textoVolverInicioSesion = findViewById(R.id.tvVolverLogin);
        textoVolverInicioSesion.setOnClickListener(v -> {
            startActivity(new Intent(Registro.this, Logueo.class));
        });

        //  INPUTS
        etNombre = findViewById(R.id.etNombre);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRepetirPassword = findViewById(R.id.etRepetirPassword);

        btnRegistrar = findViewById(R.id.btnRegistrar);

        //  DB
        db = AppDatabase.getDatabase(this);

        //  REGISTRAR
        btnRegistrar.setOnClickListener(v -> registrar());
    }

    private void registrar() {

        String nombre = etNombre.getText() != null ? etNombre.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
        String repetirPassword = etRepetirPassword.getText() != null ? etRepetirPassword.getText().toString().trim() : "";

        //  CAMPOS VACÍOS
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || repetirPassword.isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        //  CONTRASEÑAS NO COINCIDEN
        if (!password.equals(repetirPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        //  EMAIL YA EXISTE
        if (db.usuarioDao().getByEmail(email) != null) {
            Toast.makeText(this, "Ese email ya está registrado", Toast.LENGTH_SHORT).show();
            return;
        }

        //  ROL AUTOMÁTICO
        String rol;

        if (email.contains("tecnico")) {
            rol = "tecnico";
        } else if (email.contains("profesor")) {
            rol = "profesor";
        } else {
            rol = "alumno";
        }

        //  CREAR USUARIO
        Usuario usuario = new Usuario();
        usuario.nombre = nombre;
        usuario.email = email;
        usuario.password = password;
        usuario.rol = rol;

        db.usuarioDao().insertar(usuario);

        Toast.makeText(this, "Usuario creado como " + rol, Toast.LENGTH_SHORT).show();

        //  LOGIN
        startActivity(new Intent(Registro.this, Logueo.class));
        finish();
    }
}