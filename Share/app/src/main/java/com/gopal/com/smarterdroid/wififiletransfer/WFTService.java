package com.smarterdroid.wififiletransfer;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ac;
import android.support.v4.app.y;
import android.support.v4.app.z;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.smarterdroid.wftlib.a.d;
import com.smarterdroid.wftlib.aa;
import com.smarterdroid.wftlib.ab;
import com.smarterdroid.wftlib.aj;
import com.smarterdroid.wftlib.v;
import java.io.File;

public class WFTService extends IntentService {
    public static int a = 0;
    private static int d;
    private static String e;
    private static String j;
    private static int k = 4007;
    private static boolean l = false;
    private static boolean q = false;
    private static Integer r;
    private c b;
    private c c;
    private boolean f;
    private NotificationManager g;
    private aj h;
    private Context i;
    private BroadcastReceiver m = new ac(this);
    private Intent n;
    private BroadcastReceiver o;
    private BroadcastReceiver p;

    public WFTService() {
        super("WFTService");
        setIntentRedelivery(true);
    }

    public static String a() {
        return j != null ? "http://" + j + ":" + d : "not running";
    }

    private String a(int i2) {
        return getApplicationContext().getString(i2);
    }

    public static String b() {
        return j != null ? "https://" + j + ":" + r : "not running";
    }

    public static boolean c() {
        return q;
    }

    public static int d() {
        return k;
    }

    public static boolean e() {
        return l;
    }

    public static void f() {
        k = 4007;
    }

    public static boolean g() {
        return new File("/system/etc/wft.acr").exists();
    }

    private void h() {
        this.n.putExtra("message", 3001);
        sendBroadcast(this.n);
        Intent intent = new Intent(this.i, WFTWidgetProvider.class);
        intent.setAction("com.smarterdroid.wififiletransferpro.UI_UPDATE");
        this.i.sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void i() {
        String e2 = this.h.e();
        "curIP " + e2 + " current " + j;
        if (!e2.equals("0.0.0.0") && !e2.equals(j) && this.f) {
            j = e2;
            if (this.b != null) {
                this.b.a();
                this.b = null;
            }
            if (this.c != null) {
                this.c.a();
                this.c = null;
            }
            if (this.h.f()) {
                if (this.b == null) {
                    this.b = new c(this.h, false);
                    this.b.a(d);
                    this.b.a(e);
                    this.b.a(this.i);
                    this.b.start();
                }
                if (q && this.c == null) {
                    this.c = new c(this.h, true);
                    this.c.a(r.intValue());
                    this.c.a(e);
                    this.c.a(this.i);
                    this.c.start();
                }
            }
            j();
        } else if (e2.equals("0.0.0.0") && !e2.equals(j)) {
            j = e2;
            j();
        }
    }

    /* access modifiers changed from: private */
    public void j() {
        String a2;
        this.g = (NotificationManager) getSystemService("notification");
        String a3 = a((int) C0000R.string.appname);
        String a4 = a((int) C0000R.string.appname);
        String e2 = this.h.e();
        if (e2.equals("0.0.0.0") || !this.h.f()) {
            a2 = a((int) C0000R.string.nowificonnection);
            k = 4000;
        } else {
            a2 = "http://" + e2 + ":" + d;
            k = 4001;
        }
        if (!this.f) {
            a2 = a((int) C0000R.string.licensecheckfailed);
            k = 4005;
        }
        PendingIntent activity = PendingIntent.getActivity(this, 0, new Intent(this, WFTPanel.class), 0);
        Intent intent = new Intent("com.smarterdroid.wififiletransfer.ACTION_EVENT");
        intent.putExtra("action", 2001);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent, 134217728);
        ac acVar = new ac(this);
        acVar.d = activity;
        acVar.r.when = System.currentTimeMillis();
        acVar.r.tickerText = a3;
        acVar.r.icon = C0000R.drawable.ic_wft2_l;
        acVar.b = a4;
        acVar.c = a2;
        acVar.r.flags |= 2;
        acVar.q.add(new z("Stop", broadcast));
        startForeground(145634, y.a.a(acVar));
        h();
    }

    /* access modifiers changed from: protected */
    public final void a(Intent intent) {
        "processIntent: " + intent.getIntExtra("action", 2000);
        switch (intent.getIntExtra("action", 2000)) {
            case 2001:
                stopSelf();
                return;
            case 2006:
                l = true;
                return;
            default:
                return;
        }
    }

    public void onCreate() {
        super.onCreate();
        aj.a(getApplicationContext());
        aj.b = g();
    }

    public void onDestroy() {
        k = 4004;
        if (a == 1) {
            a = 0;
        }
        if (this.b != null) {
            c cVar = this.b;
            this.b = null;
            cVar.a();
            cVar.interrupt();
        }
        if (this.c != null) {
            c cVar2 = this.c;
            this.c = null;
            cVar2.a();
            cVar2.interrupt();
        }
        if (this.g != null) {
            this.g.cancelAll();
        } else {
            this.g = (NotificationManager) getSystemService("notification");
            this.g.cancelAll();
        }
        try {
            unregisterReceiver(this.p);
        } catch (Exception e2) {
        }
        try {
            unregisterReceiver(this.m);
        } catch (Exception e3) {
        }
        try {
            unregisterReceiver(this.o);
        } catch (Exception e4) {
        }
        ab.c();
        k = 4007;
        l = false;
        h();
        try {
            EasyTracker.a().a(getApplicationContext());
            EasyTracker.a().c();
        } catch (Exception e5) {
        }
        super.onDestroy();
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        int i2 = 0;
        k = 4003;
        "onHandleIntent: " + intent.getIntExtra("action", 2000);
        switch (intent.getIntExtra("action", 2000)) {
            case 2001:
                stopSelf();
                break;
            case 2006:
                l = true;
                break;
        }
        v.a(getApplicationContext());
        v.e();
        aj.a(getApplicationContext());
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e = defaultSharedPreferences.getString("password", "");
        v.a(!defaultSharedPreferences.getBoolean("disableSocialButtons", false));
        q = defaultSharedPreferences.getBoolean("ssl", false);
        boolean z = defaultSharedPreferences.getBoolean("googleAnalytics", false);
        GoogleAnalytics a2 = GoogleAnalytics.a((Context) this);
        a2.a((GoogleAnalytics.AppOptOutCallback) new ad(this));
        a2.b(z);
        try {
            r = Integer.valueOf(defaultSharedPreferences.getString("sslport", "2345"));
        } catch (NumberFormatException e2) {
            r = 2345;
            SharedPreferences.Editor edit = defaultSharedPreferences.edit();
            edit.putString("sslport", String.valueOf(r));
            edit.commit();
        }
        try {
            d = Integer.valueOf(defaultSharedPreferences.getString("port", "1234")).intValue();
        } catch (NumberFormatException e3) {
            d = 1234;
            SharedPreferences.Editor edit2 = defaultSharedPreferences.edit();
            edit2.putString("port", String.valueOf(d));
            edit2.commit();
        }
        if (defaultSharedPreferences.getBoolean("firstRun", true)) {
            SharedPreferences.Editor edit3 = defaultSharedPreferences.edit();
            edit3.putBoolean("redirect", true);
            edit3.putString("port", String.valueOf(d));
            edit3.putBoolean("firstRun", false);
            edit3.commit();
        } else {
            try {
                i2 = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            } catch (PackageManager.NameNotFoundException e4) {
            }
            if (defaultSharedPreferences.getInt("versionCode", i2 - 1) != i2) {
                SharedPreferences.Editor edit4 = defaultSharedPreferences.edit();
                edit4.putInt("versionCode", i2);
                edit4.commit();
            }
        }
        this.n = new Intent("com.smarterdroid.wififiletransfer.MSG_EVENT");
        this.f = true;
        this.i = getApplicationContext();
        this.h = new aj(this.i);
        j = "";
        SharedPreferences defaultSharedPreferences2 = PreferenceManager.getDefaultSharedPreferences(new aa(this.i).a);
        SharedPreferences.Editor edit5 = defaultSharedPreferences2.edit();
        edit5.putLong("launch_count", defaultSharedPreferences2.getLong("launch_count", 0) + 1);
        edit5.commit();
        ab.a();
        this.h.a();
        this.p = new ae(this);
        registerReceiver(this.p, new IntentFilter("android.net.wifi.STATE_CHANGE"));
        registerReceiver(this.p, new IntentFilter("android.net.wifi.supplicant.CONNECTION_CHANGE"));
        registerReceiver(this.m, new IntentFilter("com.smarterdroid.wififiletransfer.ACTION_EVENT"));
        this.o = d.a((Context) this);
        if (a == 0) {
            a = 1;
        }
        while (a == 1) {
            ab.b();
            i();
            synchronized (this) {
                try {
                    wait(5000);
                } catch (Exception e5) {
                }
            }
            int b2 = aj.b();
            if (b2 != 1) {
                a = b2;
            }
        }
        if (a == 366) {
            Intent intent2 = new Intent(getApplicationContext(), WFTPanel.class);
            intent2.addFlags(268435456);
            intent2.setAction("com.smarterdroid.wififiletransfer.SDCARD_NOT_WRITEABLE");
            intent2.putExtra("error", 1);
            getApplication().startActivity(intent2);
            stopSelf();
        }
    }
}
