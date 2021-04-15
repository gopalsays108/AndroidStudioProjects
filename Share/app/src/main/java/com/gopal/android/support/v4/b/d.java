package android.support.v4.b;

import android.os.Parcel;
import android.os.Parcelable;

public final class d implements Parcelable.ClassLoaderCreator {
    private final c a;

    public d(c cVar) {
        this.a = cVar;
    }

    public final Object createFromParcel(Parcel parcel) {
        return this.a.a(parcel, (ClassLoader) null);
    }

    public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
        return this.a.a(parcel, classLoader);
    }

    public final Object[] newArray(int i) {
        return this.a.a(i);
    }
}
