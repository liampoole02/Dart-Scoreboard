package com.example.liam.dartscoreboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreePlayer extends AppCompatActivity {

    private VideoView congrats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three_player);

        congrats=findViewById(R.id.congrats);

        String path="android.resource://"+getPackageName()+"/" +R.raw.congratulations;

        Uri uri=Uri.parse(path);
        congrats.setVideoURI(uri);

        congrats.setVisibility(View.VISIBLE);
        congrats.start();
    }

}