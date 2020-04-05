package com.example.rolplay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContenedorInicioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Declaración de variables
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private FirebaseUser mUsuario;
    private FirebaseDatabase mDatabase;
    public NavigationView navigationView;
    private TextView mNivelPersonajeNavBar, mNombrePersonaje, mCorreoElectronico;
    public int mNivel;
    private View headerView;
    private ArrayList<String> Razas = new ArrayList<String>();
    private ArrayList<String> Clases = new ArrayList<String>();
    private String[] listaRazas = new String[] {};
    private String[] listaClases = new String[] {};
    private String[] listaAlineamiento = new String[] {};
    private String Trasfondo, Alineamiento, Raza, Clase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_inicio);

        //Inicialización de variables
        mAuth = FirebaseAuth.getInstance();
        drawer = findViewById(R.id.drawer_layout);

        mUsuario = mAuth.getCurrentUser();

        //Posicionar en el JSON de Firebase
        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mRazas = mDatabase.getReference().child("DungeonAndDragons/Raza");
        final DatabaseReference mClases = mDatabase.getReference().child("DungeonAndDragons/Clases");
        //final DatabaseReference mAlineamiento = mDatabase.getReference().child("DungeonAndDragons/Alineamiento");

        //Activa la barra superior
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Inicia el fragment de Inicio
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment(), "inicio_fragment").commit();
            navigationView.setCheckedItem(R.id.nav_ficha);

        }

        //Declaración de variables de la cabecera de la barra de navegación
        headerView = navigationView.getHeaderView(0);
        mNivelPersonajeNavBar = headerView.findViewById(R.id.nav_header_nivelPersonaje);
        mNombrePersonaje = headerView.findViewById(R.id.nav_header_nombrePersonaje);
        mCorreoElectronico = headerView.findViewById(R.id.nav_header_correoElectronico);

        //Seteo de datos del header de la navegación lateral
        mDatabase.getReference("users/"+ Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mNombrePersonaje.setText((String)dataSnapshot.child("Nombre").getValue());
                Trasfondo = (String)dataSnapshot.child("Trasfondo").getValue();
                Alineamiento =(String)dataSnapshot.child("Alineamiento").getValue();
                Raza = (String)dataSnapshot.child("Raza").getValue();
                Clase = (String)dataSnapshot.child("Clase").getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mNivel = 4;
        //TODO: Poner el sistema de nivel correcto
        mNivelPersonajeNavBar.setText(getString(R.string.nivelPersonaje, Integer.toString(mNivel)));
        mCorreoElectronico.setText("psps@psps.com");

        //Cargar listas de los dropdowns
        //Razas
        cargarSpinners(mRazas, Razas, listaRazas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaRazas = value;
            }
        });
        //Clases
        cargarSpinners(mClases, Clases, listaClases, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaClases=value;
            }
        });
        //Alineamiento
        listaAlineamiento = new String[]{"Legal bueno", "Legal neutral", "Legal malvado", "Neutral bueno", "Neutral", "Neutral malvado", "Caótico bueno", "Caótico neutral", "Caótico malvado"};


    }

    //Funcion de lectura en FireBase i retorna String[] para el Dropdown
    private void cargarSpinners(DatabaseReference mDB, final ArrayList<String> ALS, final String[] SS, final MyCallback callback) {
        mDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] result = new String[] {};
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String valor = "" + ds.getKey();
                    ALS.add(valor);
                    result = ALS.toArray(SS);

                }
                callback.onCallback(result);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //TODO: Fragments de cada apartado

        switch (item.getItemId()) {

            case R.id.nav_ficha:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();
                break;
            case R.id.nav_cabecera:
                //Pasa los datos al fragment de destino
                Bundle bundle = new Bundle();
                bundle.putString("Nombre", (String) mNombrePersonaje.getText());
                bundle.putString("Trasfondo", Trasfondo);
                bundle.putString("Raza", Raza);
                bundle.putString("Clase", Clase);
                bundle.putString("Alineamiento", Alineamiento);
                bundle.putStringArray("Razas", listaRazas);
                bundle.putStringArray("Clases", listaClases);
                bundle.putStringArray("Alineamientos", listaAlineamiento);
                Fragment Cabecera = new CabeceraFragment();
                Cabecera.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Cabecera).addToBackStack(null).commit();
                break;
            case R.id.nav_puntosHabilidad:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PuntosHabilidadFragment()).commit();
                break;
            case R.id.nav_habilidadesBonificadores:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HabilidadesBonificadoresFragment()).commit();
                break;
            case R.id.nav_combate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CombateFragment()).commit();
                break;
            case R.id.nav_equipo:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EquipoFragment()).commit();
                break;
            case R.id.nav_ataquesConjuros:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AtaquesConjurosFragment()).commit();
                break;
            case R.id.nav_personalidad:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PersonalidadFragment()).commit();
                break;
            case R.id.nav_rasgosAtributos:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RasgosAtributosFragment()).commit();
                break;
            case R.id.nav_competenciasIdiomas:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CompetenciasIdiomasFragment()).commit();
                break;
            case R.id.nav_lanzarDados:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LanzarDadosFragment()).commit();
                break;
            case R.id.nav_salirPersonaje:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuPersonajesFragment()).commit();
                break;
            case R.id.nav_configuracion:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ConfiguracionFragment()).commit();
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //Si el usuario se encuentra en la pantalla de inicio o en la de login, se cierra la aplicación
            if (inicioFragment != null && inicioFragment.isVisible()) {
                cerrarApp();
            } else {
                //Si no, va al fragment de la ficha
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment(), "inicio_fragment").commit();
                navigationView.setCheckedItem(R.id.nav_ficha);
            }
        }

    }

    //Cierra la app correctamente
    public void cerrarApp() {
        this.finish();
        System.exit(0);
    }

    private void ComprobarEstatUsuari() {

        FirebaseUser usuari = mAuth.getCurrentUser();

        if (usuari != null) {
            //TODO: Cargar datos de Firebase respectivos a la ficha
        } else {
            //Pasa a la pantalla de Login si el usuario no está logueado
            startActivity(new Intent(this, MainActivity.class));
            this.finish();
        }
    }

    public void modificarNavegacionLateral(String apartado) {

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
            case "equipo":
                navigationView.setCheckedItem(R.id.nav_equipo);
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
            default:
                navigationView.setCheckedItem(R.id.nav_ficha);
                break;
        }

    }

}
