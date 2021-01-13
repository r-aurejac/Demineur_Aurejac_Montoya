package com.example.demineur_aurejac_montoya;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

public class ChooseDifficultyDialog extends Dialog implements View.OnClickListener {

    public MainActivity activity;
    public Dialog dialog;
    public Button facileButton,intermediaireButton,difficileButton;

    public ChooseDifficultyDialog(MainActivity activity) {
        super(activity);


        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_choose_difficulty);
        facileButton = (Button) findViewById(R.id.facile_button);
        intermediaireButton = (Button) findViewById(R.id.intermediaire_button);
        difficileButton = (Button) findViewById(R.id.difficile_button);
        facileButton.setOnClickListener(this);
        intermediaireButton.setOnClickListener(this);
        difficileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.facile_button:
                activity.startGame(0);
                break;
            case R.id.intermediaire_button:
                activity.startGame(1);
                break;
            case R.id.difficile_button:
                activity.startGame(2);
                break;
            default:
                break;
        }
        dismiss();
    }

}
