package com.google.analytics.tracking.android;

import android.content.Context;
import com.google.analytics.tracking.android.AnalyticsGmsCoreClient;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

class GAServiceProxy implements AnalyticsGmsCoreClient.OnConnectedListener, AnalyticsGmsCoreClient.OnConnectionFailedListener, ServiceProxy {
    /* access modifiers changed from: private */
    public volatile long a;
    /* access modifiers changed from: private */
    public volatile ConnectState b;
    private volatile AnalyticsClient c;
    private AnalyticsStore d;
    private AnalyticsStore e;
    private final AnalyticsThread f;
    private final Context g;
    /* access modifiers changed from: private */
    public final Queue h;
    private volatile int i;
    private volatile Timer j;
    private volatile Timer k;
    /* access modifiers changed from: private */
    public volatile Timer l;
    private boolean m;
    private boolean n;
    /* access modifiers changed from: private */
    public Clock o;
    /* access modifiers changed from: private */
    public long p;

    enum ConnectState {
        CONNECTING,
        CONNECTED_SERVICE,
        CONNECTED_LOCAL,
        BLOCKED,
        PENDING_CONNECTION,
        PENDING_DISCONNECT,
        DISCONNECTED
    }

    class DisconnectCheckTask extends TimerTask {
        private DisconnectCheckTask() {
        }

        /* synthetic */ DisconnectCheckTask(GAServiceProxy gAServiceProxy, byte b) {
            this();
        }

        public void run() {
            if (GAServiceProxy.this.b != ConnectState.CONNECTED_SERVICE || !GAServiceProxy.this.h.isEmpty() || GAServiceProxy.this.a + GAServiceProxy.this.p >= GAServiceProxy.this.o.a()) {
                GAServiceProxy.this.l.schedule(new DisconnectCheckTask(), GAServiceProxy.this.p);
                return;
            }
            Log.d("Disconnecting due to inactivity");
            GAServiceProxy.this.k();
        }
    }

    class FailedConnectTask extends TimerTask {
        private FailedConnectTask() {
        }

        /* synthetic */ FailedConnectTask(GAServiceProxy gAServiceProxy, byte b) {
            this();
        }

        public void run() {
            if (GAServiceProxy.this.b == ConnectState.CONNECTING) {
                GAServiceProxy.this.i();
            }
        }
    }

    class HitParams {
        private final Map a;
        private final long b;
        private final String c;
        private final List d;

        public HitParams(Map map, long j, String str, List list) {
            this.a = map;
            this.b = j;
            this.c = str;
            this.d = list;
        }

        public final Map a() {
            return this.a;
        }

        public final long b() {
            return this.b;
        }

        public final String c() {
            return this.c;
        }

        public final List d() {
            return this.d;
        }
    }

    class ReconnectTask extends TimerTask {
        private ReconnectTask() {
        }

        /* synthetic */ ReconnectTask(GAServiceProxy gAServiceProxy, byte b) {
            this();
        }

        public void run() {
            GAServiceProxy.this.j();
        }
    }

    private GAServiceProxy(Context context, AnalyticsThread analyticsThread) {
        this.h = new ConcurrentLinkedQueue();
        this.p = 300000;
        this.e = null;
        this.g = context;
        this.f = analyticsThread;
        this.o = new Clock() {
            public final long a() {
                return System.currentTimeMillis();
            }
        };
        this.i = 0;
        this.b = ConnectState.DISCONNECTED;
    }

    GAServiceProxy(Context context, AnalyticsThread analyticsThread, byte b2) {
        this(context, analyticsThread);
    }

    private static Timer a(Timer timer) {
        if (timer == null) {
            return null;
        }
        timer.cancel();
        return null;
    }

    private void f() {
        this.j = a(this.j);
        this.k = a(this.k);
        this.l = a(this.l);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x003c, code lost:
        if (r7.h.isEmpty() != false) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003e, code lost:
        r6 = (com.google.analytics.tracking.android.GAServiceProxy.HitParams) r7.h.poll();
        com.google.analytics.tracking.android.Log.d("Sending hit to store");
        r7.d.a(r6.a(), r6.b(), r6.c(), r6.d());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0068, code lost:
        if (r7.m == false) goto L_0x001f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006a, code lost:
        h();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00a0, code lost:
        r7.a = r7.o.a();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void g() {
        /*
            r7 = this;
            monitor-enter(r7)
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0063 }
            com.google.analytics.tracking.android.AnalyticsThread r2 = r7.f     // Catch:{ all -> 0x0063 }
            java.lang.Thread r2 = r2.c()     // Catch:{ all -> 0x0063 }
            boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0063 }
            if (r1 != 0) goto L_0x0021
            com.google.analytics.tracking.android.AnalyticsThread r1 = r7.f     // Catch:{ all -> 0x0063 }
            java.util.concurrent.LinkedBlockingQueue r1 = r1.b()     // Catch:{ all -> 0x0063 }
            com.google.analytics.tracking.android.GAServiceProxy$2 r2 = new com.google.analytics.tracking.android.GAServiceProxy$2     // Catch:{ all -> 0x0063 }
            r2.<init>()     // Catch:{ all -> 0x0063 }
            r1.add(r2)     // Catch:{ all -> 0x0063 }
        L_0x001f:
            monitor-exit(r7)
            return
        L_0x0021:
            boolean r1 = r7.n     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x0028
            r7.d()     // Catch:{ all -> 0x0063 }
        L_0x0028:
            int[] r1 = com.google.analytics.tracking.android.GAServiceProxy.AnonymousClass3.a     // Catch:{ all -> 0x0063 }
            com.google.analytics.tracking.android.GAServiceProxy$ConnectState r2 = r7.b     // Catch:{ all -> 0x0063 }
            int r2 = r2.ordinal()     // Catch:{ all -> 0x0063 }
            r1 = r1[r2]     // Catch:{ all -> 0x0063 }
            switch(r1) {
                case 1: goto L_0x0036;
                case 2: goto L_0x006e;
                case 3: goto L_0x00aa;
                default: goto L_0x0035;
            }     // Catch:{ all -> 0x0063 }
        L_0x0035:
            goto L_0x001f
        L_0x0036:
            java.util.Queue r1 = r7.h     // Catch:{ all -> 0x0063 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0063 }
            if (r1 != 0) goto L_0x0066
            java.util.Queue r1 = r7.h     // Catch:{ all -> 0x0063 }
            java.lang.Object r1 = r1.poll()     // Catch:{ all -> 0x0063 }
            r0 = r1
            com.google.analytics.tracking.android.GAServiceProxy$HitParams r0 = (com.google.analytics.tracking.android.GAServiceProxy.HitParams) r0     // Catch:{ all -> 0x0063 }
            r6 = r0
            java.lang.String r1 = "Sending hit to store"
            com.google.analytics.tracking.android.Log.d(r1)     // Catch:{ all -> 0x0063 }
            com.google.analytics.tracking.android.AnalyticsStore r1 = r7.d     // Catch:{ all -> 0x0063 }
            java.util.Map r2 = r6.a()     // Catch:{ all -> 0x0063 }
            long r3 = r6.b()     // Catch:{ all -> 0x0063 }
            java.lang.String r5 = r6.c()     // Catch:{ all -> 0x0063 }
            java.util.List r6 = r6.d()     // Catch:{ all -> 0x0063 }
            r1.a(r2, r3, r5, r6)     // Catch:{ all -> 0x0063 }
            goto L_0x0036
        L_0x0063:
            r1 = move-exception
            monitor-exit(r7)
            throw r1
        L_0x0066:
            boolean r1 = r7.m     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x001f
            r7.h()     // Catch:{ all -> 0x0063 }
            goto L_0x001f
        L_0x006e:
            java.util.Queue r1 = r7.h     // Catch:{ all -> 0x0063 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0063 }
            if (r1 != 0) goto L_0x00a0
            java.util.Queue r1 = r7.h     // Catch:{ all -> 0x0063 }
            java.lang.Object r1 = r1.peek()     // Catch:{ all -> 0x0063 }
            r0 = r1
            com.google.analytics.tracking.android.GAServiceProxy$HitParams r0 = (com.google.analytics.tracking.android.GAServiceProxy.HitParams) r0     // Catch:{ all -> 0x0063 }
            r6 = r0
            java.lang.String r1 = "Sending hit to service"
            com.google.analytics.tracking.android.Log.d(r1)     // Catch:{ all -> 0x0063 }
            com.google.analytics.tracking.android.AnalyticsClient r1 = r7.c     // Catch:{ all -> 0x0063 }
            java.util.Map r2 = r6.a()     // Catch:{ all -> 0x0063 }
            long r3 = r6.b()     // Catch:{ all -> 0x0063 }
            java.lang.String r5 = r6.c()     // Catch:{ all -> 0x0063 }
            java.util.List r6 = r6.d()     // Catch:{ all -> 0x0063 }
            r1.a(r2, r3, r5, r6)     // Catch:{ all -> 0x0063 }
            java.util.Queue r1 = r7.h     // Catch:{ all -> 0x0063 }
            r1.poll()     // Catch:{ all -> 0x0063 }
            goto L_0x006e
        L_0x00a0:
            com.google.analytics.tracking.android.Clock r1 = r7.o     // Catch:{ all -> 0x0063 }
            long r1 = r1.a()     // Catch:{ all -> 0x0063 }
            r7.a = r1     // Catch:{ all -> 0x0063 }
            goto L_0x001f
        L_0x00aa:
            java.lang.String r1 = "Need to reconnect"
            com.google.analytics.tracking.android.Log.d(r1)     // Catch:{ all -> 0x0063 }
            java.util.Queue r1 = r7.h     // Catch:{ all -> 0x0063 }
            boolean r1 = r1.isEmpty()     // Catch:{ all -> 0x0063 }
            if (r1 != 0) goto L_0x001f
            r7.j()     // Catch:{ all -> 0x0063 }
            goto L_0x001f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.analytics.tracking.android.GAServiceProxy.g():void");
    }

    private void h() {
        this.d.b();
        this.m = false;
    }

    /* access modifiers changed from: private */
    public synchronized void i() {
        if (this.b != ConnectState.CONNECTED_LOCAL) {
            f();
            Log.d("falling back to local store");
            if (this.e != null) {
                this.d = this.e;
            } else {
                GAServiceManager a2 = GAServiceManager.a();
                a2.a(this.g, this.f);
                this.d = a2.b();
            }
            this.b = ConnectState.CONNECTED_LOCAL;
            g();
        }
    }

    /* access modifiers changed from: private */
    public synchronized void j() {
        if (this.c == null || this.b == ConnectState.CONNECTED_LOCAL) {
            Log.f("client not initialized.");
            i();
        } else {
            try {
                this.i++;
                a(this.k);
                this.b = ConnectState.CONNECTING;
                this.k = new Timer("Failed Connect");
                this.k.schedule(new FailedConnectTask(this, (byte) 0), 3000);
                Log.d("connecting to Analytics service");
                this.c.b();
            } catch (SecurityException e2) {
                Log.f("security exception on connectToService");
                i();
            }
        }
        return;
    }

    /* access modifiers changed from: private */
    public synchronized void k() {
        if (this.c != null && this.b == ConnectState.CONNECTED_SERVICE) {
            this.b = ConnectState.PENDING_DISCONNECT;
            this.c.c();
        }
    }

    private void l() {
        this.j = a(this.j);
        this.j = new Timer("Service Reconnect");
        this.j.schedule(new ReconnectTask(this, (byte) 0), 5000);
    }

    public final synchronized void a() {
        this.k = a(this.k);
        this.i = 0;
        Log.d("Connected to service");
        this.b = ConnectState.CONNECTED_SERVICE;
        g();
        this.l = a(this.l);
        this.l = new Timer("disconnect check");
        this.l.schedule(new DisconnectCheckTask(this, (byte) 0), this.p);
    }

    public final synchronized void a(int i2) {
        this.b = ConnectState.PENDING_CONNECTION;
        if (this.i < 2) {
            Log.f("Service unavailable (code=" + i2 + "), will retry.");
            l();
        } else {
            Log.f("Service unavailable (code=" + i2 + "), using local store.");
            i();
        }
    }

    public final void a(Map map, long j2, String str, List list) {
        Log.d("putHit called");
        this.h.add(new HitParams(map, j2, str, list));
        g();
    }

    public final synchronized void b() {
        if (this.b == ConnectState.PENDING_DISCONNECT) {
            Log.d("Disconnected from service");
            f();
            this.b = ConnectState.DISCONNECTED;
        } else {
            Log.d("Unexpected disconnect.");
            this.b = ConnectState.PENDING_CONNECTION;
            if (this.i < 2) {
                l();
            } else {
                i();
            }
        }
    }

    public final void c() {
        switch (this.b) {
            case CONNECTED_LOCAL:
                h();
                return;
            case CONNECTED_SERVICE:
                return;
            default:
                this.m = true;
                return;
        }
    }

    public final void d() {
        Log.d("clearHits called");
        this.h.clear();
        switch (this.b) {
            case CONNECTED_LOCAL:
                this.d.a();
                this.n = false;
                return;
            case CONNECTED_SERVICE:
                this.c.a();
                this.n = false;
                return;
            default:
                this.n = true;
                return;
        }
    }

    public final void e() {
        if (this.c == null) {
            this.c = new AnalyticsGmsCoreClient(this.g, this, this);
            j();
        }
    }
}
