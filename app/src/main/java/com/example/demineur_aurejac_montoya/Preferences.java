package com.example.demineur_aurejac_montoya;

import android.content.Context;
import android.content.SharedPreferences;



//gardes en mémoire des statistiques de jeu et des options définies par l'utilisateurs
public class Preferences {
    private static final String SOUND_ACTIVATED = "soundActivated";
    private static final String MUSIC_ACTIVATED = "musicActivated";
    private static final String DECOUNTER_ACTIVATED = "decounterActivated";
    private static final String NB_LOST = "nbLost";
    private static final String NB_WINS = "nbWins";
    private static final String BEST_TIME = "bestTime";
    private static final String COUNTER_TIME = "counterTime";
    private static final String NB_MINES = "nbMines";
    private static final String PREFS = "PREFS";

    SharedPreferences sharedPreferences;
    Context context;

    public Preferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        this.context = context;
    }

    //musique activée ou pas par défaut
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
    //mode contre la montre activé ou pas par défaut
    public void setDecounterActivated(boolean counterActivated) {
        sharedPreferences
                .edit()
                .putBoolean(DECOUNTER_ACTIVATED, counterActivated)
                .apply();

    }

    public boolean getDecounterActivated() {
        if (sharedPreferences.contains(DECOUNTER_ACTIVATED))
            return sharedPreferences.getBoolean(DECOUNTER_ACTIVATED, true);
        else return true;

    }

    //sons activés ou pas par défaut
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

    //nombre de mines explosées depuis l'installation du jeu
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
    //nombre de victoires depuis l'installation du jeu
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

    //nombre de défaites depuis l'installation du jeu
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

    //meilleur temps en mode difficile enregistré depuis l'installation du jeu
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

    //temps choisi par l'utilisteur pour le mode contre la montre
    public void setCounterTime(int counterTime) {
        sharedPreferences
                .edit()
                .putInt(COUNTER_TIME, counterTime)
                .apply();


    }

    public int getCounterTime() {
        if (sharedPreferences.contains(COUNTER_TIME))
            return sharedPreferences.getInt(COUNTER_TIME, 60);
        else return 0;

    }





}
