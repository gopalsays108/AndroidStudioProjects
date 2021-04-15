package com.smarterdroid.wftlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import java.io.File;
import java.util.Hashtable;

public final class s extends ae {
    public static final String[] a = {"pictures"};
    public static final String[] b = {"dcim", "pictures", "photos"};
    public static final String[] c = {"music"};
    public static final String[] d = {"movies", "videos", "video"};
    private static final Hashtable f;

    static {
        Hashtable hashtable = new Hashtable();
        f = hashtable;
        hashtable.put("picsDir", a);
        f.put("camDir", b);
        f.put("musicDir", c);
        f.put("videoDir", d);
    }

    public final String a(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("WFTPanel", 0);
        String string = sharedPreferences.getString(str, "");
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (externalStorageDirectory == null || !externalStorageDirectory.exists()) {
            return "";
        }
        if (!string.equals("") && new File(externalStorageDirectory, string).exists() && new File(externalStorageDirectory, string).isDirectory()) {
            return string;
        }
        ai aiVar = new ai((String[]) f.get(str));
        String[] list = externalStorageDirectory.list();
        for (int i = 0; i < list.length; i++) {
            if (aiVar.accept(externalStorageDirectory, list[i])) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(str, String.valueOf(externalStorageDirectory.getAbsolutePath()) + File.separator + list[i]);
                edit.commit();
                return String.valueOf(externalStorageDirectory.getAbsolutePath()) + File.separator + list[i];
            }
        }
        if (!string.equals("")) {
            SharedPreferences.Editor edit2 = sharedPreferences.edit();
            edit2.putString(str, "");
            edit2.commit();
        }
        return "";
    }
}
