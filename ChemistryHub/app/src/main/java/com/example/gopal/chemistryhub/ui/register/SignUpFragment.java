package com.example.gopal.chemistryhub.ui.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gopal.chemistryhub.R;
import com.example.gopal.chemistryhub.ui.MainActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAnAccoutn;
    private FrameLayout parentframeLayout;

    // ALl variables in Signup
    private EditText email;
    private EditText fullName;
    private EditText password;
    private EditText confirmPassword;

    private Button signUpBtn;
    private ProgressBar progressBar;

    private FirebaseAuth fireBaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        //we accessed view first bcz we are in fragment now
        alreadyHaveAnAccoutn = view.findViewById(R.id.tv_have_an_account);

        parentframeLayout = getActivity().findViewById(R.id.register_frameLayout);

        email = view.findViewById(R.id.SignUp_email);
        fullName = view.findViewById(R.id.SignUP_full_name);
        password = view.findViewById(R.id.SignUp_password);
        confirmPassword = view.findViewById(R.id.SignUp_confirm_password);

        signUpBtn = view.findViewById(R.id.SignUp_btn);

        progressBar = view.findViewById(R.id.signup_progressBar);

        fireBaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccoutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        // Defination on click on sign up button
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailID = email.getText().toString();
                 final String fullNames = fullName.getText().toString();
                String passwords = password.getText().toString();
                String confirmPass = confirmPassword.getText().toString();
                //fireBaseAuth = FirebaseAuth.getInstance();

                if (emailID.isEmpty()) {
                    email.setError("E-mail Cannot be Empty");
                    email.requestFocus();
                } else if (fullNames.isEmpty()) {
                    fullName.setError("Enter Full Name");
                    fullName.requestFocus();
                } else if (passwords.isEmpty()) {
                    password.setError("Enter Password");
                    password.requestFocus();
                } else if (!(passwords.length() >= 8)) {
                    password.setError("Password Too Short");
                    password.requestFocus();
                } else if (confirmPass.isEmpty()) {
                    confirmPassword.setError("Write Password For Confirmation");
                    confirmPassword.requestFocus();
                } else if (!emailID.isEmpty() && !passwords.isEmpty()) {
                    if (email.getText().toString().matches(emailPattern)) {
                        if (passwords.equals(confirmPass)) {

                            progressBar.setVisibility(View.VISIBLE);
                            signUpBtn.setEnabled(false);
                            signUpBtn.setTextColor(Color.argb(50, 225, 225, 225));

                            //todo : send data to firebase
                            fireBaseAuth.createUserWithEmailAndPassword(emailID, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @SuppressLint("ResourceAsColor")
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Map<Object, String> userdata = new HashMap<>();
                                        userdata.put("fullName", fullNames);
                                        //userdata.put("middleName" , " Kumar"); i can put as many as data i want justt by adding userdata.put

                                        firebaseFirestore.collection("USERS").add(userdata).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getActivity(), "Sign Up Successfull \nLogged IN", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getContext(), MainActivity2.class);
                                                    startActivity(intent);
                                                    getActivity().finish();

                                                } else {
                                                    signUpBtn.setEnabled(true);
                                                    signUpBtn.setTextColor(R.color.colorAccent);
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    String Error = task.getException().getMessage();
                                                    Toast.makeText(getActivity(), Error, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        signUpBtn.setEnabled(true);
                                        signUpBtn.setTextColor(R.color.colorAccent);
                                        progressBar.setVisibility(View.INVISIBLE);
                                        String Error = task.getException().getMessage();
                                        Toast.makeText(getActivity(), Error, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            confirmPassword.setError("Password Doesn't Match");
                            confirmPassword.requestFocus();
                        }

                    } else {
                        email.setError("E-mail Format Is Wrong");
                        email.requestFocus();
                    }

                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slide_out_right);
        fragmentTransaction.replace(parentframeLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

}

