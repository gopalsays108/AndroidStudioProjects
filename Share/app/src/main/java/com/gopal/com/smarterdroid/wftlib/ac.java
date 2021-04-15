package com.smarterdroid.wftlib;

import android.content.Context;
import android.preference.PreferenceManager;

public final class ac {
    public static final String[][] a = {new String[]{"88BCF6", "EEEEEE", "DDDDDD", "BBBBBB", "000000"}, new String[]{"88BCF6", "E1EFBB", "EFF2E6", "BBBBBB", "000000"}, new String[]{"CCCCCC", "EEEEEE", "CAE2F7", "CCCCCC", "000000"}, new String[]{"CCCCCC", "EEEEEE", "FFFFFF", "CCCCCC", "000000"}};

    public static String a(Context context, int i) {
        int i2 = 0;
        String string = PreferenceManager.getDefaultSharedPreferences(context).getString("theme", "0");
        if (string.equals("")) {
            string = "0";
        }
        try {
            i2 = Integer.valueOf(string).intValue();
        } catch (Exception e) {
        }
        return "#" + a[i2][i];
    }
}
