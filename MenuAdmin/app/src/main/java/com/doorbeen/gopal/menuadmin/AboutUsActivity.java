package com.doorbeen.gopal.menuadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_about_us );
        TextView terms = findViewById( R.id.termsAndCondition );
        TextView privacy = findViewById( R.id.privacyPolicy );

        terms.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( Intent.ACTION_VIEW , Uri.parse( "https://doorbeen.flycricket.io/terms.html" ) );
                startActivity( intent );
            }
        } );

        privacy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent( Intent.ACTION_VIEW , Uri.parse( "https://doorbeen.flycricket.io/privacy.html" ) );
                startActivity( intent1 );
            }
        } );

    }
}
