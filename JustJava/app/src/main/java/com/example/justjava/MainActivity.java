package com.example.justjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int increment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FunActivity.class);
                intent.putExtra("link","https://www.instagram.com/its___gopal/");
                startActivity(intent);
            }
        });

        TextView textView = findViewById(R.id.quantity_text_view);
        //textView.setText(getResources().getString(R.string.sample));
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display() {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + increment);
    }
}