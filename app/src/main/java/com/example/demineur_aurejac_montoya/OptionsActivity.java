package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;


public class OptionsActivity extends AppCompatActivity {

    Button back;
    Navigator navigator;
    Preferences preferences;
    boolean soundActivated = true;
    boolean musicActivated = true;
    SwitchCompat soundSwitch;
    SwitchCompat musicSwitch;
    MusicManager musicManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        navigator = new Navigator(getApplicationContext());
        preferences = new Preferences(getApplicationContext());
        soundSwitch =(SwitchCompat) findViewById(R.id.soundSwitch);
        musicSwitch = (SwitchCompat) findViewById(R.id.musicSwitch);
        back = findViewById(R.id.button3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigator.goToMainActivity();
            }
        });
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferences.setSoundActivated(soundSwitch.isChecked());
            }
        });
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferences.setMusicActivated(musicSwitch.isChecked());
                if(musicSwitch.isChecked()){
                    musicManager.start(getApplicationContext());
                }
                else{
                    musicManager.stop();
                }
            }
        });

        setOptionsPreferences();
        updateViews();
    }

    private void setOptionsPreferences()
    {
        soundActivated = preferences.getSoundActivated();
        musicActivated = preferences.getMusicActivated();

    }

    private void updateViews()
    {
        soundSwitch.setChecked(soundActivated);
        musicSwitch.setChecked(musicActivated);
    }

}