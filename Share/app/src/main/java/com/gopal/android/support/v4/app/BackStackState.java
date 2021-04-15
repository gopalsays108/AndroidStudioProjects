package android.support.v4.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.ArrayList;

final class BackStackState implements Parcelable {
    public static final Parcelable.Creator CREATOR = new c();
    final int[] a;
    final int b;
    final int c;
    final String d;
    final int e;
    final int f;
    final CharSequence g;
    final int h;
    final CharSequence i;

    public BackStackState(Parcel parcel) {
        this.a = parcel.createIntArray();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
        this.d = parcel.readString();
        this.e = parcel.readInt();
        this.f = parcel.readInt();
        this.g = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.h = parcel.readInt();
        this.i = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    public BackStackState(a aVar) {
        int i2 = 0;
        for (b bVar = aVar.b; bVar != null; bVar = bVar.a) {
            if (bVar.i != null) {
                i2 += bVar.i.size();
            }
        }
        this.a = new int[(i2 + (aVar.d * 7))];
        if (!aVar.k) {
            throw new IllegalStateException("Not on back stack");
        }
        int i3 = 0;
        for (b bVar2 = aVar.b; bVar2 != null; bVar2 = bVar2.a) {
            int i4 = i3 + 1;
            this.a[i3] = bVar2.c;
            int i5 = i4 + 1;
            this.a[i4] = bVar2.d != null ? bVar2.d.f : -1;
            int i6 = i5 + 1;
            this.a[i5] = bVar2.e;
            int i7 = i6 + 1;
            this.a[i6] = bVar2.f;
            int i8 = i7 + 1;
            this.a[i7] = bVar2.g;
            int i9 = i8 + 1;
            this.a[i8] = bVar2.h;
            if (bVar2.i != null) {
                int size = bVar2.i.size();
                int i10 = i9 + 1;
                this.a[i9] = size;
                int i11 = 0;
                while (i11 < size) {
                    this.a[i10] = ((Fragment) bVar2.i.get(i11)).f;
                    i11++;
                    i10++;
                }
                i3 = i10;
            } else {
                i3 = i9 + 1;
                this.a[i9] = 0;
            }
        }
        this.b = aVar.i;
        this.c = aVar.j;
        this.d = aVar.m;
        this.e = aVar.o;
        this.f = aVar.p;
        this.g = aVar.q;
        this.h = aVar.r;
        this.i = aVar.s;
    }

    public final a a(l lVar) {
        a aVar = new a(lVar);
        int i2 = 0;
        int i3 = 0;
        while (i3 < this.a.length) {
            b bVar = new b();
            int i4 = i3 + 1;
            bVar.c = this.a[i3];
            if (l.a) {
                "Instantiate " + aVar + " op #" + i2 + " base fragment #" + this.a[i4];
            }
            int i5 = i4 + 1;
            int i6 = this.a[i4];
            if (i6 >= 0) {
                bVar.d = (Fragment) lVar.f.get(i6);
            } else {
                bVar.d = null;
            }
            int i7 = i5 + 1;
            bVar.e = this.a[i5];
            int i8 = i7 + 1;
            bVar.f = this.a[i7];
            int i9 = i8 + 1;
            bVar.g = this.a[i8];
            int i10 = i9 + 1;
            bVar.h = this.a[i9];
            int i11 = i10 + 1;
            int i12 = this.a[i10];
            if (i12 > 0) {
                bVar.i = new ArrayList(i12);
                int i13 = 0;
                while (i13 < i12) {
                    if (l.a) {
                        "Instantiate " + aVar + " set remove fragment #" + this.a[i11];
                    }
                    bVar.i.add((Fragment) lVar.f.get(this.a[i11]));
                    i13++;
                    i11++;
                }
            }
            aVar.a(bVar);
            i2++;
            i3 = i11;
        }
        aVar.i = this.b;
        aVar.j = this.c;
        aVar.m = this.d;
        aVar.o = this.e;
        aVar.k = true;
        aVar.p = this.f;
        aVar.q = this.g;
        aVar.r = this.h;
        aVar.s = this.i;
        aVar.a(1);
        return aVar;
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i2) {
        parcel.writeIntArray(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeString(this.d);
        parcel.writeInt(this.e);
        parcel.writeInt(this.f);
        TextUtils.writeToParcel(this.g, parcel, 0);
        parcel.writeInt(this.h);
        TextUtils.writeToParcel(this.i, parcel, 0);
    }
}
