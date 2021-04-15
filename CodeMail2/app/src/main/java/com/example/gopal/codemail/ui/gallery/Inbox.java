package com.example.gopal.codemail.ui.gallery;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gopal.codemail.R;
import com.example.gopal.codemail.ui.Main2Activity;

public class Inbox extends Fragment {

    private InboxViewModel mViewModel;

    public static Inbox newInstance() {
        return new Inbox();
    }

    Button btn;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.inbox_fragment, container, false);
        btn = view.findViewById(R.id.button2);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InboxViewModel.class);
        // TODO: Use the ViewModel

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               startActivity(new Intent(getActivity() , Main2Activity.class));
            }

        });
    }

}
