package com.example.rolplay;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class PuntosHabilidadFragment extends Fragment {

    //Declaración de variables
    private View v;
    private TextView mFuerzaPuntos, mDestrezaPuntos, mConstitucionPuntos, mInteligenciaPuntos,
            mSabiduriaPuntos, mCarismaPuntos, mFuerzaBonus, mDestrezaBonus, mConstitucionBonus,
            mInteligenciaBonus, mSabiduriaBonus, mCarismaBonus;
    private FirebaseDatabase mDatabase;
    private ImageView mFuerza, mDestreza, mConstitucion, mInteligencia, mSabiduria, mCarisma;
    private ImageButton mFuerzaEditar, mDestrezaEditar, mConstitucionEditar, mInteligenciaEditar, mSabiduriaEditar,
            mCarismaEditar;
    private String codigoPJ;

    public PuntosHabilidadFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_puntos_habilidad, container, false);
        final Bundle recuperados = getArguments();
        codigoPJ = recuperados.getString("codigo");
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
        //TextViews
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

        //ImageViews
        mFuerza = v.findViewById(R.id.puntosHabilidadFragment_boton_reroll1);
        mDestreza = v.findViewById(R.id.puntosHabilidadFragment_boton_reroll2);
        mConstitucion = v.findViewById(R.id.puntosHabilidadFragment_boton_reroll3);
        mInteligencia = v.findViewById(R.id.puntosHabilidadFragment_boton_reroll4);
        mSabiduria = v.findViewById(R.id.puntosHabilidadFragment_boton_reroll5);
        mCarisma = v.findViewById(R.id.puntosHabilidadFragment_boton_reroll6);
        mFuerzaEditar = v.findViewById(R.id.puntosHabilidadFragment_boton_editar1);
        mDestrezaEditar = v.findViewById(R.id.puntosHabilidadFragment_boton_editar2);
        mConstitucionEditar = v.findViewById(R.id.puntosHabilidadFragment_boton_editar3);
        mInteligenciaEditar = v.findViewById(R.id.puntosHabilidadFragment_boton_editar4);
        mSabiduriaEditar = v.findViewById(R.id.puntosHabilidadFragment_boton_editar5);
        mCarismaEditar = v.findViewById(R.id.puntosHabilidadFragment_boton_editar6);

        //Ponemos sus valores de firebase
        mFuerzaPuntos.setText(recuperados.getString("Fuerza puntos"));
        mFuerzaBonus.setText(recuperados.getString("Fuerza bonus"));
        mDestrezaPuntos.setText(recuperados.getString("Destreza puntos"));
        mDestrezaBonus.setText(recuperados.getString("Destreza bonus"));
        mConstitucionPuntos.setText(recuperados.getString("Constitucion puntos"));
        mConstitucionBonus.setText(recuperados.getString("Constitucion bonus"));
        mInteligenciaPuntos.setText(recuperados.getString("Inteligencia puntos"));
        mInteligenciaBonus.setText(recuperados.getString("Inteligencia bonus"));
        mSabiduriaPuntos.setText(recuperados.getString("Sabiduria puntos"));
        mSabiduriaBonus.setText(recuperados.getString("Sabiduria bonus"));
        mCarismaPuntos.setText(recuperados.getString("Carisma puntos"));
        mCarismaBonus.setText(recuperados.getString("Carisma bonus"));

        //Acciones de los dados
        Bonus(mFuerza, mFuerzaBonus, mFuerzaPuntos);
        Bonus(mDestreza, mDestrezaBonus, mDestrezaPuntos);
        Bonus(mConstitucion, mConstitucionBonus, mConstitucionPuntos);
        Bonus(mInteligencia, mInteligenciaBonus, mInteligenciaPuntos);
        Bonus(mSabiduria, mSabiduriaBonus, mSabiduriaPuntos);
        Bonus(mCarisma, mCarismaBonus, mCarismaPuntos);

        //Acciones de los lápiceros
        CanvioAMano(mFuerzaEditar, mFuerzaBonus, mFuerzaPuntos);
        CanvioAMano(mDestrezaEditar, mDestrezaBonus, mDestrezaPuntos);
        CanvioAMano(mConstitucionEditar, mConstitucionBonus, mConstitucionPuntos);
        CanvioAMano(mInteligenciaEditar, mInteligenciaBonus, mInteligenciaPuntos);
        CanvioAMano(mSabiduriaEditar, mSabiduriaBonus, mSabiduriaPuntos);
        CanvioAMano(mCarismaEditar, mCarismaBonus, mCarismaPuntos);

        return v;
    }

    //Funcion que abre un dialog para cambiar el valor
    private void CanvioAMano(ImageButton IB, final TextView TVB, final TextView TVP) {
        IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Muestra un dialog para que el usuario selecciona cuál quiere añadir
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                TextView title = new TextView(getActivity());
                title.setText(getString(R.string.modificar));
                title.setTextColor(getActivity().getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0,40,0,0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getActivity());

                final EditText editText = new EditText(getActivity());
                editText.setMinEms(20);
                editText.setText(TVB.getText());

                linearLayout.addView(editText);
                linearLayout.setPadding(120,10,120,10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.modificar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            switch (Integer.parseInt(editText.getText().toString())) {
                                case 1:
                                    TVB.setText(editText.getText());
                                    TVP.setText(R.string.menoscinco);
                                    break;
                                case 2:
                                case 3:
                                    TVB.setText(editText.getText());
                                    TVP.setText(R.string.menoscuatro);
                                    break;
                                case 4:
                                case 5:
                                    TVB.setText(editText.getText());
                                    TVP.setText(R.string.menostres);
                                    break;
                                case 6:
                                case 7:
                                    TVB.setText(editText.getText());
                                    TVP.setText(R.string.menosdos);
                                    break;
                                case 8:
                                case 9:
                                    TVB.setText(editText.getText());
                                    TVP.setText(R.string.menosuno);
                                    break;
                                case 10:
                                case 11:
                                    TVB.setText(editText.getText());
                                    TVP.setText(R.string.zero);
                                    break;
                                case 12:
                                case 13:
                                    TVB.setText(editText.getText());
                                    TVP.setText(R.string.masuno);
                                    break;
                                case 14:
                                case 15:
                                    TVB.setText(editText.getText());
                                    TVP.setText(R.string.masdos);
                                    break;
                                case 16:
                                case 17:
                                    TVB.setText(editText.getText());
                                    TVP.setText(R.string.mastres);
                                    break;
                                case 18:
                                    TVB.setText(editText.getText());
                                    TVP.setText(R.string.mascuatro);
                                    break;
                                default:
                                    break;
                            }
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "Han de ser números", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                constructrorDialog.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //Enseña el dialog de 'Modificar'
                AlertDialog cambiarPuntos = constructrorDialog.create();
                cambiarPuntos.show();
                Objects.requireNonNull(cambiarPuntos.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));

            }
        });
    }

    //Funcion que genera un random para cambiar el valor de dado
    public void Bonus (ImageView IV, final TextView TVB, final TextView TVP){
        IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int x = r.nextInt(15)+3;
                switch (x) {
                    case 1:
                        TVB.setText(String.valueOf(x));
                        TVP.setText(R.string.menoscinco);
                        break;
                    case 2:
                    case 3:
                        TVB.setText(String.valueOf(x));
                        TVP.setText(R.string.menoscuatro);
                        break;
                    case 4:
                    case 5:
                        TVB.setText(String.valueOf(x));
                        TVP.setText(R.string.menostres);
                        break;
                    case 6:
                    case 7:
                        TVB.setText(String.valueOf(x));
                        TVP.setText(R.string.menosdos);
                        break;
                    case 8:
                    case 9:
                        TVB.setText(String.valueOf(x));
                        TVP.setText(R.string.menosuno);
                        break;
                    case 10:
                    case 11:
                        TVB.setText(String.valueOf(x));
                        TVP.setText(R.string.zero);
                        break;
                    case 12:
                    case 13:
                        TVB.setText(String.valueOf(x));
                        TVP.setText(R.string.masuno);
                        break;
                    case 14:
                    case 15:
                        TVB.setText(String.valueOf(x));
                        TVP.setText(R.string.masdos);
                        break;
                    case 16:
                    case 17:
                        TVB.setText(String.valueOf(x));
                        TVP.setText(R.string.mastres);
                        break;
                    case 18:
                        TVB.setText(String.valueOf(x));
                        TVP.setText(R.string.mascuatro);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        mDatabase = FirebaseDatabase.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuariActual = mAuth.getCurrentUser();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Fuerza puntos",mFuerzaPuntos.getText().toString());
        hashMap.put("Fuerza bonus",mFuerzaBonus.getText().toString());
        hashMap.put("Destreza puntos",mDestrezaPuntos.getText().toString());
        hashMap.put("Destreza bonus",mDestrezaBonus.getText().toString());
        hashMap.put("Constitucion puntos",mConstitucionPuntos.getText().toString());
        hashMap.put("Constitucion bonus",mConstitucionBonus.getText().toString());
        hashMap.put("Inteligencia puntos",mInteligenciaPuntos.getText().toString());
        hashMap.put("Inteligencia bonus",mInteligenciaBonus.getText().toString());
        hashMap.put("Sabiduria puntos",mSabiduriaPuntos.getText().toString());
        hashMap.put("Sabiduria bonus",mSabiduriaBonus.getText().toString());
        hashMap.put("Carisma puntos",mCarismaPuntos.getText().toString());
        hashMap.put("Carisma bonus",mCarismaBonus.getText().toString());
        mDatabase.getReference("users/"+usuariActual.getUid()+"/"+codigoPJ).updateChildren(hashMap);

        HashMap<String, Object> ultimo = new HashMap<>();

        ultimo.put("Ultimo personaje",codigoPJ);
        mDatabase.getReference("users/"+usuariActual.getUid()).updateChildren(ultimo);
    }
}
