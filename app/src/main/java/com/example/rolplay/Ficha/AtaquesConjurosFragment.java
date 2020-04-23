package com.example.rolplay.Ficha;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.rolplay.Adapters.AdapterRecyclerAtaque;
import com.example.rolplay.Adapters.AdapterRecyclerConjuro;
import com.example.rolplay.Otros.DialogCarga;
import com.example.rolplay.Otros.ItemAtaque;
import com.example.rolplay.Otros.MyCallback;
import com.example.rolplay.R;
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

public class AtaquesConjurosFragment extends Fragment implements AdapterRecyclerConjuro.OnItemListener, AdapterRecyclerAtaque.OnItemListener {

    //Declaración de variables - Conjuros
    private ArrayList<String> listaConjuro;
    private RecyclerView recycler;
    private Button mBotonAnadirObjeto;
    private TextView valorTexto;
    private AdapterRecyclerConjuro adapter;

    //Declaración de variables - Ataques
    private ArrayList<ItemAtaque> listaAtaque;
    private RecyclerView recycler_ataque;
    private TextView mNombreAtaque, mCosteAtaqueo, mPesoAtaque, mDanyoAtaque, mPropiedadesAtaque;
    private ImageView mFotoAtaque;
    private Button mBotonAnadirObjeto_ataque;
    private AdapterRecyclerAtaque adapter_ataque;
    private String[] listaAtaquesPlaceholder = new String[] {};
    private DialogCarga mDialogCarga;

    private FirebaseDatabase mDatabase;
    private String codigoPJ;
    private int auxiliar = 0, pesoTotal=0;;

    //Constructor
    public AtaquesConjurosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ataques_conjuros, container, false);

        final Bundle recuperados = getArguments();
        codigoPJ = recuperados.getString("codigo");

        //Inicialización de variables - Conjuro
        recycler = v.findViewById(R.id.AtaquesConjuros_Recycler_conjuros);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        mBotonAnadirObjeto = v.findViewById(R.id.AtaquesConjuros_anadirConjuro_btn);
        valorTexto = v.findViewById(R.id.Conjuro_valor);
        listaConjuro = new ArrayList<String>();
        mDatabase = FirebaseDatabase.getInstance();

        //Inicialización de variables - Ataque
        mBotonAnadirObjeto_ataque = v.findViewById(R.id.AtaquesConjuros_anadirAtaque_btn);
        recycler_ataque = v.findViewById(R.id.AtaquesConjuros_Recycler_ataque);
        recycler_ataque.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        mDialogCarga = new DialogCarga();
        //Limitación de los conjuros a 84 carácteres ~

        listaConjuro = recuperados.getStringArrayList("Conjuro");


        adapter = new AdapterRecyclerConjuro(listaConjuro,this, getContext());
        recycler.setAdapter(adapter);

        //Al pulsar el botón de añadir objeto
        mBotonAnadirObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Muestra un dialog para que el usuario selecciona cuál quiere añadir
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                TextView title = new TextView(getActivity());
                title.setText(getString(R.string.anadirConjuro));
                title.setTextColor(getActivity().getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0,40,0,0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getActivity());

                final EditText editText = new EditText(getActivity());
                editText.setMinEms(20);


                linearLayout.addView(editText);
                linearLayout.setPadding(120,10,120,10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.anadir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Añade objeto al Recycle
                        listaConjuro.add(editText.getText().toString().trim());
                        adapter.notifyItemInserted(listaConjuro.size() - 1);
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

        //Al pulsar el botón de añadir objeto
        mBotonAnadirObjeto_ataque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Muestra un dialog para que el usuario selecciona cuál quiere añadir
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                TextView title = new TextView(getActivity());
                title.setText(getString(R.string.anadirAtaque));
                title.setTextColor(getActivity().getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0, 40, 0, 0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final TextView subtitle = new TextView(getActivity());
                subtitle.setTextColor(getActivity().getColor(R.color.colorPrimary));
                subtitle.setTextSize(16);
                subtitle.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                subtitle.setPadding(10,10,10,0);

                final Spinner spinnerObjeto = new Spinner(getActivity());

                //Añade la lista de objetos al spinner
                //TODO: Recoger lista de objetos de Firebase
                listaAtaquesPlaceholder = recuperados.getStringArray("Lista De Armas");
                creadorAdapter(listaAtaquesPlaceholder, spinnerObjeto);

                final DatabaseReference[] mObjetos = {mDatabase.getReference("DungeonAndDragons/Objeto")};
                spinnerObjeto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        auxiliar++;

                        if (auxiliar > 1) {
                            switch (spinnerObjeto.getItemAtPosition(position).toString()) {
                                case "Armas":
                                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, Objects.requireNonNull(recuperados.getStringArray("Lista De Armas")));
                                    spinnerObjeto.setAdapter(adapter1);
                                    auxiliar = 0;
                                    subtitle.setText(R.string.armas);
                                    break;
                                case "Armas a distancia marciales":
                                    ArrayAdapter<String> adapter21 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, Objects.requireNonNull(recuperados.getStringArray("Lista De ArmDM")));
                                    spinnerObjeto.setAdapter(adapter21);
                                    mObjetos[0] = mObjetos[0].child("Armas/Armas a distancia marciales");
                                    subtitle.setText(R.string.armasADistanciaMarciales);
                                    auxiliar = 0;
                                    break;
                                case "Armas a distancia simples":
                                    ArrayAdapter<String> adapter22 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, Objects.requireNonNull(recuperados.getStringArray("Lista De ArmDS")));
                                    spinnerObjeto.setAdapter(adapter22);
                                    mObjetos[0] = mObjetos[0].child("Armas/Armas a distancia simples");
                                    subtitle.setText(R.string.armasADistanciaSimples);
                                    auxiliar = 0;
                                    break;
                                case "Armas cuerpo cuerpo marciales":
                                    ArrayAdapter<String> adapter23 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, Objects.requireNonNull(recuperados.getStringArray("Lista De ArmCM")));
                                    spinnerObjeto.setAdapter(adapter23);
                                    mObjetos[0] = mObjetos[0].child("Armas/Armas cuerpo cuerpo marciales");
                                    subtitle.setText(R.string.armasCuerpoACuerpoMarciales);
                                    auxiliar = 0;
                                    break;
                                case "Armas cuerpo cuerpo simples":
                                    ArrayAdapter<String> adapter24 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, Objects.requireNonNull(recuperados.getStringArray("Lista De ArmCS")));
                                    spinnerObjeto.setAdapter(adapter24);
                                    mObjetos[0] = mObjetos[0].child("Armas/Armas cuerpo cuerpo simples");
                                    subtitle.setText(R.string.armasCuerpoACuerpoSimples);
                                    auxiliar = 0;
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
                        if (!spinnerObjeto.getSelectedItem().toString().equals("Ninguno")) {
                                cogerObjeto(mObjetos[0], spinnerObjeto.getSelectedItem().toString(), new MyCallback() {
                                    @Override
                                    public void onCallback(String[] value) {
                                        //Añade objeto al Recycle
                                        listaAtaque.add(new ItemAtaque(spinnerObjeto.getSelectedItem().toString(), Integer.parseInt(value[0]), Integer.parseInt(value[1]), value[2], value[3] ,value[4]));
                                        pesoTotal += Integer.parseInt(value[1]);
                                        adapter_ataque.notifyItemInserted(listaAtaque.size() - 1);
                                        mDialogCarga.dismiss();
                                    }
                                });
                        }
                        mDialogCarga.dismiss();
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
        listaAtaque = new ArrayList<ItemAtaque>();

        //Rellena el Recycler con los objetos

        ArrayList<String> aux = recuperados.getStringArrayList("Ataque");

        if(aux!=null){
            for(int i=0; i<aux.size()/6;i++){
                listaAtaque.add(new ItemAtaque(aux.get(i*6), Integer.parseInt(aux.get((i*6)+1)), Integer.parseInt(aux.get((i*6)+2)), aux.get((i*6)+3), aux.get((i*6)+4), aux.get((i*6)+5)));
                pesoTotal+=Integer.parseInt(aux.get((i*6)+2));
            }
        }

        //Añade los objetos equipados al Recycler
        adapter_ataque = new AdapterRecyclerAtaque(listaAtaque, this, getContext());
        recycler_ataque.setAdapter(adapter_ataque);

        return v;
    }

    @Override
    public void onItemClick(int position, String modo) {

        if(modo.equals("conjuro")){

            //Elimina el objeto del recycler conjuro
            listaConjuro.remove(position);
            adapter.notifyItemRemoved(position);

        }else if(modo.equals("ataque")){

            //Elimina el objeto del recycler ataque
            listaAtaque.remove(position);
            adapter_ataque.notifyItemRemoved(position);

        }

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
                    if (valor.equals(s)) {
                        String[] result;
                        String coste, peso, url, propiedades, danyo;
                        danyo = (String) ds.child("Daño").getValue();
                        propiedades = (String) ds.child("Propiedades").getValue();
                        try {
                            coste = (String) ds.child("Coste").getValue();
                        } catch (Exception e) {
                            try {
                                Long cost = (Long) ds.child("Coste").getValue();
                                coste = cost.toString();

                            } catch (Exception ex) {
                                coste = "0";
                            }
                        }
                        try {
                            peso = (String) ds.child("Peso").getValue();
                        } catch (Exception e) {
                            try {
                                Long cost = (Long) ds.child("Peso").getValue();
                                peso = cost.toString();
                            } catch (Exception ex) {
                                long cost = 0L;
                                peso = Long.toString(cost);
                            }
                        }
                        try {
                            url = (String) ds.child("URL").getValue();
                        } catch (Exception e) {
                            url = "..";
                        }
                        if (peso == null) {
                            peso = "0";
                        }

                        result = new String[]{coste,  peso, url, danyo, propiedades};
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

        //Guarda datos en FireBase al salir
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Ataque",listaAtaque);
        hashMap.put("Conjuro",listaConjuro);
        hashMap.put("Peso total",String.valueOf(pesoTotal));
        mDatabase.getReference("users/"+usuariActual.getUid()+"/"+codigoPJ).updateChildren(hashMap);

        HashMap<String, Object> ultimo = new HashMap<>();

        ultimo.put("Ultimo personaje",codigoPJ);
        mDatabase.getReference("users/"+usuariActual.getUid()).updateChildren(ultimo);
    }
}

