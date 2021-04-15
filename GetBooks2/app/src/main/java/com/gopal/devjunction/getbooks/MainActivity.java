package com.gopal.devjunction.getbooks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        new Handler(  ).postDelayed( new Runnable() {
            @Override
            public void run() {
                Toast.makeText( MainActivity.this, "Hello", Toast.LENGTH_SHORT ).show();
            }
        } , 2000);
    }
}