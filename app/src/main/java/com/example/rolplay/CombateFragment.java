package com.example.rolplay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;

public class CombateFragment extends Fragment {

    //Declaración de variables
    private ProgressBar mBarraSalud;
    private CheckBox mCheckboxExito1, mCheckboxExito2;

    public CombateFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_combate, container, false);

        //Clase de armadura: lo determina la clase del objeto armadura (suele ser num + bonificador [cuadrado] de puntosHabilidad)

        //Iniciativa: random entre 1 y 20 + modificador [cuadrado] de Destreza

        //Velocidad: 40 es lo estándar. Luego, restar según peso:
        // 1lb = 10cn
        /*
            0-400cn - 40 pies
            401-800cn - 30 pies
            801-1200cn - 20 pies
            1201-1600cn - 10 pies
            1601-2400cn - 5 pies
            2401+ cn - 0 pies
         */

        //Puntos de golpe es la vida del personaje
        //el máximo a nivel 1 está determinado por la clase escogida, que te dice el dado que tienes que tirar (generar random + bonficador [cuadrado] de Constitución)
        //Al subir de nivel, se aumenta la vida (mirar chincheta)

        //Inicialización de variables
        mBarraSalud = v.findViewById(R.id.Combate_puntosGolpe_barraProgreso);
        mCheckboxExito1 = v.findViewById(R.id.Combate_exito_checkbox_1);
        mCheckboxExito2 = v.findViewById(R.id.Combate_exito_checkbox_2);

        //Placeholder
        mBarraSalud.setMax(100);
        mBarraSalud.setProgress(50);

        //TODO: Continuar código con el checkbox3 y con los checkbox de Fallos

        //Solo permite marcar el 2 cuando el 1 está marcado
        mCheckboxExito1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckboxExito1.isChecked()){
                    mCheckboxExito2.setEnabled(true);
                }else{
                    mCheckboxExito2.setEnabled(false);
                    mCheckboxExito2.setChecked(false);
                }
            }
        });

        //Desactiva los checkbox 2 y 3 por defecto
        mCheckboxExito2.setEnabled(false);


        return v;
    }
}
