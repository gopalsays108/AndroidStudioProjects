package com.gopal.gettogether.tabFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gopal.gettogether.ChatActivity;
import com.gopal.gettogether.R;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    private RecyclerView conversationList;
    private DatabaseReference convDatabase;
    private DatabaseReference messageDatabase;
    private DatabaseReference userDatabase;
    private DatabaseReference rootRef;

    private FirebaseUser firebaseAuth;


    private String currentUserId;

    private View mainView;

    public ChatsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate( R.layout.fragment_chats, container, false );

        conversationList = mainView.findViewById( R.id.friendsConversationRecyclerView );
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseAuth != null)
            currentUserId = firebaseAuth.getUid();

        convDatabase = FirebaseDatabase.getInstance().getReference().child( "Chat" ).child( currentUserId );
        messageDatabase = FirebaseDatabase.getInstance().getReference().child( "messages" ).child( currentUserId );
        userDatabase = FirebaseDatabase.getInstance().getReference().child( "Users" );
        rootRef = FirebaseDatabase.getInstance().getReference();
        convDatabase.keepSynced( true );
        messageDatabase.keepSynced( true );
        userDatabase.keepSynced( true );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext() );
        linearLayoutManager.setReverseLayout( true );
        linearLayoutManager.setStackFromEnd( true );

        conversationList.setHasFixedSize( true );
        conversationList.setLayoutManager( linearLayoutManager );

        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Query conversationQuery = convDatabase.orderByChild( "timestamp" );

        FirebaseRecyclerAdapter<ConversationModel, ConvViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ConversationModel, ConvViewHolder>(

                        ConversationModel.class,
                        R.layout.users_single_layout,
                        ConvViewHolder.class,
                        conversationQuery
                ) {
                    @Override
                    protected void populateViewHolder(final ConvViewHolder convViewHolder, final ConversationModel conversationModel, int i) {

                        final String listUserId = getRef( i ).getKey();

                        if (listUserId != null) {  // added now to remove warning
                            Query lastMessageQuery = messageDatabase.child( listUserId ).limitToLast( 1 );

                            lastMessageQuery.addChildEventListener( new ChildEventListener() {
                                @Override
                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                    String data = snapshot.child( "message" ).getValue().toString();
                                    String type = snapshot.child( "type" ).getValue().toString();

                                    convViewHolder.setMessage( data, conversationModel.isSeen(), type );
                                }

                                @Override
                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                }

                                @Override
                                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                                }

                                @Override
                                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            } );


                            userDatabase.child( listUserId ).addValueEventListener( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    final String userName = snapshot.child( "name" ).getValue().toString();
                                    final String userImage = snapshot.child( "image" ).getValue().toString();


                                    if (snapshot.hasChild( "online" )) {

                                        String userOnline = snapshot.child( "online" ).getValue().toString();
                                        convViewHolder.setUserOnline( userOnline );
                                    }

                                    convViewHolder.setName( userName );
                                    convViewHolder.setUserImage( userImage, getContext() );

                                    convViewHolder.view.setOnClickListener( new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent chatIntent = new Intent( getContext(), ChatActivity.class );
                                            chatIntent.putExtra( "user_id", listUserId );
                                            chatIntent.putExtra( "userName", userName );
                                            chatIntent.putExtra( "userImage", userImage );
                                            startActivity( chatIntent );
                                        }
                                    } );

                                    convViewHolder.view.setOnLongClickListener( new View.OnLongClickListener() {
                                        @Override
                                        public boolean onLongClick(View v) {
                                            Log.i( "CHECKING : ", listUserId );

                                            new AlertDialog.Builder( mainView.getContext() ).setTitle( "Delete ?" ).
                                                    setMessage( "Are you sure you want to delete this chat" ).setPositiveButton( "YES", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

//                                                    Toast.makeText( mainView.getContext(), "Deleted", Toast.LENGTH_SHORT ).show();
//                                                    Map<String, Object> deleteChat = new HashMap<>();
//                                                    deleteChat.put( "Chat" + "/" + currentUserId + "/" + listUserId + "/" + "seen", null );
//                                                    deleteChat.put( "Chat" + "/" + currentUserId + "/" + listUserId + "/" + "timeStamp", null );

                                                    rootRef.child( "Chat" ).child( currentUserId ).child( listUserId ).removeValue().addOnCompleteListener( new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText( mainView.getContext(), "Deleted", Toast.LENGTH_SHORT ).show();
                                                        }
                                                    } );

//                                                    rootRef.updateChildren( deleteChat, new DatabaseReference.CompletionListener() {
//                                                        @Override
//                                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//
//                                                        }
//                                                    } );

                                                }
                                            } ).setNegativeButton( "No", null ).setIcon( android.R.drawable.ic_dialog_alert ).show();

                                            return true;
                                        }
                                    } );
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            } );
                        } else {
                            Toast.makeText( getContext(), "Something Went wrong", Toast.LENGTH_SHORT ).show();
                        }
                    }
                };
        conversationList.setAdapter( firebaseRecyclerAdapter );
    }

    public static class ConvViewHolder extends RecyclerView.ViewHolder {

        View view;

        public ConvViewHolder(@NonNull View itemView) {
            super( itemView );

            view = itemView;
        }

        public void setMessage(String message, boolean isSeen, String type) {

            TextView userStatusView = view.findViewById( R.id.userSingleStatus );

            if (type.equals( "text" )) {
                userStatusView.setText( message );
            } else {
                userStatusView.setText( "Photo" );
            }

            if (!isSeen) {
                userStatusView.setTypeface( userStatusView.getTypeface(), Typeface.BOLD );
            } else {
                userStatusView.setTypeface( userStatusView.getTypeface(), Typeface.NORMAL );
            }
        }

        public void setName(String name) {
            TextView userNameView = view.findViewById( R.id.userSingleName );
            userNameView.setText( name );

        }

        public void setUserImage(String image, Context context) {

            CircleImageView userImage = view.findViewById( R.id.userSingleImage );
            Glide.with( context ).load( image ).placeholder( R.drawable.download ).into( userImage );
        }

        public void setUserOnline(String onlineStatus) {

            ImageView userOnlineImage = view.findViewById( R.id.userSingleOnlineIcon );

            if (onlineStatus.equals( "true" )) {
                userOnlineImage.setVisibility( View.VISIBLE );
            } else {
                userOnlineImage.setVisibility( View.INVISIBLE );
            }
        }
    }
}
