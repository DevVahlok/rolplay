package com.example.rolplay.Servicios;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.rolplay.Activities.ContenedorInicioActivity;
import com.example.rolplay.Otros.CreateNotification;
import com.example.rolplay.Otros.NotificationActionService;
import com.example.rolplay.Otros.OnClearFromRecentService;
import com.example.rolplay.Otros.Playable;
import com.example.rolplay.Otros.Track;
import com.example.rolplay.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.example.rolplay.Activities.ContenedorInicioActivity.listaCanciones;
import static com.example.rolplay.Otros.CreateNotification.notification;
import static com.example.rolplay.Activities.ContenedorInicioActivity.cancionSeleccionada;
import static com.example.rolplay.Activities.ContenedorInicioActivity.mMediaPlayer;
import static com.example.rolplay.Activities.ContenedorInicioActivity.handler;
import static com.example.rolplay.Activities.ContenedorInicioActivity.runnable;
import static com.example.rolplay.Activities.ContenedorInicioActivity.position;
import static com.example.rolplay.Activities.ContenedorInicioActivity.tracks;
import static com.example.rolplay.Activities.ContenedorInicioActivity.isPlaying;

public class ReproductorMusicaFragment extends Fragment  {

    //Declaración de variables
    private View v;

    private Button mPausa, mStop;
    private SeekBar seekbar;
    private TextView mProgresoTV, mDuracionTV, title;


    private ImageButton play;
    private ImageView mFotoAmbiente, mFotoTaberna, mFotoBatalla;
    Track track;

    //Constructor
    public ReproductorMusicaFragment() {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inicialización de variables
        v = inflater.inflate(R.layout.fragment_reproductor_musica, container, false);
        seekbar = v.findViewById(R.id.ReproductorMusica_seekbar);
        mDuracionTV = v.findViewById(R.id.ReproductorMusica_duracion);
        mProgresoTV = v.findViewById(R.id.ReproductorMusica_progreso);
        title = v.findViewById(R.id.ReproductorMusica_tituloCancion);


        mFotoAmbiente = v.findViewById(R.id.ReproductorMusica_img_ambiente);
        mFotoTaberna = v.findViewById(R.id.ReproductorMusica_img_taberna);
        mFotoBatalla = v.findViewById(R.id.ReproductorMusica_img_batalla);

        //Rellena canciones
        //((ContenedorInicioActivity)getActivity()).rellenarCanciones();
        prepararReproductor();
        comprobarSeleccion();

        ((ContenedorInicioActivity)getActivity()).crearCanal();


        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(getContext(), cancionSeleccionada);
        } else {
            seekbar.setProgress(mMediaPlayer.getCurrentPosition());
            seekbar.setMax(mMediaPlayer.getDuration());
            mDuracionTV.setText(getTimeString(mMediaPlayer.getDuration()));
            mProgresoTV.setText(getTimeString(mMediaPlayer.getCurrentPosition()));
            changeSeekbar();
        }

        title.setText(tracks.get(position).getTitle());

        play = v.findViewById(R.id.btn_play);
        mPausa = v.findViewById(R.id.btn_pausa);
        mStop = v.findViewById(R.id.btn_stop);

        play.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrisClaro)));
        mPausa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrisClaro)));
        mStop.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrisClaro)));

        play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    play.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    ((ContenedorInicioActivity)getActivity()).accionBotonPlay();
                    changeSeekbar();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    play.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrisClaro)));
                }

                return true;
            }
        });

        mPausa.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mPausa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    ((ContenedorInicioActivity)getActivity()).accionBotonPausa();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mPausa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrisClaro)));
                }

                return true;
            }
        });

        mStop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mStop.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                    ((ContenedorInicioActivity)getActivity()).accionBotonStop();

                    mDuracionTV.setText(getTimeString(0));
                    mProgresoTV.setText(getTimeString(0));
                    seekbar.setProgress(0);

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mStop.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrisClaro)));
                }

                return true;
            }
        });

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mMediaPlayer.seekTo(progress);
                    mDuracionTV.setText(getTimeString(mMediaPlayer.getDuration()));
                    mProgresoTV.setText(getTimeString(mMediaPlayer.getCurrentPosition()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(mMediaPlayer.getCurrentPosition());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekBar.setProgress(mMediaPlayer.getCurrentPosition());
            }
        });


        mFotoAmbiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFotoAmbiente.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mFotoTaberna.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mFotoBatalla.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                ((ContenedorInicioActivity)getActivity()).accionBotonStop();

                mDuracionTV.setText(getTimeString(0));
                mProgresoTV.setText(getTimeString(0));


                seekbar.setProgress(0);
                cancionSeleccionada = Integer.parseInt(listaCanciones.get(0).toString());
                title.setText(getResources().getString(R.string.cancionAmbiente));

                ((ContenedorInicioActivity)getActivity()).resetearPosicion(0);

                ((ContenedorInicioActivity)getActivity()).comprobarReproductor();

                comprobarSeleccion();
            }
        });

        mFotoTaberna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFotoTaberna.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mFotoAmbiente.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mFotoBatalla.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                ((ContenedorInicioActivity)getActivity()).accionBotonStop();

                mDuracionTV.setText(getTimeString(0));
                mProgresoTV.setText(getTimeString(0));

                seekbar.setProgress(0);
                cancionSeleccionada = Integer.parseInt(listaCanciones.get(1).toString());
                title.setText(getResources().getString(R.string.cancionTaberna));
                ((ContenedorInicioActivity)getActivity()).resetearPosicion(1);

                ((ContenedorInicioActivity)getActivity()).comprobarReproductor();

                comprobarSeleccion();
            }
        });

        mFotoBatalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFotoBatalla.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mFotoTaberna.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mFotoAmbiente.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

                ((ContenedorInicioActivity)getActivity()).accionBotonStop();

                mDuracionTV.setText(getTimeString(0));
                mProgresoTV.setText(getTimeString(0));

                seekbar.setProgress(0);
                cancionSeleccionada = Integer.parseInt(listaCanciones.get(2).toString());
                title.setText(getResources().getString(R.string.cancionBatalla));
                ((ContenedorInicioActivity)getActivity()).resetearPosicion(2);

                ((ContenedorInicioActivity)getActivity()).comprobarReproductor();

                comprobarSeleccion();
            }
        });

        return v;

    }

    public void comprobarSeleccion() {

        if (cancionSeleccionada == Integer.parseInt(listaCanciones.get(0).toString())) {
            mFotoAmbiente.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mFotoTaberna.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            mFotoBatalla.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        } else if (cancionSeleccionada == Integer.parseInt(listaCanciones.get(1).toString())) {
            mFotoAmbiente.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            mFotoTaberna.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mFotoBatalla.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        } else if (cancionSeleccionada == Integer.parseInt(listaCanciones.get(2).toString())) {
            mFotoAmbiente.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            mFotoTaberna.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            mFotoBatalla.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        } else if (cancionSeleccionada == 0) {
            mFotoAmbiente.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            mFotoTaberna.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            mFotoBatalla.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }



    private void changeSeekbar() {
        if (mMediaPlayer != null) {
            if (mMediaPlayer.isPlaying()) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (mMediaPlayer != null) {
                            seekbar.setProgress(mMediaPlayer.getCurrentPosition());
                        }

                        changeSeekbar();
                        if (mMediaPlayer != null) {
                            mDuracionTV.setText(getTimeString(mMediaPlayer.getDuration()));
                            mProgresoTV.setText(getTimeString(mMediaPlayer.getCurrentPosition()));
                        }
                    }
                };

                handler.postDelayed(runnable, 1000);
            }
        }
    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%02d", hours))
                .append(":")
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }


    public void prepararReproductor(){
        seekbar.setMax(mMediaPlayer.getDuration());
        ((ContenedorInicioActivity)getActivity()).empezarReproductor();
        changeSeekbar();

        if(!isPlaying){
            ((ContenedorInicioActivity)getActivity()).pararReproductor();
        }

        comprobarSeleccion();
    }
}