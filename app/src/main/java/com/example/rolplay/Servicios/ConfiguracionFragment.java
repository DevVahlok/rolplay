package com.example.rolplay.Servicios;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rolplay.Activities.InformacionActivity;
import com.example.rolplay.Activities.MainActivity;
import com.example.rolplay.Otros.DialogCarga;
import com.example.rolplay.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import static com.example.rolplay.Activities.ContenedorInicioActivity.mMediaPlayer;
import static com.example.rolplay.Activities.ContenedorInicioActivity.volumen;

public class ConfiguracionFragment extends Fragment {

    //Declaración de variables
    private CheckBox mCorreo;
    private Switch mSonido;
    private TextView mLinkCondiciones, mLinkPoliticaDatos, mCambiarContrasenya, mBorrarCuenta;
    private ImageView mFotoCondiciones, mFotoPoliticaDatos;
    private FirebaseDatabase mDatabase;
    private DialogCarga mDialogCarga;

    //Constructor
    public ConfiguracionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_configuracion, container, false);

        //Para las estadísticas, meter un int en FireBase e ir sumando
        Bundle recuperados = getArguments();

        //Inicialización de variables
        mLinkCondiciones = v.findViewById(R.id.Configuracion_texto_condicionesUso);
        mLinkPoliticaDatos = v.findViewById(R.id.Configuracion_texto_politicaDatos);
        mFotoCondiciones = v.findViewById(R.id.Configuracion_link_condicionesUso);
        mFotoPoliticaDatos = v.findViewById(R.id.Configuracion_link_politicaDatos);
        mBorrarCuenta = v.findViewById(R.id.Configuracion_texto_eliminarCuenta);
        mCambiarContrasenya = v.findViewById(R.id.Configuracion_texto_cambiarContrasenya);
        mCorreo = v.findViewById(R.id.Configuracion_checkbox_newsletter);
        mSonido = v.findViewById(R.id.Configuracion_switch_sonido);
        mDatabase = FirebaseDatabase.getInstance();
        mDialogCarga = new DialogCarga();

        try {
            mSonido.setChecked(Objects.requireNonNull(recuperados).getBoolean("Sonido"));
            mCorreo.setChecked(recuperados.getBoolean("Correo"));
        } catch (Exception e) {
            Log.d("Datos peta: ", e.getMessage());
        }

        if (mSonido.isChecked()) {
            mMediaPlayer.setVolume(1, 1);
        } else {
            mMediaPlayer.setVolume(0, 0);
        }

        mSonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMediaPlayer.setVolume(1, 1);
                    volumen = 1;
                } else {
                    mMediaPlayer.setVolume(0, 0);
                    volumen = 0;
                }
            }
        });

        mBorrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//Muestra un dialog para que el usuario selecciona cuál quiere añadir
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                TextView title = new TextView(getActivity());
                title.setText(getString(R.string.estasSeguroQuieresBorrarCuenta));
                title.setTextColor(getActivity().getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0, 40, 0, 0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getActivity());

                linearLayout.setPadding(120, 10, 120, 10);

                constructrorDialog.setView(linearLayout);

                //Botón borrar cuenta
                constructrorDialog.setPositiveButton(getString(R.string.eliminarCuenta), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialogCarga.show(getActivity().getSupportFragmentManager(), null);
                        mDatabase.getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    mDialogCarga.dismiss();
                                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                                    getActivity().finish();
                                                }
                                            }
                                        });
                                    }
                                });
                        }
                    });
                        constructrorDialog.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        //Enseña el dialog de 'Añadir objeto'
                        AlertDialog anadirObjeto = constructrorDialog.create();
                        anadirObjeto.show();

                        Objects.requireNonNull(anadirObjeto.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));
            }
        });

        mCambiarContrasenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Muestra un dialog para que el usuario selecciona cuál quiere añadir
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                TextView title = new TextView(getActivity());
                title.setText(getString(R.string.cambiarContrasenya));
                title.setTextColor(getActivity().getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0, 40, 0, 0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getActivity());

                final EditText editText = new EditText(getActivity());
                editText.setMinEms(20);


                linearLayout.addView(editText);
                linearLayout.setPadding(120, 10, 120, 10);

                constructrorDialog.setView(linearLayout);

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                final FirebaseUser usuariActual = mAuth.getCurrentUser();
                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.cambiarContrasenya), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDialogCarga.show(getActivity().getSupportFragmentManager(), null);
                        mDialogCarga.setCancelable(false);
                        usuariActual.updatePassword(editText.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    mDialogCarga.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Cambio de contraseña fallido", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

                constructrorDialog.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //Enseña el dialog de 'Añadir objeto'
                AlertDialog anadirObjeto = constructrorDialog.create();
                anadirObjeto.show();

                Objects.requireNonNull(anadirObjeto.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));
            }
        });

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


        return v;
    }

    private void enviarLink(String link) {

        //Envía qué tipo de link se necesita enseñar
        Intent intent = new Intent(getActivity(), InformacionActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);

    }

    @Override
    public void onPause() {
        super.onPause();
        mDatabase = FirebaseDatabase.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuariActual = mAuth.getCurrentUser();

        //Guarda datos en FireBase al salir
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Sonido", mSonido.isChecked());
        hashMap.put("Correo", mCorreo.isChecked());
        mDatabase.getReference("users/" + usuariActual.getUid()).updateChildren(hashMap);
    }

}
