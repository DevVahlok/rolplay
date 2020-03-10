package com.example.rolplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class CabeceraActivity extends AppCompatActivity {

    //Declaración de variables
    private EditText mNombrePersonajeET, mClasePersonajeET, mTrasfondoPersonajeET, mAlineamientoPersonajeET;
    private Spinner mDropdownRaza;
    private ProgressBar mBarraProgreso;
    private int mNivel, mProgresoExperiencia, mExperienciaTotal;
    private TextView mExperiencia_ET, mNivel_ET;
    private FirebaseDatabase  mDatabase;
    private DatabaseReference mRazas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabecera);

        //Inicialización de variables
        mNombrePersonajeET = findViewById(R.id.CabeceraActivity_nombrePersonaje_ET);
        mDropdownRaza = findViewById(R.id.CabeceraActivity_raza_dropdown);
        mNivel_ET = findViewById(R.id.CabeceraActivity_tituloNivel);
        mExperiencia_ET = findViewById(R.id.CabeceraActivity_tituloExperiencia);
        mBarraProgreso = findViewById(R.id.CabeceraActivity_nivel_barraProgreso);
        mDatabase = FirebaseDatabase.getInstance();

        //TODO: Recoger lista de razas de FireBase
        String[] listaRazas = new String[]{"Bardo","Brujo","Bárbaro","Clérigo","Druida","Explorador","Guerrero","Hechicero","Mago","Paladín","Pícaro"};

        mRazas = mDatabase.getReference();



        mRazas.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
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
        mExperiencia_ET.setText(getString(R.string.experiencia,Integer.toString(mProgresoExperiencia),Integer.toString(mExperienciaTotal)));
        mNivel_ET.setText(getString(R.string.nivelPersonaje, Integer.toString(mNivel)));
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

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_oscuro, array1);
            mDropdownRaza.setAdapter(adapter);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}