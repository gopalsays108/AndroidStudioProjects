package com.gopal.gettogether;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static com.gopal.gettogether.GetTogether.makeOnline;

public class ChatActivity extends AppCompatActivity {

    private String chatUserId;
    private DatabaseReference chatUserDatabase;
    private TextView chatUserName;
    private TextView lastSeenView;
    private DatabaseReference rootRef;
    private DatabaseReference messageRef;
    private DatabaseReference onlineReference;
    private ImageButton chatAddBtn;
    private ImageButton chatSendBtn;
    private EditText chatMessageView;
    private CircleImageView profileImageView;
    private FirebaseUser firebaseAuth;
    private RecyclerView messagesListRecyclerView;
    private List<MessagesModel> messagesModelsList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String currentUid;
    private static final int TOTAL_ITEM_TO_LOAD = 10;
    private int currentPage = 1;
    private Uri chooseImageUri;
    private String downloadUrl;

    private String lastKey = "";
    private int itemPost = 0;
    private String prevKey = "";
    private String currentTime;
    private boolean isFull = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chat );
        firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseAuth != null) {
            currentUid = firebaseAuth.getUid();
        }

        Toolbar toolbar = findViewById( R.id.chatAppBar );
        setSupportActionBar( toolbar );

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled( true );
            actionBar.setDisplayShowCustomEnabled( true ); //to add custom view to action bar
        }
        chatUserDatabase = FirebaseDatabase.getInstance().getReference( "Users" );
        onlineReference = FirebaseDatabase.getInstance().getReference( "Users" ).child( currentUid );
        rootRef = FirebaseDatabase.getInstance().getReference();

        //------------ Current user phone time---------
        Calendar calendar = Calendar.getInstance();
        currentTime = DateFormat.getTimeInstance().format( calendar.getTime() );

        //  onlineReference.child( "online" ).setValue( "true" );

        chatUserId = getIntent().getStringExtra( "user_id" );
        String userName = getIntent().getStringExtra( "userName" );
        String userImage = getIntent().getStringExtra( "userImage" );

        LayoutInflater inflater = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        if (inflater != null) {
            View actionBarView = inflater.inflate( R.layout.chat_custom_bar, null );

            if (actionBar != null)
                actionBar.setCustomView( actionBarView );

            //-------------Custom Action Bar Item---------------
            chatUserName = findViewById( R.id.customBarUserName );
            lastSeenView = findViewById( R.id.customBarLastSeen );
            profileImageView = findViewById( R.id.customBarImage );
            chatAddBtn = findViewById( R.id.chatAddBtn );
            chatSendBtn = findViewById( R.id.chatSentBtn );
            chatAddBtn = findViewById( R.id.chatAddBtn );
            chatMessageView = findViewById( R.id.chatMessageView );

            messageAdapter = new MessageAdapter( messagesModelsList );

            messageRef = rootRef.child( "messages" ).child( currentUid ).child( chatUserId );

            messagesListRecyclerView = findViewById( R.id.messagesList );
            swipeRefreshLayout = findViewById( R.id.messageSwipeLayout );

            linearLayoutManager = new LinearLayoutManager( this );
            messagesListRecyclerView.setHasFixedSize( true );
            messagesListRecyclerView.setLayoutManager( linearLayoutManager );

            messagesListRecyclerView.setAdapter( messageAdapter );

            loadMessages();

            //------ Loading user name and image--------------
            chatUserName.setText( userName );
            if (userImage != null) {
                Glide.with( getApplicationContext() ).load( userImage ).placeholder( R.drawable.download ).into( profileImageView );
            }

            chatUserDatabase.child( chatUserId ).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String online = snapshot.child( "online" ).getValue().toString();
                    String image = (String) snapshot.child( "image" ).getValue();

                    if (online.equals( "true" )) {
                        lastSeenView.setText( "online" );
                    } else {
                        GetTimeAgo getTimeAgo = new GetTimeAgo();

                        long lastTime = 0;
                        lastTime = Long.parseLong( online );

                        String lastSeen = getTimeAgo.getTimeAgo( lastTime, ChatActivity.this );
                        Log.i( "CHATTAG", "" + lastTime + lastSeen );

                        // Toast.makeText( getTimeAgo, lastSeen, Toast.LENGTH_SHORT ).show();

                        lastSeenView.setText( lastSeen );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );

            rootRef.child( "Chat" ).child( currentUid ).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (!snapshot.hasChild( chatUserId )) {

                        Map<String, Object> chatAddMap = new HashMap<>();
                        chatAddMap.put( "seen", true );
                        chatAddMap.put( "timeStamp", currentTime );

                        Map<String, Object> chatUserMap = new HashMap<>();
                        chatUserMap.put( "Chat/" + currentUid + "/" + chatUserId, chatAddMap );
                        chatUserMap.put( "Chat/" + chatUserId + "/" + currentUid, chatAddMap );

                        rootRef.updateChildren( chatUserMap, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error != null) {
                                    Log.i( "CHATLOG", error.getDetails() );
                                }
                            }
                        } );

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );

            //----------- chat sent ------------------
            chatSendBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMessage();
                }
            } );

            chatAddBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //---------------- To add image send feature------------------------
                    checkFileTranferPermission();
                }
            } );
        }


        //---------SWIPE REFRESH LAYOUT-----------
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                currentPage++;

                messageRef.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int no = (int) snapshot.getChildrenCount();

                        int loadedNo = currentPage * TOTAL_ITEM_TO_LOAD;

                        if (loadedNo <= no) {

                            itemPost = 0;

                            loadMoreMessages();

                        } else {
                            Toast.makeText( ChatActivity.this, "All" + no + " loaded", Toast.LENGTH_SHORT ).show();

                            if (!isFull) {
                                itemPost = 0;
                                loadMoreMessages();
                                isFull = true;
                            } else {
                                swipeRefreshLayout.setRefreshing( false );
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

    private void checkFileTranferPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission( Manifest.permission.CAMERA ) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_DENIED) {

                //permission not enabled request it
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions( permission, 20 );

            } else {

                // this is used to select the photo from galley
                CropImage.activity().setGuidelines( CropImageView.Guidelines.ON )
                        .setCropShape( CropImageView.CropShape.RECTANGLE )
                        .start( this );

            }

        } else {

            CropImage.activity().setGuidelines( CropImageView.Guidelines.ON )
                    .setCropShape( CropImageView.CropShape.RECTANGLE )
                    .start( this );

        }
    }

    //------ To retrieve Image after intent-----------
    //-----this methods is called when user presses allow or deny from permission request popup
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult( data );
            if (resultCode == RESULT_OK) {
                if (result != null)

                    chooseImageUri = result.getUri();
                    startSendingImage();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                if (result != null) {
                    Exception error = result.getError();
                    Toast.makeText( this, error.getMessage(), Toast.LENGTH_LONG ).show();
                }
            }
        }
    }

    private void startSendingImage() {

        if (chooseImageUri != null) {

            if (chooseImageUri.getPath() != null) {

                final String current_user_red = "messages/" + currentUid + "/" + chatUserId;
                final String chat_user_red = "messages/" + chatUserId + "/" + currentUid;

                DatabaseReference user_message_push = rootRef.child( "messages" ).child( currentUid ).child( chatUserId ).push();

                final String push_ud = user_message_push.getKey();

                File file_thumb_image_path = new File( chooseImageUri.getPath() );

                Bitmap thumb_bitmap = null;


                try {
                    thumb_bitmap = new Compressor( this )
                            .setMaxWidth( 200 )
                            .setMaxHeight( 200 )
                            .setQuality( 75 )
                            .compressToBitmap( file_thumb_image_path );

                } catch (IOException e) {
                    e.printStackTrace();//nikal acticity yehi hai dishhh vaala
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (thumb_bitmap != null)
                    thumb_bitmap.compress( Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream );
                byte[] final_Image = byteArrayOutputStream.toByteArray();

                final StorageReference storageReference1 = FirebaseStorage.getInstance().getReference();

                if (chooseImageUri.getLastPathSegment() != null) {
                    final StorageReference imagereference1 = storageReference1.child( "GetTogether" ).child( "messages_img" )
                            .child( push_ud + ".jpg" );

                    UploadTask uploadTask = imagereference1.putBytes( final_Image );
                    uploadTask.addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagereference1.getDownloadUrl().addOnCompleteListener( new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.getResult() != null)
                                        downloadUrl = task.getResult().toString();

                                    Map<String, Object> messageMap = new HashMap<>();
                                    messageMap.put( "message", downloadUrl );
                                    messageMap.put( "seen", true );
                                    messageMap.put( "type", "image" );
                                    messageMap.put( "time", currentTime );
                                    messageMap.put( "from", currentUid );

                                    Map<String, Object> messageUseMap = new HashMap<>();
                                    messageUseMap.put( current_user_red + "/" + push_ud, messageMap );
                                    messageUseMap.put( chat_user_red + "/" + push_ud, messageMap );

                                    chatMessageView.setText( "" );


                                    rootRef.updateChildren( messageUseMap, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                            if (error == null) {

                                            } else {
                                                Toast.makeText( getApplicationContext(), error.toString(), Toast.LENGTH_SHORT ).show();
                                            }
                                        }
                                    } );
                                }
                            } );
                        }
                    } );

                } else {
                    Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
                }


            } else {
                Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
            }


        } else {
            Toast.makeText( this, "Try Again", Toast.LENGTH_SHORT ).show();
        }
    }

    private void loadMoreMessages() {

        messageRef = rootRef.child( "messages" ).child( currentUid ).child( chatUserId );

        Query messageQuery = messageRef.orderByKey().endAt( lastKey ).limitToLast( 10 );

        messageQuery.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String from = (String) snapshot.child( "from" ).getValue();
                String message = (String) snapshot.child( "message" ).getValue();
                boolean seen = (boolean) snapshot.child( "seen" ).getValue();
                String time = (String) snapshot.child( "time" ).getValue();
                String type = (String) snapshot.child( "type" ).getValue();
                String chatUser = chatUserId;

                // messagesModelsList.add( new MessagesModel( message , seen  ,time , type ,from , snapshot.getKey() , chatUser));
                String messageKey = snapshot.getKey();

                if (!prevKey.equals( messageKey )) {
                    messagesModelsList.add( itemPost++, new MessagesModel( message, seen, time, type, from, snapshot.getKey(), chatUser ) );
                } else {
                    prevKey = lastKey;
                }

                if (itemPost == 1) {
                    lastKey = messageKey;
                }

                messageAdapter.notifyDataSetChanged();

                //messagesListRecyclerView.scrollToPosition( messagesModelsList.size() - 1 ); // bottom of the recyclerview

                linearLayoutManager.scrollToPositionWithOffset( itemPost, 0 );

                swipeRefreshLayout.setRefreshing( false );

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String deletedkey = snapshot.getKey();
//                Log.i( "DELETEKEY" , deletedkey );
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

//                String deletedkey = snapshot.getKey();
//                Log.i( "DELETEKEY" , deletedkey );

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }

    private void loadMessages() {

        messageRef = rootRef.child( "messages" ).child( currentUid ).child( chatUserId );

        Query messageQuery = messageRef.limitToLast( currentPage * TOTAL_ITEM_TO_LOAD );  // only show last five messages

        messageQuery.addChildEventListener( new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String from = (String) snapshot.child( "from" ).getValue();
                String message = (String) snapshot.child( "message" ).getValue();
                boolean seen = (boolean) snapshot.child( "seen" ).getValue();
                String time = (String) snapshot.child( "time" ).getValue();
                String type = (String) snapshot.child( "type" ).getValue();
                String chatUser = chatUserId;

                //    MessagesModel messagesModel = snapshot.getValue( MessagesModel.class );


                itemPost++;

                if (itemPost == 1) {

                    lastKey = snapshot.getKey();
                    prevKey = lastKey;

                }

                messagesModelsList.add( new MessagesModel( message, seen, time, type, from, snapshot.getKey(), chatUser ) );
                //   messagesModelsList.add( messagesModel );
                messageAdapter.notifyDataSetChanged();

                messagesListRecyclerView.scrollToPosition( messagesModelsList.size() - 1 ); // bottom of the recyclerview

                swipeRefreshLayout.setRefreshing( false );

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                String deletedKey = snapshot.getKey();

                //deleting and refreshing data
                for (int i = 0; i < messagesModelsList.size(); i++) {
                    // Find the item to remove and then remove it by index
                    if (messagesModelsList.get(i).key.equals(deletedKey)) {
                        messagesModelsList.remove(i);
                        break;
                    }
                }
                messageAdapter.notifyDataSetChanged();

                linearLayoutManager.scrollToPositionWithOffset( messagesModelsList.size(), 0 );

                Log.i( "DELETEKEY" , deletedKey );
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );

    }

    private void sendMessage() {

        String message = chatMessageView.getText().toString().trim();

        if (message.isEmpty()) {
            chatMessageView.setHint( "Enter Message" );
        } else {
            chatSendBtn.setEnabled( true );

            String currentUserRef = "messages" + "/" + currentUid + "/" + chatUserId;
            String chatUserRef = "messages" + "/" + chatUserId + "/" + currentUid;

            // message id
            DatabaseReference userMessagePush = rootRef.child( "messages" ).child( currentUid ).child( chatUserId
            ).push();
            String pushId = userMessagePush.getKey();

            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put( "message", message );
            messageMap.put( "seen", true );
            messageMap.put( "type", "text" );
            messageMap.put( "time", currentTime );
            messageMap.put( "from", currentUid );

            Map<String, Object> messageUserMap = new HashMap<>();
            messageUserMap.put( currentUserRef + "/" + pushId, messageMap );
            messageUserMap.put( chatUserRef + "/" + pushId, messageMap );

            chatMessageView.setText( "" );

            rootRef.updateChildren( messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if (error == null) {
                        chatMessageView.setText( "" );
                    } else {
                        Log.i( "MESSAGE ERROR", error.getDetails() );
                    }
                }
            } );
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth != null){
            makeOnline();
        }
    }
}
