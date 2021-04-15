package com.smarterdroid.wififiletransfer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

final class k implements DialogInterface.OnClickListener {
    final /* synthetic */ WFTPanel a;

    k(WFTPanel wFTPanel) {
        this.a = wFTPanel;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        try {
            this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.smarterdroid.wififiletransferpro&referrer=utm_source%3Dfree%26utm_medium%3Dlcnserr")));
        } catch (Exception e) {
        }
    }
}
