package com.smarterdroid.wififiletransfer;

import android.content.DialogInterface;
import android.content.Intent;

final class p implements DialogInterface.OnClickListener {
    final /* synthetic */ WFTPanel a;

    p(WFTPanel wFTPanel) {
        this.a = wFTPanel;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == 0) {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("message/rfc822");
            intent.putExtra("android.intent.extra.SUBJECT", this.a.a((int) C0000R.string.wfturl));
            intent.putExtra("android.intent.extra.TEXT", String.valueOf(this.a.a((int) C0000R.string.visiturl)) + "\n\n" + WFTService.a() + "/");
            this.a.startActivity(Intent.createChooser(intent, this.a.a((int) C0000R.string.selectemailapp)));
        }
        if (i == 1) {
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.putExtra("sms_body", String.valueOf(this.a.a((int) C0000R.string.visiturl)) + "\n\n" + WFTService.a() + "/");
            intent2.setType("vnd.android-dir/mms-sms");
            this.a.startActivity(intent2);
        }
    }
}
