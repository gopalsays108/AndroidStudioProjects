package com.example.gopal.chemistryhub.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gopal.chemistryhub.R;
import com.example.gopal.chemistryhub.ui.register.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LogOut extends Fragment {

    private FirebaseAuth firebaseAuth;


    private LogOutViewModel mViewModel;

    public static LogOut newInstance() {
        return new LogOut();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.log_out_fragment, container, false);

        firebaseAuth.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_SHORT).show();
      //  firebaseAuth.getInstance().signOut();
        signout();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LogOutViewModel.class);
        // TODO: Use the ViewModel
    }

    private boolean signout(){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(getActivity(),
                RegisterActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        return true;
    }



}
