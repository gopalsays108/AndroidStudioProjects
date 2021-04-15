package com.gopal.photoblog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    public List<BlogPostModel> blogPostModels;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseAuth;

    public BlogRecyclerAdapter(List<BlogPostModel> blogPostModels) {

        // pehle vaala variable iss  page ka hai aur = is baad jo hamme list milega model class se hoga//
        this.blogPostModels = blogPostModels;

    }

    //first three methods is required for our adapter
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.blog_list_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.setIsRecyclable( false ); //

        String descData = blogPostModels.get( position ).getDesc();
        String imageUrl = blogPostModels.get( position ).getImageUrl();
        final String userId = blogPostModels.get( position ).getUserId();
        String date = blogPostModels.get( position ).getTime();
        final String blogPostId = blogPostModels.get( position ).BlogPostId;
        final String currntUserId = firebaseAuth.getUid();

        holder.setDescText( descData );
        holder.setBlogImage( imageUrl );
        holder.setDate( date );
        holder.setName( userId );

        //get likes count
        firebaseFirestore.collection( "Posts" ).document( blogPostId ).collection( "Likes" )
                .addSnapshotListener( new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (!value.isEmpty()) {

                            int count = value.size();
                            holder.updateLikeCount( count );
                        } else {

                            holder.updateLikeCount( 0 );
                        }

                    }
                } );


        //get Likes
        firebaseFirestore.collection( "Posts" ).document( blogPostId ).collection( "Likes" )
                .document( currntUserId ).addSnapshotListener( new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (value.exists()) {
                    // set drawable
                } else {
                    //grey color
                }

            }
        } );

        //If want to add click lisner we can add here ass follow
        holder.blogLikeImageBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore.collection( "Posts" ).document( blogPostId ).collection( "Likes" )
                        .document( currntUserId ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (!task.getResult().exists()) {
                            Map<String, Object> likesMap = new HashMap<>();
                            likesMap.put( "timestamp", FieldValue.serverTimestamp() );
                            firebaseFirestore.collection( "Posts" ).document( blogPostId ).collection( "Likes" )
                                    .document( currntUserId ).set( likesMap );
                        } else {
                            firebaseFirestore.collection( "Posts" ).document( blogPostId ).collection( "Likes" )
                                    .document( currntUserId ).delete();

                        }
                    }
                } );

            }
        } );
    }

    @Override
    public int getItemCount() {
        return blogPostModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView descView;
        private TextView userName;
        private TextView date;
        private TextView blogLikeCount;
        private CircleImageView userImage;
        private ImageView blogImageView;
        private ImageView blogLikeImageBtn;
        private View view;


        // required for view holder
        public ViewHolder(@NonNull View itemView) {
            super( itemView );
            view = itemView;
            descView = view.findViewById( R.id.blogDesc );
            blogImageView = view.findViewById( R.id.blogImage );
            userName = view.findViewById( R.id.blogUserName );
            date = view.findViewById( R.id.blogDate );
            firebaseFirestore = FirebaseFirestore.getInstance();
            userImage = view.findViewById( R.id.blogUserImage );
            blogLikeCount = view.findViewById( R.id.blogLikeCount );
            blogLikeImageBtn = view.findViewById( R.id.blogLikeBtn );

            firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
            String currentUSerId;
            if (firebaseAuth != null)
                currentUSerId = firebaseAuth.getUid();
        }

        public void setDescText(String descText) {
            descView.setText( descText );
        }

        public void setBlogImage(String downloadUrl) {

            Glide.with( view.getContext() ).load( downloadUrl ).placeholder( R.drawable.download )
                    .into( blogImageView );

        }

        public void setDate(String blogDate) {
            date.setText( blogDate );
        }

        public void setName(final String uid) {
            // this os not realtime
            firebaseFirestore.collection( "Users" ).document( uid )
                    .get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.getResult() != null) {
                        if (task.isSuccessful()) {
                            String name = (String) task.getResult().get( "name" );
                            String image = (String) task.getResult().get( "image" );

                            userName.setText( name );
                            // using thumbnail we can load smaller image first
                            Glide.with( view.getContext() ).load( image ).placeholder(
                                    R.drawable.download ).into( userImage );

                            /*  Glide.with( view.getContext() ).load( image ).placeholder(
                                    R.drawable.download ).thumbnail( Glide.with( view.getContext() ).load( image ) ).into( userImage );*/
                        }
                    }

                }
            } );
        }

        public void updateLikeCount(int count) {
            blogLikeCount.setText( count + " Likes" );
        }
    }
}
