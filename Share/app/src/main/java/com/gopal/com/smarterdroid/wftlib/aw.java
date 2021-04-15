package com.smarterdroid.wftlib;

import android.content.Context;
import com.google.analytics.tracking.android.EasyTracker;

public final class aw {
    public static void a(Context context, String str) {
        try {
            EasyTracker.a().a(context);
            EasyTracker.b().f(str);
        } catch (Exception e) {
        }
    }
}
