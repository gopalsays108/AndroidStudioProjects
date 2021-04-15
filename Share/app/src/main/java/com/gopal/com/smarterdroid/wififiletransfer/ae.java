package com.smarterdroid.wififiletransfer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;

final class ae extends BroadcastReceiver {
    final /* synthetic */ WFTService a;

    ae(WFTService wFTService) {
        this.a = wFTService;
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.wifi.supplicant.CONNECTION_CHANGE")) {
            if (!intent.getBooleanExtra("connected", false)) {
                this.a.i();
                this.a.j();
            }
        } else if (intent.getAction().equals("android.net.wifi.STATE_CHANGE") && ((NetworkInfo) intent.getParcelableExtra("networkInfo")).isConnected()) {
            this.a.i();
            this.a.j();
        }
    }
}
