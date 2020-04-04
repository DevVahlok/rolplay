package com.example.rolplay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CompetenciasIdiomasFragment extends Fragment {

    private String[] listaIdiomas;
    private String[] listaIdiomasRegionales;

    public CompetenciasIdiomasFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_competencias_idiomas, container, false);

        //Idiomas
        //======================================================

        //Placeholder (abajo están explicados todos)
        listaIdiomas = new String[]{"Enano", "Infracomún", "Élfico", "Drow"};

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

        //TODO: Añadir idiomas a FireBase








        //Resto de elementos
        //======================================================

        /* Simplemente con los botones de añadir/quitar, ir metiendo/quitando palabras [recomiendo que cada elemento sea un array de palabras, así se pueden ir añadiendo y quitando sin tener que hacer al usuario reescribir tod0]

                    Ejemplo:

                            Visualmente:
                                Armadura: Todas, Escudos

                            Código:
                                listaCompetenciasArmaduras = new String[]{"Todas","Escudos"};


            En 'Rango militar' y 'especialidad' solo permitir 1 palabra (deshabilitando botones de añadir)

         */

        return v;
    }
}
