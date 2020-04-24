package com.example.rolplay.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolplay.Activities.ContenedorInicioActivity;
import com.example.rolplay.Otros.ItemPersonaje;
import com.example.rolplay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterRecyclerPersonaje extends RecyclerView.Adapter<AdapterRecyclerPersonaje.ViewHolderPersonaje>{

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
    public int getItemCount() {

        //Devuelve el tamaño del ArrayList
        return listaDatos.size();

    }

    public class ViewHolderPersonaje extends RecyclerView.ViewHolder implements View.OnClickListener{

        //Declaración de variables
        private TextView mNombrePersonaje, mJuegoPersonaje, mCodigoPersonaje;
        private ImageView mFotoPersonaje;
        private OnItemListener onItemListener;

        ViewHolderPersonaje(@NonNull final View itemView, final OnItemListener onItemListener) {

            //Inicialización de variables
            super(itemView);
            this.onItemListener = onItemListener;
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

                    //Elimina el objeto del recycler
                    onItemListener.onItemClick(getAdapterPosition());

                }
            });

            mFondoElemento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Entra a la ficha de ese personaje

                    FirebaseDatabase mDatabase;

                    mDatabase = FirebaseDatabase.getInstance();
                    HashMap<String, Object> ultimo = new HashMap<>();

                    ultimo.put("Ultimo personaje",
                            mCodigoPersonaje.getText().toString().split(": ")[1]
                    );

                    ultimo.put("Recordar menu",false);
                    mDatabase.getReference("users/"+ FirebaseAuth.getInstance().getUid()).updateChildren(ultimo);

                    context.startActivity(new Intent(context, ContenedorInicioActivity.class).putExtra("codigo", mCodigoPersonaje.getText().toString()));
                    ((Activity)context).finish();

                }
            });

        }

        public void asignarDatos(ItemPersonaje s) {

            //Seteo de datos de cada objeto
            mNombrePersonaje.setText(s.getNombre());
            mJuegoPersonaje.setText(s.getTipoJuego());
            mCodigoPersonaje.setText(context.getResources().getString(R.string.codigo, (s.getCodigo())));

            //TODO: Raúl: Modificar para firebase/storage (poner imagen según la clase seleccionada podría ser una buena opción)
            //Picasso.get().load(Uri.parse(s.getUrl())).into(mFotoPersonaje);

        }

        @Override
        public void onClick(View v) { }

    }

    //Interfaz para crear un OnClickListener en la foto de la X
    public interface OnItemListener{
        void onItemClick(int position);
    }

}