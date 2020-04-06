package com.example.rolplay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

public class CombateFragment extends Fragment {

    //Declaración de variables
    private ProgressBar mBarraSalud;
    private CheckBox mCheckboxExito1, mCheckboxExito2, mCheckboxExito3,
                        mCheckboxFallo1, mCheckboxFallo2, mCheckboxFallo3;
    private TextView mClaseArmadura, mIniciativa, mVelocidad;
    private EditText mGolpesActuales, mGolpesTotales, mGolpesTemporales, mDadoGolpe,
                        mDadoTotal;
    private int mSalvaciones;

    public CombateFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_combate, container, false);
        Bundle recuperados = getArguments();

        mClaseArmadura = v.findViewById(R.id.Combate_valor_inspiracion);
        mIniciativa = v.findViewById(R.id.Combate_valor_iniciativa);
        mVelocidad = v.findViewById(R.id.Combate_valor_velocidad);
        mGolpesActuales = v.findViewById(R.id.Combate_golpeActuales_valor);
        mGolpesTemporales = v.findViewById(R.id.Combate_golpeTemporal_valor);
        mGolpesTotales = v.findViewById(R.id.Combate_golpeMaximo_valor);
        mDadoGolpe = v.findViewById(R.id.Combate_numDados_valor);
        mDadoTotal = v.findViewById(R.id.Combate_totalDados_valor);

        mClaseArmadura.setText(recuperados.getString("Clase de Armadura"));
        mIniciativa.setText(recuperados.getString("Iniciativa"));
        mVelocidad.setText(recuperados.getString("Velocidad"));
        mGolpesActuales.setText(recuperados.getString("Puntos de Golpe Actuales"));
        mGolpesTemporales.setText(recuperados.getString("Puntos de Golpe Temporales"));
        mGolpesTotales.setText(recuperados.getString("Puntos de Golpe Máximos"));
        mDadoGolpe.setText(recuperados.getString("Dado de Golpe/Valor"));
        mDadoTotal.setText(recuperados.getString("Dado de Golpe/Total"));
        mSalvaciones = recuperados.getInt("Salvacion");
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
        mCheckboxExito3 = v.findViewById(R.id.Combate_exito_checkbox_3);
        mCheckboxFallo1 = v.findViewById(R.id.Combate_fallo_checkbox_1);
        mCheckboxFallo2 = v.findViewById(R.id.Combate_fallo_checkbox_2);
        mCheckboxFallo3 = v.findViewById(R.id.Combate_fallo_checkbox_3);

        //Placeholder
        mBarraSalud.setMax(100);
        mBarraSalud.setProgress(50);

        //Solo permite marcar el 2 cuando el 1 está marcado
        switch (mSalvaciones){
            case 0:
                mCheckboxExito1.setChecked(false);
                mCheckboxExito2.setChecked(false);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(false);
                mCheckboxFallo2.setChecked(false);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxExito2.setEnabled(false);
                mCheckboxExito3.setEnabled(false);
                mCheckboxFallo2.setEnabled(false);
                mCheckboxFallo3.setEnabled(false);
                break;
            case 1:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(false);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(false);
                mCheckboxFallo2.setChecked(false);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxExito3.setEnabled(false);
                mCheckboxFallo2.setEnabled(false);
                mCheckboxFallo3.setEnabled(false);
                break;
            case 2:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(true);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(false);
                mCheckboxFallo2.setChecked(false);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxFallo2.setEnabled(false);
                mCheckboxFallo3.setEnabled(false);
                break;
            case 3:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(true);
                mCheckboxExito3.setChecked(true);
                mCheckboxFallo1.setChecked(false);
                mCheckboxFallo2.setChecked(false);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxFallo2.setEnabled(false);
                mCheckboxFallo3.setEnabled(false);
                break;
            case 4:
                mCheckboxExito1.setChecked(false);
                mCheckboxExito2.setChecked(false);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(false);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxExito2.setEnabled(false);
                mCheckboxExito3.setEnabled(false);
                mCheckboxFallo3.setEnabled(false);
                break;
            case 5:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(false);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(false);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxExito3.setEnabled(false);
                mCheckboxFallo3.setEnabled(false);
                break;
            case 6:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(true);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(false);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxFallo3.setEnabled(false);
                break;
            case 7:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(true);
                mCheckboxExito3.setChecked(true);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(false);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxFallo3.setEnabled(false);
                break;
            case 8:
                mCheckboxExito1.setChecked(false);
                mCheckboxExito2.setChecked(false);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(true);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxExito2.setEnabled(false);
                mCheckboxExito3.setEnabled(false);
                break;
            case 9:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(false);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(true);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxExito3.setEnabled(false);
                break;
            case 10:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(true);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(true);
                mCheckboxFallo3.setChecked(false);
                break;
            case 11:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(true);
                mCheckboxExito3.setChecked(true);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(true);
                mCheckboxFallo3.setChecked(false);
                break;
            case 12:
                mCheckboxExito1.setChecked(false);
                mCheckboxExito2.setChecked(false);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(true);
                mCheckboxFallo3.setChecked(true);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxExito2.setEnabled(false);
                mCheckboxExito3.setEnabled(false);
                break;
            case 13:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(false);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(true);
                mCheckboxFallo3.setChecked(true);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxExito3.setEnabled(false);
                break;
            case 14:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(true);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(true);
                mCheckboxFallo3.setChecked(true);
                break;
            case 15:
                mCheckboxExito1.setChecked(true);
                mCheckboxExito2.setChecked(true);
                mCheckboxExito3.setChecked(true);
                mCheckboxFallo1.setChecked(true);
                mCheckboxFallo2.setChecked(true);
                mCheckboxFallo3.setChecked(true);
                break;
            default:
                mSalvaciones=0;
                mCheckboxExito1.setChecked(false);
                mCheckboxExito2.setChecked(false);
                mCheckboxExito3.setChecked(false);
                mCheckboxFallo1.setChecked(false);
                mCheckboxFallo2.setChecked(false);
                mCheckboxFallo3.setChecked(false);
                //Desactiva los checkbox 2 y 3 por defecto
                mCheckboxExito2.setEnabled(false);
                mCheckboxExito3.setEnabled(false);
                mCheckboxFallo2.setEnabled(false);
                mCheckboxFallo3.setEnabled(false);
                break;
        }

        //OnClick listener de los checkbox
        mCheckboxExito1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckboxExito1.isChecked()){
                    mCheckboxExito2.setEnabled(true);
                }else{
                    mCheckboxExito2.setEnabled(false);
                    mCheckboxExito2.setChecked(false);
                    mCheckboxExito3.setEnabled(false);
                    mCheckboxExito3.setChecked(false);
                }
            }
        });
        mCheckboxExito2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (mCheckboxExito2.isChecked()){
                   mCheckboxExito3.setEnabled(true);
               }else{
                   mCheckboxExito3.setEnabled(false);
                   mCheckboxExito3.setChecked(false);
               }
           }
       });
        mCheckboxFallo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckboxFallo1.isChecked()){
                    mCheckboxFallo2.setEnabled(true);
                }else{
                    mCheckboxFallo2.setEnabled(false);
                    mCheckboxFallo2.setChecked(false);
                    mCheckboxFallo3.setEnabled(false);
                    mCheckboxFallo3.setChecked(false);
                }
            }
        });
        mCheckboxFallo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckboxFallo2.isChecked()){
                    mCheckboxFallo3.setEnabled(true);
                }else{
                    mCheckboxFallo3.setEnabled(false);
                    mCheckboxFallo3.setChecked(false);
                }
            }
        });

        return v;
    }
}
