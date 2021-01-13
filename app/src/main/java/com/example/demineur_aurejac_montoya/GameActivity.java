package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class GameActivity extends AppCompatActivity implements CellListener {

    RelativeLayout relativeLayout;
    Minesweeper minesweeper;
    int cellSize = 170;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        relativeLayout = findViewById(R.id.relative_layout);

        minesweeper = new Minesweeper(16,4,5);
        createGameBoard();


    }


    private void createGameBoard()
    {

        for(int i = 0; i< minesweeper.getWidth(); i++)
        {
            for(int j = 0; j< minesweeper.getHeight(); j++)
            {
                if(j%2 == 0)
                createCell((cellSize/2)*i + cellSize*i,
                        (cellSize/2)*j,
                        minesweeper.getGame()[j][i]);
                else
                    createCell( (cellSize/4) + (cellSize/2)*(i+1) + cellSize*i,
                            (cellSize/2)*j,
                            minesweeper.getGame()[j][i]);
            }
        }

    }

    private void createCell(int x, int y,MinesweeperBox minesweeperBox)
    {

        CellFragment cellFragment = CellFragment.newInstance(minesweeperBox,x,y);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setId(View.generateViewId());
        RelativeLayout.LayoutParams coords = new RelativeLayout.LayoutParams(cellSize,cellSize);
        coords.leftMargin = x; //x
        coords.topMargin = y; //y

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(linearLayout.getId(), cellFragment, null)
                .commit();
        relativeLayout.addView(linearLayout,coords);
    }

    @Override
    public void onCellClicked(int x,int y) {
        minesweeper.Reveal(y,x);
    }


}