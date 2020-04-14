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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class PersonalidadFragment extends Fragment {

    private View v;
    private TextView mRasgosPersonalidadTV, mIdealesTV, mVinculosTV, mDefectosTV, mRasgosPersonalidadET,
                    mIdealesET, mVinculosET, mDefectosET;
    private FirebaseDatabase mDatabase;
    private String codigoPJ;

    public PersonalidadFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_personalidad, container, false);

        final Bundle recuperados = getArguments();
        codigoPJ = recuperados.getString("codigo");

        mRasgosPersonalidadET = v.findViewById(R.id.Personalidad_editable_RasgosPersonalidad);
        mRasgosPersonalidadTV = v.findViewById(R.id.Personalidad_valor_RasgosPersonalidad);
        mIdealesET = v.findViewById(R.id.Personalidad_editable_Ideales);
        mIdealesTV = v.findViewById(R.id.Personalidad_valor_Ideales);
        mVinculosET = v.findViewById(R.id.Personalidad_editable_Vinculos);
        mVinculosTV = v.findViewById(R.id.Personalidad_valor_Vinculos);
        mDefectosET = v.findViewById(R.id.Personalidad_editable_Defectos);
        mDefectosTV = v.findViewById(R.id.Personalidad_valor_Defectos);

        mRasgosPersonalidadTV.setText(recuperados.getString("Rasgos de Personalidad"));
        mIdealesTV.setText(recuperados.getString("Ideales"));
        mVinculosTV.setText(recuperados.getString("Vínculos"));
        mDefectosTV.setText(recuperados.getString("Defectos"));

        //Añadimos el dialog a cada boton de editar
        mRasgosPersonalidadET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCambiarTexto(mRasgosPersonalidadTV, getString(R.string.rasgosPersonalidad));
                }
        });

        mIdealesET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCambiarTexto(mIdealesTV, getString(R.string.ideales));
            }
        });

        mVinculosET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCambiarTexto(mVinculosTV, getString(R.string.vinculos));
            }
        });

        mDefectosET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCambiarTexto(mDefectosTV, getString(R.string.defectos));
            }
        });

        /*

            Aquí la funcionalidad es simple. El usuario le da a "editar texto" y le aparece un dialog para editarlo o borrarlo.

            Ejemplo dialog:

            +------------------------------------------+
            | Editar Rasgos de personalidad:           |
            |                                          |
            |  [                           ]           |
            |                                          |
            |       [Cancelar] [Borrar] [Guardar]      |
            |                                          |
            +------------------------------------------+


         */

        return v;
    }

    public void DialogCambiarTexto(final TextView TV, String s){
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

    @Override
    public void onPause() {
        super.onPause();
        mDatabase = FirebaseDatabase.getInstance();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser usuariActual = mAuth.getCurrentUser();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Rasgos de Personalidad", mRasgosPersonalidadTV.getText().toString());
        hashMap.put("Ideales", mIdealesTV.getText().toString());
        hashMap.put("Vínculos", mVinculosTV.getText().toString());
        hashMap.put("Defectos", mDefectosTV.getText().toString());
        mDatabase.getReference("users/"+usuariActual.getUid()+"/"+codigoPJ).updateChildren(hashMap);

        HashMap<String, Object> ultimo = new HashMap<>();

        ultimo.put("Ultimo personaje",codigoPJ);
        mDatabase.getReference("users/"+usuariActual.getUid()).updateChildren(ultimo);
    }
}
