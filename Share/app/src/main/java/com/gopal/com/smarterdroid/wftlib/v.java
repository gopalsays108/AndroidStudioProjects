package com.smarterdroid.wftlib;

import android.content.Context;
import android.os.Build;
import java.util.Locale;

public final class v {
    private static int a;
    private static Context b;
    private static boolean c = true;

    public static String a() {
        switch (a) {
            case 402:
                return af.a("/wftres.wft?f=uploaderp.js");
            default:
                return af.a("/wftres.wft?f=uploader.js");
        }
    }

    public static String a(String str, String str2, int i) {
        StringBuffer stringBuffer = new StringBuffer(1000);
        switch (a) {
            case 402:
                stringBuffer.append("<div style=\"padding: 0px 10px 10px 10px; min-width: 1160px\"><div id=\"banner\"><div class=\"logo\"><img src=\"" + af.a("/wftres.wft?f=logo.png") + "\" width=\"472\" height=\"52\" alt=\"\" style=\"padding:0px 0px 0px 0px; vertical-align: middle;\"></div>");
                if (ag.a()) {
                    stringBuffer.append("<div class=\"logout-btn\"><span class=\"text_075em\"><a href=\"/login.wft?action=logout\">" + aj.h().getString(z.logout) + "</a></span></div>");
                }
                stringBuffer.append("<div class=\"social-container\"><div class=\"fb-like-container\" ><div class=\"fb-like\" data-href=\"https://play.google.com/store/apps/details?id=com.smarterdroid.wififiletransferpro\" data-send=\"false\" data-layout=\"button_count\" data-width=\"90\" data-show-faces=\"false\"></div></div><div class=\"g-plusone-container\"><div class=\"g-plusone\" data-size=\"medium\" data-href=\"https://play.google.com/store/apps/details?id=com.smarterdroid.wififiletransferpro\"></div></div></div><div class=\"top-btns\"><a href=\"http://www.smarterdroid.com/index.php/wifi-file-transfer/using-wifi-file-transfer\" class=\"how-btn\" target=\"_blank\">" + aj.h().getString(z.howtouse) + "</a></div></div><div class=\"clr\"></div>" + u.a(str, str2, i, aj.h()) + "<div class=\"clr\"></div></div></div></div>");
                return stringBuffer.toString();
            default:
                stringBuffer.append("<div style=\"padding: 0px 10px 10px 10px; min-width: 1160px\"><div id=\"banner\"><div class=\"logo\"><img src=\"" + af.a("/wftres.wft?f=logo.png") + "\" width=\"389\" height=\"52\" alt=\"\" style=\"padding:0px 0px 0px 0px; vertical-align: middle;\"></div>");
                if (ag.a()) {
                    stringBuffer.append("<div class=\"logout-btn\"><span class=\"text_075em\"><a href=\"/login.wft?action=logout\">" + aj.h().getString(z.logout) + "</a></span></div>");
                }
                stringBuffer.append("<div class=\"social-container\"><div class=\"fb-like-container\" ><div class=\"fb-like\" data-href=\"https://play.google.com/store/apps/details?id=com.smarterdroid.wififiletransfer\" data-send=\"false\" data-layout=\"button_count\" data-width=\"90\" data-show-faces=\"false\"></div></div><div class=\"g-plusone-container\"><div class=\"g-plusone\" data-size=\"medium\" data-href=\"https://play.google.com/store/apps/details?id=com.smarterdroid.wififiletransfer\"></div></div></div><div class=\"top-btns\"><a href=\"http://www.smarterdroid.com/index.php/wifi-file-transfer/using-wifi-file-transfer\" class=\"how-btn\" target=\"_blank\">" + aj.h().getString(z.howtouse) + "</a>");
                if (!aj.b) {
                    stringBuffer.append("<a href=\"https://market.android.com/details?id=com.smarterdroid.wififiletransferpro\" class=\"upgrade-btn\">" + aj.h().getString(z.upgradetopro) + "</a>");
                } else {
                    stringBuffer.append("<a href=\"#\" class=\"upgrade-btn\" onClick=\"return confirmUpgrade();\">" + aj.h().getString(z.upgradetopro) + "</a>");
                }
                stringBuffer.append("</div></div><div class=\"clr\"></div>" + u.a(str, str2, i, aj.h()) + "<div class=\"clr\"></div></div></div></div>");
                return stringBuffer.toString();
        }
    }

    public static void a(Context context) {
        b = context;
    }

    public static void a(boolean z) {
        c = z;
    }

    public static String b() {
        switch (a) {
            case 402:
                return c ? "<script type=\"text/javascript\" src=\"http://www.smarterdroid.com/msg/msgjs1.php?loc=" + Locale.getDefault().toString() + "\"></script><div id=\"fb-root\"></div><script type=\"text/javascript\">window.___gcfg = {lang: '" + Locale.getDefault().toString() + "'};(function() {var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;po.src = 'https://apis.google.com/js/plusone.js';var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);})();(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = \"//connect.facebook.net/" + Locale.getDefault().toString() + "/all.js#xfbml=1\";fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script>" : "<script type=\"text/javascript\" src=\"http://www.smarterdroid.com/msg/msgjs1.php?loc=" + Locale.getDefault().toString() + "\"></script>";
            default:
                return c ? "<script type=\"text/javascript\" src=\"http://www.smarterdroid.com/msg/msgjs2.php?loc=" + Locale.getDefault().toString() + "\"></script><div id=\"fb-root\"></div><script type=\"text/javascript\">window.___gcfg = {lang: '" + Locale.getDefault().toString() + "'};(function() {var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;po.src = 'https://apis.google.com/js/plusone.js';var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);})();(function(d, s, id) {var js, fjs = d.getElementsByTagName(s)[0];if (d.getElementById(id)) return;js = d.createElement(s); js.id = id;js.src = \"//connect.facebook.net/" + Locale.getDefault().toString() + "/all.js#xfbml=1\";fjs.parentNode.insertBefore(js, fjs);}(document, 'script', 'facebook-jssdk'));</script>" : "<script type=\"text/javascript\" src=\"http://www.smarterdroid.com/msg/msgjs2.php?loc=" + Locale.getDefault().toString() + "\"></script>";
        }
    }

    public static String c() {
        switch (a) {
            case 402:
                return "<div id=\"uploader-fallback\"><div id=\"basicprogress\"></div><input type=\"file\" name=\"file_1\" id=\"file_1\" multiple=\"multiple\" onchange=\"filesSelected();\"><br><br>";
            default:
                return "<div id=\"uploader-fallback\"><div id=\"basicprogress\"></div><input type=\"file\" name=\"file_1\" id=\"file_1\" multiple=\"multiple\" onchange=\"filesSelected();\"><br><br><div><span class=\"text_075em\">" + aj.h().getString(z.mbperfilemax) + " <a href=\"https://market.android.com/details?id=com.smarterdroid.wififiletransferpro\">" + aj.h().getString(z.appname_pro) + "</a> " + aj.h().getString(z.touploadfilesofanysize) + "</span></div><br>";
        }
    }

    public static String d() {
        switch (a) {
            case 402:
                return String.valueOf(Build.MODEL) + " - " + aj.h().getString(z.appname_pro);
            default:
                return String.valueOf(Build.MODEL) + " - " + aj.h().getString(z.appname_free);
        }
    }

    public static void e() {
        a = 401;
    }
}
