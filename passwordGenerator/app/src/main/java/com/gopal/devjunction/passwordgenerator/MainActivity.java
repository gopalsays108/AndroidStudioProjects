package com.gopal.devjunction.passwordgenerator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView passdwordTv;
    private TextView passwordLengthTv;
    private SeekBar seekBar;
    private Button generateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        passdwordTv = findViewById( R.id.passwordTv );
        passwordLengthTv = findViewById( R.id.passswordLengthTv );
        seekBar = findViewById( R.id.seekBar );
        generateBtn  =findViewById( R.id.generateBtn );

        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                passwordLengthTv.setText( "Lenght : " + progress );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        } );

        generateBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = PasswordGenerator.Process( seekBar.getProgress() );
                passdwordTv.setText( password );
            }
        } );
    }
}