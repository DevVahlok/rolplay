package com.example.rolplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.nio.file.Files;

public class InicioActivity extends AppCompatActivity {

    //Declaración de variables
    private Button mBotonPestanaCabecera, mBotonPestanaPuntosHabilidad, mBotonPestanaHabilidadesBonificadores1,
            mBotonPestanaHabilidadesBonificadores2, mBotonPestanaCompetenciasIdiomas, mBotonPestanaEquipo,
            mBotonPestanaAtaquesConjuros, mBotonPestanaCombate, mBotonPestanaPersonalidad, mBotonPestanaRasgosAtributos;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        mAuth = FirebaseAuth.getInstance();

        mBotonPestanaCabecera = findViewById(R.id.ActivityInicio_cabecera_btn);
        mBotonPestanaPuntosHabilidad = findViewById(R.id.ActivityInicio_puntosHabilidad_btn);
        mBotonPestanaHabilidadesBonificadores1 = findViewById(R.id.ActivityInicio_habilidadesBonificadores1_btn);
        mBotonPestanaHabilidadesBonificadores2 = findViewById(R.id.ActivityInicio_habilidadesBonificadores2_btn);
        mBotonPestanaCompetenciasIdiomas = findViewById(R.id.ActivityInicio_competenciasIdiomas_btn);
        mBotonPestanaEquipo = findViewById(R.id.ActivityInicio_equipo_btn);
        mBotonPestanaAtaquesConjuros = findViewById(R.id.ActivityInicio_ataquesConjuros_btn);
        mBotonPestanaCombate = findViewById(R.id.ActivityInicio_combate_btn);
        mBotonPestanaPersonalidad = findViewById(R.id.ActivityInicio_personalidad_btn);
        mBotonPestanaRasgosAtributos = findViewById(R.id.ActivityInicio_rasgosAtributos_btn);

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

        mBotonPestanaCompetenciasIdiomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(InicioActivity.this, XXX.class));
            }
        });

        mBotonPestanaEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(InicioActivity.this, XXX.class));
            }
        });
        mBotonPestanaAtaquesConjuros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(InicioActivity.this, XXX.class));
            }
        });
        mBotonPestanaCombate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(InicioActivity.this, XXX.class));
            }
        });
        mBotonPestanaPersonalidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(InicioActivity.this, XXX.class));
            }
        });
        mBotonPestanaRasgosAtributos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(InicioActivity.this, XXX.class));
            }
        });


        //TODO: 9 +1 botones (sabiduría percepción pasiva va en habilidades+tiradas de salvación)

    }

    @Override
    protected void onStart() {

        ComprobarEstatUsuari();

        super.onStart();
    }

    private void ComprobarEstatUsuari() {

        FirebaseUser usuari = mAuth.getCurrentUser();

        if (usuari!=null){
            //TODO: Cargar datos de Firebase respectivos a la ficha
        }else{
            startActivity(new Intent(InicioActivity.this, MainActivity.class));
        }
    }


}
