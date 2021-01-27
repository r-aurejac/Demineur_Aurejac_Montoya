package com.example.demineur_aurejac_montoya;

import android.content.Context;
import android.media.MediaPlayer;

//classe de gestion de la musique
public class MusicManager {
    private static MediaPlayer mediaPlayer;
    public static boolean isStarted = false;

    public static void start(Context context){
        if(!isStarted) {
            mediaPlayer = MediaPlayer.create(context, R.raw.music);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            isStarted = true;
        }
    }

    public static void stop(){
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            isStarted = false;
        }
    }
}
