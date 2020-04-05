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

public class AdapterRecyclerEquipo extends RecyclerView.Adapter<AdapterRecyclerEquipo.ViewHolderEquipo> implements View.OnClickListener{

    //Declaración de variables
    private ArrayList<ItemEquipo> listaDatos;
    private ImageView imgViewRemoveIcon;
    private OnItemListener mOnItemListener;
    private Context context;

    //Constructor
    public AdapterRecyclerEquipo(ArrayList<ItemEquipo> listaDatos, OnItemListener mOnItemListener, Context context) {
        this.listaDatos = listaDatos;
        this.mOnItemListener = mOnItemListener;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolderEquipo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Enlaza AdapterRecyclerEquipo con item_list_equipo.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_equipo, null, false);

        return new ViewHolderEquipo(view, mOnItemListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEquipo holder, int position) {

        //Comunica AdapterRecyclerEquipo con el ViewHolderEquipo
        holder.asignarDatos(listaDatos.get(position));

    }

    @Override
    public void onClick(View v) {}

    @Override
    public int getItemCount() {

        //Devuelve el tamaño del ArrayList
        return listaDatos.size();

    }

    public class ViewHolderEquipo extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Declaración de variables
        private TextView mNombreEquipo, mCosteEquipo, mPesoEquipo;
        private ImageView mFotoEquipo;
        private OnItemListener onItemListener;

        public ViewHolderEquipo(@NonNull View itemView, final OnItemListener onItemListener) {
            super(itemView);
            this.onItemListener = onItemListener;

            //Inicialización de variables
            mNombreEquipo = itemView.findViewById(R.id.listaEquipo_nombre);
            mCosteEquipo = itemView.findViewById(R.id.listaEquipo_coste);
            mPesoEquipo = itemView.findViewById(R.id.listaEquipo_peso);
            mFotoEquipo = itemView.findViewById(R.id.ListaEquipo_foto);
            itemView.setOnClickListener(this);
            imgViewRemoveIcon = itemView.findViewById(R.id.ListaEquipo_borrar);

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

        public void asignarDatos(ItemEquipo s) {

            //Seteo de datos de cada objeto
            mNombreEquipo.setText(s.getNombre());
            mCosteEquipo.setText(context.getResources().getString(R.string.costeEquipo,Integer.toString(s.getCoste())));
            mPesoEquipo.setText(context.getResources().getString(R.string.pesoEquipo,Integer.toString(s.getPeso())));
            //TODO: Añadir imágenes de Storage de Firebase
            //mFotoEquipo.setImageResource(s.getUrl());

        }

        @Override
        public void onClick(View v) {}

    }

    public interface OnItemListener{
        void onItemClick(int position);
    }

    public void removeAt(int position) {

        //Elimina el objeto del recycler
        listaDatos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaDatos.size());

    }

}
//TODO: Alex: Cuando esté hecho el backend, eliminar los warnings del XML seteando los textos vacíos
