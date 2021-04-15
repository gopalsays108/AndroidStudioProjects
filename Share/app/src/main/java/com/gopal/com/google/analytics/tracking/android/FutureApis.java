package com.google.analytics.tracking.android;

import android.os.Build;
import java.io.File;

class FutureApis {
    private FutureApis() {
    }

    private static int a() {
        try {
            return Integer.parseInt(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            Log.b("Invalid version number: " + Build.VERSION.SDK);
            return 0;
        }
    }

    static boolean a(String str) {
        if (a() < 9) {
            return false;
        }
        File file = new File(str);
        file.setReadable(false, false);
        file.setWritable(false, false);
        file.setReadable(true, true);
        file.setWritable(true, true);
        return true;
    }
}
