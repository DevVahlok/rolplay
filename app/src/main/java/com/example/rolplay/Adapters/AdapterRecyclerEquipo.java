package com.example.rolplay.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolplay.Otros.ItemEquipo;
import com.example.rolplay.Otros.ItemMontura;
import com.example.rolplay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRecyclerEquipo extends RecyclerView.Adapter<AdapterRecyclerEquipo.ViewHolderEquipo> {

    //Declaración de variables
    private ArrayList<Object> listaDatos;
    private ImageView imgViewRemoveIcon;
    private OnItemListener mOnItemListener;
    private Context context;

    //Constructor
    public AdapterRecyclerEquipo(ArrayList<Object> listaDatos, OnItemListener mOnItemListener, Context context) {
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

            //Inicialización de variables
            super(itemView);
            this.onItemListener = onItemListener;
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

                    //Elimina el objeto del recycler
                    onItemListener.onItemClick(getAdapterPosition());

                }
            });
        }

        @SuppressLint("StringFormatInvalid")
        public void asignarDatos(Object o) {

            try {
                ItemEquipo s = (ItemEquipo) o;
                //Seteo de datos de cada objeto
                mNombreEquipo.setText(s.getNombre());
                mCosteEquipo.setText(context.getResources().getString(R.string.costeEquipo, Integer.toString(s.getCoste())));
                mPesoEquipo.setText(context.getResources().getString(R.string.pesoEquipo, Integer.toString(s.getPeso())));
                Picasso.get().load(Uri.parse(s.getUrl())).into(mFotoEquipo);
            } catch (Exception e) {
                ItemMontura s = (ItemMontura) o;
                mNombreEquipo.setText(s.getNombre());
                mCosteEquipo.setText(context.getResources().getString(R.string.costeEquipo, Integer.toString(s.getCoste())));
                mPesoEquipo.setText(context.getResources().getString(R.string.velocidadDosPuntos, Float.toString(s.getVelocidad())));

                Picasso.get().load(Uri.parse(s.getUrl())).into(mFotoEquipo);
            }
        }

        @Override
        public void onClick(View v) {
        }

    }

    //Interfaz para crear un OnClickListener en la foto de la X
    public interface OnItemListener {
        void onItemClick(int position);
    }

}