package com.google.analytics.tracking.android;

import android.content.Context;
import com.google.analytics.tracking.android.AnalyticsThread;
import com.google.analytics.tracking.android.GAUsage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GoogleAnalytics implements TrackerHandler {
    private static GoogleAnalytics j;
    private boolean a;
    private AnalyticsThread b;
    private Context c;
    private Tracker d;
    private AdHitIdGenerator e;
    /* access modifiers changed from: private */
    public volatile String f;
    /* access modifiers changed from: private */
    public volatile Boolean g;
    private final Map h;
    private String i;

    public interface AppOptOutCallback {
        void a(boolean z);
    }

    GoogleAnalytics() {
        this.h = new HashMap();
    }

    private GoogleAnalytics(Context context) {
        this(context, GAThread.a(context));
    }

    private GoogleAnalytics(Context context, AnalyticsThread analyticsThread) {
        this.h = new HashMap();
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }
        this.c = context.getApplicationContext();
        this.b = analyticsThread;
        this.e = new AdHitIdGenerator();
        this.b.a((AppOptOutCallback) new AppOptOutCallback() {
            public final void a(boolean z) {
                Boolean unused = GoogleAnalytics.this.g = Boolean.valueOf(z);
            }
        });
        this.b.a((AnalyticsThread.ClientIdCallback) new AnalyticsThread.ClientIdCallback() {
            public final void a(String str) {
                String unused = GoogleAnalytics.this.f = str;
            }
        });
    }

    public static GoogleAnalytics a(Context context) {
        GoogleAnalytics googleAnalytics;
        synchronized (GoogleAnalytics.class) {
            if (j == null) {
                j = new GoogleAnalytics(context);
            }
            googleAnalytics = j;
        }
        return googleAnalytics;
    }

    public final Tracker a(String str) {
        Tracker tracker;
        synchronized (this) {
            if (str == null) {
                throw new IllegalArgumentException("trackingId cannot be null");
            }
            tracker = (Tracker) this.h.get(str);
            if (tracker == null) {
                tracker = new Tracker(str, this);
                this.h.put(str, tracker);
                if (this.d == null) {
                    this.d = tracker;
                }
            }
            GAUsage.a().a(GAUsage.Field.GET_TRACKER);
        }
        return tracker;
    }

    public final void a(AppOptOutCallback appOptOutCallback) {
        GAUsage.a().a(GAUsage.Field.REQUEST_APP_OPT_OUT);
        if (this.g != null) {
            appOptOutCallback.a(this.g.booleanValue());
        } else {
            this.b.a(appOptOutCallback);
        }
    }

    public final void a(Map map) {
        synchronized (this) {
            if (map == null) {
                throw new IllegalArgumentException("hit cannot be null");
            }
            map.put("language", Utils.a(Locale.getDefault()));
            map.put("adSenseAdMobHitId", Integer.toString(this.e.a()));
            map.put("screenResolution", this.c.getResources().getDisplayMetrics().widthPixels + "x" + this.c.getResources().getDisplayMetrics().heightPixels);
            map.put("usage", GAUsage.a().c());
            GAUsage.a().b();
            this.b.a(map);
            this.i = (String) map.get("trackingId");
        }
    }

    public final void a(boolean z) {
        GAUsage.a().a(GAUsage.Field.SET_DEBUG);
        this.a = z;
        Log.a(z);
    }

    public final void b(boolean z) {
        GAUsage.a().a(GAUsage.Field.SET_APP_OPT_OUT);
        this.g = Boolean.valueOf(z);
        this.b.a(z);
    }
}
