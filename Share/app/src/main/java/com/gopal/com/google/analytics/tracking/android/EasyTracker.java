package com.google.analytics.tracking.android;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import java.lang.Thread;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class EasyTracker {
    private static EasyTracker a;
    private boolean b = false;
    private String c;
    private String d;
    private String e;
    private int f = 1800;
    private boolean g;
    private Double h;
    private boolean i;
    private boolean j;
    private Thread.UncaughtExceptionHandler k;
    private boolean l = false;
    private int m = 0;
    private long n;
    private long o;
    private Context p;
    private final Map q = new HashMap();
    private Tracker r = null;
    private ParameterLoader s;
    private GoogleAnalytics t;
    private ServiceManager u;
    private Clock v = new Clock() {
        public final long a() {
            return System.currentTimeMillis();
        }
    };
    private Timer w;
    private TimerTask x;
    /* access modifiers changed from: private */
    public boolean y = false;

    class NoopTracker extends Tracker {
        private double b = 100.0d;
        private boolean c;

        NoopTracker() {
        }

        public final void a() {
        }

        public final void a(double d) {
            this.b = d;
        }

        public final void a(String str) {
        }

        public final void a(boolean z) {
            this.c = z;
        }

        public final void b(String str) {
        }

        public final void c(String str) {
        }

        public final void d(String str) {
        }

        public final Map e(String str) {
            return new HashMap();
        }
    }

    class NotInForegroundTimerTask extends TimerTask {
        private NotInForegroundTimerTask() {
        }

        /* synthetic */ NotInForegroundTimerTask(EasyTracker easyTracker, byte b) {
            this();
        }

        public void run() {
            boolean unused = EasyTracker.this.y = false;
        }
    }

    private EasyTracker() {
    }

    public static EasyTracker a() {
        if (a == null) {
            a = new EasyTracker();
        }
        return a;
    }

    public static Tracker b() {
        if (a().p != null) {
            return a().r;
        }
        throw new IllegalStateException("You must call EasyTracker.getInstance().setContext(context) or startActivity(activity) before calling getTracker()");
    }

    private synchronized void d() {
        if (this.w != null) {
            this.w.cancel();
            this.w = null;
        }
    }

    public final void a(Activity activity) {
        String a2;
        a((Context) activity);
        if (this.b) {
            d();
            if (!this.y && this.m == 0) {
                if (this.n == 0 || (this.n > 0 && this.v.a() > this.o + this.n)) {
                    this.r.a();
                    boolean z = this.l;
                }
            }
            this.y = true;
            this.m++;
            if (this.l) {
                Tracker tracker = this.r;
                String canonicalName = activity.getClass().getCanonicalName();
                if (this.q.containsKey(canonicalName)) {
                    a2 = (String) this.q.get(canonicalName);
                } else {
                    a2 = this.s.a(canonicalName);
                    if (a2 == null) {
                        a2 = canonicalName;
                    }
                    this.q.put(canonicalName, a2);
                }
                tracker.c(a2);
            }
        }
    }

    public final void a(Context context) {
        boolean z = true;
        if (context == null) {
            Log.b("Context cannot be null");
            return;
        }
        GAServiceManager a2 = GAServiceManager.a();
        ParameterLoaderImpl parameterLoaderImpl = new ParameterLoaderImpl(context.getApplicationContext());
        GoogleAnalytics a3 = GoogleAnalytics.a(context.getApplicationContext());
        if (context == null) {
            Log.b("Context cannot be null");
        }
        if (this.p == null) {
            this.p = context.getApplicationContext();
            this.t = a3;
            this.u = a2;
            this.s = parameterLoaderImpl;
            this.c = this.s.a("ga_trackingId");
            if (TextUtils.isEmpty(this.c)) {
                this.c = this.s.a("ga_api_key");
                if (TextUtils.isEmpty(this.c)) {
                    Log.b("EasyTracker requested, but missing required ga_trackingId");
                    this.r = new NoopTracker();
                    return;
                }
            }
            this.b = true;
            this.d = this.s.a("ga_appName");
            this.e = this.s.a("ga_appVersion");
            this.g = this.s.c("ga_debug");
            this.h = this.s.b("ga_sampleFrequency");
            if (this.h == null) {
                this.h = new Double((double) this.s.a("ga_sampleRate", 100));
            }
            this.f = this.s.a("ga_dispatchPeriod", 1800);
            this.n = (long) (this.s.a("ga_sessionTimeout", 30) * 1000);
            if (!this.s.c("ga_autoActivityTracking") && !this.s.c("ga_auto_activity_tracking")) {
                z = false;
            }
            this.l = z;
            this.i = this.s.c("ga_anonymizeIp");
            this.j = this.s.c("ga_reportUncaughtExceptions");
            this.r = this.t.a(this.c);
            if (!TextUtils.isEmpty(this.d)) {
                Log.c("setting appName to " + this.d);
                this.r.a(this.d);
            }
            if (this.e != null) {
                this.r.b(this.e);
            }
            this.r.a(this.i);
            this.r.a(this.h.doubleValue());
            this.t.a(this.g);
            this.u.a(this.f);
            if (this.j) {
                Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.k;
                if (uncaughtExceptionHandler == null) {
                    uncaughtExceptionHandler = new ExceptionReporter(this.r, this.u, Thread.getDefaultUncaughtExceptionHandler(), this.p);
                }
                Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
            }
        }
    }

    public final void b(Activity activity) {
        a((Context) activity);
        if (this.b) {
            this.m--;
            this.m = Math.max(0, this.m);
            this.o = this.v.a();
            if (this.m == 0) {
                d();
                this.x = new NotInForegroundTimerTask(this, (byte) 0);
                this.w = new Timer("waitForActivityStart");
                this.w.schedule(this.x, 1000);
            }
        }
    }

    public final void c() {
        if (this.b) {
            this.u.c();
        }
    }
}
