package com.example.rolplay.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.rolplay.Login.LoginFragment;
import com.example.rolplay.R;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicia el fragment de Inicio
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LoginFragment(), "login_fragment").commit();

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
