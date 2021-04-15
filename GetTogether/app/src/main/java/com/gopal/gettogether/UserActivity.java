package com.gopal.gettogether;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gopal.gettogether.GetTogether.makeOnline;

public class UserActivity extends AppCompatActivity {

    private RecyclerView userList;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseAuth;
    private String currentUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user );
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseAuth != null)
            currentUid = firebaseAuth.getUid();

        Toolbar toolbar = findViewById( R.id.userToolbar );
        setSupportActionBar( toolbar );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle( "All users" );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child( "Users" );
        userList = findViewById( R.id.userListRecyclerView );
        userList.setHasFixedSize( true );
        userList.setLayoutManager( new LinearLayoutManager( this ) );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected( item );
    }

    //since we need data real time
    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseAuth != null) {
            makeOnline();
        }

        FirebaseRecyclerAdapter<UsersModel, userViewHolder> firebaseRecyclerAdapter
                = new FirebaseRecyclerAdapter<UsersModel, userViewHolder>(
                UsersModel.class, R.layout.users_single_layout,
                userViewHolder.class, databaseReference
        ) {
            @Override
            protected void populateViewHolder(final userViewHolder userViewHolder, final UsersModel usersModel, int i) {

                final String userID = getRef( i ).getKey();

                userViewHolder.setName( usersModel.getName() );
                userViewHolder.setStatus( usersModel.getStatus() );
                userViewHolder.setImage( usersModel.getImage() );
//                userViewHolder.profileIntent( usersModel.getName() ,usersModel.getStatus() , usersModel.getImage()
//                                                , userID);
                userViewHolder.isFriendRequest( userID, currentUid );

                userViewHolder.userName.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        userViewHolder.profileIntent( usersModel.getName(), usersModel.getStatus(),
                                usersModel.getImage(), userID );
                    }
                } );
            }
        };

        userList.setAdapter( firebaseRecyclerAdapter );
    }

    //inner class so used static
    public static class userViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView userName;
        DatabaseReference databaseReference;
        DatabaseReference seenRequestDatabase;

        public userViewHolder(@NonNull View itemView) {
            super( itemView );
            view = itemView;
            databaseReference = FirebaseDatabase.getInstance().getReference().child( "FriendReq" );
            seenRequestDatabase = FirebaseDatabase.getInstance().getReference().child( "RequestSeen" );
        }

        public void setName(String name) {
            userName = view.findViewById( R.id.userSingleName );
            userName.setText( name );
        }

        void setStatus(String status) {
            TextView userStatus = view.findViewById( R.id.userSingleStatus );
            userStatus.setText( status );
        }

        void setImage(String image) {
            if (image != null) {
                CircleImageView userImage = view.findViewById( R.id.userSingleImage );
                if (image.equals( "default" ) || image.equals( "" ) || image.length() < 10) {
                    userImage.setImageResource( R.drawable.download );
                } else {
                    Glide.with( view.getContext() ).load( image ).placeholder( R.drawable.download ).into( userImage );
                }
            }
        }

        void profileIntent(String name, String status, String image, String key) {

            Intent profileIntent = new Intent( view.getContext(), ProfileActivity.class );
            profileIntent.putExtra( "name", name );
            profileIntent.putExtra( "status", status );
            profileIntent.putExtra( "image", image );
            profileIntent.putExtra( "user_id", key );

            view.getContext().startActivity( profileIntent );
        }

        void isFriendRequest(final String key, final String currentUid) {

            final ImageView newRequest = view.findViewById( R.id.newFriendRequestUserSingle );

            seenRequestDatabase.child( key ).child( currentUid ).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String requestType = snapshot.child( "seenType" ).getValue().toString();

                        if (requestType.equals( "no" )) {

                            databaseReference.child( key ).addValueEventListener( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    if (snapshot.hasChild( currentUid )) {
                                        newRequest.setVisibility( View.VISIBLE );
                                    } else {
                                        newRequest.setVisibility( View.INVISIBLE );
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            } );

                        } else {
                            newRequest.setVisibility( View.INVISIBLE );
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );

        }
    }
}
