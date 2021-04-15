package com.smarterdroid.wftlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

@TargetApi(7)
public abstract class ae {
    public static final ae e = (Build.VERSION.SDK_INT < 8 ? new s() : new t());

    public abstract String a(Context context, String str);
}
