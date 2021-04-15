package com.smarterdroid.wftlib;

import android.os.Bundle;
import java.util.Comparator;

final class ar implements Comparator {
    final /* synthetic */ al a;

    ar(al alVar) {
        this.a = alVar;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        Bundle bundle = (Bundle) obj;
        Bundle bundle2 = (Bundle) obj2;
        if (bundle.getLong("size") == bundle2.getLong("size")) {
            return 0;
        }
        return bundle.getLong("size") > bundle2.getLong("size") ? 1 : -1;
    }
}
