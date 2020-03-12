package com.example.rolplay;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    //Declaración de variables
    private InicioFragment mInicioFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicialización de variables
        mInicioFragment = new InicioFragment();

        //Inicia el fragment de Inicio
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mInicioFragment,"inicio_fragment").commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onBackPressed() {

        //Recupera el fragment por tag
        Fragment inicioFragment = getSupportFragmentManager().findFragmentByTag("inicio_fragment");
        Fragment loginFragment = getSupportFragmentManager().findFragmentByTag("login_fragment");

        //Si el usuario se encuentra en la pantalla de inicio o en la de login, se cierra la aplicación
        if (inicioFragment!=null && inicioFragment.isVisible()){
            cerrarApp();
        }else if(loginFragment!=null && loginFragment.isVisible()) {
            cerrarApp();
        }else {
            //Si no, va al anterior fragment
            getSupportFragmentManager().popBackStack();
        }

    }

    //Cierra la app correctamente
    public void cerrarApp(){
        this.finish();
        System.exit(0);
    }

}
