package com.gopal.gettogether;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gopal.gettogether.GetTogether.makeOnline;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView profileImage;
    private TextView userName;
    private TextView userStatus;
    private TextView totalFriend;

    private Button sendRequestBtn;
    private Button declineBtn;

    private String currentState;
    private String key;

    private DatabaseReference firendRequestDatabase;
    private DatabaseReference friendDatabase;
    private DatabaseReference seenTypeDatabase;
    private DatabaseReference notificationDatabase;
    private DatabaseReference userProfileDatabase;
    private DatabaseReference rootRef;
    private FirebaseUser firebaseAuth;
    String currentUserUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );

        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseAuth != null)
            currentUserUid = firebaseAuth.getUid();

        //-------- Intent Value-------
//        name = getIntent().getStringExtra( "name" );
//        status = getIntent().getStringExtra( "status" );
//        image = getIntent().getStringExtra( "image" );
        key = getIntent().getStringExtra( "user_id" );

        profileImage = findViewById( R.id.profileCircleImage );
        userName = findViewById( R.id.profileUserName );
        userStatus = findViewById( R.id.profileUserStatus );
        totalFriend = findViewById( R.id.profileTotalFriend );
        sendRequestBtn = findViewById( R.id.profileSendRequest );
        declineBtn = findViewById( R.id.profileDeclineBtn );

        notificationDatabase = FirebaseDatabase.getInstance().getReference( "Notification" );
        seenTypeDatabase = FirebaseDatabase.getInstance().getReference( "RequestSeen" );
        firendRequestDatabase = FirebaseDatabase.getInstance().getReference( "FriendReq" );
        rootRef = FirebaseDatabase.getInstance().getReference();
        friendDatabase = FirebaseDatabase.getInstance().getReference().child( "Friends" );
        userProfileDatabase = FirebaseDatabase.getInstance().getReference( "Users" );

        // To load profile of selected user from all users.
        userProfileDatabase.child( key ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String userNameE = (String) snapshot.child( "name" ).getValue();
                    String userStatusS = (String) snapshot.child( "status" ).getValue();
                    String userImage = (String) snapshot.child( "image" ).getValue();

                    //------setting default value-------
                    userName.setText( userNameE );
                    userStatus.setText( userStatusS );

                    if (userImage != null)
                        if (userImage.equals( "default" ) || userImage.equals( "" ) || userImage.length() < 10) {
                            profileImage.setImageResource( R.drawable.download );
                        } else {
                            Glide.with( getApplicationContext() ).load( userImage ).placeholder( R.drawable.download ).into( profileImage );
                        }

                } else {
                    Toast.makeText( ProfileActivity.this, "Load Again", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText( ProfileActivity.this, error.toString(), Toast.LENGTH_SHORT ).show();
            }
        } );


        currentState = "not_friends";
        declineBtn.setVisibility( View.INVISIBLE );
        declineBtn.setEnabled( false );

        //        Log.i( "DATA" , name + "  " + status + "  " + image +"  " + key  );

        //------------- FRIEND LIST / REQUEST   FEATURE-----------------
        firendRequestDatabase.child( currentUserUid ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild( key )) {

                    String req_type = snapshot.child( key ).child( "requestType" ).getValue().toString();

                    if (req_type.equals( "received" )) {

                        currentState = "req_received";
                        sendRequestBtn.setText( "Accept Friend Request" );
                        declineBtn.setEnabled( true );
                        declineBtn.setVisibility( View.VISIBLE );

                    } else if (req_type.equals( "sent" )) {

                        currentState = "req_sent";
                        sendRequestBtn.setText( "Cancel Friend Request" );
                        declineBtn.setVisibility( View.INVISIBLE );
                        declineBtn.setEnabled( false );
                    }
                } else {

                    friendDatabase.child( currentUserUid ).addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild( key )) {
                                currentState = "friends";
                                sendRequestBtn.setText( "Un-Friend" );

                                declineBtn.setVisibility( View.INVISIBLE );
                                declineBtn.setEnabled( false );

                            } else {
                                currentState = "not_friends";
                                sendRequestBtn.setText( "Send Friend Request" );

                                declineBtn.setVisibility( View.INVISIBLE );
                                declineBtn.setEnabled( false );
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText( ProfileActivity.this, "Try Again", Toast.LENGTH_SHORT ).show();
                        }
                    } );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );

        sendRequestBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendRequestBtn.setEnabled( false );

                //---------- NOT FRIEND STATE--------------
                if (currentState.equals( "not_friends" )) {

                    DatabaseReference newNotificationRef = rootRef.child( "Notification" ).child( key ).push();
                    String newNotificationId = newNotificationRef.getKey();


//                    DatabaseReference seenDatabase = rootRef.child( "RequestSeen" ).child( key ).push();
//                    String seenDataBaseId = seenDatabase.getKey();

                    HashMap<String, String> notificationData = new HashMap<>();
                    notificationData.put( "from", currentUserUid );
                    notificationData.put( "type", "Request" );

                    Map<String , Object> requestMap = new HashMap<>();
                    requestMap.put( "FriendReq/" + currentUserUid + "/" + key + "/" + "requestType", "sent" );
                    requestMap.put( "FriendReq/" + key + "/" + currentUserUid + "/" + "requestType", "received" );
                    requestMap.put( "Requests/"  + key + "/" + currentUserUid  + "/" + "requestType", "received" );
                    requestMap.put( "RequestSeen/" + currentUserUid + "/" + key + "/" + "seenType", "no" );
                    requestMap.put( "Notification/" + key + "/" + newNotificationId, notificationData );

                    rootRef.updateChildren( requestMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            if (error == null) {

                                sendRequestBtn.setEnabled( true );
                                currentState = "req_sent";
                                sendRequestBtn.setText( "Cancel Friend Request" );

                                Toast.makeText( ProfileActivity.this, "Sent", Toast.LENGTH_SHORT ).show();

                                declineBtn.setVisibility( View.INVISIBLE );
                                declineBtn.setEnabled( false );
                            } else {
                                Toast.makeText( ProfileActivity.this, " Try Again", Toast.LENGTH_SHORT ).show();
                                sendRequestBtn.setEnabled( true );
                                Toast.makeText( ProfileActivity.this, "Failed Sending Request", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } );

                }

                //---------Cancel REQUEST STATE----------
                if (currentState.equals( "req_sent" )) {

                    Map<String , Object> cancelRequest = new HashMap<>();
                    cancelRequest.put( "FriendReq" + "/" + currentUserUid + "/" + key + "/" + "requestType", null );
                    cancelRequest.put( "FriendReq" + "/" + key + "/" + currentUserUid + "/" + "requestType", null );
                    cancelRequest.put( "Requests" + "/" + currentUserUid + "/" + key + "/" + "requestType", null );

                    cancelRequest.put( "RequestSeen" + "/" + currentUserUid + "/" + key + "/" + "seenType", null );

                    rootRef.updateChildren( cancelRequest, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            if (error == null) {

                                sendRequestBtn.setEnabled( true );
                                currentState = "not_friends";
                                sendRequestBtn.setText( "Send Friend Request" );

                                declineBtn.setVisibility( View.INVISIBLE );
                                declineBtn.setEnabled( false );


                            } else {
                                sendRequestBtn.setEnabled( true );
                                Toast.makeText( ProfileActivity.this, "Failed to Delete", Toast.LENGTH_SHORT ).show();
                            }

                        }
                    } );

                }

                //--------REQ RECIEVED STATE----------
                if (currentState.equals( "req_received" )) {

                    final String date = DateFormat.getDateInstance().format( new Date() );

                    Map<String , Object> friendReq = new HashMap<>();
                    friendReq.put( "Friends" + "/" + currentUserUid + "/" + key + "/date", date );
                    friendReq.put( "Friends" + "/" + key + "/" + currentUserUid + "/date", date );

                    friendReq.put( "FriendReq" + "/" + currentUserUid + "/" + key + "/" + "requestType", null );
                    friendReq.put( "FriendReq" + "/" + key + "/" + currentUserUid + "/" + "requestType", null );
                    friendReq.put( "RequestSeen" + "/" + currentUserUid + "/" + key + "/" + "seenType", null );

                    rootRef.updateChildren( friendReq, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            if (error == null) {

                                sendRequestBtn.setEnabled( true );
                                currentState = "friends";
                                sendRequestBtn.setText( "Un-Friend" );

                                declineBtn.setVisibility( View.INVISIBLE );
                                declineBtn.setEnabled( false );

                            } else {
                                String prob = error.getDetails();
                                Toast.makeText( ProfileActivity.this, prob, Toast.LENGTH_SHORT ).show();
                            }
                        }
                    } );
                }

                //--------------Un friend---------------

                if (currentState.equals( "friends" )) {

                    Map<String , Object> removeFriend = new HashMap<>();
                    removeFriend.put( "Friends" + "/" + currentUserUid + "/" + key, null );
                    removeFriend.put( "Friends" + "/" + key + "/" + currentUserUid, null );

                    //---------- Un friend removes chats and all
                    Log.i( "DATA1", removeFriend.toString() );
                    Log.i( "DATA", "123" );

                    rootRef.updateChildren( removeFriend, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            if (error == null) {

                                currentState = "not_friends";
                                sendRequestBtn.setEnabled( true );
                                sendRequestBtn.setText( "Send Friend Request" );

                                declineBtn.setVisibility( View.INVISIBLE );
                                declineBtn.setEnabled( false );

                            } else {

                                String prob = error.getDetails();
                                Toast.makeText( ProfileActivity.this, prob, Toast.LENGTH_SHORT ).show();
                            }

                        }
                    } );
                }
            }
        } );

        declineBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String , Object> declineRequest = new HashMap<>(  );

                declineRequest.put( "FriendReq" + "/" + currentUserUid + "/" + key + "/" + "requestType", null );
                declineRequest.put( "FriendReq" + "/" + key + "/" + currentUserUid + "/" + "requestType", null );

                declineRequest.put( "RequestSeen" + "/" + currentUserUid + "/" + key + "/" + "seenType", null );

                rootRef.updateChildren( declineRequest, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                        if (error == null){

                            sendRequestBtn.setEnabled( true );
                            currentState = "not_friends";
                            sendRequestBtn.setText( "Send Friend Request" );

                            declineBtn.setVisibility( View.INVISIBLE );
                            declineBtn.setEnabled( false );

                        }else{

                        }
                    }
                } );

            }
        } );


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth != null){
            makeOnline();
        }
    }
}
