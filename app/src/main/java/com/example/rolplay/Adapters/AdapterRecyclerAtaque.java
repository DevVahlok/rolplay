package com.example.rolplay.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolplay.Otros.ItemAtaque;
import com.example.rolplay.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterRecyclerAtaque extends RecyclerView.Adapter<AdapterRecyclerAtaque.ViewHolderAtaque> {

    //Declaración de variables
    private ArrayList<ItemAtaque> listaAtaque;
    private ImageView imgViewRemoveIcon_ataque;
    private AdapterRecyclerAtaque.OnItemListener mOnItemListener;
    private Context context;
    private int lastSelectedPosition = -1;

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

        holder.radio.setChecked(position == lastSelectedPosition);

        listaAtaque.get(position).setCheckbox(String.valueOf(lastSelectedPosition == position));
    }

    @Override
    public int getItemCount() {

        //Devuelve el tamaño del ArrayList
        return listaAtaque.size();

    }

    public class ViewHolderAtaque extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Declaración de variables
        private TextView mNombreAtaque, mCosteAtaque, mPesoAtaque, mDanyoAtaque, mPropiedadesAtaque;
        private CheckBox mCheckbox;
        private ImageView mFotoAtaque;
        private AdapterRecyclerAtaque.OnItemListener onItemListener;
        private CheckBox radio;

        public ViewHolderAtaque(@NonNull View itemView, final AdapterRecyclerAtaque.OnItemListener onItemListener) {

            //Inicialización de variables
            super(itemView);
            this.onItemListener = onItemListener;
            mNombreAtaque = itemView.findViewById(R.id.ListaAtaque_nombre);
            mCosteAtaque = itemView.findViewById(R.id.ListaAtaque_coste);
            mPesoAtaque = itemView.findViewById(R.id.ListaAtaque_peso);
            mFotoAtaque = itemView.findViewById(R.id.ListaAtaque_foto);
            mDanyoAtaque = itemView.findViewById(R.id.ListaAtaque_danyo);
            mPropiedadesAtaque = itemView.findViewById(R.id.ListaAtaque_propiedades);
            mCheckbox = itemView.findViewById(R.id.ListaAtaque_equipado);
            itemView.setOnClickListener(this);
            imgViewRemoveIcon_ataque = itemView.findViewById(R.id.ListaAtaque_borrar);

            radio = itemView.findViewById(R.id.ListaAtaque_equipado);

            //Al hacer click en la imagen de la X
            imgViewRemoveIcon_ataque.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Elimina el objeto del recycler
                    onItemListener.onItemClick(getAdapterPosition(), "ataque");

                }
            });

            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCheckbox.isChecked()) {
                        lastSelectedPosition = getAdapterPosition();
                    }
                    (listaAtaque.get(getAdapterPosition())).setCheckbox(String.valueOf(mCheckbox.isChecked()));

                    notifyDataSetChanged();
                }
            });
        }

        public void asignarDatos(ItemAtaque s) {

            //Seteo de datos de cada objeto
            mNombreAtaque.setText(s.getNombre());
            mCosteAtaque.setText(context.getResources().getString(R.string.costeEquipo, Integer.toString(s.getCoste())));
            mPesoAtaque.setText(context.getResources().getString(R.string.pesoEquipo, Integer.toString(s.getPeso())));
            mDanyoAtaque.setText(context.getResources().getString(R.string.danyoAtaque, s.getDanyo()));
            mPropiedadesAtaque.setText(context.getResources().getString(R.string.propiedadesAtaque, s.getPropiedades()));
            Picasso.get().load(Uri.parse(s.getUrl())).into(mFotoAtaque);
            if (s.getCheckbox().equals("true")) {
                mCheckbox.setChecked(true);
            } else if (s.getCheckbox().equals("false")) {
                mCheckbox.setChecked(false);
            } else {
                mCheckbox.setEnabled(false);
            }

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
