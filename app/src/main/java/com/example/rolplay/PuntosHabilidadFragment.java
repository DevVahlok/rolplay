package com.example.rolplay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PuntosHabilidadFragment extends Fragment {

    //Declaración de variables
    private View v;
    private TextView mFuerzaPuntos, mDestrezaPuntos, mConstitucionPuntos, mInteligenciaPuntos,
            mSabiduriaPuntos, mCarismaPuntos, mFuerzaBonus, mDestrezaBonus, mConstitucionBonus,
            mInteligenciaBonus, mSabiduriaBonus, mCarismaBonus;

    public PuntosHabilidadFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_puntos_habilidad, container, false);
        final Bundle recuperados = getArguments();
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
        mFuerzaPuntos = v.findViewById(R.id.puntosHabilidadFragment_modificador1);
        mFuerzaBonus = v.findViewById(R.id.puntosHabilidadFragment_puntuacion1);
        mDestrezaPuntos = v.findViewById(R.id.puntosHabilidadFragment_modificador2);
        mDestrezaBonus = v.findViewById(R.id.puntosHabilidadFragment_puntuacion2);
        mConstitucionPuntos = v.findViewById(R.id.puntosHabilidadFragment_modificador3);
        mConstitucionBonus = v.findViewById(R.id.puntosHabilidadFragment_puntuacion3);
        mInteligenciaPuntos = v.findViewById(R.id.puntosHabilidadFragment_modificador4);
        mInteligenciaBonus = v.findViewById(R.id.puntosHabilidadFragment_puntuacion4);
        mSabiduriaPuntos = v.findViewById(R.id.puntosHabilidadFragment_modificador5);
        mSabiduriaBonus = v.findViewById(R.id.puntosHabilidadFragment_puntuacion5);
        mCarismaPuntos = v.findViewById(R.id.puntosHabilidadFragment_modificador6);
        mCarismaBonus = v.findViewById(R.id.puntosHabilidadFragment_puntuacion6);

        Bonus(mFuerzaBonus,recuperados.getInt("roll1"));
        Bonus(mDestrezaBonus,recuperados.getInt("roll2"));
        Bonus(mConstitucionBonus,recuperados.getInt("roll3"));
        Bonus(mInteligenciaBonus,recuperados.getInt("roll4"));
        Bonus(mSabiduriaBonus,recuperados.getInt("roll5"));
        Bonus(mCarismaBonus,recuperados.getInt("roll6"));

        return v;
    }

    public void Bonus (TextView TV, int x){
        switch (x) {
            case 1:
                TV.setText("-5");
                break;
            case 2:
            case 3:
                TV.setText("-4");
                break;
            case 4:
            case 5:
                TV.setText("-3");
                break;
            case 6:
            case 7:
                TV.setText("-2");
                break;
            case 8:
            case 9:
                TV.setText("-1");
                break;
            case 10:
            case 11:
                TV.setText("0");
                break;
            case 12:
            case 13:
                TV.setText("1");
                break;
            case 14:
            case 15:
                TV.setText("2");
                break;
            case 16:
            case 17:
                TV.setText("3");
                break;
            case 18:
                TV.setText("4");
                break;
            default:
                TV.setText("");
                break;
        }
    }
}
