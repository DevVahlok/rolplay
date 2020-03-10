package com.example.rolplay;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables
    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de variables
        mLoginFragment = new LoginFragment();

        //Inicia el fragment del Login
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentActual, mLoginFragment).commit();

        //TODO: Hacer que al darle hacia atrás te devuelva a InicioFragment

    }

}
