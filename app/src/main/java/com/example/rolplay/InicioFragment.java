package com.example.rolplay;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InicioFragment extends Fragment {

    //Declaración de variables
    private Button mBotonPestanaCabecera, mBotonPestanaPuntosHabilidad, mBotonPestanaHabilidadesBonificadores1,
            mBotonPestanaHabilidadesBonificadores2, mBotonPestanaCompetenciasIdiomas, mBotonPestanaEquipo,
            mBotonPestanaAtaquesConjuros, mBotonPestanaCombate, mBotonPestanaPersonalidad, mBotonPestanaRasgosAtributos;

    private TextView mNombreJugador_TV, mTrasfondoPersonaje_TV, mClaseNivelPersonaje_TV, mRazaPersonaje_TV,
            mAlineamientoPersonaje_TV, mExperienciaPersonaje_TV, mNombrePersonaje_TV;

    private FirebaseUser mUsuario;
    private FirebaseAuth mAuth;
    private CabeceraFragment mCabeceraFragment;
    private LoginFragment mLoginFragment;

    public InicioFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_inicio, container, false);

        //Incialización de variables
        mBotonPestanaCabecera = v.findViewById(R.id.ActivityInicio_cabecera_btn);
        mBotonPestanaPuntosHabilidad = v.findViewById(R.id.ActivityInicio_puntosHabilidad_btn);
        mBotonPestanaHabilidadesBonificadores1 = v.findViewById(R.id.ActivityInicio_habilidadesBonificadores1_btn);
        mBotonPestanaHabilidadesBonificadores2 = v.findViewById(R.id.ActivityInicio_habilidadesBonificadores2_btn);
        mNombreJugador_TV = v.findViewById(R.id.ActivityInicio_nombreJugador_TV);
        mClaseNivelPersonaje_TV = v.findViewById(R.id.ActivityInicio_claseNivelPersonaje_TV);
        mTrasfondoPersonaje_TV = v.findViewById(R.id.ActivityInicio_trasfondoPersonaje_TV);
        mRazaPersonaje_TV = v.findViewById(R.id.ActivityInicio_razaPersonaje_TV);
        mAlineamientoPersonaje_TV = v.findViewById(R.id.ActivityInicio_alineamientoPersonaje_TV);
        mExperienciaPersonaje_TV = v.findViewById(R.id.ActivityInicio_experienciaPersonaje_TV);
        mNombreJugador_TV = v.findViewById(R.id.ActivityInicio_NombrePersonaje_TV);
        mCabeceraFragment = new CabeceraFragment();
        mLoginFragment = new LoginFragment();

        mAuth = FirebaseAuth.getInstance();
        mUsuario = mAuth.getCurrentUser();

        //TODO: Setear datos de Firebase en los siguientes campos
        //Seteo datos en ficha
        //ALERTA: DATOS DE PLACEHOLDER, SUSTITUIR POR BBDD DE FIREBASE

        mClaseNivelPersonaje_TV.setText("Brujo 4");
        mTrasfondoPersonaje_TV.setText("Soldado");
        mRazaPersonaje_TV.setText("Dracónido");
        //TODO: No hay alineamientos en BBDD. Maybe añadirlos a FireBase?
        mAlineamientoPersonaje_TV.setText("Neutral Bueno");
        mExperienciaPersonaje_TV.setText("500 / 1500 exp");
        mNombreJugador_TV.setText("Vahlokillo");

        //TODO: LOPD (en una activity nueva tipo párrafo info?)

        mBotonPestanaCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mCabeceraFragment).addToBackStack(null).commit();
            }
        });

        mBotonPestanaPuntosHabilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Puntos Habilidad", Toast.LENGTH_SHORT).show();
            }
        });

        mBotonPestanaHabilidadesBonificadores1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Habilidades y Bonificadores", Toast.LENGTH_SHORT).show();
            }
        });

        mBotonPestanaHabilidadesBonificadores2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Habilidades y Bonificadores", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        ComprobarEstatUsuari();
    }

    private void ComprobarEstatUsuari() {

        FirebaseUser usuari = mAuth.getCurrentUser();

        if (usuari!=null){
            //TODO: Cargar datos de Firebase respectivos a la ficha
        }else{
            //Pasa a la pantalla de Login si el usuario no está logueado
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mLoginFragment,"login_fragment").commit();
        }
    }
}
