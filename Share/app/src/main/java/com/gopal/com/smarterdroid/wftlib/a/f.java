package com.smarterdroid.wftlib.a;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.StatFs;
import android.util.Pair;
import java.io.File;

public final class f extends Pair {
    private f(long j, long j2) {
        super(Long.valueOf(j), Long.valueOf(j2));
    }

    @SuppressLint({"NewApi"})
    public static f a(File file) {
        if (file != null) {
            try {
                if (Build.VERSION.SDK_INT >= 9) {
                    return new f(file.getUsableSpace(), file.getTotalSpace());
                }
                StatFs statFs = new StatFs(file.getAbsolutePath());
                return new f(((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize()), ((long) statFs.getBlockCount()) * ((long) statFs.getBlockSize()));
            } catch (Exception e) {
            }
        }
        return new f(0, 0);
    }
}
