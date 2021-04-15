package com.google.analytics.tracking.android;

import android.text.TextUtils;
import java.util.List;

class NoopDispatcher implements Dispatcher {
    NoopDispatcher() {
    }

    public final int a(List list) {
        if (list == null) {
            return 0;
        }
        Log.d("Hits not actually being sent as dispatch is false...");
        int min = Math.min(list.size(), 40);
        for (int i = 0; i < min; i++) {
            if (Log.a()) {
                String a = TextUtils.isEmpty(((Hit) list.get(i)).a()) ? "" : HitBuilder.a((Hit) list.get(i), System.currentTimeMillis());
                Log.d((TextUtils.isEmpty(a) ? "Hit couldn't be read, wouldn't be sent:" : a.length() <= 2036 ? "GET would be sent:" : a.length() > 8192 ? "Would be too big:" : "POST would be sent:") + a);
            }
        }
        return min;
    }

    public final boolean a() {
        return true;
    }
}
