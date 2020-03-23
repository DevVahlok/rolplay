package com.example.rolplay;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ContenedorInicioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Declaración de variables
    private InicioFragment mInicioFragment;
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_inicio);

        //Inicialización de variables
        mInicioFragment = new InicioFragment();
        mAuth = FirebaseAuth.getInstance();
        drawer = findViewById(R.id.drawer_layout);

        //Activa la barra superior
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Inicia el fragment de Inicio
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mInicioFragment, "inicio_fragment").commit();
            navigationView.setCheckedItem(R.id.nav_ficha);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //TODO: Fragments de cada apartado

        switch (item.getItemId()){

            case R.id.nav_ficha:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();
                break;
            case R.id.nav_cabecera:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CabeceraFragment()).commit();
                break;
            case R.id.nav_puntosHabilidad:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PuntosHabilidadFragment()).commit();
                break;
            case R.id.nav_habilidadesBonificadores:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HabilidadesBonificadoresFragment()).commit();
                break;
            case R.id.nav_combate:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CombateFragment()).commit();
                break;
            case R.id.nav_ataquesConjuros:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AtaquesConjurosFragment()).commit();
                break;
            case R.id.nav_personalidad:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PersonalidadoFragment()).commit();
                break;
            case R.id.nav_rasgosAtributos:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RasgosAtributosFragment()).commit();
                break;
            case R.id.nav_competenciasIdiomas:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CompetenciasIdiomasFragment()).commit();
                break;
            case R.id.nav_lanzarDados:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LanzarDadosFragment()).commit();
                break;
            case R.id.nav_salirPersonaje:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuPersonajesFragment()).commit();
                break;
            case R.id.nav_politicasCondiciones:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PoliticasCondicionesFragment()).commit();
                break;
            case R.id.nav_logout:
                //TODO: Dialog preguntando si está segur@ de cerrar sesión.
                mAuth.signOut();
                ComprobarEstatUsuari();
                break;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        //Recupera el fragment por tag
        Fragment inicioFragment = getSupportFragmentManager().findFragmentByTag("inicio_fragment");

        //Cierra el menú lateral si está abierto
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            //Si el usuario se encuentra en la pantalla de inicio o en la de login, se cierra la aplicación
            if (inicioFragment!=null && inicioFragment.isVisible()){
                cerrarApp();
            }else {
                //Si no, va al fragment de la ficha
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment(),"inicio_fragment").commit();
                navigationView.setCheckedItem(R.id.nav_ficha);
            }
        }

    }

    //Cierra la app correctamente
    public void cerrarApp(){
        this.finish();
        System.exit(0);
    }

    private void ComprobarEstatUsuari() {

        FirebaseUser usuari = mAuth.getCurrentUser();

        if (usuari!=null){
            //TODO: Cargar datos de Firebase respectivos a la ficha
        }else{
            //Pasa a la pantalla de Login si el usuario no está logueado
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }
    }

    public void modificarNavegacionLateral(String apartado){

        //Pone como seleccionado el apartado del botón pulsado de la ficha
        switch (apartado) {
            case "cabecera":
                navigationView.setCheckedItem(R.id.nav_cabecera);
                break;
            case "puntosHabilidad":
                navigationView.setCheckedItem(R.id.nav_puntosHabilidad);
                break;
            case "habilidadesBonificadores":
                navigationView.setCheckedItem(R.id.nav_habilidadesBonificadores);
                break;
            case "combate":
                navigationView.setCheckedItem(R.id.nav_combate);
                break;
            case "ataquesConjuros":
                navigationView.setCheckedItem(R.id.nav_ataquesConjuros);
                break;
            case "personalidad":
                navigationView.setCheckedItem(R.id.nav_personalidad);
                break;
            case "rasgosAtributos":
                navigationView.setCheckedItem(R.id.nav_rasgosAtributos);
                break;
            case "competenciasIdiomas":
                navigationView.setCheckedItem(R.id.nav_competenciasIdiomas);
                break;
            case "lanzarDados":
                navigationView.setCheckedItem(R.id.nav_lanzarDados);
                break;
            default:
                navigationView.setCheckedItem(R.id.nav_ficha);
                break;
        }

    }

}
