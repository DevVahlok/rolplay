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

        Log.d("----------------------------------", mRazas.toString());

        //TODO: Recoger lista de razas de FireBase
        String[] listaRazas = new String[]{"Dracónido", "Elfo", "Enano", "Gnomo", "Humano", "Mediano", "Semielfo", "Semiorco", "Tiefling"};
        String[] listaClases = new String[]{"Bardo", "Brujo", "Bárbaro", "Clérigo", "Druida", "Explorador", "Guerrero", "Hechicero", "Mago", "Paladín", "Pícaro"};
        String[] listaAlineamiento = new String[]{"Legal bueno", "Legal neutral", "Legal malvado", "Neutral bueno", "Neutral", "Neutral malvado", "Caótico bueno", "Caótico neutral", "Caótico malvado"};

        //Setea Array al dropdown de Alineamiento
        ArrayAdapter<String> adapterAlineamiento = new ArrayAdapter<>(getActivity(), R.layout.spinner_oscuro, listaAlineamiento);
        mDropdownAlineamiento.setAdapter(adapterAlineamiento);

        //Setea Array al dropdown de Clase
        ArrayAdapter<String> adapterClase = new ArrayAdapter<>(getActivity(), R.layout.spinner_oscuro, listaClases);
        mDropdownClase.setAdapter(adapterClase);

        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("----------------------------------", "Abans de la funció");

                showData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mRazas.addValueEventListener(event);
        mRazas.addListenerForSingleValueEvent(event);

        readData(mRazas, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onStart() {
                mRazas.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d("----------------------------------", "Abans de la funció");

                        showData(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });


        Log.d("----------------------------------", "Hola despues de londata");

        //Setea Array al dropdown de Raza
        ArrayAdapter<String> adapterRaza = new ArrayAdapter<>(getActivity(), R.layout.spinner_oscuro, listaRazas);
        mDropdownRaza.setAdapter(adapterRaza);

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


    private void showData(DataSnapshot dataSnapshot) {

        Log.d("----------------------------------", "Abans del for");

        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            String raza = "" + ds.getKey();
            Log.d("----------------------------------", raza);


        }
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

    public void readData(DatabaseReference ref, final OnGetDataListener listener) {
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onFailure();
            }
        });

    }
}
