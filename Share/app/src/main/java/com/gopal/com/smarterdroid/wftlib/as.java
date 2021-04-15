package com.smarterdroid.wftlib;

import android.os.Bundle;
import java.util.Comparator;

final class as implements Comparator {
    final /* synthetic */ al a;

    as(al alVar) {
        this.a = alVar;
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        return ((Bundle) obj).getInt("type") - ((Bundle) obj2).getInt("type");
    }
}
