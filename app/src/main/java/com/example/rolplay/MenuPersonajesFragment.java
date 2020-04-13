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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class MenuPersonajesFragment extends Fragment implements AdapterRecyclerPersonaje.OnItemListener {

    //Declaración de variables
    private ArrayList<ItemPersonaje> listaDatos;
    private RecyclerView recycler;
    private TextView mNombrePersonaje, mJuegoPersonaje, mCodigoPersonaje;
    private ImageView mFotoPersonaje;
    private Button mBotonCrearPersonaje;
    private AdapterRecyclerPersonaje adapter;
    private View v;

    //Constructor
    public MenuPersonajesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_menu_personajes, container, false);

        //Inicialización de variables
        mNombrePersonaje = v.findViewById(R.id.ListaPersonajes_nombre);
        mJuegoPersonaje = v.findViewById(R.id.ListaPersonajes_juego);
        mCodigoPersonaje = v.findViewById(R.id.ListaPersonajes_codigo);
        mFotoPersonaje = v.findViewById(R.id.ListaPersonajes_foto);
        mBotonCrearPersonaje = v.findViewById(R.id.MenuPersonajes_crearPersonaje_btn);
        recycler = v.findViewById(R.id.MenuPersonajes_Recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        //Al pulsar el botón de crear personaje
        mBotonCrearPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Muestra un dialog para que el usuario selecciona cuál quiere añadir
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                TextView title = new TextView(getActivity());
                title.setText(getString(R.string.crearPersonaje));
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

                linearLayout.addView(subtitle);
                linearLayout.setPadding(120,10,120,10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.anadir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Añadir personaje a Firebase y pasar a Ficha
                    }
                });

                constructrorDialog.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //Enseña el dialog de 'Añadir objeto'
                AlertDialog anadirPersonaje = constructrorDialog.create();
                anadirPersonaje.show();
                Objects.requireNonNull(anadirPersonaje.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));

            }
        });

        //Placeholder
        listaDatos = new ArrayList<ItemPersonaje>();
        listaDatos.add(new ItemPersonaje("Vahlok", getString(R.string.dungeonsAndDragons),getString(R.string.codigo,"ABC123")));

        //Añade los personajes al Recycler
        adapter = new AdapterRecyclerPersonaje(listaDatos, this, getContext());
        recycler.setAdapter(adapter);

        return v;
    }

    @Override
    public void onItemClick(int position) {

        //Elimina el objeto del recycler
        listaDatos.remove(position);
        adapter.notifyItemRemoved(position);

    }
}
