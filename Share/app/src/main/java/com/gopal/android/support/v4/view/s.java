package android.support.v4.view;

import android.view.View;

class s extends r {
    s() {
    }

    public final void a(View view, Runnable runnable) {
        view.postOnAnimation(runnable);
    }

    public final void b(View view) {
        view.postInvalidateOnAnimation();
    }
}
