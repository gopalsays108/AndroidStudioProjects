package android.support.v4.view;

import android.os.Build;
import android.view.View;

public final class n {
    static final u a;

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 17) {
            a = new t();
        } else if (i >= 16) {
            a = new s();
        } else if (i >= 14) {
            a = new r();
        } else if (i >= 11) {
            a = new q();
        } else if (i >= 9) {
            a = new p();
        } else {
            a = new o();
        }
    }

    public static int a(View view) {
        return a.a(view);
    }

    public static void a(View view, Runnable runnable) {
        a.a(view, runnable);
    }

    public static boolean a(View view, int i) {
        return a.a(view, i);
    }

    public static void b(View view) {
        a.b(view);
    }

    public static void b(View view, int i) {
        a.b(view, i);
    }
}
