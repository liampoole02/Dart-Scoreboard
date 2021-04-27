package com.example.liam.dartscoreboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText player1;
    private EditText player2;
    Spinner spinner;

    private EditText player1score;
    private EditText player2score;

    private TextView player1scores;
    private TextView player2scores;

    private ArrayList<Integer> player1Array;
    private ArrayList<Integer> player2Array;

    private Button add1;
    private Button add2;
    private Button reset;

    private Button undop1;
    private Button undop2;

    private TextView player1remain;
    private TextView player2remain;

    private LinearLayout player1layout;
    private LinearLayout player2layout;

    private int game;

    private int total1;
    private int total2;

    private Game scores;

    private TextView leader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.GRAY);
        actionBar.setBackgroundDrawable(colorDrawable);

        scores=new Game();

        player1=findViewById(R.id.player1);
        player2=findViewById(R.id.player2);

        player1score=findViewById(R.id.player1score);
        player2score=findViewById(R.id.player2score);

        add1=findViewById(R.id.add1);
        add2=findViewById(R.id.add2);

        undop1=findViewById(R.id.undop1);
        undop2=findViewById(R.id.undop2);

        reset=findViewById(R.id.reset);

        spinner=findViewById(R.id.spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.amount, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        player1Array=new ArrayList();
        player2Array=new ArrayList();

        player1remain=findViewById(R.id.player1remain);
        player2remain=findViewById(R.id.player2remain);

        player1layout=findViewById(R.id.player1layout);
        player2layout=findViewById(R.id.player2layout);

        player1scores=findViewById(R.id.player1scores);
        player2scores=findViewById(R.id.player2scores);

        leader=findViewById(R.id.leader);

        scores.setTotal1(0);
        scores.setTotal2(0);

        game = Integer.parseInt(spinner.getSelectedItem().toString());

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game = Integer.parseInt(spinner.getSelectedItem().toString());

                if(player1Array.size()==player2Array.size()) {
                    total1=0;

                    if (player1score.getText().toString().equals("") || spinner.getSelectedItem() == null) {
                        Toast.makeText(MainActivity.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                    } else {
                        player1Array.add(Integer.parseInt(player1score.getText().toString()));

                        for (int x : player1Array) {
                            total1 = total1 + x;
                        }

                        scores.setTotal1(game - total1);
                        player1remain.setText(scores.getTotal1() + "");

                        player1score.setText("");

                        if (scores.getTotal1() < scores.getTotal2()) {
                            player1layout.setBackgroundColor(Color.GREEN);
                            player2layout.setBackgroundColor(Color.RED);
                        } else if(scores.getTotal1() == scores.getTotal2()){
                            player2layout.setBackgroundColor(Color.GRAY);
                            player1layout.setBackgroundColor(Color.GRAY);
                        }else {
                            player2layout.setBackgroundColor(Color.GREEN);
                            player1layout.setBackgroundColor(Color.RED);
                        }
                    }

                    player1scores.setText(""+playerScoresList(player1Array));

                }else {
                    Toast.makeText(MainActivity.this, "Player 2 turn", Toast.LENGTH_SHORT).show();
                    player1score.setText("");
                }

                leader();
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total2=0;

                game = Integer.parseInt(spinner.getSelectedItem().toString());

                if(player1Array.size()!=player2Array.size()) {

                if (player2score.getText().toString().equals("") || spinner.getSelectedItem() == null) {
                    Toast.makeText(MainActivity.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                } else {
                    player2Array.add(Integer.parseInt(player2score.getText().toString()));

                    for (int x : player2Array) {
                        total2 = total2 + x;
                    }

                    scores.setTotal2(game - total2);
                    player2remain.setText(scores.getTotal2() + "");

                    player2score.setText("");

                    if (scores.getTotal1() < scores.getTotal2()) {
                        player1layout.setBackgroundColor(Color.GREEN);
                        player2layout.setBackgroundColor(Color.RED);
                    } else if(scores.getTotal1() == scores.getTotal2()){
                        player2layout.setBackgroundColor(Color.GRAY);
                        player1layout.setBackgroundColor(Color.GRAY);
                    }
                    else {
                        player2layout.setBackgroundColor(Color.GREEN);
                        player1layout.setBackgroundColor(Color.RED);
                    }
                }

                    player2scores.setText(""+playerScoresList(player2Array));

                    leader();

            }else{
                    Toast.makeText(MainActivity.this, "Player 1 turn", Toast.LENGTH_SHORT).show();
                    player2score.setText("");

                }
            }
        });



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
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

                scores.setTotal1(game - total1);
                player1remain.setText(scores.getTotal1() + "");

                player1scores.setText(""+playerScoresList(player1Array));

                Toast.makeText(MainActivity.this, "Player 1 turn, enter correct score", Toast.LENGTH_LONG).show();

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

                scores.setTotal2(game-total2);
                player2remain.setText(scores.getTotal2() + "");

                player2scores.setText(""+playerScoresList(player2Array));


                Toast.makeText(MainActivity.this, "Player 2 turn, enter correct score", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.twoplayer:
                startActivity(new Intent(MainActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
            case R.id.threeplayer:
                startActivity(new Intent(MainActivity.this, ThreePlayer.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
        }
        return false;

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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}