package com.example.rolplay.Servicios;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rolplay.Activities.InformacionActivity;
import com.example.rolplay.R;

public class ConfiguracionFragment extends Fragment {

    //Declaración de variables
    private TextView mLinkCondiciones, mLinkPoliticaDatos, mLinkApi, mCambiarContrasenya, mBorrarCuenta;
    private ImageView mFotoCondiciones, mFotoPoliticaDatos, mFotoLink;

    //Constructor
    public ConfiguracionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_configuracion, container, false);

        //Para el switch del sonido podríamos hacerlo en una variable global (o desde FireBase) y que haya un if() antes de reproducir un sonido.

        //En el recibir información al correo, si está activado hay que guardar el correo en un nodo de FireBase que se llame "newsletter".
        // Así se podrán recoger todos fácilmente y mandar correos si se quiere. (y borrarlo al desmarcarlo)

        //Vamos a ser buena gente y poner el checkbox desmarcado como default

        //TODO: Raúl: Guardar configuración en FireBase
        //TODO: Alex: Cuando esté hecho el backend, eliminar warnings del XML
        //Para las estadísticas, meter un int en FireBase e ir sumando

        //Inicialización de variables
        mLinkCondiciones = v.findViewById(R.id.Configuracion_texto_condicionesUso);
        mLinkPoliticaDatos = v.findViewById(R.id.Configuracion_texto_politicaDatos);
        mLinkApi = v.findViewById(R.id.Configuracion_texto_api);
        mFotoCondiciones = v.findViewById(R.id.Configuracion_link_condicionesUso);
        mFotoPoliticaDatos = v.findViewById(R.id.Configuracion_link_politicaDatos);
        mFotoLink = v.findViewById(R.id.Configuracion_link_api);
        mBorrarCuenta = v.findViewById(R.id.Configuracion_texto_eliminarCuenta);
        mCambiarContrasenya = v.findViewById(R.id.Configuracion_texto_cambiarContrasenya);

        mBorrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Raúl: Borrar cuenta y sus personajes
            }
        });

        mCambiarContrasenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Raúl: Cambiar contraseña
            }
        });

        //Al hacer click pasa a enseñar la web de Condiciones
        mLinkCondiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarLink("condiciones");
            }
        });

        //Al hacer click pasa a enseñar la web de Política de Datos
        mLinkPoliticaDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarLink("politicaDatos");
            }
        });

        //Al hacer click pasa a enseñar la web de Api
        mLinkApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarLink("api");
            }
        });

        //Al hacer click pasa a enseñar la web de Política de Datos
        mFotoCondiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarLink("condiciones");
            }
        });

        //Al hacer click pasa a enseñar la web de Condiciones
        mFotoPoliticaDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarLink("politicaDatos");
            }
        });

        //Al hacer click pasa a enseñar la web de Api
        mFotoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarLink("api");
            }
        });

        return v;
    }

    private void enviarLink(String link) {

        //Envía qué tipo de link se necesita enseñar
        Intent intent = new Intent(getActivity(), InformacionActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);

    }

}
