package com.smarterdroid.wftlib;

import android.view.View;

final class p implements View.OnClickListener {
    final /* synthetic */ DirectoryPicker a;

    p(DirectoryPicker directoryPicker) {
        this.a = directoryPicker;
    }

    public final void onClick(View view) {
        this.a.a(this.a.a.getAbsolutePath());
    }
}
