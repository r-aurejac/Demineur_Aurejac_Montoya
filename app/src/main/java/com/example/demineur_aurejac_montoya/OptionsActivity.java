package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class OptionsActivity extends AppCompatActivity  {

    Button back;
    Navigator navigator;
    Preferences preferences;
    boolean soundActivated = true;
    boolean musicActivated = true;
    boolean counterActivated = false;
    SwitchCompat soundSwitch;
    SwitchCompat musicSwitch;
    SwitchCompat counterSwitch;

    LinearLayout timeLinear;
    TextView timer_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        navigator = new Navigator(getApplicationContext());
        preferences = new Preferences(getApplicationContext());
        soundSwitch =(SwitchCompat) findViewById(R.id.soundSwitch);
        counterSwitch = (SwitchCompat) findViewById(R.id.counterSwitch);
        musicSwitch = (SwitchCompat) findViewById(R.id.musicSwitch);
        timeLinear = (LinearLayout) findViewById(R.id.timer_layout);
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
        counterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferences.setDecounterActivated(counterSwitch.isChecked());
                hideTime(!counterSwitch.isChecked());
            }
        });

        timer_tv = (TextView) findViewById(R.id.timer_tv);
        timer_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        setOptionsPreferences();
        updateViews();
    }


    private void showTimePickerDialog()
    {
        TimePickerDialog tpd=new TimePickerDialog(this);
        tpd.show();
    }
    public void setTime(int minutes, int seconds) {

        timer_tv.setText(String.valueOf(minutes) +" minutes et " + String .valueOf(seconds) + " secondes" );
        preferences.setCounterTime(minutes*60+seconds);
    }

    private void setOptionsPreferences()
    {
        soundActivated = preferences.getSoundActivated();
        musicActivated = preferences.getMusicActivated();
        counterActivated = preferences.getDecounterActivated();

    }

    private void hideTime(boolean hide)
    {
        if(hide)
        timeLinear.setVisibility(LinearLayout.GONE);
        else
            timeLinear.setVisibility(LinearLayout.VISIBLE);

    }

    private void updateViews()
    {
        hideTime(!counterSwitch.isActivated());
        soundSwitch.setChecked(soundActivated);
        musicSwitch.setChecked(musicActivated);
        counterSwitch.setChecked(counterActivated);
        int seconds = preferences.getCounterTime();
        int minutes = seconds/60;
        seconds = seconds -minutes*60;
        timer_tv.setText(String.valueOf(minutes) +"minutes et " + String .valueOf(seconds) + "secondes" );
    }

}