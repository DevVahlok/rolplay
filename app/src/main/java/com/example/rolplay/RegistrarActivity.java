package com.example.rolplay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrarActivity extends AppCompatActivity {

    //Declaración de variables
    private TextView mTitulo;
    private Button mBotonRegistrar;
    private TextInputEditText mTextInputCorreo, mTextInputPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //Inicialización de variables
        mTitulo = findViewById(R.id.RegistrarActivity_titulo);
        mBotonRegistrar = findViewById(R.id.RegistrarActivity_registrar_btn);
        mTextInputCorreo = findViewById(R.id.RegistrarActivity_email_et);
        mTextInputPassword = findViewById(R.id.RegistrarActivity_password_et);
        mAuth = FirebaseAuth.getInstance();

        mBotonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //TODO: Botón hacia atrás en barra superior (o no incluir barra superior???)
        //TODO: Comprobación de 2 inputs contraseña sean iguales
        //TODO: Registro en FireBase

    }
}
