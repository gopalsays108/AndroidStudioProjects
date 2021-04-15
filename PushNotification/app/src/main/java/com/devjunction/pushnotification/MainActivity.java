package com.devjunction.pushnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView profileLabel;
    private TextView userLabel;
    private TextView notificationLabel;

    private ViewPager viewPager;

    private PagerViewAdapter pagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationLabel = findViewById( R.id.notificationLabel );
        userLabel = findViewById( R.id.allUserLabel );
        profileLabel = findViewById( R.id.profileLabel );

        viewPager = findViewById( R.id.mainPager );

        pagerViewAdapter = new PagerViewAdapter( getSupportFragmentManager(), 0 );
        viewPager.setAdapter( pagerViewAdapter  );
    }
}