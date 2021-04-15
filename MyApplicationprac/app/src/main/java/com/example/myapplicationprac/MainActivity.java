package com.example.myapplicationprac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;       // global variable

    public void increment(View view) {

        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        // int quantity = 2;                 // yeah tha local variable
        quantity = quantity - 1;
        display(quantity);
    }

    public void sumbitOrder(View view) {
        int price = quantity * 5;
        String priceMessage = "TOTAL " + price ;
        priceMessage = priceMessage + "\nWohoo";
        displayMessage(priceMessage);
        // same iss baar displayprice method ko call kiya  jo main calss mien define hai
    }

    private void displayMessage(String priceMessage) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(priceMessage);
    }


    /**
     * this method siaplay the given wuantity number on the screen
     */
    private void display(int number) {                        // //TextView k baad kuch bhi likh sakta hu , fir uske according .setTExt change krna hoga
        TextView quantityTextView = findViewById(
                R.id.quantity_text_view
        );
        quantityTextView.setText("" + number);
    }

    /**
     * this method display the given quantity value on the screen
     */
    // private void displayPrice(int number) {
    //   TextView priceTextView =  findViewById(R.id.price_text_view);
    //  priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    //}
}




