package com.gopal.photoblog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView blogListView;
    private View view;
    public List<BlogPostModel> blogPostModelsList;
    private FirebaseFirestore firebaseFirestore;
    private BlogRecyclerAdapter blogRecyclerAdapter;
    private DocumentSnapshot lastVisible;
    private FirebaseAuth firebaseAuth;
    private boolean isFirstPage = true;
    private FirebaseUser firebaseUser;
    private String currentUser;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_home, container, false );
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            currentUser = firebaseUser.getUid();

            blogPostModelsList = new ArrayList<>();
            blogListView = view.findViewById( R.id.blogListView );
            firebaseFirestore = FirebaseFirestore.getInstance();
            blogRecyclerAdapter = new BlogRecyclerAdapter( blogPostModelsList );


            blogListView.setLayoutManager( new LinearLayoutManager( view.getContext() ) );
            blogListView.setAdapter( blogRecyclerAdapter );

            blogListView.addOnScrollListener( new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled( recyclerView, dx, dy );

                    boolean isReachedAtBottom = !recyclerView.canScrollVertically( 1 ); // 1 for reached at bottom or not

                    if (isReachedAtBottom) {
                        String lastVisi = lastVisible.getString( "desc" );
                        Toast.makeText( view.getContext(), "Reached : " + lastVisi, Toast.LENGTH_SHORT ).show();

                        loadMorePost();
                    }
                }
            } );

            /* starAt = 1 ,2 ,3 and then loads 3 , 4,5 ... and soo son
             *  startAfter = 1,2.3 and then loads 4,5,6 and soo on..
             *  therefore we will use start after as it doesn't load same post twice
             * */

            // we have arranged post to newer one first by query
            Query firstQuery = firebaseFirestore.collection( "Posts" )
                    .document(currentUser).collection( "Post" )
                    .orderBy( "timestamp", Query.Direction.DESCENDING ).limit( 3 );

            //snapshot listner is for real time database , this is not realtime
//            if (getActivity() != null)
            firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null) {

                        if (!value.isEmpty()) {

                            if (isFirstPage) {
                                lastVisible = value.getDocuments().get( value.size() - 1 ); // we didn't passed it in for loop bcz we want it ot run once
                            }
                            for (DocumentChange doc : value.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    String blogPostId = doc.getDocument().getId();
                                    BlogPostModel blogPostModel = doc.getDocument().toObject( BlogPostModel.class ).withId( blogPostId );

                                    if (isFirstPage) {
                                        blogPostModelsList.add( blogPostModel );
                                    }else{
                                        Toast.makeText( getContext(), "new Post added", Toast.LENGTH_SHORT ).show();
                                        blogPostModelsList.add(0 , blogPostModel  ); // add new post yo 0th position means at first in recycler view
                                    }
                                    blogRecyclerAdapter.notifyDataSetChanged();

                                }
                            }
                            isFirstPage = false;
                        }

                    }
                }
            } );
        }

        return view;
    }

    // for pagination see part 14
    public void loadMorePost() {

        Query secondQuery = firebaseFirestore.collection( "Posts" )
                .document(currentUser).collection( "Post" )
                .orderBy( "timestamp", Query.Direction.DESCENDING )
                .startAfter( lastVisible )
                .limit( 3 );

         // add getactivity or this whereever we use addsnapshot listner bcz
            // it is real time query soo we need to include activity so it attaches itself to the
            // activity and whenever we start it start lisnting and whenever we minimize or close it stops listning
        //snapshot listner is for real time database , this is not realtime
        //if (getActivity() != null)
        secondQuery.addSnapshotListener( new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (value != null)
                    if (!value.isEmpty()) {

                        lastVisible = value.getDocuments().get( value.size() - 1 ); // we didn't passed it in for loop bcz we want it ot run once

                        for (DocumentChange doc : value.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String blogPostId = doc.getDocument().getId();
                                BlogPostModel blogPostModel = doc.getDocument().toObject( BlogPostModel.class ).withId( blogPostId );
                                blogPostModelsList.add( blogPostModel );

                                blogRecyclerAdapter.notifyDataSetChanged();

                            }
                        }
                    } else {
                        Toast.makeText( view.getContext(), "Reached At Bottom", Toast.LENGTH_LONG ).show();
                    }
                }

        } );
    }
}

//Query firstQuery = firebaseFirestore.collection( "Posts" ).orderBy( "time" , Query.Direction.DESCENDING );