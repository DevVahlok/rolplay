package com.example.rolplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioActivity extends AppCompatActivity {

    //Declaración de variables
    private Button mBotonPestanaCabecera, mBotonPestanaPuntosHabilidad, mBotonPestanaHabilidadesBonificadores1,
            mBotonPestanaHabilidadesBonificadores2, mBotonPestanaCompetenciasIdiomas, mBotonPestanaEquipo,
            mBotonPestanaAtaquesConjuros, mBotonPestanaCombate, mBotonPestanaPersonalidad, mBotonPestanaRasgosAtributos;

    private FirebaseAuth mAuth;

    private TextView mNombreJugador_TV, mTrasfondoPersonaje_TV, mClaseNivelPersonaje_TV, mRazaPersonaje_TV,
            mAlineamientoPersonaje_TV, mExperienciaPersonaje_TV, mNombrePersonaje_TV;

    private FirebaseUser mUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Incialización de variables
        mAuth = FirebaseAuth.getInstance();

        mBotonPestanaCabecera = findViewById(R.id.ActivityInicio_cabecera_btn);
        mBotonPestanaPuntosHabilidad = findViewById(R.id.ActivityInicio_puntosHabilidad_btn);
        mBotonPestanaHabilidadesBonificadores1 = findViewById(R.id.ActivityInicio_habilidadesBonificadores1_btn);
        mBotonPestanaHabilidadesBonificadores2 = findViewById(R.id.ActivityInicio_habilidadesBonificadores2_btn);
        mNombreJugador_TV = findViewById(R.id.ActivityInicio_nombreJugador_TV);
        mClaseNivelPersonaje_TV = findViewById(R.id.ActivityInicio_claseNivelPersonaje_TV);
        mTrasfondoPersonaje_TV = findViewById(R.id.ActivityInicio_trasfondoPersonaje_TV);
        mRazaPersonaje_TV = findViewById(R.id.ActivityInicio_razaPersonaje_TV);
        mAlineamientoPersonaje_TV = findViewById(R.id.ActivityInicio_alineamientoPersonaje_TV);
        mExperienciaPersonaje_TV = findViewById(R.id.ActivityInicio_experienciaPersonaje_TV);
        mNombreJugador_TV = findViewById(R.id.ActivityInicio_NombrePersonaje_TV);
        mBotonPestanaCompetenciasIdiomas = findViewById(R.id.ActivityInicio_competenciasIdiomas_btn);
        mBotonPestanaEquipo = findViewById(R.id.ActivityInicio_competenciasIdiomas_btn);
        mBotonPestanaAtaquesConjuros = findViewById(R.id.ActivityInicio_ataquesConjuros_btn);
        mBotonPestanaCombate = findViewById(R.id.ActivityInicio_combate_btn);
        mBotonPestanaPersonalidad = findViewById(R.id.ActivityInicio_personalidad_btn);
        mBotonPestanaRasgosAtributos = findViewById(R.id.ActivityInicio_rasgosAtributos_btn);

        mAuth = FirebaseAuth.getInstance();
        mUsuario = mAuth.getCurrentUser();

        //TODO: Setear datos de Firebase en los siguientes campos
        //Seteo datos en ficha
        //ALERTA: DATOS DE PLACEHOLDER, SUSTITUIR POR BBDD DE FIREBASE
        mNombreJugador_TV.setText(mUsuario.getEmail());
        mClaseNivelPersonaje_TV.setText("Brujo 4");
        mTrasfondoPersonaje_TV.setText("Soldado");
        mRazaPersonaje_TV.setText("Dracónido");
        //TODO: No hay alineamientos en BBDD. Maybe añadirlos a FireBase?
        mAlineamientoPersonaje_TV.setText("Neutral Bueno");
        mExperienciaPersonaje_TV.setText("500 / 1500 exp");
        mNombreJugador_TV.setText("Vahlokillo");


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
