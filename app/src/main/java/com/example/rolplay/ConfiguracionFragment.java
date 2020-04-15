package com.example.rolplay;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfiguracionFragment extends Fragment {

    //Declaración de variables
    private TextView mLinkCondiciones, mLinkPoliticaDatos, mLinkApi;
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
        //Para las estadísticas, meter un int en FireBase e ir sumando

        //Inicialización de variables
        mLinkCondiciones = v.findViewById(R.id.Configuracion_texto_politicaDatos);
        mLinkPoliticaDatos = v.findViewById(R.id.Configuracion_texto_condicionesUso);
        mLinkApi = v.findViewById(R.id.Configuracion_texto_api);
        mFotoCondiciones = v.findViewById(R.id.Configuracion_link_politicaDatos);
        mFotoPoliticaDatos = v.findViewById(R.id.Configuracion_link_condicionesUso);
        mFotoLink = v.findViewById(R.id.Configuracion_link_api);

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