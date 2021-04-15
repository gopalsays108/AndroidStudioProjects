package com.smarterdroid.wftlib;

import android.annotation.SuppressLint;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;

public final class ab {
    private static long a = System.currentTimeMillis();
    private static long b = 180000;
    private static PowerManager c;
    private static PowerManager.WakeLock d;
    private static WifiManager e = ((WifiManager) aj.h().getSystemService("wifi"));
    private static WifiManager.WifiLock f;

    static {
        PowerManager powerManager = (PowerManager) aj.h().getSystemService("power");
        c = powerManager;
        d = powerManager.newWakeLock(1, aj.h().getString(z.appname_free));
        if (Build.VERSION.SDK_INT < 13) {
            f = e.createWifiLock(1, aj.h().getString(z.appname_free));
        } else {
            f = e.createWifiLock(3, aj.h().getString(z.appname_free));
        }
    }

    public static void a() {
        a = System.currentTimeMillis();
    }

    @SuppressLint({"Wakelock"})
    public static void b() {
        if (System.currentTimeMillis() - a < b) {
            if (!d.isHeld()) {
                d.acquire();
            }
            if (!f.isHeld()) {
                f.acquire();
                return;
            }
            return;
        }
        if (d.isHeld()) {
            d.release();
        }
        if (f.isHeld()) {
            f.release();
        }
    }

    public static void c() {
        a = System.currentTimeMillis() - (2 * b);
        if (d.isHeld()) {
            d.release();
        }
        if (f.isHeld()) {
            f.release();
        }
    }
}
