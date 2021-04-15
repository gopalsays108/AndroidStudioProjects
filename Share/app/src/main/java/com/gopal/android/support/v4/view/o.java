package android.support.v4.view;

import android.view.View;

class o implements u {
    o() {
    }

    public int a(View view) {
        return 2;
    }

    /* access modifiers changed from: package-private */
    public long a() {
        return 10;
    }

    public void a(View view, Runnable runnable) {
        view.postDelayed(runnable, a());
    }

    public boolean a(View view, int i) {
        return false;
    }

    public void b(View view) {
        view.postInvalidateDelayed(a());
    }

    public void b(View view, int i) {
    }
}
