package com.example.gopal.chemistryhub.ui.ui.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gopal.chemistryhub.R;

public class Quiz extends Fragment {

    private QuizViewModel mViewModel;
    Button grade11Option;
    Button grade12Option;


    public static Quiz newInstance() {
        return new Quiz();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quiz_fragment, container, false);
        grade11Option = view.findViewById(R.id.quiz_start_button_11);
        grade12Option = view.findViewById(R.id.quiz_start_button_12);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        // TODO: Use the ViewModel
        grade11Option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , Quiz11.class));
            }
        });

        grade12Option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity() , Quiz12.class));
            }
        });

    }

}
