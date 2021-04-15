package android.support.v4.view;

import android.os.Build;
import android.view.KeyEvent;

public final class a {
    static final d a;

    static {
        if (Build.VERSION.SDK_INT >= 11) {
            a = new c();
        } else {
            a = new b();
        }
    }

    public static boolean a(KeyEvent keyEvent) {
        return a.a(keyEvent.getMetaState());
    }

    public static boolean b(KeyEvent keyEvent) {
        return a.b(keyEvent.getMetaState());
    }
}
