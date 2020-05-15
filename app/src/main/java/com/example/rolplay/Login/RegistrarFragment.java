package com.example.rolplay.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rolplay.Activities.ContenedorInicioActivity;
import com.example.rolplay.Otros.DialogCarga;
import com.example.rolplay.R;
import com.example.rolplay.Servicios.MenuPersonajesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrarFragment extends Fragment {

    //Declaración de variables
    private TextView mTitulo;
    private Button mBotonRegistrar;
    private TextInputEditText mTextInputCorreo, mTextInputPassword, mTextInputConfirmPassword;
    private FirebaseAuth mAuth;
    private DialogCarga mDialogCarga;

    public RegistrarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_registrar, container, false);

        //Inicialización de variables
        mTitulo = v.findViewById(R.id.RegistrarActivity_titulo);
        mBotonRegistrar = v.findViewById(R.id.RegistrarActivity_registrar_btn);

        mTextInputCorreo = v.findViewById(R.id.RegistrarActivity_email_et);
        mTextInputPassword = v.findViewById(R.id.RegistrarActivity_password_et);
        mTextInputConfirmPassword = v.findViewById(R.id.RegistrarActivity_confirmPassword_et);

        mAuth = FirebaseAuth.getInstance();
        mDialogCarga = new DialogCarga();

        //Boton de registrar
        mBotonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mTextInputCorreo.getText().toString();
                String pass = mTextInputPassword.getText().toString();
                String confirmpass = mTextInputConfirmPassword.getText().toString();

                //Comprobaciones de mail y password
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    mTextInputCorreo.setError("El formato del email no es correcto.");
                    mTextInputCorreo.setFocusable(true);

                } else if (pass.length() < 6) {

                    mTextInputPassword.setError("La contraseña es demasiado corta");
                    mTextInputPassword.setFocusable(true);

                } else if (!pass.equals(confirmpass)) {

                    mTextInputPassword.setError("No coinciden las contraseñas");
                    mTextInputPassword.setFocusable(true);
                    mTextInputConfirmPassword.setFocusable(true);

                } else {
                    RegistrarUsuari(email, pass);
                }
            }
        });

        return v;
    }

    //Accion de registrar
    private void RegistrarUsuari(String email, String pass) {

        mDialogCarga.show(getActivity().getSupportFragmentManager(), null);
        mDialogCarga.setCancelable(false);
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //Caso de que registre correctamente
                            mDialogCarga.dismiss();
                            startActivity(new Intent(getActivity(), MenuPersonajesActivity.class));

                            getActivity().finish();

                        } else {

                            //Caso de que falle el registro
                            mDialogCarga.dismiss();
                            Toast.makeText(getActivity(), "Creación de usuario fallida", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //Fallo en firebase
                mDialogCarga.dismiss();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


}


