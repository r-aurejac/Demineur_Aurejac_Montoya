package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;


public class OptionsActivity extends AppCompatActivity implements android.app.TimePickerDialog.OnTimeSetListener {

    Button back;
    Navigator navigator;
    Preferences preferences;
    boolean soundActivated = true;
    boolean musicActivated = true;
    SwitchCompat soundSwitch;
    SwitchCompat musicSwitch;
    MusicManager musicManager;
    TextView timer_tv;
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
            }
        });

        timer_tv = (TextView) findViewById(R.id.timer_tv);
        timer_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerDialog();
                timePicker.show(getSupportFragmentManager(), "Choisissez la durée");
            }
        });

        setOptionsPreferences();
        updateViews();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        timer_tv.setText("Hour: " + hourOfDay + " Minute: " + minute);
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