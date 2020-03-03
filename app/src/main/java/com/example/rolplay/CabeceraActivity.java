package com.example.rolplay;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class CabeceraActivity extends AppCompatActivity {

    //Declaración de variables
    private EditText mEditTextNombrePersonaje;
    private Spinner mDropdownRaza;
    private ProgressBar mBarraProgreso;
    private int mNivel, mProgresoExperiencia, mExperienciaTotal;
    private TextView mExperiencia, mNivel;

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
        mNivel = findViewById(R.id.CabeceraActivity_tituloNivel);
        mExperiencia = findViewById(R.id.CabeceraActivity_tituloExperiencia);
        mBarraProgreso = findViewById(R.id.CabeceraActivity_nivel_barraProgreso);
        mBarraProgreso.setMax(mExperienciaTotal);
        mBarraProgreso.setProgress(mProgresoExperiencia);
        mExperiencia.setText(mProgresoExperiencia+" / "+mExperienciaTotal);
        mNivel.setText("Nivel "+mNivel);




    }
}
