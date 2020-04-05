package com.example.rolplay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

        private TextView dato;
        private AdapterRecyclerRasgosAtributos.OnItemListener onItemListener;

        public ViewHolderRasgosAtributos(@NonNull View itemView, final AdapterRecyclerRasgosAtributos.OnItemListener onItemListener) {
            super(itemView);
            this.onItemListener = onItemListener;

            dato = itemView.findViewById(R.id.RasgosAtributos_valor);

            itemView.setOnClickListener(this);
            imgViewRemoveIcon = itemView.findViewById(R.id.RasgosAtributos_borrar);

            //Al hacer click en la imagen de la X
            imgViewRemoveIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemListener.onItemClick(getAdapterPosition());

                    //Elimina el objeto del recycler
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        removeAt(getAdapterPosition());
                    }

                }
            });

        }

        public void asignarDatos(String s) {
            //Seteo de datos de cada objeto
            dato.setText(s);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }

    public void removeAt(int position) {

        //Elimina el objeto del recycler
        listaRasgosAtributos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaRasgosAtributos.size());

    }

}
