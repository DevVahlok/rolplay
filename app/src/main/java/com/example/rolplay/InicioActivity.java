package com.example.rolplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.nio.file.Files;

public class InicioActivity extends AppCompatActivity {

    //Declaración de variables
    private Button mBotonPestanaCabecera, mBotonPestanaPuntosHabilidad, mBotonPestanaHabilidadesBonificadores1,
            mBotonPestanaHabilidadesBonificadores2, mBotonPestanaCompetenciasIdiomas, mBotonPestanaEquipo,
            mBotonPestanaAtaquesConjuros, mBotonPestanaCombate, mBotonPestanaPersonalidad, mBotonPestanaRasgosAtributos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mBotonPestanaCabecera = findViewById(R.id.ActivityInicio_cabecera_btn);
        mBotonPestanaPuntosHabilidad = findViewById(R.id.ActivityInicio_puntosHabilidad_btn);
        mBotonPestanaHabilidadesBonificadores1 = findViewById(R.id.ActivityInicio_habilidadesBonificadores1_btn);
        mBotonPestanaHabilidadesBonificadores2 = findViewById(R.id.ActivityInicio_habilidadesBonificadores2_btn);

        mBotonPestanaCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(InicioActivity.this, CabeceraActivity.class));
            }
        });

        mBotonPestanaPuntosHabilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "Puntos Habilidad", Toast.LENGTH_SHORT).show();
            }
        });

        mBotonPestanaHabilidadesBonificadores1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "Habilidades y Bonificadores", Toast.LENGTH_SHORT).show();
            }
        });

        mBotonPestanaHabilidadesBonificadores2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "Habilidades y Bonificadores", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO: 9 +1 botones (sabiduría percepción pasiva va en habilidades+tiradas de salvación)

    }
}
