package com.example.demineur_aurejac_montoya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;

public class GameActivity extends AppCompatActivity implements CellListener {

    RelativeLayout relativeLayout;
    Minesweeper minesweeper;
    Navigator navigator;
    int nbMines = 0;
    int yOffset = -12;
    int cellSize = 170;
    int headerOffset = 100;
    int difficulty;
    ArrayList<CellFragment> cellFragments;
    Preferences preferences;
    Intent intentService;
    private TimerService timerService;
    static public String BROADCAST = "com.cfc.demineur.event";
    private Time timer;

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        timer = new Time(120);

        intentService = new Intent(this,TimerService.class);
        setContentView(R.layout.activity_game);

        preferences = new Preferences(getApplicationContext());

        tv = findViewById(R.id.timer_tv);

        if(preferences.getCounterActivated()) {
            timer = new Time(preferences.getCounterTime());
            tv.setText(timer.display);
        }
        else{
            tv.setVisibility(View.INVISIBLE);
        }

        if(preferences.getMusicActivated()) {
            MusicManager.start(getApplicationContext());
        }

        relativeLayout = findViewById(R.id.relative_layout);

        Intent intent = getIntent();
        if (intent != null) {
            difficulty = (intent.getIntExtra("difficulty", 0));
        }
        switch (difficulty) {
            case 0:
                nbMines = 10;
                minesweeper = new Minesweeper(16, 4, nbMines);
                break;
            case 1:
                nbMines = 16;
                minesweeper = new Minesweeper(22, 4, nbMines);
                break;
            case 2:
                nbMines = 26;
                minesweeper = new Minesweeper(26, 4, nbMines);
                break;
        }

        cellFragments = new ArrayList<>();
        //createGameBoard();
        navigator = new Navigator(this);

    }

    @Override
    protected void onStart(){
        super.onStart();
        startService(intentService);
        bindService(intentService, myServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (preferences.getCounterActivated()) {
            stopService(intentService);
            unbindService(myServiceConnection);
        }
    }

    private final ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            timerService = ((TimerService.MyBinder) service).getService();

        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            timerService = null;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if(preferences.getMusicActivated()) {
            MusicManager.stop();
        }
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(preferences.getMusicActivated()) {
            MusicManager.start(getApplicationContext());
        }
        registerReceiver(receiver,new IntentFilter(BROADCAST));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(preferences.getCounterActivated()) {
                timer.decrement();
                tv.setText(timer.display);
                if (timer.nSeconds == 0 && !minesweeper.isLost) {
                    minesweeper.isLost = true;
                    showEndGameDialog(false);
                }
            }
        }
    };

    private void createGameBoard() {

        for (int i = 0; i < minesweeper.getWidth(); i++) {
            for (int j = 0; j < minesweeper.getHeight(); j++) {
                if (j % 2 == 0)
                    createCell((cellSize / 2) * i + cellSize * i,
                            (cellSize / 2) * j + j * yOffset,
                            minesweeper.getGame()[j][i], i, j);
                else
                    createCell((cellSize / 4) + (cellSize / 2) * (i + 1) + cellSize * i,
                            (cellSize / 2) * j + j * yOffset,
                            minesweeper.getGame()[j][i], i, j);
            }
        }

    }

    private void createCell(int x, int y, MinesweeperBox minesweeperBox, int line, int column) {

        CellFragment cellFragment = CellFragment.newInstance(minesweeperBox, line, column);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setId(View.generateViewId());
        RelativeLayout.LayoutParams coords = new RelativeLayout.LayoutParams(cellSize, cellSize);
        coords.leftMargin = x; //x
        coords.topMargin = y; //y


        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(linearLayout.getId(), cellFragment, null)
                .commit();
        relativeLayout.addView(linearLayout, coords);
        cellFragments.add(cellFragment);
    }

    private void updateAllCellFragments() {
        if (minesweeper.isLost || minesweeper.isWon) {
            for (CellFragment cellFragment : cellFragments) {
                cellFragment.updateCell(false);
                cellFragment.islocked = true;
            }
        }
        else{
            for (CellFragment cellFragment : cellFragments) {
                cellFragment.updateCell(false);
            }
        }
    }

    public void replay() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void goToMainMenu() {
        navigator.goToMainActivity();
    }

    void showEndGameDialog(boolean isWon) {
        EndGameDialog egd = new EndGameDialog(this, isWon);
        egd.show();

    }

    private void delay(final boolean isWon) {
    final Handler handler = new Handler();
     handler.postDelayed(new

    Runnable() {
        @Override
        public void run () {
            // Do something after 5s = 5000ms
            showEndGameDialog(isWon);
        }
    },2000);
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
            delay(false);
            preferences.setNbLost(preferences.getNbLost()+1);
            preferences.setNbMines(preferences.getNbMines()+nbMines);
        }
        else if(minesweeper.isWon)
        {
            delay(true);
            preferences.setNbWins(preferences.getNbWins()+1);
        }
    }




}