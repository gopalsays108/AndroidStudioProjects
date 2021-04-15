package com.gopal.gettogether;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.gopal.gettogether.register.StartActivity;

import static com.gopal.gettogether.GetTogether.makeOnline;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    //    FirebaseUser currentUser;
    private FirebaseUser getCurrentUser;
    private String uid;
    private DatabaseReference userRef;
    private SectionPagerAdapter sectionPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        firebaseAuth = FirebaseAuth.getInstance();
        getCurrentUser = firebaseAuth.getCurrentUser();

        if (getCurrentUser != null) {
            uid = getCurrentUser.getUid();

            Toolbar toolbar = findViewById( R.id.main_page_toolbar );
            setSupportActionBar( toolbar );
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle( "Get Together" );
            }

            userRef = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( uid );

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( uid );

            //tabs
            viewPager = findViewById( R.id.mainPager );

            sectionPagerAdapter = new SectionPagerAdapter( getSupportFragmentManager(), 0 );
            viewPager.setAdapter( sectionPagerAdapter );

            //----- To get to specific page
            viewPager.setCurrentItem( sectionPagerAdapter.getCount() - 2 );
//        String sds = (String) sectionPagerAdapter.getPageTitle( 0 );
//        Log.i( "NAME" , sds );

            TabLayout tabLayout = findViewById( R.id.mainTab );
            tabLayout.setupWithViewPager( viewPager );

//        tabLayout.getTabAt( 0 ).setIcon( R.drawable.add );

        }
    }

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {

            if (viewPager.getCurrentItem() == 0 && viewPager != null) {
                viewPager.setCurrentItem( sectionPagerAdapter.getCount() - 2 );
            } else {
                if (viewPager != null)
                    if (viewPager.getCurrentItem() == 2 && viewPager != null) {
                        viewPager.setCurrentItem( sectionPagerAdapter.getCount() - 2 );
                    } else {
                        finish();
                    }
            }
        }
        mBackPressed = System.currentTimeMillis();
    }

    @Override
    protected void onStart() {
        super.onStart();

        makeOnline();
        // Check if user is signed in (non-null) and update UI accordingly.

        if (getCurrentUser == null) {
            sendToStart();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (getCurrentUser != null) {
            super.onStop();
            userRef.child( "online" ).setValue( ServerValue.TIMESTAMP );
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logOut) {
            firebaseAuth.signOut();
            sendToStart();
        } else if (item.getItemId() == R.id.accountSetting) {
            startActivity( new Intent( getApplicationContext(), SettingActivity.class ) );
        } else if (item.getItemId() == R.id.allUsersList) {
            startActivity( new Intent( getApplicationContext(), UserActivity.class ) );
        }
        return super.onOptionsItemSelected( item );
    }

    public void sendToStart() {
        Intent startIntent = new Intent( getApplicationContext(), StartActivity.class );
        startActivity( startIntent );
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }
}
