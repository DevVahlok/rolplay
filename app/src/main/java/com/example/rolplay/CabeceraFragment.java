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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CabeceraFragment extends Fragment implements OnGetDataListener {

    //Declaración de variables
    private EditText mNombrePersonajeET, mClasePersonajeET, mTrasfondoPersonajeET, mAlineamientoPersonajeET;
    private Spinner mDropdownRaza, mDropdownClase, mDropdownAlineamiento;
    private ProgressBar mBarraProgreso;
    private int mNivel, mProgresoExperiencia, mExperienciaTotal;
    private TextView mExperiencia_ET, mNivel_ET;
    private FirebaseDatabase mDatabase;
    private ArrayList<String> Razas = new ArrayList<String>();
    private ArrayList<String> Clases = new ArrayList<String>();
    private String[] listaRazas = new String[] {};
    private String[] listaClases = new String[] {};
    private String[] listaAlineamiento = new String[] {};

    public CabeceraFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cabecera, container, false);

        //Inicialización de variables
        mNombrePersonajeET = v.findViewById(R.id.CabeceraActivity_nombrePersonaje_ET);
        mDropdownRaza = v.findViewById(R.id.CabeceraActivity_raza_dropdown);
        mNivel_ET = v.findViewById(R.id.CabeceraActivity_tituloNivel);
        mExperiencia_ET = v.findViewById(R.id.CabeceraActivity_tituloExperiencia);
        mBarraProgreso = v.findViewById(R.id.CabeceraActivity_nivel_barraProgreso);
        mDropdownClase = v.findViewById(R.id.CabeceraActivity_clase_dropdown);
        mDropdownAlineamiento = v.findViewById(R.id.CabeceraActivity_alineamiento_dropdown);
        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mRazas = mDatabase.getReference().child("DungeonAndDragons/Raza");
        final DatabaseReference mClases = mDatabase.getReference().child("DungeonAndDragons/Clases");
        final DatabaseReference mAlineamiento = mDatabase.getReference().child("DungeonAndDragons/Raza");

        Log.d("----------------------------------", mRazas.toString());

        //TODO: Recoger lista de razas de FireBase
        String[] listaAlineamiento = new String[]{"Legal bueno", "Legal neutral", "Legal malvado", "Neutral bueno", "Neutral", "Neutral malvado", "Caótico bueno", "Caótico neutral", "Caótico malvado"};

        //Setea Array al dropdown de Alineamiento
        ArrayAdapter<String> adapterAlineamiento = new ArrayAdapter<>(getActivity(), R.layout.spinner_oscuro, listaAlineamiento);
        mDropdownAlineamiento.setAdapter(adapterAlineamiento);

        //Setea Array al dropdown de Clase
        ArrayAdapter<String> adapterClase = new ArrayAdapter<>(getActivity(), R.layout.spinner_oscuro, listaClases);
        mDropdownClase.setAdapter(adapterClase);

        mRazas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot, Razas, listaRazas, mDropdownRaza);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mClases.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot, Clases, listaClases, mDropdownClase);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //TODO: Recoger Nivel y Experiencia de Firebase
        mExperienciaTotal = 100;
        mProgresoExperiencia = 50;
        mNivel = 4;

        //Se setean los valores máximos y actuales a la barra de progreso de nivel
        mBarraProgreso.setMax(mExperienciaTotal);
        mBarraProgreso.setProgress(mProgresoExperiencia);

        //Actualización de los valores en pantalla
        mExperiencia_ET.setText(getString(R.string.experiencia, Integer.toString(mProgresoExperiencia), Integer.toString(mExperienciaTotal)));
        mNivel_ET.setText(getString(R.string.nivelPersonaje, Integer.toString(mNivel)));

        return v;
    }


    private void showData(DataSnapshot dataSnapshot, ArrayList<String> AL, String[] SS, Spinner Spin) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            String valor = "" + ds.getKey();
            AL.add(valor);
            SS= AL.toArray(SS);

        }
        //Setea Array al dropdown de Raza
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_oscuro, SS);
        Spin.setAdapter(adapter);
    }

    @Override
    public void onSuccess(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFailure() {

    }

}
