package com.google.analytics.tracking.android;

class AdHitIdGenerator {
    private boolean a;

    AdHitIdGenerator() {
        try {
            this.a = Class.forName("com.google.ads.AdRequest") != null;
        } catch (ClassNotFoundException e) {
            this.a = false;
        }
    }

    /* access modifiers changed from: package-private */
    public final int a() {
        if (!this.a) {
            return 0;
        }
        return com.google.analytics.tracking.android.AdMobInfo.a().b();
    }
}
