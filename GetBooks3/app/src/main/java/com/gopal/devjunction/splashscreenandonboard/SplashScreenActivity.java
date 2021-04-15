package com.gopal.devjunction.splashscreenandonboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gopal.devjunction.getbooks.MainActivity;
import com.gopal.devjunction.getbooks.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash_screen );

        int duration = 1;
        new Handler(  ).postDelayed( new Runnable() {
            @Override
            public void run() {
                startActivity( new Intent( getApplicationContext() , IntroActivity.class ) );
                finish();
            }
        } , 1000 * duration );
    }
}