package com.smarterdroid.wftlib;

import android.os.Environment;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Properties;

public final class b implements a {
    private File a;
    private aj b;

    public b(Properties properties, aj ajVar) {
        this.b = ajVar;
        File file = new File(properties.getProperty("dir", Environment.getExternalStorageDirectory().getAbsolutePath()));
        if (!file.exists() || !file.isDirectory()) {
            this.a = null;
        } else {
            this.a = file;
        }
    }

    public final void a(OutputStream outputStream) {
        File[] listFiles;
        long j = 0;
        if (!(this.a == null || (listFiles = this.a.listFiles(new c(this))) == null || listFiles.length <= 0)) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isFile()) {
                    j += listFiles[i].length();
                }
            }
        }
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.print("<html><head></head><body><div id=\"progressdiv\" style=\"\tfont-family: Arial, Helvetica, sans-serif; font-size: 12px;\"><img src=\"" + af.a("/wftres.wft?f=loading.gif") + "\" alt=\"\" > " + this.b.g().getString(z.uploaded) + ": " + (((double) Math.round((((double) j) / 1048576.0d) * 100.0d)) / 100.0d) + " MB</div></body></html>");
        printWriter.flush();
        printWriter.close();
    }
}
