package com.example.liam.dartscoreboard.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liam.dartscoreboard.Game;
import com.example.liam.dartscoreboard.MainActivity;
import com.example.liam.dartscoreboard.R;
import com.example.liam.dartscoreboard.ThreePlayer;

import java.util.ArrayList;
import java.util.List;


public class ThreePlayerfragment extends Fragment {
    private EditText player1;
    private EditText player2;
    private EditText player3;

    Spinner spinner;

    private EditText player1score;
    private EditText player2score;
    private EditText player3score;

    private TextView player1scores;
    private TextView player2scores;
    private TextView player3scores;

    private ArrayList<Integer> player1Array;
    private ArrayList<Integer> player2Array;
    private ArrayList<Integer> player3Array;

    private Button add1;
    private Button add2;
    private Button add3;

    private Button reset;

    private Button undop1;
    private Button undop2;
    private Button undop3;

    private Button deductZero1;
    private Button deductZero2;
    private Button deductZero3;

    private TextView player1remain;
    private TextView player2remain;
    private TextView player3remain;

    private LinearLayout player1layout;
    private LinearLayout player2layout;
    private LinearLayout player3layout;

    private int totalGame;

    private int total1;
    private int total2;
    private int total3;

    private Game scores;

    private TextView leader;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_three_playerfragment, container, false);

        scores = new Game();

        player1 = view.findViewById(R.id.player1);
        player2 = view.findViewById(R.id.player2);
        player3 = view.findViewById(R.id.player3);

        player1score = view.findViewById(R.id.player1score);
        player2score = view.findViewById(R.id.player2score);
        player3score = view.findViewById(R.id.player3score);

        add1 = view.findViewById(R.id.add1);
        add2 = view.findViewById(R.id.add2);
        add3 = view.findViewById(R.id.add3);

        undop1 = view.findViewById(R.id.undop1);
        undop2 = view.findViewById(R.id.undop2);
        undop3 = view.findViewById(R.id.undop3);

        deductZero1 = view.findViewById(R.id.deductZero1);
        deductZero2 = view.findViewById(R.id.deductZero2);
        deductZero3 = view.findViewById(R.id.deductZero3);

        player1Array = new ArrayList();
        player2Array = new ArrayList();
        player3Array = new ArrayList();

        player1remain = view.findViewById(R.id.player1remain);
        player2remain = view.findViewById(R.id.player2remain);
        player3remain = view.findViewById(R.id.player3remain);

        player1layout = view.findViewById(R.id.player1layout);
        player2layout = view.findViewById(R.id.player2layout);
        player3layout = view.findViewById(R.id.player3layout);

        player1scores = view.findViewById(R.id.player1scores);
        player2scores = view.findViewById(R.id.player2scores);
        player3scores = view.findViewById(R.id.player3scores);

        leader = view.findViewById(R.id.leader);

        scores.setTotal1(0);
        scores.setTotal2(0);
        scores.setTotal3(0);

        chronometer = view.findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - chronometer.getBase()) >= 1000000000) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                }
            }
        });

        startChronometer(view);


        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int totalGame = Integer.parseInt(MainActivity.spinner.getSelectedItem().toString());

                if (player1Array.size() == player2Array.size() && player2Array.size() == player3Array.size()) {
                    total1 = 0;

                    if (player1score.getText().toString().equals("") || MainActivity.spinner.getSelectedItem() == null) {
                        Toast.makeText(getContext(), "Please enter required fields", Toast.LENGTH_SHORT).show();
                    } else {
                        player1Array.add(Integer.parseInt(player1score.getText().toString()));

                        for (int x : player1Array) {
                            total1 = total1 + x;
                        }

                        scores.setTotal1(totalGame - total1);
                        player1remain.setText(scores.getTotal1() + "");
                        Toast.makeText(getContext(), "Score has been deducted", Toast.LENGTH_SHORT).show();

                        player1score.setText("");

                    }

                    player1scores.setText("" + playerScoresList(player1Array));

                } else if (player1Array.size() > player2Array.size()) {
                    Toast.makeText(getContext(), "Player 2 turn", Toast.LENGTH_SHORT).show();
                    player1score.setText("");
                } else {
                    Toast.makeText(getContext(), "Player 3 turn", Toast.LENGTH_SHORT).show();
                    player1score.setText("");
                }

//                leader();
            }
        });

        deductZero1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int totalGame = Integer.parseInt(MainActivity.spinner.getSelectedItem().toString());

                if (player1Array.size() == player2Array.size() && player2Array.size() == player3Array.size()) {
                    total1 = 0;

                        player1Array.add(0);

                        for (int x : player1Array) {
                            total1 = total1 + x;
                        }

                        scores.setTotal1(totalGame - total1);
                        player1remain.setText(scores.getTotal1() + "");
                        Toast.makeText(getContext(), "Score has been deducted", Toast.LENGTH_SHORT).show();

                        player1score.setText("");

                    player1scores.setText("" + playerScoresList(player1Array));

                } else if (player1Array.size() > player2Array.size()) {
                    Toast.makeText(getContext(), "Player 2 turn", Toast.LENGTH_SHORT).show();
                    player1score.setText("");
                } else {
                    Toast.makeText(getContext(), "Player 3 turn", Toast.LENGTH_SHORT).show();
                    player1score.setText("");
                }
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total2 = 0;

                final int totalGame = Integer.parseInt(MainActivity.spinner.getSelectedItem().toString());

                if (player1Array.size() != player2Array.size()) {

                    if (player2score.getText().toString().equals("") || MainActivity.spinner.getSelectedItem() == null) {
                        Toast.makeText(getContext(), "Please enter required fields", Toast.LENGTH_SHORT).show();
                    } else {
                        player2Array.add(Integer.parseInt(player2score.getText().toString()));

                        for (int x : player2Array) {
                            total2 = total2 + x;
                        }

                        scores.setTotal2(totalGame - total2);
                        player2remain.setText(scores.getTotal2() + "");
                        Toast.makeText(getContext(), "Score has been deducted", Toast.LENGTH_SHORT).show();

                        player2score.setText("");

                    }

                    player2scores.setText("" + playerScoresList(player2Array));

//                    leader();

                } else {
                    Toast.makeText(getContext(), "Player 3 turn", Toast.LENGTH_SHORT).show();
                    player2score.setText("");

                }
            }
        });

        deductZero2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total2 = 0;

                final int totalGame = Integer.parseInt(MainActivity.spinner.getSelectedItem().toString());

                if (player1Array.size() != player2Array.size()) {

                        player2Array.add(0);

                        for (int x : player2Array) {
                            total2 = total2 + x;
                        }

                        scores.setTotal2(totalGame - total2);
                        player2remain.setText(scores.getTotal2() + "");
                    Toast.makeText(getContext(), "Score has been deducted", Toast.LENGTH_SHORT).show();

                        player2score.setText("");

                    player2scores.setText("" + playerScoresList(player2Array));

//                    leader();

                } else {
                    Toast.makeText(getContext(), "Player 3 turn", Toast.LENGTH_SHORT).show();
                    player2score.setText("");

                }
            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total3 = 0;

                final int totalGame = Integer.parseInt(MainActivity.spinner.getSelectedItem().toString());

                if (player2Array.size() > player3Array.size()) {

                        player3Array.add(Integer.parseInt(player3score.getText().toString()));

                        for (int x : player3Array) {
                            total3 = total3 + x;
                        }

                        scores.setTotal3(totalGame - total3);
                        player3remain.setText(scores.getTotal3() + "");
                       Toast.makeText(getContext(), "Score has been deducted", Toast.LENGTH_SHORT).show();

                        player3score.setText("");

                    player3scores.setText("" + playerScoresList(player3Array));

                } else {
                    Toast.makeText(getContext(), "Player 1 turn", Toast.LENGTH_SHORT).show();
                    player3score.setText("");

                }
            }
        });

        deductZero3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total3 = 0;

                final int totalGame = Integer.parseInt(MainActivity.spinner.getSelectedItem().toString());

                if (player2Array.size() > player3Array.size()) {

                    player3Array.add(0);

                    for (int x : player3Array) {
                        total3 = total3 + x;
                    }

                    scores.setTotal3(totalGame - total3);
                    player3remain.setText(scores.getTotal3() + "");

                    player3score.setText("");

                    player3scores.setText("" + playerScoresList(player3Array));

                } else {
                    Toast.makeText(getContext(), "Player 1 turn", Toast.LENGTH_SHORT).show();
                    player3score.setText("");

                }
            }
        });


        undop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total1 = 0;

                player1Array.remove(player1Array.size() - 1);

                for (int x : player1Array) {
                    total1 = total1 + x;
                }

                scores.setTotal1(totalGame - total1);
                player1remain.setText(scores.getTotal1() + "");

                player1scores.setText("" + playerScoresList(player1Array));

                Toast.makeText(getContext(), "Player 1 turn, enter correct score", Toast.LENGTH_LONG).show();

            }

        });

        undop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total2 = 0;

                player2Array.remove(player2Array.size() - 1);

                for (int x : player2Array) {
                    total2 = total2 + x;
                }

                scores.setTotal2(totalGame - total2);
                player2remain.setText(scores.getTotal2() + "");

                player2scores.setText("" + playerScoresList(player2Array));

                Toast.makeText(getContext(), "Player 2 turn, enter correct score", Toast.LENGTH_LONG).show();

            }
        });

        undop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total3 = 0;

                player3Array.remove(player3Array.size() - 1);

                for (int x : player3Array) {
                    total3 = total3 + x;
                }

                scores.setTotal3(totalGame - total3);
                player3remain.setText(scores.getTotal3() + "");

                player3scores.setText("" + playerScoresList(player3Array));

                Toast.makeText(getContext(), "Player 3 turn, enter correct score", Toast.LENGTH_LONG).show();

            }
        });

        return view;
    }

    public static int largest(int first, int second, int third) {
        int max = first;
        if (second > max) {
            max = second;
        }
        if (third > max) {
            max = third;
        }
        return max;
    }

    public static int smallest(int first, int second, int third) {
        int min = first;
        if (second < min) {
            min = second;
        }
        if (third < min) {
            min = third;
        }
        return min;
    }

    public String playerScoresList(ArrayList<Integer> a) {
        List<Integer> tail = a.subList(Math.max(a.size() - 5, 0), a.size());

        String t = "";
        for (int strTemp : tail) {
            t = t + ">" + strTemp;
        }
        return t;
    }

    public void leader() {
        if (total1 > total2) {
            leader.setText(player1.getText() + " Leading by " + (total1 - total2));
        } else if (total1 < total2) {
            leader.setText(player2.getText() + " Leading by " + (total2 - total1));
        } else {
            leader.setText("Players are equal ");
        }
    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }
    public void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }
    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }
}