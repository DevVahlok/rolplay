package com.example.rolplay;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
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
    private ArrayList<String> Objetos = new ArrayList<>();
    private ArrayList<String> Equipo = new ArrayList<>();
    private ArrayList<String> Rasgos = new ArrayList<>();
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
    private String ClaseDeArmadura, Iniciativa, Velocidad,
            PuntosGolpeActuales, PuntosGolpeMaximos, PuntosGolpeTemporales, DadoGolpe, TotalDadoGolpe,
            RasgosPersonalidad, Ideales, Defectos, Vinculos, mFuerzaPuntos, mDestrezaPuntos, mConstitucionPuntos,
            mInteligenciaPuntos, mSabiduriaPuntos, mCarismaPuntos, mFuerzaBonus, mDestrezaBonus, mConstitucionBonus,
            mInteligenciaBonus, mSabiduriaBonus, mCarismaBonus, mAcrobaciasCB, mAtletismoCB, mConocimientoCB, mEngañoCB, mHistoriaCB, mInterpretacionCB,
            mIntimidacionCB, mInvestigacionCB, mJuegoManosCB, mMedicinaCB, mNaturalezaCB, mPercepcionCB, mPerspicacioCB,
            mPersuasionCB, mReligionCB, mSigiloCB, mSupervivenciaCB, mTratoAnimalesCB, mInspiracion, mBonificador, mSabiduria;
    private int SalvacionesMuerte, Nivel, PuntosExperiencia, PCobre, PPlata, PEsmeralda, POro, PPlatino;


    public InicioFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_inicio, container, false);

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

        //Posicionar en el JSON de Firebase
        mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mRazas = mDatabase.getReference().child("DungeonAndDragons/Raza");
        final DatabaseReference mClases = mDatabase.getReference().child("DungeonAndDragons/Clases");
        //final DatabaseReference mAlineamiento = mDatabase.getReference().child("DungeonAndDragons/Alineamiento");
        final DatabaseReference mObjetos = mDatabase.getReference("DungeonAndDragons/Objeto");

        //Seteo datos en ficha
        mAuth= FirebaseAuth.getInstance();
        ComprobarEstatUsuari();
        mDatabase.getReference("users/"+ Objects.requireNonNull(mAuth.getCurrentUser()).getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mNombreJugador_TV.setText((String)dataSnapshot.child("Nombre").getValue());
                mTrasfondoPersonaje_TV.setText((String)dataSnapshot.child("Trasfondo").getValue());
                mAlineamientoPersonaje_TV.setText((String)dataSnapshot.child("Alineamiento").getValue());
                mRazaPersonaje_TV.setText((String)dataSnapshot.child("Raza").getValue());
                mClaseNivelPersonaje_TV.setText((String)dataSnapshot.child("Clase").getValue());
                Nivel = Integer.parseInt((String)dataSnapshot.child("Nivel").getValue());
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
                RasgosPersonalidad = (String)dataSnapshot.child("Rasgos de Personalidad").getValue();
                Ideales = (String)dataSnapshot.child("Ideales").getValue();
                Vinculos = (String)dataSnapshot.child("Vínculos").getValue();
                Defectos = (String)dataSnapshot.child("Defectos").getValue();
                mFuerzaPuntos = (String)dataSnapshot.child("Fuerza puntos").getValue();
                mFuerzaBonus = (String)dataSnapshot.child("Fuerza bonus").getValue();
                mDestrezaPuntos = (String)dataSnapshot.child("Destreza puntos").getValue();
                mDestrezaBonus = (String)dataSnapshot.child("Destreza bonus").getValue();
                mConstitucionPuntos = (String)dataSnapshot.child("Constitucion puntos").getValue();
                mConstitucionBonus = (String)dataSnapshot.child("Constitucion bonus").getValue();
                mInteligenciaPuntos = (String)dataSnapshot.child("Inteligencia puntos").getValue();
                mInteligenciaBonus = (String)dataSnapshot.child("Inteligencia bonus").getValue();
                mSabiduriaPuntos = (String)dataSnapshot.child("Sabiduria puntos").getValue();
                mSabiduriaBonus = (String)dataSnapshot.child("Sabiduria bonus").getValue();
                mCarismaPuntos = (String)dataSnapshot.child("Carisma puntos").getValue();
                mCarismaBonus = (String)dataSnapshot.child("Carisma bonus").getValue();
                mAcrobaciasCB = (String)dataSnapshot.child("AcrobaciasCB").getValue();
                mAtletismoCB = (String)dataSnapshot.child("AtletismoCB").getValue();
                mConocimientoCB = (String)dataSnapshot.child("ConocimientoCB").getValue();
                mEngañoCB = (String)dataSnapshot.child("EngañoCB").getValue();
                mHistoriaCB = (String)dataSnapshot.child("HistoriaCB").getValue();
                mInterpretacionCB = (String)dataSnapshot.child("InterpretacionCB").getValue();
                mIntimidacionCB = (String)dataSnapshot.child("IntimidacionCB").getValue();
                mInvestigacionCB = (String)dataSnapshot.child("InvestigacionCB").getValue();
                mJuegoManosCB = (String)dataSnapshot.child("JuegoManosCB").getValue();
                mMedicinaCB = (String)dataSnapshot.child("MedicinaCB").getValue();
                mNaturalezaCB = (String)dataSnapshot.child("NaturalezaCB").getValue();
                mPercepcionCB = (String)dataSnapshot.child("PercepcionCB").getValue();
                mPerspicacioCB = (String)dataSnapshot.child("PerspicaciaCB").getValue();
                mPersuasionCB = (String)dataSnapshot.child("PersuasionCB").getValue();
                mReligionCB = (String)dataSnapshot.child("ReligionCB").getValue();
                mSigiloCB = (String)dataSnapshot.child("SigiloCB").getValue();
                mSupervivenciaCB = (String)dataSnapshot.child("SupervivenciaCB").getValue();
                mTratoAnimalesCB = (String)dataSnapshot.child("TratoAnimalesCB").getValue();
                mInspiracion = (String)dataSnapshot.child("Inspiracion").getValue();
                mBonificador = (String)dataSnapshot.child("Bonificador Competencia").getValue();
                mSabiduria = (String)dataSnapshot.child("Sabiduria Pasiva").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

        final DatabaseReference mEquipo = mDatabase.getReference("users/"+mAuth.getCurrentUser().getUid()+"/Equipo");
        mEquipo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Equipo.add((String)ds.child("nombre").getValue());
                    Equipo.add(((Long)ds.child("coste").getValue()).toString());
                    Equipo.add(((Long)ds.child("peso").getValue()).toString());
                    Equipo.add((String)ds.child("url").getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference mRasgos = mDatabase.getReference("users/"+mAuth.getCurrentUser().getUid()+"/Rasgos");
        mRasgos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    Rasgos.add((String)ds.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //TODO: No hay alineamientos en BBDD. Maybe añadirlos a FireBase?
        mExperienciaPersonaje_TV.setText(PuntosExperiencia+" / "+(Nivel * (Nivel+1)*500)+" exp");

        //TODO: LOPD (en un fragment tipo párrafo info?)

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
                bundle.putString("AcrobaciasCB",mAcrobaciasCB);
                bundle.putString("AtletismoCB",mAtletismoCB);
                bundle.putString("ConocimientoCB",mConocimientoCB);
                bundle.putString("EngañoCB",mEngañoCB);
                bundle.putString("HistoriaCB",mHistoriaCB);
                bundle.putString("InterpretacionCB",mInterpretacionCB);
                bundle.putString("IntimidacionCB",mIntimidacionCB);
                bundle.putString("InvestigacionCB",mInvestigacionCB);
                bundle.putString("JuegoManosCB",mJuegoManosCB);
                bundle.putString("MedicinaCB",mMedicinaCB);
                bundle.putString("NaturalezaCB",mNaturalezaCB);
                bundle.putString("PercepcionCB",mPercepcionCB);
                bundle.putString("PerspicaciaCB",mPerspicacioCB);
                bundle.putString("PersuasionCB",mPersuasionCB);
                bundle.putString("ReligionCB",mReligionCB);
                bundle.putString("SigiloCB",mSigiloCB);
                bundle.putString("SupervivenciaCB",mSupervivenciaCB);
                bundle.putString("TratoAnimalesCB",mTratoAnimalesCB);
                bundle.putString("Inspiracion", mInspiracion);
                bundle.putString("Bonificador Competencia", mBonificador);
                bundle.putString("Sabiduria Pasiva",mSabiduria);
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
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("habilidadesBonificadores");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HabilidadesBonificadoresFragment()).addToBackStack(null).commit();
            }
        });

        mBotonPestanaCombate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cambia de Fragment y lo marca en la navegación lateral
                //Pasa los datos al fragment de destino
                Bundle bundle = new Bundle();
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
                bundle.putStringArrayList("Equipo", Equipo);
                Fragment Equipo = new EquipoFragment();
                Equipo.setArguments(bundle);
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("equipo");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Equipo).addToBackStack(null).commit();
            }
        });

        mBotonPestanaAtaquesConjuros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("ataquesConjuros");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AtaquesConjurosFragment()).addToBackStack(null).commit();
            }
        });

        mBotonPestanaCompetenciasIdiomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("competenciasIdiomas");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CompetenciasIdiomasFragment()).addToBackStack(null).commit();
            }
        });

        mBotonPestanaPersonalidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("Rasgos de Personalidad",RasgosPersonalidad);
                bundle.putString("Ideales",Ideales);
                bundle.putString("Vínculos",Vinculos);
                bundle.putString("Defectos", Defectos);
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
                bundle.putStringArrayList("Rasgos y Atributos",Rasgos );
                Fragment RasgosYAtributos = new RasgosAtributosFragment();
                RasgosYAtributos.setArguments(bundle);
                ((ContenedorInicioActivity) Objects.requireNonNull(getActivity())).modificarNavegacionLateral("rasgosAtributos");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, RasgosYAtributos).addToBackStack(null).commit();
            }
        });

        return v;
    }




    //Funcion de lectura en FireBase i retorna String[] para el Dropdown
    private void cargarSpinners(DatabaseReference mDB, final ArrayList<String> ALS, final String[] SS, final MyCallback callback) {
        mDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String[] result = new String[] {};
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
    public void onStart() {
        super.onStart();
        ComprobarEstatUsuari();
    }

    private void ComprobarEstatUsuari() {

        FirebaseUser usuari = mAuth.getCurrentUser();

        if (usuari==null){
            //TODO: Cargar datos de Firebase respectivos a la ficha{
            //Pasa a la pantalla de Login si el usuario no está logueado
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }
}
