package com.example.gopal.chemistryhub.ui.register;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gopal.chemistryhub.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    private EditText registeredEmail;
    private Button resetPasswordBtn;
    private TextView goBack;
    private String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private FrameLayout parentFrameLayout;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View  view =  inflater.inflate(R.layout.fragment_reset_password, container, false);
       registeredEmail =view.findViewById(R.id.forgotPasswordEmail);
       resetPasswordBtn = view.findViewById(R.id.resetPasswordButton);
       goBack = view.findViewById(R.id.forgotPasswordGoBackTV);
       progressBar = view.findViewById(R.id.forgotPasswordProgressBar);
       parentFrameLayout = getActivity().findViewById(R.id.register_frameLayout);
       firebaseAuth = FirebaseAuth.getInstance();
       return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registeredEmail.getText().toString();
                if(email.isEmpty()){
                    registeredEmail.setError("Enter Registered E-mail");
                    registeredEmail.requestFocus();
                }else if(!(email.isEmpty())){
                    if (registeredEmail.getText().toString().matches(emailPatterns)){

                        resetPasswordBtn.setEnabled(false);
                        resetPasswordBtn.setTextColor(Color.argb(50 , 250 , 250 ,250));

                        progressBar.setVisibility(View.VISIBLE);
                        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getActivity(), "Email sent \n Successfully", Toast.LENGTH_SHORT).show();

                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();

                                }
                                resetPasswordBtn.setEnabled(true);
                                resetPasswordBtn.setTextColor(R.color.colorAccent);
                            }
                        });

                    }else{
                        Toast.makeText(getActivity(), "Email Format is Incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    private  void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right , R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId() , fragment);
        fragmentTransaction.commit();
    }


}
