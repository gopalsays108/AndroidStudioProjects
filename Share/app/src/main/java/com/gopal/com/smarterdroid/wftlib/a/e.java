package com.smarterdroid.wftlib.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class e extends BroadcastReceiver {
    private final /* synthetic */ Runnable a = null;

    e() {
    }

    public final void onReceive(Context context, Intent intent) {
        "Storage: " + intent.getAction() + "-" + intent.getData();
        d.a();
        if (this.a != null) {
            this.a.run();
        }
    }
}
