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
        context.startActivity(intent);
    }

    public void goToOptionsActivity() {
        Intent intent = new Intent(context, OptionsActivity.class);
        context.startActivity(intent);
    }
}
