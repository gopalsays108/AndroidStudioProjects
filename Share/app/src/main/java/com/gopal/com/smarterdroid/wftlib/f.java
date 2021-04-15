package com.smarterdroid.wftlib;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public final class f implements a {
    private static SimpleDateFormat d;
    private aj a;
    private File b;
    private Properties c;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        d = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public f(Properties properties, aj ajVar) {
        this.a = ajVar;
        this.c = properties;
        String property = properties.getProperty("dirpath", "");
        "dir: " + property;
        File file = new File(property);
        if (!file.exists() || !file.isDirectory()) {
            this.b = null;
        } else {
            this.b = file;
        }
    }

    private g a(File[] fileArr, int i, int i2) {
        g gVar;
        boolean z;
        g gVar2 = new g(this, 0, false);
        if (fileArr != null) {
            int length = fileArr.length;
            int i3 = 0;
            while (i3 < length) {
                File file = fileArr[i3];
                if (!file.isDirectory()) {
                    gVar2.a = file.length() + gVar2.a;
                    gVar = gVar2;
                } else if (i2 >= i) {
                    gVar2.b = true;
                    gVar = gVar2;
                } else if (file == null) {
                    try {
                        throw new NullPointerException("File must not be null");
                    } catch (IOException e) {
                        z = false;
                    }
                } else {
                    File file2 = file.getParent() == null ? file : new File(file.getParentFile().getCanonicalFile(), file.getName());
                    z = !file2.getCanonicalFile().equals(file2.getAbsoluteFile());
                    if (!z) {
                        g a2 = a(file.listFiles(), i, i2 + 1);
                        gVar = new g(gVar2.c, gVar2.a + a2.a, gVar2.b || a2.b);
                    }
                    gVar = gVar2;
                }
                i3++;
                gVar2 = gVar;
            }
        }
        return gVar2;
    }

    private static String a(double d2, int i) {
        DecimalFormat decimalFormat;
        switch (i) {
            case 1:
                decimalFormat = new DecimalFormat("#0.0");
                break;
            case 2:
                decimalFormat = new DecimalFormat("0.00");
                break;
            default:
                decimalFormat = new DecimalFormat("##0");
                break;
        }
        return decimalFormat.format(d2);
    }

    private String a(int i) {
        return this.a.g().getString(i);
    }

    public final String a() {
        if (!this.c.containsKey("lmiji")) {
            return "Content-Type: text/html\r\n";
        }
        return String.valueOf(String.valueOf("Content-Type: text/html\r\n") + "Expires: " + d.format(new Date(new Date().getTime() + 15552000000L)) + "\r\n") + "Cache-Control: public, max-age=15552000\r\n";
    }

    public final void a(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        if (this.b != null) {
            g a2 = a(this.b.listFiles(), 6, 0);
            StringBuilder sb = new StringBuilder("<html><head></head><body>");
            String str = " ";
            if (a2.b) {
                str = "+";
            }
            long j = a2.a;
            printWriter.print(sb.append(j < 1000 ? String.valueOf(j) + " " + a(z.b) : j < 10240 ? String.valueOf(a(((double) j) / 1024.0d, 2)) + str + a(z.kb) : j < 102400 ? String.valueOf(a(((double) j) / 1024.0d, 1)) + str + a(z.kb) : j < 1024000 ? String.valueOf(a(((double) j) / 1024.0d, 0)) + str + a(z.kb) : j < 10485760 ? String.valueOf(a(((double) j) / 1048576.0d, 2)) + str + a(z.mb) : j < 104857600 ? String.valueOf(a(((double) j) / 1048576.0d, 1)) + str + a(z.mb) : j < 1048576000 ? String.valueOf(a(((double) j) / 1048576.0d, 0)) + str + a(z.mb) : String.valueOf(a(((double) j) / 1.073741824E9d, 2)) + str + a(z.gb)).append("</body></html>").toString());
        } else {
            printWriter.print("<html><head></head><body>-</body></html>");
        }
        printWriter.flush();
        printWriter.close();
    }
}
