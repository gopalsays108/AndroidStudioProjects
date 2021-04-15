package com.smarterdroid.wftlib;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public final class m implements a {
    private static SimpleDateFormat c;
    private aj a;
    private Properties b;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        c = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public m(Properties properties, aj ajVar) {
        this.a = ajVar;
        this.b = properties;
    }

    public static String a() {
        return "Content-Type: application/javascript\r\nExpires: " + c.format(new Date(new Date().getTime() + 86400000)) + "\r\nCache-Control: public, max-age=86400\r\n";
    }

    public final void a(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.print(ad.a(this.a.g()));
        printWriter.flush();
        printWriter.close();
    }
}
