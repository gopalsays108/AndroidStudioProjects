package android.support.v4.b;

import android.os.Parcel;
import android.os.Parcelable;

public final class b implements Parcelable.Creator {
    final c a;

    public b(c cVar) {
        this.a = cVar;
    }

    public final Object createFromParcel(Parcel parcel) {
        return this.a.a(parcel, (ClassLoader) null);
    }

    public final Object[] newArray(int i) {
        return this.a.a(i);
    }
}
