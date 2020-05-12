package com.example.rolplay.Ficha;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rolplay.Activities.ContenedorInicioActivity;
import com.example.rolplay.Activities.MainActivity;
import com.example.rolplay.Otros.MyCallback;
import com.example.rolplay.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class InicioFragment extends Fragment {

    //Declaración de variables
    private Button mBotonPestanaCabecera, mBotonPestanaPuntosHabilidad, mBotonPestanaHabilidadesBonificadores1,
            mBotonPestanaHabilidadesBonificadores2, mBotonPestanaCompetenciasIdiomas, mBotonPestanaEquipo,
            mBotonPestanaAtaquesConjuros, mBotonPestanaCombate, mBotonPestanaPersonalidad, mBotonPestanaRasgosAtributos;

    private TextView mNombreJugador_TV, mTrasfondoPersonaje_TV, mClaseNivelPersonaje_TV, mRazaPersonaje_TV,
            mAlineamientoPersonaje_TV, mExperienciaPersonaje_TV, mNombrePersonaje_TV;

    private FirebaseUser mUsuario;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private ArrayList<String> Razas = new ArrayList<>();
    private ArrayList<String> Clases = new ArrayList<>();
    private ArrayList<String> Alineamiento = new ArrayList<>();
    private ArrayList<String> Objetos = new ArrayList<>();
    private ArrayList<String> Equipo = new ArrayList<>();
    private ArrayList<String> Ataque = new ArrayList<>();
    private ArrayList<String> Rasgos = new ArrayList<>();
    private ArrayList<String> Conjuro = new ArrayList<>();
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
    private String ClaseDeArmadura, Iniciativa, Velocidad,
            PuntosGolpeActuales, PuntosGolpeMaximos, PuntosGolpeTemporales, DadoGolpe, TotalDadoGolpe,
            RasgosPersonalidad, Ideales, Defectos, Vinculos, mFuerzaPuntos, mDestrezaPuntos, mConstitucionPuntos,
            mInteligenciaPuntos, mSabiduriaPuntos, mCarismaPuntos, mFuerzaBonus, mDestrezaBonus, mConstitucionBonus,
            mInteligenciaBonus, mSabiduriaBonus, mCarismaBonus, mAcrobaciasCB, mAtletismoCB, mConocimientoCB, mEngañoCB, mHistoriaCB, mInterpretacionCB,
            mIntimidacionCB, mInvestigacionCB, mJuegoManosCB, mMedicinaCB, mNaturalezaCB, mPercepcionCB, mPerspicacioCB,
            mPersuasionCB, mReligionCB, mSigiloCB, mSupervivenciaCB, mTratoAnimalesCB, mInspiracion, mBonificador, mSabiduria,
            mIdiomas, mArmadura, mArmas, mHerramientas, mEspecialidad, mRangoMilitar, mOtras, codigoPersonaje, mFuerzaCB, mDestrezaCB,
            mConstitucionCB, mInteligenciaCB, mSabiduriaCB, mCarismaCB;
    private int SalvacionesMuerte, Nivel, PuntosExperiencia, PCobre, PPlata, PEsmeralda, POro, PPlatino, PesoTotal;


    public InicioFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_inicio, container, false);

        Bundle recuperados = getArguments();

        //Incialización de variables
        mBotonPestanaCabecera = v.findViewById(R.id.ActivityInicio_cabecera_btn);
        mBotonPestanaPuntosHabilidad = v.findViewById(R.id.ActivityInicio_puntosHabilidad_btn);
        mBotonPestanaHabilidadesBonificadores1 = v.findViewById(R.id.ActivityInicio_habilidadesBonificadores1_btn);
        mBotonPestanaHabilidadesBonificadores2 = v.findViewById(R.id.ActivityInicio_habilidadesBonificadores2_btn);
        mNombreJugador_TV = v.findViewById(R.id.ActivityInicio_nombreJugador_TV);
        mClaseNivelPersonaje_TV = v.findViewById(R.id.ActivityInicio_claseNivelPersonaje_TV);
        mTrasfondoPersonaje_TV = v.findViewById(R.id.ActivityInicio_trasfondoPersonaje_TV);
        mRazaPersonaje_TV = v.findViewById(R.id.ActivityInicio_razaPersonaje_TV);
        mAlineamientoPersonaje_TV = v.findViewById(R.id.ActivityInicio_alineamientoPersonaje_TV);
        mExperienciaPersonaje_TV = v.findViewById(R.id.ActivityInicio_experienciaPersonaje_TV);
        mNombreJugador_TV = v.findViewById(R.id.ActivityInicio_NombrePersonaje_TV);
        mBotonPestanaCombate = v.findViewById(R.id.ActivityInicio_combate_btn);
        mBotonPestanaEquipo = v.findViewById(R.id.ActivityInicio_equipo_btn);
        mBotonPestanaAtaquesConjuros = v.findViewById(R.id.ActivityInicio_ataquesConjuros_btn);
        mBotonPestanaCompetenciasIdiomas = v.findViewById(R.id.ActivityInicio_competenciasIdiomas_btn);
        mBotonPestanaPersonalidad = v.findViewById(R.id.ActivityInicio_personalidad_btn);
        mBotonPestanaRasgosAtributos = v.findViewById(R.id.ActivityInicio_rasgosAtributos_btn);

        mAuth = FirebaseAuth.getInstance();
        mUsuario = mAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance();

        HashMap<String, Object> recordar = new HashMap<>();

        recordar.put("Recordar menu", false);
        mDatabase.getReference("users/" + mAuth.getCurrentUser().getUid()).updateChildren(recordar);

        if (mAuth.getCurrentUser() != null) {
            mDatabase.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    codigoPersonaje = (String) dataSnapshot.child("Ultimo personaje").getValue();
                    cargarDatos();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        mBotonPestanaCabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("cabecera");

                //Pasa los datos al fragment de destino
                Bundle bundle = new Bundle();
                bundle.putString("Nombre", (String) mNombreJugador_TV.getText());
                bundle.putString("Trasfondo", (String) mTrasfondoPersonaje_TV.getText());
                bundle.putString("Raza", (String) mRazaPersonaje_TV.getText());
                bundle.putString("Clase", (String) mClaseNivelPersonaje_TV.getText());
                bundle.putString("Alineamiento", (String) mAlineamientoPersonaje_TV.getText());
                bundle.putInt("Nivel", Nivel);
                bundle.putInt("Puntos de Experiencia", PuntosExperiencia);
                bundle.putStringArray("Razas", listaRazas);
                bundle.putStringArray("Clases", listaClases);
                bundle.putStringArray("Alineamientos", listaAlineamiento);
                bundle.putString("origen", " ");
                bundle.putString("codigo", codigoPersonaje);
                Fragment Cabecera = new CabeceraFragment();
                Cabecera.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Cabecera).addToBackStack(null).commit();
            }
        });

        mBotonPestanaPuntosHabilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("puntosHabilidad");

                Bundle bundle = new Bundle();
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
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Puntos).addToBackStack(null).commit();
            }
        });

        mBotonPestanaHabilidadesBonificadores1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                Bundle bundle = new Bundle();
                bundle.putString("Fuerza puntos", mFuerzaPuntos);
                bundle.putString("Destreza puntos", mDestrezaPuntos);
                bundle.putString("Constitucion puntos", mConstitucionPuntos);
                bundle.putString("Inteligencia puntos", mInteligenciaPuntos);
                bundle.putString("Sabiduría puntos", mSabiduriaPuntos);
                bundle.putString("Carisma puntos", mCarismaPuntos);
                bundle.putBoolean("AcrobaciasCB", Boolean.parseBoolean(mAcrobaciasCB));
                bundle.putBoolean("AtletismoCB", Boolean.parseBoolean(mAtletismoCB));
                bundle.putBoolean("ConocimientoCB", Boolean.parseBoolean(mConocimientoCB));
                bundle.putBoolean("EngañoCB", Boolean.parseBoolean(mEngañoCB));
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
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("habilidadesBonificadores");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Bonificador).addToBackStack(null).commit();
            }
        });

        mBotonPestanaHabilidadesBonificadores2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                Bundle bundle = new Bundle();
                bundle.putString("Fuerza puntos", mFuerzaPuntos);
                bundle.putString("Destreza puntos", mDestrezaPuntos);
                bundle.putString("Constitucion puntos", mConstitucionPuntos);
                bundle.putString("Inteligencia puntos", mInteligenciaPuntos);
                bundle.putString("Sabiduría puntos", mSabiduriaPuntos);
                bundle.putString("Carisma puntos", mCarismaPuntos);
                bundle.putBoolean("AcrobaciasCB", Boolean.parseBoolean(mAcrobaciasCB));
                bundle.putBoolean("AtletismoCB", Boolean.parseBoolean(mAtletismoCB));
                bundle.putBoolean("ConocimientoCB", Boolean.parseBoolean(mConocimientoCB));
                bundle.putBoolean("EngañoCB", Boolean.parseBoolean(mEngañoCB));
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
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("habilidadesBonificadores");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Bonificador).addToBackStack(null).commit();
            }
        });

        mBotonPestanaCombate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                //Pasa los datos al fragment de destino
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("Equipo", Equipo);
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
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("combate");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Combate).addToBackStack(null).commit();
            }
        });

        mBotonPestanaEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                //Pasa los datos al fragment de destino
                Bundle bundle = new Bundle();
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
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("equipo");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Equipo).addToBackStack(null).commit();
            }
        });

        mBotonPestanaAtaquesConjuros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
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
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("ataquesConjuros");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, AtaquesConjuros).addToBackStack(null).commit();
            }
        });

        mBotonPestanaCompetenciasIdiomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
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
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("competenciasIdiomas");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Competencias).addToBackStack(null).commit();
            }
        });

        mBotonPestanaPersonalidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Rasgos de Personalidad", RasgosPersonalidad);
                bundle.putString("Ideales", Ideales);
                bundle.putString("Vínculos", Vinculos);
                bundle.putString("Defectos", Defectos);
                bundle.putString("codigo", codigoPersonaje);
                Fragment Personalidad = new PersonalidadFragment();
                Personalidad.setArguments(bundle);
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("personalidad");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Personalidad).addToBackStack(null).commit();
            }
        });

        mBotonPestanaRasgosAtributos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("Rasgos y Atributos", Rasgos);
                bundle.putString("codigo", codigoPersonaje);
                Fragment RasgosYAtributos = new RasgosAtributosFragment();
                RasgosYAtributos.setArguments(bundle);
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("rasgosAtributos");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, RasgosYAtributos).addToBackStack(null).commit();
            }
        });

        return v;
    }

    private void cargarDatos() {
        //Posicionar en el JSON de Firebase
        final DatabaseReference mRazas = mDatabase.getReference().child("DungeonAndDragons/Raza");
        final DatabaseReference mClases = mDatabase.getReference().child("DungeonAndDragons/Clases");
        final DatabaseReference mAlineamiento = mDatabase.getReference().child("DungeonAndDragons/Alineamientos");
        final DatabaseReference mObjetos = mDatabase.getReference("DungeonAndDragons/Objeto");

        //Seteo datos en ficha
        mAuth = FirebaseAuth.getInstance();
        ComprobarEstatUsuari();
        mDatabase.getReference("users/" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "/" + codigoPersonaje).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mNombreJugador_TV.setText((String) dataSnapshot.child("Nombre").getValue());
                mTrasfondoPersonaje_TV.setText((String) dataSnapshot.child("Trasfondo").getValue());
                mAlineamientoPersonaje_TV.setText((String) dataSnapshot.child("Alineamiento").getValue());
                mRazaPersonaje_TV.setText((String) dataSnapshot.child("Raza").getValue());
                mClaseNivelPersonaje_TV.setText((String) dataSnapshot.child("Clase").getValue());
                try {
                    Nivel = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Nivel").getValue()));
                    PuntosExperiencia = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Puntos de Experiencia").getValue()));
                } catch (Exception e) {
                }
                ClaseDeArmadura = (String) dataSnapshot.child("Clase de Armadura").getValue();
                Iniciativa = (String) dataSnapshot.child("Iniciativa").getValue();
                Velocidad = (String) dataSnapshot.child("Velocidad").getValue();
                PuntosGolpeActuales = (String) dataSnapshot.child("Puntos de Golpe Actuales").getValue();
                PuntosGolpeMaximos = (String) dataSnapshot.child("Puntos de Golpe Máximos").getValue();
                PuntosGolpeTemporales = (String) dataSnapshot.child("Puntos de Golpe Temporales").getValue();
                DadoGolpe = (String) dataSnapshot.child("Dado de Golpe/Valor").getValue();
                TotalDadoGolpe = (String) dataSnapshot.child("Dado de Golpe/Total").getValue();
                try {
                    PesoTotal = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Peso total").getValue()));
                    SalvacionesMuerte = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Salvaciones de Muerte").getValue()));
                    PCobre = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Piezas de cobre").getValue()));
                    PPlata = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Piezas de plata").getValue()));
                    PEsmeralda = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Piezas de esmeralda").getValue()));
                    POro = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Piezas de oro").getValue()));
                    PPlatino = Integer.parseInt((String) Objects.requireNonNull(dataSnapshot.child("Piezas de platino").getValue()));
                } catch (Exception e) {
                }
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
                try {
                    mAcrobaciasCB = ((Boolean) dataSnapshot.child("AcrobaciasCB").getValue()).toString();
                    mAtletismoCB = ((Boolean) dataSnapshot.child("AtletismoCB").getValue()).toString();
                    mConocimientoCB = ((Boolean) dataSnapshot.child("ConocimientoCB").getValue()).toString();
                    mEngañoCB = ((Boolean) dataSnapshot.child("EngañoCB").getValue()).toString();
                    mHistoriaCB = ((Boolean) dataSnapshot.child("HistoriaCB").getValue()).toString();
                    mInterpretacionCB = ((Boolean) dataSnapshot.child("InterpretacionCB").getValue()).toString();
                    mIntimidacionCB = ((Boolean) dataSnapshot.child("IntimidacionCB").getValue()).toString();
                    mInvestigacionCB = ((Boolean) dataSnapshot.child("InvestigacionCB").getValue()).toString();
                    mJuegoManosCB = ((Boolean) dataSnapshot.child("JuegoManosCB").getValue()).toString();
                    mMedicinaCB = ((Boolean) dataSnapshot.child("MedicinaCB").getValue()).toString();
                    mNaturalezaCB = ((Boolean) dataSnapshot.child("NaturalezaCB").getValue()).toString();
                    mPercepcionCB = ((Boolean) dataSnapshot.child("PercepcionCB").getValue()).toString();
                    mPerspicacioCB = ((Boolean) dataSnapshot.child("PerspicaciaCB").getValue()).toString();
                    mPersuasionCB = ((Boolean) dataSnapshot.child("PersuasionCB").getValue()).toString();
                    mReligionCB = ((Boolean) dataSnapshot.child("ReligionCB").getValue()).toString();
                    mSigiloCB = ((Boolean) dataSnapshot.child("SigiloCB").getValue()).toString();
                    mSupervivenciaCB = ((Boolean) dataSnapshot.child("SupervivenciaCB").getValue()).toString();
                    mTratoAnimalesCB = ((Boolean) dataSnapshot.child("TratoAnimalesCB").getValue()).toString();
                    mFuerzaCB = ((Boolean) dataSnapshot.child("FuerzaCB").getValue()).toString();
                    mDestrezaCB = ((Boolean) dataSnapshot.child("DestrezaCB").getValue()).toString();
                    mConstitucionCB = ((Boolean) dataSnapshot.child("ConstitucionCB").getValue()).toString();
                    mInteligenciaCB = ((Boolean) dataSnapshot.child("InteligenciaCB").getValue()).toString();
                    mSabiduriaCB = ((Boolean) dataSnapshot.child("SabiduriaCB").getValue()).toString();
                    mCarismaCB = ((Boolean) dataSnapshot.child("CarismaCB").getValue()).toString();
                } catch (Exception e) {
                }
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

        //Cargar listas de los dropdowns
        //Razas
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
        cargarSpinnersAux(mAlineamiento, Alineamiento, listaAlineamiento, new MyCallback() {
            @Override
            public void onCallback(String[] value) {
                listaAlineamiento = value;
            }
        });

        cargarSpinners(mObjetos.child(""), Objetos, listaObjetos, new MyCallback() {
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
                        Equipo.add((String) ds.child("nombre").getValue() + ";;;" + ((Long) ds.child("coste").getValue()).toString() + ";;;" + ((Long) ds.child("peso").getValue()).toString() + ";;;" + (String) ds.child("url").getValue() + ";;;" + (String) ds.child("checkbox").getValue());
                    } catch (Exception e) {
                        Equipo.add((String) ds.child("nombre").getValue() + ";;;" + ((Long) ds.child("coste").getValue()).toString() + ";;;" + ((Long) ds.child("velocidad").getValue()).toString() + ";;;" + ((Long) ds.child("capacidadCarga").getValue()).toString() + ";;;" + (String) ds.child("url").getValue() + ";;;" + (String) ds.child("checkbox").getValue());
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
                    Ataque.add((String) ds.child("checkbox").getValue());
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

        mExperienciaPersonaje_TV.setText(PuntosExperiencia + " / " + (Nivel * (Nivel + 1) * 500) + " exp");

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

    @Override
    public void onStart() {
        super.onStart();
        ComprobarEstatUsuari();
    }

    private void ComprobarEstatUsuari() {

        FirebaseUser usuari = mAuth.getCurrentUser();

        if (usuari == null) {
            //Pasa a la pantalla de Login si el usuario no está logueado
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }
}
