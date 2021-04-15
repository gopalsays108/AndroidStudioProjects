package com.smarterdroid.wftlib;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Locale;

public final class ai implements FilenameFilter {
    private String[] a;

    public ai(String[] strArr) {
        this.a = strArr;
    }

    public final boolean accept(File file, String str) {
        boolean z = false;
        for (int i = 0; i < this.a.length && !z; i++) {
            if (str.toLowerCase(Locale.US).equals(this.a[i]) && new File(file, str).isDirectory()) {
                z = true;
            }
        }
        return z;
    }
}
