package com.example.rolplay.Servicios;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.rolplay.Activities.ContenedorInicioActivity;
import com.example.rolplay.Activities.InformacionActivity;
import com.example.rolplay.Activities.MainActivity;
import com.example.rolplay.R;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class ConfiguracionFragment extends Fragment {

    //Declaración de variables
    private CheckBox mCorreo;
    private Switch mSonido;
    private TextView mLinkCondiciones, mLinkPoliticaDatos, mLinkApi, mCambiarContrasenya, mBorrarCuenta;
    private ImageView mFotoCondiciones, mFotoPoliticaDatos, mFotoLink;
    private FirebaseDatabase mDatabase;

    //Constructor
    public ConfiguracionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_configuracion, container, false);

        //Para el switch del sonido podríamos hacerlo en una variable global (o desde FireBase) y que haya un if() antes de reproducir un sonido.

        //En el recibir información al correo, si está activado hay que guardar el correo en un nodo de FireBase que se llame "newsletter".
        // Así se podrán recoger todos fácilmente y mandar correos si se quiere. (y borrarlo al desmarcarlo)

        //Vamos a ser buena gente y poner el checkbox desmarcado como default

        //TODO: Alex: Cuando esté hecho el backend, eliminar warnings del XML
        //Para las estadísticas, meter un int en FireBase e ir sumando
        Bundle recuperados = getArguments();

        //Inicialización de variables
        mLinkCondiciones = v.findViewById(R.id.Configuracion_texto_condicionesUso);
        mLinkPoliticaDatos = v.findViewById(R.id.Configuracion_texto_politicaDatos);
        mLinkApi = v.findViewById(R.id.Configuracion_texto_api);
        mFotoCondiciones = v.findViewById(R.id.Configuracion_link_condicionesUso);
        mFotoPoliticaDatos = v.findViewById(R.id.Configuracion_link_politicaDatos);
        mFotoLink = v.findViewById(R.id.Configuracion_link_api);
        mBorrarCuenta = v.findViewById(R.id.Configuracion_texto_eliminarCuenta);
        mCambiarContrasenya = v.findViewById(R.id.Configuracion_texto_cambiarContrasenya);
        mCorreo = v.findViewById(R.id.Configuracion_checkbox_newsletter);
        mSonido = v.findViewById(R.id.Configuracion_switch_sonido);
        mDatabase = FirebaseDatabase.getInstance();

        try {
            mSonido.setChecked(Objects.requireNonNull(recuperados).getBoolean("Sonido"));
            mCorreo.setChecked(recuperados.getBoolean("Correo"));
        }catch (Exception e){
            Log.d("Datos peta: ", e.getMessage());
        }


        mBorrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                FirebaseAuth.getInstance().getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(getActivity(), MainActivity.class));
                                            getActivity().finish();
                                        }
                                    }
                                });
                            }
                        });
            }
        });

        mCambiarContrasenya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Raúl: Cambiar contraseña
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

        //Al hacer click pasa a enseñar la web de Api
        mLinkApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarLink("api");
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

        //Al hacer click pasa a enseñar la web de Api
        mFotoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarLink("api");
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
        hashMap.put("Sonido",mSonido.isChecked());
        hashMap.put("Correo",mCorreo.isChecked());
        mDatabase.getReference("users/"+usuariActual.getUid()).updateChildren(hashMap);
    }

}
