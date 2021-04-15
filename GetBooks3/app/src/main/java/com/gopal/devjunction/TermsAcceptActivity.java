package com.gopal.devjunction;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gopal.devjunction.getbooks.MainActivity;
import com.gopal.devjunction.getbooks.R;

public class TermsAcceptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        //when this activity is about to launch check shared preference
        if (restoredPrefData().equals( "yes" )) {
            startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
            finish();
        }

        setContentView( R.layout.activity_terms_accept );
        Button acceptBtn = findViewById( R.id.acceptBtn );
        TextView acceptTerm = findViewById( R.id.accpetTerms );

        acceptBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences( "privacyAccept" , MODE_PRIVATE );
                SharedPreferences.Editor editor  = sharedPreferences.edit();
                editor.putString( "isAccpeted" , "yes" );
                editor.apply();

                startActivity( new Intent( getApplicationContext() , MainActivity.class ) );
                finish();
            }
        } );

        acceptTerm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://getbooks.flycricket.io/privacy.html";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData( Uri.parse(url));
                startActivity(i);
            }
        } );
    }

    private String restoredPrefData() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences( "privacyAccept", MODE_PRIVATE );
        return sharedPreferences.getString( "isAccpeted", "no" );
    }
}