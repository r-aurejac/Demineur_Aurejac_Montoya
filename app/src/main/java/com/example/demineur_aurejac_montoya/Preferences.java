package com.example.demineur_aurejac_montoya;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static final String SOUND_ACTIVATED = "soundActivated";
    private static final String MUSIC_ACTIVATED = "musicActivated";
    private static final String NB_LOST = "nbLost";
    private static final String NB_WINS = "nbWins";
    private static final String BEST_TIME = "bestTime";
    private static final String NB_MINES = "nbMines";
    private static final String PREFS = "PREFS";

    SharedPreferences sharedPreferences;
    Context context;

    public Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        this.context = context;
    }

    public void setMusicActivated(boolean musicActivated) {
        sharedPreferences
                .edit()
                .putBoolean(MUSIC_ACTIVATED, musicActivated)
                .apply();

    }

    public boolean getMusicActivated() {
        if (sharedPreferences.contains(MUSIC_ACTIVATED))
            return sharedPreferences.getBoolean(MUSIC_ACTIVATED, true);
        else return true;

    }
    public void setSoundActivated(boolean soundActivated) {
        sharedPreferences
                .edit()
                .putBoolean(SOUND_ACTIVATED, soundActivated)
                .apply();

    }

    public boolean getSoundActivated() {
        if (sharedPreferences.contains(SOUND_ACTIVATED))
            return sharedPreferences.getBoolean(SOUND_ACTIVATED, true);
        else return true;

    }

    public void setNbMines(int nbMines) {
        sharedPreferences
                .edit()
                .putInt(NB_MINES, nbMines)
                .apply();


    }

    public int getNbMines() {
        if (sharedPreferences.contains(NB_MINES))
            return sharedPreferences.getInt(NB_MINES, 0);
        else return 0;

    }

    public void setNbWins(int nbWins) {
        sharedPreferences
                .edit()
                .putInt(NB_WINS, nbWins)
                .apply();


    }

    public int getNbWins() {
        if (sharedPreferences.contains(NB_WINS))
            return sharedPreferences.getInt(NB_WINS, 0);
        else return 0;

    }

    public void setNbLost(int nbLost) {
        sharedPreferences
                .edit()
                .putInt(NB_LOST, nbLost)
                .apply();


    }

    public int getNbLost() {
        if (sharedPreferences.contains(NB_LOST))
            return sharedPreferences.getInt(NB_LOST, 0);
        else return 0;

    }

    public void setBestTime(int bestTime) {
        sharedPreferences
                .edit()
                .putInt(BEST_TIME, bestTime)
                .apply();


    }

    public int getBestTime() {
        if (sharedPreferences.contains(BEST_TIME))
            return sharedPreferences.getInt(BEST_TIME, 0);
        else return 0;

    }





}
