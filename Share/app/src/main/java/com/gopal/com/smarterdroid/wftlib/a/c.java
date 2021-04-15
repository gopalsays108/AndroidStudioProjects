package com.smarterdroid.wftlib.a;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import java.io.File;

final class c extends a {
    boolean c;
    private String d;

    @SuppressLint({"NewApi"})
    c() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        this.b = externalStorageDirectory.getAbsolutePath();
        if (Build.VERSION.SDK_INT >= 9) {
            this.c = Environment.isExternalStorageRemovable();
        } else {
            this.c = true;
        }
        f();
        "new DeviceExternal " + externalStorageDirectory.getAbsolutePath();
    }

    public final String c() {
        return new File(this.b).getName();
    }

    public final boolean d() {
        return this.c;
    }

    public final boolean e() {
        return "mounted".equals(this.d) || "mounted_ro".equals(this.d);
    }

    /* access modifiers changed from: protected */
    public final void f() {
        this.d = Environment.getExternalStorageState();
        if (e()) {
            this.a = f.a(new File(this.b));
        }
    }
}
