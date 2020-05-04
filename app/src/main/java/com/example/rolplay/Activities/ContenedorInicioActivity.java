package com.example.rolplay.Activities;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.rolplay.Ficha.AtaquesConjurosFragment;
import com.example.rolplay.Ficha.CabeceraFragment;
import com.example.rolplay.Ficha.CombateFragment;
import com.example.rolplay.Ficha.CompetenciasIdiomasFragment;
import com.example.rolplay.Ficha.EquipoFragment;
import com.example.rolplay.Ficha.HabilidadesBonificadoresFragment;
import com.example.rolplay.Ficha.InicioFragment;
import com.example.rolplay.Ficha.PersonalidadFragment;
import com.example.rolplay.Ficha.PuntosHabilidadFragment;
import com.example.rolplay.Ficha.RasgosAtributosFragment;
import com.example.rolplay.Otros.CreateNotification;
import com.example.rolplay.Otros.DialogCarga;
import com.example.rolplay.Otros.MyCallback;
import com.example.rolplay.Otros.OnClearFromRecentService;
import com.example.rolplay.Otros.Playable;
import com.example.rolplay.Otros.Track;
import com.example.rolplay.R;
import com.example.rolplay.Servicios.ConfiguracionFragment;
import com.example.rolplay.Servicios.LanzarDadosFragment;
import com.example.rolplay.Servicios.MenuPersonajesActivity;
import com.example.rolplay.Servicios.ReproductorMusicaFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class ContenedorInicioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Playable {

    //Declaración de variables
    private DrawerLayout drawer;
    private FirebaseAuth mAuth;
    private FirebaseUser mUsuario;
    private FirebaseDatabase mDatabase;
    public NavigationView navigationView;
    private ProgressBar mProgressBar;
    private TextView mNivelPersonajeNavBar, mNombrePersonaje, mCorreoElectronico;
    private View headerView;
    private ArrayList<String> Razas = new ArrayList<>();
    private ArrayList<String> Clases = new ArrayList<>();
    private ArrayList<String> Alineamientos = new ArrayList<>();
    private ArrayList<String> Objetos = new ArrayList<>();
    private ArrayList<String> Equipo = new ArrayList<>();
    private ArrayList<String> Ataque = new ArrayList<>();
    private ArrayList<String> Rasgos = new ArrayList<>();
    private ArrayList<String> Conjuro = new ArrayList<>();
    private ArrayList<String> dados4 = new ArrayList<>();
    private ArrayList<String> dados6 = new ArrayList<>();
    private ArrayList<String> dados8 = new ArrayList<>();
    private ArrayList<String> dados10 = new ArrayList<>();
    private ArrayList<String> dados12 = new ArrayList<>();
    private ArrayList<String> dados20 = new ArrayList<>();
    private String[] listaRazas = new String[]{};
    private String[] listaClases = new String[]{};
    private String[] listaAlineamiento = new String[]{};
    private String[] listaObjetos = new String[]{};
    private String[] listaArmaduras = new String[]{};
    private String[] listaArmas = new String[]{};
    private String[] listaHerramientas = new String[]{};
    private String[] listaMercancias = new String[]{};
    private String[] listaMisceláneo = new String[]{};
    private String[] listaMonturas = new String[]{};
    private String[] listaMonturasAGV = new String[]{};
    private String[] listaMonturasMOA = new String[]{};
    private String[] listaMonturasVA = new String[]{};
    private String[] listaArmadurasLigeras = new String[]{};
    private String[] listaArmadurasMedias = new String[]{};
    private String[] listaArmadurasPesadas = new String[]{};
    private String[] listaArmasDM = new String[]{};
    private String[] listaArmasDS = new String[]{};
    private String[] listaArmasCM = new String[]{};
    private String[] listaArmasCS = new String[]{};
    private String Trasfondo, Alineamiento, Raza, Clase, ClaseDeArmadura, Iniciativa, Velocidad,
            PuntosGolpeActuales, PuntosGolpeMaximos, PuntosGolpeTemporales, DadoGolpe, TotalDadoGolpe,
            RasgosPersonalidad, Ideales, Defectos, Vinculos, mFuerzaPuntos, mDestrezaPuntos, mConstitucionPuntos,
            mInteligenciaPuntos, mSabiduriaPuntos, mCarismaPuntos, mFuerzaBonus, mDestrezaBonus, mConstitucionBonus,
            mInteligenciaBonus, mSabiduriaBonus, mCarismaBonus, mAcrobaciasCB, mAtletismoCB, mConocimientoCB, mEnganyoCB, mHistoriaCB, mInterpretacionCB,
            mIntimidacionCB, mInvestigacionCB, mJuegoManosCB, mMedicinaCB, mNaturalezaCB, mPercepcionCB, mPerspicacioCB,
            mPersuasionCB, mReligionCB, mSigiloCB, mSupervivenciaCB, mTratoAnimalesCB, mInspiracion, mBonificador, mSabiduria,
            mIdiomas, mArmadura, mArmas, mHerramientas, mEspecialidad, mRangoMilitar, mOtras, codigoPersonaje = "A", mFuerzaCB, mDestrezaCB,
            mConstitucionCB, mInteligenciaCB, mSabiduriaCB, mCarismaCB, mCorreo, mSonido;
    private int SalvacionesMuerte, mNivel, PuntosExperiencia, PCobre, PPlata, PEsmeralda, POro, PPlatino, PesoTotal;
    private DialogCarga mDialogCarga;
    private boolean recordarMenuInterno = false;
    public static List<Track> tracks;
    public static ArrayList listaCanciones;
    public static int cancionSeleccionada = 0;
    public static final String CHANNEL_ID = "channel1";
    public static final String ACTION_PREVIOUS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";
    public static NotificationChannel channel;
    public static int position = 0;
    public static boolean isPlaying = false;
    private static NotificationManager notificationManager;
    public static MediaPlayer mMediaPlayer;
    public static Runnable runnable;
    public static Handler handler;
    public static SoundPool mSoundPool;
    public static int mSonidoDado1 = 0, mSonidoDado2 = 0, mSonidoDado3 = 0;
    public static ArrayList<Integer> listaSonidos;
    public static float volumen = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor_inicio);

        mDialogCarga = new DialogCarga();
        mDialogCarga.show(getSupportFragmentManager(), null);
        mDialogCarga.setCancelable(false);

        //Inicialización de variables
        mAuth = FirebaseAuth.getInstance();
        ComprobarEstatUsuari();
        drawer = findViewById(R.id.drawer_layout);

        mUsuario = mAuth.getCurrentUser();

        //Administración de los sonidos de los dados
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
        mSonidoDado1 = mSoundPool.load(this, R.raw.dado1, 1);
        mSonidoDado2 = mSoundPool.load(this, R.raw.dado2, 1);
        mSonidoDado3 = mSoundPool.load(this, R.raw.dado3, 1);

        listaSonidos = new ArrayList<>();
        listaSonidos.add(mSonidoDado1);
        listaSonidos.add(mSonidoDado2);
        listaSonidos.add(mSonidoDado3);


        codigoPersonaje = getIntent().getStringExtra("codigo");

        //Posicionar en el JSON de Firebase
        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mRazas = mDatabase.getReference().child("DungeonAndDragons/Raza");
        final DatabaseReference mClases = mDatabase.getReference().child("DungeonAndDragons/Clases");
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

        final Activity activity = this;
        final boolean[] entrado = {false};

        if (mAuth.getCurrentUser() != null) {
            mDatabase.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/Recordar menu").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    recordarMenuInterno = (Boolean) dataSnapshot.getValue();
                    if (!entrado[0]) {
                        if (recordarMenuInterno) {
                            startActivity(new Intent(ContenedorInicioActivity.this, MenuPersonajesActivity.class).putExtra("origen", "login"));
                            RecordarMenu(new HashMap<String, Object>());
                            entrado[0] = true;
                            activity.finish();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //Inicia el fragment de Inicio
        if (savedInstanceState == null) {

            //Si viene de Login o del manú lateral, redirige a MenuPersonajesActivity. Si no, redirige a la ficha
            if (Objects.equals(getIntent().getStringExtra("origen"), "seleccionPersonaje")) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser usuariActual = mAuth.getCurrentUser();

                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("Nombre", "");
                hashMap.put("Raza", "");
                hashMap.put("Trasfondo", "");
                hashMap.put("Alineamiento", "");
                hashMap.put("Nivel", "1");
                hashMap.put("Puntos de Experiencia", "0");
                hashMap.put("Clase de Armadura", "0");
                hashMap.put("Iniciativa", "");
                hashMap.put("Velocidad", "");
                hashMap.put("Puntos de Golpe Actuales", "0");
                hashMap.put("Puntos de Golpe Máximos", "100");
                hashMap.put("Puntos de Golpe Temporales", "2");
                hashMap.put("Dado de Golpe/Valor", "");
                hashMap.put("Dado de Golpe/Total", "");
                hashMap.put("Salvaciones de Muerte", "0");
                hashMap.put("Idiomas", "");
                hashMap.put("Armadura", "");
                hashMap.put("Armas", "");
                hashMap.put("Herramientas", "");
                hashMap.put("Especialidad", "");
                hashMap.put("Rango militar", "");
                hashMap.put("Otras", "");
                hashMap.put("Equipo", "");
                hashMap.put("Ataque", "");
                hashMap.put("Conjuro", "");
                hashMap.put("Piezas de cobre", "0");
                hashMap.put("Piezas de plata", "0");
                hashMap.put("Piezas de esmeralda", "0");
                hashMap.put("Piezas de oro", "0");
                hashMap.put("Piezas de platino", "0");
                hashMap.put("Peso total", "0");
                hashMap.put("AcrobaciasCB", false);
                hashMap.put("AtletismoCB", false);
                hashMap.put("ConocimientoCB", false);
                hashMap.put("EngañoCB", false);
                hashMap.put("HistoriaCB", false);
                hashMap.put("InterpretacionCB", false);
                hashMap.put("IntimidacionCB", false);
                hashMap.put("InvestigacionCB", false);
                hashMap.put("JuegoManosCB", false);
                hashMap.put("MedicinaCB", false);
                hashMap.put("NaturalezaCB", false);
                hashMap.put("PercepcionCB", false);
                hashMap.put("PerspicaciaCB", false);
                hashMap.put("PersuasionCB", false);
                hashMap.put("ReligionCB", false);
                hashMap.put("SigiloCB", false);
                hashMap.put("SupervivenciaCB", false);
                hashMap.put("TratoAnimalesCB", false);
                hashMap.put("FuerzaCB", false);
                hashMap.put("DestrezaCB", false);
                hashMap.put("ConstitucionCB", false);
                hashMap.put("InteligenciaCB", false);
                hashMap.put("SabiduriaCB", false);
                hashMap.put("CarismaCB", false);
                hashMap.put("Inspiracion", "0");
                hashMap.put("Bonificador Competencia", "0");
                hashMap.put("Sabiduria Pasiva", "0");
                hashMap.put("Rasgos de Personalidad", "");
                hashMap.put("Ideales", "");
                hashMap.put("Vínculos", "");
                hashMap.put("Defectos", "");
                hashMap.put("Fuerza puntos", "");
                hashMap.put("Fuerza bonus", "");
                hashMap.put("Destreza puntos", "");
                hashMap.put("Destreza bonus", "");
                hashMap.put("Constitucion puntos", "");
                hashMap.put("Constitucion bonus", "");
                hashMap.put("Inteligencia puntos", "");
                hashMap.put("Inteligencia bonus", "");
                hashMap.put("Sabiduria puntos", "");
                hashMap.put("Sabiduria bonus", "");
                hashMap.put("Carisma puntos", "");
                hashMap.put("Carisma bonus", "");
                hashMap.put("Rasgos", "");
                hashMap.put("Foto", "");

                mDatabase.getReference("users/" + usuariActual.getUid() + "/" + codigoPersonaje).updateChildren(hashMap);

                HashMap<String, Object> ultimo = new HashMap<>();

                ultimo.put("Ultimo personaje", codigoPersonaje);
                mDatabase.getReference("users/" + usuariActual.getUid()).updateChildren(ultimo);

                Bundle bundle = new Bundle();
                bundle.putString("Nombre", "");
                bundle.putString("Trasfondo", "");
                bundle.putString("Raza", "");
                bundle.putString("Clase", "");
                bundle.putString("Alineamiento", "");
                bundle.putInt("Nivel", 1);
                bundle.putInt("Puntos de Experiencia", 0);
                bundle.putString("origen", "seleccionPersonaje");
                bundle.putString("codigo", codigoPersonaje);
                Fragment Cabecera = new CabeceraFragment();
                Cabecera.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Cabecera, "cabecera_fragment").addToBackStack(null).commit();
                navigationView.setCheckedItem(R.id.nav_cabecera);
            } else {
                if (mAuth.getCurrentUser() != null) {
                    mDatabase.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (codigoPersonaje == null) {
                                codigoPersonaje = (String) dataSnapshot.child("Ultimo personaje").getValue();
                            }
                            Bundle bundle = new Bundle();
                            bundle.putString("codigo", codigoPersonaje);
                            bundle.putString("origen", "inicio");
                            Fragment inicio = new InicioFragment();
                            inicio.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, inicio, "inicio_fragment").commitAllowingStateLoss();
                            navigationView.setCheckedItem(R.id.nav_ficha);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            //TODO: Alex y Raúl: Si viene de Login o del menú lateral, enseñar SeleccionarPersonaje. Si no, seleccionar Ficha (maybe guardar la ficha actual en alguna variable (código?) ?) -- Lo mismo al pulsar Back
        }

        //Declaración de variables de la cabecera de la barra de navegación
        headerView = navigationView.getHeaderView(0);
        mNivelPersonajeNavBar = headerView.findViewById(R.id.nav_header_nivelPersonaje);
        mNombrePersonaje = headerView.findViewById(R.id.nav_header_nombrePersonaje);
        mCorreoElectronico = headerView.findViewById(R.id.nav_header_correoElectronico);
        mProgressBar = headerView.findViewById(R.id.nav_header_nivel_barraProgreso);


        if (mAuth.getCurrentUser() != null) {
            mDatabase.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    codigoPersonaje = (String) dataSnapshot.child("Ultimo personaje").getValue();
                    cargarDatosFB();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        //Prepara el thread para el reproductor de música
        handler = new Handler();

        rellenarCanciones();

        //Selecciona canción por defecto
        cancionSeleccionada = Integer.parseInt(listaCanciones.get(0).toString());

        //Crea el reproductor de música
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, cancionSeleccionada);
        }

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                empezarReproductor();
                pararReproductor();
            }
        });

        mDialogCarga.dismiss();

    }

    private void cargarDatosFB() {
        //Posicionar en el JSON de Firebase
        final DatabaseReference mRazas = mDatabase.getReference().child("DungeonAndDragons/Raza");
        final DatabaseReference mClases = mDatabase.getReference().child("DungeonAndDragons/Clases");
        final DatabaseReference mAlineamiento = mDatabase.getReference().child("DungeonAndDragons/Alineamientos");
        final DatabaseReference mObjetos = mDatabase.getReference("DungeonAndDragons/Objeto");

        //Seteo datos en ficha
        mAuth = FirebaseAuth.getInstance();
        ComprobarEstatUsuari();
        try {
            Log.d("-------------", codigoPersonaje);
        } catch (Exception e) {

        }

        //TODO: Raúl: Al borrar un personaje, peta lo siguiente:
        mDatabase.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/" + codigoPersonaje).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mNombrePersonaje.setText((String) dataSnapshot.child("Nombre").getValue());
                Trasfondo = (String) dataSnapshot.child("Trasfondo").getValue();
                Alineamiento = (String) dataSnapshot.child("Alineamiento").getValue();
                Raza = (String) dataSnapshot.child("Raza").getValue();
                Clase = (String) dataSnapshot.child("Clase").getValue();
                mNivel = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Nivel").getValue()));
                PuntosExperiencia = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Puntos de Experiencia").getValue()));
                ClaseDeArmadura = (String) dataSnapshot.child("Clase de Armadura").getValue();
                Iniciativa = (String) dataSnapshot.child("Iniciativa").getValue();
                Velocidad = (String) dataSnapshot.child("Velocidad").getValue();
                PuntosGolpeActuales = (String) dataSnapshot.child("Puntos de Golpe Actuales").getValue();
                PuntosGolpeMaximos = (String) dataSnapshot.child("Puntos de Golpe Máximos").getValue();
                PuntosGolpeTemporales = (String) dataSnapshot.child("Puntos de Golpe Temporales").getValue();
                DadoGolpe = (String) dataSnapshot.child("Dado de Golpe/Valor").getValue();
                TotalDadoGolpe = (String) dataSnapshot.child("Dado de Golpe/Total").getValue();
                PesoTotal = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Peso total").getValue()));
                SalvacionesMuerte = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Salvaciones de Muerte").getValue()));
                PCobre = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Piezas de cobre").getValue()));
                PPlata = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Piezas de plata").getValue()));
                PEsmeralda = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Piezas de esmeralda").getValue()));
                POro = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Piezas de oro").getValue()));
                PPlatino = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Piezas de platino").getValue()));
                RasgosPersonalidad = (String) dataSnapshot.child("Rasgos de Personalidad").getValue();
                Ideales = (String) dataSnapshot.child("Ideales").getValue();
                Vinculos = (String) dataSnapshot.child("Vínculos").getValue();
                Defectos = (String) dataSnapshot.child("Defectos").getValue();
                mFuerzaPuntos = (String) dataSnapshot.child("Fuerza puntos").getValue();
                mFuerzaBonus = (String) dataSnapshot.child("Fuerza bonus").getValue();
                mDestrezaPuntos = (String) dataSnapshot.child("Destreza puntos").getValue();
                mDestrezaBonus = (String) dataSnapshot.child("Destreza bonus").getValue();
                mConstitucionPuntos = (String) dataSnapshot.child("Constitucion puntos").getValue();
                mConstitucionBonus = (String) dataSnapshot.child("Constitucion bonus").getValue();
                mInteligenciaPuntos = (String) dataSnapshot.child("Inteligencia puntos").getValue();
                mInteligenciaBonus = (String) dataSnapshot.child("Inteligencia bonus").getValue();
                mSabiduriaPuntos = (String) dataSnapshot.child("Sabiduria puntos").getValue();
                mSabiduriaBonus = (String) dataSnapshot.child("Sabiduria bonus").getValue();
                mCarismaPuntos = (String) dataSnapshot.child("Carisma puntos").getValue();
                mCarismaBonus = (String) dataSnapshot.child("Carisma bonus").getValue();
                mAcrobaciasCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("AcrobaciasCB").getValue())).toString();
                mAtletismoCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("AtletismoCB").getValue())).toString();
                mConocimientoCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("ConocimientoCB").getValue())).toString();
                mEnganyoCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("EngañoCB").getValue())).toString();
                mHistoriaCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("HistoriaCB").getValue())).toString();
                mInterpretacionCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("InterpretacionCB").getValue())).toString();
                mIntimidacionCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("IntimidacionCB").getValue())).toString();
                mInvestigacionCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("InvestigacionCB").getValue())).toString();
                mJuegoManosCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("JuegoManosCB").getValue())).toString();
                mMedicinaCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("MedicinaCB").getValue())).toString();
                mNaturalezaCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("NaturalezaCB").getValue())).toString();
                mPercepcionCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("PercepcionCB").getValue())).toString();
                mPerspicacioCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("PerspicaciaCB").getValue())).toString();
                mPersuasionCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("PersuasionCB").getValue())).toString();
                mReligionCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("ReligionCB").getValue())).toString();
                mSigiloCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("SigiloCB").getValue())).toString();
                mSupervivenciaCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("SupervivenciaCB").getValue())).toString();
                mTratoAnimalesCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("TratoAnimalesCB").getValue())).toString();
                mFuerzaCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("FuerzaCB").getValue())).toString();
                mDestrezaCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("DestrezaCB").getValue())).toString();
                mConstitucionCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("ConstitucionCB").getValue())).toString();
                mInteligenciaCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("InteligenciaCB").getValue())).toString();
                mSabiduriaCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("SabiduriaCB").getValue())).toString();
                mCarismaCB = ((Boolean) Objects.requireNonNull(dataSnapshot.child("CarismaCB").getValue())).toString();
                mInspiracion = (String) dataSnapshot.child("Inspiracion").getValue();
                mBonificador = (String) dataSnapshot.child("Bonificador Competencia").getValue();
                mSabiduria = (String) dataSnapshot.child("Sabiduria Pasiva").getValue();
                mIdiomas = (String) dataSnapshot.child("Idiomas").getValue();
                mArmadura = (String) dataSnapshot.child("Armadura").getValue();
                mArmas = (String) dataSnapshot.child("Armas").getValue();
                mHerramientas = (String) dataSnapshot.child("Herramientas").getValue();
                mEspecialidad = (String) dataSnapshot.child("Especialidad").getValue();
                mRangoMilitar = (String) dataSnapshot.child("Rango militar").getValue();
                mOtras = (String) dataSnapshot.child("Otras").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    mCorreo = dataSnapshot.child("Correo").getValue().toString();
                    mSonido = dataSnapshot.child("Sonido").getValue().toString();

                    if(Boolean.valueOf(mSonido)){
                        mMediaPlayer.setVolume(1,1);
                        volumen = 1;
                    }else{
                        mMediaPlayer.setVolume(0,0);
                        volumen = 0;
                    }

                } catch (Exception e) {
                    Log.d("Error Firebase: ", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mNivelPersonajeNavBar.setText(getString(R.string.nivelPersonaje, Integer.toString(mNivel)));
        mCorreoElectronico.setText(mUsuario.getEmail());

        //Cargar listas de los dropdowns
        cargarSpinnersAux(mRazas, Razas, listaRazas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaRazas = value;
            }
        });
        //Clases
        cargarSpinnersAux(mClases, Clases, listaClases, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaClases = value;
            }
        });
        //Alineamiento
        cargarSpinnersAux(mAlineamiento, Alineamientos, listaAlineamiento, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaAlineamiento = value;
            }
        });

        //Cargamos todos los objetos de Equipo
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
        cargarSpinners(mObjetos.child("Herramientas"), Objetos, listaHerramientas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaHerramientas = value;
            }
        });
        cargarSpinners(mObjetos.child("Mercancías"), Objetos, listaMercancias, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMercancias = value;
            }
        });
        cargarSpinners(mObjetos.child("Misceláneo"), Objetos, listaMisceláneo, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMisceláneo = value;
            }
        });
        cargarSpinners(mObjetos.child("Monturas y Vehículos"), Objetos, listaMonturas, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMonturas = value;
            }
        });
        cargarSpinners(mObjetos.child("Monturas y Vehículos/Arreos, Guarniciones y Vehículos de Tiro"), Objetos, listaMonturasAGV, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMonturasAGV = value;
            }
        });
        cargarSpinners(mObjetos.child("Monturas y Vehículos/Monturas y Otros Animales"), Objetos, listaMonturasMOA, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMonturasMOA = value;
            }
        });
        cargarSpinners(mObjetos.child("Monturas y Vehículos/Vehículos Acuáticos"), Objetos, listaMonturasVA, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaMonturasVA = value;
            }
        });

        final DatabaseReference mEquipo = mDatabase.getReference("users/" + mAuth.getCurrentUser().getUid() + "/" + codigoPersonaje + "/Equipo");
        mEquipo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    try {
                        Equipo.add((String) ds.child("nombre").getValue()+";;;"+((Long) ds.child("coste").getValue()).toString()+";;;"+((Long) ds.child("peso").getValue()).toString()+";;;"+(String) ds.child("url").getValue());
                    }catch (Exception e){
                        Equipo.add((String) ds.child("nombre").getValue()+";;;"+((Long) ds.child("coste").getValue()).toString()+";;;"+((Long) ds.child("velocidad").getValue()).toString()+";;;"+((Long) ds.child("capacidadCarga").getValue()).toString()+";;;"+(String) ds.child("url").getValue());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference mAtaque = mDatabase.getReference("users/" + mAuth.getCurrentUser().getUid() + "/" + codigoPersonaje + "/Ataque");
        mAtaque.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Ataque.add((String) ds.child("nombre").getValue());
                    Ataque.add(((Long) Objects.requireNonNull(ds.child("coste").getValue())).toString());
                    Ataque.add(((Long) Objects.requireNonNull(ds.child("peso").getValue())).toString());
                    Ataque.add((String) ds.child("url").getValue());
                    Ataque.add((String) ds.child("danyo").getValue());
                    Ataque.add((String) ds.child("propiedades").getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatabaseReference mRasgos = mDatabase.getReference("users/" + mAuth.getCurrentUser().getUid() + "/" + codigoPersonaje + "/Rasgos");
        mRasgos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Rasgos.add((String) ds.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference mConjuro = mDatabase.getReference("users/" + mAuth.getCurrentUser().getUid() + "/" + codigoPersonaje + "/Conjuro");
        mConjuro.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Conjuro.add((String) ds.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Cargamos las imagenes de los dados
        final DatabaseReference mdados = mDatabase.getReference("DungeonAndDragons/Dados");

        cargarDados("1d4", dados4, mdados);
        cargarDados("1d6", dados6, mdados);
        cargarDados("1d8", dados8, mdados);
        cargarDados("1d10", dados10, mdados);
        cargarDados("1d12", dados12, mdados);
        cargarDados("1d20", dados20, mdados);

        int PuntosMaximos = mNivel * (mNivel + 1) * 500;
        mProgressBar.setMax(PuntosMaximos);
        mProgressBar.setProgress(PuntosExperiencia);

    }

    //Funcion para rellenar las imagenes de dados
    private void cargarDados(String s, final ArrayList<String> AL, DatabaseReference mdados) {
        mdados.child(s).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    AL.add((String) ds.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Funcion de lectura en FireBase i retorna String[] para el Dropdown
    private void cargarSpinnersAux(DatabaseReference mDB, final ArrayList<String> ALS, final String[] SS, final MyCallback callback) {
        mDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] result = new String[]{};
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String valor = "" + ds.getKey();
                    ALS.add(valor);
                    result = ALS.toArray(SS);

                }
                callback.onCallback(result);
                ALS.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", databaseError.getMessage());
            }
        });
    }

    //Funcion de lectura en FireBase i retorna String[] para el Dropdown
    private void cargarSpinners(DatabaseReference mDB, final ArrayList<String> ALS, final String[] SS, final MyCallback callback) {
        mDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] result = new String[]{};
                ALS.add("Ninguno");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String valor = "" + ds.getKey();
                    ALS.add(valor);
                    result = ALS.toArray(SS);

                }
                callback.onCallback(result);
                ALS.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
                bundle.putString("origen", " ");
                bundle.putString("codigo", codigoPersonaje);
                Fragment Cabecera = new CabeceraFragment();
                Cabecera.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Cabecera).addToBackStack(null).commit();
                break;
            case R.id.nav_puntosHabilidad:
                bundle = new Bundle();
                bundle.putString("Fuerza puntos", mFuerzaPuntos);
                bundle.putString("Fuerza bonus", mFuerzaBonus);
                bundle.putString("Destreza puntos", mDestrezaPuntos);
                bundle.putString("Destreza bonus", mDestrezaBonus);
                bundle.putString("Constitucion puntos", mConstitucionPuntos);
                bundle.putString("Constitucion bonus", mConstitucionBonus);
                bundle.putString("Inteligencia puntos", mInteligenciaPuntos);
                bundle.putString("Inteligencia bonus", mInteligenciaBonus);
                bundle.putString("Sabiduria puntos", mSabiduriaPuntos);
                bundle.putString("Sabiduria bonus", mSabiduriaBonus);
                bundle.putString("Carisma puntos", mCarismaPuntos);
                bundle.putString("Carisma bonus", mCarismaBonus);
                bundle.putString("codigo", codigoPersonaje);
                Fragment Puntos = new PuntosHabilidadFragment();
                Puntos.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Puntos).commit();
                break;
            case R.id.nav_habilidadesBonificadores:
                bundle = new Bundle();
                bundle.putString("Fuerza puntos", mFuerzaPuntos);
                bundle.putString("Destreza puntos", mDestrezaPuntos);
                bundle.putString("Constitucion puntos", mConstitucionPuntos);
                bundle.putString("Inteligencia puntos", mInteligenciaPuntos);
                bundle.putString("Sabiduria puntos", mSabiduriaPuntos);
                bundle.putString("Carisma puntos", mCarismaPuntos);
                bundle.putBoolean("AcrobaciasCB", Boolean.parseBoolean(mAcrobaciasCB));
                bundle.putBoolean("AtletismoCB", Boolean.parseBoolean(mAtletismoCB));
                bundle.putBoolean("ConocimientoCB", Boolean.parseBoolean(mConocimientoCB));
                bundle.putBoolean("EngañoCB", Boolean.parseBoolean(mEnganyoCB));
                bundle.putBoolean("HistoriaCB", Boolean.parseBoolean(mHistoriaCB));
                bundle.putBoolean("InterpretacionCB", Boolean.parseBoolean(mInterpretacionCB));
                bundle.putBoolean("IntimidacionCB", Boolean.parseBoolean(mIntimidacionCB));
                bundle.putBoolean("InvestigacionCB", Boolean.parseBoolean(mInvestigacionCB));
                bundle.putBoolean("JuegoManosCB", Boolean.parseBoolean(mJuegoManosCB));
                bundle.putBoolean("MedicinaCB", Boolean.parseBoolean(mMedicinaCB));
                bundle.putBoolean("NaturalezaCB", Boolean.parseBoolean(mNaturalezaCB));
                bundle.putBoolean("PercepcionCB", Boolean.parseBoolean(mPercepcionCB));
                bundle.putBoolean("PerspicaciaCB", Boolean.parseBoolean(mPerspicacioCB));
                bundle.putBoolean("PersuasionCB", Boolean.parseBoolean(mPersuasionCB));
                bundle.putBoolean("ReligionCB", Boolean.parseBoolean(mReligionCB));
                bundle.putBoolean("SigiloCB", Boolean.parseBoolean(mSigiloCB));
                bundle.putBoolean("SupervivenciaCB", Boolean.parseBoolean(mSupervivenciaCB));
                bundle.putBoolean("TratoAnimalesCB", Boolean.parseBoolean(mTratoAnimalesCB));
                bundle.putBoolean("FuerzaCB", Boolean.parseBoolean(mFuerzaCB));
                bundle.putBoolean("DestrezaCB", Boolean.parseBoolean(mDestrezaCB));
                bundle.putBoolean("ConstitucionCB", Boolean.parseBoolean(mConstitucionCB));
                bundle.putBoolean("InteligenciaCB", Boolean.parseBoolean(mInteligenciaCB));
                bundle.putBoolean("SabiduriaCB", Boolean.parseBoolean(mSabiduriaCB));
                bundle.putBoolean("CarismaCB", Boolean.parseBoolean(mCarismaCB));
                bundle.putString("Inspiracion", mInspiracion);
                bundle.putString("Bonificador Competencia", mBonificador);
                bundle.putString("Sabiduria Pasiva", mSabiduria);
                bundle.putString("codigo", codigoPersonaje);
                Fragment Bonificador = new HabilidadesBonificadoresFragment();
                Bonificador.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Bonificador).commit();
                break;
            case R.id.nav_combate:
                //Pasa los datos al fragment de destino
                bundle = new Bundle();
                bundle.putStringArrayList("Ataque", Ataque);
                bundle.putString("Clase de Armadura", ClaseDeArmadura);
                bundle.putString("Iniciativa", Iniciativa);
                bundle.putString("Destreza puntos", mDestrezaPuntos);
                bundle.putString("Velocidad", Velocidad);
                bundle.putInt("Peso total", PesoTotal);
                bundle.putString("Puntos de Golpe Actuales", PuntosGolpeActuales);
                bundle.putString("Puntos de Golpe Máximos", PuntosGolpeMaximos);
                bundle.putString("Puntos de Golpe Temporales", PuntosGolpeTemporales);
                bundle.putString("Dado de Golpe/Valor", DadoGolpe);
                bundle.putString("Dado de Golpe/Total", TotalDadoGolpe);
                bundle.putInt("Salvacion", SalvacionesMuerte);
                bundle.putString("codigo", codigoPersonaje);
                Fragment Combate = new CombateFragment();
                Combate.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Combate).addToBackStack(null).commit();
                break;
            case R.id.nav_equipo:
                bundle = new Bundle();
                bundle.putStringArray("Lista De Objetos", listaObjetos);
                bundle.putStringArray("Lista De ArmLig", listaArmadurasLigeras);
                bundle.putStringArray("Lista De ArmMed", listaArmadurasMedias);
                bundle.putStringArray("Lista De ArmPes", listaArmadurasPesadas);
                bundle.putStringArray("Lista De ArmDM", listaArmasDM);
                bundle.putStringArray("Lista De ArmDS", listaArmasDS);
                bundle.putStringArray("Lista De ArmCM", listaArmasCM);
                bundle.putStringArray("Lista De ArmCS", listaArmasCS);
                bundle.putStringArray("Lista De Herram", listaHerramientas);
                bundle.putStringArray("Lista De Merc", listaMercancias);
                bundle.putStringArray("Lista De Misc", listaMisceláneo);
                bundle.putStringArray("Lista De Mont", listaMonturas);
                bundle.putStringArray("Lista De MontAGV", listaMonturasAGV);
                bundle.putStringArray("Lista De MontMOA", listaMonturasMOA);
                bundle.putStringArray("Lista De MontVA", listaMonturasVA);
                bundle.putStringArray("Lista De Armaduras", listaArmaduras);
                bundle.putStringArray("Lista De Armas", listaArmas);
                bundle.putInt("Piezas de cobre", PCobre);
                bundle.putInt("Piezas de plata", PPlata);
                bundle.putInt("Piezas de esmeralda", PEsmeralda);
                bundle.putInt("Piezas de oro", POro);
                bundle.putInt("Piezas de platino", PPlatino);
                bundle.putStringArrayList("Equipo", Equipo);
                bundle.putString("codigo", codigoPersonaje);
                Fragment Equipo = new EquipoFragment();
                Equipo.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Equipo).addToBackStack(null).commit();
                break;
            case R.id.nav_ataquesConjuros:
                bundle = new Bundle();
                bundle.putStringArrayList("Ataque", Ataque);
                bundle.putStringArray("Lista De Armas", listaArmas);
                bundle.putStringArray("Lista De ArmDM", listaArmasDM);
                bundle.putStringArray("Lista De ArmDS", listaArmasDS);
                bundle.putStringArray("Lista De ArmCM", listaArmasCM);
                bundle.putStringArray("Lista De ArmCS", listaArmasCS);
                bundle.putStringArrayList("Conjuro", Conjuro);
                bundle.putString("codigo", codigoPersonaje);
                Fragment AtaquesConjuros = new AtaquesConjurosFragment();
                AtaquesConjuros.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, AtaquesConjuros).commit();
                break;
            case R.id.nav_personalidad:
                bundle = new Bundle();
                bundle.putString("Rasgos de Personalidad", RasgosPersonalidad);
                bundle.putString("Ideales", Ideales);
                bundle.putString("Vínculos", Vinculos);
                bundle.putString("Defectos", Defectos);
                bundle.putString("codigo", codigoPersonaje);
                Fragment Personalidad = new PersonalidadFragment();
                Personalidad.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Personalidad).commit();
                break;
            case R.id.nav_rasgosAtributos:
                bundle = new Bundle();
                bundle.putStringArrayList("Rasgos y Atributos", Rasgos);
                bundle.putString("codigo", codigoPersonaje);
                Fragment RasgosYAtributos = new RasgosAtributosFragment();
                RasgosYAtributos.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, RasgosYAtributos).commit();
                break;
            case R.id.nav_competenciasIdiomas:
                bundle = new Bundle();
                bundle.putString("Idiomas", mIdiomas);
                bundle.putString("Armadura", mArmadura);
                bundle.putString("Armas", mArmas);
                bundle.putString("Herramientas", mHerramientas);
                bundle.putString("Especialidad", mEspecialidad);
                bundle.putString("Rango militar", mRangoMilitar);
                bundle.putString("Otras", mOtras);
                bundle.putString("codigo", codigoPersonaje);
                Fragment Competencias = new CompetenciasIdiomasFragment();
                Competencias.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Competencias).commit();
                break;
            case R.id.nav_lanzarDados:
                bundle = new Bundle();
                bundle.putStringArrayList("d4", dados4);
                bundle.putStringArrayList("d6", dados6);
                bundle.putStringArrayList("d8", dados8);
                bundle.putStringArrayList("d10", dados10);
                bundle.putStringArrayList("d12", dados12);
                bundle.putStringArrayList("d20", dados20);
                Fragment LanzarDados = new LanzarDadosFragment();
                LanzarDados.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, LanzarDados).commit();
                break;
            case R.id.nav_salirPersonaje:
                startActivity(new Intent(ContenedorInicioActivity.this, MenuPersonajesActivity.class));
                final Activity activity = this;
                activity.finish();
                break;
            case R.id.nav_configuracion:
                bundle = new Bundle();
                bundle.putBoolean("Correo", Boolean.parseBoolean(mCorreo));
                bundle.putBoolean("Sonido", Boolean.parseBoolean(mSonido));
                Fragment Configuracion = new ConfiguracionFragment();
                Configuracion.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Configuracion).commit();
                break;
            case R.id.nav_reproductorMusica:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReproductorMusicaFragment(), "reproductorMusica").commit();
                break;
            case R.id.nav_logout:

                //Muestra un dialog para que el usuario selecciona cuál quiere añadir
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(this));

                TextView title = new TextView(this);
                title.setText(getString(R.string.estasSegurxQuieresCerrarSesion));
                title.setTextColor(this.getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0, 40, 0, 0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(this);

                linearLayout.setPadding(120, 10, 120, 10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.cerrarSesion), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        ComprobarEstatUsuari();
                    }
                });

                constructrorDialog.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //Enseña el dialog de 'Cerrar sesión'
                AlertDialog anadirObjeto = constructrorDialog.create();
                anadirObjeto.show();

                Objects.requireNonNull(anadirObjeto.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));

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

    private void RecordarMenu(HashMap<String, Object> recordar) {
        recordar.put("Recordar menu", recordarMenuInterno);
        mDatabase.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).updateChildren(recordar);
    }


    public void cerrarApp() {

        /*

        if(broadcastReceiver!=null){
            this.unregisterReceiver(broadcastReceiver);
        }

         */

        //Cierra la app correctamente
        this.finish();
        System.exit(0);
    }

    private void ComprobarEstatUsuari() {

        FirebaseUser usuari = mAuth.getCurrentUser();

        if (usuari == null) {
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

    public void rellenarCanciones() {

        //Crea una lista y la rellena con las canciones
        listaCanciones = new ArrayList();
        listaCanciones.add(R.raw.ambiente);
        listaCanciones.add(R.raw.taberna);
        listaCanciones.add(R.raw.batalla);

        //Prepara los datos de la notificación
        tracks = new ArrayList<>();
        tracks.add(new Track("Canción Ambiente", getResources().getString(R.string.dungeonsAndDragons), R.drawable.ic_ambiente));
        tracks.add(new Track("Canción Taberna", getResources().getString(R.string.dungeonsAndDragons), R.drawable.ic_taberna));
        tracks.add(new Track("Canción Batalla", getResources().getString(R.string.dungeonsAndDragons), R.drawable.ic_batalla));

    }

    public void crearCanal() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            this.registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            this.startService(new Intent(this, OnClearFromRecentService.class));
        }
    }

    public void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CreateNotification.CHANNEL_ID, "Rol & Play", NotificationManager.IMPORTANCE_HIGH);

            notificationManager = this.getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");

            switch (action) {
                case CreateNotification.ACTION_PREVIOUS:
                    onTrackPrevious();
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (isPlaying) {
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    onTrackNext();
                    break;
            }

        }
    };

    @Override
    public void onTrackPrevious() {
        position--;
        CreateNotification.createNotification(this, tracks.get(position), R.drawable.play, position, tracks.size() - 1, true);


        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }


        cancionSeleccionada = Integer.parseInt(listaCanciones.get(position).toString());

        Fragment reproductorMusicaFragment = getSupportFragmentManager().findFragmentByTag("reproductorMusica");

        //Si el usuario se encuentra en la pantalla de ReproductorMusicaFragment, cambia la foto
        if (reproductorMusicaFragment != null && reproductorMusicaFragment.isVisible()) {
            ((ReproductorMusicaFragment) getSupportFragmentManager().findFragmentByTag("reproductorMusica")).comprobarSeleccion();
        }

        onTrackPlay();
    }

    @Override
    public void onTrackPlay() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, cancionSeleccionada);

        }

        mMediaPlayer.start();
        CreateNotification.createNotification(this, tracks.get(position), R.drawable.pause, position, tracks.size() - 1, true);
        isPlaying = true;
    }

    @Override
    public void onTrackPause() {
        CreateNotification.createNotification(this, tracks.get(position), R.drawable.play, position, tracks.size() - 1, false);

        isPlaying = false;
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void onTrackNext() {
        position++;
        CreateNotification.createNotification(this, tracks.get(position), R.drawable.play, position, tracks.size() - 1, true);
        mMediaPlayer.stop();
        mMediaPlayer = null;
        cancionSeleccionada = Integer.parseInt(listaCanciones.get(position).toString());

        Fragment reproductorMusicaFragment = getSupportFragmentManager().findFragmentByTag("reproductorMusica");

        //Si el usuario se encuentra en la pantalla de ReproductorMusicaFragment, cambia la foto
        if (reproductorMusicaFragment != null && reproductorMusicaFragment.isVisible()) {
            ((ReproductorMusicaFragment) getSupportFragmentManager().findFragmentByTag("reproductorMusica")).comprobarSeleccion();
        }


        onTrackPlay();
    }

    public void accionBotonPlay() {

        if (isPlaying) {
            onTrackPause();
        } else {

            if (mMediaPlayer == null) {
                mMediaPlayer = MediaPlayer.create(this, cancionSeleccionada);
                mMediaPlayer.start();

            }

            mMediaPlayer.start();
            onTrackPlay();
        }

    }

    public void accionBotonPausa() {
        onTrackPause();
    }

    public void accionBotonStop() {

        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            handler.removeCallbacks(runnable);
        }
    }

    public void resetearPosicion(int num) {
        position = num;
    }

    public void comprobarReproductor() {
        if (isPlaying) {
            onTrackPlay();
        }
    }

    public void empezarReproductor() {
        mMediaPlayer.start();

    }

    public void pararReproductor() {
        mMediaPlayer.pause();
    }

}
