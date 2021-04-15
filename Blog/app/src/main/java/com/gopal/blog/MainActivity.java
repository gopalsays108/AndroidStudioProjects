package com.gopal.blog;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gopal.blog.register.LogInActivity;
import com.gopal.blog.register.SetUpActivity;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private RecyclerView blog_list;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceUsers;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference firebaseDatabaseLike;
    private boolean isProcessLike = false;
    String likedUserName;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    String uid;
    private  FirebaseRecyclerAdapter<BlogModel, BlogHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mWaveSwipeRefreshLayout = findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors( R.color.colorAccent );

        FirebaseDatabase.getInstance().setPersistenceEnabled( true );

            firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent loginIntent = new Intent( MainActivity.this, LogInActivity.class );
                    loginIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                    startActivity( loginIntent );
                    finish();
                }
            }
        };

        databaseReference = FirebaseDatabase.getInstance().getReference().child( "Blog" );
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference().child( "Users" );
        firebaseDatabaseLike = FirebaseDatabase.getInstance().getReference().child( "Likes" );

        databaseReferenceUsers.keepSynced( true );
        firebaseDatabaseLike.keepSynced( true );
        databaseReference.keepSynced( true );

        if (firebaseAuth.getCurrentUser() != null)
            uid = firebaseAuth.getCurrentUser().getUid();

        blog_list = findViewById( R.id.blog_list_recyclerView );
        blog_list.setHasFixedSize( true );
        blog_list.setLayoutManager( new LinearLayoutManager( this ) );

        checkUserExist();

        mWaveSwipeRefreshLayout.setOnRefreshListener( new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWaveSwipeRefreshLayout.setRefreshing( true );

                firebaseRecyclerAdapter.notifyDataSetChanged();
                if (isNetworkConnected()){
                    Toast.makeText( getApplicationContext() , "connected" ,Toast.LENGTH_SHORT ).show();
                    mWaveSwipeRefreshLayout.setRefreshing( false );
                }else{
                    Toast.makeText( getApplicationContext() , "not connected" ,Toast.LENGTH_SHORT ).show();
                    mWaveSwipeRefreshLayout.setRefreshing( true );
                }


            }
        } );

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    @Override
    protected void onStart() {
        super.onStart();


        firebaseAuth.addAuthStateListener( authStateListener );

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogModel, BlogHolder>(
                        BlogModel.class, R.layout.blog_row, BlogHolder.class,
                        databaseReference

                ) {
                    @Override
                    protected void populateViewHolder(final BlogHolder blogHolder, final BlogModel blogModel, int i) {

                        //<---- this give key or whole postion of card view clicked---->
                        final String postKey = getRef( i ).getKey();


                        blogHolder.setTitle( blogModel.getTitle() );
                        blogHolder.setDesc( blogModel.getDesc() );
                        blogHolder.setUrl( blogModel.getUrl() );
                        blogHolder.setDate( blogModel.getDate() );
                        blogHolder.setTime( blogModel.getTime() );
                        blogHolder.setImageUrl( blogModel.getImageUrl() );
                        blogHolder.setUserName( blogModel.getUserName() );
                        blogHolder.setLike( postKey );
                        blogHolder.likeCount( postKey, getApplicationContext() );

                        mWaveSwipeRefreshLayout.setRefreshing( false );
                        //this is add on click listener to whole post (one post)
                        blogHolder.view.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent( getApplicationContext(), BlogSingleActivity.class );
                                intent.putExtra( "blogKey", postKey );
                                startActivity( intent );

                            }
                        } );

                        blogHolder.likeButton.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isProcessLike = true;

                                firebaseDatabaseLike.addValueEventListener( new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        //String userName = blogModel.getUserName();

                                        //<---- to get name of like user---->
                                        databaseReferenceUsers.child( uid ).addValueEventListener( new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                likedUserName = (String) snapshot.child( "name" ).getValue();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        } );

                                        if (isProcessLike) {

                                            if (postKey != null)

                                                if (snapshot.child( postKey ).hasChild( uid )) {

                                                    firebaseDatabaseLike.child( postKey ).child( uid ).removeValue();
                                                    isProcessLike = false;

                                                } else {
                                                    firebaseDatabaseLike.child( postKey ).child( uid )
                                                            .setValue( likedUserName );

                                                    isProcessLike = false;
                                                }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                } );
                            }

                        } );

                    }
                };

        blog_list.setAdapter( firebaseRecyclerAdapter );
    }

    public static class BlogHolder extends RecyclerView.ViewHolder {

        View view;

        ImageButton likeButton;

        DatabaseReference databaseReference;
        DatabaseReference postLikeReference;
        FirebaseAuth firebaseAuth;

        public BlogHolder(@NonNull View itemView) {
            super( itemView );

            view = itemView;
            likeButton = view.findViewById( R.id.post_like );
            databaseReference = FirebaseDatabase.getInstance().getReference( "Likes" );
            postLikeReference = FirebaseDatabase.getInstance().getReference( "Likes" );
            firebaseAuth = FirebaseAuth.getInstance();
            databaseReference.keepSynced( true );

        }

        void setLike(final String postKey) {
            databaseReference.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (firebaseAuth.getCurrentUser() != null) {

                        String uid = firebaseAuth.getCurrentUser().getUid();
                        if (snapshot.child( postKey ).hasChild( uid )) {

                            likeButton.setImageResource( R.drawable.like_logo );

                        } else {
                            likeButton.setImageResource( R.drawable.ic_thumb_up_black_18dp );
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );

        }

        void setTitle(String title) {

            TextView postTitle = view.findViewById( R.id.post_title_id );
            postTitle.setText( title );
        }

        void setDesc(String desc) {
            TextView postDesc = view.findViewById( R.id.post_desc_id );
            postDesc.setText( desc );
        }

        void setUrl(String url) {
            ImageView postImage = view.findViewById( R.id.post_image_id );
            Glide.with( view.getContext() ).load( url ).into( postImage );

        }

        void setDate(String date) {
            TextView postDate = view.findViewById( R.id.currentDate );
            postDate.setText( date );
        }

        void setTime(String time) {
            TextView postTime = view.findViewById( R.id.currentTime );
            postTime.setText( time );
        }

        void setImageUrl(String imageUrl) {
            ImageView postProfileImage = view.findViewById( R.id.profile_image );
            Glide.with( view.getContext() ).load( imageUrl ).into( postProfileImage );

        }

        void setUserName(String userName) {
            TextView postUserName = view.findViewById( R.id.userName );
            postUserName.setText( userName );
        }

        void likeCount(String postKey, final Context applicationContext) {

            final TextView countText = view.findViewById( R.id.likeCount );

            postLikeReference.child( postKey ).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    int count = (int) snapshot.getChildrenCount();
                    if (count == 0) {
                        countText.setVisibility( View.INVISIBLE );
                    } else if (count > 0 && count <= 100) {
                        countText.setVisibility( View.VISIBLE );
                        countText.setText( String.valueOf( count ) );
                    } else {
                        countText.setVisibility( View.VISIBLE );
                        countText.setText( String.format( "%s %s", String.valueOf( count ), applicationContext.getResources().getString( R.string.manyMore ) ) );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );

        }
    }

    private void checkUserExist() {

        if (firebaseAuth.getCurrentUser() != null) {
            final String uid = firebaseAuth.getCurrentUser().getUid();
            databaseReferenceUsers.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.hasChild( uid )) {
                        Intent intent = new Intent( getApplicationContext(), SetUpActivity.class );
                        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity( intent );
                        finish();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );
        }
    }

    // this help to inflate(to show) main menu resource
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.main_menu, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity( new Intent( getApplicationContext(), PostActivity.class ) );
        } else if (item.getItemId() == R.id.action_logOut) {
            logOut();
        }else if (item.getItemId() == R.id.action_setting){
            Toast.makeText( this, "Gaandu kaam ni krta yeah , mat click kra kr ", Toast.LENGTH_LONG ).show();
        }

        return super.onOptionsItemSelected( item );
    }

    private void logOut() {

        firebaseAuth.signOut();
    }
}
