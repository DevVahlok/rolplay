package com.example.rolplay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PersonalidadFragment extends Fragment {

    public PersonalidadFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_personalidad, container, false);

        /*

            Aquí la funcionalidad es simple. El usuario le da a "editar texto" y le aparece un dialog para editarlo o borrarlo.

            Ejemplo dialog:

            +------------------------------------------+
            | Editar Rasgos de personalidad:           |
            |                                          |
            |  [                           ]           |
            |                                          |
            |       [Cancelar] [Borrar] [Guardar]      |
            |                                          |
            +------------------------------------------+


         */

        return v;
    }
}
