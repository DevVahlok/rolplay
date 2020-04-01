package com.example.rolplay;


import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Objects;

public class EquipoFragment extends Fragment implements AdapterRecyclerEquipo.OnItemListener {

    //Declaración de variables
    private ArrayList<ItemEquipo> listaDatos;
    private RecyclerView recycler;
    private TextView mNombreEquipo, mCosteEquipo, mPesoEquipo;
    private ImageView mFotoEquipo;
    private Button mBotonAnadirObjeto;
    private AdapterRecyclerEquipo adapter;
    private String[] listaObjetos = new String[] {};

    //Constructor
    public EquipoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_equipo, container, false);

        //Inicialización de varaibles
        recycler = v.findViewById(R.id.Equipo_Recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mNombreEquipo = v.findViewById(R.id.listaEquipo_nombre);
        mCosteEquipo = v.findViewById(R.id.listaEquipo_coste);
        mPesoEquipo = v.findViewById(R.id.listaEquipo_peso);
        mBotonAnadirObjeto = v.findViewById(R.id.Equipo_anadirObjeto_btn);

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

                final Spinner spinnerObjeto = new Spinner(getActivity());

                //Añade la lista de objetos al spinner
                //TODO: Recoger lista de objetos de Firebase
                listaObjetos = new String[]{"Botella", "Palo", "Mochila"};
                creadorAdapter(listaObjetos,spinnerObjeto,"Objetos");


                linearLayout.addView(spinnerObjeto);
                linearLayout.setPadding(120,10,120,10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.anadir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Añade objeto al Recycle
                        listaDatos.add(new ItemEquipo(spinnerObjeto.getSelectedItem().toString(),0,0,".."));
                        adapter.notifyItemInserted(listaDatos.size() - 1);
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

        listaDatos = new ArrayList<ItemEquipo>();

        //TODO: Recuperar objetos de la ficha de Firebase
        //Rellena el Recycler con los objetos
        for (int i = 0; i < 3; i++) {
            listaDatos.add(new ItemEquipo("Objeto: "+i,i*3,i+7,"hey"));
        }

        //TODO: Guardar peso total de los items en una variable y establecerlo en Firebase

        //Añade los objetos equipados al Recycler
        adapter = new AdapterRecyclerEquipo(listaDatos, this, getContext());
        recycler.setAdapter(adapter);

        return v;
    }


    @Override
    public void onItemClick(int position) {

        //Elimina el objeto del recycler
        listaDatos.remove(position);
        adapter.notifyItemRemoved(position);
    }

    private void creadorAdapter(String[] lista, Spinner dropdown, String s) {

        //Crea un adapter para el dialog
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, lista);
        dropdown.setAdapter(adapter);
        int aux = adapter.getPosition(s);
        dropdown.setSelection(aux);

    }
}

