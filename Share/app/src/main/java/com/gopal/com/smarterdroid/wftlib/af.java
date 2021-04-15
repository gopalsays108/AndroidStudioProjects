package com.smarterdroid.wftlib;

import android.content.Context;
import java.io.File;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;

public final class af {
    public static String a = "&amp;v=33";
    public static final String[] b = {"pictures"};
    public static final String[] c = {"dcim", "pictures", "photos"};
    public static final String[] d = {"music"};
    public static final String[] e = {"movies", "videos", "video"};
    private static final Hashtable f;
    private static Properties g;

    static {
        Hashtable hashtable = new Hashtable();
        f = hashtable;
        hashtable.put("picsDir", b);
        f.put("camDir", c);
        f.put("musicDir", d);
        f.put("videoDir", e);
        Properties properties = new Properties();
        g = properties;
        properties.put("jpg", "/wftres.wft?f=file_icon_image.png");
        g.put("png", "/wftres.wft?f=file_icon_image.png");
        g.put("gif", "/wftres.wft?f=file_icon_image.png");
        g.put("bmp", "/wftres.wft?f=file_icon_image.png");
        g.put("ico", "/wftres.wft?f=file_icon_image.png");
        g.put("jpeg", "/wftres.wft?f=file_icon_image.png");
        g.put("mp3", "/wftres.wft?f=file_icon_sound.png");
        g.put("ogg", "/wftres.wft?f=file_icon_sound.png");
        g.put("wma", "/wftres.wft?f=file_icon_sound.png");
        g.put("wav", "/wftres.wft?f=file_icon_sound.png");
        g.put("mdi", "/wftres.wft?f=file_icon_sound.png");
        g.put("pdf", "/wftres.wft?f=file_icon_pdf.png");
        g.put("zip", "/wftres.wft?f=file_icon_zip.png");
        g.put("avi", "/wftres.wft?f=file_icon_video.png");
        g.put("flv", "/wftres.wft?f=file_icon_video.png");
        g.put("mkv", "/wftres.wft?f=file_icon_video.png");
        g.put("3gp", "/wftres.wft?f=file_icon_video.png");
        g.put("mpg", "/wftres.wft?f=file_icon_video.png");
        g.put("mp4", "/wftres.wft?f=file_icon_video.png");
        g.put("wmv", "/wftres.wft?f=file_icon_video.png");
        g.put("txt", "/wftres.wft?f=file_icon_text.png");
        g.put("vcf", "/wftres.wft?f=file_icon_vcard.png");
        g.put("mov", "/wftres.wft?f=file_icon_qt.png");
        g.put("apk", "/wftres.wft?f=file_icon_apk.png");
    }

    public static String a() {
        return "/wftres.wft?f=folder_icon.png";
    }

    public static String a(File file) {
        try {
            if (!file.canRead()) {
                return "/wftres.wft?f=file_icon_locked.png";
            }
            String name = file.getName();
            if (name.lastIndexOf(".") <= 0 || name.length() - name.lastIndexOf(".") <= 0) {
                return a("/wftres.wft?f=file_icon.png");
            }
            String lowerCase = name.substring(name.lastIndexOf(".") + 1, name.length()).toLowerCase(Locale.US);
            return g.containsKey(lowerCase) ? a(g.getProperty(lowerCase)) : a("/wftres.wft?f=file_icon.png");
        } catch (Exception e2) {
            return a("/wftres.wft?f=file_icon.png");
        }
    }

    public static String a(String str) {
        return String.valueOf(str) + a;
    }

    public static String a(String str, Context context) {
        return ae.e.a(context, str);
    }

    public static String a(String str, String str2) {
        if (str.lastIndexOf(".") <= 0 || str.length() - str.lastIndexOf(".") <= 0) {
            return a("/wftres.wft?f=file_icon.png");
        }
        String lowerCase = str.substring(str.lastIndexOf(".") + 1, str.length()).toLowerCase(Locale.US);
        return (lowerCase.equals("jpg") || lowerCase.equals("jpeg") || lowerCase.equals("gif") || lowerCase.equals("png")) ? "/thumbnail.wft?image=" + c(str) + "&amp;kind=micro&amp;lm=" + str2 : a(g.getProperty(lowerCase));
    }

    public static String b(String str) {
        return "/lang.wft?lang=" + str;
    }

    public static String b(String str, String str2) {
        if (str.lastIndexOf(".") <= 0 || str.length() - str.lastIndexOf(".") <= 0) {
            return a("/wftres.wft?f=file_icon.png");
        }
        String lowerCase = str.substring(str.lastIndexOf(".") + 1, str.length()).toLowerCase(Locale.US);
        return (lowerCase.equals("mp4") || lowerCase.equals("3gp")) ? "/thumbnail.wft?image=" + c(str) + "&amp;kind=micro&amp;type=video&amp;lm=" + str2 : a(g.getProperty(lowerCase));
    }

    private static String c(String str) {
        return str.replace("#", "%23").replace("&", "%26").replace("{", "%7B").replace("}", "%7D");
    }
}
