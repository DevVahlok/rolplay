package com.example.rolplay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class MenuPersonajesActivity extends AppCompatActivity implements AdapterRecyclerPersonaje.OnItemListener {

    //Declaración de variables
    private ArrayList<ItemPersonaje> listaDatos;
    private RecyclerView recycler;
    private TextView mNombrePersonaje, mJuegoPersonaje, mCodigoPersonaje;
    private ImageView mFotoPersonaje;
    private Button mBotonCrearPersonaje;
    private AdapterRecyclerPersonaje adapter;
    static boolean recordarMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_personajes);

        recordarMenu = true;

        //Inicialización de variables
        mNombrePersonaje = findViewById(R.id.ListaPersonajes_nombre);
        mJuegoPersonaje = findViewById(R.id.ListaPersonajes_juego);
        mCodigoPersonaje = findViewById(R.id.ListaPersonajes_codigo);
        mFotoPersonaje = findViewById(R.id.ListaPersonajes_foto);
        mBotonCrearPersonaje = findViewById(R.id.MenuPersonajes_crearPersonaje_btn);
        recycler = findViewById(R.id.MenuPersonajes_Recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Al pulsar el botón de crear personaje
        mBotonCrearPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Muestra un dialog para que el usuario selecciona cuál quiere añadir
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(v.getContext()));

                TextView title = new TextView(v.getContext());
                title.setText(getString(R.string.crearPersonaje));
                title.setTextColor(v.getContext().getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0, 40, 0, 0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(v.getContext());

                final TextView subtitle = new TextView(v.getContext());
                subtitle.setTextColor(v.getContext().getColor(R.color.colorPrimary));
                subtitle.setTextSize(16);
                subtitle.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                subtitle.setPadding(10, 10, 10, 0);

                linearLayout.addView(subtitle);
                linearLayout.setPadding(120, 10, 120, 10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.anadir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Añadir personaje y su código a Firebase
                        recordarMenu = false;

                        //TODO: Raúl: Guardar lista de códigos al crear personaje en FireBase. Recuperarla y usar el algoritmo de abajo para comprobar que no existe al generar uno nuevo.
                        String codigoGenerado = generarCodigo();
                        ArrayList listaCodigos = new ArrayList();
                        boolean codigoLibre = false;

                        while (!codigoLibre) {

                            for (int i = 0; i < listaCodigos.size(); i++) {
                                if (listaCodigos.get(i).equals(codigoGenerado)) {
                                    codigoGenerado = generarCodigo();
                                } else {
                                    codigoLibre = true;
                                }
                            }

                        }
                        startActivity(new Intent(MenuPersonajesActivity.this, ContenedorInicioActivity.class).putExtra("codigo", codigoGenerado).putExtra("origen", "seleccionPersonaje"));
                        MenuPersonajesActivity.this.finish();

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
        listaDatos.add(new ItemPersonaje("Vahlok", getString(R.string.dungeonsAndDragons), getString(R.string.codigo, "ABC123")));

        //Añade los personajes al Recycler
        adapter = new AdapterRecyclerPersonaje(listaDatos, this, this);
        recycler.setAdapter(adapter);

    }

    @Override
    public void onItemClick(final int position) {

        //Muestra un dialog para que el usuario selecciona cuál quiere añadir
        AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(this));

        TextView title = new TextView(this);
        title.setText(getString(R.string.estasSegurxQuieresEliminarPersonaje));
        title.setTextColor(this.getColor(R.color.colorPrimary));
        title.setTextSize(20);
        title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setPadding(0, 40, 0, 0);

        constructrorDialog.setCustomTitle(title);

        LinearLayout linearLayout = new LinearLayout(this);

        final TextView subtitle = new TextView(this);
        subtitle.setTextColor(this.getColor(R.color.colorPrimary));
        subtitle.setTextSize(16);
        subtitle.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
        subtitle.setPadding(10, 10, 10, 0);

        linearLayout.addView(subtitle);
        linearLayout.setPadding(120, 10, 120, 10);

        constructrorDialog.setView(linearLayout);

        //Botón de añadir
        constructrorDialog.setPositiveButton(getString(R.string.eliminar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borrar(position);
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

    private void borrar(int position) {
        //Elimina el objeto del recycler
        listaDatos.remove(position);
        adapter.notifyItemRemoved(position);
    }


    //Genera un código aleatorio de 6 dígitos
    private String generarCodigo() {

        //Carácteres
        String stringAlfanumerico = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789";

        //Se crea un espacio en búfer de 6 carácteres
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {

            //Genera un carácter random
            int index = (int) (stringAlfanumerico.length() * Math.random());

            //Lo pone al final del string
            sb.append(stringAlfanumerico.charAt(index));
        }

        return sb.toString();
    }

}