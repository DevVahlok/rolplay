package com.example.rolplay.Servicios;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rolplay.Adapters.AdapterRecyclerPersonaje;
import com.example.rolplay.Activities.ContenedorInicioActivity;
import com.example.rolplay.Otros.ItemPersonaje;
import com.example.rolplay.Activities.MainActivity;
import com.example.rolplay.Otros.MyCallback;
import com.example.rolplay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class MenuPersonajesActivity extends AppCompatActivity implements AdapterRecyclerPersonaje.OnItemListener {

    //Declaración de variables
    private ArrayList<ItemPersonaje> listaDatos;
    private ArrayList<String> lista = new ArrayList<>();
    private RecyclerView recycler;
    private TextView mNombrePersonaje, mJuegoPersonaje, mCodigoPersonaje;
    private ImageView mFotoPersonaje;
    private ArrayList listaCodigos = new ArrayList();
    private Button mBotonCrearPersonaje;
    private AdapterRecyclerPersonaje adapter;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private String codigoGenerado;
    private boolean recordarMenu = true;
    static final HashMap<String, Object> recordar = new HashMap<>();
    private boolean crearpj = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_personajes);
        mAuth = FirebaseAuth.getInstance();
        ComprobarEstatUsuari();

        //Inicialización de variables
        mNombrePersonaje = findViewById(R.id.ListaPersonajes_nombre);
        mJuegoPersonaje = findViewById(R.id.ListaPersonajes_juego);
        mCodigoPersonaje = findViewById(R.id.ListaPersonajes_codigo);
        mFotoPersonaje = findViewById(R.id.ListaPersonajes_foto);
        mBotonCrearPersonaje = findViewById(R.id.MenuPersonajes_crearPersonaje_btn);
        recycler = findViewById(R.id.MenuPersonajes_Recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mDatabase = FirebaseDatabase.getInstance();

        try {
            mDatabase.getReference("users/" + mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String valor = "" + ds.getKey();
                        if(!valor.equals("Recordar menu") && !valor.equals("Ultimo personaje") && !valor.equals("Sonido") && !valor.equals("Correo")) {
                            if (!listaCodigos.contains(valor)) {
                                listaCodigos.add(valor);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("ERROR", databaseError.getMessage());
                }
            });
        }catch (Exception e){

        }

            recordarMenu = true;
            RecordarMenu(recordar);


        //Al pulsar el botón de crear personaje
        mBotonCrearPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearpj=false;
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

                final EditText editText = new EditText(v.getContext());
                editText.setMinEms(20);
                linearLayout.addView(editText);

                linearLayout.addView(subtitle);
                linearLayout.setPadding(120, 10, 120, 10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.anadir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recordarMenu = false;
                        RecordarMenu(recordar);
                        codigoGenerado = generarCodigo();
                        if (listaCodigos.size()==0) {
                            listaCodigos.add(codigoGenerado);
                        }else {
                            boolean codigoLibre = false;
                            int aux;
                            while (!codigoLibre) {
                                aux = 0;
                                for (int i = 0; i < listaCodigos.size(); i++) {
                                    if (listaCodigos.get(i).equals(codigoGenerado)) {
                                        codigoGenerado = generarCodigo();
                                        aux++;
                                    }
                                    if (aux == 0) {
                                        codigoLibre = true;
                                    }
                                }

                            }

                            listaCodigos.add(codigoGenerado);
                        }
                        if (!(editText.getText().toString()).equals("")) {
                            mDatabase.getReference("users").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        for (DataSnapshot deese : ds.getChildren()) {
                                            if ((editText.getText().toString()).equals(deese.getKey())) {
                                                mDatabase.getReference("users/" + mAuth.getCurrentUser().getUid() + "/" + codigoGenerado).setValue(deese.getValue(), new DatabaseReference.CompletionListener()
                                                {
                                                    @Override
                                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                                        if (databaseError != null)
                                                        {
                                                            System.out.println("Copy failed");
                                                        }
                                                        else
                                                        {
                                                            HashMap<String, Object> ultimo = new HashMap<>();

                                                            ultimo.put("Ultimo personaje", codigoGenerado);
                                                            mDatabase.getReference("users/" +  mAuth.getCurrentUser().getUid()).updateChildren(ultimo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (!crearpj) {
                                                                        crearpj=true;
                                                                        startActivity(new Intent(MenuPersonajesActivity.this, ContenedorInicioActivity.class).putExtra("codigo", codigoGenerado).putExtra("origen", ""));
                                                                        MenuPersonajesActivity.this.finish();
                                                                    }
                                                                }
                                                            });
                                                        }

                                                    }
                                                });
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }else {
                            startActivity(new Intent(MenuPersonajesActivity.this, ContenedorInicioActivity.class).putExtra("codigo", codigoGenerado).putExtra("origen", "seleccionPersonaje"));
                            MenuPersonajesActivity.this.finish();
                        }
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
        cogerPersonaje(mDatabase.getReference("users/" + mAuth.getCurrentUser().getUid()), lista, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaDatos = new ArrayList<ItemPersonaje>();
                for (int i = 0; i<listaCodigos.size(); i++) {
                    listaDatos.add(new ItemPersonaje(lista.get((i*3)+1), getString(R.string.dungeonsAndDragons), lista.get(i*3), lista.get((i*3)+2)));
                }
                //Añade los personajes al Recycler
                adapter = new AdapterRecyclerPersonaje(listaDatos, MenuPersonajesActivity.this, MenuPersonajesActivity.this);
                recycler.setAdapter(adapter);
            }
        });
    }

    private void RecordarMenu(HashMap<String, Object> recordar) {
        recordar.put("Recordar menu", recordarMenu);
        mDatabase.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).updateChildren(recordar);
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


    private void cogerPersonaje(DatabaseReference mDatabase, final ArrayList<String> ALS, final MyCallback callback) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] result = new String[0];
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String valor = "" + ds.getKey();
                    if(!valor.equals("Recordar menu") && !valor.equals("Ultimo personaje") && !valor.equals("Sonido") && !valor.equals("Correo")) {
                        if (!ALS.contains(valor)) {
                            ALS.add(valor);
                            ALS.add((String) ds.child("Nombre").getValue());
                            ALS.add((String) ds.child("Foto").getValue());
                            result = ALS.toArray(result);
                        }
                    }
                }
                callback.onCallback(result);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void borrar(final int position) {
        //Elimina el objeto del recycler
        (mDatabase.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/" + listaDatos.get(position).getCodigo()).removeValue()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                listaDatos.remove(position);
                adapter.notifyItemRemoved(position);
                HashMap<String, Object> ultimo = new HashMap<>();

                ultimo.put("Ultimo personaje","");
                mDatabase.getReference("users/"+mAuth.getCurrentUser().getUid()).updateChildren(ultimo);
            }
        });
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

    private void ComprobarEstatUsuari() {

        FirebaseUser usuari = mAuth.getCurrentUser();

        if (usuari==null){
            //Pasa a la pantalla de Login si el usuario no está logueado
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }
    }
}