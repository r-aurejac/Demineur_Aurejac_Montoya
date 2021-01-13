package com.example.demineur_aurejac_montoya;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CellFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CellFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MINESWEEPER_BOX = "minesweeperBox";
    private static final String X_COORD = "x_coord" ;
    private static final String Y_COORD = "y_coord" ;

    // TODO: Rename and change types of parameters

    CellListener cellListener;
    HexagonImageView hexagonImageView;
    MinesweeperBox minesweeperBox;
    int X, Y;
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

        hexagonImageView = view.findViewById(R.id.hexagon_image_view);
        hexagonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"test",Toast.LENGTH_LONG).show();
                onCellClicked();
            }
        });

        ;
        return view;
    }

    void onCellClicked()
    {
        cellListener.onCellClicked(X,Y);
        updatePicture();
    }

    void updatePicture()
    {
        switch(minesweeperBox.getState())
        {
            case 0 :
                hexagonImageView.setImageResource(R.drawable.empty);
                break;
            case 1 :
                switch(minesweeperBox.getNeighbours())
                {
                    case 0 :
                        hexagonImageView.setImageResource(R.drawable.empty);
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
                hexagonImageView.setImageResource(R.drawable.red_mine);
                break;
            case 5 :
                hexagonImageView.setImageResource(R.drawable.mine);
                break;
            case 6 :
                hexagonImageView.setImageResource(R.drawable.wrong_flag);
                break;
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