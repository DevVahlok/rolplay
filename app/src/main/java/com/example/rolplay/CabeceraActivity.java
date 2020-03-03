package com.example.rolplay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class CabeceraActivity extends AppCompatActivity {

    //Declaración de variables
    private EditText mEditTextNombrePersonaje;
    private Spinner mDropdownRaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabecera);

        //Inicialización de variables
        mEditTextNombrePersonaje = findViewById(R.id.CabeceraActivity_nombrePersonaje_ET);
        mDropdownRaza = findViewById(R.id.CabeceraActivity_raza_dropdown);

        //TODO: Recoger lista de razas de FireBase
        String[] listaRazas = new String[]{"Bardo","Brujo","Bárbaro","Clérigo","Druida","Explorador","Guerrero","Hechicero","Mago","Paladín","Pícaro"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_oscuro, listaRazas);

        mDropdownRaza.setAdapter(adapter);

    }
}
