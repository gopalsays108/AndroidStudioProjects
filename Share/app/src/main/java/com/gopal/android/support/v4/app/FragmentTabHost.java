package android.support.v4.app;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.TabHost;
import java.util.ArrayList;

public final class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
    private final ArrayList a;
    private Context b;
    private k c;
    private int d;
    private TabHost.OnTabChangeListener e;
    private r f;
    private boolean g;

    class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator CREATOR = new q();
        String a;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.a = parcel.readString();
        }

        /* synthetic */ SavedState(Parcel parcel, byte b) {
            this(parcel);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.a + "}";
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.a);
        }
    }

    private s a(String str, s sVar) {
        r rVar = null;
        int i = 0;
        while (i < this.a.size()) {
            r rVar2 = (r) this.a.get(i);
            if (!rVar2.a.equals(str)) {
                rVar2 = rVar;
            }
            i++;
            rVar = rVar2;
        }
        if (rVar == null) {
            throw new IllegalStateException("No tab known for tag " + str);
        }
        if (this.f != rVar) {
            if (sVar == null) {
                sVar = this.c.a();
            }
            if (!(this.f == null || this.f.d == null)) {
                sVar.a(this.f.d);
            }
            if (rVar != null) {
                if (rVar.d == null) {
                    rVar.d = Fragment.a(this.b, rVar.b.getName(), rVar.c);
                    sVar.a(this.d, rVar.d, rVar.a);
                } else {
                    sVar.b(rVar.d);
                }
            }
            this.f = rVar;
        }
        return sVar;
    }

    /* access modifiers changed from: protected */
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTabTag = getCurrentTabTag();
        s sVar = null;
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= this.a.size()) {
                break;
            }
            r rVar = (r) this.a.get(i2);
            rVar.d = this.c.a(rVar.a);
            if (rVar.d != null && !rVar.d.d()) {
                if (rVar.a.equals(currentTabTag)) {
                    this.f = rVar;
                } else {
                    if (sVar == null) {
                        sVar = this.c.a();
                    }
                    sVar.a(rVar.d);
                }
            }
            i = i2 + 1;
        }
        this.g = true;
        s a2 = a(currentTabTag, sVar);
        if (a2 != null) {
            a2.a();
            this.c.b();
        }
    }

    /* access modifiers changed from: protected */
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.g = false;
    }

    /* access modifiers changed from: protected */
    public final void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setCurrentTabByTag(savedState.a);
    }

    /* access modifiers changed from: protected */
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = getCurrentTabTag();
        return savedState;
    }

    public final void onTabChanged(String str) {
        s a2;
        if (this.g && (a2 = a(str, (s) null)) != null) {
            a2.a();
        }
        if (this.e != null) {
            this.e.onTabChanged(str);
        }
    }

    public final void setOnTabChangedListener(TabHost.OnTabChangeListener onTabChangeListener) {
        this.e = onTabChangeListener;
    }

    @Deprecated
    public final void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }
}
