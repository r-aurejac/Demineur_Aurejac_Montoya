package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {
    TextView nbMinesTv,nbWinsTv,nbLostTv,bestTimeTv;
    Button returnButton;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        final Navigator navigator = new Navigator(getApplicationContext());
        preferences = new Preferences(this);
        nbLostTv = (TextView) findViewById(R.id.nb_lost_tv);
        nbWinsTv = (TextView) findViewById(R.id.nb_wins_tv);
        nbMinesTv = (TextView) findViewById(R.id.nb_mines);
        bestTimeTv = (TextView) findViewById(R.id.best_time_tv);
        returnButton = (Button) findViewById(R.id.return_button);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigator.goToMainActivity();
            }
        });

        initTv();
    }

    private void initTv()
    {
        nbLostTv.setText(String.valueOf(preferences.getNbLost()));
        nbWinsTv.setText(String.valueOf(preferences.getNbWins()));
        nbMinesTv.setText(String.valueOf(preferences.getNbMines()));
        int seconds = preferences.getBestTime();
        int minutes = seconds/60;
        seconds = seconds -minutes*60;
        bestTimeTv.setText(String.valueOf(minutes) +"minutes et " + String .valueOf(seconds) + "secondes" );

    }
}