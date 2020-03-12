package com.example.rolplay;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ContenedorInicioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_inicio);

        //TODO: Hacer 2 activities. La primera para el login (sin menú lateral), la segunda para inicio y todos sus fragments (con menú lateral) en el cambio de activity, acordarse de cerrar
        //TODO: Modificar también el comprobarEstadoUsuario() para saber si está logeado
        //TODO: Alex: https://www.youtube.com/watch?v=zYVEMCiDcmY // https://www.youtube.com/watch?v=bjYstsO1PgI

    }
}
