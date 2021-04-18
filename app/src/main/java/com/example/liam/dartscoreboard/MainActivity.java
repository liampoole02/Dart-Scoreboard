package com.example.liam.dartscoreboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    private int game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game=Integer.parseInt(spinner.getSelectedItem().toString());
                int total=0;

                if(player1score.getText().toString().equals("") || spinner.getSelectedItem()==null){
                    Toast.makeText(MainActivity.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                }else{
                    player1Array.add(Integer.parseInt(player1score.getText().toString()));

                    for(int x: player1Array){
                        total=total+x;
                    }
                    player1remain.setText(game-total+"");
                    player1score.setText("");
                }
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game=Integer.parseInt(spinner.getSelectedItem().toString());
                int total=0;

                if(player2score.getText().toString().equals("") || spinner.getSelectedItem()==null){
                    Toast.makeText(MainActivity.this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                }else{
                    player2Array.add(Integer.parseInt(player2score.getText().toString()));

                    for(int x: player1Array){
                        total=total+x;
                    }
                    player2remain.setText(game-total+"");
                    player2score.setText("");
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(this, ""+spinner.getSelectedItem(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}