package com.example.rolplay;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CabeceraFragment extends Fragment {

    //Declaración de variables
    private EditText mNombrePersonajeET, mClasePersonajeET, mTrasfondoPersonajeET, mAlineamientoPersonajeET;
    private Spinner mDropdownRaza, mDropdownClase, mDropdownAlineamiento;
    private ProgressBar mBarraProgreso;
    private int mNivel, mProgresoExperiencia, mExperienciaTotal;
    private TextView mExperiencia_ET, mNivel_ET;
    private FirebaseDatabase mDatabase;
    private String[] listaRazas = new String[] {};
    private String[] listaClases = new String[] {};
    private String[] listaAlineamiento = new String[] {};
    private FirebaseAuth mAuth;
    private View v;

    public CabeceraFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_cabecera, container, false);

        Bundle recuperados = getArguments();

        //Inicialización de variables
        mNombrePersonajeET = v.findViewById(R.id.CabeceraActivity_nombrePersonaje_ET);
        mDropdownRaza = v.findViewById(R.id.CabeceraActivity_raza_dropdown);
        mNivel_ET = v.findViewById(R.id.CabeceraActivity_tituloNivel);
        mExperiencia_ET = v.findViewById(R.id.CabeceraActivity_tituloExperiencia);
        mBarraProgreso = v.findViewById(R.id.CabeceraActivity_nivel_barraProgreso);
        mDropdownClase = v.findViewById(R.id.CabeceraActivity_clase_dropdown);
        mDropdownAlineamiento = v.findViewById(R.id.CabeceraActivity_alineamiento_dropdown);
        mTrasfondoPersonajeET = v.findViewById(R.id.CabeceraActivity_trasfondoPersonaje_ET);
        mDatabase = FirebaseDatabase.getInstance();

        listaRazas = recuperados.getStringArray("Razas");
        listaClases = recuperados.getStringArray("Clases");
        listaAlineamiento = recuperados.getStringArray("Alineamientos");
        mNombrePersonajeET.setText(recuperados.getString("Nombre"));
        mTrasfondoPersonajeET.setText(recuperados.getString("Trasfondo"));

        creadorAdapter(listaRazas, mDropdownRaza, recuperados.getString("Raza"));
        creadorAdapter(listaClases, mDropdownClase, recuperados.getString("Clase"));
        creadorAdapter(listaAlineamiento, mDropdownAlineamiento, recuperados.getString("Alineamiento"));


        mProgresoExperiencia = recuperados.getInt("Puntos de Experiencia");
        mNivel = recuperados.getInt("Nivel");
        mExperienciaTotal = mNivel * (mNivel+1) * 500;

        //Se setean los valores máximos y actuales a la barra de progreso de nivel
        mBarraProgreso.setMax(mExperienciaTotal);
        mBarraProgreso.setProgress(mProgresoExperiencia);

        mExperiencia_ET.setText(getString(R.string.experiencia, Integer.toString(mProgresoExperiencia), Integer.toString(mExperienciaTotal)));
        mNivel_ET.setText(getString(R.string.nivelPersonaje, Integer.toString(mNivel)));

        //TODO: Al darle al botón de sumar exp, abrir dialog preguntando por una cantidad y 3 botones: sumar, restar, cancelar (a ver si podemos hacer que NO se cierre el dialog al darle a sumar/restar, solo al cancelar)

        return v;
    }

    private void creadorAdapter(String[] lista, Spinner dropdown, String s) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, lista);
        dropdown.setAdapter(adapter);
        int aux = adapter.getPosition(s);
        dropdown.setSelection(aux);
    }


    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onPause() {
        super.onPause();

        //Inicialización de variables
        mNombrePersonajeET = v.findViewById(R.id.CabeceraActivity_nombrePersonaje_ET);
        mDropdownRaza = v.findViewById(R.id.CabeceraActivity_raza_dropdown);
        mNivel_ET = v.findViewById(R.id.CabeceraActivity_tituloNivel);
        mExperiencia_ET = v.findViewById(R.id.CabeceraActivity_tituloExperiencia);
        mBarraProgreso = v.findViewById(R.id.CabeceraActivity_nivel_barraProgreso);
        mDropdownClase = v.findViewById(R.id.CabeceraActivity_clase_dropdown);
        mDropdownAlineamiento = v.findViewById(R.id.CabeceraActivity_alineamiento_dropdown);
        mTrasfondoPersonajeET = v.findViewById(R.id.CabeceraActivity_trasfondoPersonaje_ET);
        mDatabase = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuariActual = mAuth.getCurrentUser();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("Nombre",mNombrePersonajeET.getText().toString().trim());
        hashMap.put("Raza",mDropdownRaza.getSelectedItem().toString().trim());
        hashMap.put("Trasfondo",mTrasfondoPersonajeET.getText().toString().trim());
        hashMap.put("Clase",mDropdownClase.getSelectedItem().toString().trim());
        hashMap.put("Alineamiento",mDropdownAlineamiento.getSelectedItem().toString().trim());
        hashMap.put("Nivel", Integer.toString(mNivel));
        hashMap.put("Puntos de Experiencia", Integer.toString(mProgresoExperiencia));

        mDatabase.getReference("users/"+usuariActual.getUid()).updateChildren(hashMap);

    }
}
