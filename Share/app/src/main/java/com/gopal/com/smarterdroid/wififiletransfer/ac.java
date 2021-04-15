package com.smarterdroid.wififiletransfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class ac extends BroadcastReceiver {
    final /* synthetic */ WFTService a;

    ac(WFTService wFTService) {
        this.a = wFTService;
    }

    public final void onReceive(Context context, Intent intent) {
        this.a.a(intent);
    }
}
