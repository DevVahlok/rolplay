package com.example.rolplay;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class CombateFragment extends Fragment {

    //Declaración de variables
    private ProgressBar mBarraSalud;
    private CheckBox mCheckboxExito1, mCheckboxExito2, mCheckboxExito3,
            mCheckboxFallo1, mCheckboxFallo2, mCheckboxFallo3;
    private TextView mClaseArmadura, mIniciativa, mVelocidad;
    private EditText mGolpesActuales, mGolpesTotales, mGolpesTemporales, mDadoGolpe,
            mDadoTotal;
    private int mSalvaciones, mPeso, mDestreza;
    private ImageButton mIniciativaButon;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private View v;
    private String codigoPJ;

    //Constructor
    public CombateFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inicialización de variables
        v = inflater.inflate(R.layout.fragment_combate, container, false);
        Bundle recuperados = getArguments();
        codigoPJ = recuperados.getString("codigo");
        mClaseArmadura = v.findViewById(R.id.Combate_valor_inspiracion);
        mIniciativa = v.findViewById(R.id.Combate_valor_iniciativa);
        mIniciativaButon = v.findViewById(R.id.Combate_iniciativa_boton_editar);
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
        mPeso = recuperados.getInt("Peso total");
        try {
            mDestreza = Integer.parseInt(Objects.requireNonNull(recuperados.getString("Destreza puntos")));
        }catch (Exception e){
            
        }


        //Clase de armadura: lo determina la clase del objeto armadura (suele ser num + bonificador [cuadrado] de puntosHabilidad)


        //Iniciativa: random entre 1 y 20 + modificador [cuadrado] de Destreza
        mIniciativaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                mIniciativa.setText(String.valueOf(r.nextInt(19)+1+mDestreza));
            }
        });

        //Velocidad depende del peso de todos los objetos
        if (mPeso<=40 && mPeso>=0) {
            mVelocidad.setText("40");
        }else if (mPeso<=80 && mPeso>=40.1) {
            mVelocidad.setText("30");
        }else if (mPeso<=120 && mPeso>=80.1) {
            mVelocidad.setText("20");
        }else if (mPeso<=160 && mPeso>=120.1) {
            mVelocidad.setText("10");
        }else if (mPeso<=240 && mPeso>=160.1) {
            mVelocidad.setText("5");
        }else if (mPeso>=240.1) {
            mVelocidad.setText("0");
        }


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

        //TODO: Raúl: El valor de clase de armadura ha desaparecido

        ActualizarBarraDeVida();

        mGolpesActuales.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ActualizarBarraDeVida();
                }
            }
        });

        mGolpesTotales.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    ActualizarBarraDeVida();
                }
            }
        });


        //Solo permite marcar el 2 cuando el 1 está marcado
        switch (mSalvaciones) {
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
                mSalvaciones = 0;
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
                if (mCheckboxExito1.isChecked()) {
                    mCheckboxExito2.setEnabled(true);
                } else {
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
                if (mCheckboxExito2.isChecked()) {
                    mCheckboxExito3.setEnabled(true);
                } else {
                    mCheckboxExito3.setEnabled(false);
                    mCheckboxExito3.setChecked(false);
                }
            }
        });
        mCheckboxFallo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckboxFallo1.isChecked()) {
                    mCheckboxFallo2.setEnabled(true);
                } else {
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
                if (mCheckboxFallo2.isChecked()) {
                    mCheckboxFallo3.setEnabled(true);
                } else {
                    mCheckboxFallo3.setEnabled(false);
                    mCheckboxFallo3.setChecked(false);
                }
            }
        });

        return v;
    }

    private void ActualizarBarraDeVida() {
        mBarraSalud.setMax(Integer.parseInt(String.valueOf(mGolpesTotales.getText())));
        mBarraSalud.setProgress(Integer.parseInt(String.valueOf(mGolpesTotales.getText())) - Integer.parseInt(String.valueOf(mGolpesActuales.getText())));
    }


    @Override
    public void onPause() {
        super.onPause();

        //Inicialización de variables
        mClaseArmadura = v.findViewById(R.id.Combate_valor_inspiracion);
        mIniciativa = v.findViewById(R.id.Combate_valor_iniciativa);
        mVelocidad = v.findViewById(R.id.Combate_valor_velocidad);
        mGolpesActuales = v.findViewById(R.id.Combate_golpeActuales_valor);
        mGolpesTemporales = v.findViewById(R.id.Combate_golpeTemporal_valor);
        mGolpesTotales = v.findViewById(R.id.Combate_golpeMaximo_valor);
        mDadoGolpe = v.findViewById(R.id.Combate_numDados_valor);
        mDadoTotal = v.findViewById(R.id.Combate_totalDados_valor);
        mCheckboxExito1 = v.findViewById(R.id.Combate_exito_checkbox_1);
        mCheckboxExito2 = v.findViewById(R.id.Combate_exito_checkbox_2);
        mCheckboxExito3 = v.findViewById(R.id.Combate_exito_checkbox_3);
        mCheckboxFallo1 = v.findViewById(R.id.Combate_fallo_checkbox_1);
        mCheckboxFallo2 = v.findViewById(R.id.Combate_fallo_checkbox_2);
        mCheckboxFallo3 = v.findViewById(R.id.Combate_fallo_checkbox_3);
        int salvacio;
        if (!mCheckboxFallo1.isChecked() && !mCheckboxExito1.isChecked()) {
            salvacio = 0;
        } else if (!mCheckboxFallo1.isChecked() && !mCheckboxExito2.isChecked()) {
            salvacio = 1;
        } else if (!mCheckboxFallo1.isChecked() && !mCheckboxExito3.isChecked()) {
            salvacio = 2;
        } else if (!mCheckboxFallo2.isChecked() && !mCheckboxExito1.isChecked()) {
            salvacio = 4;
        } else if (!mCheckboxFallo2.isChecked() && !mCheckboxExito2.isChecked()) {
            salvacio = 5;
        } else if (!mCheckboxFallo2.isChecked() && !mCheckboxExito3.isChecked()) {
            salvacio = 6;
        } else if (!mCheckboxFallo3.isChecked() && !mCheckboxExito1.isChecked()) {
            salvacio = 8;
        } else if (!mCheckboxFallo3.isChecked() && !mCheckboxExito2.isChecked()) {
            salvacio = 9;
        } else if (!mCheckboxFallo3.isChecked() && !mCheckboxExito3.isChecked()) {
            salvacio = 10;
        } else if (mCheckboxFallo3.isChecked() && !mCheckboxExito1.isChecked()) {
            salvacio = 12;
        } else if (mCheckboxFallo3.isChecked() && !mCheckboxExito2.isChecked()) {
            salvacio = 13;
        } else if (mCheckboxFallo3.isChecked() && !mCheckboxExito3.isChecked()) {
            salvacio = 14;
        } else if (!mCheckboxFallo1.isChecked() && mCheckboxExito3.isChecked()) {
            salvacio = 3;
        } else if (!mCheckboxFallo2.isChecked() && mCheckboxExito3.isChecked()) {
            salvacio = 7;
        } else if (!mCheckboxFallo3.isChecked() && mCheckboxExito3.isChecked()) {
            salvacio = 11;
        } else {
            salvacio = 15;
        }
        mDatabase = FirebaseDatabase.getInstance();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuariActual = mAuth.getCurrentUser();

        HashMap<String, Object> hashMap = new HashMap<>();

        //Guarda los datos en FireBase
        hashMap.put("Clase de Armadura", mClaseArmadura.getText().toString().trim());
        hashMap.put("Iniciativa", mIniciativa.getText().toString().trim());
        hashMap.put("Velocidad", mVelocidad.getText().toString().trim());
        hashMap.put("Puntos de Golpe Actuales", mGolpesActuales.getText().toString().trim());
        hashMap.put("Puntos de Golpe Máximos", mGolpesTotales.getText().toString().trim());
        hashMap.put("Puntos de Golpe Temporales", mGolpesTemporales.getText().toString().trim());
        hashMap.put("Dado de Golpe/Valor", mDadoGolpe.getText().toString().trim());
        hashMap.put("Dado de Golpe/Total", mDadoTotal.getText().toString().trim());
        hashMap.put("Salvaciones de Muerte", String.valueOf(salvacio));

        mDatabase.getReference("users/" + usuariActual.getUid() + "/" + codigoPJ).updateChildren(hashMap);

        HashMap<String, Object> ultimo = new HashMap<>();

        ultimo.put("Ultimo personaje", codigoPJ);
        mDatabase.getReference("users/" + usuariActual.getUid()).updateChildren(ultimo);
    }
}
