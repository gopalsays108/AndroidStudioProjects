package android.support.v4.view;

import android.view.VelocityTracker;

final class l implements m {
    l() {
    }

    public final float a(VelocityTracker velocityTracker, int i) {
        return velocityTracker.getXVelocity(i);
    }
}
