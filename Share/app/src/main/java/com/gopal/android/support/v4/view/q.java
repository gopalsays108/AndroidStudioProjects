package android.support.v4.view;

import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.view.View;

class q extends p {
    q() {
    }

    /* access modifiers changed from: package-private */
    public final long a() {
        return ValueAnimator.getFrameDelay();
    }

    public final void b(View view, int i) {
        view.setLayerType(i, (Paint) null);
    }
}
