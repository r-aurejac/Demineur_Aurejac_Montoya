package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button scoreButton,gameButton,optionsButton,helpButton;
    Navigator navigator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigator = new Navigator(getApplicationContext());
        scoreButton = findViewById(R.id.score_button);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showChooseDifficultyDialog();
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
        switch(difficulty)
        {
            case(0) :
                navigator.goToGameActivity();
                break;
            case(1) :
                navigator.goToGameActivity();
                break;
            case(2) :
                navigator.goToGameActivity();
                break;
        }

    }
}