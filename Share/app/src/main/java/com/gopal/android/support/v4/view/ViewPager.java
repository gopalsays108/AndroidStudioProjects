package android.support.v4.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.b.b;
import android.support.v4.b.d;
import android.support.v4.d.a;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class ViewPager extends ViewGroup {
    /* access modifiers changed from: private */
    public static final int[] a = {16842931};
    private static final af ae = new af();
    private static final Comparator b = new v();
    private static final Interpolator c = new w();
    private boolean A;
    private int B;
    private int C;
    private int D;
    private float E;
    private float F;
    private float G;
    private float H;
    private int I;
    private VelocityTracker J;
    private int K;
    private int L;
    private int M;
    private int N;
    private boolean O;
    private a P;
    private a Q;
    private boolean R;
    private boolean S;
    private boolean T;
    private int U;
    private ab V;
    private ab W;
    private aa Z;
    private ac aa;
    private Method ab;
    private int ac;
    private ArrayList ad;
    private final Runnable af;
    private int ag;
    private final ArrayList d;
    private final y e;
    private final Rect f;
    private i g;
    private int h;
    private int i;
    private Parcelable j;
    private ClassLoader k;
    private Scroller l;
    private ad m;
    private int n;
    private Drawable o;
    private int p;
    private int q;
    private float r;
    private float s;
    private int t;
    private int u;
    private boolean v;
    private boolean w;
    private boolean x;
    private int y;
    private boolean z;

    public class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator CREATOR;
        int a;
        Parcelable b;
        ClassLoader c;

        static {
            ae aeVar = new ae();
            if (Build.VERSION.SDK_INT >= 13) {
                new d(aeVar);
            }
            CREATOR = new b(aeVar);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel);
            classLoader = classLoader == null ? getClass().getClassLoader() : classLoader;
            this.a = parcel.readInt();
            this.b = parcel.readParcelable(classLoader);
            this.c = classLoader;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.a + "}";
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.a);
            parcel.writeParcelable(this.b, i);
        }
    }

    private Rect a(Rect rect, View view) {
        Rect rect2 = rect == null ? new Rect() : rect;
        if (view == null) {
            rect2.set(0, 0, 0, 0);
            return rect2;
        }
        rect2.left = view.getLeft();
        rect2.right = view.getRight();
        rect2.top = view.getTop();
        rect2.bottom = view.getBottom();
        ViewParent parent = view.getParent();
        while ((parent instanceof ViewGroup) && parent != this) {
            ViewGroup viewGroup = (ViewGroup) parent;
            rect2.left += viewGroup.getLeft();
            rect2.right += viewGroup.getRight();
            rect2.top += viewGroup.getTop();
            rect2.bottom += viewGroup.getBottom();
            parent = viewGroup.getParent();
        }
        return rect2;
    }

    private y a(int i2) {
        new y().b = i2;
        i iVar = this.g;
        throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
    }

    private y a(View view) {
        while (true) {
            ViewParent parent = view.getParent();
            if (parent == this) {
                return d();
            }
            if (parent == null || !(parent instanceof View)) {
                return null;
            }
            view = (View) parent;
        }
        return null;
    }

    private void a(int i2, int i3, int i4, int i5) {
        if (i3 <= 0 || this.d.isEmpty()) {
            y c2 = c(this.h);
            int min = (int) ((c2 != null ? Math.min(c2.e, this.s) : 0.0f) * ((float) i2));
            if (min != getScrollX()) {
                a(false);
                scrollTo(min, getScrollY());
                return;
            }
            return;
        }
        int scrollX = (int) (((float) (i2 + i4)) * (((float) getScrollX()) / ((float) (i3 + i5))));
        scrollTo(scrollX, getScrollY());
        if (!this.l.isFinished()) {
            this.l.startScroll(scrollX, 0, (int) (c(this.h).e * ((float) i2)), 0, this.l.getDuration() - this.l.timePassed());
        }
    }

    private void a(int i2, boolean z2, int i3, boolean z3) {
        int abs;
        y c2 = c(i2);
        int i4 = 0;
        if (c2 != null) {
            i4 = (int) (((float) getWidth()) * Math.max(this.r, Math.min(c2.e, this.s)));
        }
        if (z2) {
            if (getChildCount() == 0) {
                setScrollingCacheEnabled(false);
            } else {
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int i5 = i4 - scrollX;
                int i6 = 0 - scrollY;
                if (i5 == 0 && i6 == 0) {
                    a(false);
                    c();
                    setScrollState(0);
                } else {
                    setScrollingCacheEnabled(true);
                    setScrollState(2);
                    int width = getWidth();
                    int i7 = width / 2;
                    float f2 = (float) i7;
                    float sin = (((float) i7) * ((float) Math.sin((double) ((float) (((double) (Math.min(1.0f, (1.0f * ((float) Math.abs(i5))) / ((float) width)) - 0.5f)) * 0.4712389167638204d))))) + f2;
                    int abs2 = Math.abs(i3);
                    if (abs2 > 0) {
                        abs = Math.round(1000.0f * Math.abs(sin / ((float) abs2))) * 4;
                    } else {
                        i iVar = this.g;
                        int i8 = this.h;
                        abs = (int) (((((float) Math.abs(i5)) / (((float) width) + ((float) this.n))) + 1.0f) * 100.0f);
                    }
                    this.l.startScroll(scrollX, scrollY, i5, i6, Math.min(abs, 600));
                    n.b(this);
                }
            }
            if (z3 && this.V != null) {
                ab abVar = this.V;
            }
            if (z3 && this.W != null) {
                ab abVar2 = this.W;
                return;
            }
            return;
        }
        if (z3 && this.V != null) {
            ab abVar3 = this.V;
        }
        if (z3 && this.W != null) {
            ab abVar4 = this.W;
        }
        a(false);
        scrollTo(i4, 0);
    }

    private void a(int i2, boolean z2, boolean z3) {
        a(i2, z2, z3, 0);
    }

    private void a(int i2, boolean z2, boolean z3, int i3) {
        boolean z4 = false;
        if (this.g == null || this.g.a() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (z3 || this.h != i2 || this.d.size() == 0) {
            if (i2 < 0) {
                i2 = 0;
            } else if (i2 >= this.g.a()) {
                i2 = this.g.a() - 1;
            }
            int i4 = this.y;
            if (i2 > this.h + i4 || i2 < this.h - i4) {
                for (int i5 = 0; i5 < this.d.size(); i5++) {
                    ((y) this.d.get(i5)).c = true;
                }
            }
            if (this.h != i2) {
                z4 = true;
            }
            b(i2);
            a(i2, z2, i3, z4);
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    private void a(MotionEvent motionEvent) {
        int a2 = e.a(motionEvent);
        if (e.b(motionEvent, a2) == this.I) {
            int i2 = a2 == 0 ? 1 : 0;
            this.E = e.c(motionEvent, i2);
            this.I = e.b(motionEvent, i2);
            if (this.J != null) {
                this.J.clear();
            }
        }
    }

    private void a(boolean z2) {
        boolean z3 = this.ag == 2;
        if (z3) {
            setScrollingCacheEnabled(false);
            this.l.abortAnimation();
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int currX = this.l.getCurrX();
            int currY = this.l.getCurrY();
            if (!(scrollX == currX && scrollY == currY)) {
                scrollTo(currX, currY);
            }
        }
        this.x = false;
        boolean z4 = z3;
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            y yVar = (y) this.d.get(i2);
            if (yVar.c) {
                yVar.c = false;
                z4 = true;
            }
        }
        if (!z4) {
            return;
        }
        if (z2) {
            n.a((View) this, this.af);
        } else {
            this.af.run();
        }
    }

    private boolean a(float f2) {
        boolean z2;
        float f3;
        boolean z3 = true;
        boolean z4 = false;
        this.E = f2;
        float scrollX = ((float) getScrollX()) + (this.E - f2);
        int width = getWidth();
        float f4 = ((float) width) * this.r;
        float f5 = ((float) width) * this.s;
        y yVar = (y) this.d.get(0);
        y yVar2 = (y) this.d.get(this.d.size() - 1);
        if (yVar.b != 0) {
            f4 = yVar.e * ((float) width);
            z2 = false;
        } else {
            z2 = true;
        }
        if (yVar2.b != this.g.a() - 1) {
            f3 = yVar2.e * ((float) width);
            z3 = false;
        } else {
            f3 = f5;
        }
        if (scrollX < f4) {
            if (z2) {
                z4 = this.P.a(Math.abs(f4 - scrollX) / ((float) width));
            }
        } else if (scrollX > f3) {
            if (z3) {
                z4 = this.Q.a(Math.abs(scrollX - f3) / ((float) width));
            }
            f4 = f3;
        } else {
            f4 = scrollX;
        }
        this.E += f4 - ((float) ((int) f4));
        scrollTo((int) f4, getScrollY());
        e();
        return z4;
    }

    private boolean a(View view, boolean z2, int i2, int i3, int i4) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (i3 + scrollX >= childAt.getLeft() && i3 + scrollX < childAt.getRight() && i4 + scrollY >= childAt.getTop() && i4 + scrollY < childAt.getBottom()) {
                    if (a(childAt, true, i2, (i3 + scrollX) - childAt.getLeft(), (i4 + scrollY) - childAt.getTop())) {
                        return true;
                    }
                }
            }
        }
        return z2 && n.a(view, -i2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0055, code lost:
        if (r0.b == r14.h) goto L_0x0057;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(int r15) {
        /*
            r14 = this;
            r0 = 0
            int r1 = r14.h
            if (r1 == r15) goto L_0x037a
            int r0 = r14.h
            android.support.v4.view.y r0 = r14.c(r0)
            r14.h = r15
            r1 = r0
        L_0x000e:
            android.support.v4.view.i r0 = r14.g
            if (r0 != 0) goto L_0x0013
        L_0x0012:
            return
        L_0x0013:
            boolean r0 = r14.x
            if (r0 != 0) goto L_0x0012
            android.os.IBinder r0 = r14.getWindowToken()
            if (r0 == 0) goto L_0x0012
            android.support.v4.view.i r0 = r14.g
            int r0 = r14.y
            r2 = 0
            int r3 = r14.h
            int r3 = r3 - r0
            int r7 = java.lang.Math.max(r2, r3)
            android.support.v4.view.i r2 = r14.g
            int r8 = r2.a()
            int r2 = r8 + -1
            int r3 = r14.h
            int r0 = r0 + r3
            int r9 = java.lang.Math.min(r2, r0)
            r3 = 0
            r0 = 0
            r2 = r0
        L_0x003b:
            java.util.ArrayList r0 = r14.d
            int r0 = r0.size()
            if (r2 >= r0) goto L_0x0377
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r2)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
            int r4 = r0.b
            int r5 = r14.h
            if (r4 < r5) goto L_0x00b0
            int r4 = r0.b
            int r5 = r14.h
            if (r4 != r5) goto L_0x0377
        L_0x0057:
            if (r0 != 0) goto L_0x0374
            if (r8 <= 0) goto L_0x0374
            int r0 = r14.h
            android.support.v4.view.y r0 = r14.a((int) r0)
            r6 = r0
        L_0x0062:
            if (r6 == 0) goto L_0x02cd
            r5 = 0
            int r4 = r2 + -1
            if (r4 < 0) goto L_0x00b4
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r4)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
        L_0x0071:
            r3 = 1073741824(0x40000000, float:2.0)
            float r10 = r6.d
            float r10 = r3 - r10
            int r3 = r14.h
            int r3 = r3 + -1
            r12 = r3
            r3 = r5
            r5 = r12
            r13 = r4
            r4 = r2
            r2 = r13
        L_0x0081:
            if (r5 < 0) goto L_0x00e6
            int r11 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r11 < 0) goto L_0x00b8
            if (r5 >= r7) goto L_0x00b8
            if (r0 == 0) goto L_0x00e6
            int r11 = r0.b
            if (r5 != r11) goto L_0x00ad
            boolean r11 = r0.c
            if (r11 != 0) goto L_0x00ad
            java.util.ArrayList r11 = r14.d
            r11.remove(r2)
            android.support.v4.view.i r11 = r14.g
            java.lang.Object r0 = r0.a
            android.support.v4.view.i.b()
            int r2 = r2 + -1
            int r4 = r4 + -1
            if (r2 < 0) goto L_0x00b6
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r2)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
        L_0x00ad:
            int r5 = r5 + -1
            goto L_0x0081
        L_0x00b0:
            int r0 = r2 + 1
            r2 = r0
            goto L_0x003b
        L_0x00b4:
            r0 = 0
            goto L_0x0071
        L_0x00b6:
            r0 = 0
            goto L_0x00ad
        L_0x00b8:
            if (r0 == 0) goto L_0x00d0
            int r11 = r0.b
            if (r5 != r11) goto L_0x00d0
            float r0 = r0.d
            float r3 = r3 + r0
            int r2 = r2 + -1
            if (r2 < 0) goto L_0x00ce
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r2)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
            goto L_0x00ad
        L_0x00ce:
            r0 = 0
            goto L_0x00ad
        L_0x00d0:
            android.support.v4.view.y r0 = r14.a((int) r5)
            float r0 = r0.d
            float r3 = r3 + r0
            int r4 = r4 + 1
            if (r2 < 0) goto L_0x00e4
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r2)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
            goto L_0x00ad
        L_0x00e4:
            r0 = 0
            goto L_0x00ad
        L_0x00e6:
            float r2 = r6.d
            int r3 = r4 + 1
            r0 = 1073741824(0x40000000, float:2.0)
            int r0 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r0 >= 0) goto L_0x0181
            java.util.ArrayList r0 = r14.d
            int r0 = r0.size()
            if (r3 >= r0) goto L_0x013d
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r3)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
        L_0x0100:
            int r5 = r14.h
            int r5 = r5 + 1
        L_0x0104:
            if (r5 >= r8) goto L_0x0181
            r7 = 1073741824(0x40000000, float:2.0)
            int r7 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r7 < 0) goto L_0x0141
            if (r5 <= r9) goto L_0x0141
            if (r0 == 0) goto L_0x0181
            int r7 = r0.b
            if (r5 != r7) goto L_0x036f
            boolean r7 = r0.c
            if (r7 != 0) goto L_0x036f
            java.util.ArrayList r7 = r14.d
            r7.remove(r3)
            android.support.v4.view.i r7 = r14.g
            java.lang.Object r0 = r0.a
            android.support.v4.view.i.b()
            java.util.ArrayList r0 = r14.d
            int r0 = r0.size()
            if (r3 >= r0) goto L_0x013f
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r3)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
        L_0x0134:
            r12 = r2
            r2 = r0
            r0 = r12
        L_0x0137:
            int r5 = r5 + 1
            r12 = r0
            r0 = r2
            r2 = r12
            goto L_0x0104
        L_0x013d:
            r0 = 0
            goto L_0x0100
        L_0x013f:
            r0 = 0
            goto L_0x0134
        L_0x0141:
            if (r0 == 0) goto L_0x0162
            int r7 = r0.b
            if (r5 != r7) goto L_0x0162
            float r0 = r0.d
            float r2 = r2 + r0
            int r3 = r3 + 1
            java.util.ArrayList r0 = r14.d
            int r0 = r0.size()
            if (r3 >= r0) goto L_0x0160
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r3)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
        L_0x015c:
            r12 = r2
            r2 = r0
            r0 = r12
            goto L_0x0137
        L_0x0160:
            r0 = 0
            goto L_0x015c
        L_0x0162:
            android.support.v4.view.y r0 = r14.a((int) r5)
            int r3 = r3 + 1
            float r0 = r0.d
            float r2 = r2 + r0
            java.util.ArrayList r0 = r14.d
            int r0 = r0.size()
            if (r3 >= r0) goto L_0x017f
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r3)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
        L_0x017b:
            r12 = r2
            r2 = r0
            r0 = r12
            goto L_0x0137
        L_0x017f:
            r0 = 0
            goto L_0x017b
        L_0x0181:
            android.support.v4.view.i r0 = r14.g
            int r7 = r0.a()
            int r0 = r14.getWidth()
            if (r0 <= 0) goto L_0x01d4
            int r2 = r14.n
            float r2 = (float) r2
            float r0 = (float) r0
            float r0 = r2 / r0
            r5 = r0
        L_0x0194:
            if (r1 == 0) goto L_0x0233
            int r2 = r1.b
            int r0 = r6.b
            if (r2 >= r0) goto L_0x01ed
            r0 = 0
            float r3 = r1.e
            float r1 = r1.d
            float r1 = r1 + r3
            float r3 = r1 + r5
            int r2 = r2 + 1
            r1 = r0
        L_0x01a7:
            int r0 = r6.b
            if (r2 > r0) goto L_0x0233
            java.util.ArrayList r0 = r14.d
            int r0 = r0.size()
            if (r1 >= r0) goto L_0x0233
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r1)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
        L_0x01bb:
            int r8 = r0.b
            if (r2 <= r8) goto L_0x01d7
            java.util.ArrayList r8 = r14.d
            int r8 = r8.size()
            int r8 = r8 + -1
            if (r1 >= r8) goto L_0x01d7
            int r1 = r1 + 1
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r1)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
            goto L_0x01bb
        L_0x01d4:
            r0 = 0
            r5 = r0
            goto L_0x0194
        L_0x01d7:
            int r8 = r0.b
            if (r2 >= r8) goto L_0x01e4
            android.support.v4.view.i r8 = r14.g
            r8 = 1065353216(0x3f800000, float:1.0)
            float r8 = r8 + r5
            float r3 = r3 + r8
            int r2 = r2 + 1
            goto L_0x01d7
        L_0x01e4:
            r0.e = r3
            float r0 = r0.d
            float r0 = r0 + r5
            float r3 = r3 + r0
            int r2 = r2 + 1
            goto L_0x01a7
        L_0x01ed:
            int r0 = r6.b
            if (r2 <= r0) goto L_0x0233
            java.util.ArrayList r0 = r14.d
            int r0 = r0.size()
            int r0 = r0 + -1
            float r3 = r1.e
            int r2 = r2 + -1
            r1 = r0
        L_0x01fe:
            int r0 = r6.b
            if (r2 < r0) goto L_0x0233
            if (r1 < 0) goto L_0x0233
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r1)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
        L_0x020c:
            int r8 = r0.b
            if (r2 >= r8) goto L_0x021d
            if (r1 <= 0) goto L_0x021d
            int r1 = r1 + -1
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r1)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
            goto L_0x020c
        L_0x021d:
            int r8 = r0.b
            if (r2 <= r8) goto L_0x022a
            android.support.v4.view.i r8 = r14.g
            r8 = 1065353216(0x3f800000, float:1.0)
            float r8 = r8 + r5
            float r3 = r3 - r8
            int r2 = r2 + -1
            goto L_0x021d
        L_0x022a:
            float r8 = r0.d
            float r8 = r8 + r5
            float r3 = r3 - r8
            r0.e = r3
            int r2 = r2 + -1
            goto L_0x01fe
        L_0x0233:
            java.util.ArrayList r0 = r14.d
            int r8 = r0.size()
            float r2 = r6.e
            int r0 = r6.b
            int r1 = r0 + -1
            int r0 = r6.b
            if (r0 != 0) goto L_0x0271
            float r0 = r6.e
        L_0x0245:
            r14.r = r0
            int r0 = r6.b
            int r3 = r7 + -1
            if (r0 != r3) goto L_0x0275
            float r0 = r6.e
            float r3 = r6.d
            float r0 = r0 + r3
            r3 = 1065353216(0x3f800000, float:1.0)
            float r0 = r0 - r3
        L_0x0255:
            r14.s = r0
            int r0 = r4 + -1
            r3 = r0
        L_0x025a:
            if (r3 < 0) goto L_0x028b
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r3)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
        L_0x0264:
            int r9 = r0.b
            if (r1 <= r9) goto L_0x0279
            android.support.v4.view.i r9 = r14.g
            int r1 = r1 + -1
            r9 = 1065353216(0x3f800000, float:1.0)
            float r9 = r9 + r5
            float r2 = r2 - r9
            goto L_0x0264
        L_0x0271:
            r0 = -8388609(0xffffffffff7fffff, float:-3.4028235E38)
            goto L_0x0245
        L_0x0275:
            r0 = 2139095039(0x7f7fffff, float:3.4028235E38)
            goto L_0x0255
        L_0x0279:
            float r9 = r0.d
            float r9 = r9 + r5
            float r2 = r2 - r9
            r0.e = r2
            int r0 = r0.b
            if (r0 != 0) goto L_0x0285
            r14.r = r2
        L_0x0285:
            int r0 = r3 + -1
            int r1 = r1 + -1
            r3 = r0
            goto L_0x025a
        L_0x028b:
            float r0 = r6.e
            float r1 = r6.d
            float r0 = r0 + r1
            float r2 = r0 + r5
            int r0 = r6.b
            int r1 = r0 + 1
            int r0 = r4 + 1
            r3 = r0
        L_0x0299:
            if (r3 >= r8) goto L_0x02ca
            java.util.ArrayList r0 = r14.d
            java.lang.Object r0 = r0.get(r3)
            android.support.v4.view.y r0 = (android.support.v4.view.y) r0
        L_0x02a3:
            int r4 = r0.b
            if (r1 >= r4) goto L_0x02b0
            android.support.v4.view.i r4 = r14.g
            int r1 = r1 + 1
            r4 = 1065353216(0x3f800000, float:1.0)
            float r4 = r4 + r5
            float r2 = r2 + r4
            goto L_0x02a3
        L_0x02b0:
            int r4 = r0.b
            int r9 = r7 + -1
            if (r4 != r9) goto L_0x02be
            float r4 = r0.d
            float r4 = r4 + r2
            r9 = 1065353216(0x3f800000, float:1.0)
            float r4 = r4 - r9
            r14.s = r4
        L_0x02be:
            r0.e = r2
            float r0 = r0.d
            float r0 = r0 + r5
            float r2 = r2 + r0
            int r0 = r3 + 1
            int r1 = r1 + 1
            r3 = r0
            goto L_0x0299
        L_0x02ca:
            r0 = 0
            r14.S = r0
        L_0x02cd:
            android.support.v4.view.i r0 = r14.g
            int r0 = r14.h
            if (r6 == 0) goto L_0x02d5
            java.lang.Object r0 = r6.a
        L_0x02d5:
            android.support.v4.view.i r0 = r14.g
            int r0 = r14.ac
            if (r0 == 0) goto L_0x0322
            r0 = 1
            r2 = r0
        L_0x02dd:
            if (r2 == 0) goto L_0x02ea
            java.util.ArrayList r0 = r14.ad
            if (r0 != 0) goto L_0x0325
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r14.ad = r0
        L_0x02ea:
            int r3 = r14.getChildCount()
            r0 = 0
            r1 = r0
        L_0x02f0:
            if (r1 >= r3) goto L_0x032b
            android.view.View r4 = r14.getChildAt(r1)
            android.view.ViewGroup$LayoutParams r0 = r4.getLayoutParams()
            android.support.v4.view.z r0 = (android.support.v4.view.z) r0
            r0.f = r1
            boolean r5 = r0.a
            if (r5 != 0) goto L_0x0317
            float r5 = r0.c
            r6 = 0
            int r5 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r5 != 0) goto L_0x0317
            android.support.v4.view.y r5 = r14.d()
            if (r5 == 0) goto L_0x0317
            float r6 = r5.d
            r0.c = r6
            int r5 = r5.b
            r0.e = r5
        L_0x0317:
            if (r2 == 0) goto L_0x031e
            java.util.ArrayList r0 = r14.ad
            r0.add(r4)
        L_0x031e:
            int r0 = r1 + 1
            r1 = r0
            goto L_0x02f0
        L_0x0322:
            r0 = 0
            r2 = r0
            goto L_0x02dd
        L_0x0325:
            java.util.ArrayList r0 = r14.ad
            r0.clear()
            goto L_0x02ea
        L_0x032b:
            if (r2 == 0) goto L_0x0334
            java.util.ArrayList r0 = r14.ad
            android.support.v4.view.af r1 = ae
            java.util.Collections.sort(r0, r1)
        L_0x0334:
            boolean r0 = r14.hasFocus()
            if (r0 == 0) goto L_0x0012
            android.view.View r0 = r14.findFocus()
            if (r0 == 0) goto L_0x036d
            android.support.v4.view.y r0 = r14.a((android.view.View) r0)
        L_0x0344:
            if (r0 == 0) goto L_0x034c
            int r0 = r0.b
            int r1 = r14.h
            if (r0 == r1) goto L_0x0012
        L_0x034c:
            r0 = 0
        L_0x034d:
            int r1 = r14.getChildCount()
            if (r0 >= r1) goto L_0x0012
            android.view.View r1 = r14.getChildAt(r0)
            android.support.v4.view.y r2 = r14.d()
            if (r2 == 0) goto L_0x036a
            int r2 = r2.b
            int r3 = r14.h
            if (r2 != r3) goto L_0x036a
            r2 = 2
            boolean r1 = r1.requestFocus(r2)
            if (r1 != 0) goto L_0x0012
        L_0x036a:
            int r0 = r0 + 1
            goto L_0x034d
        L_0x036d:
            r0 = 0
            goto L_0x0344
        L_0x036f:
            r12 = r2
            r2 = r0
            r0 = r12
            goto L_0x0137
        L_0x0374:
            r6 = r0
            goto L_0x0062
        L_0x0377:
            r0 = r3
            goto L_0x0057
        L_0x037a:
            r1 = r0
            goto L_0x000e
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.b(int):void");
    }

    private y c(int i2) {
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i4 >= this.d.size()) {
                return null;
            }
            y yVar = (y) this.d.get(i4);
            if (yVar.b == i2) {
                return yVar;
            }
            i3 = i4 + 1;
        }
    }

    private void c() {
        b(this.h);
    }

    private y d() {
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i3 >= this.d.size()) {
                return null;
            }
            y yVar = (y) this.d.get(i3);
            i iVar = this.g;
            Object obj = yVar.a;
            if (iVar.c()) {
                return yVar;
            }
            i2 = i3 + 1;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006a, code lost:
        if (r7 != 2) goto L_0x0034;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0085  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean d(int r7) {
        /*
            r6 = this;
            r5 = 66
            r4 = 17
            r1 = 1
            r2 = 0
            android.view.View r0 = r6.findFocus()
            if (r0 != r6) goto L_0x000d
            r0 = 0
        L_0x000d:
            android.view.FocusFinder r3 = android.view.FocusFinder.getInstance()
            android.view.View r3 = r3.findNextFocus(r6, r0, r7)
            if (r3 == 0) goto L_0x005e
            if (r3 == r0) goto L_0x005e
            if (r7 != r4) goto L_0x0043
            android.graphics.Rect r1 = r6.f
            android.graphics.Rect r1 = r6.a(r1, r3)
            int r1 = r1.left
            android.graphics.Rect r2 = r6.f
            android.graphics.Rect r2 = r6.a(r2, r0)
            int r2 = r2.left
            if (r0 == 0) goto L_0x003e
            if (r1 < r2) goto L_0x003e
            boolean r0 = r6.i()
        L_0x0033:
            r2 = r0
        L_0x0034:
            if (r2 == 0) goto L_0x003d
            int r0 = android.view.SoundEffectConstants.getContantForFocusDirection(r7)
            r6.playSoundEffect(r0)
        L_0x003d:
            return r2
        L_0x003e:
            boolean r0 = r3.requestFocus()
            goto L_0x0033
        L_0x0043:
            if (r7 != r5) goto L_0x0034
            android.graphics.Rect r4 = r6.f
            android.graphics.Rect r4 = r6.a(r4, r3)
            int r4 = r4.left
            android.graphics.Rect r5 = r6.f
            android.graphics.Rect r5 = r6.a(r5, r0)
            int r5 = r5.left
            if (r0 == 0) goto L_0x0059
            if (r4 <= r5) goto L_0x006c
        L_0x0059:
            boolean r0 = r3.requestFocus()
            goto L_0x0033
        L_0x005e:
            if (r7 == r4) goto L_0x0062
            if (r7 != r1) goto L_0x0067
        L_0x0062:
            boolean r0 = r6.i()
            goto L_0x0033
        L_0x0067:
            if (r7 == r5) goto L_0x006c
            r0 = 2
            if (r7 != r0) goto L_0x0034
        L_0x006c:
            android.support.v4.view.i r0 = r6.g
            if (r0 == 0) goto L_0x0085
            int r0 = r6.h
            android.support.v4.view.i r3 = r6.g
            int r3 = r3.a()
            int r3 = r3 + -1
            if (r0 >= r3) goto L_0x0085
            int r0 = r6.h
            int r0 = r0 + 1
            r6.setCurrentItem$2563266(r0)
            r0 = r1
            goto L_0x0033
        L_0x0085:
            r0 = r2
            goto L_0x0033
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.d(int):boolean");
    }

    private boolean e() {
        if (this.d.size() == 0) {
            this.T = false;
            f();
            if (this.T) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        y g2 = g();
        getWidth();
        int i2 = this.n;
        int i3 = this.n;
        int i4 = g2.b;
        float f2 = g2.e;
        float f3 = g2.d;
        this.T = false;
        f();
        if (this.T) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    private void f() {
        int i2;
        int i3;
        int measuredWidth;
        if (this.U > 0) {
            int scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width = getWidth();
            int childCount = getChildCount();
            int i4 = 0;
            while (i4 < childCount) {
                View childAt = getChildAt(i4);
                z zVar = (z) childAt.getLayoutParams();
                if (zVar.a) {
                    switch (zVar.b & 7) {
                        case 1:
                            measuredWidth = Math.max((width - childAt.getMeasuredWidth()) / 2, paddingLeft);
                            int i5 = paddingRight;
                            i2 = paddingLeft;
                            i3 = i5;
                            break;
                        case 3:
                            int width2 = childAt.getWidth() + paddingLeft;
                            int i6 = paddingLeft;
                            i3 = paddingRight;
                            i2 = width2;
                            measuredWidth = i6;
                            break;
                        case 5:
                            measuredWidth = (width - paddingRight) - childAt.getMeasuredWidth();
                            int measuredWidth2 = paddingRight + childAt.getMeasuredWidth();
                            i2 = paddingLeft;
                            i3 = measuredWidth2;
                            break;
                        default:
                            measuredWidth = paddingLeft;
                            int i7 = paddingRight;
                            i2 = paddingLeft;
                            i3 = i7;
                            break;
                    }
                    int left = (measuredWidth + scrollX) - childAt.getLeft();
                    if (left != 0) {
                        childAt.offsetLeftAndRight(left);
                    }
                } else {
                    int i8 = paddingRight;
                    i2 = paddingLeft;
                    i3 = i8;
                }
                i4++;
                int i9 = i3;
                paddingLeft = i2;
                paddingRight = i9;
            }
        }
        if (this.V != null) {
            ab abVar = this.V;
        }
        if (this.W != null) {
            ab abVar2 = this.W;
        }
        if (this.aa != null) {
            getScrollX();
            int childCount2 = getChildCount();
            for (int i10 = 0; i10 < childCount2; i10++) {
                View childAt2 = getChildAt(i10);
                if (!((z) childAt2.getLayoutParams()).a) {
                    childAt2.getLeft();
                    getWidth();
                    ac acVar = this.aa;
                }
            }
        }
        this.T = true;
    }

    private y g() {
        int i2;
        y yVar;
        int width = getWidth();
        float scrollX = width > 0 ? ((float) getScrollX()) / ((float) width) : 0.0f;
        float f2 = width > 0 ? ((float) this.n) / ((float) width) : 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        int i3 = -1;
        int i4 = 0;
        boolean z2 = true;
        y yVar2 = null;
        while (i4 < this.d.size()) {
            y yVar3 = (y) this.d.get(i4);
            if (z2 || yVar3.b == i3 + 1) {
                y yVar4 = yVar3;
                i2 = i4;
                yVar = yVar4;
            } else {
                y yVar5 = this.e;
                yVar5.e = f3 + f4 + f2;
                yVar5.b = i3 + 1;
                i iVar = this.g;
                int i5 = yVar5.b;
                yVar5.d = 1.0f;
                y yVar6 = yVar5;
                i2 = i4 - 1;
                yVar = yVar6;
            }
            float f5 = yVar.e;
            float f6 = yVar.d + f5 + f2;
            if (!z2 && scrollX < f5) {
                return yVar2;
            }
            if (scrollX < f6 || i2 == this.d.size() - 1) {
                return yVar;
            }
            f4 = f5;
            i3 = yVar.b;
            z2 = false;
            f3 = yVar.d;
            yVar2 = yVar;
            i4 = i2 + 1;
        }
        return yVar2;
    }

    private void h() {
        this.z = false;
        this.A = false;
        if (this.J != null) {
            this.J.recycle();
            this.J = null;
        }
    }

    private boolean i() {
        if (this.h <= 0) {
            return false;
        }
        setCurrentItem$2563266(this.h - 1);
        return true;
    }

    private void setCurrentItem$2563266(int i2) {
        this.x = false;
        a(i2, true, false);
    }

    private void setScrollState(int i2) {
        if (this.ag != i2) {
            this.ag = i2;
            if (this.aa != null) {
                boolean z2 = i2 != 0;
                int childCount = getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    n.b(getChildAt(i3), z2 ? 2 : 0);
                }
            }
            if (this.V != null) {
                ab abVar = this.V;
            }
        }
    }

    private void setScrollingCacheEnabled(boolean z2) {
        if (this.w != z2) {
            this.w = z2;
        }
    }

    /* access modifiers changed from: package-private */
    public final void a() {
        boolean z2 = this.d.size() < (this.y << 1) + 1 && this.d.size() < this.g.a();
        int i2 = this.h;
        for (int i3 = 0; i3 < this.d.size(); i3++) {
            i iVar = this.g;
            Object obj = ((y) this.d.get(i3)).a;
        }
        Collections.sort(this.d, b);
        if (z2) {
            int childCount = getChildCount();
            for (int i4 = 0; i4 < childCount; i4++) {
                z zVar = (z) getChildAt(i4).getLayoutParams();
                if (!zVar.a) {
                    zVar.c = 0.0f;
                }
            }
            a(i2, false, true);
            requestLayout();
        }
    }

    public final void addFocusables(ArrayList arrayList, int i2, int i3) {
        y d2;
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i4 = 0; i4 < getChildCount(); i4++) {
                View childAt = getChildAt(i4);
                if (childAt.getVisibility() == 0 && (d2 = d()) != null && d2.b == this.h) {
                    childAt.addFocusables(arrayList, i2, i3);
                }
            }
        }
        if ((descendantFocusability == 262144 && size != arrayList.size()) || !isFocusable()) {
            return;
        }
        if (((i3 & 1) != 1 || !isInTouchMode() || isFocusableInTouchMode()) && arrayList != null) {
            arrayList.add(this);
        }
    }

    public final void addTouchables(ArrayList arrayList) {
        y d2;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (d2 = d()) != null && d2.b == this.h) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    public final void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        ViewGroup.LayoutParams generateLayoutParams = !checkLayoutParams(layoutParams) ? generateLayoutParams(layoutParams) : layoutParams;
        z zVar = (z) generateLayoutParams;
        zVar.a |= view instanceof x;
        if (!this.v) {
            super.addView(view, i2, generateLayoutParams);
        } else if (zVar == null || !zVar.a) {
            zVar.d = true;
            addViewInLayout(view, i2, generateLayoutParams);
        } else {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        }
    }

    /* access modifiers changed from: protected */
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof z) && super.checkLayoutParams(layoutParams);
    }

    public final void computeScroll() {
        if (this.l.isFinished() || !this.l.computeScrollOffset()) {
            a(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.l.getCurrX();
        int currY = this.l.getCurrY();
        if (!(scrollX == currX && scrollY == currY)) {
            scrollTo(currX, currY);
            if (!e()) {
                this.l.abortAnimation();
                scrollTo(0, currY);
            }
        }
        n.b(this);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r5) {
        /*
            r4 = this;
            r1 = 1
            r0 = 0
            boolean r2 = super.dispatchKeyEvent(r5)
            if (r2 != 0) goto L_0x0018
            int r2 = r5.getAction()
            if (r2 != 0) goto L_0x0015
            int r2 = r5.getKeyCode()
            switch(r2) {
                case 21: goto L_0x001a;
                case 22: goto L_0x0021;
                case 61: goto L_0x0028;
                default: goto L_0x0015;
            }
        L_0x0015:
            r2 = r0
        L_0x0016:
            if (r2 == 0) goto L_0x0019
        L_0x0018:
            r0 = r1
        L_0x0019:
            return r0
        L_0x001a:
            r2 = 17
            boolean r2 = r4.d(r2)
            goto L_0x0016
        L_0x0021:
            r2 = 66
            boolean r2 = r4.d(r2)
            goto L_0x0016
        L_0x0028:
            int r2 = android.os.Build.VERSION.SDK_INT
            r3 = 11
            if (r2 < r3) goto L_0x0015
            boolean r2 = android.support.v4.view.a.b(r5)
            if (r2 == 0) goto L_0x003a
            r2 = 2
            boolean r2 = r4.d(r2)
            goto L_0x0016
        L_0x003a:
            boolean r2 = android.support.v4.view.a.a(r5)
            if (r2 == 0) goto L_0x0015
            boolean r2 = r4.d(r1)
            goto L_0x0016
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    public final boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        y d2;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (d2 = d()) != null && d2.b == this.h && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
        }
        return false;
    }

    public final void draw(Canvas canvas) {
        super.draw(canvas);
        boolean z2 = false;
        int a2 = n.a(this);
        if (a2 == 0 || (a2 == 1 && this.g != null && this.g.a() > 1)) {
            if (!this.P.a()) {
                int save = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate((float) ((-height) + getPaddingTop()), this.r * ((float) width));
                this.P.a(height, width);
                z2 = this.P.a(canvas) | false;
                canvas.restoreToCount(save);
            }
            if (!this.Q.a()) {
                int save2 = canvas.save();
                int width2 = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate((float) (-getPaddingTop()), (-(this.s + 1.0f)) * ((float) width2));
                this.Q.a(height2, width2);
                z2 |= this.Q.a(canvas);
                canvas.restoreToCount(save2);
            }
        } else {
            this.P.b();
            this.Q.b();
        }
        if (z2) {
            n.b(this);
        }
    }

    /* access modifiers changed from: protected */
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.o;
        if (drawable != null && drawable.isStateful()) {
            drawable.setState(getDrawableState());
        }
    }

    /* access modifiers changed from: protected */
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new z();
    }

    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new z(getContext(), attributeSet);
    }

    /* access modifiers changed from: protected */
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    public final i getAdapter() {
        return this.g;
    }

    /* access modifiers changed from: protected */
    public final int getChildDrawingOrder(int i2, int i3) {
        if (this.ac == 2) {
            i3 = (i2 - 1) - i3;
        }
        return ((z) ((View) this.ad.get(i3)).getLayoutParams()).f;
    }

    public final int getCurrentItem() {
        return this.h;
    }

    public final int getOffscreenPageLimit() {
        return this.y;
    }

    public final int getPageMargin() {
        return this.n;
    }

    /* access modifiers changed from: protected */
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.R = true;
    }

    /* access modifiers changed from: protected */
    public final void onDetachedFromWindow() {
        removeCallbacks(this.af);
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public final void onDraw(Canvas canvas) {
        float f2;
        super.onDraw(canvas);
        if (this.n > 0 && this.o != null && this.d.size() > 0 && this.g != null) {
            int scrollX = getScrollX();
            int width = getWidth();
            float f3 = ((float) this.n) / ((float) width);
            y yVar = (y) this.d.get(0);
            float f4 = yVar.e;
            int size = this.d.size();
            int i2 = yVar.b;
            int i3 = ((y) this.d.get(size - 1)).b;
            int i4 = 0;
            int i5 = i2;
            while (i5 < i3) {
                while (i5 > yVar.b && i4 < size) {
                    i4++;
                    yVar = (y) this.d.get(i4);
                }
                if (i5 == yVar.b) {
                    f2 = (yVar.e + yVar.d) * ((float) width);
                    f4 = yVar.e + yVar.d + f3;
                } else {
                    i iVar = this.g;
                    f2 = (1.0f + f4) * ((float) width);
                    f4 += 1.0f + f3;
                }
                if (((float) this.n) + f2 > ((float) scrollX)) {
                    this.o.setBounds((int) f2, this.p, (int) (((float) this.n) + f2 + 0.5f), this.q);
                    this.o.draw(canvas);
                }
                if (f2 <= ((float) (scrollX + width))) {
                    i5++;
                } else {
                    return;
                }
            }
        }
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            this.z = false;
            this.A = false;
            this.I = -1;
            if (this.J == null) {
                return false;
            }
            this.J.recycle();
            this.J = null;
            return false;
        }
        if (action != 0) {
            if (this.z) {
                return true;
            }
            if (this.A) {
                return false;
            }
        }
        switch (action) {
            case 0:
                float x2 = motionEvent.getX();
                this.G = x2;
                this.E = x2;
                float y2 = motionEvent.getY();
                this.H = y2;
                this.F = y2;
                this.I = e.b(motionEvent, 0);
                this.A = false;
                this.l.computeScrollOffset();
                if (this.ag == 2 && Math.abs(this.l.getFinalX() - this.l.getCurrX()) > this.N) {
                    this.l.abortAnimation();
                    this.x = false;
                    c();
                    this.z = true;
                    setScrollState(1);
                    break;
                } else {
                    a(false);
                    this.z = false;
                    break;
                }
            case 2:
                int i2 = this.I;
                if (i2 != -1) {
                    int a2 = e.a(motionEvent, i2);
                    float c2 = e.c(motionEvent, a2);
                    float f2 = c2 - this.E;
                    float abs = Math.abs(f2);
                    float d2 = e.d(motionEvent, a2);
                    float abs2 = Math.abs(d2 - this.H);
                    if (f2 != 0.0f) {
                        float f3 = this.E;
                        if (!((f3 < ((float) this.C) && f2 > 0.0f) || (f3 > ((float) (getWidth() - this.C)) && f2 < 0.0f))) {
                            if (a(this, false, (int) f2, (int) c2, (int) d2)) {
                                this.E = c2;
                                this.F = d2;
                                this.A = true;
                                return false;
                            }
                        }
                    }
                    if (abs > ((float) this.D) && 0.5f * abs > abs2) {
                        this.z = true;
                        setScrollState(1);
                        this.E = f2 > 0.0f ? this.G + ((float) this.D) : this.G - ((float) this.D);
                        this.F = d2;
                        setScrollingCacheEnabled(true);
                    } else if (abs2 > ((float) this.D)) {
                        this.A = true;
                    }
                    if (this.z && a(c2)) {
                        n.b(this);
                        break;
                    }
                }
                break;
            case 6:
                a(motionEvent);
                break;
        }
        if (this.J == null) {
            this.J = VelocityTracker.obtain();
        }
        this.J.addMovement(motionEvent);
        return this.z;
    }

    /* access modifiers changed from: protected */
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        y d2;
        int i6;
        int i7;
        int i8;
        int measuredHeight;
        int i9;
        int i10;
        this.v = true;
        c();
        this.v = false;
        int childCount = getChildCount();
        int i11 = i4 - i2;
        int i12 = i5 - i3;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i13 = 0;
        int i14 = 0;
        while (i14 < childCount) {
            View childAt = getChildAt(i14);
            if (childAt.getVisibility() != 8) {
                z zVar = (z) childAt.getLayoutParams();
                if (zVar.a) {
                    int i15 = zVar.b & 7;
                    int i16 = zVar.b & 112;
                    switch (i15) {
                        case 1:
                            i8 = Math.max((i11 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                            break;
                        case 3:
                            i8 = paddingLeft;
                            paddingLeft = childAt.getMeasuredWidth() + paddingLeft;
                            break;
                        case 5:
                            int measuredWidth = (i11 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                            i8 = measuredWidth;
                            break;
                        default:
                            i8 = paddingLeft;
                            break;
                    }
                    switch (i16) {
                        case 16:
                            measuredHeight = Math.max((i12 - childAt.getMeasuredHeight()) / 2, paddingTop);
                            int i17 = paddingBottom;
                            i9 = paddingTop;
                            i10 = i17;
                            break;
                        case 48:
                            int measuredHeight2 = childAt.getMeasuredHeight() + paddingTop;
                            int i18 = paddingTop;
                            i10 = paddingBottom;
                            i9 = measuredHeight2;
                            measuredHeight = i18;
                            break;
                        case 80:
                            measuredHeight = (i12 - paddingBottom) - childAt.getMeasuredHeight();
                            int measuredHeight3 = paddingBottom + childAt.getMeasuredHeight();
                            i9 = paddingTop;
                            i10 = measuredHeight3;
                            break;
                        default:
                            measuredHeight = paddingTop;
                            int i19 = paddingBottom;
                            i9 = paddingTop;
                            i10 = i19;
                            break;
                    }
                    int i20 = i8 + scrollX;
                    childAt.layout(i20, measuredHeight, childAt.getMeasuredWidth() + i20, childAt.getMeasuredHeight() + measuredHeight);
                    i6 = i13 + 1;
                    i7 = i9;
                    paddingBottom = i10;
                    i14++;
                    paddingLeft = paddingLeft;
                    paddingRight = paddingRight;
                    paddingTop = i7;
                    i13 = i6;
                }
            }
            i6 = i13;
            i7 = paddingTop;
            i14++;
            paddingLeft = paddingLeft;
            paddingRight = paddingRight;
            paddingTop = i7;
            i13 = i6;
        }
        for (int i21 = 0; i21 < childCount; i21++) {
            View childAt2 = getChildAt(i21);
            if (childAt2.getVisibility() != 8) {
                z zVar2 = (z) childAt2.getLayoutParams();
                if (!zVar2.a && (d2 = d()) != null) {
                    int i22 = ((int) (d2.e * ((float) i11))) + paddingLeft;
                    if (zVar2.d) {
                        zVar2.d = false;
                        childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (zVar2.c * ((float) ((i11 - paddingLeft) - paddingRight))), 1073741824), View.MeasureSpec.makeMeasureSpec((i12 - paddingTop) - paddingBottom, 1073741824));
                    }
                    childAt2.layout(i22, paddingTop, childAt2.getMeasuredWidth() + i22, childAt2.getMeasuredHeight() + paddingTop);
                }
            }
        }
        this.p = paddingTop;
        this.q = i12 - paddingBottom;
        this.U = i13;
        this.R = false;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r14, int r15) {
        /*
            r13 = this;
            r0 = 0
            int r0 = getDefaultSize(r0, r14)
            r1 = 0
            int r1 = getDefaultSize(r1, r15)
            r13.setMeasuredDimension(r0, r1)
            int r0 = r13.getMeasuredWidth()
            int r1 = r0 / 10
            int r2 = r13.B
            int r1 = java.lang.Math.min(r1, r2)
            r13.C = r1
            int r1 = r13.getPaddingLeft()
            int r0 = r0 - r1
            int r1 = r13.getPaddingRight()
            int r3 = r0 - r1
            int r0 = r13.getMeasuredHeight()
            int r1 = r13.getPaddingTop()
            int r0 = r0 - r1
            int r1 = r13.getPaddingBottom()
            int r5 = r0 - r1
            int r9 = r13.getChildCount()
            r0 = 0
            r8 = r0
        L_0x003b:
            if (r8 >= r9) goto L_0x00bc
            android.view.View r10 = r13.getChildAt(r8)
            int r0 = r10.getVisibility()
            r1 = 8
            if (r0 == r1) goto L_0x00a5
            android.view.ViewGroup$LayoutParams r0 = r10.getLayoutParams()
            android.support.v4.view.z r0 = (android.support.v4.view.z) r0
            if (r0 == 0) goto L_0x00a5
            boolean r1 = r0.a
            if (r1 == 0) goto L_0x00a5
            int r1 = r0.b
            r6 = r1 & 7
            int r1 = r0.b
            r4 = r1 & 112(0x70, float:1.57E-43)
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r7 = 48
            if (r4 == r7) goto L_0x0069
            r7 = 80
            if (r4 != r7) goto L_0x00a9
        L_0x0069:
            r4 = 1
            r7 = r4
        L_0x006b:
            r4 = 3
            if (r6 == r4) goto L_0x0071
            r4 = 5
            if (r6 != r4) goto L_0x00ac
        L_0x0071:
            r4 = 1
            r6 = r4
        L_0x0073:
            if (r7 == 0) goto L_0x00af
            r2 = 1073741824(0x40000000, float:2.0)
        L_0x0077:
            int r4 = r0.width
            r11 = -2
            if (r4 == r11) goto L_0x010f
            r4 = 1073741824(0x40000000, float:2.0)
            int r2 = r0.width
            r11 = -1
            if (r2 == r11) goto L_0x010c
            int r2 = r0.width
        L_0x0085:
            int r11 = r0.height
            r12 = -2
            if (r11 == r12) goto L_0x010a
            r1 = 1073741824(0x40000000, float:2.0)
            int r11 = r0.height
            r12 = -1
            if (r11 == r12) goto L_0x010a
            int r0 = r0.height
        L_0x0093:
            int r2 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r4)
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r1)
            r10.measure(r2, r0)
            if (r7 == 0) goto L_0x00b4
            int r0 = r10.getMeasuredHeight()
            int r5 = r5 - r0
        L_0x00a5:
            int r0 = r8 + 1
            r8 = r0
            goto L_0x003b
        L_0x00a9:
            r4 = 0
            r7 = r4
            goto L_0x006b
        L_0x00ac:
            r4 = 0
            r6 = r4
            goto L_0x0073
        L_0x00af:
            if (r6 == 0) goto L_0x0077
            r1 = 1073741824(0x40000000, float:2.0)
            goto L_0x0077
        L_0x00b4:
            if (r6 == 0) goto L_0x00a5
            int r0 = r10.getMeasuredWidth()
            int r3 = r3 - r0
            goto L_0x00a5
        L_0x00bc:
            r0 = 1073741824(0x40000000, float:2.0)
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r0)
            r13.t = r0
            r0 = 1073741824(0x40000000, float:2.0)
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r5, r0)
            r13.u = r0
            r0 = 1
            r13.v = r0
            r13.c()
            r0 = 0
            r13.v = r0
            int r2 = r13.getChildCount()
            r0 = 0
            r1 = r0
        L_0x00db:
            if (r1 >= r2) goto L_0x0109
            android.view.View r4 = r13.getChildAt(r1)
            int r0 = r4.getVisibility()
            r5 = 8
            if (r0 == r5) goto L_0x0105
            android.view.ViewGroup$LayoutParams r0 = r4.getLayoutParams()
            android.support.v4.view.z r0 = (android.support.v4.view.z) r0
            if (r0 == 0) goto L_0x00f5
            boolean r5 = r0.a
            if (r5 != 0) goto L_0x0105
        L_0x00f5:
            float r5 = (float) r3
            float r0 = r0.c
            float r0 = r0 * r5
            int r0 = (int) r0
            r5 = 1073741824(0x40000000, float:2.0)
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r5)
            int r5 = r13.u
            r4.measure(r0, r5)
        L_0x0105:
            int r0 = r1 + 1
            r1 = r0
            goto L_0x00db
        L_0x0109:
            return
        L_0x010a:
            r0 = r5
            goto L_0x0093
        L_0x010c:
            r2 = r3
            goto L_0x0085
        L_0x010f:
            r4 = r2
            r2 = r3
            goto L_0x0085
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.view.ViewPager.onMeasure(int, int):void");
    }

    /* access modifiers changed from: protected */
    public final boolean onRequestFocusInDescendants(int i2, Rect rect) {
        int i3;
        y d2;
        int i4 = -1;
        int childCount = getChildCount();
        if ((i2 & 2) != 0) {
            i4 = 1;
            i3 = 0;
        } else {
            i3 = childCount - 1;
            childCount = -1;
        }
        while (i3 != childCount) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() == 0 && (d2 = d()) != null && d2.b == this.h && childAt.requestFocus(i2, rect)) {
                return true;
            }
            i3 += i4;
        }
        return false;
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (this.g != null) {
            i iVar = this.g;
            Parcelable parcelable2 = savedState.b;
            ClassLoader classLoader = savedState.c;
            a(savedState.a, false, true);
            return;
        }
        this.i = savedState.a;
        this.j = savedState.b;
        this.k = savedState.c;
    }

    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.a = this.h;
        if (this.g != null) {
            i iVar = this.g;
            savedState.b = null;
        }
        return savedState;
    }

    /* access modifiers changed from: protected */
    public final void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4) {
            a(i2, i4, this.n, this.n);
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int i2;
        boolean z2 = false;
        if (this.O) {
            return true;
        }
        if (motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) {
            return false;
        }
        if (this.g == null || this.g.a() == 0) {
            return false;
        }
        if (this.J == null) {
            this.J = VelocityTracker.obtain();
        }
        this.J.addMovement(motionEvent);
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.l.abortAnimation();
                this.x = false;
                c();
                this.z = true;
                setScrollState(1);
                float x2 = motionEvent.getX();
                this.G = x2;
                this.E = x2;
                float y2 = motionEvent.getY();
                this.H = y2;
                this.F = y2;
                this.I = e.b(motionEvent, 0);
                break;
            case 1:
                if (this.z) {
                    VelocityTracker velocityTracker = this.J;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.L);
                    int a2 = (int) j.a(velocityTracker, this.I);
                    this.x = true;
                    int width = getWidth();
                    int scrollX = getScrollX();
                    y g2 = g();
                    int i3 = g2.b;
                    float f2 = ((((float) scrollX) / ((float) width)) - g2.e) / g2.d;
                    if (Math.abs((int) (e.c(motionEvent, e.a(motionEvent, this.I)) - this.G)) <= this.M || Math.abs(a2) <= this.K) {
                        i2 = (int) (((float) i3) + f2 + (i3 >= this.h ? 0.4f : 0.6f));
                    } else {
                        if (a2 <= 0) {
                            i3++;
                        }
                        i2 = i3;
                    }
                    if (this.d.size() > 0) {
                        i2 = Math.max(((y) this.d.get(0)).b, Math.min(i2, ((y) this.d.get(this.d.size() - 1)).b));
                    }
                    a(i2, true, true, a2);
                    this.I = -1;
                    h();
                    z2 = this.P.c() | this.Q.c();
                    break;
                }
                break;
            case 2:
                if (!this.z) {
                    int a3 = e.a(motionEvent, this.I);
                    float c2 = e.c(motionEvent, a3);
                    float abs = Math.abs(c2 - this.E);
                    float d2 = e.d(motionEvent, a3);
                    float abs2 = Math.abs(d2 - this.F);
                    if (abs > ((float) this.D) && abs > abs2) {
                        this.z = true;
                        this.E = c2 - this.G > 0.0f ? this.G + ((float) this.D) : this.G - ((float) this.D);
                        this.F = d2;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                    }
                }
                if (this.z) {
                    z2 = a(e.c(motionEvent, e.a(motionEvent, this.I))) | false;
                    break;
                }
                break;
            case 3:
                if (this.z) {
                    a(this.h, true, 0, false);
                    this.I = -1;
                    h();
                    z2 = this.P.c() | this.Q.c();
                    break;
                }
                break;
            case 5:
                int a4 = e.a(motionEvent);
                this.E = e.c(motionEvent, a4);
                this.I = e.b(motionEvent, a4);
                break;
            case 6:
                a(motionEvent);
                this.E = e.c(motionEvent, e.a(motionEvent, this.I));
                break;
        }
        if (z2) {
            n.b(this);
        }
        return true;
    }

    public final void removeView(View view) {
        if (this.v) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public final void setAdapter(i iVar) {
        if (this.g != null) {
            this.g.a.unregisterObserver(this.m);
            i iVar2 = this.g;
            for (int i2 = 0; i2 < this.d.size(); i2++) {
                y yVar = (y) this.d.get(i2);
                i iVar3 = this.g;
                int i3 = yVar.b;
                Object obj = yVar.a;
                i.b();
            }
            i iVar4 = this.g;
            this.d.clear();
            int i4 = 0;
            while (i4 < getChildCount()) {
                if (!((z) getChildAt(i4).getLayoutParams()).a) {
                    removeViewAt(i4);
                    i4--;
                }
                i4++;
            }
            this.h = 0;
            scrollTo(0, 0);
        }
        i iVar5 = this.g;
        this.g = iVar;
        if (this.g != null) {
            if (this.m == null) {
                this.m = new ad(this, (byte) 0);
            }
            this.g.a.registerObserver(this.m);
            this.x = false;
            this.R = true;
            if (this.i >= 0) {
                i iVar6 = this.g;
                Parcelable parcelable = this.j;
                ClassLoader classLoader = this.k;
                a(this.i, false, true);
                this.i = -1;
                this.j = null;
                this.k = null;
            } else {
                c();
            }
        }
        if (this.Z != null && iVar5 != iVar) {
            aa aaVar = this.Z;
        }
    }

    /* access modifiers changed from: package-private */
    public final void setChildrenDrawingOrderEnabledCompat(boolean z2) {
        if (this.ab == null) {
            Class<ViewGroup> cls = ViewGroup.class;
            try {
                this.ab = cls.getDeclaredMethod("setChildrenDrawingOrderEnabled", new Class[]{Boolean.TYPE});
            } catch (NoSuchMethodException e2) {
            }
        }
        try {
            this.ab.invoke(this, new Object[]{Boolean.valueOf(z2)});
        } catch (Exception e3) {
        }
    }

    public final void setCurrentItem(int i2) {
        this.x = false;
        a(i2, !this.R, false);
    }

    public final void setOffscreenPageLimit(int i2) {
        if (i2 <= 0) {
            Log.w("ViewPager", "Requested offscreen page limit " + i2 + " too small; defaulting to 1");
            i2 = 1;
        }
        if (i2 != this.y) {
            this.y = i2;
            c();
        }
    }

    /* access modifiers changed from: package-private */
    public final void setOnAdapterChangeListener(aa aaVar) {
        this.Z = aaVar;
    }

    public final void setOnPageChangeListener(ab abVar) {
        this.V = abVar;
    }

    public final void setPageMargin(int i2) {
        int i3 = this.n;
        this.n = i2;
        int width = getWidth();
        a(width, width, i2, i3);
        requestLayout();
    }

    public final void setPageMarginDrawable(int i2) {
        setPageMarginDrawable(getContext().getResources().getDrawable(i2));
    }

    public final void setPageMarginDrawable(Drawable drawable) {
        this.o = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    /* access modifiers changed from: protected */
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.o;
    }
}
