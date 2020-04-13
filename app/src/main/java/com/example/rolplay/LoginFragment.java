package com.example.rolplay;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.text.InputType;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static com.example.rolplay.MenuPersonajesActivity.recordarMenu;

public class LoginFragment extends Fragment {

    //Declaración de variables
    private TextView mTitulo;
    private Button mBotonRegistrar, mBotonEntrar, mBotonRecuperarPassword;
    private TextInputEditText mTextInputCorreo, mTextInputPassword;
    private FirebaseAuth mAuth;
    private DialogCarga mDialogCarga;

    //Constructor vacío del Fragment
    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //Inicialización de variables
        mTitulo = v.findViewById(R.id.MainActivity_titulo);
        mTextInputCorreo = v.findViewById(R.id.MainActivity_email_et);
        mTextInputPassword = v.findViewById(R.id.MainActivity_password_et);

        mBotonEntrar = v.findViewById(R.id.MainActivity_entrar_btn);
        mBotonRegistrar = v.findViewById(R.id.MainActivity_registrar_btn);
        mBotonRecuperarPassword = v.findViewById(R.id.MainActivity_recuperar_password_btn);

        mAuth = FirebaseAuth.getInstance();
        mDialogCarga = new DialogCarga();

        //Cambio de pantalla a 'LoginActivity'
        mBotonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(mTextInputCorreo.getText()).toString();
                String pass = Objects.requireNonNull(mTextInputPassword.getText()).toString();

                //Comprobaciones de email y password
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mTextInputCorreo.setError("El formato del email no es correcto.");
                    mTextInputCorreo.setFocusable(true);
                }else if(pass.length()<6){
                    mTextInputPassword.setError("La contraseña es demasiado corta");
                    mTextInputPassword.setFocusable(true);
                }else{
                    LoginUsuari(email, pass);
                }
            }
        });

        //Cambio de pantalla a 'RegistrarActivity'
        mBotonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegistrarFragment()).addToBackStack(null).commit();
            }
        });

        //Recuperar contraseña
        mBotonRecuperarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarRecuperarPassword();
            }
        });


        return v;
    }

    //Función para crear dinámicamente un dialog
    private void MostrarRecuperarPassword() {

        AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        TextView title = new TextView(getActivity());
        title.setText(getString(R.string.recuperarPassword));
        title.setTextColor(getActivity().getColor(R.color.colorPrimary));
        title.setTextSize(20);
        title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setPadding(0,40,0,0);

        constructrorDialog.setCustomTitle(title);

        LinearLayout linearLayout = new LinearLayout(getActivity());

        final EditText emailET = new EditText(getActivity());
        emailET.setHint("Email");
        emailET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailET.setMinEms(20);

        linearLayout.addView(emailET);
        linearLayout.setPadding(120,10,120,10);

        constructrorDialog.setView(linearLayout);

        //Botón para recuperar password
        constructrorDialog.setPositiveButton(getString(R.string.recuperar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = emailET.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailET.setError("El formato del email no es correcto.");
                    emailET.setFocusable(true);
                } else {
                    IniciarRecuperacionPassword(email);
                }
            }
        });

        constructrorDialog.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog recuperacion = constructrorDialog.create();
        recuperacion.show();

        Objects.requireNonNull(recuperacion.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));

    }

    //Recuperación de contraseña
    private void IniciarRecuperacionPassword(String email) {
        mDialogCarga.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), null);

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    //Funciona correctamente
                    mDialogCarga.dismiss();
                    Toast.makeText(getActivity(), "Correo para recuperar contraseña enviado", Toast.LENGTH_LONG).show();

                }else {

                    //Da error el proceso
                    mDialogCarga.dismiss();
                    Toast.makeText(getActivity(), "Correo no enviado", Toast.LENGTH_LONG).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //Fallo en firebase
                mDialogCarga.dismiss();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    //Login del usuario
    private void LoginUsuari(String email, String pass) {

        //Carga del dialog y accion de login
        mDialogCarga.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), null);
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //Login funciona correctamente
                    mDialogCarga.dismiss();
                    startActivity(new Intent(getActivity(), MenuPersonajesActivity.class).putExtra("origen","login"));
                    getActivity().finish();

                }else{

                    //Login fallido
                    mDialogCarga.dismiss();
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //Error en Firebase
                mDialogCarga.dismiss();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}
