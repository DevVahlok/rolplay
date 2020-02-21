package com.example.rolplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables
    private TextView mTitulo;
    private Button mBotonRegistrar, mBotonEntrar;
    private TextInputEditText mTextInputCorreo, mTextInputPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de variables
        mTitulo = findViewById(R.id.MainActivity_titulo);
        mBotonEntrar = findViewById(R.id.MainActivity_entrar_btn);
        mBotonRegistrar = findViewById(R.id.MainActivity_registrar_btn);
        mTextInputCorreo = findViewById(R.id.MainActivity_email_et);
        mTextInputPassword = findViewById(R.id.MainActivity_password_et);
        mAuth = FirebaseAuth.getInstance();

        //Cambio de pantalla a 'LoginActivity'
        mBotonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //Cambio de pantalla a 'RegistrarActivity'
        mBotonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrarActivity.class));
            }
        });

        //TO DO: Crear activity Inicio (cuando ya haya iniciado sesión)
        //TO DO: startActivity de Inicio
        //TO DO: Comprobar login en Firebase

    }
}
