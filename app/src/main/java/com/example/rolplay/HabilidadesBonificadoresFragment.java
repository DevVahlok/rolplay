package com.example.rolplay;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;

public class HabilidadesBonificadoresFragment extends Fragment {

    //Declaración de variables
    private View v;
    private TextView mInspiracion, mBonificador, mSabiduria;
    private TextView mAcrobaciasET, mAtletismoET, mConocimientoET, mEngañoET, mHistoriaET, mInterpretacionET,
    mIntimidacionET, mInvestigacionET, mJuegoManosET, mMedicinaET, mNaturalezaET, mPercepcionET, mPerspicacioET,
            mPersuasionET, mReligionET, mSigiloET, mSupervivenciaET, mTratoAnimalesET, mFuerzaSalvacion, mDestrezaSalvacion,
            mConstitucionSalvacion, mInteligenciaSalvacion, mSabiduriaSalvacion, mCarismaSalvacion;

    private CheckBox mAcrobaciasCB, mAtletismoCB, mConocimientoCB, mEngañoCB, mHistoriaCB, mInterpretacionCB,
            mIntimidacionCB, mInvestigacionCB, mJuegoManosCB, mMedicinaCB, mNaturalezaCB, mPercepcionCB, mPerspicacioCB,
            mPersuasionCB, mReligionCB, mSigiloCB, mSupervivenciaCB, mTratoAnimalesCB;

    private Button mPlusInspiracion, mMinusInspiracion, mPlusBonificador, mMinusBonificador, mPlusSabiduria, mMinusSabiduria;
    private String codigoPJ;
    private FirebaseDatabase mDatabase;

    //Constructor
    public HabilidadesBonificadoresFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_habilidades_bonificadores, container, false);

        Bundle recuperados = getArguments();
        codigoPJ = recuperados.getString("codigo");

        mDatabase = FirebaseDatabase.getInstance();

        //Inicialización de variables

        //Asignamos los EditText
         mAcrobaciasET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_acrobacias);
         mAtletismoET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_atletismo);
         mConocimientoET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_conocimientoArcano);
         mEngañoET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_engaño);
         mHistoriaET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_historia);
         mInterpretacionET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_interpretacion);
         mIntimidacionET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_intimidacion);
         mInvestigacionET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_investigacion);
         mJuegoManosET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_juegoManos);
         mMedicinaET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_medicina);
         mNaturalezaET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_naturaleza);
         mPercepcionET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_percepcion);
         mPerspicacioET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_perspicacia);
         mPersuasionET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_persuasion);
         mReligionET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_religion);
         mSigiloET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_sigilo);
         mSupervivenciaET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_supervivencia);
         mTratoAnimalesET= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_tratoAnimales);

         //Asignamos los Checkbox
         mAcrobaciasCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_acrobacias);
         mAtletismoCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_atletismo);
         mConocimientoCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_conocimientoArcano);
         mEngañoCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_engaño);
         mHistoriaCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_historia);
         mInterpretacionCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_interpretacion);
         mIntimidacionCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_intimidacion);
         mInvestigacionCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_investigacion);
         mJuegoManosCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_juegoManos);
         mMedicinaCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_medicina);
         mNaturalezaCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_naturaleza);
         mPercepcionCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_percepcion);
         mPerspicacioCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_perspicacia);
         mPersuasionCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_persuasion);
         mReligionCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_religion);
         mSigiloCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_sigilo);
         mSupervivenciaCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_supervivencia);
         mTratoAnimalesCB= v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_checkbox_tratoAnimales);

         //Lo botones
         mPlusInspiracion = v.findViewById(R.id.HabilidadesBonificadores_botonSumarInspiracion);
         mMinusInspiracion = v.findViewById(R.id.HabilidadesBonificadores_botonRestarInspiracion);
         mPlusBonificador = v.findViewById(R.id.HabilidadesBonificadores_botonSumarBonificadorCompetencia);
         mMinusBonificador = v.findViewById(R.id.HabilidadesBonificadores_botonRestarBonificadorCompetencia);
         mPlusSabiduria = v.findViewById(R.id.HabilidadesBonificadores_botonSumarBonificadorSabiduriaPercepcionPasiva);
         mMinusSabiduria = v.findViewById(R.id.HabilidadesBonificadores_botonRestarSabiduriaPercepcionPasiva);

         //Los TextViews
         mInspiracion = v.findViewById(R.id.HabilidadesBonificadores_inspiracion);
         mBonificador = v.findViewById(R.id.HabilidadesBonificadores_bonificadorCompetencia);
         mSabiduria = v.findViewById(R.id.HabilidadesBonificadores_sabiduriaPercepcionPasiva);

         //Tiradas de salvacion
         mFuerzaSalvacion = v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_fuerza);
         mDestrezaSalvacion = v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_destreza);
         mConstitucionSalvacion = v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_constitucion);
         mInteligenciaSalvacion = v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_inteligencia);
         mSabiduriaSalvacion = v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_sabiduria);
         mCarismaSalvacion = v.findViewById(R.id.HabilidadesBonificadores_tiradasSalvacion_valor_carisma);


         //Le damos el valor de firebase

        if(recuperados!=null){
            mAcrobaciasET.setText(recuperados.getString("Destreza puntos"));
            mAtletismoET.setText(recuperados.getString("Fuerza puntos"));
            mConocimientoET.setText(recuperados.getString("Inteligencia puntos"));
            mEngañoET.setText(recuperados.getString("Carisma puntos"));
            mHistoriaET.setText(recuperados.getString("Inteligencia puntos"));
            mInterpretacionET.setText(recuperados.getString("Carisma puntos"));
            mIntimidacionET.setText(recuperados.getString("Carisma puntos"));
            mInvestigacionET.setText(recuperados.getString("Inteligencia puntos"));
            mJuegoManosET.setText(recuperados.getString("Destreza puntos"));
            mMedicinaET.setText(recuperados.getString("Sabiduría puntos"));
            mNaturalezaET.setText(recuperados.getString("Inteligencia puntos"));
            mPercepcionET.setText(recuperados.getString("Sabiduría puntos"));
            mPerspicacioET.setText(recuperados.getString("Sabiduría puntos"));
            mPersuasionET.setText(recuperados.getString("Carisma puntos"));
            mReligionET.setText(recuperados.getString("Inteligencia puntos"));
            mSigiloET.setText(recuperados.getString("Destreza puntos"));
            mSupervivenciaET.setText(recuperados.getString("Sabiduría puntos"));
            mTratoAnimalesET.setText(recuperados.getString("Sabiduría puntos"));

            //Le damos el check de firebase
            mAcrobaciasCB.setChecked(recuperados.getBoolean("AcrobaciasCB"));
            mAtletismoCB.setChecked(recuperados.getBoolean("AtletismoCB"));
            mConocimientoCB.setChecked(recuperados.getBoolean("ConocimientoCB"));
            mEngañoCB.setChecked(recuperados.getBoolean("EngañoCB"));
            mHistoriaCB.setChecked(recuperados.getBoolean("HistoriaCB"));
            mInterpretacionCB.setChecked(recuperados.getBoolean("InterpretacionCB"));
            mIntimidacionCB.setChecked(recuperados.getBoolean("IntimidacionCB"));
            mInvestigacionCB.setChecked(recuperados.getBoolean("InvestigacionCB"));
            mJuegoManosCB.setChecked(recuperados.getBoolean("JuegoManosCB"));
            mMedicinaCB.setChecked(recuperados.getBoolean("MedicinaCB"));
            mNaturalezaCB.setChecked(recuperados.getBoolean("NaturalezaCB"));
            mPercepcionCB.setChecked(recuperados.getBoolean("PercepcionCB"));
            mPerspicacioCB.setChecked(recuperados.getBoolean("PerspicaciaCB"));
            mPersuasionCB.setChecked(recuperados.getBoolean("PersuasionCB"));
            mReligionCB.setChecked(recuperados.getBoolean("ReligionCB"));
            mSigiloCB.setChecked(recuperados.getBoolean("SigiloCB"));
            mSupervivenciaCB.setChecked(recuperados.getBoolean("SupervivenciaCB"));
            mTratoAnimalesCB.setChecked(recuperados.getBoolean("TratoAnimalesCB"));

            mInspiracion.setText(recuperados.getString("Inspiracion"));
            mBonificador.setText(recuperados.getString("Bonificador Competencia"));
            mSabiduria.setText(recuperados.getString("Sabiduria Pasiva"));

            mFuerzaSalvacion.setText(recuperados.getString("Fuerza puntos"));
            mDestrezaSalvacion.setText(recuperados.getString("Destreza puntos"));
            mConstitucionSalvacion.setText(recuperados.getString("Constitucion puntos"));
            mInteligenciaSalvacion.setText(recuperados.getString("Inteligencia puntos"));
            mSabiduriaSalvacion.setText(recuperados.getString("Sabiduría puntos"));
            mCarismaSalvacion.setText(recuperados.getString("Carisma puntos"));
        }

         //Añadir onClicks
         SumarPuntos(mPlusSabiduria, mSabiduria);
         SumarPuntos(mPlusInspiracion, mInspiracion);
         SumarPuntos(mPlusBonificador, mBonificador);

         RestarPuntos(mMinusSabiduria, mSabiduria);
         RestarPuntos(mMinusInspiracion, mInspiracion);
         RestarPuntos(mMinusBonificador, mBonificador);

        //Aquí poner cada número con signo (+/-) menos "Inspiración" y "Sabiduría (percepción) pasiva"

        //Los números junto a los checkbox se recogen directamente del modificador [cuadrado] de PuntosHabilidadFragment

        //Si no hay ningún valor numérico, setearlo a 0 (directamente desde firebase si puede ser)

        /*

        Lista de dónde recoger los valores

            - Acrobacias (Destreza)
            - Atletismo (Fuerza)
            - Conocimiento Arcano (Inteligencia)
            - Engaño (Carisma)
            - Historia (Inteligencia)
            - Interpretación (Carisma)
            - Intimidación (Carisma)
            - Investigación (Inteligencia)
            - Juego de manos (Destreza)
            - Medicina (Sabiduría)
            - Naturaleza (Inteligencia)
            - Percepción (Sabiduría)
            - Perspicacia (Sabiduría)
            - Persuasión (Carisma)
            - Religión (Inteligencia)
            - Sigilo (Destreza)
            - Supervivencia (Sabiduría)
            - Trato con Animales (Sabiduría)

        */

        //Con esto se hacen cositas en los checkbox
        //  .setChecked(boolean);
        //  .isChecked();

        //TODO: Raúl: Las tiradas de salvación no se guardan en FireBase

        return v;
    }

    private void RestarPuntos(Button button, final TextView TV) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TV.setText(String.valueOf(Integer.parseInt(TV.getText().toString())-1));
            }
        });
    }

    private void SumarPuntos(Button button, final TextView TV) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TV.setText(String.valueOf(Integer.parseInt(TV.getText().toString())+1));
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        mDatabase = FirebaseDatabase.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuariActual = mAuth.getCurrentUser();

        //Guarda los datos en FireBase al salir
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("AcrobaciasCB",mAcrobaciasCB.isChecked());
        hashMap.put("AtletismoCB",mAtletismoCB.isChecked());
        hashMap.put("ConocimientoCB",mConocimientoCB.isChecked());
        hashMap.put("EngañoCB",mEngañoCB.isChecked());
        hashMap.put("HistoriaCB",mHistoriaCB.isChecked());
        hashMap.put("InterpretacionCB",mInterpretacionCB.isChecked());
        hashMap.put("IntimidacionCB",mIntimidacionCB.isChecked());
        hashMap.put("InvestigacionCB",mInvestigacionCB.isChecked());
        hashMap.put("JuegoManosCB",mJuegoManosCB.isChecked());
        hashMap.put("MedicinaCB",mMedicinaCB.isChecked());
        hashMap.put("NaturalezaCB",mNaturalezaCB.isChecked());
        hashMap.put("PercepcionCB",mPercepcionCB.isChecked());
        hashMap.put("PerspicaciaCB",mPerspicacioCB.isChecked());
        hashMap.put("PersuasionCB",mPersuasionCB.isChecked());
        hashMap.put("ReligionCB",mReligionCB.isChecked());
        hashMap.put("SigiloCB",mSigiloCB.isChecked());
        hashMap.put("SupervivenciaCB",mSupervivenciaCB.isChecked());
        hashMap.put("TratoAnimalesCB",mTratoAnimalesCB.isChecked());
        hashMap.put("Inspiracion", mInspiracion.getText().toString());
        hashMap.put("Bonificador Competencia", mBonificador.getText().toString());
        hashMap.put("Sabiduria Pasiva",mSabiduria.getText().toString());
        mDatabase.getReference("users/"+usuariActual.getUid()+"/"+codigoPJ).updateChildren(hashMap);

        HashMap<String, Object> ultimo = new HashMap<>();

        ultimo.put("Ultimo personaje",codigoPJ);
        mDatabase.getReference("users/"+usuariActual.getUid()).updateChildren(ultimo);
    }
}
