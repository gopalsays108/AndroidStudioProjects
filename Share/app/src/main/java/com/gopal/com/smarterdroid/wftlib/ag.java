package com.smarterdroid.wftlib;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public final class ag {
    private static String a;
    private static String c = "";
    private String b = "WFTAUTH";
    private String d = "";

    public ag(String str) {
        a = str;
        if (c.equals("")) {
            c = d();
        }
    }

    public static boolean a() {
        return !a.equals("");
    }

    public static boolean a(String str) {
        return str.equals(a);
    }

    public static void b() {
        c = d();
    }

    private static String d() {
        return Long.toHexString(Double.doubleToLongBits(Math.random()));
    }

    public final boolean a(Properties properties) {
        if (a.equals("")) {
            return true;
        }
        if (!properties.containsKey("cookie") || properties.getProperty("cookie") == null) {
            return false;
        }
        String property = properties.getProperty("cookie");
        if (property.indexOf("WFTSession=") < 0) {
            return false;
        }
        return property.substring(property.indexOf("WFTSession=") + 11, property.length()).equals(c());
    }

    public final String c() {
        try {
            this.d = new BigInteger(1, MessageDigest.getInstance("MD5").digest((String.valueOf(a) + this.b + c).getBytes("UTF-8"))).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        return this.d;
    }
}
