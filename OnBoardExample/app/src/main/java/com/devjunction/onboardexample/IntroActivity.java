package com.devjunction.onboardexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout tabIndicator;
    private Button nextBtn;
    private int position = 0;
    private Animation btnAnimation;
    private Button getStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        //make the activity full screen
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        //when this activity is about to launch check shared preference
        if (restoredPrefData()){
            startActivity( new Intent( getApplicationContext() , MainActivity.class ) );
            finish();
        }

        setContentView( R.layout.activity_intro_a_ct );

        getSupportActionBar().hide();

        tabIndicator = findViewById( R.id.tab_indicator );
        nextBtn = findViewById( R.id.nextBtn );
        getStarted = findViewById( R.id.getStartedBtn );
        btnAnimation = AnimationUtils.loadAnimation( getApplicationContext(), R.anim.button_animation );

        //fillin screen

        final List<ScreenItem> list = new ArrayList<>();
        list.add( new ScreenItem( "Fresh food", "An essay is nothing but a piece of content which is written from the perception of writer or author. Essays are similar to a story, pamphlet, thesis, etc. The best thing about Essay is you can use any type of language – formal or informal.", R.drawable.holic ) );
        list.add( new ScreenItem( "Fresh Drink", "An essay is nothing but a piece of content which is written from the perception of writer or author. Essays are similar to a story, pamphlet, thesis, etc. The best thing about Essay is you can use any type of language – formal or informal.", R.drawable.imgg ) );
        list.add( new ScreenItem( "Thank You", "An essay is nothing but a piece of content which is written from the perception of writer or author. Essays are similar to a story, pamphlet, thesis, etc. The best thing about Essay is you can use any type of language – formal or informal.", R.drawable.design ) );

        //setting up viewPager
        screenPager = findViewById( R.id.screen_viewPager );
        introViewPagerAdapter = new IntroViewPagerAdapter( this, list );
        screenPager.setAdapter( introViewPagerAdapter );


        //Setting up tab Layout
        tabIndicator.setupWithViewPager( screenPager );

        nextBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < list.size()) {

                    position++;
                    screenPager.setCurrentItem( position );
                }

                if (position == list.size() - 1) {
                    loadLastScreen();
                }

            }
        } );

        tabIndicator.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == list.size() - 1) {
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );

        getStarted.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getApplicationContext(), MainActivity.class );
                startActivity( intent );
                finish();
                savePreference();
            }
        } );

    }

    private boolean restoredPrefData() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences( "myPref" , MODE_PRIVATE );
        return sharedPreferences.getBoolean( "isIntroOpened"  , false);
    }

    private void savePreference() {

        SharedPreferences sharedPreferences =  getApplicationContext().getSharedPreferences( "myPref" , MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean( "isIntroOpened" , true );
        editor.apply();
    }

    private void loadLastScreen() {
        nextBtn.setVisibility( View.INVISIBLE );
        tabIndicator.setVisibility( View.INVISIBLE );
        // screenPager.beginFakeDrag();
        getStarted.setAnimation( btnAnimation );
        getStarted.setVisibility( View.VISIBLE );
    }
}