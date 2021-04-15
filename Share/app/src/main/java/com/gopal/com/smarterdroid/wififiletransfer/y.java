package com.smarterdroid.wififiletransfer;

import android.preference.Preference;

final class y implements Preference.OnPreferenceChangeListener {
    final /* synthetic */ WFTPreferences a;

    y(WFTPreferences wFTPreferences) {
        this.a = wFTPreferences;
    }

    public final boolean onPreferenceChange(Preference preference, Object obj) {
        WFTPanel.a = true;
        if (preference.getKey().equals("ssl") && obj.toString().equals("true")) {
            this.a.showDialog(106);
        }
        return true;
    }
}
