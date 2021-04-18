package com.example.liam.dartscoreboard;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText player1;
    private EditText player2;
    Spinner spinner;

    private EditText player1score;
    private EditText player2score;

    private ArrayList<Integer> player1Array;
    private ArrayList<Integer> player2Array;

    private Button add1;
    private Button add2;

    private TextView player1remain;
    private TextView player2remain;

    private LinearLayout player1layout;
    private LinearLayout player2layout;

    private int game;

    private int total1;
    private int total2;

    private Game scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scores=new Game();

        player1=findViewById(R.id.player1);
        player2=findViewById(R.id.player2);

        player1score=findViewById(R.id.player1score);
        player2score=findViewById(R.id.player2score);

        add1=findViewById(R.id.add1);
        add2=findViewById(R.id.add2);

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

        scores.setTotal1(0);
        scores.setTotal2(0);

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game=Integer.parseInt(spinner.getSelectedItem().toString());

                if(player1score.getText().toString().equals("") || spinner.getSelectedItem()==null){
                    Toast.makeText(MainActivity.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                }else{
                    player1Array.add(Integer.parseInt(player1score.getText().toString()));

                    for(int x: player1Array){
                        total1=total1+x;
                    }

                    player1remain.setText(game-total1+"");

                    scores.setTotal1(game-total1);
                    player1score.setText("");

                    if(scores.getTotal1()<scores.getTotal2()){
                        player1layout.setBackgroundColor(Color.GREEN);
                        player2layout.setBackgroundColor(Color.RED);
                    }else{
                        player2layout.setBackgroundColor(Color.GREEN);
                        player1layout.setBackgroundColor(Color.RED);
                    }
                }
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game=Integer.parseInt(spinner.getSelectedItem().toString());

                if(player2score.getText().toString().equals("") || spinner.getSelectedItem()==null){
                    Toast.makeText(MainActivity.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                }else{
                    player2Array.add(Integer.parseInt(player2score.getText().toString()));

                    for(int x: player2Array){
                        total2=total2+x;
                    }

                    player2remain.setText(game-total2+"");
                    scores.setTotal2(game-total2);

                    Toast.makeText(MainActivity.this, ""+scores.getTotal1()+" "+scores.getTotal2(), Toast.LENGTH_SHORT).show();


                    player2score.setText("");

                    if(scores.getTotal1()<scores.getTotal2()){
                        player1layout.setBackgroundColor(Color.GREEN);
                        player2layout.setBackgroundColor(Color.RED);
                    }else{
                        player2layout.setBackgroundColor(Color.GREEN);
                        player1layout.setBackgroundColor(Color.RED);
                    }
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}