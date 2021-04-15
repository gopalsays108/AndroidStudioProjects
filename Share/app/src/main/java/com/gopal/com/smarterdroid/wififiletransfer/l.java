package com.smarterdroid.wififiletransfer;

import android.content.DialogInterface;

final class l implements DialogInterface.OnClickListener {
    final /* synthetic */ WFTPanel a;

    l(WFTPanel wFTPanel) {
        this.a = wFTPanel;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.a.b(true);
    }
}
