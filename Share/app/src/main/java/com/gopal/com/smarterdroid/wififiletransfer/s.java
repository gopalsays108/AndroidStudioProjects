package com.smarterdroid.wififiletransfer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

final class s implements DialogInterface.OnClickListener {
    final /* synthetic */ WFTPanel a;

    s(WFTPanel wFTPanel) {
        this.a = wFTPanel;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent("android.intent.action.DELETE");
        intent.setData(Uri.parse("package:com.smarterdroid.wififiletransfer"));
        try {
            this.a.startActivity(intent);
        } catch (Exception e) {
        }
    }
}
