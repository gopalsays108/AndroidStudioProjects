package com.gopal.gettogether;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived( remoteMessage );

        String clickAction = remoteMessage.getNotification().getClickAction();
        String fromUserId = remoteMessage.getData().get( "from_user_id" );

        Log.i("USERID" , fromUserId);

           String notificationTitle = remoteMessage.getNotification().getTitle();
           String notificationMessage = remoteMessage.getNotification().getBody();

           Notification.Builder notification = new Notification.Builder( this )
                   .setContentTitle( notificationTitle )
                   .setContentText( notificationMessage )
                   .setSmallIcon( R.drawable.download );


        Intent intent = new Intent( clickAction );
        intent.putExtra( "user_id" , fromUserId );

        PendingIntent pendingIntent  =
                PendingIntent.getActivity( this , 0 ,
                        intent , PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setContentIntent( pendingIntent );

           int notificationId = (int) System.currentTimeMillis();

           NotificationManager notificationManager =
                   (NotificationManager) getSystemService( NOTIFICATION_SERVICE );

           notificationManager.notify( notificationId, notification.build() );



    }
}
