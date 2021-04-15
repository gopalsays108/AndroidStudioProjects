package com.google.analytics.tracking.android;

public class Log {
    private static boolean a;

    private Log() {
    }

    public static int a(String str) {
        if (a) {
            return android.util.Log.d("GAV2", h(str));
        }
        return 0;
    }

    public static void a(boolean z) {
        a = z;
    }

    public static boolean a() {
        return a;
    }

    public static int b(String str) {
        return android.util.Log.e("GAV2", h(str));
    }

    public static int c(String str) {
        return android.util.Log.i("GAV2", h(str));
    }

    public static int d(String str) {
        if (a) {
            return c(str);
        }
        return 0;
    }

    public static int e(String str) {
        if (a) {
            return android.util.Log.v("GAV2", h(str));
        }
        return 0;
    }

    public static int f(String str) {
        return android.util.Log.w("GAV2", h(str));
    }

    public static int g(String str) {
        if (a) {
            return f(str);
        }
        return 0;
    }

    private static String h(String str) {
        return Thread.currentThread().toString() + ": " + str;
    }
}
