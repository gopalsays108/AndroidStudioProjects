package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;

final class o implements Parcelable.Creator {
    o() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new FragmentManagerState(parcel);
    }

    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new FragmentManagerState[i];
    }
}
