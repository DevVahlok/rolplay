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
    private Button mModificar;
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
        mIdiomas = v.findViewById(R.id.CompetenciasIdiomas_valor_idiomas);
        mArmadura = v.findViewById(R.id.CompetenciasIdiomas_valor_armadura);
        mArmas = v.findViewById(R.id.CompetenciasIdiomas_valor_armas);
        mHerramientas = v.findViewById(R.id.CompetenciasIdiomas_valor_herramientas);
        mEspecialidad = v.findViewById(R.id.CompetenciasIdiomas_valor_especialidad);
        mRangoMilitar = v.findViewById(R.id.CompetenciasIdiomas_valor_rangoMilitar);
        mOtras = v.findViewById(R.id.CompetenciasIdiomas_valor_otras);
        mModificar = v.findViewById(R.id.CompetenciasIdiomas_modificarCompetenciasIdiomas_btn);

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

        mModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

                TextView title = new TextView(getActivity());
                title.setText(R.string.modificar);
                title.setTextColor(getActivity().getColor(R.color.colorPrimary));
                title.setTextSize(20);
                title.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setPadding(0,40,0,0);

                constructrorDialog.setCustomTitle(title);

                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.VERTICAL);

                //Idioma
                final TextView Tidioma = new TextView(getActivity());
                Tidioma.setTextColor(getActivity().getColor(R.color.colorPrimary));
                Tidioma.setTextSize(16);
                Tidioma.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                Tidioma.setPadding(10,10,10,0);
                Tidioma.setText(R.string.idiomas);
                final EditText idiomaET = new EditText(getActivity());
                idiomaET.setMinEms(20);
                idiomaET.setText(mIdiomas.getText());
                //Armadura
                final TextView Tarmadura = new TextView(getActivity());
                Tarmadura.setTextColor(getActivity().getColor(R.color.colorPrimary));
                Tarmadura.setTextSize(16);
                Tarmadura.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                Tarmadura.setPadding(10,10,10,0);
                Tarmadura.setText(R.string.armaduras);
                final EditText armaduraET = new EditText(getActivity());
                armaduraET.setMinEms(20);
                armaduraET.setText(mArmadura.getText());
                //Armas
                final TextView TArma = new TextView(getActivity());
                TArma.setTextColor(getActivity().getColor(R.color.colorPrimary));
                TArma.setTextSize(16);
                TArma.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                TArma.setPadding(10,10,10,0);
                TArma.setText(R.string.armas);
                final EditText ArmaET = new EditText(getActivity());
                ArmaET.setMinEms(20);
                ArmaET.setText(mArmas.getText());
                //Herramientas
                final TextView Therramientas = new TextView(getActivity());
                Therramientas.setTextColor(getActivity().getColor(R.color.colorPrimary));
                Therramientas.setTextSize(16);
                Therramientas.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                Therramientas.setPadding(10,10,10,0);
                Therramientas.setText(R.string.herramientas);
                final EditText herramientasET = new EditText(getActivity());
                herramientasET.setMinEms(20);
                herramientasET.setText(mHerramientas.getText());
                //Especialidad
                final TextView Tespecialidad = new TextView(getActivity());
                Tespecialidad.setTextColor(getActivity().getColor(R.color.colorPrimary));
                Tespecialidad.setTextSize(16);
                Tespecialidad.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                Tespecialidad.setPadding(10,10,10,0);
                Tespecialidad.setText(R.string.especialidad);
                final EditText especialidadET = new EditText(getActivity());
                especialidadET.setMinEms(20);
                especialidadET.setText(mEspecialidad.getText());
                //Rango Militar
                final TextView TRangoMilitar = new TextView(getActivity());
                TRangoMilitar.setTextColor(getActivity().getColor(R.color.colorPrimary));
                TRangoMilitar.setTextSize(16);
                TRangoMilitar.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                TRangoMilitar.setPadding(10,10,10,0);
                TRangoMilitar.setText(R.string.rangoMilitar);
                final EditText RangoMilitarET = new EditText(getActivity());
                RangoMilitarET.setMinEms(20);
                RangoMilitarET.setText(mRangoMilitar.getText());
                //Otras
                final TextView Totras = new TextView(getActivity());
                Totras.setTextColor(getActivity().getColor(R.color.colorPrimary));
                Totras.setTextSize(16);
                Totras.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                Totras.setPadding(10,10,10,0);
                Totras.setText(R.string.otras);
                final EditText otrasET = new EditText(getActivity());
                otrasET.setMinEms(20);
                otrasET.setText(mOtras.getText());

                linearLayout.addView(Tidioma);
                linearLayout.addView(idiomaET);
                linearLayout.addView(Tarmadura);
                linearLayout.addView(armaduraET);
                linearLayout.addView(TArma);
                linearLayout.addView(ArmaET);
                linearLayout.addView(Therramientas);
                linearLayout.addView(herramientasET);
                linearLayout.addView(Tespecialidad);
                linearLayout.addView(especialidadET);
                linearLayout.addView(TRangoMilitar);
                linearLayout.addView(RangoMilitarET);
                linearLayout.addView(Totras);
                linearLayout.addView(otrasET);
                linearLayout.setPadding(120,10,120,10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.anadir), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Tidioma.setText(idiomaET.getText());
                        Tarmadura.setText(armaduraET.getText());
                        TArma.setText(ArmaET.getText());
                        Therramientas.setText(herramientasET.getText());
                        Tespecialidad.setText(especialidadET.getText());
                        TRangoMilitar.setText(RangoMilitarET.getText());
                        Totras.setText(otrasET.getText());
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
