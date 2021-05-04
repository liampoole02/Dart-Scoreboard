package com.example.liam.dartscoreboard.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;

public class MainFragment extends Fragment {

    private EditText player1;
    private EditText player2;

    private EditText player1score;
    private EditText player2score;

    private TextView player1scores;
    private TextView player2scores;

    private TextView player1average;
    private TextView player2average;

    private ArrayList<Integer> player1Array;
    private ArrayList<Integer> player2Array;

    private Button add1;
    private Button add2;

    private Button undop1;
    private Button undop2;

    private Button deductZero1;
    private Button deductZero2;

    private TextView player1remain;
    private TextView player2remain;

    private LinearLayout player1layout;
    private LinearLayout player2layout;

    private int total1;
    private int total2;

    Game scores;

    int totalGame;

    private TextView leader;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    private TextToSpeech mTTS;

    private Spinner nameSelector1;
    private Spinner nameSelector2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        scores = new Game();

        player1 = view.findViewById(R.id.player1);
        player2 = view.findViewById(R.id.player2);

        player1score = view.findViewById(R.id.player1score);
        player2score = view.findViewById(R.id.player2score);

        add1 = view.findViewById(R.id.add1);
        add2 = view.findViewById(R.id.add2);

        undop1 = view.findViewById(R.id.undop1);
        undop2 = view.findViewById(R.id.undop2);

        deductZero1 = view.findViewById(R.id.deductZero1);
        deductZero2 = view.findViewById(R.id.deductZero2);

        player1Array = new ArrayList();
        player2Array = new ArrayList();

        player1remain = view.findViewById(R.id.player1remain);
        player2remain = view.findViewById(R.id.player2remain);

        player1average = view.findViewById(R.id.player1average);
        player2average = view.findViewById(R.id.player2average);

        player1layout = view.findViewById(R.id.player1layout);
        player2layout = view.findViewById(R.id.player2layout);

        player1scores = view.findViewById(R.id.player1scores);
        player2scores = view.findViewById(R.id.player2scores);

        leader = view.findViewById(R.id.leader);

        nameSelector1=view.findViewById(R.id.nameSelector1);
        nameSelector2=view.findViewById(R.id.nameSelector2);

        scores.setTotal1(0);
        scores.setTotal2(0);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSelector1.setAdapter(adapter);

        nameSelector1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                player1.setText(nameSelector1.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.names, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nameSelector2.setAdapter(adapter2);

        nameSelector2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                player2.setText(nameSelector2.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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

        mTTS=new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                 if(i== TextToSpeech.SUCCESS){
                    int result= mTTS.setLanguage(Locale.US);
                     if(result==TextToSpeech.LANG_MISSING_DATA ||
                      result==TextToSpeech.LANG_NOT_SUPPORTED){
                         Log.e("TTS", "Language not supported");
                     }
                 }else{
                     Log.e("TTS", "Failed");
                 }
            }
        });

        startChronometer(view);

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int totalGame = Integer.parseInt(MainActivity.spinner.getSelectedItem().toString());

                if (player1Array.size() == player2Array.size()) {
                    total1 = 0;

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

                        getColors();
                    }

                    if (scores.getTotal1() != 0) {
                        player1average.setText("Average per turn: " + (totalGame - scores.getTotal1()) / player1Array.size());
                    }
                    hideKeyboard(getActivity());

                    player1scores.setText("" + playerScoresList(player1Array));

                    mTTS.speak(player1Array.get(player1Array.size()-1)+"Deducted and"+scores.getTotal1()+"Left for "+player1.getText(), TextToSpeech.QUEUE_FLUSH, null);


                } else {
                    Toast.makeText(getContext(), "Player 2 turn", Toast.LENGTH_SHORT).show();
                    player1score.setText("");
                }

                leader();
            }
        });

        deductZero1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final int totalGame = Integer.parseInt(MainActivity.spinner.getSelectedItem().toString());


                if (player1Array.size() == player2Array.size()) {
                    total1 = 0;

                    player1Array.add(0);

                    for (int x : player1Array) {
                        total1 = total1 + x;
                    }

                    scores.setTotal1(totalGame - total1);
                    player1remain.setText(scores.getTotal1() + "");

                    player1score.setText("");

                    getColors();

                    player1scores.setText("" + playerScoresList(player1Array));
                    if (scores.getTotal1() != 0) {

                        player1average.setText("Average per turn: " + (totalGame - scores.getTotal1()) / player1Array.size());
                    }
                    mTTS.speak("0 Deducted", TextToSpeech.QUEUE_FLUSH, null);

                } else {
                    Toast.makeText(getContext(), "Player 2 turn", Toast.LENGTH_SHORT).show();
                    player1score.setText("");
                }

                leader();
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total2 = 0;

                final int totalGame = Integer.parseInt(MainActivity.spinner.getSelectedItem().toString());

                if (player1Array.size() != player2Array.size()) {

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

                        getColors();

                    }

                    if (scores.getTotal2() != 0) {

                        player2scores.setText("" + playerScoresList(player2Array));
                    }

                    hideKeyboard(getActivity());

                    player2average.setText("Average per turn: " + (totalGame - scores.getTotal2()) / player2Array.size());
                    mTTS.speak(player2Array.get(player2Array.size()-1)+"Deducted and "+scores.getTotal2()+"Left for "+player2.getText(), TextToSpeech.QUEUE_FLUSH, null);

                    leader();

                } else {
                    Toast.makeText(getContext(), "Player 1 turn", Toast.LENGTH_SHORT).show();
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

                    player2score.setText("");

                    getColors();

                }

                player2scores.setText("" + playerScoresList(player2Array));

                if (scores.getTotal2() != 0) {
                    player2average.setText("Average per turn: " + (totalGame - scores.getTotal2()) / player2Array.size());
                }

                mTTS.speak("0 Deducted", TextToSpeech.QUEUE_FLUSH, null);

                leader();
            }
        });

        undop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total1 = 0;

                if (player1Array.size() != 0 && player1Array.size()>player2Array.size()) {
                    player1Array.remove(player1Array.size() - 1);

                    for (int x : player1Array) {
                        total1 = total1 + x;
                    }

                    scores.setTotal1(totalGame - total1);
                    player1remain.setText(scores.getTotal1() + "");
                    if (scores.getTotal1() != 0) {

                        player1average.setText("Average per turn: " + (totalGame - scores.getTotal1()) / player1Array.size());
                    }
                    player1scores.setText("" + playerScoresList(player1Array));

                    mTTS.speak("Undo success, now enter the correct score, do it now", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Toast.makeText(getContext(), "Cannot undo because there are no scores present", Toast.LENGTH_LONG).show();
                }


            }

        });

        undop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total2 = 0;

                if (player2Array.size() != 0) {
                    player2Array.remove(player2Array.size() - 1);

                    for (int x : player2Array) {
                        total2 = total2 + x;
                    }

                    scores.setTotal2(totalGame - total2);
                    player2remain.setText(scores.getTotal2() + "");

                    if (scores.getTotal2() != 0) {
                        player2average.setText("Average per turn: " + (totalGame - scores.getTotal2()) / player2Array.size());
                    }
                    player2scores.setText("" + playerScoresList(player2Array));

                    mTTS.speak("Undo success, now enter the correct score, do it now", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Toast.makeText(getContext(), "Cannot undo because there are no scores present", Toast.LENGTH_LONG).show();

                }


            }
        });

        return view;
    }

    public String playerScoresList(ArrayList<Integer> a) {
        List<Integer> tail = a.subList(Math.max(a.size() - 5, 0), a.size());

        String t = "";
        for (int strTemp : tail) {
            t = t + " > " + strTemp;
        }
        return t;
    }

    public void getColors(){
        if (scores.getTotal1() < scores.getTotal2()) {
            player1layout.setBackgroundColor(Color.parseColor("#8BC34A"));
            player2layout.setBackgroundColor(Color.parseColor("#CF5151"));
        } else if (scores.getTotal1() == scores.getTotal2()) {
            player2layout.setBackgroundColor(Color.GRAY);
            player1layout.setBackgroundColor(Color.GRAY);
        } else {
            player2layout.setBackgroundColor(Color.parseColor("#8BC34A"));
            player1layout.setBackgroundColor(Color.parseColor("#CF5151"));
        }
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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}