package com.smarterdroid.wftlib.a;

import android.text.TextUtils;
import java.io.File;

final class b extends a {
    String c;
    private String d;
    private boolean e;
    private boolean f;

    b(TextUtils.SimpleStringSplitter simpleStringSplitter) {
        this.d = simpleStringSplitter.next().trim();
        this.b = simpleStringSplitter.next().trim();
        if ("/mnt/sdcard/extStorages".equals(this.b)) {
            String trim = simpleStringSplitter.next().trim();
            if (trim.contains("/")) {
                this.b = trim;
            }
        }
        "new DeviceDiv " + this.b;
        f();
    }

    public final String c() {
        return this.c;
    }

    public final boolean d() {
        return true;
    }

    public final boolean e() {
        return this.e;
    }

    /* access modifiers changed from: protected */
    public final void f() {
        "updateState " + this.b;
        File file = new File(this.b);
        if ((!file.isDirectory() || !file.canRead()) && this.b.contains(":")) {
            String substring = this.b.substring(0, this.b.indexOf(":"));
            "mp " + substring;
            File file2 = new File(substring);
            if (file2.exists() && file2.isDirectory()) {
                this.b = substring;
                file = file2;
            }
        }
        this.c = file.getName();
        boolean z = file.isDirectory() && file.canRead();
        this.e = z;
        if (z) {
            this.a = f.a(file);
            this.f = file.canWrite();
            if (this.b.startsWith(d.a.b) && this.a.equals(d.a.a)) {
                this.f = false;
                this.e = false;
                return;
            }
            return;
        }
        this.f = false;
    }
}
