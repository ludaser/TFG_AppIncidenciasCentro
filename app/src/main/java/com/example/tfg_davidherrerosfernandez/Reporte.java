package com.example.tfg_davidherrerosfernandez;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class Reporte extends AppCompatActivity {

    private MaterialCardView cardUpload;
    private ImageView ivPlaceholder;

    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    // Mostramos la imagen de la galería
                    ivPlaceholder.setImageURI(uri);
                    ivPlaceholder.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ivPlaceholder.setImageTintList(null);
                }
            });

    private final ActivityResultLauncher<Void> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.TakePicturePreview(),
            bitmap -> {
                if (bitmap != null) {
                    // Mostramos la foto de la cámara
                    ivPlaceholder.setImageBitmap(bitmap);
                    ivPlaceholder.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ivPlaceholder.setImageTintList(null);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reporte);


        ImageButton botonvolver = findViewById(R.id.btnBack);
        cardUpload = findViewById(R.id.cardUpload);
        ivPlaceholder = findViewById(R.id.ivPlaceholder);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // 4. Configurar los clics
        botonvolver.setOnClickListener(v -> {
            Intent intent = new Intent(Reporte.this, Hub.class);
            startActivity(intent);
            finish();
        });

        cardUpload.setOnClickListener(v -> mostrarOpcionesImagen());
    }


    private void mostrarOpcionesImagen() {
        String[] opciones = {"Hacer foto", "Elegir de la galería", "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar Imagen");
        builder.setItems(opciones, (dialog, which) -> {
            if (which == 0) {
                // Lógica para Cámara: Comprobar permiso
                if (checkSelfPermission(android.Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(null);
                } else {
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 100);
                }
            } else if (which == 1) {
                // Lógica para Galería: Comprobar permiso según versión de Android
                String permisoGaleria = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
                        ? android.Manifest.permission.READ_MEDIA_IMAGES
                        : android.Manifest.permission.READ_EXTERNAL_STORAGE;

                if (checkSelfPermission(permisoGaleria) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                    galleryLauncher.launch("image/*");
                } else {
                    requestPermissions(new String[]{permisoGaleria}, 101);
                }
            } else {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}