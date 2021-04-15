package com.smarterdroid.wififiletransfer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import com.smarterdroid.wftlib.aw;

public class WFTPreferences extends PreferenceActivity {
    SharedPreferences a;

    private String a(int i) {
        return getApplicationContext().getString(i);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.a = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        addPreferencesFromResource(C0000R.xml.settings);
        w wVar = new w(this);
        ((EditTextPreference) getPreferenceScreen().findPreference("port")).setOnPreferenceChangeListener(wVar);
        ((EditTextPreference) getPreferenceScreen().findPreference("sslport")).setOnPreferenceChangeListener(wVar);
        ((EditTextPreference) getPreferenceScreen().findPreference("password")).setOnPreferenceChangeListener(new x(this));
        ((CheckBoxPreference) getPreferenceScreen().findPreference("ssl")).setOnPreferenceChangeListener(new y(this));
        aw.a(getApplicationContext(), "Preferences");
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int i) {
        switch (i) {
            case 106:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(a(C0000R.string.yourbrowserwillshow));
                builder.setTitle(a(C0000R.string.appname));
                builder.setIcon(C0000R.drawable.ic_wft2_m);
                builder.setPositiveButton(a(C0000R.string.ok), new z(this));
                return builder.create();
            case 107:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setMessage(a(C0000R.string.portscannotbeidentical));
                builder2.setTitle(a(C0000R.string.appname));
                builder2.setIcon(C0000R.drawable.ic_wft2_m);
                builder2.setPositiveButton(a(C0000R.string.ok), new aa(this));
                return builder2.create();
            case 108:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setMessage(a(C0000R.string.validvalues));
                builder3.setTitle(a(C0000R.string.appname));
                builder3.setIcon(C0000R.drawable.ic_wft2_m);
                builder3.setPositiveButton(a(C0000R.string.ok), new ab(this));
                return builder3.create();
            default:
                return null;
        }
    }
}
