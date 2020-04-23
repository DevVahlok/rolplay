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

import static com.example.rolplay.Otros.CreateNotification.notification;

public class ReproductorMusicaFragment extends Fragment implements Playable {

    //Declaración de variables
    private View v;
    static MediaPlayer mMediaPlayer;
    private Button mPausa, mStop;
    private SeekBar seekbar;
    private TextView mProgresoTV, mDuracionTV, title;
    private Handler handler;
    private Runnable runnable;
    static int cancionSeleccionada = 0;
    private ArrayList listaCanciones;
    private ImageButton play;
    private static NotificationManager notificationManager;
    private List<Track> tracks;
    private int position = 0;
    private boolean isPlaying = false;
    private ImageView mFotoAmbiente, mFotoTaberna, mFotoBatalla;
    Track track;
    public static final String CHANNEL_ID = "channel1";
    public static final String ACTION_PREVIOUS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";
    public static NotificationChannel channel;

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
        handler = new Handler();
        listaCanciones = new ArrayList();
        listaCanciones.add(R.raw.ambiente);
        listaCanciones.add(R.raw.taberna);
        listaCanciones.add(R.raw.batalla);
        mFotoAmbiente = v.findViewById(R.id.ReproductorMusica_img_ambiente);
        mFotoTaberna = v.findViewById(R.id.ReproductorMusica_img_taberna);
        mFotoBatalla = v.findViewById(R.id.ReproductorMusica_img_batalla);

        populateTracks();

        comprobarSeleccion();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            getActivity().registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            getActivity().startService(new Intent(getContext(), OnClearFromRecentService.class));
        }

        cancionSeleccionada = Integer.parseInt(listaCanciones.get(0).toString());

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(getContext(), cancionSeleccionada);
        } else {
            seekbar.setProgress(mMediaPlayer.getCurrentPosition());
            seekbar.setMax(mMediaPlayer.getDuration());
            mDuracionTV.setText(getTimeString(mMediaPlayer.getDuration()));
            mProgresoTV.setText(getTimeString(mMediaPlayer.getCurrentPosition()));
            changeSeekbar();
        }

        play = v.findViewById(R.id.btn_play);
        mPausa = v.findViewById(R.id.btn_pausa);
        mStop = v.findViewById(R.id.btn_stop);

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekbar.setMax(mMediaPlayer.getDuration());
                mMediaPlayer.start();
                changeSeekbar();
                mMediaPlayer.pause();
                comprobarSeleccion();
            }
        });


        play.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrisClaro)));
        mPausa.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrisClaro)));
        mStop.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorGrisClaro)));

        play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    play.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));

                    if (isPlaying) {
                        onTrackPause();
                    } else {

                        if (mMediaPlayer == null) {
                            mMediaPlayer = MediaPlayer.create(getContext(), cancionSeleccionada);
                            mMediaPlayer.start();

                        }

                        mMediaPlayer.start();
                        onTrackPlay();
                    }

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
                    onTrackPause();
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
                    if (mMediaPlayer != null) {
                        mMediaPlayer.release();
                        mMediaPlayer = null;
                    }
                    mDuracionTV.setText(getTimeString(0));
                    mProgresoTV.setText(getTimeString(0));

                    if (mMediaPlayer != null) {
                        mMediaPlayer.release();
                        mMediaPlayer = null;


                        handler.removeCallbacks(runnable);
                    }
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
                if (mMediaPlayer != null) {
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                mDuracionTV.setText(getTimeString(0));
                mProgresoTV.setText(getTimeString(0));

                if (mMediaPlayer != null) {
                    mMediaPlayer.release();
                    mMediaPlayer = null;


                    handler.removeCallbacks(runnable);
                }
                seekbar.setProgress(0);
                cancionSeleccionada = Integer.parseInt(listaCanciones.get(0).toString());
                title.setText(getResources().getString(R.string.cancionAmbiente));
                position = 0;

                if (isPlaying) {
                    onTrackPlay();

                }
                comprobarSeleccion();
            }
        });

        mFotoTaberna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFotoTaberna.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mFotoAmbiente.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mFotoBatalla.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                if (mMediaPlayer != null) {
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                mDuracionTV.setText(getTimeString(0));
                mProgresoTV.setText(getTimeString(0));

                if (mMediaPlayer != null) {
                    mMediaPlayer.release();
                    mMediaPlayer = null;


                    handler.removeCallbacks(runnable);
                }
                seekbar.setProgress(0);
                cancionSeleccionada = Integer.parseInt(listaCanciones.get(1).toString());
                title.setText(getResources().getString(R.string.cancionTaberna));
                position = 1;

                if (isPlaying) {
                    onTrackPlay();

                }
                comprobarSeleccion();
            }
        });

        mFotoBatalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFotoBatalla.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mFotoTaberna.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                mFotoAmbiente.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                if (mMediaPlayer != null) {
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
                mDuracionTV.setText(getTimeString(0));
                mProgresoTV.setText(getTimeString(0));

                if (mMediaPlayer != null) {
                    mMediaPlayer.release();
                    mMediaPlayer = null;


                    handler.removeCallbacks(runnable);
                }
                seekbar.setProgress(0);
                cancionSeleccionada = Integer.parseInt(listaCanciones.get(2).toString());
                title.setText(getResources().getString(R.string.cancionBatalla));
                position = 2;

                if (isPlaying) {
                    onTrackPlay();

                }
                comprobarSeleccion();
            }
        });

        return v;

    }

    private void comprobarSeleccion() {

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

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CreateNotification.CHANNEL_ID, "Rol & Play", NotificationManager.IMPORTANCE_HIGH);

            notificationManager = getActivity().getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
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

    private void mantenerNotificacion(boolean modo) {

    }

    private void populateTracks() {
        tracks = new ArrayList<>();

        tracks.add(new Track("Canción Ambiente", getResources().getString(R.string.dungeonsAndDragons), R.drawable.ic_ambiente));
        tracks.add(new Track("Canción Taberna", getResources().getString(R.string.dungeonsAndDragons), R.drawable.ic_taberna));
        tracks.add(new Track("Canción Batalla", getResources().getString(R.string.dungeonsAndDragons), R.drawable.ic_batalla));
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
            comprobarSeleccion();
        }
    };

    @Override
    public void onTrackPrevious() {

        position--;
        CreateNotification.createNotification(getContext(), tracks.get(position), R.drawable.play, position, tracks.size() - 1, true);
        title.setText(tracks.get(position).getTitle());

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer = null;
        }


        cancionSeleccionada = Integer.parseInt(listaCanciones.get(position).toString());

        onTrackPlay();

    }

    @Override
    public void onTrackPlay() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(getContext(), cancionSeleccionada);
            mMediaPlayer.start();
        }

        mMediaPlayer.start();
        CreateNotification.createNotification(getContext(), tracks.get(position), R.drawable.pause, position, tracks.size() - 1, true);
        title.setText(tracks.get(position).getTitle());
        isPlaying = true;

    }

    @Override
    public void onTrackPause() {

        CreateNotification.createNotification(getContext(), tracks.get(position), R.drawable.play, position, tracks.size() - 1, false);
        play.setImageResource(R.drawable.boton_play);
        title.setText(tracks.get(position).getTitle());
        isPlaying = false;
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void onTrackNext() {

        position++;
        CreateNotification.createNotification(getContext(), tracks.get(position), R.drawable.play, position, tracks.size() - 1, true);
        title.setText(tracks.get(position).getTitle());
        mMediaPlayer.stop();
        mMediaPlayer = null;
        cancionSeleccionada = Integer.parseInt(listaCanciones.get(position).toString());

        onTrackPlay();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancelAll();
        }*/
//TODO: mover musicplayer a la activitiy
        getActivity().unregisterReceiver(broadcastReceiver);

    }
}