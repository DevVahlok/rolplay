package com.example.rolplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.rolplay.MenuPersonajesActivity.recordarMenu;

public class AdapterRecyclerPersonaje extends RecyclerView.Adapter<AdapterRecyclerPersonaje.ViewHolderPersonaje> implements View.OnClickListener{

    //Declaración de variables
    private ArrayList<ItemPersonaje> listaDatos;
    private ImageView imgViewRemoveIcon, mFondoElemento;
    private OnItemListener mOnItemListener;
    private Context context;

    //Constructor
    public AdapterRecyclerPersonaje(ArrayList<ItemPersonaje> listaDatos, OnItemListener mOnItemListener, Context context) {
        this.listaDatos = listaDatos;
        this.mOnItemListener = mOnItemListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderPersonaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Enlaza AdapterRecyclerPersonaje con item_list_personaje.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_personaje, null, false);

        return new ViewHolderPersonaje(view, mOnItemListener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPersonaje holder, int position) {

        //Comunica AdapterRecyclerPersonaje con el ViewHolderPersonaje
        holder.asignarDatos(listaDatos.get(position));

    }

    @Override
    public void onClick(View v) {}

    @Override
    public int getItemCount() {

        //Devuelve el tamaño del ArrayList
        return listaDatos.size();

    }

    public class ViewHolderPersonaje extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Declaración de variables
        private TextView mNombrePersonaje, mJuegoPersonaje, mCodigoPersonaje;
        private ImageView mFotoPersonaje;
        private OnItemListener onItemListener;

        public ViewHolderPersonaje(@NonNull final View itemView, final OnItemListener onItemListener) {
            super(itemView);
            this.onItemListener = onItemListener;

            //Inicialización de variables
            mNombrePersonaje = itemView.findViewById(R.id.ListaPersonajes_nombre);
            mJuegoPersonaje = itemView.findViewById(R.id.ListaPersonajes_juego);
            mCodigoPersonaje = itemView.findViewById(R.id.ListaPersonajes_codigo);
            mFotoPersonaje = itemView.findViewById(R.id.ListaPersonajes_foto);
            itemView.setOnClickListener(this);
            imgViewRemoveIcon = itemView.findViewById(R.id.ListaPersonajes_borrar);
            mFondoElemento = itemView.findViewById(R.id.ListaPersonajes_barra_fondo);

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

            mFondoElemento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Entra a la ficha de ese personaje
                    recordarMenu = false;
                    context.startActivity(new Intent(context, ContenedorInicioActivity.class).putExtra("codigo", mCodigoPersonaje.getText()));
                    ((Activity)context).finish();

                }
            });

        }

        public void asignarDatos(ItemPersonaje s) {

            //Seteo de datos de cada objeto
            mNombrePersonaje.setText(s.getNombre());
            mJuegoPersonaje.setText(s.getTipoJuego());
            mCodigoPersonaje.setText(s.getCodigo());

            //TODO: Raúl: Modificar para firebase/storage
            //Picasso.get().load(Uri.parse(s.getUrl())).into(mFotoPersonaje);

        }

        @Override
        public void onClick(View v) {}

    }

    public interface OnItemListener{
        void onItemClick(int position);
    }

    public void removeAt(int position) {
/*
        //Elimina el objeto del recycler
        listaDatos.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listaDatos.size());
*/
    }

}
//TODO: Alex: Cuando esté hecho el backend, eliminar los warnings del XML seteando los textos vacíos