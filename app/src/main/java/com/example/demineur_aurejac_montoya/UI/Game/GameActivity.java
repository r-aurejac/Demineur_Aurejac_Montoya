package com.example.demineur_aurejac_montoya.UI.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.demineur_aurejac_montoya.Minesweeper;
import com.example.demineur_aurejac_montoya.MinesweeperBox;
import com.example.demineur_aurejac_montoya.MusicManager;
import com.example.demineur_aurejac_montoya.Navigator;
import com.example.demineur_aurejac_montoya.Preferences;
import com.example.demineur_aurejac_montoya.R;
import com.example.demineur_aurejac_montoya.Time.Time;
import com.example.demineur_aurejac_montoya.Time.TimerService;

import java.util.ArrayList;

//activité où se déroule les parties
public class GameActivity extends AppCompatActivity implements CellListener {

    RelativeLayout relativeLayout;
    Minesweeper minesweeper;
    Navigator navigator;
    int nbMines = 0;
    int yOffset = -12;
    int cellSize = 170;
    int headerOffset = 100;
    int difficulty;
    boolean isHardMode = false;
    ArrayList<CellFragment> cellFragments;
    Preferences preferences;
    Intent intentService;
    private TimerService timerService;
    static public String BROADCAST = "com.cfc.demineur.event";
    private Time decounter, counter;

    TextView timeTv;
    TextView minesLeftTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        calculateCellSize();

        counter= new Time(0); //compteur initialisé à 0 seconde
        intentService = new Intent(this,TimerService.class);
        setContentView(R.layout.activity_game);

        preferences = new Preferences(getApplicationContext());

        timeTv = findViewById(R.id.timer_tv);
        minesLeftTv = findViewById(R.id.mine_tv);


        decounter = new Time(preferences.getCounterTime()); // décompteur initilisé selon la durée choisi par l'utilisateur dans les options


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
                isHardMode = true;
                break;
        }

        cellFragments = new ArrayList<>();
        createGameBoard();
        navigator = new Navigator(this);

        minesLeftTv.setText(String.valueOf(minesweeper.getnRemainingMines()));
    }

    @Override
    protected void onStart(){
        super.onStart();
            startService(intentService);
            bindService(intentService, myServiceConnection, Context.BIND_AUTO_CREATE);
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
        stopService(intentService);
        unbindService(myServiceConnection);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(preferences.getMusicActivated()) {
            MusicManager.start(getApplicationContext());
        }
        registerReceiver(receiver,new IntentFilter(BROADCAST));
    }

    //determine la taille des cases en fonction de la longueur de l'écran du téléphone
    private void calculateCellSize()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        float tempSize = size.y/2198f * cellSize;
        cellSize = (int) tempSize;
    }

    //actulisation du compteur et du decompteur toutes les secondes
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            counter.increment();

            if (!minesweeper.isWon && !minesweeper.isLost) {
                if (preferences.getDecounterActivated()) {
                    decounter.decrement();
                    timeTv.setText(decounter.display);
                    if (decounter.nSeconds == 0) {
                        minesweeper.isLost = true;
                        showEndGameDialog(false);
                    }
                }
                else {
                    timeTv.setText(counter.display);
                }
            }
        }
    };

    //Placement des cases du plateau
    private void createGameBoard() {

        for (int i = 0; i < minesweeper.getWidth(); i++) {
            for (int j = 0; j < minesweeper.getHeight(); j++) {
                if (j % 2 == 0)
                    createCell((cellSize / 2) * i + cellSize * i,
                            headerOffset + (cellSize / 2) * j + j * yOffset,
                            minesweeper.getGame()[j][i], i, j);
                else
                    createCell((cellSize / 4) + (cellSize / 2) * (i + 1) + cellSize * i,
                            headerOffset + (cellSize / 2) * j + j * yOffset,
                            minesweeper.getGame()[j][i], i, j);
            }
        }

    }
//création d'une case
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
//misa à jour de toutes les cases
    private void updateAllCellFragments() {
        minesLeftTv.setText(String.valueOf(minesweeper.getnRemainingMines()));
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
        EndGameDialog egd = new EndGameDialog(this, isWon,counter.nSeconds-2);
        egd.show();

    }
//délai de 2 secondes
    private void delay(final boolean isWon) {
    final Handler handler = new Handler();
     handler.postDelayed(new

    Runnable() {
        @Override
        public void run () {
            // Do something after 5s = 5000ms
            preferences.setNbLost(preferences.getNbLost()+1);
            showEndGameDialog(isWon);
        }
    },2000);
}

//gestion des clics
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
            if(counter.nSeconds<preferences.getBestTime()&&isHardMode)
            preferences.setBestTime(counter.nSeconds);
        }
    }

    @Override
    public void onCellLongClicked(int x,int y){
        updateAllCellFragments();
    }
}