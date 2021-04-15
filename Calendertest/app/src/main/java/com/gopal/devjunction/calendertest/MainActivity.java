package com.gopal.devjunction.calendertest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }

    public void showCalendar(View view) {
        Calendar calendar = Calendar.getInstance() ;
        int year = calendar.get( Calendar.YEAR );
        int month = calendar.get( Calendar.MONTH );
        int day = calendar.get( Calendar.DAY_OF_MONTH );

        DatePickerDialog datePickerDialog = new DatePickerDialog( MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText( MainActivity.this, year + " " + (month + 1) + " " + dayOfMonth , Toast.LENGTH_SHORT ).show();
            }
        },year,month,day );
        datePickerDialog.show();
    }
}