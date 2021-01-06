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


    // TODO: Rename and change types of parameters

    CellListener cellListener;
    HexagonImageView hexagonImageView;
    MinesweeperBox minesweeperBox;

    public CellFragment() {
        // Required empty public constructor
    }



    public static CellFragment newInstance(MinesweeperBox minesweeperBoxParam) {
        CellFragment fragment = new CellFragment();
        Bundle args = new Bundle();
        args.putSerializable(MINESWEEPER_BOX, minesweeperBoxParam);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            minesweeperBox = (MinesweeperBox) getArguments().getSerializable(MINESWEEPER_BOX);

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
            }
        });
        return view;
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