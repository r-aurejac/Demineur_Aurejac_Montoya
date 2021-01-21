package com.example.demineur_aurejac_montoya;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static final String SOUND_ACTIVATED = "soundActivated";
    private static final String MUSIC_ACTIVATED = "musicActivated";
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
/*
    public void setProdByPage(int nb_ref) {
        //si aucun utilisateur n'est sauvegardé, on ajouter [24,florent]
        sharedPreferences
                .edit()
                .putInt(PROD_BY_PAGE, nb_ref)
                .apply();


    }

    public int getRefByPage() {
        if (sharedPreferences.contains(REF_BY_PAGE))
            return sharedPreferences.getInt(REF_BY_PAGE, 20);
        else return 20;

    }

    public String getExportPath() {
        if (sharedPreferences.contains(EXPORT_PATH))
            return sharedPreferences.getString(EXPORT_PATH, Environment.getExternalStorageDirectory().toString());
        else return Environment.getExternalStorageDirectory().toString();
    }

    public void setExportPath(String path) {
        sharedPreferences
                .edit()
                .putString(EXPORT_PATH, path)
                .apply();
    }
    */

}
