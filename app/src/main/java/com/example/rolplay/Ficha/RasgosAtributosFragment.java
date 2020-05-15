package com.example.rolplay.Ficha;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rolplay.Activities.ContenedorInicioActivity;
import com.example.rolplay.Adapters.AdapterRecyclerRasgosAtributos;
import com.example.rolplay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RasgosAtributosFragment extends Fragment implements AdapterRecyclerRasgosAtributos.OnItemListener {

    //Declaración de variables
    private ArrayList<String> listaRasgosAtributos;
    private RecyclerView recycler;
    private Button mBotonAnadirObjeto;
    private TextView valorTexto;
    private AdapterRecyclerRasgosAtributos adapter;
    private View v;
    private FirebaseDatabase mDatabase;
    private String codigoPJ;

    //Constructor
    public RasgosAtributosFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_rasgos_atributos, container, false);

        final Bundle recuperados = getArguments();
        codigoPJ = recuperados.getString("codigo");

        //Inicialización de variables
        recycler = v.findViewById(R.id.RasgosAtributos_Recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mBotonAnadirObjeto = v.findViewById(R.id.RasgosAtributos_anadirObjeto_btn);
        valorTexto = v.findViewById(R.id.RasgosAtributos_valor);
        listaRasgosAtributos = recuperados.getStringArrayList("Rasgos y Atributos");

        //Limitación de los Rasgos / Atributos a 84 carácteres ~

        adapter = new AdapterRecyclerRasgosAtributos(listaRasgosAtributos, this, getContext());
        recycler.setAdapter(adapter);

        //Al pulsar el botón de añadir objeto
        mBotonAnadirObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Muestra un dialog para que el usuario selecciona cuál quiere añadir
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                TextView title = new TextView(getActivity());
                title.setText(getString(R.string.anadirRasgoAtributo));
                title.setTextColor(getActivity().getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0, 40, 0, 0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getActivity());

                final EditText editText = new EditText(getActivity());
                editText.setMinEms(20);


                linearLayout.addView(editText);
                linearLayout.setPadding(120, 10, 120, 10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.anadir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Añade objeto al Recycle
                        listaRasgosAtributos.add(editText.getText().toString().trim());
                        adapter.notifyItemInserted(listaRasgosAtributos.size() - 1);
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

        return v;
    }

    @Override
    public void onItemClick(int position) {

        //Elimina el objeto del recycler
        listaRasgosAtributos.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onPause() {
        super.onPause();
        mDatabase = FirebaseDatabase.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuariActual = mAuth.getCurrentUser();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Rasgos", listaRasgosAtributos);
        mDatabase.getReference("users/" + usuariActual.getUid() + "/" + codigoPJ).updateChildren(hashMap);
        HashMap<String, Object> ultimo = new HashMap<>();

        ultimo.put("Ultimo personaje", codigoPJ);
        mDatabase.getReference("users/" + usuariActual.getUid()).updateChildren(ultimo);

        ((ContenedorInicioActivity) getActivity()).cargarDatosFB();
    }
}
