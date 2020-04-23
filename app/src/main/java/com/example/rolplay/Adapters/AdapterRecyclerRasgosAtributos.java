package com.example.rolplay.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolplay.R;

import java.util.ArrayList;

public class AdapterRecyclerRasgosAtributos extends RecyclerView.Adapter<AdapterRecyclerRasgosAtributos.ViewHolderRasgosAtributos> {

    //Declaración de variables
    private ArrayList<String> listaRasgosAtributos;
    private ImageView imgViewRemoveIcon;
    private AdapterRecyclerRasgosAtributos.OnItemListener mOnItemListener;
    private Context context;

    //Constructor
    public AdapterRecyclerRasgosAtributos(ArrayList<String> listaRasgosAtributos, OnItemListener mOnItemListener, Context context) {
        this.listaRasgosAtributos = listaRasgosAtributos;
        this.mOnItemListener = mOnItemListener;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRecyclerRasgosAtributos.ViewHolderRasgosAtributos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Enlaza AdapterRecyclerRasgosAtributos con item_list_rasgos_atributos.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_rasgos_atributos, null, false);

        return new ViewHolderRasgosAtributos(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerRasgosAtributos.ViewHolderRasgosAtributos holder, int position) {

        //Comunica AdapterRecyclerRasgosAtributos con el ViewHolderRasgosAtributos
        holder.asignarDatos(listaRasgosAtributos.get(position));

    }

    @Override
    public int getItemCount() {

        //Devuelve el tamaño del ArrayList
        return listaRasgosAtributos.size();

    }

    public class ViewHolderRasgosAtributos extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Declaración de variables
        private TextView dato;
        private AdapterRecyclerRasgosAtributos.OnItemListener onItemListener;

        ViewHolderRasgosAtributos(@NonNull View itemView, final AdapterRecyclerRasgosAtributos.OnItemListener onItemListener) {

            //Inicialización de variables
            super(itemView);
            this.onItemListener = onItemListener;
            dato = itemView.findViewById(R.id.RasgosAtributos_valor);
            itemView.setOnClickListener(this);
            imgViewRemoveIcon = itemView.findViewById(R.id.RasgosAtributos_borrar);

            //Al hacer click en la imagen de la X
            imgViewRemoveIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Elimina el objeto del recycler
                    onItemListener.onItemClick(getAdapterPosition());

                }
            });

        }

        public void asignarDatos(String s) {
            //Seteo de datos de cada objeto
            dato.setText(s);
        }

        @Override
        public void onClick(View v) { }
    }

    //Interfaz para crear un OnClickListener en la foto de la X
    public interface OnItemListener{
        void onItemClick(int position);
    }

}
