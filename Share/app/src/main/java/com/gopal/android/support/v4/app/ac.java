package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import java.util.ArrayList;

public final class ac {
    Context a;
    public CharSequence b;
    public CharSequence c;
    public PendingIntent d;
    PendingIntent e;
    RemoteViews f;
    Bitmap g;
    CharSequence h;
    int i;
    int j;
    boolean k;
    aj l;
    CharSequence m;
    int n;
    int o;
    boolean p;
    public ArrayList q = new ArrayList();
    public Notification r = new Notification();

    public ac(Context context) {
        this.a = context;
        this.r.when = System.currentTimeMillis();
        this.r.audioStreamType = -1;
        this.j = 0;
    }
}
