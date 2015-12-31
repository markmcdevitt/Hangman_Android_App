package com.mcdevitt.myfinalapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mark on 25/11/2015.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {//we created the object and directly called the method post delayed
            @Override
            public void run() {
                nextActivity();
                finish();

            }
        }, 5000);

    }

    public void nextActivity() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


}
