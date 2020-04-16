package com.example.rolplay;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class LanzarDadosFragment extends Fragment {

    private Button md4, md6, md8, md10, md12, md20, md100, mdCantidad;
    private ArrayList<String> dados4, dados6, dados8, dados10, dados12, dados20;

    public LanzarDadosFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_lanzar_dados, container, false);

        md4 = v.findViewById(R.id.LanzarDados_lanzar_1d4_btn);
        md6 = v.findViewById(R.id.LanzarDados_lanzar_1d6_btn);
        md8 = v.findViewById(R.id.LanzarDados_lanzar_1d8_btn);
        md10 = v.findViewById(R.id.LanzarDados_lanzar_1d10_btn);
        md12 = v.findViewById(R.id.LanzarDados_lanzar_1d12_btn);
        md20 = v.findViewById(R.id.LanzarDados_lanzar_1d20_btn);
        md100 = v.findViewById(R.id.LanzarDados_lanzar_1d100_btn);
        mdCantidad = v.findViewById(R.id.LanzarDados_lanzar_1du_btn);

        Bundle recuperados = getArguments();

        //Los strings con las imagenes:
        dados4=recuperados.getStringArrayList("d4");
        dados6=recuperados.getStringArrayList("d6");
        dados8=recuperados.getStringArrayList("d8");
        dados10=recuperados.getStringArrayList("d10");
        dados12=recuperados.getStringArrayList("d12");
        dados20=recuperados.getStringArrayList("d20");

        //Todos los dados menos 1d100 y 1du se cambian con imágenes. Esos dos solo se setea el número en el TextView de su interior

        DialogDados(md4, getString(R.string.unodcuatro), 4, dados4);
        DialogDados(md6, getString(R.string.unodseis), 6, dados6);
        DialogDados(md8, getString(R.string.unodocho), 8, dados8);
        DialogDados(md10, getString(R.string.unoddiez), 10, dados10);
        DialogDados(md12, getString(R.string.unoddoce), 12, dados12);
        DialogDados(md20, getString(R.string.unodveinte), 20, dados20);

        return v;
    }

    private void DialogDados(Button button, final String s, final int x, final ArrayList imagenes) {
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
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final TextView subtitle = new TextView(getActivity());
                subtitle.setText("Cuantos dados quieres lanzar?");
                subtitle.setTextColor(getActivity().getColor(R.color.colorPrimary));
                subtitle.setTextSize(16);
                subtitle.setTypeface(getResources().getFont(R.font.chantelli_antiqua));
                subtitle.setPadding(10,10,10,0);

                linearLayout.addView(subtitle);
                final EditText editText = new EditText(getActivity());
                editText.setMinEms(20);
                linearLayout.addView(editText);
                linearLayout.setPadding(120,10,120,10);

                constructrorDialog.setView(linearLayout);

                //Botón de añadir
                constructrorDialog.setPositiveButton(getString(R.string.lanzar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int dados = Integer.parseInt(editText.getText().toString());
                        AlertDialog.Builder constructrorDialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                        LinearLayout linearLayout = new LinearLayout(getActivity());
                        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        constructrorDialog.setView(linearLayout);

                        Random r= new Random();
                        
                        //Añadimos tantos dados como haya dicho el usuario
                        ArrayList<ImageView> IV = new ArrayList<>();

                        for (int i=0; i<dados; i++){
                            IV.add(new ImageView(getContext()));
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(150, 150);
                            IV.get(i).setLayoutParams(layoutParams);
                            Picasso.get().load(Uri.parse(imagenes.get(r.nextInt(x)).toString())).into(IV.get(i));
                            linearLayout.addView(IV.get(i));
                        }

                        constructrorDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        constructrorDialog.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog lanzarDados = constructrorDialog.create();
                        lanzarDados.show();

                        Objects.requireNonNull(lanzarDados.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));
                    }
                });

                constructrorDialog.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                //Enseña el dialog de 'Lanzar dados'
                constructrorDialog.setView(linearLayout);
                AlertDialog lanzarDados = constructrorDialog.create();
                lanzarDados.show();

                Objects.requireNonNull(lanzarDados.getWindow()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorSecondaryDark)));

            }
        });
    }
}
