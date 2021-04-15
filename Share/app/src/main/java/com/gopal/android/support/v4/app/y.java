package android.support.v4.app;

import android.os.Build;

public final class y {
    /* access modifiers changed from: private */
    public static final ae a;

    static {
        if (Build.VERSION.SDK_INT >= 16) {
            a = new ai();
        } else if (Build.VERSION.SDK_INT >= 14) {
            a = new ah();
        } else if (Build.VERSION.SDK_INT >= 11) {
            a = new ag();
        } else {
            a = new af();
        }
    }
}
