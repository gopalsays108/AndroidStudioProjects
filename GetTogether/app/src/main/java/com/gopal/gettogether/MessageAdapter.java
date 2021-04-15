package com.gopal.gettogether;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<MessagesModel> messagesModels;
    private FirebaseUser firebaseAuth;
    private String uid;
    private DatabaseReference databaseReference;
    private DatabaseReference deleteReference;

    public MessageAdapter(List<MessagesModel> messagesModels) {
        this.messagesModels = messagesModels;

    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.message_single_layout
                ,parent, false );
        return new MessageViewHolder( view );
    }


    class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView messageText;
        private CircleImageView profileImage;
        private TextView displayName;
        private ImageView messageImage;
        private TextView messageTime;

        private TextView messageTextMe;
      //  private CircleImageView profileImageMe;
        private TextView displayNameMe;
        private ImageView messageImageMe;
        private TextView messageTimeMe;

        private RelativeLayout otherLayout, mineLayout;

        MessageViewHolder(@NonNull View view) {
            super( view );

            messageText = view.findViewById( R.id.messageTextLayout );
            profileImage = view.findViewById( R.id.messageProfileLayout );
            displayName = view.findViewById( R.id.nameTextLayout );
            messageImage = view.findViewById( R.id.imageImageSingleLayout );
            messageTime = view.findViewById( R.id.timeMessageSingleLayout );

            // other person
            otherLayout = view.findViewById( R.id.otherPersonLayout );
            mineLayout = view.findViewById( R.id.myLayout );
            messageTextMe = view.findViewById( R.id.messageTextLayoutMe );
          //  profileImageMe = view.findViewById( R.id.messageProfileLayoutMe );
            displayNameMe = view.findViewById( R.id.nameTextLayoutMe );
            messageImageMe = view.findViewById( R.id.imageImageSingleLayoutMe );
            messageTimeMe = view.findViewById( R.id.timeMessageSingleLayoutMe );

            firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseAuth != null)
                uid = firebaseAuth.getUid();

        }

        private void setData(final String message, String type, final String from, final String time, final int position,
                             final String key, final String chatUser) {

            firebaseAuth = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseAuth != null)
                uid = firebaseAuth.getUid();

            databaseReference = FirebaseDatabase.getInstance().getReference().child( "Users" ).child( from );
            deleteReference = FirebaseDatabase.getInstance().getReference().child( "messages" );
            databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String name = snapshot.child( "name" ).getValue().toString();
                    String image = snapshot.child( "image" ).getValue().toString();

                    if (from.equals( uid )) {

                        otherLayout.setVisibility( View.GONE );
                        mineLayout.setVisibility( View.VISIBLE );
                       // profileImageMe.setVisibility( View.GONE );
                        displayNameMe.setText( "You" );
//                      messageText.setPaddingRelative( 250 ,0 , 15 , 0 );
//                      messageTime.setPaddingRelative( 250 , 0 , 15 , 0 );

                    } else {
                        mineLayout.setVisibility( View.GONE );
                        otherLayout.setVisibility( View.VISIBLE );
                        //profileImage.setVisibility( View.VISIBLE );
                        //displayName.setVisibility( View.VISIBLE );
                        displayName.setText( name );
                        Glide.with( itemView.getContext() ).load( image ).placeholder( R.drawable.download )
                                .into( profileImage );
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            } );

            if (type.equals( "text" )) {
                if (from.equals( uid )){
                    messageTextMe.setVisibility( View.VISIBLE );
                    messageImageMe.setVisibility( View.GONE );
                    messageTimeMe.setText( time );
                    messageTextMe.setText( message );
                }else {
                    messageText.setVisibility( View.VISIBLE );
                    messageImage.setVisibility( View.GONE );
                    messageTime.setText( time );
                    messageText.setText( message );
                }
            } else {
                if (from.equals( uid )){
                    otherLayout.setVisibility( View.GONE );
                    mineLayout.setVisibility( View.VISIBLE );
                    messageTextMe.setVisibility( View.INVISIBLE );
                    messageImageMe.setVisibility( View.VISIBLE );
                    messageTimeMe.setText( time );
                    Glide.with( itemView.getContext() ).load( message )
                            .placeholder( R.drawable.download ).into( messageImageMe );
                }else {
                    otherLayout.setVisibility( View.VISIBLE );
                    mineLayout.setVisibility( View.GONE );
                    messageText.setVisibility( View.INVISIBLE );
                    messageTime.setVisibility( View.VISIBLE );
                    messageTime.setText( time );
                    messageImage.setVisibility( View.VISIBLE );
                    Glide.with( itemView.getContext() ).load( message )
                            .placeholder( R.drawable.download ).into( messageImage );
                }
            }

            itemView.setOnLongClickListener( new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if (from.equals( uid )) {

                        CharSequence[] option = new CharSequence[]{"Delete for me", "Delete for everyone"};

                        AlertDialog.Builder builder = new AlertDialog.Builder( itemView.getContext() );
                        builder.setTitle( "Select an option" );
                        builder.setItems( option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (which == 0) {

                                    deleteReference.child( from ).child( chatUser ).child( key ).removeValue().addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText( itemView.getContext(), "Deleted", Toast.LENGTH_SHORT ).show();
                                        }
                                    } );
                                    notifyDataSetChanged();

                                } else if (which == 1) {
                                    // Log.i( "KEYY" , key +"\n" + "From = " + from + "\n" + "chatuser = " + chatUser) ;

                                    Map<String ,Object> deleteMap = new HashMap<>();
                                    deleteMap.put( from + "/" + chatUser + "/" + key, null );
                                    deleteMap.put( chatUser + "/" + from + "/" + key, null );

                                    deleteReference.updateChildren( deleteMap, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                                            Toast.makeText( itemView.getContext(), "Deleted", Toast.LENGTH_SHORT ).show();
                                            notifyDataSetChanged();

                                        }
                                    } );
                                }
                            }
                        } );
                        builder.show();
                    } else {
                        Toast.makeText( itemView.getContext(), "gopal", Toast.LENGTH_SHORT ).show();
                    }

                    return true;
                }
            } );


        }

        //
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position) {

        holder.setData( messagesModels.get( position ).getMessage(), messagesModels.get( position ).getType(),
                messagesModels.get( position ).getFrom(), messagesModels.get( position ).getTime(), position,
                messagesModels.get( position ).getKey(), messagesModels.get( position ).chatUser );
    }

    @Override
    public int getItemCount() {
        return messagesModels.size();
    }


}
