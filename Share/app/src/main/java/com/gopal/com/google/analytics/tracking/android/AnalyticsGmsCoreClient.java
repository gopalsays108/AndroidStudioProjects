package com.google.analytics.tracking.android;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.analytics.internal.IAnalyticsService;
import java.util.List;
import java.util.Map;

class AnalyticsGmsCoreClient implements com.google.analytics.tracking.android.AnalyticsClient {
    /* access modifiers changed from: private */
    public ServiceConnection a;
    /* access modifiers changed from: private */
    public OnConnectedListener b;
    /* access modifiers changed from: private */
    public OnConnectionFailedListener c;
    /* access modifiers changed from: private */
    public Context d;
    /* access modifiers changed from: private */
    public IAnalyticsService e;

    final class AnalyticsServiceConnection implements ServiceConnection {
        AnalyticsServiceConnection() {
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            com.google.analytics.tracking.android.Log.a("service connected, binder: " + iBinder);
            try {
                if ("com.google.android.gms.analytics.internal.IAnalyticsService".equals(iBinder.getInterfaceDescriptor())) {
                    com.google.analytics.tracking.android.Log.a("bound to service");
                    IAnalyticsService unused = AnalyticsGmsCoreClient.this.e = IAnalyticsService.Stub.a(iBinder);
                    AnalyticsGmsCoreClient.this.b.a();
                    return;
                }
            } catch (RemoteException e) {
            }
            AnalyticsGmsCoreClient.this.d.unbindService(this);
            ServiceConnection unused2 = AnalyticsGmsCoreClient.this.a = null;
            AnalyticsGmsCoreClient.this.c.a(2);
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            com.google.analytics.tracking.android.Log.a("service disconnected: " + componentName);
            ServiceConnection unused = AnalyticsGmsCoreClient.this.a = null;
            AnalyticsGmsCoreClient.this.b.b();
        }
    }

    public interface OnConnectedListener {
        void a();

        void b();
    }

    public interface OnConnectionFailedListener {
        void a(int i);
    }

    public AnalyticsGmsCoreClient(Context context, OnConnectedListener onConnectedListener, OnConnectionFailedListener onConnectionFailedListener) {
        this.d = context;
        if (onConnectedListener == null) {
            throw new IllegalArgumentException("onConnectedListener cannot be null");
        }
        this.b = onConnectedListener;
        if (onConnectionFailedListener == null) {
            throw new IllegalArgumentException("onConnectionFailedListener cannot be null");
        }
        this.c = onConnectionFailedListener;
    }

    private IAnalyticsService d() {
        if (this.e != null) {
            return this.e;
        }
        throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
    }

    public final void a() {
        d().a();
    }

    public final void a(Map map, long j, String str, List list) {
        d().a(map, j, str, list);
    }

    public final void b() {
        Intent intent = new Intent("com.google.android.gms.analytics.service.START");
        intent.putExtra("app_package_name", this.d.getPackageName());
        if (this.a != null) {
            com.google.analytics.tracking.android.Log.b("Calling connect() while still connected, missing disconnect().");
            return;
        }
        this.a = new AnalyticsServiceConnection();
        boolean bindService = this.d.bindService(intent, this.a, 129);
        com.google.analytics.tracking.android.Log.d("connect: bindService returned " + bindService + " for " + intent);
        if (!bindService) {
            this.a = null;
            this.c.a(1);
        }
    }

    public final void c() {
        this.e = null;
        if (this.a != null) {
            try {
                this.d.unbindService(this.a);
            } catch (IllegalArgumentException | IllegalStateException e2) {
            }
            this.a = null;
            this.b.b();
        }
    }
}
