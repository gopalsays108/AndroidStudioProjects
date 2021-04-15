package com.example.miwoklanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        //array list of words

        ArrayList<String> words = new ArrayList<String>();
        words.add("one");
        words.add("two");
        words.add("three");
        words.add("four");
        words.add("five");
        words.add("six");
        words.add("seven");
        words.add("eight");
        words.add("nine");
        words.add("ten");

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , words);

        ListView listView = findViewById(R.id.list);

        listView.setAdapter(itemAdapter);

    }
}

// somw old works expirements

/*
        //find the root view for whole layout
        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);
        // using while loop
        //make an variable index to keep an track of index position
        int index = 0;
        while (index < words.size()) {

            TextView wordView = new TextView(this);
            wordView.setText(words.get(index));
            rootView.addView(wordView);
            index++;

        }

 */

/*
// using for loop
        for (int index = 0; index < words.size(); index++) {
            TextView wordView = new TextView(this);
            wordView.setText(words.get(index));
            rootView.addView(wordView);

        }

 */


      /*
        // Log message using loop while loop
        int in = 0;
        int index = 0;
        while (in < words.size()){

            Log.v("MainActivity" , " words are  : " + words.get(index) + "\n");
            in++;
            index++;
        }

        int index = 0;

        TextView wordView  = new TextView(this);
        wordView.setText(words.get(index));
        rootView.addView(wordView);


        index = index + 1;
        TextView wordView2 = new TextView(this);
        wordView2.setText(words.get(index));
        rootView.addView(wordView2);

        index = index + 1;
        TextView wordView3 = new TextView(this);
        wordView3.setText(words.get(index));
        rootView.addView(wordView3);*/

//verify the each conetent of array list by printing the each element to the logs
//Log.v("NumberActivity" ,"words ar zero : " + words.get(0) + " " + words.get(1) + " " + words.get(2));

/*
        //CREATE AN ARRAY OF WORDS
        String[] words = new String[10];

        words[0] = "one";
        words[1] = "two";
        words[2] = "three";
        words[3] = "four";
        words[4] = "five";
        words[5] = "six";
        words[6] = "seven";
        words[7] = "eight";
        words[8] = "nine";
        words[9] = "ten";

        int len = words.length;

       Log.v("NumberActivity", "words at 0 : " + words[0] );
        Log.v("NumberActivity" , "word at 1 : " + words[1] + words[2] + words [3] + words[4] + "  \n" + len);

*/


