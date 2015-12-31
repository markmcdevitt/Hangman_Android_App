package com.mcdevitt.myfinalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Mark on 21/11/2015.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void hangman(View v) {
        Intent myIntent = new Intent(this, Hangman.class);
        startActivity(myIntent);
    }

    public void maps(View v) {
        Intent myIntent = new Intent(this, MapsActivity.class);
        startActivity(myIntent);
    }

    public void scores(View v) {
        Intent myIntent = new Intent(this, ScoreActivity.class);
        startActivity(myIntent);
    }

}