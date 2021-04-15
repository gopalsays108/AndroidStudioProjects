package com.example.gopal.chemistryhub.ui.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import static com.example.gopal.chemistryhub.ui.register.RegisterActivity.onResetPasswordFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount;
    private FrameLayout parentFrameLayout; //variable for parent frame to acces to start
    private TextView forgotPassword;

    private TextView emails;
    private TextView password;

    private ProgressBar progressBar;


    private Button signInBtn;
    private FirebaseAuth firebaseAuth;
    private String emailPatterns = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.tv_dont_have_an_accoutn);  //we are in currently in fragment so we cannot use r.id directly we need to use as follows
        parentFrameLayout = getActivity().findViewById(R.id.register_frameLayout ); //we accesed parent frame layout from register frame layout

        emails = view.findViewById(R.id.sign_in_email);
        password =view.findViewById(R.id.sign_in_password);
        signInBtn = view.findViewById(R.id.signIn_btn);
        progressBar = view.findViewById(R.id.signIn_progressBar);
        forgotPassword = view.findViewById(R.id.sign_in_forgot_password);
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    //this method is created after the view is created and i used explcitly
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emails.getText().toString();
                String pass = password.getText().toString();
                if (email.isEmpty()){
                    emails.setError("E-mail Cannot Be Empty");
                    emails.requestFocus();
                }else if(pass.isEmpty()){
                    password.setError("Enter Password");
                    password.requestFocus();
                }else if(!(pass.length() >=8)){
                      password.setError("Password Length Is Too Small");
                      password.requestFocus();
                } else if(!(email.isEmpty()) && !(pass.isEmpty())){
                    if (emails.getText().toString().matches(emailPatterns)){

                        progressBar.setVisibility(View.VISIBLE);
                        signInBtn.setEnabled(false);
                        signInBtn.setTextColor(Color.argb(50 , 255 , 255 , 255));

                        firebaseAuth.signInWithEmailAndPassword(email , pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @SuppressLint("ResourceAsColor")
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Signed In Successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity() , MainActivity2.class));
                                    getActivity().finish();
                                }else{
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signInBtn.setEnabled(true);
                                    signInBtn.setTextColor(R.color.colorAccent);

                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(getActivity(), "E-mail Format Is Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private  void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction(); //we accesed getActivitt first bcz we are in fragment now
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right , R.anim.slide_out_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId() , fragment);
        fragmentTransaction.commit();
    }

}
