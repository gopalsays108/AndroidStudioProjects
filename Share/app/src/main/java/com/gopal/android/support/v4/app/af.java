package android.support.v4.app;

import android.app.Notification;

final class af implements ae {
    af() {
    }

    public final Notification a(ac acVar) {
        Notification notification = acVar.r;
        notification.setLatestEventInfo(acVar.a, acVar.b, acVar.c, acVar.d);
        if (acVar.j > 0) {
            notification.flags |= 128;
        }
        return notification;
    }
}
