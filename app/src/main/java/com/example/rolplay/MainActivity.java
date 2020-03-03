package com.example.rolplay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables
    private TextView mTitulo;
    private Button mBotonRegistrar, mBotonEntrar, mBotonRecuperarPassword;
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
        mBotonRecuperarPassword = findViewById(R.id.MainActivity_recuperar_password_btn);
        mAuth = FirebaseAuth.getInstance();

        //Cambio de pantalla a 'LoginActivity'
        mBotonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mTextInputCorreo.getText().toString();
                String pass = mTextInputPassword.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mTextInputCorreo.setError("El formato del email no es correcto.");
                    mTextInputCorreo.setFocusable(true);
                }else{
                    LoginUsuari(email, pass);
                }
            }
        });

        //Cambio de pantalla a 'RegistrarActivity'
        mBotonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistrarActivity.class));
            }
        });

        mBotonRecuperarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //TODO: DialogCarga al iniciar sesión

    }

    private void LoginUsuari(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,InicioActivity.class));
                }else{
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
