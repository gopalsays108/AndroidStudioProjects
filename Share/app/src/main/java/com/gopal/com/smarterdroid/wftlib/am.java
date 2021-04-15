package com.smarterdroid.wftlib;

import android.os.Bundle;
import java.util.Comparator;
import java.util.Locale;

final class am implements Comparator {
    final /* synthetic */ al a;

    am(al alVar) {
        this.a = alVar;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((Bundle) obj2).getString("name").toLowerCase(Locale.US).compareTo(((Bundle) obj).getString("name").toLowerCase(Locale.US));
    }
}
