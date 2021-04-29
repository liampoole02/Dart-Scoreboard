package com.example.liam.dartscoreboard.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liam.dartscoreboard.Game;
import com.example.liam.dartscoreboard.MainActivity;
import com.example.liam.dartscoreboard.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private EditText player1;
    private EditText player2;

    private EditText player1score;
    private EditText player2score;

    private TextView player1scores;
    private TextView player2scores;

    private ArrayList<Integer> player1Array;
    private ArrayList<Integer> player2Array;

    private Button add1;
    private Button add2;

    private Button undop1;
    private Button undop2;

    private TextView player1remain;
    private TextView player2remain;

    private LinearLayout player1layout;
    private LinearLayout player2layout;

    private int game;

    private int total1;
    private int total2;

    Game scores;

    int totalGame;

    private TextView leader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_main, container, false);

        scores=new Game();

        player1=view.findViewById(R.id.player1);
        player2=view.findViewById(R.id.player2);

        player1score=view.findViewById(R.id.player1score);
        player2score=view.findViewById(R.id.player2score);

        add1=view.findViewById(R.id.add1);
        add2=view.findViewById(R.id.add2);

        undop1=view.findViewById(R.id.undop1);
        undop2=view.findViewById(R.id.undop2);

        player1Array=new ArrayList();
        player2Array=new ArrayList();

        player1remain=view.findViewById(R.id.player1remain);
        player2remain=view.findViewById(R.id.player2remain);

        player1layout=view.findViewById(R.id.player1layout);
        player2layout=view.findViewById(R.id.player2layout);

        player1scores=view.findViewById(R.id.player1scores);
        player2scores=view.findViewById(R.id.player2scores);

        leader=view.findViewById(R.id.leader);

        scores.setTotal1(0);
        scores.setTotal2(0);

        if (getArguments() != null) {
             totalGame = Integer.parseInt(getArguments().getString("totalGame"));
        }else{
            Toast.makeText(getContext(), "Null", Toast.LENGTH_SHORT).show();
        }

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(player1Array.size()==player2Array.size()) {
                    total1=0;

                    if (player1score.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please enter required fields", Toast.LENGTH_SHORT).show();
                    } else {
                        player1Array.add(Integer.parseInt(player1score.getText().toString()));

                        for (int x : player1Array) {
                            total1 = total1 + x;
                        }

                        scores.setTotal1(totalGame - total1);
                        player1remain.setText(scores.getTotal1() + "");

                        player1score.setText("");

                        if (scores.getTotal1() < scores.getTotal2()) {
                            player1layout.setBackgroundColor(Color.parseColor("#8BC34A"));
                            player2layout.setBackgroundColor(Color.parseColor("#3698E6"));
                        } else if(scores.getTotal1() == scores.getTotal2()){
                            player2layout.setBackgroundColor(Color.GRAY);
                            player1layout.setBackgroundColor(Color.GRAY);
                        }else {
                            player1layout.setBackgroundColor(Color.parseColor("#8BC34A"));
                            player2layout.setBackgroundColor(Color.parseColor("#3698E6"));
                        }
                    }

                    player1scores.setText(""+playerScoresList(player1Array));

                }else {
                    Toast.makeText(getContext(), "Player 2 turn", Toast.LENGTH_SHORT).show();
                    player1score.setText("");
                }

                leader();
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total2=0;

                if(player1Array.size()!=player2Array.size()) {

                    if (player2score.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Please enter required fields", Toast.LENGTH_SHORT).show();
                    } else {
                        player2Array.add(Integer.parseInt(player2score.getText().toString()));

                        for (int x : player2Array) {
                            total2 = total2 + x;
                        }

                        scores.setTotal2(totalGame - total2);
                        player2remain.setText(scores.getTotal2() + "");

                        player2score.setText("");

                        if (scores.getTotal1() < scores.getTotal2()) {
                            player1layout.setBackgroundColor(Color.parseColor("#8BC34A"));
                            player2layout.setBackgroundColor(Color.parseColor("#3698E6"));
                        } else if(scores.getTotal1() == scores.getTotal2()){
                            player2layout.setBackgroundColor(Color.GRAY);
                            player1layout.setBackgroundColor(Color.GRAY);
                        }
                        else {
                            player1layout.setBackgroundColor(Color.parseColor("#8BC34A"));
                            player2layout.setBackgroundColor(Color.parseColor("#3698E6"));
                        }
                    }

                    player2scores.setText(""+playerScoresList(player2Array));

                    leader();

                }else{
                    Toast.makeText(getContext(), "Player 1 turn", Toast.LENGTH_SHORT).show();
                    player2score.setText("");

                }
            }
        });

        undop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total1=0;

                player1Array.remove(player1Array.size()-1);

                for (int x : player1Array) {
                    total1 = total1 + x;
                }

                scores.setTotal1(totalGame - total1);
                player1remain.setText(scores.getTotal1() + "");

                player1scores.setText(""+playerScoresList(player1Array));

                Toast.makeText(getContext(), "Player 1 turn, enter correct score", Toast.LENGTH_LONG).show();

            }

        });

        undop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total2=0;

                player2Array.remove(player2Array.size()-1);

                for (int x : player2Array) {
                    total2 = total2 + x;
                }

                scores.setTotal2(totalGame-total2);
                player2remain.setText(scores.getTotal2() + "");

                player2scores.setText(""+playerScoresList(player2Array));

                Toast.makeText(getContext(), "Player 2 turn, enter correct score", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

    public String playerScoresList(ArrayList<Integer> a ){
        List<Integer> tail = a.subList(Math.max(a.size() - 5, 0), a.size());

        String t="";
        for (int strTemp : tail){
            t=t+" > "+strTemp;
        }
        return t;
    }

    public void leader(){
        if(total1>total2){
            leader.setText(player1.getText()+" Leading by "+ (total1-total2) );
        }else if (total1<total2){
            leader.setText(player2.getText()+" Leading by "+ (total2-total1) );
        }else{
            leader.setText("Players are equal ");
        }
    }
}