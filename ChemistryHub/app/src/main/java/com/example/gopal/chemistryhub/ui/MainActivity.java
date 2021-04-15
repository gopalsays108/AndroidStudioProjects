package com.example.gopal.chemistryhub.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gopal.chemistryhub.R;
import com.example.gopal.chemistryhub.ui.register.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        SystemClock.sleep(2000);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser == null) {
            Intent loginActivity = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(loginActivity);
            finish();
        } else {
            Intent MainActivity2 = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(MainActivity2);
            finish();
        }
    }
}
