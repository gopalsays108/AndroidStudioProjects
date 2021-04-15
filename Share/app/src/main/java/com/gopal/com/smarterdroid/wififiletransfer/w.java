package com.smarterdroid.wififiletransfer;

import android.preference.Preference;

final class w implements Preference.OnPreferenceChangeListener {
    final /* synthetic */ WFTPreferences a;

    w(WFTPreferences wFTPreferences) {
        this.a = wFTPreferences;
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0083  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onPreferenceChange(android.preference.Preference r9, java.lang.Object r10) {
        /*
            r8 = this;
            r3 = 107(0x6b, float:1.5E-43)
            r1 = 0
            r0 = 1
            java.lang.String r2 = r10.toString()
            java.lang.String r4 = r2.trim()
            r2 = 108(0x6c, float:1.51E-43)
            java.lang.String r5 = ""
            boolean r5 = r4.equals(r5)
            if (r5 != 0) goto L_0x0081
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch:{ NumberFormatException -> 0x0080 }
            int r4 = r4.intValue()     // Catch:{ NumberFormatException -> 0x0080 }
            r5 = 1024(0x400, float:1.435E-42)
            if (r4 <= r5) goto L_0x0078
            r5 = 65536(0x10000, float:9.18355E-41)
            if (r4 >= r5) goto L_0x0078
            java.lang.String r5 = r9.getKey()     // Catch:{ NumberFormatException -> 0x0080 }
            java.lang.String r6 = "port"
            boolean r5 = r5.equals(r6)     // Catch:{ NumberFormatException -> 0x0080 }
            if (r5 == 0) goto L_0x0057
            com.smarterdroid.wififiletransfer.WFTPreferences r5 = r8.a     // Catch:{ NumberFormatException -> 0x0080 }
            android.content.Context r5 = r5.getApplicationContext()     // Catch:{ NumberFormatException -> 0x0080 }
            android.content.SharedPreferences r5 = android.preference.PreferenceManager.getDefaultSharedPreferences(r5)     // Catch:{ NumberFormatException -> 0x0080 }
            java.lang.String r6 = "sslport"
            java.lang.String r7 = "2345"
            java.lang.String r5 = r5.getString(r6, r7)     // Catch:{ NumberFormatException -> 0x0080 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ NumberFormatException -> 0x0080 }
            int r5 = r5.intValue()     // Catch:{ NumberFormatException -> 0x0080 }
            if (r4 == r5) goto L_0x0054
            r3 = r0
        L_0x004f:
            if (r3 == 0) goto L_0x0083
            com.smarterdroid.wififiletransfer.WFTPanel.a = r0
        L_0x0053:
            return r0
        L_0x0054:
            r2 = r3
            r3 = r1
            goto L_0x004f
        L_0x0057:
            com.smarterdroid.wififiletransfer.WFTPreferences r5 = r8.a     // Catch:{ NumberFormatException -> 0x0080 }
            android.content.Context r5 = r5.getApplicationContext()     // Catch:{ NumberFormatException -> 0x0080 }
            android.content.SharedPreferences r5 = android.preference.PreferenceManager.getDefaultSharedPreferences(r5)     // Catch:{ NumberFormatException -> 0x0080 }
            java.lang.String r6 = "port"
            java.lang.String r7 = "1234"
            java.lang.String r5 = r5.getString(r6, r7)     // Catch:{ NumberFormatException -> 0x0080 }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ NumberFormatException -> 0x0080 }
            int r5 = r5.intValue()     // Catch:{ NumberFormatException -> 0x0080 }
            if (r4 == r5) goto L_0x0075
            r3 = r0
            goto L_0x004f
        L_0x0075:
            r2 = r3
            r3 = r1
            goto L_0x004f
        L_0x0078:
            r3 = 84712(0x14ae8, float:1.18707E-40)
            if (r4 != r3) goto L_0x0081
            r2 = r1
            r3 = r1
            goto L_0x004f
        L_0x0080:
            r3 = move-exception
        L_0x0081:
            r3 = r1
            goto L_0x004f
        L_0x0083:
            if (r2 == 0) goto L_0x008a
            com.smarterdroid.wififiletransfer.WFTPreferences r0 = r8.a
            r0.showDialog(r2)
        L_0x008a:
            r0 = r1
            goto L_0x0053
        */
        throw new UnsupportedOperationException("Method not decompiled: com.smarterdroid.wififiletransfer.w.onPreferenceChange(android.preference.Preference, java.lang.Object):boolean");
    }
}
