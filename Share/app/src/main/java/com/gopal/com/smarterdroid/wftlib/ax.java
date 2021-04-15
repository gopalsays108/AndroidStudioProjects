package com.smarterdroid.wftlib;

import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

public final class ax {
    public static SimpleDateFormat a;
    private static Hashtable b = new Hashtable();

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        a = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        StringTokenizer stringTokenizer = new StringTokenizer("htm\t\ttext/html html\t\ttext/html txt\t\ttext/plain asc\t\ttext/plain css\t\ttext/css js\t\ttext/javascript gif\t\timage/gif jpg\t\timage/jpeg jpeg\t\timage/jpeg png\t\timage/png ico\t\timage/x-icon mp3\t\taudio/mpeg m3u\t\taudio/mpeg-url pdf\t\tapplication/pdf doc\t\tapplication/msword ogg\t\tapplication/x-ogg zip\t\tapplication/octet-stream exe\t\tapplication/octet-stream class\t\tapplication/octet-stream ");
        while (stringTokenizer.hasMoreTokens()) {
            b.put(stringTokenizer.nextToken(), stringTokenizer.nextToken());
        }
    }

    public static String a(String str) {
        String str2 = null;
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            str2 = (String) b.get(str.substring(lastIndexOf + 1).toLowerCase(Locale.US));
        }
        return str2 == null ? "application/octet-stream" : str2;
    }
}
