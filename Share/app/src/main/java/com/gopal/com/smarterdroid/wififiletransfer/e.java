package com.smarterdroid.wififiletransfer;

import android.content.DialogInterface;
import android.content.Intent;

final class e implements DialogInterface.OnClickListener {
    final /* synthetic */ WFTPanel a;

    e(WFTPanel wFTPanel) {
        this.a = wFTPanel;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        try {
            this.a.startActivity(new Intent("android.settings.WIFI_SETTINGS"));
        } catch (Exception e) {
        }
    }
}
