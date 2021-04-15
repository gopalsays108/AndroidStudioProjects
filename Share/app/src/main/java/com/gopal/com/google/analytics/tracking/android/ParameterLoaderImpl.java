package com.google.analytics.tracking.android;

import android.content.Context;
import android.text.TextUtils;

class ParameterLoaderImpl implements ParameterLoader {
    private final Context a;

    public ParameterLoaderImpl(Context context) {
        if (context == null) {
            throw new NullPointerException("Context cannot be null");
        }
        this.a = context.getApplicationContext();
    }

    private int a(String str, String str2) {
        if (this.a == null) {
            return 0;
        }
        return this.a.getResources().getIdentifier(str, str2, this.a.getPackageName());
    }

    public final int a(String str, int i) {
        int a2 = a(str, "integer");
        if (a2 == 0) {
            return i;
        }
        try {
            return Integer.parseInt(this.a.getString(a2));
        } catch (NumberFormatException e) {
            Log.f("NumberFormatException parsing " + this.a.getString(a2));
            return i;
        }
    }

    public final String a(String str) {
        int a2 = a(str, "string");
        if (a2 == 0) {
            return null;
        }
        return this.a.getString(a2);
    }

    public final Double b(String str) {
        String a2 = a(str);
        if (TextUtils.isEmpty(a2)) {
            return null;
        }
        try {
            return Double.valueOf(Double.parseDouble(a2));
        } catch (NumberFormatException e) {
            Log.f("NumberFormatException parsing " + a2);
            return null;
        }
    }

    public final boolean c(String str) {
        int a2 = a(str, "bool");
        if (a2 == 0) {
            return false;
        }
        return "true".equalsIgnoreCase(this.a.getString(a2));
    }
}
