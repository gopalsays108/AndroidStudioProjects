package android.support.v4.app;

import android.app.Notification;
import java.util.Iterator;

final class ai implements ae {
    ai() {
    }

    public final Notification a(ac acVar) {
        ak akVar = new ak(acVar.a, acVar.r, acVar.b, acVar.c, acVar.h, acVar.f, acVar.i, acVar.d, acVar.e, acVar.g, acVar.n, acVar.o, acVar.p, acVar.k, acVar.j, acVar.m);
        Iterator it = acVar.q.iterator();
        while (it.hasNext()) {
            z zVar = (z) it.next();
            akVar.a.addAction(zVar.a, zVar.b, zVar.c);
        }
        if (acVar.l != null) {
            if (acVar.l instanceof ab) {
                ab abVar = (ab) acVar.l;
                CharSequence charSequence = abVar.b;
                boolean z = abVar.d;
                CharSequence charSequence2 = abVar.c;
                Notification.BigTextStyle bigText = new Notification.BigTextStyle(akVar.a).setBigContentTitle(charSequence).bigText(abVar.a);
                if (z) {
                    bigText.setSummaryText(charSequence2);
                }
            } else if (acVar.l instanceof ad) {
                ad adVar = (ad) acVar.l;
                akVar.a(adVar.b, adVar.d, adVar.c, adVar.a);
            } else if (acVar.l instanceof aa) {
                aa aaVar = (aa) acVar.l;
                CharSequence charSequence3 = aaVar.b;
                boolean z2 = aaVar.d;
                CharSequence charSequence4 = aaVar.c;
                Notification.BigPictureStyle bigPicture = new Notification.BigPictureStyle(akVar.a).setBigContentTitle(charSequence3).bigPicture(aaVar.a);
                if (z2) {
                    bigPicture.setSummaryText(charSequence4);
                }
            }
        }
        return akVar.a.build();
    }
}
