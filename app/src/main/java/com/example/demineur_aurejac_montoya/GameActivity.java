package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GameActivity extends AppCompatActivity implements CellListener {

    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        relativeLayout = findViewById(R.id.relative_layout);


        createCell(500,500);




    }



    private void createCell(int x, int y)
    {
        CellFragment cellFragment = CellFragment.newInstance(new MinesweeperBox());
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setId(View.generateViewId());
        RelativeLayout.LayoutParams coords = new RelativeLayout.LayoutParams(100,100);
        coords.leftMargin = x; //x
        coords.topMargin = y; //y

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(linearLayout.getId(), cellFragment, null)
                .commit();
        relativeLayout.addView(linearLayout,coords);
    }

    @Override
    public void onCellClicked() {

    }
}