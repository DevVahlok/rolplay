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

public class AdapterRecyclerAtaque extends RecyclerView.Adapter<AdapterRecyclerAtaque.ViewHolderAtaque> {

    //Declaración de variables
    private ArrayList<ItemAtaque> listaAtaque;
    private ImageView imgViewRemoveIcon_ataque;
    private AdapterRecyclerAtaque.OnItemListener mOnItemListener;
    private Context context;

    //Constructor
    public AdapterRecyclerAtaque(ArrayList<ItemAtaque> listaAtaque, AdapterRecyclerAtaque.OnItemListener mOnItemListener, Context context) {
        this.listaAtaque = listaAtaque;
        this.mOnItemListener = mOnItemListener;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRecyclerAtaque.ViewHolderAtaque onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Enlaza AdapterRecyclerAtaque con item_list_ataque.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ataque, null, false);

        return new AdapterRecyclerAtaque.ViewHolderAtaque(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAtaque holder, int position) {

        //Comunica AdapterRecyclerAtaque con el ViewHolderAtaque
        holder.asignarDatos(listaAtaque.get(position));
    }

    @Override
    public int getItemCount() {

        //Devuelve el tamaño del ArrayList
        return listaAtaque.size();

    }

    public class ViewHolderAtaque extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Declaración de variables
        private TextView mNombreAtaque, mCosteAtaque, mPesoAtaque, mDanyoAtaque, mPropiedadesAtaque;
        private ImageView mFotoAtaque;
        private AdapterRecyclerAtaque.OnItemListener onItemListener;

        public ViewHolderAtaque(@NonNull View itemView, final AdapterRecyclerAtaque.OnItemListener onItemListener) {
            super(itemView);

            this.onItemListener = onItemListener;

            mNombreAtaque = itemView.findViewById(R.id.ListaAtaque_nombre);
            mCosteAtaque = itemView.findViewById(R.id.ListaAtaque_coste);
            mPesoAtaque = itemView.findViewById(R.id.ListaAtaque_peso);
            mFotoAtaque = itemView.findViewById(R.id.ListaAtaque_foto);
            mDanyoAtaque = itemView.findViewById(R.id.ListaAtaque_danyo);
            mPropiedadesAtaque = itemView.findViewById(R.id.ListaAtaque_propiedades);

            itemView.setOnClickListener(this);
            imgViewRemoveIcon_ataque = itemView.findViewById(R.id.ListaAtaque_borrar);

            //Al hacer click en la imagen de la X
            imgViewRemoveIcon_ataque.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemListener.onItemClick(getAdapterPosition(),"ataque");

                    //Elimina el objeto del recycler
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        removeAt(getAdapterPosition());
                    }

                }
            });
        }

        public void asignarDatos(ItemAtaque s) {

            //Seteo de datos de cada objeto
            mNombreAtaque.setText(s.getNombre());
            mCosteAtaque.setText(context.getResources().getString(R.string.costeEquipo,Integer.toString(s.getCoste())));
            mPesoAtaque.setText(context.getResources().getString(R.string.pesoEquipo,Integer.toString(s.getPeso())));
            mDanyoAtaque.setText(context.getResources().getString(R.string.danyoAtaque,Integer.toString(s.getCoste())));
            mPropiedadesAtaque.setText(context.getResources().getString(R.string.propiedadesAtaque,Integer.toString(s.getPeso())));
            //TODO: Añadir imágenes de Storage de Firebase
            //mFotoAtaque.setImageResource(s.getUrl());

        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface OnItemListener {
        void onItemClick(int position, String modo);
    }

    public void removeAt(int position) {

        //Elimina el objeto del recycler
        listaAtaque.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaAtaque.size());

    }

}
