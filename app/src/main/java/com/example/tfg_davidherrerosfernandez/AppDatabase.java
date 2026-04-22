package com.example.tfg_davidherrerosfernandez;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Usuario.class, Incidencia.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();

    // 🔥 ESTA LÍNEA ES LA CLAVE (seguro que te falta o no se ha actualizado)
    public abstract IncidenciaDao incidenciaDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "mi_base_datos"
                    )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // esto ultimo es nuevo
                    .addCallback(roomCallback)
                    .build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);

            new Thread(() -> {
                UsuarioDao dao = INSTANCE.usuarioDao();

                Usuario tecnico = new Usuario();
                tecnico.nombre = "Tecnico 1";
                tecnico.email = "tecnico@efas.com";
                tecnico.password = "1234";
                tecnico.rol = "tecnico";
                dao.insertar(tecnico);

                Usuario profesor = new Usuario();
                profesor.nombre = "Profesor 1";
                profesor.email = "profesor@efas.com";
                profesor.password = "1234";
                profesor.rol = "profesor";
                dao.insertar(profesor);

                Usuario alumno = new Usuario();
                alumno.nombre = "Alumno 1";
                alumno.email = "alumno@efas.com";
                alumno.password = "1234";
                alumno.rol = "alumno";
                dao.insertar(alumno);
            }).start();
        }
    };
}