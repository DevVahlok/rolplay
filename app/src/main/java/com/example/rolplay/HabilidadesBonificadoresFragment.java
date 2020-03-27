package com.example.rolplay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class HabilidadesBonificadoresFragment extends Fragment {



    public HabilidadesBonificadoresFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_habilidades_bonificadores, container, false);


        //Aquí poner cada número con signo (+/-) menos "Inspiración" y "Sabiduría (percepción) pasiva"

        //Los números junto a los checkbox se recogen directamente del modificador [cuadrado] de PuntosHabilidadFragment

        //Si no hay ningún valor numérico, setearlo a 0 (directamente desde firebase si puede ser)

        /*

        Lista de dónde recoger los valores

            - Acrobacias (Destreza)
            - Atletismo (Fuerza)
            - Conocimiento Arcano (Inteligencia)
            - Engaño (Carisma)
            - Historia (Inteligencia)
            - Interpretación (Carisma)
            - Intimidación (Carisma)
            - Investigación (Inteligencia)
            - Juego de manos (Destreza)
            - Medicina (Sabiduría)
            - Naturaleza (Inteligencia)
            - Percepción (Sabiduría)
            - Perspicacia (Sabiduría)
            - Persuasión (Carisma)
            - Religión (Inteligencia)
            - Sigilo (Destreza)
            - Supervivencia (Sabiduría)
            - Trato con Animales (Sabiduría)

        */

        //Con esto se hacen cositas en los checkbox
        //  .setChecked(boolean);
        //  .isChecked();

        //TODO: Alex: Cuando esté hecho el backend, eliminar los warnings del XML seteando los textos vacíos

        return v;
    }
}
