package com.example.practiceset2;
import java.util.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int dollor = 40;
        int dollorToYen = 118;
        int yen =  dollorToYen * dollor;
        display(yen);
        /*int day1 = 15;         yeah doosra
        int day2 = 22;
        int day3 = 18;
        display((day1 + day2 + day3 ) /3);
                                               yeah pehla practice tha
        * int weekend = 9;
        int weekday = 5;
        int optimalHour = 7 * 8;
        int actualHours  = weekday * 5;
        actualHours = actualHours + weekend * 2;
        int solution = optimalHour - actualHours;
        display(solution);*/
    }

    private void display(double i) {
        TextView dis = findViewById(R.id.displayTotal);
    dis.setText("   " +i);
    }

}
