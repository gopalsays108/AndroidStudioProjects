package com.gopal.gettogether.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gopal.gettogether.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_start );

        Button startRegistration = findViewById( R.id.startRegistrationButton );
        Button startLogin = findViewById( R.id.startLoginBtn );

        startRegistration.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent( getApplicationContext(), RegisterActivity.class );
                startActivity( registerIntent );

            }
        } );

        startLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent( getApplicationContext(), LoginActivity.class );
                startActivity( loginIntent );
            }
        } );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected( item );
    }
}
