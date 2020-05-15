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

public class AdapterRecyclerConjuro extends RecyclerView.Adapter<AdapterRecyclerConjuro.ViewHolderConjuro> {

    //Declaración de variables
    private ArrayList<String> listaConjuro;
    private ImageView imgViewRemoveIcon;
    private AdapterRecyclerConjuro.OnItemListener mOnItemListener;
    private Context context;

    //Constructor
    public AdapterRecyclerConjuro(ArrayList<String> listaConjuro, AdapterRecyclerConjuro.OnItemListener mOnItemListener, Context context) {
        this.listaConjuro = listaConjuro;
        this.mOnItemListener = mOnItemListener;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterRecyclerConjuro.ViewHolderConjuro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Enlaza AdapterRecyclerConjuro con item_list_conjuro.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_conjuro, null, false);

        return new AdapterRecyclerConjuro.ViewHolderConjuro(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerConjuro.ViewHolderConjuro holder, int position) {

        //Comunica AdapterRecyclerConjuro con el ViewHolderConjuro
        holder.asignarDatos(listaConjuro.get(position));
    }

    @Override
    public int getItemCount() {

        //Devuelve el tamaño del ArrayList
        return listaConjuro.size();

    }

    public class ViewHolderConjuro extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Declaración de variables
        private TextView dato;
        private AdapterRecyclerConjuro.OnItemListener onItemListener;

        //Constructor
        ViewHolderConjuro(@NonNull View itemView, final AdapterRecyclerConjuro.OnItemListener onItemListener) {

            //Inicialización de variables
            super(itemView);
            this.onItemListener = onItemListener;
            dato = itemView.findViewById(R.id.Conjuro_valor);
            itemView.setOnClickListener(this);
            imgViewRemoveIcon = itemView.findViewById(R.id.Conjuro_borrar);

            //Al hacer click en la imagen de la X
            imgViewRemoveIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Elimina el objeto del recycler
                    onItemListener.onItemClick(getAdapterPosition(), "conjuro");

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

    //Interfaz para crear un OnClickListener en la foto de la X
    public interface OnItemListener {
        void onItemClick(int position, String modo);
    }

}
