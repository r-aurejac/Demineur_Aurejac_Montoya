package com.example.demineur_aurejac_montoya.UI.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demineur_aurejac_montoya.Navigator;
import com.example.demineur_aurejac_montoya.R;


//menu principal
public class MainActivity extends AppCompatActivity {

    Button scoreButton,gameButton,optionsButton,helpButton;
    Navigator navigator;
    int difficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigator = new Navigator(getApplicationContext());
        scoreButton = findViewById(R.id.score_button);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navigator.goToScoreActivity();
            }
        });

        gameButton = findViewById(R.id.game_button);
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChooseDifficultyDialog();
            }
        });

        optionsButton = findViewById(R.id.options_button);
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigator.goToOptionsActivity();
            }
        });

        helpButton = findViewById(R.id.help_button);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigator.goToHelpActivity();
            }
        });

    }

    void showChooseDifficultyDialog()
    {
        ChooseDifficultyDialog cdd=new ChooseDifficultyDialog(this);
        cdd.show();
    }

    public void startGame(int difficulty)
    {
        navigator.goToGameActivity(difficulty);
    }
}