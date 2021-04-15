package com.smarterdroid.wftlib;

import android.os.Bundle;
import java.util.Comparator;
import java.util.Locale;

final class an implements Comparator {
    final /* synthetic */ al a;

    an(al alVar) {
        this.a = alVar;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((Bundle) obj).getString("name").toLowerCase(Locale.US).compareTo(((Bundle) obj2).getString("name").toLowerCase(Locale.US));
    }
}
