package com.google.android.gms.analytics.internal;

import android.os.Parcel;
import android.os.Parcelable;

public class Command implements Parcelable {
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return new Command(parcel);
        }

        public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return new Command[i];
        }
    };
    private String a;
    private String b;
    private String c;

    public Command() {
    }

    Command(Parcel parcel) {
        this.a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
    }

    public Command(String str, String str2, String str3) {
        this.a = str;
        this.b = str2;
        this.c = str3;
    }

    public final String a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }

    public final String c() {
        return this.c;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
    }
}
