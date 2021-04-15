package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.widget.RemoteViews;

final class ah implements ae {
    ah() {
    }

    public final Notification a(ac acVar) {
        Context context = acVar.a;
        Notification notification = acVar.r;
        CharSequence charSequence = acVar.b;
        CharSequence charSequence2 = acVar.c;
        CharSequence charSequence3 = acVar.h;
        RemoteViews remoteViews = acVar.f;
        int i = acVar.i;
        PendingIntent pendingIntent = acVar.d;
        PendingIntent pendingIntent2 = acVar.e;
        return new Notification.Builder(context).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(acVar.g).setNumber(i).setProgress(acVar.n, acVar.o, acVar.p).getNotification();
    }
}
