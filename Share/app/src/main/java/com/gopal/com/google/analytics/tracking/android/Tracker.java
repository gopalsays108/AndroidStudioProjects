package com.google.analytics.tracking.android;

import android.text.TextUtils;
import com.google.analytics.tracking.android.GAUsage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Tracker {
    private static final DecimalFormat a = new DecimalFormat("0.######", new DecimalFormatSymbols(Locale.US));
    private final com.google.analytics.tracking.android.TrackerHandler b;
    private final SimpleModel c;
    private volatile boolean d;
    private volatile boolean e;
    private long f;
    private long g;
    private boolean h;

    class SimpleModel {
        private Map a;
        private Map b;

        private SimpleModel() {
            this.a = new HashMap();
            this.b = new HashMap();
        }

        /* synthetic */ SimpleModel(byte b2) {
            this();
        }

        public final synchronized void a() {
            this.a.clear();
        }

        public final synchronized void a(String str, String str2) {
            this.a.put(str, str2);
        }

        public final synchronized void a(Map map, Boolean bool) {
            if (bool.booleanValue()) {
                this.a.putAll(map);
            } else {
                this.b.putAll(map);
            }
        }

        public final synchronized Map b() {
            HashMap hashMap;
            hashMap = new HashMap(this.b);
            hashMap.putAll(this.a);
            return hashMap;
        }

        public final synchronized void b(String str, String str2) {
            this.b.put(str, str2);
        }
    }

    Tracker() {
        this.d = false;
        this.e = false;
        this.f = 120000;
        this.h = true;
        this.b = null;
        this.c = null;
    }

    Tracker(String str, com.google.analytics.tracking.android.TrackerHandler trackerHandler) {
        this.d = false;
        this.e = false;
        this.f = 120000;
        this.h = true;
        if (str == null) {
            throw new IllegalArgumentException("trackingId cannot be null");
        }
        this.b = trackerHandler;
        this.c = new SimpleModel((byte) 0);
        this.c.b("trackingId", str);
        this.c.b("sampleRate", "100");
        this.c.a("sessionControl", "start");
        this.c.b("useSecure", Boolean.toString(true));
    }

    private void a(String str, Map map) {
        this.e = true;
        if (map == null) {
            map = new HashMap();
        }
        map.put("hitType", str);
        this.c.a(map, (Boolean) true);
        if (!c()) {
            com.google.analytics.tracking.android.Log.g("Too many hits sent too quickly, throttling invoked.");
        } else {
            this.b.a(this.c.b());
        }
        this.c.a();
    }

    private void b() {
        if (this.d) {
            throw new IllegalStateException("Tracker closed");
        }
    }

    private synchronized boolean c() {
        boolean z = true;
        synchronized (this) {
            if (this.h) {
                long currentTimeMillis = System.currentTimeMillis();
                if (this.f < 120000) {
                    long j = currentTimeMillis - this.g;
                    if (j > 0) {
                        this.f = Math.min(120000, j + this.f);
                    }
                }
                this.g = currentTimeMillis;
                if (this.f >= 2000) {
                    this.f -= 2000;
                } else {
                    com.google.analytics.tracking.android.Log.g("Excessive tracking detected.  Tracking call ignored.");
                    z = false;
                }
            }
        }
        return z;
    }

    public void a() {
        b();
        GAUsage.a().a(GAUsage.Field.SET_START_SESSION);
        this.c.a("sessionControl", "start");
    }

    public void a(double d2) {
        GAUsage.a().a(GAUsage.Field.SET_SAMPLE_RATE);
        this.c.b("sampleRate", Double.toString(d2));
    }

    public void a(String str) {
        if (this.e) {
            com.google.analytics.tracking.android.Log.g("Tracking already started, setAppName call ignored");
        } else if (TextUtils.isEmpty(str)) {
            com.google.analytics.tracking.android.Log.g("setting appName to empty value not allowed, call ignored");
        } else {
            GAUsage.a().a(GAUsage.Field.SET_APP_NAME);
            this.c.b("appName", str);
        }
    }

    public void a(boolean z) {
        GAUsage.a().a(GAUsage.Field.SET_ANONYMIZE_IP);
        this.c.b("anonymizeIp", Boolean.toString(z));
    }

    public void b(String str) {
        if (this.e) {
            com.google.analytics.tracking.android.Log.g("Tracking already started, setAppVersion call ignored");
            return;
        }
        GAUsage.a().a(GAUsage.Field.SET_APP_VERSION);
        this.c.b("appVersion", str);
    }

    public void c(String str) {
        b();
        if (TextUtils.isEmpty(str)) {
            throw new IllegalStateException("trackView requires a appScreen to be set");
        }
        GAUsage.a().a(GAUsage.Field.TRACK_VIEW_WITH_APPSCREEN);
        this.c.b("description", str);
        a("appview", (Map) null);
    }

    public void d(String str) {
        b();
        GAUsage.a().a(GAUsage.Field.TRACK_EXCEPTION_WITH_DESCRIPTION);
        GAUsage.a().a(true);
        a("exception", e(str));
        GAUsage.a().a(false);
    }

    public Map e(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("exDescription", str);
        hashMap.put("exFatal", Boolean.toString(true));
        GAUsage.a().a(GAUsage.Field.CONSTRUCT_EXCEPTION);
        return hashMap;
    }

    @Deprecated
    public final void f(String str) {
        c(str);
    }
}
