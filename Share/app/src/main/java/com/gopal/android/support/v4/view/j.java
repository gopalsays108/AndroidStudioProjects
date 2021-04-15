package android.support.v4.view;

import android.os.Build;
import android.view.VelocityTracker;

public final class j {
    static final m a;

    static {
        if (Build.VERSION.SDK_INT >= 11) {
            a = new l();
        } else {
            a = new k();
        }
    }

    public static float a(VelocityTracker velocityTracker, int i) {
        return a.a(velocityTracker, i);
    }
}
