package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;

final class p implements Parcelable.Creator {
    p() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new FragmentState(parcel);
    }

    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new FragmentState[i];
    }
}
