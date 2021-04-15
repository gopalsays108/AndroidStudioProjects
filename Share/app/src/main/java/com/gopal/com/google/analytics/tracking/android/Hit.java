package com.google.analytics.tracking.android;

class Hit {
    private String a = null;
    private final long b;
    private final long c;
    private String d;

    Hit(long j, long j2) {
        this.b = j;
        this.c = j2;
    }

    /* access modifiers changed from: package-private */
    public final String a() {
        return this.a;
    }

    /* access modifiers changed from: package-private */
    public final void a(String str) {
        this.a = str;
    }

    /* access modifiers changed from: package-private */
    public final long b() {
        return this.b;
    }

    /* access modifiers changed from: package-private */
    public final void b(String str) {
        this.d = str;
    }

    /* access modifiers changed from: package-private */
    public final long c() {
        return this.c;
    }

    /* access modifiers changed from: package-private */
    public final String d() {
        return this.d;
    }
}
