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

import java.util.Objects;

public class InicioFragment extends Fragment {

    //Declaración de variables
    private Button mBotonPestanaCabecera, mBotonPestanaPuntosHabilidad, mBotonPestanaHabilidadesBonificadores1,
            mBotonPestanaHabilidadesBonificadores2, mBotonPestanaCompetenciasIdiomas, mBotonPestanaEquipo,
            mBotonPestanaAtaquesConjuros, mBotonPestanaCombate, mBotonPestanaPersonalidad, mBotonPestanaRasgosAtributos;

    private TextView mNombreJugador_TV, mTrasfondoPersonaje_TV, mClaseNivelPersonaje_TV, mRazaPersonaje_TV,
            mAlineamientoPersonaje_TV, mExperienciaPersonaje_TV, mNombrePersonaje_TV;

    private FirebaseUser mUsuario;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
        mUsuario = mAuth.getCurrentUser();

        //TODO: Setear datos de Firebase en los siguientes campos

        //Seteo datos en ficha
        mClaseNivelPersonaje_TV.setText("Brujo 4");
        mTrasfondoPersonaje_TV.setText("Soldado");
        mRazaPersonaje_TV.setText("Dracónido");
        //TODO: No hay alineamientos en BBDD. Maybe añadirlos a FireBase?
        mAlineamientoPersonaje_TV.setText("Neutral Bueno");
        mExperienciaPersonaje_TV.setText("500 / 1500 exp");
        mNombreJugador_TV.setText("Vahlokillo");

        //TODO: LOPD (en un fragment tipo párrafo info?)

        mBotonPestanaCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("cabecera");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CabeceraFragment()).addToBackStack(null).commit();
            }
        });

        mBotonPestanaPuntosHabilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("puntosHabilidad");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PuntosHabilidadFragment()).addToBackStack(null).commit();
            }
        });

        mBotonPestanaHabilidadesBonificadores1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("habilidadesBonificadores");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HabilidadesBonificadoresFragment()).addToBackStack(null).commit();
            }
        });

        mBotonPestanaHabilidadesBonificadores2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("habilidadesBonificadores");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HabilidadesBonificadoresFragment()).addToBackStack(null).commit();
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
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }
}
