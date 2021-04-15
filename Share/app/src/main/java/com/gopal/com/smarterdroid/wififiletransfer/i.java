package com.smarterdroid.wififiletransfer;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

final class i implements DialogInterface.OnClickListener {
    final /* synthetic */ WFTPanel a;

    i(WFTPanel wFTPanel) {
        this.a = wFTPanel;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        Intent intent = new Intent("android.intent.action.DELETE");
        intent.setData(Uri.parse("package:com.smarterdroid.wififiletransfer"));
        this.a.startActivity(intent);
    }
}
