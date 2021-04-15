package com.gopal.getbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        //------- Splash Screen Duration--------
        int secondDelayed = 1;
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
                finish();
            }
        }, secondDelayed * 1000 );
    }
}