package com.example.demineur_aurejac_montoya.UI.Game;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.example.demineur_aurejac_montoya.MinesweeperBox;
import com.example.demineur_aurejac_montoya.Preferences;
import com.example.demineur_aurejac_montoya.R;


//classe représentant les cases du démineurs

public class CellFragment extends Fragment {


    private static final String MINESWEEPER_BOX = "minesweeperBox";
    private static final String X_COORD = "x_coord" ;
    private static final String Y_COORD = "y_coord" ;



    CellListener cellListener;
    Preferences preferences;
    HexagonImageView hexagonImageView;
    MinesweeperBox minesweeperBox;
    int X, Y;
    public boolean islocked = false;
    public CellFragment() {
        // Required empty public constructor
    }



    public static CellFragment newInstance(MinesweeperBox minesweeperBoxParam, int X, int Y) {
        CellFragment fragment = new CellFragment();
        Bundle args = new Bundle();
        args.putSerializable(MINESWEEPER_BOX, minesweeperBoxParam);
        args.putInt(X_COORD,X);
        args.putInt(Y_COORD,Y);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            minesweeperBox = (MinesweeperBox) getArguments().getSerializable(MINESWEEPER_BOX);
            X = getArguments().getInt(X_COORD);
            Y = getArguments().getInt(Y_COORD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cell, container, false);
        preferences = new Preferences(getActivity());
        hexagonImageView = view.findViewById(R.id.hexagon_image_view);
        hexagonImageView.setImageResource(R.drawable.empty);

        //apui court
        hexagonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!islocked) {
                    cellListener.onCellClicked(X, Y);
                    updateCell(true);
                }
            }
        });

        //appui long
        hexagonImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(!islocked) {
                    minesweeperBox.setFlag();
                    cellListener.onCellLongClicked(X, Y);
                    updateCell(false);
                }
                return true;
            }
        });

        return view;
    }

//mise à jour de l'image, déclanchement de son/animation en fonction de l'état de la case
    public void updateCell(boolean isClicked)
    {
        switch(minesweeperBox.getState())
        {
            case 0 :
                hexagonImageView.setImageResource(R.drawable.empty);
                break;
            case 1 :
                if(isClicked) {
                    playClickSound();
                    hexagonImageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.click_animation));
                }
                switch(minesweeperBox.getNeighbours())
                {
                    case 0 :
                        hexagonImageView.setImageResource(R.drawable.discovered);
                        break;
                    case 1 :
                        hexagonImageView.setImageResource(R.drawable.one);
                        break;
                    case 2 :
                        hexagonImageView.setImageResource(R.drawable.two);
                        break;
                    case 3 :
                        hexagonImageView.setImageResource(R.drawable.three);
                        break;
                    case 4 :
                        hexagonImageView.setImageResource(R.drawable.four);
                        break;
                    case 5 :
                        hexagonImageView.setImageResource(R.drawable.five);
                        break;
                    case 6 :
                        hexagonImageView.setImageResource(R.drawable.six);
                        break;
                }
                break;
            case 2 :
                hexagonImageView.setImageResource(R.drawable.flag);
                break;
            case 3 :
                hexagonImageView.setImageResource(R.drawable.guess);
                break;
            case 4 :
                if(isClicked) {
                    playExplosionSound();
                    hexagonImageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.mine_animation));
                }
                hexagonImageView.setImageResource(R.drawable.red_mine);
                break;
            case 5 :
                hexagonImageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.mine_animation));
                hexagonImageView.setImageResource(R.drawable.mine);
                break;
            case 6 :
                playExplosionSound();
                hexagonImageView.setImageResource(R.drawable.wrong_flag);
                break;
        }
    }

    private void playExplosionSound() {
        if(preferences.getSoundActivated()) {
            final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.explosion);
            mp.start();
        }
    }
    private void playClickSound() {
        if(preferences.getSoundActivated()) {
            final MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.click);
            mp.start();
        }
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            cellListener = (CellListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }



}