package com.smarterdroid.wftlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import java.io.File;

@TargetApi(8)
public final class t extends ae {
    public final String a(Context context, String str) {
        File file = null;
        if (str.equals("picsDir")) {
            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        } else if (str.equals("camDir")) {
            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        } else if (str.equals("musicDir")) {
            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        } else if (str.equals("videoDir")) {
            file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        }
        return (file == null || !file.exists()) ? "" : file.getAbsolutePath();
    }
}
