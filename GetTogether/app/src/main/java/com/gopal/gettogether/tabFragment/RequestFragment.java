package com.gopal.gettogether.tabFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.gopal.gettogether.R;
import com.gopal.gettogether.RequestModel;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    private RecyclerView requestRecyclerView;
    private DatabaseReference requestReference;
    private String currentUid;
    private View view;
    private DatabaseReference rootRef;
    private DatabaseReference userDatabase;

    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_request, container, false );

        requestRecyclerView = view.findViewById( R.id.friendsRequestRecyclerView );
        FirebaseUser firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseAuth != null)
            currentUid = firebaseAuth.getUid();

        rootRef = FirebaseDatabase.getInstance().getReference();
        requestReference = FirebaseDatabase.getInstance().getReference().child( "Requests" ).child( currentUid );
        userDatabase = FirebaseDatabase.getInstance().getReference().child( "Users" );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( getContext() );

        requestRecyclerView.setHasFixedSize( true );
        requestRecyclerView.setLayoutManager( linearLayoutManager );

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<RequestModel, requestViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<RequestModel, requestViewHolder>(
                        RequestModel.class,
                        R.layout.request_layout,
                        requestViewHolder.class,
                        requestReference
                ) {
                    @Override
                    protected void populateViewHolder(final requestViewHolder requestViewHolder, final RequestModel requestModel, int i) {

                        final String listUserId = getRef( i ).getKey();

                        if (listUserId != null)
                            userDatabase.child( listUserId ).addValueEventListener( new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    String name = (String) snapshot.child( "name" ).getValue();
                                    String image = (String) snapshot.child( "image" ).getValue();

                                    requestViewHolder.setName( name );
                                    requestViewHolder.setImage( image );

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            } );

                        requestViewHolder.accept.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText( view.getContext(), "accept", Toast.LENGTH_SHORT ).show();
                                final String date = DateFormat.getDateInstance().format( new Date() );

                                Map<String,Object> friendReq  =new HashMap<>();
                                friendReq.put( "Friends" + "/" + currentUid + "/" + listUserId + "/date", date );
                                friendReq.put( "Friends" + "/" + listUserId + "/" + currentUid + "/date", date );
                                friendReq.put( "FriendReq" + "/" + currentUid + "/" + listUserId + "/" + "requestType", null );
                                friendReq.put( "FriendReq" + "/" + listUserId + "/" + currentUid + "/" + "requestType", null );
                                friendReq.put( "RequestSeen" + "/" + currentUid + "/" + listUserId + "/" + "seenType", null );
                                friendReq.put( "Requests" + "/" + currentUid + "/" + listUserId + "/" + "requestType", null );

                                rootRef.updateChildren( friendReq, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText( view.getContext(), "DONE", Toast.LENGTH_SHORT ).show();
                                    }
                                } );
                            }
                        } );

                        requestViewHolder.decline.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Map<String,Object> declineRequest  =new HashMap<>();
                                declineRequest.put( "FriendReq" + "/" + currentUid + "/" + listUserId + "/" + "requestType", null );
                                declineRequest.put( "FriendReq" + "/" + listUserId + "/" + currentUid + "/" + "requestType", null );
                                declineRequest.put( "Requests" + "/" + currentUid + "/" + listUserId + "/" + "requestType", null  );
                                declineRequest.put( "RequestSeen" + "/" + currentUid + "/" + listUserId + "/" + "seenType", null );

                                rootRef.updateChildren( declineRequest, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText( view.getContext(), "Canceled", Toast.LENGTH_SHORT ).show();
                                    }
                                } );
                            }
                        } );
                    }
                };
        requestRecyclerView.setAdapter( firebaseRecyclerAdapter );

    }

    public static class requestViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView userName;
        CircleImageView profileImage;
        Button accept;
        Button decline;

        public requestViewHolder(@NonNull View itemView) {
            super( itemView );

            view = itemView;
            accept = view.findViewById( R.id.acceptRequestBtn );
            decline = view.findViewById( R.id.declineRequestBtn );

        }

        public void setName(String name) {
            userName = view.findViewById( R.id.userNameRequest );
            userName.setText( name );
        }

        public void setImage(String image) {
            profileImage = view.findViewById( R.id.profileImageRequest );

            Glide.with( view.getContext() ).load( image ).placeholder( R.drawable.download )
                    .into( profileImage );

        }
    }
}
