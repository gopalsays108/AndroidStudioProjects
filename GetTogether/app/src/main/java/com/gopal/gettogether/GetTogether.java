package com.gopal.gettogether;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class GetTogether extends Application {

    private DatabaseReference userDatabase;
    private FirebaseUser firebaseAuth;
    String currentUserId;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled( true );

        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseAuth != null) {
            currentUserId = firebaseAuth.getUid();

            userDatabase = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserId );


            makeOnline();

            userDatabase.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    userDatabase.child( "online" ).onDisconnect().setValue( ServerValue.TIMESTAMP );  //
//                userDatabase.child( "lastSeen" ).setValue( ServerValue.TIMESTAMP );
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }
    }

    public static void makeOnline (){
        FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseAuth != null) {
            String currentUserId = firebaseAuth.getUid();
            DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( currentUserId );
            userDatabase.child( "online" ).setValue( "true" );

            Log.i("ONLINE" , "YES");
        }
    }
}
