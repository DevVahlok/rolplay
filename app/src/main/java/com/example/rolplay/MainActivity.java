package com.example.rolplay;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables
    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de variables
        mLoginFragment = new LoginFragment();

        //Inicia el fragment de Inicio
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mLoginFragment, "login_fragment").commit();

    }

    @Override
    public void onBackPressed() {

        //Recupera el fragment por tag
        Fragment loginFragment = getSupportFragmentManager().findFragmentByTag("login_fragment");

        //Si el usuario se encuentra en la pantalla de inicio o en la de login, se cierra la aplicación
        if (loginFragment != null && loginFragment.isVisible()) {
            cerrarApp();
        } else {
            //Si no, va al anterior fragment
            getSupportFragmentManager().popBackStack();
        }

    }

    //Cierra la app correctamente
    public void cerrarApp() {
        this.finish();
        System.exit(0);
    }

}
