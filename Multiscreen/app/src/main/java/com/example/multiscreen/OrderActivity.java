package com.example.multiscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Intent intent  = getIntent();
        String message  = intent.getStringExtra(MainActivity.MSG);

        //set it inside the textview

        TextView textView = findViewById(R.id.order);
        textView.setText(message);


    }
}
