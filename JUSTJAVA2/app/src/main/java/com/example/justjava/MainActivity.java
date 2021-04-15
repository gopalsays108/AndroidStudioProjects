package com.example.justjava;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    int quantity = 1;       // global variable if not initilised it take default value of int that is 0

    public void increment(View view) {
       if(quantity != 100) {
           quantity = quantity + 1;
       }
       if(quantity >100){

           Toast.makeText(this , "YOU CANNOT HAVE MORE THAN 100 COFFEE", Toast.LENGTH_SHORT).show();
           return;
       }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        // int quantity = 2;                 // yeah tha local variable
        if(quantity == 1) {
            Toast.makeText(this , "YOU CANNOT HAVE COFFEE LESS THAN 0", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity-1;
        displayQuantity(quantity);
    }

    public void sumbitOrder(View view)
    {
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);  // using casting is now redundant , no onger need
        boolean whippedcream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCream  = findViewById(R.id.chocolate_cream_checkbox);
        boolean chocolate = chocolateCream.isChecked();
        EditText typeName  = findViewById(R.id.edit_text);
        String name = typeName.getText().toString().trim();
       // Log.v("MainActivity" , "Has whipped cream : " + name);

        int price = calculatePrice(whippedcream , chocolate);
        //String priceMessage = createOrderSummary(price); // both are corect just to optimise
        String message = createOrderSummary(price , whippedcream , name , chocolate);
       // Log.v("MainActivity" , "this " + message);

        Log.i("MAIL LOG",message);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, " just " + name);
       // intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"gkkg738@gmail.com,surajgiri@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, message);
        //intent.putExtra(Intent.EXTRA_SUBJECT , " Subject : JUST JAVA ORDER FOT " + name);
        //intent.putExtra(Intent.EXTRA_TEXT , message);
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Mail account not configured", Toast.LENGTH_SHORT).show();
        }
                                                     // same iss baar displayprice method ko call kiya  jo main calss mien define hai
    }



    /*create summuary of the order
    *
    * @param price of the order
    * this methods print summary
    * @wgippedCream wheather user want whipped cream or not
    * @chocolate whether user wants chocolate
    * @name name os the user
    *@return summary
    */
    private  String  createOrderSummary(int price , boolean whippedCream , String name , boolean chocolate)
    {
        String priceMessage = "NAME : " + name ;
        priceMessage +=     "\nAdd Whipped Cream ? :" + whippedCream;
        priceMessage += "\nAdd Whipped Cream ? :" + chocolate;
        priceMessage += "\nQUANITY : " + quantity;
        priceMessage += "\nTOTAL: $" + price;        //+= or == are both correct
        priceMessage = priceMessage + "\nTHANK YOU!!";
        priceMessage = priceMessage;
        String summary = priceMessage;
        return summary;
    }

 /*
   * this methods calcuate the price of the order
   * @return total price
  */
    private int calculatePrice(boolean whippedCream , boolean chocolate)
    {
       // int price = quantity * 5;  both are correct just to optimise
       int basePrice = 5;

       if(whippedCream)
       {
           basePrice = basePrice + 1;
       }

       if(chocolate)
       {
           basePrice = basePrice + 2 ;
       }


         return quantity * basePrice;

    }
/*
        private void displayMessage(String priceMessage)
        {
            TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
            orderSummaryTextView.setText(priceMessage);
        }
*/

    /**
     * this method siaplay the given wuantity number on the screen
     */
    private void displayQuantity(int number)
    {
        TextView quantityTextView = findViewById(                 // //TextView k baad kuch bhi likh sakta hu , fir uske according .setTExt change krna hoga
                R.id.quantity_text_view
        );
        quantityTextView.setText("" + number);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(),"Gopal",Toast.LENGTH_SHORT).show();
    }

    /**
     * this method display the given quantity value on the screen
     */
   // private void displayPrice(int number) {
     //   TextView priceTextView =  findViewById(R.id.price_text_view);
      //  priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    //}
}


