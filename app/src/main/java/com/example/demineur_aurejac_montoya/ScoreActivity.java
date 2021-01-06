package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {
    HexagonImageView hexagonMaskView, hexagonMaskView2,  hexagonMaskView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        hexagonMaskView = findViewById(R.id.hexa1);
        hexagonMaskView.setImageResource(R.drawable.mine);
        hexagonMaskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
            }
        });
        hexagonMaskView = findViewById(R.id.hexa2);
        hexagonMaskView.setImageResource(R.drawable.mine);
        hexagonMaskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
            }
        });
        hexagonMaskView = findViewById(R.id.hexa3);
        hexagonMaskView.setImageResource(R.drawable.mine);
        hexagonMaskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
            }
        });
    }
}