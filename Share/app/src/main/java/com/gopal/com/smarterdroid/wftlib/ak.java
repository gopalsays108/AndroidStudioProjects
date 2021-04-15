package com.smarterdroid.wftlib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class ak extends BroadcastReceiver {
    final /* synthetic */ aj a;

    ak(aj ajVar) {
        this.a = ajVar;
    }

    public final void onReceive(Context context, Intent intent) {
        try {
            context.unregisterReceiver(this);
        } catch (Exception e) {
        }
        int intExtra = intent.getIntExtra("level", -1);
        int intExtra2 = intent.getIntExtra("scale", -1);
        this.a.g = -1;
        if (intExtra >= 0 && intExtra2 > 0) {
            this.a.g = (intExtra * 100) / intExtra2;
        }
    }
}
