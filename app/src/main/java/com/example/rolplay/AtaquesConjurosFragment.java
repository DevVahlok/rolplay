package com.example.rolplay;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
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

    //Constructor
    public AtaquesConjurosFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ataques_conjuros, container, false);

        //Inicialización de variables - Conjuro
        recycler = v.findViewById(R.id.AtaquesConjuros_Recycler_conjuros);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        mBotonAnadirObjeto = v.findViewById(R.id.AtaquesConjuros_anadirConjuro_btn);
        valorTexto = v.findViewById(R.id.Conjuro_valor);
        listaConjuro = new ArrayList<String>();

        //Inicialización de variables - Ataque
        mBotonAnadirObjeto_ataque = v.findViewById(R.id.AtaquesConjuros_anadirAtaque_btn);
        recycler_ataque = v.findViewById(R.id.AtaquesConjuros_Recycler_ataque);
        recycler_ataque.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));

        //Limitación de los conjuros a 84 carácteres ~

        for (int i=0;i<3;i++){
            listaConjuro.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse eu lacus neque. Duis tempor libero vitae facilisis pulvinar. Duis congue, dolor vitae blandit imperdiet, nulla libero tempus libero, at dignissim lectus turpis ac nisi. Sed et mauris quam. Nam fringilla malesuada ante. Ut vitae tristique arcu, ac tincidunt leo."+i);
        }

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
                title.setPadding(0,40,0,0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getActivity());

                final Spinner spinnerObjeto = new Spinner(getActivity());

                //Añade la lista de objetos al spinner
                //TODO: Recoger lista de objetos de Firebase
                listaAtaquesPlaceholder = new String[]{"Cuchillo", "Espada", "Arco"};
                creadorAdapter(listaAtaquesPlaceholder,spinnerObjeto,"Objetos");


                linearLayout.addView(spinnerObjeto);
                linearLayout.setPadding(120,10,120,10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.anadir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Añade objeto al Recycle
                        listaAtaque.add(new ItemAtaque(spinnerObjeto.getSelectedItem().toString(),0,0,"..","1d6 Cortante","Ligera, sutil"));
                        adapter_ataque.notifyItemInserted(listaAtaque.size() - 1);
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

        //TODO: Recuperar objetos de la ficha de Firebase
        //Rellena el Recycler con los objetos
        for (int i = 0; i < 3; i++) {
            listaAtaque.add(new ItemAtaque("Ataque: "+i,i*3,i+7,"hey","1d6 Perforante","Sutil"));
        }

        //TODO: Guardar peso total de los items en una variable y establecerlo en Firebase

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

    private void creadorAdapter(String[] lista, Spinner dropdown, String s) {

        //Crea un adapter para el dialog
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_oscuro, lista);
        dropdown.setAdapter(adapter);
        int aux = adapter.getPosition(s);
        dropdown.setSelection(aux);

    }

}
