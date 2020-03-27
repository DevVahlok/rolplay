package com.example.rolplay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PuntosHabilidadFragment extends Fragment {

    //Declaración de variables

    public PuntosHabilidadFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_puntos_habilidad, container, false);

        //Generación de estadísticas: si se quiere rerollear, hay que dar un número aleatorio entre 3-18 (ambos incluídos) [colocar en el círculo]
        //Sí, el máximo es 18 pero las posibilidades llegan hasta 30. No tengo muy claro cómo funciona (maybe se puede aumentar subiendo de nivel?)
        //Importante marcar el signo (+/-) en el modificador [cuadrado]

        /*

            Tabla de puntuación [círculo] -> modificador [cuadrado]
            1 -> -5
            2,3 -> -4
            4,5 -> -3
            6,7 -> -2
            8,9 -> -1
            10,11 -> 0
            12,13 -> +1
            14,15 -> +2
            16,17 -> +3
            18,19 -> +4
            20,21 -> +5
            22,23 -> +6
            24,25 -> +7
            26,27 -> +8
            28,29 -> +9
            30 -> +10

        */

        return v;
    }
}
