package com.example.rolplay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class CabeceraFragment extends Fragment {

    //Declaración de variables
    private EditText mNombrePersonajeET, mClasePersonajeET, mTrasfondoPersonajeET, mAlineamientoPersonajeET;
    private Spinner mDropdownRaza, mDropdownClase, mDropdownAlineamiento;
    private ProgressBar mBarraProgreso;
    private int mNivel, mProgresoExperiencia, mExperienciaTotal;
    private TextView mExperiencia_ET, mNivel_ET;

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

        //TODO: Recoger lista de razas de FireBase
        String[] listaRazas = new String[]{"Dracónido","Elfo","Enano","Gnomo","Humano","Mediano","Semielfo","Semiorco","Tiefling"};
        String[] listaClases = new String[]{"Bardo","Brujo","Bárbaro","Clérigo","Druida","Explorador","Guerrero","Hechicero","Mago","Paladín","Pícaro"};
        String[] listaAlineamiento = new String[]{"Legal bueno","Legal neutral","Legal malvado","Neutral bueno","Neutral","Neutral malvado","Caótico bueno","Caótico neutral","Caótico malvado"};

        //Setea Array al dropdown de Alineamiento
        ArrayAdapter<String> adapterAlineamiento = new ArrayAdapter<>(getActivity(), R.layout.spinner_oscuro, listaAlineamiento);
        mDropdownAlineamiento.setAdapter(adapterAlineamiento);

        //Setea Array al dropdown de Clase
        ArrayAdapter<String> adapterClase = new ArrayAdapter<>(getActivity(), R.layout.spinner_oscuro, listaClases);
        mDropdownClase.setAdapter(adapterClase);

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
        mExperiencia_ET.setText(getString(R.string.experiencia,Integer.toString(mProgresoExperiencia),Integer.toString(mExperienciaTotal)));
        mNivel_ET.setText(getString(R.string.nivelPersonaje, Integer.toString(mNivel)));

        return v;
    }


    private void showData(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds : dataSnapshot.getChildren()){
            Raza r = new Raza();
            r.setDraco(ds.child("Raza").getValue(Raza.class).getDraco());
            r.setElfo(ds.child("Raza").getValue(Raza.class).getElfo());
            r.setEnano(ds.child("Raza").getValue(Raza.class).getEnano());
            r.setGnomo(ds.child("Raza").getValue(Raza.class).getGnomo());
            r.setHumano(ds.child("Raza").getValue(Raza.class).getHumano());
            r.setMediano(ds.child("Raza").getValue(Raza.class).getMediano());
            r.setSemiorco(ds.child("Raza").getValue(Raza.class).getSemiorco());
            r.setSemielfo(ds.child("Raza").getValue(Raza.class).getSemielfo());
            r.setTiflin(ds.child("Raza").getValue(Raza.class).getTiflin());

            //Setea Array al dropdown de Raza
            ArrayList<String> array = new ArrayList<>();
            array.add(r.getDraco());
            array.add(r.getElfo());
            array.add(r.getEnano());
            array.add(r.getGnomo());
            array.add(r.getHumano());
            array.add(r.getMediano());
            array.add(r.getSemielfo());
            array.add(r.getSemiorco());
            array.add(r.getTiflin());

            String[] array1 = new String[]{r.getDraco(), r.getElfo(), r.getEnano(), r.getGnomo(), r.getHumano(), r.getMediano(), r.getSemielfo(), r.getSemiorco(), r.getTiflin()};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_oscuro, array);
            mDropdownRaza.setAdapter(adapter);

        }
    }

}
