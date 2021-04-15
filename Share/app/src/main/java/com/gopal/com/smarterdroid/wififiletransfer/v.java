package com.smarterdroid.wififiletransfer;

import java.util.TimerTask;

final class v extends TimerTask {
    final /* synthetic */ WFTPanel a;

    v(WFTPanel wFTPanel) {
        this.a = wFTPanel;
    }

    public final void run() {
        this.a.g = false;
    }
}
