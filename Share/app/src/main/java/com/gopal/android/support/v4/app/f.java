package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

final class f implements Parcelable.Creator {
    f() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new Fragment.SavedState(parcel);
    }

    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new Fragment.SavedState[i];
    }
}
