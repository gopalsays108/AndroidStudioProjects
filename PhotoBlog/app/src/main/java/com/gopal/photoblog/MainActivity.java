package com.gopal.photoblog;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.gopal.register.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore firebaseFirestore;
    private FloatingActionButton addPostBtn;
    private String currentUserUid;
    private BottomNavigationView mainBottomNav;

    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Toolbar toolbar = findViewById( R.id.mainToolbar );
        setSupportActionBar( toolbar );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( R.string.app_name );
        }

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            currentUserUid = currentUser.getUid();

            addPostBtn = findViewById( R.id.floatingActionButtonAddPost );
            firebaseFirestore = FirebaseFirestore.getInstance();
            mainBottomNav = findViewById( R.id.mainBottomNav );

            //Fragments
            homeFragment = new HomeFragment();
            accountFragment = new AccountFragment();
            notificationFragment = new NotificationFragment();

            replaceFragment( homeFragment ); // home fragment load first whenever main activity loaded

            mainBottomNav.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {

                        case R.id.bottomActionHome:
                            replaceFragment( homeFragment );
                            return true;

                        case R.id.bottomActionAccount:
                            replaceFragment( accountFragment );
                            return true;

                        case R.id.bottomActionNotification:
                            replaceFragment( notificationFragment );
                            return true;

                        default:
                            return false;
                    }
                }
            } );

            addPostBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity( new Intent( getApplicationContext(), NewPostActivity.class ) );
                }
            } );
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser == null) {
            loginIntent();
        } else {
            firebaseFirestore.collection( "Users" ).document( currentUserUid ).get()
                    .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if (task.isSuccessful()) {
                                if (task.getResult() != null) {
                                    if (!task.getResult().exists()) {
                                        Intent setUpIntent = new Intent( getApplicationContext(), SetUpActivity.class );
                                        startActivity( setUpIntent );
                                        finish();
                                    }
                                }
                            } else {
                                if (task.getException() != null) {
                                    Exception exception = task.getException();
                                    Toast.makeText( getApplicationContext(), exception.toString(), Toast.LENGTH_LONG ).show();
                                }
                            }
                        }
                    } );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.app_bar_search) {

        } else if (item.getItemId() == R.id.accountSettingMenu) {
            startActivity( new Intent( getApplicationContext(), SetUpActivity.class ) );
        } else if (item.getItemId() == R.id.logOut) {
            firebaseAuth.signOut();
            loginIntent();
        }
        return super.onOptionsItemSelected( item );
    }

    public void loginIntent() {
        Intent loginIntent = new Intent( getApplicationContext(), LoginActivity.class );
        startActivity( loginIntent );
        finish();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.mainContainer, fragment );
        fragmentTransaction.commit();
    }
}