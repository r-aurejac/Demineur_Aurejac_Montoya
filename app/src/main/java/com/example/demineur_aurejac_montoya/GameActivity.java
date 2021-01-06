package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;

public class GameActivity extends AppCompatActivity {

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        relativeLayout = findViewById(R.id.relative_layout);

        HexagonImageView imageView = new HexagonImageView(this);
        RelativeLayout.LayoutParams coords = new RelativeLayout.LayoutParams(100,100);
        coords.leftMargin = 100; //x
        coords.topMargin = 200; //y
        relativeLayout.addView(imageView,coords);





    }
}