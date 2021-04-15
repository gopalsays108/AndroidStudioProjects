package com.smarterdroid.wftlib;

import java.io.File;

public final class av {
    public int a = -1;
    public String b = null;
    public File c = null;
    public String d = "";
    public int e = -1;
    public a f;

    public final long a() {
        if (this.b != null) {
            return Long.valueOf((long) this.b.length()).longValue();
        }
        if (this.c != null) {
            return this.c.length();
        }
        Long l = 0L;
        return l.longValue();
    }

    public final void a(String str) {
        this.d = "Location: " + str + "\r\n";
        this.b = "You are being redirected to <a href=\"" + str + "\">" + str + "</a>";
    }
}
