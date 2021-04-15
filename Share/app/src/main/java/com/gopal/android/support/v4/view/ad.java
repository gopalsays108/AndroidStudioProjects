package android.support.v4.view;

import android.database.DataSetObserver;

final class ad extends DataSetObserver {
    final /* synthetic */ ViewPager a;

    private ad(ViewPager viewPager) {
        this.a = viewPager;
    }

    /* synthetic */ ad(ViewPager viewPager, byte b) {
        this(viewPager);
    }

    public final void onChanged() {
        this.a.a();
    }

    public final void onInvalidated() {
        this.a.a();
    }
}
