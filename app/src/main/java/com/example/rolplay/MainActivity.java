package com.example.rolplay;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private DialogCarga mDialogCarga;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de variables
        mTitulo = findViewById(R.id.MainActivity_titulo);
        mTextInputCorreo = findViewById(R.id.MainActivity_email_et);
        mTextInputPassword = findViewById(R.id.MainActivity_password_et);

        mBotonEntrar = findViewById(R.id.MainActivity_entrar_btn);
        mBotonRegistrar = findViewById(R.id.MainActivity_registrar_btn);
        mBotonRecuperarPassword = findViewById(R.id.MainActivity_recuperar_password_btn);

        mAuth = FirebaseAuth.getInstance();
        mDialogCarga = new DialogCarga();

        //Cambio de pantalla a 'LoginActivity'
        mBotonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mTextInputCorreo.getText().toString();
                String pass = mTextInputPassword.getText().toString();

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
                startActivity(new Intent(MainActivity.this,RegistrarActivity.class));
            }
        });

        mBotonRecuperarPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MostrarRecuperarPassword();
            }
        });
    }

    //Funcion para crear dinamicamente un dialog
    private void MostrarRecuperarPassword() {

        AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(this);

        TextView title = new TextView(this);
        title.setText("Recuperar password");
        title.setTextColor(getColor(R.color.colorPrimary));
        title.setTextSize(20);
        title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setPadding(0,40,0,0);

        constructrorDialog.setCustomTitle(title);

        LinearLayout linearLayout = new LinearLayout(this);

        final EditText emailET = new EditText(this);
        emailET.setHint("Email");
        emailET.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailET.setMinEms(20);

        linearLayout.addView(emailET);
        linearLayout.setPadding(120,10,120,10);

        constructrorDialog.setView(linearLayout);

        //Boton para recuperar password
        constructrorDialog.setPositiveButton("Recuperar", new DialogInterface.OnClickListener() {
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

        constructrorDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog recuperacion = constructrorDialog.create();
        recuperacion.show();

        recuperacion.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));

    }

    //Recuperacion de contraseña
    private void IniciarRecuperacionPassword(String email) {
        mDialogCarga.show(getSupportFragmentManager(), null);

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    //Funciona correctamente
                    mDialogCarga.dismiss();
                    Toast.makeText(MainActivity.this, "Correo para recuperar contraseña enviado", Toast.LENGTH_LONG).show();

                }else {

                    //Da error el proceso
                    mDialogCarga.dismiss();
                    Toast.makeText(MainActivity.this, "Correo no enviado", Toast.LENGTH_LONG).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //Fallo en firebase
                mDialogCarga.dismiss();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    //Login del usuario
    private void LoginUsuari(String email, String pass) {
        //Carga del dialog y accion de login
        mDialogCarga.show(getSupportFragmentManager(), null);
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //Login funciona correctamente
                    mDialogCarga.dismiss();
                    startActivity(new Intent(MainActivity.this,InicioActivity.class));

                }else{

                    //Login fallido
                    mDialogCarga.dismiss();
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //Error en Firebase
                mDialogCarga.dismiss();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
