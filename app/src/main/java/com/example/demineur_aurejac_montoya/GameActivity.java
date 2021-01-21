package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements CellListener {

    RelativeLayout relativeLayout;
    Minesweeper minesweeper;
    Navigator navigator;
    int yOffset = -12;
    int cellSize = 170;
    int difficulty;
    ArrayList<CellFragment> cellFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        relativeLayout = findViewById(R.id.relative_layout);

        Intent intent = getIntent();
        if(intent != null) {
            difficulty = (intent.getIntExtra("difficulty", 0));
        }
        switch(difficulty){
            case 0:
                minesweeper = new Minesweeper(16,4,10);
                break;
            case 1:
                minesweeper = new Minesweeper(22,4,16);
                break;
            case 2:
                minesweeper = new Minesweeper(26,4,26);
                break;
        }

        cellFragments = new ArrayList<>();
        createGameBoard();
        navigator = new Navigator(this);

    }


    private void createGameBoard()
    {

        for(int i = 0; i< minesweeper.getWidth(); i++)
        {
            for(int j = 0; j< minesweeper.getHeight(); j++)
            {
                if(j%2 == 0)
                createCell((cellSize/2)*i + cellSize*i,
                        (cellSize/2)*j+j*yOffset,
                        minesweeper.getGame()[j][i],i,j);
                else
                    createCell( (cellSize/4) + (cellSize/2)*(i+1) + cellSize*i,
                            (cellSize/2)*j+j*yOffset,
                            minesweeper.getGame()[j][i],i,j);
            }
        }

    }

    private void createCell(int x, int y,MinesweeperBox minesweeperBox, int line, int column)
    {

        CellFragment cellFragment = CellFragment.newInstance(minesweeperBox,line,column);
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
        cellFragments.add(cellFragment);
    }

    private void updateAllCellFragments()
    {
        for(CellFragment cellFragment : cellFragments)
            cellFragment.updatePicture();
    }

    public void replay()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void goToMainMenu()
    {
        navigator.goToMainActivity();
    }

    void showEndGameDialog(boolean isWon)
    {
        EndGameDialog egd =new EndGameDialog(this,isWon);
        egd.show();

    }

    @Override
    public void onCellClicked(int x,int y) {
        if(!minesweeper.isStarted){
            minesweeper.isStarted = true;
            minesweeper.setMines(y,x);
        }
        minesweeper.Reveal(y,x);
        updateAllCellFragments();
        if(minesweeper.isLost)
        {
            showEndGameDialog(false);
        }
        else if(minesweeper.isWon)
        {
            showEndGameDialog(true);
        }
    }




}