package com.smarterdroid.wftlib;

import android.content.Context;
import com.smarterdroid.wftlib.a.a;
import com.smarterdroid.wftlib.a.d;
import java.io.File;

public final class u {
    public static String a(String str, String str2, int i, Context context) {
        StringBuilder sb = new StringBuilder(4096);
        sb.append("<div id=\"tabs\" class=\"ui-tabs ui-widget ui-widget-content ui-corner-all\"><ul class=\"ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all\">");
        if (i == 201) {
            sb.append("<li class=\"ui-corner-top ui-tabs-selected ui-state-active\">");
        } else {
            sb.append("<li class=\"ui-corner-top ui-state-default\">");
        }
        sb.append("<a href=\"#tabs-1\"><img src=\"" + af.a("/wftres.wft?f=browser-icon.png") + "\" width=\"130\" height=\"80\" border=\"0\" style=\"margin-top: -51px; position: relative;\" alt=\"\">" + context.getString(z.filebrowser) + "</a></li>");
        if (i == 202) {
            sb.append("<li class=\"ui-corner-top ui-tabs-selected ui-state-active\">");
        } else {
            sb.append("<li class=\"ui-corner-top ui-state-default\">");
        }
        sb.append("<a href=\"#tabs-2\"><img src=\"" + af.a("/wftres.wft?f=media-icon.png") + "\" width=\"130\" height=\"80\" border=\"0\" style=\"margin-top: -51px; position: relative;\" alt=\"\">" + context.getString(z.mediagallery) + "</a></li>");
        sb.append("</ul><div id=\"tabs-1\" class=\"ui-tabs-panel ui-widget-content ui-corner-all\"><div class=\"menu\"><ul id=\"navin\"><!-- CSS Tabs -->" + a(str, str2, context) + "</ul></div><div class=\"clr\"></div></div><div id=\"tabs-2\" class=\"ui-tabs-panel ui-widget-content ui-corner-all ui-tabs-hide\"><div class=\"menu\"><ul id=\"navin\"><li><a href=\"/gallery.wft\">" + context.getString(z.pictures) + "</a></li></ul></div>");
        return sb.toString();
    }

    private static String a(String str, String str2, Context context) {
        StringBuilder sb = new StringBuilder(2000);
        for (a aVar : d.b()) {
            if (aVar.e()) {
                sb.append("<li><a href=\"" + aVar.b() + File.separator + str + str2 + "\">" + d.a(aVar, context.getString(z.internalstorage), context.getString(z.sdcard), context.getString(z.usbstorage)) + "</a></li>");
            }
        }
        String a = af.a("camDir", context);
        if (a != null && !a.equals("")) {
            sb.append("<li><a href=\"" + a + File.separator + str + str2 + "\">" + context.getString(z.myphotos) + "</a></li>");
        }
        String a2 = af.a("picsDir", context);
        if (a2 != null && !a2.equals("")) {
            sb.append("<li><a href=\"" + a2 + File.separator + str + str2 + "\">" + context.getString(z.mypictures) + "</a></li>");
        }
        String a3 = af.a("musicDir", context);
        if (a3 != null && !a3.equals("")) {
            sb.append("<li><a href=\"" + a3 + File.separator + str + str2 + "\">" + context.getString(z.mymusic) + "</a></li>");
        }
        String a4 = af.a("videoDir", context);
        if (a4 != null && !a4.equals("")) {
            sb.append("<li><a href=\"" + a + File.separator + str + str2 + "\">" + context.getString(z.myvideos) + "</a></li>");
        }
        return sb.toString();
    }
}
