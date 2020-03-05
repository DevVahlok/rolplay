package com.example.rolplay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class CabeceraActivity extends AppCompatActivity {

    //Declaración de variables
    private EditText mNombrePersonajeET, mClasePersonajeET, mTrasfondoPersonajeET, mAlineamientoPersonajeET;
    private Spinner mDropdownRaza;
    private ProgressBar mBarraProgreso;
    private int mNivel, mProgresoExperiencia, mExperienciaTotal;
    private TextView mExperiencia_ET, mNivel_ET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabecera);

        //Inicialización de variables
        mNombrePersonajeET = findViewById(R.id.CabeceraActivity_nombrePersonaje_ET);
        mDropdownRaza = findViewById(R.id.CabeceraActivity_raza_dropdown);
        mNivel_ET = findViewById(R.id.CabeceraActivity_tituloNivel);
        mExperiencia_ET = findViewById(R.id.CabeceraActivity_tituloExperiencia);
        mBarraProgreso = findViewById(R.id.CabeceraActivity_nivel_barraProgreso);

        //TODO: Recoger lista de razas de FireBase
        String[] listaRazas = new String[]{"Bardo","Brujo","Bárbaro","Clérigo","Druida","Explorador","Guerrero","Hechicero","Mago","Paladín","Pícaro"};

        //Setea Array al dropdown de Raza
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_oscuro, listaRazas);
        mDropdownRaza.setAdapter(adapter);

        //TODO: Recoger Nivel y Experiencia de Firebase
        mExperienciaTotal = 100;
        mProgresoExperiencia = 50;
        mNivel = 4;

        //Se setean los valores máximos y actuales a la barra de progreso de nivel
        mBarraProgreso.setMax(mExperienciaTotal);
        mBarraProgreso.setProgress(mProgresoExperiencia);

        //Actualización de los valores en pantalla
        mExperiencia_ET.setText(getString(R.string.experiencia,Integer.toString(mProgresoExperiencia),Integer.toString(mExperienciaTotal)));
        mNivel_ET.setText(getString(R.string.nivelPersonaje, Integer.toString(mNivel)));

    }
}
