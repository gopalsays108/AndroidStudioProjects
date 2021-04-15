package com.smarterdroid.wififiletransfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class d extends BroadcastReceiver {
    final /* synthetic */ WFTPanel a;

    d(WFTPanel wFTPanel) {
        this.a = wFTPanel;
    }

    public final void onReceive(Context context, Intent intent) {
        this.a.a(intent);
    }
}
