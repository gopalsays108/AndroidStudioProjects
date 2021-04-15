package com.gopal.gettogether.tabFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.gopal.gettogether.ChatActivity;
import com.gopal.gettogether.ProfileActivity;
import com.gopal.gettogether.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    private RecyclerView friendList;
    private FirebaseUser firebaseAuth;
    private DatabaseReference friendDatabase;
    private DatabaseReference userDatabase;
    private String currentUserId;
    private View mainView;
    private String userName;
    private Vibrator vibrator ;

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mainView = inflater.inflate( R.layout.fragment_friends, container, false );

        friendList = mainView.findViewById( R.id.friendListRecyclerView );
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseAuth != null)
            currentUserId = firebaseAuth.getUid();

        friendDatabase = FirebaseDatabase.getInstance().getReference( "Friends" ).child( currentUserId );
        userDatabase = FirebaseDatabase.getInstance().getReference().child( "Users" );
        userDatabase.keepSynced( true );
        friendDatabase.keepSynced( true );

        friendList.setHasFixedSize( true );
        friendList.setLayoutManager( new LinearLayoutManager( getContext() ) );
        vibrator = (Vibrator) mainView.getContext().getSystemService( Context.VIBRATOR_SERVICE );
        // Inflate the layout for this fragment
        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<FriendsModel, FriendsViewHolder> friendsRecyclerView = new FirebaseRecyclerAdapter<FriendsModel, FriendsViewHolder>(
                FriendsModel.class,
                R.layout.users_single_layout,
                FriendsViewHolder.class,
                friendDatabase
        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder friendsViewHolder, final FriendsModel friendsModel, int i) {

                final String listUserId = getRef( i ).getKey();
                // final String[] userName = new String[1];

                friendsViewHolder.setDate( friendsModel.getDate() );

                if (listUserId != null)
                userDatabase.child( listUserId ).addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {
                            userName = (String) snapshot.child( "name" ).getValue();
                            final String userImage = (String) snapshot.child( "image" ).getValue();

                            if (snapshot.hasChild( "online" )) {
                                String userOnline = (String) snapshot.child( "online" ).getValue().toString();
                                if (userOnline != null)
                                    friendsViewHolder.setUserOnline( userOnline );
                            }
                            friendsViewHolder.setName( userName );
                            friendsViewHolder.setImage( userImage );

                            friendsViewHolder.view.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    vibrator.vibrate( 50 );
                                        //alert dialogue
                                    CharSequence[] option = new CharSequence[]{"Open Profile", "Send message"};

                                    AlertDialog.Builder builder = new AlertDialog.Builder( getContext() );

                                    builder.setTitle( "Select Option" );
                                    builder.setItems( option, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // click listner for each item
                                            if (which == 0) {
                                                Intent profileActivity = new Intent( getContext(), ProfileActivity.class );
                                                profileActivity.putExtra( "user_id", listUserId );
                                                startActivity( profileActivity );
                                            } else if (which == 1) {
//                                                Toast.makeText( getContext(), "hey", Toast.LENGTH_SHORT ).show();
                                                Intent chatIntent = new Intent( getContext(), ChatActivity.class );
                                                chatIntent.putExtra( "user_id", listUserId );
                                                chatIntent.putExtra( "userName", userName );
                                                chatIntent.putExtra( "userImage" , userImage );
                                                startActivity( chatIntent );
                                            }
                                        }
                                    } );

                                    builder.show();
                                }
                            } );

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                } );
            }
        };

        friendList.setAdapter( friendsRecyclerView );
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        View view;

        public FriendsViewHolder(View itemView) {
            super( itemView );
            view = itemView;
        }

        public void setDate(String date) {

            TextView userDate = view.findViewById( R.id.userSingleStatus );

            userDate.setText( "Friendship since " + date );
        }

        public void setName(String name) {

            TextView userName = view.findViewById( R.id.userSingleName );
            userName.setTextSize( 18 );
            userName.setText( name );
        }

        public void setImage(String image) {

            CircleImageView userImage = view.findViewById( R.id.userSingleImage );
            Glide.with( view.getContext() ).load( image ).placeholder( R.drawable.download ).into( userImage );

        }

        public void setUserOnline(String online) {

            ImageView userOnlineIcon = view.findViewById( R.id.userSingleOnlineIcon );
            if (online.equals( "true" )) {
                userOnlineIcon.setVisibility( View.VISIBLE );
            } else {
                userOnlineIcon.setVisibility( View.INVISIBLE );
            }
        }
    }
}
