package com.example.rolplay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrarActivity extends AppCompatActivity {

    //Declaración de variables
    private TextView mTitulo;
    private Button mBotonRegistrar;
    private TextInputEditText mTextInputCorreo, mTextInputPassword, mTextInputConfirmPassword;
    private FirebaseAuth mAuth;
    private DialogCarga mDialogCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //Inicialización de variables
        mTitulo = findViewById(R.id.RegistrarActivity_titulo);
        mBotonRegistrar = findViewById(R.id.RegistrarActivity_registrar_btn);
        mTextInputCorreo = findViewById(R.id.RegistrarActivity_email_et);
        mTextInputPassword = findViewById(R.id.RegistrarActivity_password_et);
        mTextInputConfirmPassword = findViewById(R.id.RegistrarActivity_confirmPassword_et);
        mAuth = FirebaseAuth.getInstance();
        mDialogCarga = new DialogCarga();

        mBotonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mTextInputCorreo.getText().toString();
                String pass = mTextInputPassword.getText().toString();
                String confirmpass = mTextInputConfirmPassword.getText().toString();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mTextInputCorreo.setError("El formato del email no es correcto.");
                    mTextInputCorreo.setFocusable(true);
                }else if(!pass.equals(confirmpass)){
                    mTextInputPassword.setError("No coinciden las contraseñas");
                    mTextInputPassword.setFocusable(true);
                    mTextInputConfirmPassword.setFocusable(true);
                }else{
                    RegistrarUsuari(email, pass);
                }
            }
        });

        //TODO: Botón hacia atrás en barra superior (o no incluir barra superior???)
        //TODO: Comprobar longitud de 6 carácteres antes de que salga el error default de Google

    }

    private void RegistrarUsuari(String email, String pass) {
        mDialogCarga.show(getSupportFragmentManager(),null);
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            mDialogCarga.dismiss();
                            startActivity(new Intent(RegistrarActivity.this, MainActivity.class));

                            Toast.makeText(RegistrarActivity.this, "OK", Toast.LENGTH_SHORT).show();

                            finish();

                        }else{
                            mDialogCarga.dismiss();
                            Toast.makeText(RegistrarActivity.this, "Creación de usuario fallida", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mDialogCarga.dismiss();
                Toast.makeText(RegistrarActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
