package com.example.rolplay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.nio.file.Files;

public class InicioActivity extends AppCompatActivity {

    //Declaración de variables
    private Button mBotonCabecera, mBotonPuntosHabilidad, mBotonHabilidadesBonificadores1,mBotonHabilidadesBonificadores2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mBotonCabecera = findViewById(R.id.ActivityInicio_cabecera_btn);
        mBotonPuntosHabilidad = findViewById(R.id.ActivityInicio_puntosHabilidad_btn);
        mBotonHabilidadesBonificadores1 = findViewById(R.id.ActivityInicio_habilidadesBonificadores1_btn);
        mBotonHabilidadesBonificadores2 = findViewById(R.id.ActivityInicio_habilidadesBonificadores2_btn);

        mBotonCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "Cabecera", Toast.LENGTH_SHORT).show();
            }
        });

        mBotonPuntosHabilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "Puntos Habilidad", Toast.LENGTH_SHORT).show();
            }
        });

        mBotonHabilidadesBonificadores1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "Habilidades y Bonificadores", Toast.LENGTH_SHORT).show();
            }
        });

        mBotonHabilidadesBonificadores2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InicioActivity.this, "Habilidades y Bonificadores", Toast.LENGTH_SHORT).show();
            }
        });

        //TODO: 9 +1 botones (sabiduría percepción pasiva va en habilidades+tiradas de salvación)

    }
}
