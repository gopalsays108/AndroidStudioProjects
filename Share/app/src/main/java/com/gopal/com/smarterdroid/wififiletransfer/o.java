package com.smarterdroid.wififiletransfer;

final class o implements Runnable {
    final /* synthetic */ WFTPanel a;

    o(WFTPanel wFTPanel) {
        this.a = wFTPanel;
    }

    public final void run() {
        this.a.b().scrollTo(0, 200);
    }
}
