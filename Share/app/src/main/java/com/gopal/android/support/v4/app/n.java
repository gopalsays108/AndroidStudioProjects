package android.support.v4.app;

import android.view.animation.Animation;

final class n implements Animation.AnimationListener {
    final /* synthetic */ Fragment a;
    final /* synthetic */ l b;

    n(l lVar, Fragment fragment) {
        this.b = lVar;
        this.a = fragment;
    }

    public final void onAnimationEnd(Animation animation) {
        if (this.a.b != null) {
            this.a.b = null;
            this.b.a(this.a, this.a.c, 0, 0, false);
        }
    }

    public final void onAnimationRepeat(Animation animation) {
    }

    public final void onAnimationStart(Animation animation) {
    }
}
