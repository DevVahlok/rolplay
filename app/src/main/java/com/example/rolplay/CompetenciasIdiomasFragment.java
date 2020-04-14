package com.example.rolplay;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Objects;

public class CompetenciasIdiomasFragment extends Fragment {

    //Declaración de variables
    private String[] listaIdiomas;
    private String[] listaIdiomasRegionales;
    private Button mIdiomasSumar, mArmaduraSumar, mArmasSumar, mHerramientasSumar, mEspecialidadSumar, mRangoMilitarSumar,
        mOtrasSumar;
    private TextView mIdiomas, mArmadura, mArmas, mHerramientas, mEspecialidad, mRangoMilitar, mOtras;
    private FirebaseDatabase mDatabase;
    private String codigoPJ;

    //Constructor
    public CompetenciasIdiomasFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_competencias_idiomas, container, false);
        Bundle recuperados = getArguments();
        codigoPJ = recuperados.getString("codigo");

        //Inicialización de variables
        mIdiomasSumar = v.findViewById(R.id.HabilidadesBonificadores_botonSumarIdioma);
        mArmaduraSumar = v.findViewById(R.id.HabilidadesBonificadores_botonSumarArmadura);
        mArmasSumar = v.findViewById(R.id.HabilidadesBonificadores_botonSumarArmas);
        mHerramientasSumar = v.findViewById(R.id.HabilidadesBonificadores_botonSumarHerramientas);
        mEspecialidadSumar = v.findViewById(R.id.HabilidadesBonificadores_botonSumarEspecialidad);
        mRangoMilitarSumar = v.findViewById(R.id.HabilidadesBonificadores_botonSumarRangoMilitar);
        mOtrasSumar = v.findViewById(R.id.HabilidadesBonificadores_botonSumarOtras);
        mIdiomas = v.findViewById(R.id.CompetenciasIdiomas_valor_idiomas);
        mArmadura = v.findViewById(R.id.CompetenciasIdiomas_valor_armadura);
        mArmas = v.findViewById(R.id.CompetenciasIdiomas_valor_armas);
        mHerramientas = v.findViewById(R.id.CompetenciasIdiomas_valor_herramientas);
        mEspecialidad = v.findViewById(R.id.CompetenciasIdiomas_valor_especialidad);
        mRangoMilitar = v.findViewById(R.id.CompetenciasIdiomas_valor_rangoMilitar);
        mOtras = v.findViewById(R.id.CompetenciasIdiomas_valor_otras);

        //Recuperación y seteo de datos desde FireBase
        if(recuperados!=null){
            mIdiomas.setText(recuperados.getString("Idiomas"));
            mArmadura.setText(recuperados.getString("Armadura"));
            mArmas.setText(recuperados.getString("Armas"));
            mHerramientas.setText(recuperados.getString("Herramientas"));
            mEspecialidad.setText(recuperados.getString("Especialidad"));
            mRangoMilitar.setText(recuperados.getString("Rango militar"));
            mOtras.setText(recuperados.getString("Otras"));
        }

        //Idiomas
        //======================================================

        //Placeholder (abajo están explicados todos)
        listaIdiomas = new String[]{"Enano", "Infracomún", "Élfico", "Drow"};

        dialogModificar(mIdiomasSumar, mIdiomas, String.valueOf(getText(R.string.idiomas)));
        dialogModificar(mArmaduraSumar, mArmadura, String.valueOf(getText(R.string.armadura)));
        dialogModificar(mArmasSumar, mArmas, String.valueOf(getText(R.string.armas)));
        dialogModificar(mHerramientasSumar, mHerramientas, String.valueOf(getText(R.string.herramientas)));
        dialogModificar(mEspecialidadSumar, mEspecialidad, String.valueOf(getText(R.string.especialidad)));
        dialogModificar(mRangoMilitarSumar, mRangoMilitar, String.valueOf(getText(R.string.rangoMilitar)));
        dialogModificar(mOtrasSumar, mOtras, String.valueOf(getText(R.string.otras)));

        /*

            Todos los personajes conocen el idioma 'común'

            Depende de la raza:

                - Enanos -> Enano, infracomún
                - Elfos -> Élfico, drow, infracomún (y al llegar a lvl 10 aprenden 'lenguaje de signos drow')
                - Gnomos -> Gnomo (si la subclase es svirfneblins, aprenden infracomún)
                - Medianos -> Mediano
                - Semielfos -> Élfico, idioma regional
                - Semiorcos -> Orco, idioma regional
                - Humano -> Idioma regional

            Depende de la clase:

                - Druida lvl 3 -> Animal
                - Explorador lvl 5 -> Animal
                - Pícaro lvl 5 -> Argot de ladrones
                - Asesino lvl 5 -> Argot de asesinos
                - Druida ->Druídico

            Depende del bonificador de inteligencia:

                - Si tiene un +1 (o más) se puede elegir un idioma extra de los de esta lista: orco, élfico, mediano, gnomo, enano, gnoll, infracomún, gigante
                - Si es clérigo, se añade a la lista: abisal, infernal, celestial
                - Si es mago, se añade a la lista: dracónido
                - Si es druida, se añade a la lista: silvano

         */

        //Estos son todos los idiomas regionales
        listaIdiomasRegionales = new String[]{"Aglarondano", "Alzhedo", "Damarano", "Dambrazhano", "Durparí", "Halruyano", "Iluskano", "Khessentano", "Khondazhano", "Khultano", "Lantanés", "Midaní", "Mulhorandino", "Nexalano", "Rashemí", "Serusano", "Sheirano", "Tashalano", "Teigano", "Túrmico", "Úluik", "Unzhérico"};

        //Resto de elementos
        //======================================================

        /*
            Simplemente editar el texto del interior.
         */

        return v;
    }

    private void dialogModificar(Button button, final TextView TV, final String s) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                TextView title = new TextView(getActivity());
                title.setText(s);
                title.setTextColor(getActivity().getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0,40,0,0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getActivity());

                final EditText editText = new EditText(getActivity());
                editText.setMinEms(20);
                editText.setText(TV.getText());

                linearLayout.addView(editText);
                linearLayout.setPadding(120,10,120,10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.anadir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TV.setText(editText.getText());
                    }
                });

                constructrorDialog.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //Enseña el dialog de 'Añadir objeto'
                AlertDialog cambiarTexto = constructrorDialog.create();
                cambiarTexto.show();

                Objects.requireNonNull(cambiarTexto.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));

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
        hashMap.put("Idiomas", mIdiomas.getText().toString());
        hashMap.put("Armadura", mArmadura.getText().toString());
        hashMap.put("Armas", mArmas.getText().toString());
        hashMap.put("Herramientas", mHerramientas.getText().toString());
        hashMap.put("Especialidad", mEspecialidad.getText().toString());
        hashMap.put("Rango militar", mRangoMilitar.getText().toString());
        hashMap.put("Otras", mOtras.getText().toString());

        mDatabase.getReference("users/"+usuariActual.getUid()+"/"+codigoPJ).updateChildren(hashMap);

        HashMap<String, Object> ultimo = new HashMap<>();

        ultimo.put("Ultimo personaje",codigoPJ);
        mDatabase.getReference("users/"+usuariActual.getUid()).updateChildren(ultimo);
    }
}
