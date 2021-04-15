package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentTabHost;

final class q implements Parcelable.Creator {
    q() {
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new FragmentTabHost.SavedState(parcel, (byte) 0);
    }

    public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
        return new FragmentTabHost.SavedState[i];
    }
}
