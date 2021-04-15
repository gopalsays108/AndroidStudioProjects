package com.smarterdroid.wftlib;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import java.io.File;
import java.util.Locale;
import java.util.Properties;

public final class e {
    private aj a;
    private Properties b;

    public e(aj ajVar, Properties properties) {
        this.a = ajVar;
        this.b = properties;
    }

    private String a(int i) {
        return this.a.g().getString(i);
    }

    private static String a(String str) {
        return str.replace("#", "%23").replace("&", "%26").replace("{", "%7B").replace("}", "%7D");
    }

    public final String a() {
        int i;
        int i2;
        String str;
        String str2;
        String str3;
        String str4;
        StringBuilder sb = new StringBuilder(20000);
        try {
            i = Integer.valueOf(this.b.getProperty("skip", "0")).intValue();
        } catch (NumberFormatException e) {
            i = 0;
        }
        try {
            i2 = Integer.valueOf(this.b.getProperty("perpage", "60")).intValue();
        } catch (NumberFormatException e2) {
            i2 = 60;
        }
        String property = this.b.getProperty("p", "a");
        String property2 = this.b.getProperty("s", "d");
        String property3 = this.b.getProperty("o", "d");
        if (!this.b.containsKey("p")) {
            this.b.setProperty("p", "a");
            str = "a";
        } else {
            str = property;
        }
        if (!this.b.containsKey("s")) {
            this.b.setProperty("s", "d");
        }
        if (!this.b.containsKey("o")) {
            this.b.setProperty("o", "d");
        }
        if (this.b.getProperty("p").equals("o")) {
            str2 = "";
            str3 = " selected=\"selected\"";
            str4 = " NOT";
        } else if (this.b.getProperty("p").equals("c")) {
            str2 = " selected=\"selected\"";
            str3 = "";
            str4 = "";
        } else {
            str2 = "";
            str3 = "";
            str4 = null;
        }
        String str5 = this.b.getProperty("s").equals("d") ? "datetaken" : this.b.getProperty("s").equals("n") ? "_display_name" : "_size";
        String str6 = this.b.getProperty("o").equals("a") ? " ASC" : " DESC";
        Properties properties = new Properties();
        properties.setProperty(String.valueOf(this.b.getProperty("s")) + this.b.getProperty("o"), " selected=\"selected\"");
        Context g = this.a.g();
        String a2 = af.a("camDir", g);
        ContentResolver contentResolver = g.getContentResolver();
        String[] strArr = {"_data", "_id", "_display_name", "datetaken", "_size", "date_modified"};
        String str7 = null;
        if (str4 != null) {
            str7 = "_data" + str4 + " LIKE '" + a2 + "%'";
        }
        Cursor query = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, strArr, str7, (String[]) null, String.valueOf(str5) + str6);
        int count = query.getCount();
        String upperCase = Locale.getDefault().getLanguage().toUpperCase(Locale.US);
        if (upperCase.equals("")) {
            upperCase = "EN";
        }
        sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n<title>" + v.d() + "</title>\r\n<link rel=\"shortcut icon\" href=\"" + af.a("/wftres.wft?f=ficon.ico") + "\">\r\n<link href=\"" + af.a("/wftres.wft?f=wft.css") + "\" rel=\"stylesheet\" type=\"text/css\" >\r\n<script type=\"text/javascript\" src=\"" + af.a("/wftres.wft?f=jquery161.min.js") + "\"></script>\r\n<script type=\"text/javascript\">jQuery.noConflict();</script>\r\n<script type=\"text/javascript\" src=\"" + af.a("/wftres.wft?f=jq-plugins.js") + "\"></script>\r\n<script type=\"text/javascript\" src=\"" + af.a("/wftres.wft?f=mootools.js") + "\"></script>\r\n<script type=\"text/javascript\" src=\"" + af.b(upperCase) + "\"></script>\r\n<script type=\"text/javascript\" src=\"" + af.a("/wftres.wft?f=wft.js") + "\"></script>\r\n<script type=\"text/javascript\">jQuery(document).ready(function(){jQuery(\"a[rel='cb']\").colorbox({transition:\"fade\", opacity:\"0.80\", maxWidth:\"90%\", maxHeight:\"94%\", scalePhotos:\"true\", top:\"25px\", fixed:\"true\"});});</script>\r\n<script type=\"text/javascript\">jQuery(function(){\tjQuery('#tabs').tabs();\t});</script><style type=\"text/css\">body {background-color: " + ac.a(this.a.g(), 0) + "; color: " + ac.a(this.a.g(), 4) + ";} .how-btn {background:url(" + af.a("/wftres.wft?f=how-bg.png") + ") no-repeat;} .upgrade-btn {background:url(" + af.a("/wftres.wft?f=upg-bg.png") + ") no-repeat;} .tableContainer {background: url(" + af.a("/wftres.wft?f=bg-tabs-trans.png") + ") repeat;} .gallery_bar {background:url(" + af.a("/wftres.wft?f=bg-t-tr.png") + ") repeat-x;} #cboxLoadingGraphic{background:url(" + af.a("/wftres.wft?f=loading.gif") + ") no-repeat center center;} #cboxPrevious{background:url(" + af.a("/wftres.wft?f=controls.png") + ") no-repeat -75px 0;} #cboxNext{background:url(" + af.a("/wftres.wft?f=controls.png") + ") no-repeat -50px 0;} #cboxClose{background:url(" + af.a("/wftres.wft?f=controls.png") + ") no-repeat -25px 0;} #uploader-status .progress {background:url(" + af.a("/wftres.wft?f=progress.gif") + ") no-repeat;} .ui-state-default, .ui-widget-content .ui-state-default {background: #e6e6e6 url(" + af.a("/wftres.wft?f=bg-tabs-trans-nor.png") + ") repeat;} .ui-state-hover, .ui-widget-content .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus {background: url(" + af.a("/wftres.wft?f=hover-tab.png") + ") repeat-x;} .ui-state-active, .ui-widget-content .ui-state-active {background: url(" + af.a("/wftres.wft?f=bg-tabs-trans.png") + ") repeat;} .ui-tabs .ui-tabs-panel {background:url(" + af.a("/wftres.wft?f=bg-tabs-trans.png") + ") repeat ;} </style>");
        sb.append("</head>\r\n<body>\r\n" + v.a("?", "", 202) + "<div style=\"padding: 0px 10px 10px 10px;\"><div><form method=\"POST\" action=\"\" enctype=\"multipart/form-data\" name=\"filelist\" autocomplete=\"off\"><input type=\"hidden\" name=\"action\"><input type=\"hidden\" name=\"data_file\"><input type=\"hidden\" name=\"data_currentParams\" value=\"" + "?" + "" + "\"><div class=\"tableContainer ui-corner-all\"><div id=\"outertable\" class=\"bdr ui-corner-all\"><div class=\"gallery_bar\"><span style=\"padding: 0px 30px 0px 0px;\">" + a(z.show) + ": <select name=\"source\" onChange=\"optionsChanged()\"><option value=\"&amp;p=a\">" + a(z.allpictures) + "</option><option value=\"&amp;p=c\"" + str2 + ">" + a(z.camerapictures) + "</option><option value=\"&amp;p=o\"" + str3 + ">" + a(z.otherpictures) + "</option></select></span><span>" + a(z.sortby) + ": <select name=\"order\" onChange=\"optionsChanged()\"><option value=\"?s=d&amp;o=d\"" + properties.getProperty("dd", "") + ">" + a(z.datenewestfirst) + "</option><option value=\"?s=d&amp;o=a\"" + properties.getProperty("da", "") + ">" + a(z.dateoldestfirst) + "</option><option value=\"?s=n&amp;o=a\"" + properties.getProperty("na", "") + ">" + a(z.nameascending) + "</option><option value=\"?s=n&amp;o=d\"" + properties.getProperty("nd", "") + ">" + a(z.namedescending) + "</option><option value=\"?s=s&amp;o=d\"" + properties.getProperty("sd", "") + ">" + a(z.sizelargestfirst) + "</option><option value=\"?s=s&amp;o=a\"" + properties.getProperty("sa", "") + ">" + a(z.sizesmallestfirst) + "</option></select></span></div>\r\n<div><img src=\"" + af.a("/wftres.wft?f=1px.png") + "\" height=\"10\" width=\"920\" alt=\"\" ></div>\r\n");
        if (i2 < count) {
            sb.append("<div style=\"padding: 10px;\"><span class=\"pageLinks\">" + a(z.pages) + " ");
            for (int i3 = 0; i3 * i2 <= count; i3++) {
                if (i == i3 * i2) {
                    sb.append("[" + (i3 + 1) + "] ");
                } else {
                    sb.append("<a href=\"/gallery.wft?s=" + property2 + "&amp;o=" + property3 + "&amp;p=" + str + "&amp;skip=" + (i3 * i2) + "\">" + (i3 + 1) + "</a> ");
                }
            }
            sb.append("</span></div>");
        }
        sb.append("<div style=\"padding: 0px 18px 0px 18px;\"><ol class=\"olGal\">");
        if (query != null && query.getCount() > 0) {
            int i4 = i;
            while (query.moveToPosition(i4) && i4 < i + i2) {
                String string = query.getString(query.getColumnIndex("_data"));
                String string2 = query.getString(query.getColumnIndex("_display_name"));
                sb.append("<li class=\"liGal\" style=\"height: 180px; width: 160px;\"><a href=\"" + a(string) + "\" rel=\"cb\" title=\"" + string2 + "\"><img src=\"/thumbnail.wft?image=" + a(string) + "&amp;lm=" + query.getString(query.getColumnIndex("date_modified")) + "\" alt=\"\" style=\"height: 120px; max-width: 160px; margin-left: auto; margin-right: auto;\"></a><br><a href=\"" + a(string) + "\">" + string2 + "</a><br><br><a href=\"" + a(string) + "?cd=at\">" + a(z.download) + "</a><br><a href=\"" + a(new File(string).getParent()) + "\">" + a(z.gotoparentdir) + "</a></li> ");
                i4++;
            }
        }
        if (query != null) {
            query.close();
        }
        sb.append("</ol></div>");
        if (i2 < count) {
            sb.append("<div style=\"padding: 10px;\"><span class=\"pageLinks\">" + a(z.pages) + " ");
            for (int i5 = 0; i5 * i2 <= count; i5++) {
                if (i == i5 * i2) {
                    sb.append("[" + (i5 + 1) + "] ");
                } else {
                    sb.append("<a href=\"/gallery.wft?s=" + property2 + "&amp;o=" + property3 + "&amp;p=" + str + "&amp;skip=" + (i5 * i2) + "\">" + (i5 + 1) + "</a> ");
                }
            }
            sb.append("</span></div>");
        }
        sb.append("</div></div></form>");
        sb.append("<div><span class=\"text_075em\">Nuvola icons by <a href=\"http://www.icon-king.com\">David Vignoni</a></span></div></div></div></body></html>");
        return sb.toString();
    }
}
