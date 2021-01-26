package com.example.demineur_aurejac_montoya;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class EndGameDialog extends Dialog implements View.OnClickListener {

    private GameActivity activity;
    private Dialog dialog;
    public Button mainMenuButton, replayButton;
    private boolean isWon;
    private TextView endTv;
    private ImageView imageView;
    private int time = 0;

    public EndGameDialog(GameActivity activity,boolean isWon, int time) {
        super(activity);
        this.time = time;
        this.isWon = isWon;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_end_game);
        setCancelable(false);
        imageView = (ImageView) findViewById(R.id.image_view);
        mainMenuButton = (Button) findViewById(R.id.main_menu_button);
        replayButton = (Button) findViewById(R.id.replay_button);
        endTv = (TextView) findViewById(R.id.end_tv);


        mainMenuButton.setOnClickListener(this);
        replayButton.setOnClickListener(this);


        if(isWon)
        {
            imageView.setImageResource(R.drawable.trophy);
            int seconds = time;
            int minutes = seconds/60;
            seconds = seconds -minutes*60;
            String text = "Félicitation vous avez gagné !\n" + "Votre temps est de : " +String.valueOf(minutes) + " minutes et " + String .valueOf(seconds) + " secondes" ;
            endTv.setText(text);
        }
        else
        {
            imageView.setImageResource(R.drawable.cry);
            endTv.setText("Désolé vous avez perdu...");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_menu_button:
                activity.goToMainMenu();
                break;
            case R.id.replay_button:
                activity.replay();
                break;
            default:
                break;
        }
        dismiss();
    }

}

