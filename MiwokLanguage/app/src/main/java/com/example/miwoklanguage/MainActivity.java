package com.example.miwoklanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find the view that show te number category
        TextView number = findViewById(R.id.numbers);

        //set click listner on that view
        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creating intent to open number acticity
                Intent i = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(i);
            }
        });

        TextView family = findViewById(R.id.family);

        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent family = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(family);
            }
        });

        TextView colors = findViewById(R.id.colors);

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent color = new Intent(MainActivity.this, ColorActivity.class);
                startActivity(color);
            }
        });

        TextView phrases = findViewById(R.id.phrases);

        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phrase = new Intent(MainActivity.this, PhraseActivity.class);
                startActivity(phrase);

            }
        });

    }

}

/*
    public void openNumbersList(View view){

        Intent i = new Intent(this , NumbersActivity.class);
        startActivity(i);
    }

    public void openFamilyList(View view) {
        Intent i = new Intent(this , FamilyActivity.class);
        startActivity(i);
    }

 */
