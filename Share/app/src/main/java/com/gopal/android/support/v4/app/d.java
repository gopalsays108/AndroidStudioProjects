package android.support.v4.app;

import android.view.View;

final class d implements j {
    final /* synthetic */ Fragment a;

    d(Fragment fragment) {
        this.a = fragment;
    }

    public final View a(int i) {
        if (this.a.I != null) {
            return this.a.I.findViewById(i);
        }
        throw new IllegalStateException("Fragment does not have a view");
    }
}
