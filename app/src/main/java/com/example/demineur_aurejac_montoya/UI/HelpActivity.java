package com.example.demineur_aurejac_montoya.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.demineur_aurejac_montoya.Navigator;
import com.example.demineur_aurejac_montoya.R;

//écran d'aide pour le joueur
public class HelpActivity extends AppCompatActivity {

    Button back;
    Navigator navigator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        navigator = new Navigator(getApplicationContext());

        back = findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigator.goToMainActivity();
            }
        });
    }
}