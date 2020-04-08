package com.example.rolplay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LanzarDadosFragment extends Fragment {

    public LanzarDadosFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lanzar_dados, container, false);

        //Todos los dados menos 1d100 y 1du se cambian con imágenes. Esos dos solo se setea el número en el TextView de su interior

        //Al pulsar en un dado se abre un dialog que pregunta la cantidad de dados que se quiere lanzar de ese tipo. En el caso de 1du, también dejará editar el número de caras

        return v;
    }
}
