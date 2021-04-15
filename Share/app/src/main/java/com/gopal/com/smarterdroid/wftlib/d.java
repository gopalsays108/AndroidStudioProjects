package com.smarterdroid.wftlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.smarterdroid.wftlib.a.a;
import com.smarterdroid.wftlib.a.f;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

public final class d implements a {
    private static SimpleDateFormat m;
    private aj a;
    private File b;
    private Properties c;
    private Properties d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private Context l = this.a.g();

    static {
        DateFormat dateTimeInstance = DateFormat.getDateTimeInstance(3, 3);
        if (dateTimeInstance instanceof SimpleDateFormat) {
            String pattern = ((SimpleDateFormat) dateTimeInstance).toPattern();
            if (pattern.indexOf("MM") == -1) {
                pattern = pattern.replace("M", "MM");
            }
            if (pattern.indexOf("dd") == -1) {
                pattern = pattern.replace("d", "dd");
            }
            if (pattern.indexOf("hh") == -1) {
                pattern = pattern.replace("h", "hh");
            }
            if (pattern.indexOf("mm") == -1) {
                pattern = pattern.replace("m", "mm");
            }
            m = new SimpleDateFormat(pattern);
        } else {
            m = new SimpleDateFormat();
        }
        m.setTimeZone(TimeZone.getDefault());
    }

    public d(aj ajVar, File file, Properties properties, Properties properties2) {
        this.a = ajVar;
        this.b = file;
        this.c = properties;
        this.d = properties2;
    }

    private static String a(double d2, int i2) {
        DecimalFormat decimalFormat;
        switch (i2) {
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

    private String a(int i2) {
        return this.l.getString(i2);
    }

    private String a(long j2) {
        return j2 < 1000 ? String.valueOf(j2) + " " + a(z.b) : j2 < 10240 ? String.valueOf(a(((double) j2) / 1024.0d, 2)) + " " + a(z.kb) : j2 < 102400 ? String.valueOf(a(((double) j2) / 1024.0d, 1)) + " " + a(z.kb) : j2 < 1024000 ? String.valueOf(a(((double) j2) / 1024.0d, 0)) + " " + a(z.kb) : j2 < 10485760 ? String.valueOf(a(((double) j2) / 1048576.0d, 2)) + " " + a(z.mb) : j2 < 104857600 ? String.valueOf(a(((double) j2) / 1048576.0d, 1)) + " " + a(z.mb) : j2 < 1048576000 ? String.valueOf(a(((double) j2) / 1048576.0d, 0)) + " " + a(z.mb) : String.valueOf(a(((double) j2) / 1.073741824E9d, 2)) + " " + a(z.gb);
    }

    private static String a(String str) {
        return str.replace("'", "\\'");
    }

    private String a(boolean z, String str) {
        return z ? "<tr class=\"action_bar\"><td class=\"checkbox_cell\"><input type=\"checkbox\" name=\"selectall1\" onClick=\"toggleAll(this);\"></td><td>&#160;</td><td colspan=\"4\"><a href=\"#\" onClick=\"return downAll('" + a(str) + "');\"><img src=\"" + af.a("/wftres.wft?f=action_icon_down.png") + "\" alt=\"\" style=\"padding:0px 5px 0px 0px;\">" + a(z.download) + "</a><a href=\"#\" onClick=\"return delAll();\"><img src=\"" + af.a("/wftres.wft?f=action_icon_delete.png") + "\" alt=\"\" style=\"padding:0px 5px 0px 10px;\">" + a(z.delete) + "</a><a href=\"#\" onClick=\"return moveAll('" + a(str) + "');\"><img src=\"" + af.a("/wftres.wft?f=action_icon_move.png") + "\" alt=\"\" style=\"padding:0px 5px 0px 10px;\">" + a(z.move) + "</a><a href=\"#\" onClick=\"return zipAll();\"><img src=\"" + af.a("/wftres.wft?f=action_icon_zip.png") + "\" alt=\"\" style=\"padding:0px 5px 0px 10px;\">" + a(z.makezipfile) + "</a><a href=\"#\" onClick=\"return makeDir('" + a(str) + "');\"><img src=\"" + af.a("/wftres.wft?f=action_icon_newdir.png") + "\" alt=\"\" style=\"padding:0px 5px 0px 10px;\">" + a(z.newdirectory) + "</a></td></tr>" : "<tr class=\"action_bar\"><td><input type=\"checkbox\" name=\"selectall1\" onClick=\"toggleAll(this);\"></td><td>&#160;</td><td colspan=\"4\"><a href=\"#\" onClick=\"return downAll('" + a(str) + "');\"><img src=\"" + af.a("/wftres.wft?f=action_icon_down.png") + "\" alt=\"\" style=\"padding:0px 5px 0px 0px;\">" + a(z.download) + "</a></td></tr>";
    }

    private boolean a() {
        for (a b2 : com.smarterdroid.wftlib.a.d.b()) {
            if (this.e.toLowerCase(Locale.US).startsWith(b2.b().toLowerCase(Locale.US))) {
                return false;
            }
        }
        return true;
    }

    private static double b(long j2) {
        return ((double) Math.round((((double) j2) / 1.073741824E9d) * 100.0d)) / 100.0d;
    }

    private static String b(String str) {
        return str.replace("#", "%23").replace("&", "%26").replace("{", "%7B").replace("}", "%7D");
    }

    private static String c(String str) {
        return str.length() > 37 ? String.valueOf(str.substring(0, 35)) + ".." : str;
    }

    public final void a(OutputStream outputStream) {
        char c2;
        char c3;
        boolean z;
        long j2;
        PrintWriter printWriter = new PrintWriter(outputStream);
        this.f = "";
        this.e = "";
        boolean z2 = PreferenceManager.getDefaultSharedPreferences(this.l).getBoolean("displayDirSize", false);
        this.g = "?";
        this.h = "?s=n&amp;o=a";
        this.i = "?s=m&amp;o=a";
        this.j = "?s=s&amp;o=a";
        this.k = "";
        if (!this.c.containsKey("s")) {
            this.c.setProperty("s", "n");
        }
        if (!this.c.containsKey("o")) {
            this.c.setProperty("o", "a");
        }
        if (this.c.getProperty("s").compareTo("m") == 0) {
            c2 = 2;
            if (this.c.getProperty("o").compareTo("d") == 0) {
                c3 = 65535;
                this.i = "?s=m&amp;o=a";
                this.g = "?s=m&amp;o=d&";
            } else {
                c3 = 1;
                this.i = "?s=m&amp;o=d";
                this.g = "?s=m&amp;o=a&";
            }
        } else if (this.c.getProperty("s").compareTo("s") == 0) {
            c2 = 3;
            if (this.c.getProperty("o").compareTo("d") == 0) {
                c3 = 65535;
                this.j = "?s=s&amp;o=a";
                this.g = "?s=s&amp;o=d&";
            } else {
                c3 = 1;
                this.j = "?s=s&amp;o=d";
                this.g = "?s=s&amp;o=a&";
            }
        } else {
            c2 = 1;
            if (this.c.getProperty("o").compareTo("d") == 0) {
                c3 = 65535;
                this.h = "?s=n&amp;o=a";
                this.g = "?s=m&amp;o=d&";
            } else {
                c3 = 1;
                this.h = "?s=n&amp;o=d";
                this.g = "?";
            }
        }
        if (!this.c.containsKey("uploader") || !this.c.getProperty("uploader").equals("basic")) {
            z = false;
        } else {
            z = true;
            this.k = "uploader=basic";
            this.h = String.valueOf(this.h) + "&amp;uploader=basic";
            this.j = String.valueOf(this.j) + "&amp;uploader=basic";
            this.i = String.valueOf(this.i) + "&amp;uploader=basic";
        }
        File[] listFiles = this.b.listFiles();
        this.f = this.b.getParent();
        this.e = this.b.getAbsolutePath();
        if (!this.e.endsWith("/")) {
            this.e = String.valueOf(this.e) + "/";
        }
        if (z2) {
            String str = this.e;
            if (a()) {
                z2 = false;
            }
        }
        boolean canWrite = this.b.canWrite();
        String upperCase = Locale.getDefault().getLanguage().toUpperCase(Locale.US);
        if (upperCase.equals("")) {
            upperCase = "EN";
        }
        StringBuilder sb = new StringBuilder(1000);
        StringBuilder sb2 = new StringBuilder(1000);
        sb.append(" " + a(z.currentdir) + ": /");
        String[] split = this.e.substring(1).split("/", 0);
        for (int i2 = 0; i2 < split.length; i2++) {
            if (!split[i2].equals("/") && split[i2].length() > 0) {
                sb.append("<a href=\"/" + b(String.valueOf(sb2.toString()) + split[i2]) + "/?" + this.k + "\">" + split[i2] + "/</a>");
                sb2.append(String.valueOf(split[i2]) + "/");
            }
        }
        sb.append(" <a href=\"" + b(this.e) + this.g + this.k + "\"><img src=\"" + af.a("/wftres.wft?f=action_icon_reload.png") + "\" alt=\"\" ></a>");
        printWriter.print("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\r\n<html>\r\n<head>\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n<title>" + this.b.getName() + " - " + v.d() + "</title>\r\n<link rel=\"shortcut icon\" href=\"" + af.a("/wftres.wft?f=ficon.ico") + "\">\r\n<link rel=\"stylesheet\" type=\"text/css\" href=\"" + af.a("/wftres.wft?f=wft.css") + "\">\r\n");
        printWriter.print("<script type=\"text/javascript\" src=\"" + af.a("/wftres.wft?f=jquery161.min.js") + "\"></script>\r\n<script type=\"text/javascript\">jQuery.noConflict();</script>\r\n<script type=\"text/javascript\" src=\"" + af.a("/wftres.wft?f=mootools.js") + "\"></script>\r\n<script type=\"text/javascript\" src=\"" + af.a("/wftres.wft?f=jq-plugins.js") + "\"></script>\r\n<script type=\"text/javascript\" src=\"" + af.b(upperCase) + "\"></script>\r\n");
        if (!z && new File(this.e).canWrite()) {
            printWriter.print("<script type=\"text/javascript\" src=\"" + v.a() + "\"></script>\r\n");
        }
        printWriter.print("<script type=\"text/javascript\" src=\"" + af.a("/wftres.wft?f=wft.js") + "\"></script>\r\n<style type=\"text/css\">body {background-color: " + ac.a(this.l, 0) + "; color: " + ac.a(this.l, 4) + ";} .how-btn {background:url(" + af.a("/wftres.wft?f=how-bg.png") + ") no-repeat;} .upgrade-btn {background:url(" + af.a("/wftres.wft?f=upg-bg.png") + ") no-repeat;} .tableContainer {background: url(" + af.a("/wftres.wft?f=bg-tabs-trans.png") + ") repeat;} .action_bar {background:url(" + af.a("/wftres.wft?f=bg-t-tr.png") + ") repeat-x;} #cboxLoadingGraphic{background:url(" + af.a("/wftres.wft?f=loading.gif") + ") no-repeat center center;} #cboxPrevious{background:url(" + af.a("/wftres.wft?f=controls.png") + ") no-repeat -75px 0;} #cboxNext{background:url(" + af.a("/wftres.wft?f=controls.png") + ") no-repeat -50px 0;} #cboxClose{background:url(" + af.a("/wftres.wft?f=controls.png") + ") no-repeat -25px 0;} #uploader-status .progress {background:url(" + af.a("/wftres.wft?f=progress.gif") + ") no-repeat;} .ui-state-default, .ui-widget-content .ui-state-default {background: #e6e6e6 url(" + af.a("/wftres.wft?f=bg-tabs-trans-nor.png") + ") repeat;} .ui-state-hover, .ui-widget-content .ui-state-hover, .ui-state-focus, .ui-widget-content .ui-state-focus {background: url(" + af.a("/wftres.wft?f=hover-tab.png") + ") repeat-x;} .ui-state-active, .ui-widget-content .ui-state-active {background: url(" + af.a("/wftres.wft?f=bg-tabs-trans.png") + ") repeat;} .ui-tabs .ui-tabs-panel {background:url(" + af.a("/wftres.wft?f=bg-tabs-trans.png") + ") repeat ;} </style></head>\r\n");
        printWriter.print(v.a(this.g, this.k, 201) + "<div style=\"min-width: 1180px\"><table id=\"maintable\" cellspacing=\"0\" cellpadding=\"0\"><tr><td></td><td height=\"1\" width=\"350\"></td></tr><tr><td id=\"maintable_left\"><form method=\"POST\" action=\"\" enctype=\"multipart/form-data\" name=\"filelist\"><input type=\"hidden\" name=\"action\"><input type=\"hidden\" name=\"data_file\"><input type=\"hidden\" name=\"data_currentParams\" value=\"" + this.g + this.k + "\"><input type=\"hidden\" name=\"data_filepath\" value=\"" + b(this.e) + "\"><div class=\"tableContainer ui-corner-all\"><div><table class=\"table_headline\"><tr><td><img src=\"" + af.a("/wftres.wft?f=table_icon_current.png") + "\" alt=\"\" ></td><td>" + sb.toString() + "</td></tr></table></div><div class=\"bdr ui-corner-all\"><div id=\"msg_container\"><div id=\"msg_div\"></div></div><table id=\"filetable\" width=\"96%\" cellspacing=\"0\" cellpadding=\"6\" class=\"bdr ui-corner-all\">" + a(new File(this.e).canWrite(), this.e) + "<tr class=\"filetable_entry brd-top txt-bold\"><td colspan=\"2\">&#160;</td><td><a href=\"" + b(this.e) + this.h + "\">" + a(z.name) + "</a></td><td><a href=\"" + b(this.e) + this.i + "\">" + a(z.lastmodified) + "</a></td><td><a href=\"" + b(this.e) + this.j + "\">" + a(z.size) + "</a></td><td>" + a(z.actions) + "</td></tr>\r\n");
        if (this.f != null) {
            if (!this.f.endsWith("/")) {
                this.f = String.valueOf(this.f) + "/";
            }
            printWriter.print("<tr class=\"filetable_entry brd-bot \"><td>&#160;</td><td><a href=\"" + b(this.f) + "?" + this.k + "\"><img src=\"" + af.a("/wftres.wft?f=action_icon_up.png") + "\" alt=\"\" ></a></td><td><a href=\"" + b(this.f) + "?" + this.k + "\">[" + a(z.parentdir) + "]</a></td><td colspan=\"3\">&#160;</td></tr>\r\n\r\n");
        }
        printWriter.flush();
        int i3 = 0;
        int i4 = 0;
        if (listFiles != null) {
            Bundle[] bundleArr = new Bundle[listFiles.length];
            for (int i5 = 0; i5 < listFiles.length; i5++) {
                bundleArr[i5] = new Bundle(6);
            }
            j2 = 0;
            for (int i6 = 0; i6 < listFiles.length; i6++) {
                if (listFiles[i6].isDirectory()) {
                    bundleArr[i6].putInt("type", 1);
                    bundleArr[i6].putBoolean("writable", listFiles[i6].canWrite());
                    bundleArr[i6].putString("name", listFiles[i6].getName());
                    bundleArr[i6].putLong("modified", listFiles[i6].lastModified());
                    bundleArr[i6].putLong("size", 0);
                    bundleArr[i6].putString("icon", af.a());
                    i4++;
                } else {
                    bundleArr[i6].putInt("type", 2);
                    bundleArr[i6].putBoolean("writable", listFiles[i6].canWrite());
                    bundleArr[i6].putString("name", listFiles[i6].getName());
                    bundleArr[i6].putLong("modified", listFiles[i6].lastModified());
                    long length = listFiles[i6].length();
                    bundleArr[i6].putLong("size", length);
                    Bundle bundle = bundleArr[i6];
                    File file = listFiles[i6];
                    Context context = this.l;
                    bundle.putString("icon", af.a(file));
                    i3++;
                    j2 += length;
                }
            }
            al alVar = new al(bundleArr);
            if (c2 == 1 && c3 == 65535) {
                Arrays.sort(alVar.a, new am(alVar));
            } else {
                Arrays.sort(alVar.a, new an(alVar));
            }
            if (c2 == 2) {
                if (c3 == 65535) {
                    Arrays.sort(alVar.a, new ao(alVar));
                } else {
                    Arrays.sort(alVar.a, new ap(alVar));
                }
            }
            if (c2 == 3) {
                if (c3 == 65535) {
                    Arrays.sort(alVar.a, new aq(alVar));
                } else {
                    Arrays.sort(alVar.a, new ar(alVar));
                }
            }
            Arrays.sort(alVar.a, new as(alVar));
            Bundle[] bundleArr2 = alVar.a;
            for (int i7 = 0; i7 < bundleArr2.length; i7++) {
                StringBuilder sb3 = new StringBuilder(1000);
                if (bundleArr2[i7].getInt("type") == 1) {
                    if (i7 % 2 != 0) {
                        sb3.append("<tr class=\"filetable_entry_alt\">");
                    } else {
                        sb3.append("<tr class=\"filetable_entry\">");
                    }
                    sb3.append("<td class=\"checkbox_cell\"><input type=\"checkbox\" name=\"" + bundleArr2[i7].getString("name") + "\" class=\"chkbox\" value=\"dir\"></td><td class=\"icon_cell\"><a href=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "/?" + this.k + "\" title=\"" + bundleArr2[i7].getString("name") + "\"><img src=\"" + af.a("/wftres.wft?f=folder_icon.png") + "\" alt=\"\" ></a></td><td><a href=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "/?" + this.k + "\" title=\"" + bundleArr2[i7].getString("name") + "\">" + c(bundleArr2[i7].getString("name")) + "</a></td><td>" + m.format(Long.valueOf(bundleArr2[i7].getLong("modified"))));
                    if (z2) {
                        sb3.append("</td><td class=\"size_cell\"><span class=\"dirsize\" dirpath=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "\" lastmodified=\"" + Long.toString(bundleArr2[i7].getLong("modified")) + "\">-</span></td><td>");
                    } else {
                        sb3.append("</td><td class=\"size_cell\">-</td><td>");
                    }
                    if (!bundleArr2[i7].getString("icon").equals("/wftres.wft?f=file_icon_locked.png")) {
                        sb3.append("<a href=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + ".zip?cd=mzip&amp;files=" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "\" title=\"" + bundleArr2[i7].getString("name") + "\">" + a(z.actiondownload) + "</a>");
                    }
                } else {
                    if (i7 % 2 != 0) {
                        sb3.append("<tr class=\"filetable_entry_alt\">");
                    } else {
                        sb3.append("<tr class=\"filetable_entry\">");
                    }
                    sb3.append("<td class=\"checkbox_cell\"><input type=\"checkbox\" name=\"" + bundleArr2[i7].getString("name") + "\" class=\"chkbox\" value=\"file\"></td>");
                    sb3.append("<td class=\"icon_cell\">");
                    if (bundleArr2[i7].getString("icon").startsWith("/wftres.wft?f=file_icon_image.png") || bundleArr2[i7].getString("icon").startsWith("/wftres.wft?f=file_icon_video.png")) {
                        if (bundleArr2[i7].getString("icon").startsWith("/wftres.wft?f=file_icon_video.png")) {
                            sb3.append("<a href=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "\" title=\"" + bundleArr2[i7].getString("name") + "\" class=\"thumb\"><img src=\"" + bundleArr2[i7].getString("icon") + "\" alt=\"\" class=\"lazy\" style=\"display: none\" data-original=\"" + af.b(String.valueOf(this.e) + bundleArr2[i7].getString("name"), Long.toString(bundleArr2[i7].getLong("modified"))) + "\" onload=\"showPlaceholder(this)\"><noscript><img src=\"" + af.b(String.valueOf(this.e) + bundleArr2[i7].getString("name"), Long.toString(bundleArr2[i7].getLong("modified"))) + "\"  alt=\"\"></noscript><b><img src=\"" + bundleArr2[i7].getString("icon") + "\" alt=\"\" class=\"lazy\" style=\"width: 150px; height: 150px; display: none\" data-original=\"" + af.b(String.valueOf(this.e) + bundleArr2[i7].getString("name"), Long.toString(bundleArr2[i7].getLong("modified"))) + "\" onload=\"showPlaceholder(this)\"></b></a></td><td><a href=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "\" title=\"" + bundleArr2[i7].getString("name") + "\">" + c(bundleArr2[i7].getString("name")) + "</a></td>");
                        } else {
                            sb3.append("<a href=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "\" title=\"" + bundleArr2[i7].getString("name") + "\" class=\"thumb\" rel=\"cb\"><img src=\"" + bundleArr2[i7].getString("icon") + "\" alt=\"\" class=\"lazy\" style=\"display: none\" data-original=\"" + af.a(String.valueOf(this.e) + bundleArr2[i7].getString("name"), Long.toString(bundleArr2[i7].getLong("modified"))) + "\" onload=\"showPlaceholder(this)\"><noscript><img src=\"" + af.a(String.valueOf(this.e) + bundleArr2[i7].getString("name"), Long.toString(bundleArr2[i7].getLong("modified"))) + "\" alt=\"\"></noscript><b><img src=\"" + bundleArr2[i7].getString("icon") + "\" alt=\"\" class=\"lazy\" style=\"width: 150px; height: 150px; display: none\" data-original=\"" + af.a(String.valueOf(this.e) + bundleArr2[i7].getString("name"), Long.toString(bundleArr2[i7].getLong("modified"))) + "\" onload=\"showPlaceholder(this)\"></b></a></td><td><a href=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "\" title=\"" + bundleArr2[i7].getString("name") + "\">" + c(bundleArr2[i7].getString("name")) + "</a></td>");
                        }
                    } else if (!bundleArr2[i7].getString("icon").startsWith("/wftres.wft?f=file_icon_locked.png")) {
                        sb3.append("<a href=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "\" title=\"" + bundleArr2[i7].getString("name") + "\"><img src=\"" + bundleArr2[i7].getString("icon") + "\" alt=\"\" ></a></td><td><a href=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "\" title=\"" + bundleArr2[i7].getString("name") + "\">" + c(bundleArr2[i7].getString("name")) + "</a></td>");
                    } else {
                        sb3.append("<img src=\"" + bundleArr2[i7].getString("icon") + "\" alt=\"\" ></td><td>" + c(bundleArr2[i7].getString("name")) + "</td>");
                    }
                    sb3.append("<td>" + m.format(Long.valueOf(bundleArr2[i7].getLong("modified"))) + "</td><td class=\"size_cell\">" + a(bundleArr2[i7].getLong("size")) + "</td><td>");
                    if (!bundleArr2[i7].getString("icon").startsWith("/wftres.wft?f=file_icon_locked.png")) {
                        sb3.append("<a href=\"" + b(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "?cd=at\" title=\"" + bundleArr2[i7].getString("name") + "\">" + a(z.actiondownload) + "</a>");
                    }
                }
                if (bundleArr2[i7].getBoolean("writable") && canWrite) {
                    sb3.append(" | <a href=\"#\" onClick=\"return confirmDel('" + a(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "', '" + a(this.e) + this.g + this.k + "');\">" + a(z.actiondelete) + "</a> | <a href=\"#\" onClick=\"return confirmRen('" + a(this.e) + "','" + a(bundleArr2[i7].getString("name")) + "','" + a(this.e) + this.g + this.k + "');\">" + a(z.actionrename) + "</a> | <a href=\"#\" onClick=\"return confirmCopy('" + a(this.e) + "','" + a(bundleArr2[i7].getString("name")) + "', '" + a(this.e) + this.g + this.k + "');\">" + a(z.actioncopy) + "</a>\r\n");
                } else if (canWrite) {
                    sb3.append(" | <a href=\"#\" onClick=\"return confirmCopy('" + a(this.e) + "','" + a(bundleArr2[i7].getString("name")) + "', '" + a(this.e) + this.g + this.k + "');\">" + a(z.actioncopy) + "</a>");
                }
                if (bundleArr2[i7].getString("name").endsWith(".zip") && canWrite && bundleArr2[i7].getInt("type") == 2) {
                    sb3.append(" | <a href=\"#\" onClick=\"return confirmUnzip('" + a(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "', '" + a(this.e) + this.g + this.k + "');\">" + a(z.actionunzip) + "</a>");
                }
                if (bundleArr2[i7].getString("name").endsWith(".apk") && bundleArr2[i7].getInt("type") == 2) {
                    sb3.append(" | <a href=\"#\" onClick=\"return confirmInstall('" + a(String.valueOf(this.e) + bundleArr2[i7].getString("name")) + "', '" + a(this.e) + this.g + this.k + "');\">" + a(z.actioninstall) + "</a>");
                }
                if (bundleArr2[i7].getInt("type") == 1 && canWrite) {
                    sb3.append(" | <a href=\"#\" onClick=\"return confirmZip('" + a(this.e) + "','" + a(bundleArr2[i7].getString("name")) + "','" + a(this.e) + this.g + this.k + "');\">" + a(z.actionzip) + "</a>");
                }
                sb3.append("</td></tr>");
                printWriter.print(sb3);
                printWriter.flush();
            }
        } else {
            j2 = 0;
        }
        int a2 = this.a.a();
        printWriter.print("<tr><td colspan=\"6\">&#160;</td></tr><tr><td colspan=\"2\">&#160;</td><td colspan=\"4\"><span class=\"text_075em\">" + a(z.total) + ": " + a(j2) + " " + a(z.in) + " " + i3 + " " + a(z.files) + " / " + i4 + " " + a(z.directories) + "</span></td></tr><tr><td colspan=\"6\">&#160;</td></tr>" + a(new File(this.e).canWrite(), this.e) + "</table></div></div></form></td>");
        printWriter.print("<td id=\"maintable_right\"><div class=\"ui-corner-all tableContainer\"><div><table class=\"table_headline\"><tr><td><img src=\"" + af.a("/wftres.wft?f=table_icon_device.png") + "\" alt=\"\" ></td><td>" + Build.MODEL + "</td></tr></table><table class=\"bdr1 ui-corner-all\" id=\"statustable\"><tr><td colspan=\"3\"><span class=\"text_075em\">" + a(z.spaceavailable) + "</span></td></tr>");
        for (a aVar : com.smarterdroid.wftlib.a.d.b()) {
            if (aVar.e()) {
                printWriter.print("<tr><td><span class=\"text_075em\"><a href=\"" + aVar.b() + File.separator + this.g + this.k + "\">" + com.smarterdroid.wftlib.a.d.a(aVar, a(z.internalstorage), a(z.sdcard), a(z.usbstorage)) + "</a>:</span></td><td><div class=\"text_075em\" style=\"width: 100px; height: 18px; background:url(" + af.a("/wftres.wft?f=progress.gif") + ") no-repeat; background-position: " + (((int) (100.0d * ((b(((Long) f.a(aVar.a()).second).longValue()) - b(((Long) f.a(aVar.a()).first).longValue())) / b(((Long) f.a(aVar.a()).second).longValue())))) - 250) + "px 0px;\"></div></td><td><span class=\"text_075em\">" + b(((Long) f.a(aVar.a()).first).longValue()) + " / " + b(((Long) f.a(aVar.a()).second).longValue()) + " " + a(z.gb) + "</span></td></tr>");
            }
        }
        printWriter.print("<tr><td colspan=\"3\"><span class=\"text_075em\">&#160;</span></td></tr><tr><td><span class=\"text_075em\">" + a(z.batterylevel) + ":</span></td><td><div class=\"text_075em\" style=\"width: 100px; height: 18px; background:url(" + af.a("/wftres.wft?f=progress.gif") + ") no-repeat; background-position: " + (a2 - 250) + "px 0px;\"></div></td><td><span class=\"text_075em\">" + a2 + "%</span></td></tr><tr><td><span class=\"text_075em\">" + a(z.wifissid) + ":</span></td><td colspan=\"2\"><span class=\"text_075em\">" + this.a.d() + "</span></td></tr></table></div>");
        if (new File(this.e).canWrite()) {
            String str2 = "";
            if (this.d.getProperty("user-agent", "none").indexOf("Macintosh") > 0) {
                str2 = "<div id=\"macMSG\"></div>";
            }
            printWriter.print("<form action=\"" + b(this.e) + "\" method=\"post\" enctype=\"multipart/form-data\" name=\"uploadform\" id=\"form-uploader\" onSubmit=\"return updateProgress('" + b(a(this.e)) + "');\"><input type=\"hidden\" name=\"data_bytesAvailable\" value=\"" + this.a.a(this.e) + "\"><input type=\"hidden\" name=\"data_currentParams\" value=\"" + this.g + this.k + "\"><table class=\"table_headline\"><tr><td><img src=\"" + af.a("/wftres.wft?f=table_icon_upload.png") + "\" alt=\"\" ></td><td>" + a(z.uploadfiles) + "</td></tr></table><div id=\"uploadtable\" class=\"bdr1 ui-corner-all\"><div id=\"uploader-status\" class=\"hide\"><div><div><strong class=\"overall-title\"></strong></div><img src=\"" + af.a("/wftres.wft?f=bar.gif") + "\" class=\"progress overall-progress\" alt=\"\" ><span class=\"overall-progress-text\"></span></div><div><div><strong class=\"current-title\"></strong></div><img src=\"" + af.a("/wftres.wft?f=bar.gif") + "\" class=\"progress current-progress\" alt=\"\" ><span class=\"current-progress-text\"></span></div><div class=\"current-text\"></div></div><div id=\"fupload-container\"><div id=\"uploader-browse\" class=\"plupload_button hide\" >" + a(z.selectfiles) + "...</div><div id=\"uploader-upload-container\" class=\"hide\"><a id=\"uploader-upload\" class=\"hide plupload_button\"> " + a(z.startupload) + "</a></div>" + v.c() + "<div id=\"submitbuttons\"><input type=\"submit\" id=\"basicsubmit\" value=\"" + a(z.startupload) + "\"> <input type=\"reset\" id=\"basicreset\" value=\"Reset\"></div></div><div id=\"overwrite-option\" class=\"text_075em hide\"><input type=\"checkbox\" name=\"overwrite\" id=\"overwrite\" value=\"true\"> " + a(z.overwriteexistingfiles) + " </div>" + str2 + "<ul id=\"uploader-list\"></ul></div><div id=\"uploader\" class=\"hide\"><div class=\"plupload_wrapper plupload_scroll\"><div id=\"uploader_container\" class=\"plupload_container\"><div class=\"plupload\"><div class=\"plupload_content\"><div><div class=\"plupload_buttons\"><div><a href=\"#\" class=\"plupload_button plupload_add\">" + a(z.selectdirectory) + "...</a></div><div class=\"plupload_start hide\"><a href=\"#\" class=\"plupload_button plupload_start hide\">" + a(z.startupload) + "</a></div><div id=\"ploverwrite-option\" class=\"text_075em hide\"><input type=\"checkbox\" name=\"ploverwrite\" id=\"ploverwrite\" value=\"true\"> " + a(z.overwriteexistingfiles) + " </div></div><div class=\"plupload_clearer\">&nbsp;</div></div><ul id=\"uploader_filelist\" class=\"plupload_filelist hide\"></ul></div></div></div><input type=\"hidden\" id=\"uploader_count\" name=\"uploader_count\" value=\"0\" /></div></div><div style=\"padding:5px;\"></div><div id=\"uploader-instructions\" class=\"text_075em\"><div>" + a(z.holdctrl) + "</div><div>" + a(z.fileswillbesaved) + "</div></div>");
            if (!z) {
                printWriter.print("<span class=\"text_075em\" id=\"switch-to-basic\"><br><a href=\"" + b(this.e) + this.g + "uploader=basic\">" + a(z.trybasicuploadform) + "</a></span></div>");
            } else {
                printWriter.print("<span class=\"text_075em\"><a href=\"" + b(this.e) + this.g + "\">" + a(z.switchtoflashuploader) + "</a></span></div>");
            }
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(new aa(this.l).a);
            if (((defaultSharedPreferences.getBoolean("dontaskagain", false) || defaultSharedPreferences.getLong("launch_count", 0) <= 3) ? (char) 601 : 602) == 602) {
                printWriter.print("<div id=\"rate-msg\"><table class=\"table_headline\"><tr><td><img src=\"" + af.a("/wftres.wft?f=table_icon_message.png") + "\" alt=\"\" ></td><td>" + a(z.hithere) + "</td></tr></table><div class=\"bdr1 ui-corner-all\"><div class=\"text_075em\">" + a(z.ifyoulikeourapp) + "</div><div style=\"padding:5px;\"></div><div class=\"text_075em\"><a href=\"https://market.android.com/details?id=" + this.l.getPackageName() + "&amp;reviewId=0\" target=\"_blank\" onClick=\"dontAskAgain();\"><img src=\"" + af.a("/wftres.wft?f=action_icon_accept.png") + "\" alt=\"\" >" + a(z.ratethisapp) + "</a></div><div class=\"text_075em\"><a href=\"/dontaskagain.wft\" onClick=\"return dontAskAgain();\"><img src=\"/wftres.wft?f=action_icon_cancel.png\" alt=\"\" > " + a(z.dontshowthismsgagain) + "</a></div></div></div>");
            }
            printWriter.print("</form></div>");
        } else {
            printWriter.print("<table class=\"table_headline\"><tr><td><img src=\"" + af.a("/wftres.wft?f=table_icon_upload.png") + "\" alt=\"\" ></td><td>" + a(z.uploadfiles) + "</td></tr></table><div id=\"uploadtable\" class=\"bdr1 ui-corner-all\"><div style=\"padding:5px;\"></div><div class=\"text_075em\">" + a(z.directorynotwriteable) + "</div><div style=\"padding:5px;\"></div></form></div>");
        }
        printWriter.print("</td></tr><tr><td colspan=\"2\"><span class=\"text_075em\">Nuvola icons by <a href=\"http://www.icon-king.com\">David Vignoni</a></span></td></tr></table></div><div id=\"progress_dialog\" class=\"msg_box\"><div><img src=\"" + af.a("/wftres.wft?f=loading.gif") + "\" alt=\"\" ></div><div>" + a(z.processingpleasewait) + "</div></div><div id=\"fade\" class=\"black_overlay\"></div><div class=\"clr\"></div>" + v.b() + "</body></html>");
        printWriter.flush();
        printWriter.close();
    }
}
