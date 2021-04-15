package com.example.gopal.chemistryhub.ui.register;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gopal.chemistryhub.R;

public class RegisterActivity extends AppCompatActivity {

    private FrameLayout frameLayout; // variable for frame layout

    public static boolean onResetPasswordFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        frameLayout = findViewById(R.id.register_frameLayout);
        setFragment(new SignInFragment());


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
                if(onResetPasswordFragment){
                    onResetPasswordFragment = false;
                    setFragments(new SignInFragment());
                    return  false;
                }
        }
        return super.onKeyDown(keyCode, event);

    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId() , fragment);
        fragmentTransaction.commit();
    }

    private void setFragments(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction(); // no need to call get activity bcz we are in register activity
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_out_right);
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
