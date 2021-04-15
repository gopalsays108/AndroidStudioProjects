package android.support.v4.view;

import android.view.KeyEvent;

final class c implements d {
    c() {
    }

    public final boolean a(int i) {
        return KeyEvent.metaStateHasModifiers(i, 1);
    }

    public final boolean b(int i) {
        return KeyEvent.metaStateHasNoModifiers(i);
    }
}
