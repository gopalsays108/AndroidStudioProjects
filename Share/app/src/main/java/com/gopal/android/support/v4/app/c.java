package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;

final class c implements Parcelable.Creator {
    c() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new BackStackState(parcel);
    }

    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new BackStackState[i];
    }
}
