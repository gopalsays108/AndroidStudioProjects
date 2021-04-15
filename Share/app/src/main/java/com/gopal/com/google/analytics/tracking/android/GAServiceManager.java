package com.google.analytics.tracking.android;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.google.analytics.tracking.android.GAUsage;

public class GAServiceManager implements ServiceManager {
    /* access modifiers changed from: private */
    public static final Object a = new Object();
    private static GAServiceManager m;
    private Context b;
    private AnalyticsStore c;
    private volatile AnalyticsThread d;
    /* access modifiers changed from: private */
    public int e = 1800;
    private boolean f = true;
    /* access modifiers changed from: private */
    public boolean g = true;
    private boolean h = true;
    private AnalyticsStoreStateListener i = new AnalyticsStoreStateListener() {
        public final void a(boolean z) {
            GAServiceManager.this.a(z, GAServiceManager.this.g);
        }
    };
    /* access modifiers changed from: private */
    public Handler j;
    private GANetworkReceiver k;
    /* access modifiers changed from: private */
    public boolean l = false;

    private GAServiceManager() {
    }

    public static GAServiceManager a() {
        if (m == null) {
            m = new GAServiceManager();
        }
        return m;
    }

    public final synchronized void a(int i2) {
        if (this.j == null) {
            Log.f("Need to call initialize() and be in fallback mode to start dispatch.");
            this.e = i2;
        } else {
            GAUsage.a().a(GAUsage.Field.SET_DISPATCH_PERIOD);
            if (!this.l && this.g && this.e > 0) {
                this.j.removeMessages(1, a);
            }
            this.e = i2;
            if (i2 > 0 && !this.l && this.g) {
                this.j.sendMessageDelayed(this.j.obtainMessage(1, a), (long) (i2 * 1000));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final synchronized void a(Context context, AnalyticsThread analyticsThread) {
        if (this.b == null) {
            this.b = context.getApplicationContext();
            if (this.d == null) {
                this.d = analyticsThread;
                if (this.f) {
                    analyticsThread.a();
                }
            }
        }
    }

    public final synchronized void a(boolean z) {
        a(this.l, z);
    }

    /* access modifiers changed from: package-private */
    public final synchronized void a(boolean z, boolean z2) {
        if (!(this.l == z && this.g == z2)) {
            if (z || !z2) {
                if (this.e > 0) {
                    this.j.removeMessages(1, a);
                }
            }
            if (!z && z2 && this.e > 0) {
                this.j.sendMessageDelayed(this.j.obtainMessage(1, a), (long) (this.e * 1000));
            }
            Log.d("PowerSaveMode " + ((z || !z2) ? "initiated." : "terminated."));
            this.l = z;
            this.g = z2;
        }
    }

    /* access modifiers changed from: package-private */
    public final synchronized AnalyticsStore b() {
        if (this.c == null) {
            if (this.b == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.c = new PersistentAnalyticsStore(this.i, this.b);
        }
        if (this.j == null) {
            this.j = new Handler(this.b.getMainLooper(), new Handler.Callback() {
                public boolean handleMessage(Message message) {
                    if (1 == message.what && GAServiceManager.a.equals(message.obj)) {
                        GAUsage.a().a(true);
                        GAServiceManager.this.c();
                        GAUsage.a().a(false);
                        if (GAServiceManager.this.e > 0 && !GAServiceManager.this.l) {
                            GAServiceManager.this.j.sendMessageDelayed(GAServiceManager.this.j.obtainMessage(1, GAServiceManager.a), (long) (GAServiceManager.this.e * 1000));
                        }
                    }
                    return true;
                }
            });
            if (this.e > 0) {
                this.j.sendMessageDelayed(this.j.obtainMessage(1, a), (long) (this.e * 1000));
            }
        }
        if (this.k == null && this.h) {
            this.k = new GANetworkReceiver(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.b.registerReceiver(this.k, intentFilter);
        }
        return this.c;
    }

    public final synchronized void c() {
        if (this.d == null) {
            Log.f("dispatch call queued.  Need to call GAServiceManager.getInstance().initialize().");
            this.f = true;
        } else {
            GAUsage.a().a(GAUsage.Field.DISPATCH);
            this.d.a();
        }
    }
}
