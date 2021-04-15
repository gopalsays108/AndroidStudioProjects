package android.support.v4.view;

import android.view.View;
import java.util.Comparator;

final class af implements Comparator {
    af() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        z zVar = (z) ((View) obj).getLayoutParams();
        z zVar2 = (z) ((View) obj2).getLayoutParams();
        return zVar.a != zVar2.a ? zVar.a ? 1 : -1 : zVar.e - zVar2.e;
    }
}
