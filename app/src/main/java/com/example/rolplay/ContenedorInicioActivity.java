package com.example.rolplay;

import android.content.Context;
import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
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

import com.google.android.gms.common.util.ArrayUtils;
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
    private View headerView;
    private ArrayList<String> Razas = new ArrayList<String>();
    private ArrayList<String> Clases = new ArrayList<String>();
    private ArrayList<String> Objetos = new ArrayList<String>();
    private String[] listaRazas = new String[] {};
    private String[] listaClases = new String[] {};
    private String[] listaAlineamiento = new String[] {};
    private String[] listaObjetos = new String[] {};
    private String[] listaArmaduras = new String[] {};
    private String[] listaArmas = new String[] {};
    private String[] listaHerramientas = new String[] {};
    private String[] listaMercancias = new String[] {};
    private String[] listaMisceláneo = new String[] {};
    private String[] listaMonturas = new String[] {};
    private String[] listaArmadurasLigeras = new String[] {};
    private String[] listaArmadurasMedias = new String[] {};
    private String[] listaArmadurasPesadas = new String[] {};
    private String[] listaArmasDM = new String[] {};
    private String[] listaArmasDS = new String[] {};
    private String[] listaArmasCM = new String[] {};
    private String[] listaArmasCS = new String[] {};
    private String Trasfondo, Alineamiento, Raza, Clase, ClaseDeArmadura, Iniciativa, Velocidad,
            PuntosGolpeActuales, PuntosGolpeMaximos, PuntosGolpeTemporales, DadoGolpe, TotalDadoGolpe;
    private int SalvacionesMuerte, mNivel, PuntosExperiencia, PCobre, PPlata, PEsmeralda, POro, PPlatino;

    private DialogCarga mDialogCarga;

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
        final DatabaseReference mCombate = mDatabase.getReference().child("DungeonAndDragons/Plantilla/Combate");
        final DatabaseReference mObjetos = mDatabase.getReference("DungeonAndDragons/Objeto");
        //final DatabaseReference mAlineamiento = mDatabase.getReference().child("DungeonAndDragons/Alineamiento");

        //Activa la barra superior
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        ComprobarEstatUsuari();
        
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
        mDialogCarga = new DialogCarga();

        //Seteo de datos del header de la navegación lateral
        mDatabase.getReference("users/"+ Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mNombrePersonaje.setText((String)dataSnapshot.child("Nombre").getValue());
                Trasfondo = (String)dataSnapshot.child("Trasfondo").getValue();
                Alineamiento =(String)dataSnapshot.child("Alineamiento").getValue();
                Raza = (String)dataSnapshot.child("Raza").getValue();
                Clase = (String)dataSnapshot.child("Clase").getValue();
                mNivel = Integer.parseInt((String)dataSnapshot.child("Nivel").getValue());
                PuntosExperiencia = Integer.parseInt((String)dataSnapshot.child("Puntos de Experiencia").getValue());
                ClaseDeArmadura = (String)dataSnapshot.child("Clase de Armadura").getValue();
                Iniciativa =(String)dataSnapshot.child("Iniciativa").getValue();
                Velocidad = (String)dataSnapshot.child("Velocidad").getValue();
                PuntosGolpeActuales = (String)dataSnapshot.child("Puntos de Golpe Actuales").getValue();
                PuntosGolpeMaximos = (String)dataSnapshot.child("Puntos de Golpe Máximos").getValue();
                PuntosGolpeTemporales =(String)dataSnapshot.child("Puntos de Golpe Temporales").getValue();
                DadoGolpe = (String)dataSnapshot.child("Dado de Golpe/Valor").getValue();
                TotalDadoGolpe = (String)dataSnapshot.child("Dado de Golpe/Total").getValue();
                SalvacionesMuerte = Integer.parseInt((String)dataSnapshot.child("Salvaciones de Muerte").getValue());
                PCobre = Integer.parseInt((String)dataSnapshot.child("Piezas de cobre").getValue());
                PPlata = Integer.parseInt((String)dataSnapshot.child("Piezas de plata").getValue());
                PEsmeralda = Integer.parseInt((String)dataSnapshot.child("Piezas de esmeralda").getValue());
                POro = Integer.parseInt((String)dataSnapshot.child("Piezas de oro").getValue());
                PPlatino = Integer.parseInt((String)dataSnapshot.child("Piezas de platino").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mNivelPersonajeNavBar.setText(getString(R.string.nivelPersonaje, Integer.toString(mNivel)));
        mCorreoElectronico.setText(mUsuario.getEmail());

        //Cargar listas de los dropdowns
        mDialogCarga.show(getSupportFragmentManager(), null);
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

        cargarSpinners(mObjetos, Objetos, listaObjetos, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaObjetos = value;
            }
        });
        cargarSpinners(mObjetos.child("Armaduras"), Objetos, listaArmaduras, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaArmaduras = value;
            }
        });

        cargarSpinners(mObjetos.child("Armaduras/Armaduras Ligeras"), Objetos, listaArmadurasLigeras, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaArmadurasLigeras = value;
            }
        });
        cargarSpinners(mObjetos.child("Armaduras/Armaduras Medias"), Objetos, listaArmadurasMedias, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaArmadurasMedias = value;
            }
        });
        cargarSpinners(mObjetos.child("Armaduras/Armaduras Pesadas"), Objetos, listaArmadurasPesadas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaArmadurasPesadas = value;
            }
        });
        cargarSpinners(mObjetos.child("Armaduras/Escudos"), Objetos, listaObjetos, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaObjetos = value;
            }
        });
        cargarSpinners(mObjetos.child("Armas"), Objetos, listaArmas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaArmas = value;
            }
        });
        cargarSpinners(mObjetos.child("Armas/Armas a distancia marciales"), Objetos, listaArmasDM, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaArmasDM = value;
            }
        });
        cargarSpinners(mObjetos.child("Armas/Armas a distancia simples"), Objetos, listaArmasDS, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaArmasDS = value;
            }
        });
        cargarSpinners(mObjetos.child("Armas/Armas cuerpo cuerpo marciales"), Objetos, listaArmasCM, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaArmasCM = value;
            }
        });
        cargarSpinners(mObjetos.child("Armas/Armas cuerpo cuerpo simples"), Objetos, listaArmasCS, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaArmasCS = value;
            }
        });
        cargarSpinners(mObjetos.child("Herramientas/Herramientas de artesano"), Objetos, listaHerramientas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaHerramientas = value;
            }
        });
        cargarSpinners(mObjetos.child("Herramientas/Instrumentos musicales"), Objetos, listaHerramientas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaHerramientas = value;
            }
        });
        cargarSpinners(mObjetos.child("Herramientas/Set de juego"), Objetos, listaHerramientas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaHerramientas = value;
            }
        });
        cargarSpinners(mObjetos.child("Herramientas"), Objetos, listaHerramientas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaHerramientas = value;
            }
        });
        cargarSpinners(mObjetos.child("Mercancias"), Objetos, listaMercancias, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMercancias = value;
            }
        });
        cargarSpinners(mObjetos.child("Misceláneo"), Objetos, listaMisceláneo, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMisceláneo = value;
                mDialogCarga.dismiss();
            }
        });
        cargarSpinners(mObjetos.child("Monturas y Vehículos/Arreos, Guarniciones y Vehículos de Tiro"), Objetos, listaMonturas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMonturas = value;
            }
        });
        cargarSpinners(mObjetos.child("Monturas y Vehículos/Monturas y Otros Animales"), Objetos, listaMonturas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMonturas = value;
            }
        });
        cargarSpinners(mObjetos.child("Monturas y Vehículos/Vehículos Acuáticos"), Objetos, listaMonturas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMonturas = value;
            }
        });

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
        Bundle bundle;
        switch (item.getItemId()) {

            case R.id.nav_ficha:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioFragment()).commit();
                break;
            case R.id.nav_cabecera:
                //Pasa los datos al fragment de destino
                bundle = new Bundle();
                bundle.putString("Nombre", (String) mNombrePersonaje.getText());
                bundle.putString("Trasfondo", Trasfondo);
                bundle.putString("Raza", Raza);
                bundle.putString("Clase", Clase);
                bundle.putString("Alineamiento", Alineamiento);
                bundle.putInt("Nivel", mNivel);
                bundle.putInt("Puntos de Experiencia", PuntosExperiencia);
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
                //Pasa los datos al fragment de destino
                bundle = new Bundle();
                bundle.putString("Clase de Armadura", ClaseDeArmadura);
                bundle.putString("Iniciativa", Iniciativa);
                bundle.putString("Velocidad", Velocidad);
                bundle.putString("Puntos de Golpe Actuales", PuntosGolpeActuales);
                bundle.putString("Puntos de Golpe Máximos", PuntosGolpeMaximos);
                bundle.putString("Puntos de Golpe Temporales", PuntosGolpeTemporales);
                bundle.putString("Dado de Golpe/Valor", DadoGolpe);
                bundle.putString("Dado de Golpe/Total", TotalDadoGolpe);
                bundle.putInt("Salvacion", SalvacionesMuerte);
                Fragment Combate = new CombateFragment();
                Combate.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Combate).addToBackStack(null).commit();
                break;
            case R.id.nav_equipo:
                bundle = new Bundle();
                bundle.putStringArray("Lista De Objetos",listaObjetos);
                bundle.putStringArray("Lista De ArmLig",listaArmadurasLigeras);
                bundle.putStringArray("Lista De ArmMed",listaArmadurasMedias);
                bundle.putStringArray("Lista De ArmPes",listaArmadurasPesadas);
                bundle.putStringArray("Lista De ArmDM",listaArmasDM);
                bundle.putStringArray("Lista De ArmDS",listaArmasDS);
                bundle.putStringArray("Lista De ArmCM",listaArmasCM);
                bundle.putStringArray("Lista De ArmCS",listaArmasCS);
                bundle.putStringArray("Lista De Herram",listaHerramientas);
                bundle.putStringArray("Lista De Merc",listaMercancias);
                bundle.putStringArray("Lista De Misc",listaMisceláneo);
                bundle.putStringArray("Lista De Mont",listaMonturas);
                bundle.putStringArray("Lista De Armaduras",listaArmaduras);
                bundle.putStringArray("Lista De Armas",listaArmas);
                bundle.putInt("Piezas de cobre", PCobre);
                bundle.putInt("Piezas de plata", PPlata);
                bundle.putInt("Piezas de esmeralda", PEsmeralda);
                bundle.putInt("Piezas de oro", POro);
                bundle.putInt("Piezas de platino", PPlatino);
                Fragment Equipo = new EquipoFragment();
                Equipo.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Equipo).addToBackStack(null).commit();
                break;
            case R.id.nav_ataquesConjuros:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AtaquesConjurosFragment()).commit();
                break;
            case R.id.nav_personalidad:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PersonalidadFragment()).commit();
                break;
            case R.id.nav_rasgosAtributos:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RasgosAtributosFragment()).commit();
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
