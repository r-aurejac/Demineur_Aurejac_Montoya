package com.example.demineur_aurejac_montoya;

import android.content.Context;
import android.content.Intent;

public class Navigator {

    Context context;

    public Navigator(Context context) {
        this.context = context;
    }

    public void goToScoreActivity() {
        Intent intent = new Intent(context, ScoreActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goToOptionsActivity() {
        Intent intent = new Intent(context, OptionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void goToGameActivity() {
        Intent intent = new Intent(context, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
