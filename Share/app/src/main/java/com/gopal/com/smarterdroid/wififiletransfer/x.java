package com.smarterdroid.wififiletransfer;

import android.preference.Preference;

final class x implements Preference.OnPreferenceChangeListener {
    final /* synthetic */ WFTPreferences a;

    x(WFTPreferences wFTPreferences) {
        this.a = wFTPreferences;
    }

    public final boolean onPreferenceChange(Preference preference, Object obj) {
        WFTPanel.a = true;
        return true;
    }
}
