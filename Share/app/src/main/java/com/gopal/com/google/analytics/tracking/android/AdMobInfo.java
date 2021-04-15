package com.google.analytics.tracking.android;

import java.util.Random;

class AdMobInfo {
    private static final AdMobInfo a = new AdMobInfo();
    private int b;
    private Random c = new Random();

    enum AdMobKey {
        CLIENT_ID_KEY("ga_cid"),
        HIT_ID_KEY("ga_hid"),
        PROPERTY_ID_KEY("ga_wpids"),
        VISITOR_ID_KEY("ga_uid");
        
        private String e;

        private AdMobKey(String str) {
            this.e = str;
        }
    }

    private AdMobInfo() {
    }

    static AdMobInfo a() {
        return a;
    }

    /* access modifiers changed from: package-private */
    public final int b() {
        this.b = this.c.nextInt(2147483646) + 1;
        return this.b;
    }
}
