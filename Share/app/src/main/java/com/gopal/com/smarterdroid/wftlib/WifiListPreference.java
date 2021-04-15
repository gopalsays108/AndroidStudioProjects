package com.smarterdroid.wftlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.preference.ListPreference;
import android.util.AttributeSet;
import java.util.List;

public class WifiListPreference extends ListPreference {
    private Context a;

    public WifiListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        try {
            this.a = context;
            List<WifiConfiguration> configuredNetworks = ((WifiManager) context.getSystemService("wifi")).getConfiguredNetworks();
            CharSequence[] charSequenceArr = new CharSequence[(configuredNetworks.size() + 1)];
            CharSequence[] charSequenceArr2 = new CharSequence[(configuredNetworks.size() + 1)];
            charSequenceArr[0] = a(z.autostartoff);
            charSequenceArr2[0] = "autostartoff";
            int i = 1;
            for (WifiConfiguration next : configuredNetworks) {
                charSequenceArr[i] = a(next);
                charSequenceArr2[i] = b(next);
                i++;
            }
            setEntries(charSequenceArr);
            setEntryValues(charSequenceArr2);
        } catch (Exception e) {
            setEntries(new CharSequence[]{a(z.autostartoff)});
            setEntryValues(new CharSequence[]{"autostartoff"});
        }
    }

    private String a(int i) {
        return this.a.getString(i);
    }

    private String a(WifiConfiguration wifiConfiguration) {
        String str = "";
        if (wifiConfiguration != null) {
            try {
                if (wifiConfiguration.SSID != null) {
                    str = wifiConfiguration.SSID;
                }
            } catch (Exception e) {
                return "(" + a(z.hidden) + ")";
            }
        }
        if (str.length() > 2 && str.startsWith("\"") && str.endsWith("\"")) {
            str = str.substring(1, str.length() - 1);
        }
        return "".equals(str) ? "(" + a(z.hidden) + ")" : str;
    }

    private static String b(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration != null) {
            try {
                if (wifiConfiguration.SSID != null) {
                    return String.valueOf(wifiConfiguration.SSID.replaceAll("^\"|\"$", "")) + "||" + wifiConfiguration.networkId;
                }
            } catch (Exception e) {
            }
        }
        return "||" + System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        return "autostartoff";
    }
}
