package com.smarterdroid.wftlib;

import android.os.Bundle;
import java.util.Comparator;

final class ap implements Comparator {
    final /* synthetic */ al a;

    ap(al alVar) {
        this.a = alVar;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        Bundle bundle = (Bundle) obj;
        Bundle bundle2 = (Bundle) obj2;
        if (bundle.getLong("modified") == bundle2.getLong("modified")) {
            return 0;
        }
        return bundle.getLong("modified") > bundle2.getLong("modified") ? 1 : -1;
    }
}
