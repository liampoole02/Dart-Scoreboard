package com.example.liam.dartscoreboard.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


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

    private TextView player1average;
    private TextView player2average;
    private TextView player3average;

    private ArrayList<Integer> player1Array;
    private ArrayList<Integer> player2Array;
    private ArrayList<Integer> player3Array;

    private Button add1;
    private Button add2;
    private Button add3;

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

//    private TextView leader;

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;

    private TextToSpeech mTTS;

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

        player1average = view.findViewById(R.id.player1average);
        player2average = view.findViewById(R.id.player2average);
        player3average = view.findViewById(R.id.player3average);

        player1scores = view.findViewById(R.id.player1scores);
        player2scores = view.findViewById(R.id.player2scores);
        player3scores = view.findViewById(R.id.player3scores);

//        leader = view.findViewById(R.id.leader);

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

        mTTS = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Failed");
                }
            }
        });

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
                        mTTS.speak(player1Array.get(player1Array.size() - 1) + "Deducted", TextToSpeech.QUEUE_FLUSH, null);

                        if (scores.getTotal1() != 0) {
                            player1average.setText("Average per turn: " + (totalGame - scores.getTotal1()) / player1Array.size());
                        }
                        hideKeyboard(getActivity());
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
                setColors();
                setLowest();
                setMiddleColor();            }
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

                    if (scores.getTotal1() != 0) {
                        player1average.setText("Average per turn: " + (totalGame - scores.getTotal1()) / player1Array.size());
                    }

                    mTTS.speak("0 Deducted", TextToSpeech.QUEUE_FLUSH, null);
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
                        if (scores.getTotal2() != 0) {

                            player2average.setText("Average per turn: " + (totalGame - scores.getTotal2()) / player2Array.size());
                        }
                        hideKeyboard(getActivity());
                        mTTS.speak(player2Array.get(player2Array.size() - 1) + "Deducted", TextToSpeech.QUEUE_FLUSH, null);
                        player2score.setText("");
                    }

                    player2scores.setText("" + playerScoresList(player2Array));


                } else {
                    Toast.makeText(getContext(), "Player 3 turn", Toast.LENGTH_SHORT).show();
                    player2score.setText("");

                }
                setColors();
                setLowest();
                setMiddleColor();            }
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
                    mTTS.speak("0 Deducted", TextToSpeech.QUEUE_FLUSH, null);
                    if (scores.getTotal2() != 0) {

                        player2average.setText("Average per turn: " + (totalGame - scores.getTotal2()) / player2Array.size());
                    }
                    player2score.setText("");

                    player2scores.setText("" + playerScoresList(player2Array));

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

                    mTTS.speak(player3Array.get(player3Array.size() - 1) + "Deducted", TextToSpeech.QUEUE_FLUSH, null);

                    if (scores.getTotal3() != 0) {
                        player3average.setText("Average per turn: " + (totalGame - scores.getTotal3()) / player3Array.size());
                    }

                    hideKeyboard(getActivity());
                    player3score.setText("");
                    player3scores.setText("" + playerScoresList(player3Array));
                } else {
                    Toast.makeText(getContext(), "Player 1 turn", Toast.LENGTH_SHORT).show();
                    player3score.setText("");

                }
                setColors();
                setLowest();
                setMiddleColor();            }
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
                    mTTS.speak("0 Deducted", TextToSpeech.QUEUE_FLUSH, null);

                    if (scores.getTotal3() != 0) {
                        player3average.setText("Average per turn: " + (totalGame - scores.getTotal3()) / player3Array.size());
                    }

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

                if (scores.getTotal1() != 0) {
                    player1average.setText("Average per turn: " + (totalGame - scores.getTotal1()) / player1Array.size());
                }

                mTTS.speak("Undo success, now enter the correct score, do it now", TextToSpeech.QUEUE_FLUSH, null);

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

                if (scores.getTotal2() != 0) {
                    player2average.setText("Average per turn: " + (totalGame - scores.getTotal2()) / player2Array.size());
                }
                mTTS.speak("Undo success, now enter the correct score, do it now", TextToSpeech.QUEUE_FLUSH, null);

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

                if (scores.getTotal3() != 0) {
                    player3average.setText("Average per turn: " + (totalGame - scores.getTotal3()) / player3Array.size());
                }
                mTTS.speak("Undo success, now enter the correct score, do it now", TextToSpeech.QUEUE_FLUSH, null);

            }
        });

        return view;
    }

    public void setColors() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", scores.getTotal1());
        map.put("2", scores.getTotal2());
        map.put("3", scores.getTotal3());

        if (getMapKeyWithHighestValue(map).equals("1")) {
            player1layout.setBackgroundColor(Color.RED);
        } else if (getMapKeyWithHighestValue(map).equals("2")) {
            player2layout.setBackgroundColor(Color.RED);
        } else {
            player3layout.setBackgroundColor(Color.RED);
        }
    }

    public void setLowest() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", scores.getTotal1());
        map.put("2", scores.getTotal2());
        map.put("3", scores.getTotal3());

        if (getMapKeyWithLowestValue(map).equals("1")) {
            player1layout.setBackgroundColor(Color.GREEN);
        } else if (getMapKeyWithLowestValue(map).equals("2")) {
            player2layout.setBackgroundColor(Color.GREEN);
        } else {
            player3layout.setBackgroundColor(Color.GREEN);
        }
    }

    public String getMapKeyWithHighestValue(HashMap<String, Integer> map) {
        String keyWithHighestVal = "";

        // getting the maximum value in the Hashmap
        int maxValueInMap = (Collections.max(map.values()));

        //iterate through the map to get the key that corresponds to the maximum value in the Hashmap
        for (Map.Entry<String, Integer> entry : map.entrySet()) {  // Iterate through hashmap
            if (entry.getValue() == maxValueInMap) {
                keyWithHighestVal = entry.getKey();     // this is the key which has the max value
            }

        }
        return keyWithHighestVal;
    }

    public void setMiddleColor() {
        ArrayList<String> a=new ArrayList<>();

        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", scores.getTotal1());
        map.put("2", scores.getTotal2());
        map.put("3", scores.getTotal3());

        a.add(getMapKeyWithHighestValue(map));
        a.add(getMapKeyWithLowestValue(map));

        if(!a.contains("1")){
            player1layout.setBackgroundColor(Color.YELLOW);
        }else if(!a.contains("2")){
            player2layout.setBackgroundColor(Color.YELLOW);
        }else if(!a.contains("3")){
            player3layout.setBackgroundColor(Color.YELLOW);
        }
    }

    public void setMiddlePlayerColor() {
        if (scores.getTotal3() > scores.getTotal2() && scores.getTotal1() < scores.getTotal2()) {
            player2layout.setBackgroundColor(Color.YELLOW);
        } else if (scores.getTotal3() < scores.getTotal2() && scores.getTotal1() > scores.getTotal2()) {
            player2layout.setBackgroundColor(Color.YELLOW);
        }
    }

    public String getMapKeyWithLowestValue(HashMap<String, Integer> map) {
        String keyWithLowestVal = "";

        // getting the maximum value in the Hashmap
        int maxValueInMap = (Collections.min(map.values()));

        //iterate through the map to get the key that corresponds to the maximum value in the Hashmap
        for (Map.Entry<String, Integer> entry : map.entrySet()) {  // Iterate through hashmap
            if (entry.getValue() == maxValueInMap) {
                keyWithLowestVal = entry.getKey();     // this is the key which has the max value
            }

        }
        return keyWithLowestVal;
    }

    public String playerScoresList(ArrayList<Integer> a) {
        List<Integer> tail = a.subList(Math.max(a.size() - 5, 0), a.size());

        String t = "";
        for (int strTemp : tail) {
            t = t + ">" + strTemp;
        }
        return t;
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
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}