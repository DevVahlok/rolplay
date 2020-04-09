package com.example.rolplay;


import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EquipoFragment extends Fragment implements AdapterRecyclerEquipo.OnItemListener {

    //Declaración de variables
    private ArrayList<ItemEquipo> listaDatos;
    private RecyclerView recycler;
    private TextView mNombreEquipo, mCosteEquipo, mPesoEquipo, mMonCobre, mMonPlata, mMonEsmeralda, mMonOro, mMonPlatino;
    private ImageView mFotoEquipo;
    private Button mBotonAnadirObjeto;
    private AdapterRecyclerEquipo adapter;
    private FirebaseDatabase mDatabase;
    private String[] listaObjetos = new String[] {};
    private DialogCarga mDialogCarga;
    private View v;

    private int auxiliar = 0, pesoTotal=0;;

    //Constructor
    public EquipoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_equipo, container, false);

        final Bundle recuperados = getArguments();

        //Inicialización de varaibles
        recycler = v.findViewById(R.id.Equipo_Recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mNombreEquipo = v.findViewById(R.id.listaEquipo_nombre);
        mCosteEquipo = v.findViewById(R.id.listaEquipo_coste);
        mPesoEquipo = v.findViewById(R.id.listaEquipo_peso);
        mBotonAnadirObjeto = v.findViewById(R.id.Equipo_anadirObjeto_btn);
        mMonCobre = v.findViewById(R.id.Equipo_moneda_cobre);
        mMonPlata = v.findViewById(R.id.Equipo_moneda_plata);
        mMonEsmeralda = v.findViewById(R.id.Equipo_moneda_esmeralda);
        mMonOro = v.findViewById(R.id.Equipo_moneda_oro);
        mMonPlatino = v.findViewById(R.id.Equipo_moneda_platino);
        mDatabase = FirebaseDatabase.getInstance();

        mDialogCarga = new DialogCarga();

        //Al pulsar el botón de añadir objeto
        mBotonAnadirObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Muestra un dialog para que el usuario selecciona cuál quiere añadir
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                TextView title = new TextView(getActivity());
                title.setText(getString(R.string.anadirObjeto));
                title.setTextColor(getActivity().getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0,40,0,0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getActivity());

                final TextView subtitle = new TextView(getActivity());
                subtitle.setTextColor(getActivity().getColor(R.color.colorPrimary));
                subtitle.setTextSize(16);
                subtitle.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                subtitle.setPadding(10,10,10,0);
                final Spinner spinnerObjeto = new Spinner(getActivity());

                //Añade la lista de objetos al spinner
                listaObjetos = recuperados.getStringArray("Lista De Objetos");
                creadorAdapter(listaObjetos,spinnerObjeto);

                final DatabaseReference[] mObjetos = {mDatabase.getReference("DungeonAndDragons/Objeto")};
                spinnerObjeto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        auxiliar++;
                        if (auxiliar>1) {
                            switch (spinnerObjeto.getItemAtPosition(position).toString()){
                                case "Armaduras":
                                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De Armaduras"));
                                    spinnerObjeto.setAdapter(adapter);
                                    auxiliar=0;
                                    subtitle.setText("Armaduras");
                                    break;
                                case "Armas":
                                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De Armas"));
                                    spinnerObjeto.setAdapter(adapter1);
                                    auxiliar=0;
                                    subtitle.setText("Armas");
                                    break;
                                case "Armaduras Ligeras":
                                    ArrayAdapter<String> adapter11 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De ArmLig"));
                                    spinnerObjeto.setAdapter(adapter11);
                                    auxiliar=0;
                                    mObjetos[0] = mObjetos[0].child("Armaduras/Armaduras Ligeras");
                                    break;
                                case "Armaduras Medias":
                                    ArrayAdapter<String> adapter12 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De ArmMed"));
                                    spinnerObjeto.setAdapter(adapter12);
                                    auxiliar=0;
                                    mObjetos[0] = mObjetos[0].child("Armaduras/Armaduras Medias");
                                    break;
                                case "Armaduras Pesadas":
                                    ArrayAdapter<String> adapter13 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De ArmPes"));
                                    spinnerObjeto.setAdapter(adapter13);
                                    mObjetos[0] = mObjetos[0].child("Armaduras/Armaduras Pesadas");
                                    auxiliar=0;
                                    break;
                                case "Armas a distancia marciales":
                                    ArrayAdapter<String> adapter21 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De ArmDM"));
                                    spinnerObjeto.setAdapter(adapter21);
                                    mObjetos[0] = mObjetos[0].child("Armas/Armas a distancia marciales");
                                    auxiliar=0;
                                    break;
                                case "Armas a distancia simples":
                                    ArrayAdapter<String> adapter22 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De ArmDS"));
                                    spinnerObjeto.setAdapter(adapter22);
                                    mObjetos[0] = mObjetos[0].child("Armas/Armas a distancia simples");
                                    auxiliar=0;
                                    break;
                                case "Armas cuerpo cuerpo marciales":
                                    ArrayAdapter<String> adapter23 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De ArmCM"));
                                    spinnerObjeto.setAdapter(adapter23);
                                    mObjetos[0] = mObjetos[0].child("Armas/Armas cuerpo cuerpo marciales");
                                    auxiliar=0;
                                    break;
                                case "Armas cuerpo cuerpo simples":
                                    ArrayAdapter<String> adapter24 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De ArmCS"));
                                    spinnerObjeto.setAdapter(adapter24);
                                    mObjetos[0] = mObjetos[0].child("Armas cuerpo cuerpo simples");
                                    auxiliar=0;
                                    break;
                                case "Herramientas":
                                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De Herram"));
                                    spinnerObjeto.setAdapter(adapter2);
                                    auxiliar=0;
                                    mObjetos[0] = mObjetos[0].child("Herramientas");
                                    subtitle.setText("Herramientas");
                                    break;
                                case "Mercancías":
                                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De Merc"));
                                    spinnerObjeto.setAdapter(adapter3);
                                    auxiliar=0;
                                    mObjetos[0] = mObjetos[0].child("Mercancías");
                                    subtitle.setText("Mercancías");
                                    break;
                                case "Misceláneo":
                                    ArrayAdapter<String> adapter4 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De Misc"));
                                    spinnerObjeto.setAdapter(adapter4);
                                    auxiliar=0;
                                    mObjetos[0] = mObjetos[0].child("Misceláneo");
                                    subtitle.setText("Misceláneo");
                                    break;
                                case "Monturas y Vehículos":
                                    ArrayAdapter<String> adapter5 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, recuperados.getStringArray("Lista De Mont"));
                                    spinnerObjeto.setAdapter(adapter5);
                                    auxiliar=0;
                                    mObjetos[0] = mObjetos[0].child("Monturas y Vehículos");
                                    subtitle.setText("Monturas y Vehículos");
                                    break;
                            }


                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        auxiliar=0;
                    }
                });

                linearLayout.addView(subtitle);
                linearLayout.addView(spinnerObjeto);
                linearLayout.setPadding(120,10,120,10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.anadir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialogCarga.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), null);
                        cogerObjeto(mObjetos[0], spinnerObjeto.getSelectedItem().toString(), new MyCallback() {
                             @Override
                             public void onCallback(String[] value) {
                                 //Añade objeto al Recycle
                                 listaDatos.add(new ItemEquipo(spinnerObjeto.getSelectedItem().toString(), Integer.parseInt(value[0]), Integer.parseInt(value[1]),value[2]));
                                 pesoTotal+=Integer.parseInt(value[1]);
                                 adapter.notifyItemInserted(listaDatos.size() - 1);
                                 mDialogCarga.dismiss();
                             }
                         });

                    }


                });

                constructrorDialog.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //Enseña el dialog de 'Añadir objeto'
                AlertDialog anadirObjeto = constructrorDialog.create();
                anadirObjeto.show();
                Objects.requireNonNull(anadirObjeto.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));

            }
        });

        mMonCobre.setText(String.valueOf(recuperados.getInt("Piezas de cobre")));
        mMonPlata.setText(String.valueOf(recuperados.getInt("Piezas de plata")));
        mMonEsmeralda.setText(String.valueOf(recuperados.getInt("Piezas de esmeralda")));
        mMonOro.setText(String.valueOf(recuperados.getInt("Piezas de oro")));
        mMonPlatino.setText(String.valueOf(recuperados.getInt("Piezas de platino")));

        //Rellena el Recycler con los objetos
        listaDatos = new ArrayList<ItemEquipo>();

        ArrayList<String> aux = recuperados.getStringArrayList("Equipo");
        for(int i=0; i<aux.size()/4;i++){
            listaDatos.add(new ItemEquipo(aux.get(i*4), Integer.parseInt(aux.get((i*4)+1)), Integer.parseInt(aux.get((i*4)+2)), aux.get((i*4)+3)));
            pesoTotal+=Integer.parseInt(aux.get((i*4)+2));
        }


        //Añade los objetos equipados al Recycler
        adapter = new AdapterRecyclerEquipo(listaDatos, this, getContext());
        recycler.setAdapter(adapter);

        return v;
    }


    @Override
    public void onItemClick(int position) {

        //Elimina el objeto del recycler
        pesoTotal-=listaDatos.get(position).getPeso();
        listaDatos.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void creadorAdapter(String[] lista, Spinner dropdown) {

        //Crea un adapter para el dialog
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, lista);
        dropdown.setAdapter(adapter);

    }


    private void cogerObjeto(DatabaseReference mDatabase, final String s, final MyCallback myCallback) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String valor = "" + ds.getKey();
                    if(valor.equals(s)) {
                        String[] result;
                        String coste, peso, url;
                        try {
                            coste = (String) ds.child("Coste").getValue();
                        }catch (Exception e){
                            try {
                                Long cost = (Long) ds.child("Coste").getValue();
                                coste = cost.toString();

                            }catch (Exception ex){
                                coste="0";
                            }
                        }
                        try {
                            peso = (String) ds.child("Peso").getValue();
                        }catch (Exception e){
                            try {
                                Long cost = (Long) ds.child("Peso").getValue();
                                peso = cost.toString();

                            }catch (Exception ex){
                                peso="0";
                            }
                        }
                        try {
                            url = (String) ds.child("URL").getValue();
                        }catch (Exception e){
                            url ="..";
                        }
                        result = new String[]{coste, peso, url};
                        myCallback.onCallback(result);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mDatabase = FirebaseDatabase.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuariActual = mAuth.getCurrentUser();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Equipo",listaDatos);
        hashMap.put("Piezas de cobre",mMonCobre.getText().toString());
        hashMap.put("Piezas de plata",mMonPlata.getText().toString());
        hashMap.put("Piezas de esmeralda",mMonEsmeralda.getText().toString());
        hashMap.put("Piezas de oro",mMonOro.getText().toString());
        hashMap.put("Piezas de platino",mMonPlatino.getText().toString());
        hashMap.put("Peso total",String.valueOf(pesoTotal));
        mDatabase.getReference("users/"+usuariActual.getUid()).updateChildren(hashMap);
    }
}

