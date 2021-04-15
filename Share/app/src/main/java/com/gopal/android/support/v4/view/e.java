package android.support.v4.view;

import android.os.Build;
import android.view.MotionEvent;

public final class e {
    static final h a;

    static {
        if (Build.VERSION.SDK_INT >= 5) {
            a = new g();
        } else {
            a = new f();
        }
    }

    public static int a(MotionEvent motionEvent) {
        return (motionEvent.getAction() >> 8) & 255;
    }

    public static int a(MotionEvent motionEvent, int i) {
        return a.a(motionEvent, i);
    }

    public static int b(MotionEvent motionEvent, int i) {
        return a.b(motionEvent, i);
    }

    public static float c(MotionEvent motionEvent, int i) {
        return a.c(motionEvent, i);
    }

    public static float d(MotionEvent motionEvent, int i) {
        return a.d(motionEvent, i);
    }
}
