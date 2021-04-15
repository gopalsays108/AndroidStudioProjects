package android.support.v4.app;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.c.a;
import android.support.v4.c.b;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

final class l extends k {
    static final Interpolator A = new DecelerateInterpolator(1.5f);
    static final Interpolator B = new AccelerateInterpolator(2.5f);
    static final Interpolator C = new AccelerateInterpolator(1.5f);
    static boolean a = false;
    static final boolean b;
    static final Interpolator z = new DecelerateInterpolator(2.5f);
    ArrayList c;
    Runnable[] d;
    boolean e;
    ArrayList f;
    ArrayList g;
    ArrayList h;
    ArrayList i;
    ArrayList j;
    ArrayList k;
    ArrayList l;
    ArrayList m;
    int n = 0;
    g o;
    j p;
    Fragment q;
    boolean r;
    boolean s;
    boolean t;
    String u;
    boolean v;
    Bundle w = null;
    SparseArray x = null;
    Runnable y = new m(this);

    static {
        boolean z2 = false;
        if (Build.VERSION.SDK_INT >= 11) {
            z2 = true;
        }
        b = z2;
    }

    l() {
    }

    private Fragment a(Bundle bundle, String str) {
        int i2 = bundle.getInt(str, -1);
        if (i2 == -1) {
            return null;
        }
        if (i2 >= this.f.size()) {
            a((RuntimeException) new IllegalStateException("Fragement no longer exists for key " + str + ": index " + i2));
        }
        Fragment fragment = (Fragment) this.f.get(i2);
        if (fragment != null) {
            return fragment;
        }
        a((RuntimeException) new IllegalStateException("Fragement no longer exists for key " + str + ": index " + i2));
        return fragment;
    }

    private static Animation a(float f2, float f3) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f3);
        alphaAnimation.setInterpolator(A);
        alphaAnimation.setDuration(220);
        return alphaAnimation;
    }

    private static Animation a(float f2, float f3, float f4, float f5) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f2, f3, f2, f3, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(z);
        scaleAnimation.setDuration(220);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f4, f5);
        alphaAnimation.setInterpolator(A);
        alphaAnimation.setDuration(220);
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }

    private Animation a(Fragment fragment, int i2, boolean z2, int i3) {
        Animation loadAnimation;
        int i4 = fragment.G;
        Fragment.j();
        if (fragment.G != 0 && (loadAnimation = AnimationUtils.loadAnimation(this.o, fragment.G)) != null) {
            return loadAnimation;
        }
        if (i2 == 0) {
            return null;
        }
        char c2 = 65535;
        switch (i2) {
            case 4097:
                if (!z2) {
                    c2 = 2;
                    break;
                } else {
                    c2 = 1;
                    break;
                }
            case 4099:
                if (!z2) {
                    c2 = 6;
                    break;
                } else {
                    c2 = 5;
                    break;
                }
            case 8194:
                if (!z2) {
                    c2 = 4;
                    break;
                } else {
                    c2 = 3;
                    break;
                }
        }
        if (c2 < 0) {
            return null;
        }
        switch (c2) {
            case 1:
                g gVar = this.o;
                return a(1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                g gVar2 = this.o;
                return a(1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                g gVar3 = this.o;
                return a(0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                g gVar4 = this.o;
                return a(1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                g gVar5 = this.o;
                return a(0.0f, 1.0f);
            case 6:
                g gVar6 = this.o;
                return a(1.0f, 0.0f);
            default:
                if (i3 == 0 && this.o.getWindow() != null) {
                    i3 = this.o.getWindow().getAttributes().windowAnimations;
                }
                return i3 == 0 ? null : null;
        }
    }

    private void a(int i2, a aVar) {
        synchronized (this) {
            if (this.k == null) {
                this.k = new ArrayList();
            }
            int size = this.k.size();
            if (i2 < size) {
                if (a) {
                    "Setting back stack index " + i2 + " to " + aVar;
                }
                this.k.set(i2, aVar);
            } else {
                while (size < i2) {
                    this.k.add((Object) null);
                    if (this.l == null) {
                        this.l = new ArrayList();
                    }
                    if (a) {
                        "Adding available back stack index " + size;
                    }
                    this.l.add(Integer.valueOf(size));
                    size++;
                }
                if (a) {
                    "Adding back stack index " + i2 + " with " + aVar;
                }
                this.k.add(aVar);
            }
        }
    }

    private void a(RuntimeException runtimeException) {
        runtimeException.getMessage();
        PrintWriter printWriter = new PrintWriter(new b("FragmentManager"));
        if (this.o != null) {
            try {
                this.o.dump("  ", (FileDescriptor) null, printWriter, new String[0]);
            } catch (Exception e2) {
            }
        } else {
            try {
                a("  ", (FileDescriptor) null, printWriter, new String[0]);
            } catch (Exception e3) {
            }
        }
        throw runtimeException;
    }

    private void b(Fragment fragment) {
        if (fragment.J != null) {
            if (this.x == null) {
                this.x = new SparseArray();
            } else {
                this.x.clear();
            }
            fragment.J.saveHierarchyState(this.x);
            if (this.x.size() > 0) {
                fragment.e = this.x;
                this.x = null;
            }
        }
    }

    public static int c(int i2) {
        switch (i2) {
            case 4097:
                return 8194;
            case 4099:
                return 4099;
            case 8194:
                return 4097;
            default:
                return 0;
        }
    }

    private void d(int i2) {
        a(i2, 0, 0, false);
    }

    private void r() {
        if (this.f != null) {
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                Fragment fragment = (Fragment) this.f.get(i2);
                if (fragment != null && fragment.K) {
                    if (this.e) {
                        this.v = true;
                    } else {
                        fragment.K = false;
                        a(fragment, this.n, 0, 0, false);
                    }
                }
            }
        }
    }

    private void s() {
        if (this.s) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.u != null) {
            throw new IllegalStateException("Can not perform this action inside of " + this.u);
        }
    }

    private void t() {
        if (this.m != null) {
            for (int i2 = 0; i2 < this.m.size(); i2++) {
                this.m.get(i2);
            }
        }
    }

    public final int a(a aVar) {
        int i2;
        synchronized (this) {
            if (this.l == null || this.l.size() <= 0) {
                if (this.k == null) {
                    this.k = new ArrayList();
                }
                i2 = this.k.size();
                if (a) {
                    "Setting back stack index " + i2 + " to " + aVar;
                }
                this.k.add(aVar);
            } else {
                i2 = ((Integer) this.l.remove(this.l.size() - 1)).intValue();
                if (a) {
                    "Adding back stack index " + i2 + " with " + aVar;
                }
                this.k.set(i2, aVar);
            }
        }
        return i2;
    }

    public final Fragment a(int i2) {
        if (this.g != null) {
            for (int size = this.g.size() - 1; size >= 0; size--) {
                Fragment fragment = (Fragment) this.g.get(size);
                if (fragment != null && fragment.w == i2) {
                    return fragment;
                }
            }
        }
        if (this.f != null) {
            for (int size2 = this.f.size() - 1; size2 >= 0; size2--) {
                Fragment fragment2 = (Fragment) this.f.get(size2);
                if (fragment2 != null && fragment2.w == i2) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    public final Fragment a(String str) {
        if (!(this.g == null || str == null)) {
            for (int size = this.g.size() - 1; size >= 0; size--) {
                Fragment fragment = (Fragment) this.g.get(size);
                if (fragment != null && str.equals(fragment.y)) {
                    return fragment;
                }
            }
        }
        if (!(this.f == null || str == null)) {
            for (int size2 = this.f.size() - 1; size2 >= 0; size2--) {
                Fragment fragment2 = (Fragment) this.f.get(size2);
                if (fragment2 != null && str.equals(fragment2.y)) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    public final s a() {
        return new a(this);
    }

    /* access modifiers changed from: package-private */
    public final void a(int i2, int i3, int i4, boolean z2) {
        if (this.o == null && i2 != 0) {
            throw new IllegalStateException("No activity");
        } else if (z2 || this.n != i2) {
            this.n = i2;
            if (this.f != null) {
                int i5 = 0;
                boolean z3 = false;
                while (i5 < this.f.size()) {
                    Fragment fragment = (Fragment) this.f.get(i5);
                    if (fragment != null) {
                        a(fragment, i2, i3, i4, false);
                        if (fragment.M != null) {
                            z3 |= fragment.M.a();
                        }
                    }
                    i5++;
                    z3 = z3;
                }
                if (!z3) {
                    r();
                }
                if (this.r && this.o != null && this.n == 5) {
                    this.o.a();
                    this.r = false;
                }
            }
        }
    }

    public final void a(Configuration configuration) {
        if (this.g != null) {
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 < this.g.size()) {
                    Fragment fragment = (Fragment) this.g.get(i3);
                    if (fragment != null) {
                        fragment.a(configuration);
                    }
                    i2 = i3 + 1;
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void a(Parcelable parcelable, ArrayList arrayList) {
        if (parcelable != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
            if (fragmentManagerState.a != null) {
                if (arrayList != null) {
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        Fragment fragment = (Fragment) arrayList.get(i2);
                        if (a) {
                            "restoreAllState: re-attaching retained " + fragment;
                        }
                        FragmentState fragmentState = fragmentManagerState.a[fragment.f];
                        fragmentState.k = fragment;
                        fragment.e = null;
                        fragment.r = 0;
                        fragment.p = false;
                        fragment.l = false;
                        fragment.i = null;
                        if (fragmentState.j != null) {
                            fragmentState.j.setClassLoader(this.o.getClassLoader());
                            fragment.e = fragmentState.j.getSparseParcelableArray("android:view_state");
                        }
                    }
                }
                this.f = new ArrayList(fragmentManagerState.a.length);
                if (this.h != null) {
                    this.h.clear();
                }
                for (int i3 = 0; i3 < fragmentManagerState.a.length; i3++) {
                    FragmentState fragmentState2 = fragmentManagerState.a[i3];
                    if (fragmentState2 != null) {
                        Fragment a2 = fragmentState2.a(this.o, this.q);
                        if (a) {
                            "restoreAllState: active #" + i3 + ": " + a2;
                        }
                        this.f.add(a2);
                        fragmentState2.k = null;
                    } else {
                        this.f.add((Object) null);
                        if (this.h == null) {
                            this.h = new ArrayList();
                        }
                        if (a) {
                            "restoreAllState: avail #" + i3;
                        }
                        this.h.add(Integer.valueOf(i3));
                    }
                }
                if (arrayList != null) {
                    for (int i4 = 0; i4 < arrayList.size(); i4++) {
                        Fragment fragment2 = (Fragment) arrayList.get(i4);
                        if (fragment2.j >= 0) {
                            if (fragment2.j < this.f.size()) {
                                fragment2.i = (Fragment) this.f.get(fragment2.j);
                            } else {
                                Log.w("FragmentManager", "Re-attaching retained fragment " + fragment2 + " target no longer exists: " + fragment2.j);
                                fragment2.i = null;
                            }
                        }
                    }
                }
                if (fragmentManagerState.b != null) {
                    this.g = new ArrayList(fragmentManagerState.b.length);
                    for (int i5 = 0; i5 < fragmentManagerState.b.length; i5++) {
                        Fragment fragment3 = (Fragment) this.f.get(fragmentManagerState.b[i5]);
                        if (fragment3 == null) {
                            a((RuntimeException) new IllegalStateException("No instantiated fragment for index #" + fragmentManagerState.b[i5]));
                        }
                        fragment3.l = true;
                        if (a) {
                            "restoreAllState: added #" + i5 + ": " + fragment3;
                        }
                        if (this.g.contains(fragment3)) {
                            throw new IllegalStateException("Already added!");
                        }
                        this.g.add(fragment3);
                    }
                } else {
                    this.g = null;
                }
                if (fragmentManagerState.c != null) {
                    this.i = new ArrayList(fragmentManagerState.c.length);
                    for (int i6 = 0; i6 < fragmentManagerState.c.length; i6++) {
                        a a3 = fragmentManagerState.c[i6].a(this);
                        if (a) {
                            "restoreAllState: back stack #" + i6 + " (index " + a3.o + "): " + a3;
                            a3.a("  ", new PrintWriter(new b("FragmentManager")), false);
                        }
                        this.i.add(a3);
                        if (a3.o >= 0) {
                            a(a3.o, a3);
                        }
                    }
                    return;
                }
                this.i = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void a(Fragment fragment) {
        a(fragment, this.n, 0, 0, false);
    }

    public final void a(Fragment fragment, int i2, int i3) {
        if (a) {
            "remove: " + fragment + " nesting=" + fragment.r;
        }
        boolean z2 = !fragment.b();
        if (!fragment.A || z2) {
            if (this.g != null) {
                this.g.remove(fragment);
            }
            if (fragment.D && fragment.E) {
                this.r = true;
            }
            fragment.l = false;
            fragment.m = true;
            a(fragment, z2 ? 0 : 1, i2, i3, false);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01ec, code lost:
        if (a == false) goto L_0x01fc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01ee, code lost:
        "moveto RESUMED: " + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01fc, code lost:
        r10.n = true;
        r10.r();
        r10.d = null;
        r10.e = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x020b, code lost:
        r10.J = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0219, code lost:
        if (r11 > 0) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x021d, code lost:
        if (r9.t == false) goto L_0x022a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0221, code lost:
        if (r10.b == null) goto L_0x022a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0223, code lost:
        r0 = r10.b;
        r10.b = null;
        r0.clearAnimation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x022c, code lost:
        if (r10.b == null) goto L_0x02de;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x022e, code lost:
        r10.c = r11;
        r11 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x024d, code lost:
        if (r11 >= 4) goto L_0x0264;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x0251, code lost:
        if (a == false) goto L_0x0261;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0253, code lost:
        "movefrom STARTED: " + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0261, code lost:
        r10.u();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x0264, code lost:
        if (r11 >= 3) goto L_0x027b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x0268, code lost:
        if (a == false) goto L_0x0278;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x026a, code lost:
        "movefrom STOPPED: " + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0278, code lost:
        r10.v();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x027c, code lost:
        if (r11 >= 2) goto L_0x0219;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0280, code lost:
        if (a == false) goto L_0x0290;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0282, code lost:
        "movefrom ACTIVITY_CREATED: " + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0292, code lost:
        if (r10.I == null) goto L_0x02a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x029a, code lost:
        if (r9.o.isFinishing() != false) goto L_0x02a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x029e, code lost:
        if (r10.e != null) goto L_0x02a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x02a0, code lost:
        b(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x02a3, code lost:
        r10.w();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x02a8, code lost:
        if (r10.I == null) goto L_0x02d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x02ac, code lost:
        if (r10.H == null) goto L_0x02d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x02b0, code lost:
        if (r9.n <= 0) goto L_0x0366;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x02b4, code lost:
        if (r9.t != false) goto L_0x0366;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x02b6, code lost:
        r0 = a(r10, r12, false, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x02ba, code lost:
        if (r0 == null) goto L_0x02cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x02bc, code lost:
        r10.b = r10.I;
        r10.c = r11;
        r0.setAnimationListener(new android.support.v4.app.n(r9, r10));
        r10.I.startAnimation(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x02cf, code lost:
        r10.H.removeView(r10.I);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x02d6, code lost:
        r10.H = null;
        r10.I = null;
        r10.J = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x02e0, code lost:
        if (a == false) goto L_0x02f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x02e2, code lost:
        "movefrom CREATED: " + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x02f2, code lost:
        if (r10.C != false) goto L_0x02f7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x02f4, code lost:
        r10.x();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x02f7, code lost:
        r10.F = false;
        r10.m();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x02fe, code lost:
        if (r10.F != false) goto L_0x031b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x031a, code lost:
        throw new android.support.v4.app.al("Fragment " + r10 + " did not call through to super.onDetach()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x031b, code lost:
        if (r14 != false) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x031f, code lost:
        if (r10.C != false) goto L_0x0360;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0323, code lost:
        if (r10.f < 0) goto L_0x0045;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x0327, code lost:
        if (a == false) goto L_0x0337;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0329, code lost:
        "Freeing fragment index " + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0337, code lost:
        r9.f.set(r10.f, (java.lang.Object) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0340, code lost:
        if (r9.h != null) goto L_0x0349;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x0342, code lost:
        r9.h = new java.util.ArrayList();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x0349, code lost:
        r9.h.add(java.lang.Integer.valueOf(r10.f));
        r9.o.a(r10.g);
        r10.l();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x0360, code lost:
        r10.t = null;
        r10.s = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x0366, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x0369, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0117, code lost:
        if (r11 <= 1) goto L_0x01d1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x011b, code lost:
        if (a == false) goto L_0x012b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x011d, code lost:
        "moveto ACTIVITY_CREATED: " + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x012d, code lost:
        if (r10.o != false) goto L_0x01c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0131, code lost:
        if (r10.x == 0) goto L_0x0369;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x0133, code lost:
        r0 = (android.view.ViewGroup) r9.p.a(r10.x);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x013d, code lost:
        if (r0 != null) goto L_0x017e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0141, code lost:
        if (r10.q != false) goto L_0x017e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0143, code lost:
        a((java.lang.RuntimeException) new java.lang.IllegalArgumentException("No view found for id 0x" + java.lang.Integer.toHexString(r10.x) + " (" + r10.c().getResourceName(r10.x) + ") for fragment " + r10));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x017e, code lost:
        r10.H = r0;
        r1 = r10.d;
        r10.g();
        r1 = r10.d;
        r10.I = r10.o();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x018f, code lost:
        if (r10.I == null) goto L_0x020b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0191, code lost:
        r10.J = r10.I;
        r10.I = android.support.v4.app.x.a(r10.I);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x019d, code lost:
        if (r0 == null) goto L_0x01af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x019f, code lost:
        r1 = a(r10, r12, true, r13);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01a3, code lost:
        if (r1 == null) goto L_0x01aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01a5, code lost:
        r10.I.startAnimation(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01aa, code lost:
        r0.addView(r10.I);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01b1, code lost:
        if (r10.z == false) goto L_0x01ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01b3, code lost:
        r10.I.setVisibility(8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01ba, code lost:
        r0 = r10.I;
        r0 = r10.d;
        android.support.v4.app.Fragment.k();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01c1, code lost:
        r0 = r10.d;
        r10.p();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x01c8, code lost:
        if (r10.I == null) goto L_0x01cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01ca, code lost:
        r0 = r10.d;
        r10.a();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01cf, code lost:
        r10.d = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01d1, code lost:
        if (r11 <= 3) goto L_0x01e8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01d5, code lost:
        if (a == false) goto L_0x01e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01d7, code lost:
        "moveto STARTED: " + r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01e5, code lost:
        r10.q();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01e8, code lost:
        if (r11 <= 4) goto L_0x0045;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.support.v4.app.Fragment r10, int r11, int r12, int r13, boolean r14) {
        /*
            r9 = this;
            r8 = 4
            r6 = 3
            r3 = 0
            r5 = 1
            r7 = 0
            boolean r0 = r10.l
            if (r0 == 0) goto L_0x000d
            boolean r0 = r10.A
            if (r0 == 0) goto L_0x0010
        L_0x000d:
            if (r11 <= r5) goto L_0x0010
            r11 = r5
        L_0x0010:
            boolean r0 = r10.m
            if (r0 == 0) goto L_0x001a
            int r0 = r10.a
            if (r11 <= r0) goto L_0x001a
            int r11 = r10.a
        L_0x001a:
            boolean r0 = r10.K
            if (r0 == 0) goto L_0x0025
            int r0 = r10.a
            if (r0 >= r8) goto L_0x0025
            if (r11 <= r6) goto L_0x0025
            r11 = r6
        L_0x0025:
            int r0 = r10.a
            if (r0 >= r11) goto L_0x020e
            boolean r0 = r10.o
            if (r0 == 0) goto L_0x0032
            boolean r0 = r10.p
            if (r0 != 0) goto L_0x0032
        L_0x0031:
            return
        L_0x0032:
            android.view.View r0 = r10.b
            if (r0 == 0) goto L_0x0040
            r10.b = r7
            int r2 = r10.c
            r0 = r9
            r1 = r10
            r4 = r3
            r0.a(r1, r2, r3, r4, r5)
        L_0x0040:
            int r0 = r10.a
            switch(r0) {
                case 0: goto L_0x0048;
                case 1: goto L_0x0117;
                case 2: goto L_0x01d1;
                case 3: goto L_0x01d1;
                case 4: goto L_0x01e8;
                default: goto L_0x0045;
            }
        L_0x0045:
            r10.a = r11
            goto L_0x0031
        L_0x0048:
            boolean r0 = a
            if (r0 == 0) goto L_0x005a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "moveto CREATED: "
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            r0.toString()
        L_0x005a:
            android.os.Bundle r0 = r10.d
            if (r0 == 0) goto L_0x0093
            android.os.Bundle r0 = r10.d
            java.lang.String r1 = "android:view_state"
            android.util.SparseArray r0 = r0.getSparseParcelableArray(r1)
            r10.e = r0
            android.os.Bundle r0 = r10.d
            java.lang.String r1 = "android:target_state"
            android.support.v4.app.Fragment r0 = r9.a((android.os.Bundle) r0, (java.lang.String) r1)
            r10.i = r0
            android.support.v4.app.Fragment r0 = r10.i
            if (r0 == 0) goto L_0x0080
            android.os.Bundle r0 = r10.d
            java.lang.String r1 = "android:target_req_state"
            int r0 = r0.getInt(r1, r3)
            r10.k = r0
        L_0x0080:
            android.os.Bundle r0 = r10.d
            java.lang.String r1 = "android:user_visible_hint"
            boolean r0 = r0.getBoolean(r1, r5)
            r10.L = r0
            boolean r0 = r10.L
            if (r0 != 0) goto L_0x0093
            r10.K = r5
            if (r11 <= r6) goto L_0x0093
            r11 = r6
        L_0x0093:
            android.support.v4.app.g r0 = r9.o
            r10.t = r0
            android.support.v4.app.Fragment r0 = r9.q
            r10.v = r0
            android.support.v4.app.Fragment r0 = r9.q
            if (r0 == 0) goto L_0x00cb
            android.support.v4.app.Fragment r0 = r9.q
            android.support.v4.app.l r0 = r0.u
        L_0x00a3:
            r10.s = r0
            r10.F = r3
            android.support.v4.app.g r0 = r9.o
            r10.i()
            boolean r0 = r10.F
            if (r0 != 0) goto L_0x00d0
            android.support.v4.app.al r0 = new android.support.v4.app.al
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Fragment "
            r1.<init>(r2)
            java.lang.StringBuilder r1 = r1.append(r10)
            java.lang.String r2 = " did not call through to super.onAttach()"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00cb:
            android.support.v4.app.g r0 = r9.o
            android.support.v4.app.l r0 = r0.b
            goto L_0x00a3
        L_0x00d0:
            android.support.v4.app.Fragment r0 = r10.v
            if (r0 != 0) goto L_0x00d9
            android.support.v4.app.g r0 = r9.o
            android.support.v4.app.g.b()
        L_0x00d9:
            boolean r0 = r10.C
            if (r0 != 0) goto L_0x00e2
            android.os.Bundle r0 = r10.d
            r10.a((android.os.Bundle) r0)
        L_0x00e2:
            r10.C = r3
            boolean r0 = r10.o
            if (r0 == 0) goto L_0x0117
            android.os.Bundle r0 = r10.d
            r10.g()
            android.os.Bundle r0 = r10.d
            android.view.View r0 = r10.o()
            r10.I = r0
            android.view.View r0 = r10.I
            if (r0 == 0) goto L_0x0207
            android.view.View r0 = r10.I
            r10.J = r0
            android.view.View r0 = r10.I
            android.view.ViewGroup r0 = android.support.v4.app.x.a(r0)
            r10.I = r0
            boolean r0 = r10.z
            if (r0 == 0) goto L_0x0110
            android.view.View r0 = r10.I
            r1 = 8
            r0.setVisibility(r1)
        L_0x0110:
            android.view.View r0 = r10.I
            android.os.Bundle r0 = r10.d
            android.support.v4.app.Fragment.k()
        L_0x0117:
            if (r11 <= r5) goto L_0x01d1
            boolean r0 = a
            if (r0 == 0) goto L_0x012b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "moveto ACTIVITY_CREATED: "
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            r0.toString()
        L_0x012b:
            boolean r0 = r10.o
            if (r0 != 0) goto L_0x01c1
            int r0 = r10.x
            if (r0 == 0) goto L_0x0369
            android.support.v4.app.j r0 = r9.p
            int r1 = r10.x
            android.view.View r0 = r0.a(r1)
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            if (r0 != 0) goto L_0x017e
            boolean r1 = r10.q
            if (r1 != 0) goto L_0x017e
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "No view found for id 0x"
            r2.<init>(r3)
            int r3 = r10.x
            java.lang.String r3 = java.lang.Integer.toHexString(r3)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = " ("
            java.lang.StringBuilder r2 = r2.append(r3)
            android.content.res.Resources r3 = r10.c()
            int r4 = r10.x
            java.lang.String r3 = r3.getResourceName(r4)
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = ") for fragment "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r10)
            java.lang.String r2 = r2.toString()
            r1.<init>(r2)
            r9.a((java.lang.RuntimeException) r1)
        L_0x017e:
            r10.H = r0
            android.os.Bundle r1 = r10.d
            r10.g()
            android.os.Bundle r1 = r10.d
            android.view.View r1 = r10.o()
            r10.I = r1
            android.view.View r1 = r10.I
            if (r1 == 0) goto L_0x020b
            android.view.View r1 = r10.I
            r10.J = r1
            android.view.View r1 = r10.I
            android.view.ViewGroup r1 = android.support.v4.app.x.a(r1)
            r10.I = r1
            if (r0 == 0) goto L_0x01af
            android.view.animation.Animation r1 = r9.a((android.support.v4.app.Fragment) r10, (int) r12, (boolean) r5, (int) r13)
            if (r1 == 0) goto L_0x01aa
            android.view.View r2 = r10.I
            r2.startAnimation(r1)
        L_0x01aa:
            android.view.View r1 = r10.I
            r0.addView(r1)
        L_0x01af:
            boolean r0 = r10.z
            if (r0 == 0) goto L_0x01ba
            android.view.View r0 = r10.I
            r1 = 8
            r0.setVisibility(r1)
        L_0x01ba:
            android.view.View r0 = r10.I
            android.os.Bundle r0 = r10.d
            android.support.v4.app.Fragment.k()
        L_0x01c1:
            android.os.Bundle r0 = r10.d
            r10.p()
            android.view.View r0 = r10.I
            if (r0 == 0) goto L_0x01cf
            android.os.Bundle r0 = r10.d
            r10.a()
        L_0x01cf:
            r10.d = r7
        L_0x01d1:
            if (r11 <= r6) goto L_0x01e8
            boolean r0 = a
            if (r0 == 0) goto L_0x01e5
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "moveto STARTED: "
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            r0.toString()
        L_0x01e5:
            r10.q()
        L_0x01e8:
            if (r11 <= r8) goto L_0x0045
            boolean r0 = a
            if (r0 == 0) goto L_0x01fc
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "moveto RESUMED: "
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            r0.toString()
        L_0x01fc:
            r10.n = r5
            r10.r()
            r10.d = r7
            r10.e = r7
            goto L_0x0045
        L_0x0207:
            r10.J = r7
            goto L_0x0117
        L_0x020b:
            r10.J = r7
            goto L_0x01c1
        L_0x020e:
            int r0 = r10.a
            if (r0 <= r11) goto L_0x0045
            int r0 = r10.a
            switch(r0) {
                case 1: goto L_0x0219;
                case 2: goto L_0x027b;
                case 3: goto L_0x0264;
                case 4: goto L_0x024d;
                case 5: goto L_0x0233;
                default: goto L_0x0217;
            }
        L_0x0217:
            goto L_0x0045
        L_0x0219:
            if (r11 > 0) goto L_0x0045
            boolean r0 = r9.t
            if (r0 == 0) goto L_0x022a
            android.view.View r0 = r10.b
            if (r0 == 0) goto L_0x022a
            android.view.View r0 = r10.b
            r10.b = r7
            r0.clearAnimation()
        L_0x022a:
            android.view.View r0 = r10.b
            if (r0 == 0) goto L_0x02de
            r10.c = r11
            r11 = r5
            goto L_0x0045
        L_0x0233:
            r0 = 5
            if (r11 >= r0) goto L_0x024d
            boolean r0 = a
            if (r0 == 0) goto L_0x0248
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "movefrom RESUMED: "
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            r0.toString()
        L_0x0248:
            r10.t()
            r10.n = r3
        L_0x024d:
            if (r11 >= r8) goto L_0x0264
            boolean r0 = a
            if (r0 == 0) goto L_0x0261
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "movefrom STARTED: "
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            r0.toString()
        L_0x0261:
            r10.u()
        L_0x0264:
            if (r11 >= r6) goto L_0x027b
            boolean r0 = a
            if (r0 == 0) goto L_0x0278
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "movefrom STOPPED: "
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            r0.toString()
        L_0x0278:
            r10.v()
        L_0x027b:
            r0 = 2
            if (r11 >= r0) goto L_0x0219
            boolean r0 = a
            if (r0 == 0) goto L_0x0290
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "movefrom ACTIVITY_CREATED: "
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            r0.toString()
        L_0x0290:
            android.view.View r0 = r10.I
            if (r0 == 0) goto L_0x02a3
            android.support.v4.app.g r0 = r9.o
            boolean r0 = r0.isFinishing()
            if (r0 != 0) goto L_0x02a3
            android.util.SparseArray r0 = r10.e
            if (r0 != 0) goto L_0x02a3
            r9.b((android.support.v4.app.Fragment) r10)
        L_0x02a3:
            r10.w()
            android.view.View r0 = r10.I
            if (r0 == 0) goto L_0x02d6
            android.view.ViewGroup r0 = r10.H
            if (r0 == 0) goto L_0x02d6
            int r0 = r9.n
            if (r0 <= 0) goto L_0x0366
            boolean r0 = r9.t
            if (r0 != 0) goto L_0x0366
            android.view.animation.Animation r0 = r9.a((android.support.v4.app.Fragment) r10, (int) r12, (boolean) r3, (int) r13)
        L_0x02ba:
            if (r0 == 0) goto L_0x02cf
            android.view.View r1 = r10.I
            r10.b = r1
            r10.c = r11
            android.support.v4.app.n r1 = new android.support.v4.app.n
            r1.<init>(r9, r10)
            r0.setAnimationListener(r1)
            android.view.View r1 = r10.I
            r1.startAnimation(r0)
        L_0x02cf:
            android.view.ViewGroup r0 = r10.H
            android.view.View r1 = r10.I
            r0.removeView(r1)
        L_0x02d6:
            r10.H = r7
            r10.I = r7
            r10.J = r7
            goto L_0x0219
        L_0x02de:
            boolean r0 = a
            if (r0 == 0) goto L_0x02f0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "movefrom CREATED: "
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            r0.toString()
        L_0x02f0:
            boolean r0 = r10.C
            if (r0 != 0) goto L_0x02f7
            r10.x()
        L_0x02f7:
            r10.F = r3
            r10.m()
            boolean r0 = r10.F
            if (r0 != 0) goto L_0x031b
            android.support.v4.app.al r0 = new android.support.v4.app.al
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Fragment "
            r1.<init>(r2)
            java.lang.StringBuilder r1 = r1.append(r10)
            java.lang.String r2 = " did not call through to super.onDetach()"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x031b:
            if (r14 != 0) goto L_0x0045
            boolean r0 = r10.C
            if (r0 != 0) goto L_0x0360
            int r0 = r10.f
            if (r0 < 0) goto L_0x0045
            boolean r0 = a
            if (r0 == 0) goto L_0x0337
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "Freeing fragment index "
            r0.<init>(r1)
            java.lang.StringBuilder r0 = r0.append(r10)
            r0.toString()
        L_0x0337:
            java.util.ArrayList r0 = r9.f
            int r1 = r10.f
            r0.set(r1, r7)
            java.util.ArrayList r0 = r9.h
            if (r0 != 0) goto L_0x0349
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r9.h = r0
        L_0x0349:
            java.util.ArrayList r0 = r9.h
            int r1 = r10.f
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r0.add(r1)
            android.support.v4.app.g r0 = r9.o
            java.lang.String r1 = r10.g
            r0.a((java.lang.String) r1)
            r10.l()
            goto L_0x0045
        L_0x0360:
            r10.t = r7
            r10.s = r7
            goto L_0x0045
        L_0x0366:
            r0 = r7
            goto L_0x02ba
        L_0x0369:
            r0 = r7
            goto L_0x017e
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.l.a(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    public final void a(Fragment fragment, boolean z2) {
        if (this.g == null) {
            this.g = new ArrayList();
        }
        if (a) {
            "add: " + fragment;
        }
        if (fragment.f < 0) {
            if (this.h == null || this.h.size() <= 0) {
                if (this.f == null) {
                    this.f = new ArrayList();
                }
                fragment.a(this.f.size(), this.q);
                this.f.add(fragment);
            } else {
                fragment.a(((Integer) this.h.remove(this.h.size() - 1)).intValue(), this.q);
                this.f.set(fragment.f, fragment);
            }
            if (a) {
                "Allocated fragment index " + fragment;
            }
        }
        if (fragment.A) {
            return;
        }
        if (this.g.contains(fragment)) {
            throw new IllegalStateException("Fragment already added: " + fragment);
        }
        this.g.add(fragment);
        fragment.l = true;
        fragment.m = false;
        if (fragment.D && fragment.E) {
            this.r = true;
        }
        if (z2) {
            a(fragment);
        }
    }

    public final void a(g gVar, j jVar, Fragment fragment) {
        if (this.o != null) {
            throw new IllegalStateException("Already attached");
        }
        this.o = gVar;
        this.p = jVar;
        this.q = fragment;
    }

    public final void a(Runnable runnable) {
        s();
        synchronized (this) {
            if (this.o == null) {
                throw new IllegalStateException("Activity has been destroyed");
            }
            if (this.c == null) {
                this.c = new ArrayList();
            }
            this.c.add(runnable);
            if (this.c.size() == 1) {
                this.o.a.removeCallbacks(this.y);
                this.o.a.post(this.y);
            }
        }
    }

    public final void a(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int size;
        int size2;
        int size3;
        int size4;
        int size5;
        int size6;
        String str2 = str + "    ";
        if (this.f != null && (size6 = this.f.size()) > 0) {
            printWriter.print(str);
            printWriter.print("Active Fragments in ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this)));
            printWriter.println(":");
            for (int i2 = 0; i2 < size6; i2++) {
                Fragment fragment = (Fragment) this.f.get(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.println(fragment);
                if (fragment != null) {
                    fragment.a(str2, fileDescriptor, printWriter, strArr);
                }
            }
        }
        if (this.g != null && (size5 = this.g.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Added Fragments:");
            for (int i3 = 0; i3 < size5; i3++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i3);
                printWriter.print(": ");
                printWriter.println(((Fragment) this.g.get(i3)).toString());
            }
        }
        if (this.j != null && (size4 = this.j.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Fragments Created Menus:");
            for (int i4 = 0; i4 < size4; i4++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i4);
                printWriter.print(": ");
                printWriter.println(((Fragment) this.j.get(i4)).toString());
            }
        }
        if (this.i != null && (size3 = this.i.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Back Stack:");
            for (int i5 = 0; i5 < size3; i5++) {
                a aVar = (a) this.i.get(i5);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i5);
                printWriter.print(": ");
                printWriter.println(aVar.toString());
                aVar.a(str2, printWriter);
            }
        }
        synchronized (this) {
            if (this.k != null && (size2 = this.k.size()) > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack Indices:");
                for (int i6 = 0; i6 < size2; i6++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i6);
                    printWriter.print(": ");
                    printWriter.println((a) this.k.get(i6));
                }
            }
            if (this.l != null && this.l.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.l.toArray()));
            }
        }
        if (this.c != null && (size = this.c.size()) > 0) {
            printWriter.print(str);
            printWriter.println("Pending Actions:");
            for (int i7 = 0; i7 < size; i7++) {
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(i7);
                printWriter.print(": ");
                printWriter.println((Runnable) this.c.get(i7));
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mActivity=");
        printWriter.println(this.o);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.p);
        if (this.q != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.q);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.n);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.s);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.t);
        if (this.r) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.r);
        }
        if (this.u != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.u);
        }
        if (this.h != null && this.h.size() > 0) {
            printWriter.print(str);
            printWriter.print("  mAvailIndices: ");
            printWriter.println(Arrays.toString(this.h.toArray()));
        }
    }

    public final boolean a(Menu menu) {
        if (this.g == null) {
            return false;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            Fragment fragment = (Fragment) this.g.get(i2);
            if (fragment != null && fragment.a(menu)) {
                z2 = true;
            }
        }
        return z2;
    }

    public final boolean a(Menu menu, MenuInflater menuInflater) {
        boolean z2;
        ArrayList arrayList = null;
        if (this.g != null) {
            int i2 = 0;
            z2 = false;
            while (i2 < this.g.size()) {
                Fragment fragment = (Fragment) this.g.get(i2);
                if (fragment != null && fragment.a(menu, menuInflater)) {
                    z2 = true;
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(fragment);
                }
                i2++;
                z2 = z2;
            }
        } else {
            z2 = false;
        }
        if (this.j != null) {
            for (int i3 = 0; i3 < this.j.size(); i3++) {
                Fragment fragment2 = (Fragment) this.j.get(i3);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    Fragment.n();
                }
            }
        }
        this.j = arrayList;
        return z2;
    }

    public final boolean a(MenuItem menuItem) {
        if (this.g == null) {
            return false;
        }
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            Fragment fragment = (Fragment) this.g.get(i2);
            if (fragment != null && fragment.a(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public final void b(int i2) {
        synchronized (this) {
            this.k.set(i2, (Object) null);
            if (this.l == null) {
                this.l = new ArrayList();
            }
            if (a) {
                "Freeing back stack index " + i2;
            }
            this.l.add(Integer.valueOf(i2));
        }
    }

    public final void b(Fragment fragment, int i2, int i3) {
        if (a) {
            "hide: " + fragment;
        }
        if (!fragment.z) {
            fragment.z = true;
            if (fragment.I != null) {
                Animation a2 = a(fragment, i2, true, i3);
                if (a2 != null) {
                    fragment.I.startAnimation(a2);
                }
                fragment.I.setVisibility(8);
            }
            if (fragment.l && fragment.D && fragment.E) {
                this.r = true;
            }
            Fragment.e();
        }
    }

    /* access modifiers changed from: package-private */
    public final void b(a aVar) {
        if (this.i == null) {
            this.i = new ArrayList();
        }
        this.i.add(aVar);
        t();
    }

    public final void b(Menu menu) {
        if (this.g != null) {
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 < this.g.size()) {
                    Fragment fragment = (Fragment) this.g.get(i3);
                    if (fragment != null) {
                        fragment.b(menu);
                    }
                    i2 = i3 + 1;
                } else {
                    return;
                }
            }
        }
    }

    public final boolean b() {
        return d();
    }

    public final boolean b(MenuItem menuItem) {
        if (this.g == null) {
            return false;
        }
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            Fragment fragment = (Fragment) this.g.get(i2);
            if (fragment != null && fragment.b(menuItem)) {
                return true;
            }
        }
        return false;
    }

    public final void c(Fragment fragment, int i2, int i3) {
        if (a) {
            "show: " + fragment;
        }
        if (fragment.z) {
            fragment.z = false;
            if (fragment.I != null) {
                Animation a2 = a(fragment, i2, true, i3);
                if (a2 != null) {
                    fragment.I.startAnimation(a2);
                }
                fragment.I.setVisibility(0);
            }
            if (fragment.l && fragment.D && fragment.E) {
                this.r = true;
            }
            Fragment.e();
        }
    }

    public final boolean c() {
        int size;
        s();
        d();
        Handler handler = this.o.a;
        if (this.i == null || this.i.size() - 1 < 0) {
            return false;
        }
        ((a) this.i.remove(size)).b();
        t();
        return true;
    }

    public final void d(Fragment fragment, int i2, int i3) {
        if (a) {
            "detach: " + fragment;
        }
        if (!fragment.A) {
            fragment.A = true;
            if (fragment.l) {
                if (this.g != null) {
                    if (a) {
                        "remove from detach: " + fragment;
                    }
                    this.g.remove(fragment);
                }
                if (fragment.D && fragment.E) {
                    this.r = true;
                }
                fragment.l = false;
                a(fragment, 1, i2, i3, false);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0083, code lost:
        r6.e = true;
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0086, code lost:
        if (r1 >= r3) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0088, code lost:
        r6.d[r1].run();
        r6.d[r1] = null;
        r1 = r1 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean d() {
        /*
            r6 = this;
            r0 = 1
            r2 = 0
            boolean r1 = r6.e
            if (r1 == 0) goto L_0x000e
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Recursive entry to executePendingTransactions"
            r0.<init>(r1)
            throw r0
        L_0x000e:
            android.os.Looper r1 = android.os.Looper.myLooper()
            android.support.v4.app.g r3 = r6.o
            android.os.Handler r3 = r3.a
            android.os.Looper r3 = r3.getLooper()
            if (r1 == r3) goto L_0x0024
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Must be called from main thread of process"
            r0.<init>(r1)
            throw r0
        L_0x0024:
            r1 = r2
        L_0x0025:
            monitor-enter(r6)
            java.util.ArrayList r3 = r6.c     // Catch:{ all -> 0x0097 }
            if (r3 == 0) goto L_0x0032
            java.util.ArrayList r3 = r6.c     // Catch:{ all -> 0x0097 }
            int r3 = r3.size()     // Catch:{ all -> 0x0097 }
            if (r3 != 0) goto L_0x005a
        L_0x0032:
            monitor-exit(r6)     // Catch:{ all -> 0x0097 }
            boolean r0 = r6.v
            if (r0 == 0) goto L_0x00a5
            r3 = r2
            r4 = r2
        L_0x0039:
            java.util.ArrayList r0 = r6.f
            int r0 = r0.size()
            if (r3 >= r0) goto L_0x009e
            java.util.ArrayList r0 = r6.f
            java.lang.Object r0 = r0.get(r3)
            android.support.v4.app.Fragment r0 = (android.support.v4.app.Fragment) r0
            if (r0 == 0) goto L_0x0056
            android.support.v4.app.v r5 = r0.M
            if (r5 == 0) goto L_0x0056
            android.support.v4.app.v r0 = r0.M
            boolean r0 = r0.a()
            r4 = r4 | r0
        L_0x0056:
            int r0 = r3 + 1
            r3 = r0
            goto L_0x0039
        L_0x005a:
            java.util.ArrayList r1 = r6.c     // Catch:{ all -> 0x0097 }
            int r3 = r1.size()     // Catch:{ all -> 0x0097 }
            java.lang.Runnable[] r1 = r6.d     // Catch:{ all -> 0x0097 }
            if (r1 == 0) goto L_0x0069
            java.lang.Runnable[] r1 = r6.d     // Catch:{ all -> 0x0097 }
            int r1 = r1.length     // Catch:{ all -> 0x0097 }
            if (r1 >= r3) goto L_0x006d
        L_0x0069:
            java.lang.Runnable[] r1 = new java.lang.Runnable[r3]     // Catch:{ all -> 0x0097 }
            r6.d = r1     // Catch:{ all -> 0x0097 }
        L_0x006d:
            java.util.ArrayList r1 = r6.c     // Catch:{ all -> 0x0097 }
            java.lang.Runnable[] r4 = r6.d     // Catch:{ all -> 0x0097 }
            r1.toArray(r4)     // Catch:{ all -> 0x0097 }
            java.util.ArrayList r1 = r6.c     // Catch:{ all -> 0x0097 }
            r1.clear()     // Catch:{ all -> 0x0097 }
            android.support.v4.app.g r1 = r6.o     // Catch:{ all -> 0x0097 }
            android.os.Handler r1 = r1.a     // Catch:{ all -> 0x0097 }
            java.lang.Runnable r4 = r6.y     // Catch:{ all -> 0x0097 }
            r1.removeCallbacks(r4)     // Catch:{ all -> 0x0097 }
            monitor-exit(r6)     // Catch:{ all -> 0x0097 }
            r6.e = r0
            r1 = r2
        L_0x0086:
            if (r1 >= r3) goto L_0x009a
            java.lang.Runnable[] r4 = r6.d
            r4 = r4[r1]
            r4.run()
            java.lang.Runnable[] r4 = r6.d
            r5 = 0
            r4[r1] = r5
            int r1 = r1 + 1
            goto L_0x0086
        L_0x0097:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        L_0x009a:
            r6.e = r2
            r1 = r0
            goto L_0x0025
        L_0x009e:
            if (r4 != 0) goto L_0x00a5
            r6.v = r2
            r6.r()
        L_0x00a5:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.l.d():boolean");
    }

    /* access modifiers changed from: package-private */
    public final ArrayList e() {
        ArrayList arrayList = null;
        if (this.f != null) {
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 >= this.f.size()) {
                    break;
                }
                Fragment fragment = (Fragment) this.f.get(i3);
                if (fragment != null && fragment.B) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(fragment);
                    fragment.C = true;
                    fragment.j = fragment.i != null ? fragment.i.f : -1;
                    if (a) {
                        "retainNonConfig: keeping retained " + fragment;
                    }
                }
                i2 = i3 + 1;
            }
        }
        return arrayList;
    }

    public final void e(Fragment fragment, int i2, int i3) {
        if (a) {
            "attach: " + fragment;
        }
        if (fragment.A) {
            fragment.A = false;
            if (!fragment.l) {
                if (this.g == null) {
                    this.g = new ArrayList();
                }
                if (this.g.contains(fragment)) {
                    throw new IllegalStateException("Fragment already added: " + fragment);
                }
                if (a) {
                    "add from attach: " + fragment;
                }
                this.g.add(fragment);
                fragment.l = true;
                if (fragment.D && fragment.E) {
                    this.r = true;
                }
                a(fragment, this.n, i2, i3, false);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final Parcelable f() {
        int[] iArr;
        int size;
        int size2;
        boolean z2;
        Bundle bundle;
        BackStackState[] backStackStateArr = null;
        d();
        if (b) {
            this.s = true;
        }
        if (this.f == null || this.f.size() <= 0) {
            return null;
        }
        int size3 = this.f.size();
        FragmentState[] fragmentStateArr = new FragmentState[size3];
        int i2 = 0;
        boolean z3 = false;
        while (i2 < size3) {
            Fragment fragment = (Fragment) this.f.get(i2);
            if (fragment != null) {
                if (fragment.f < 0) {
                    a((RuntimeException) new IllegalStateException("Failure saving state: active " + fragment + " has cleared index: " + fragment.f));
                }
                FragmentState fragmentState = new FragmentState(fragment);
                fragmentStateArr[i2] = fragmentState;
                if (fragment.a <= 0 || fragmentState.j != null) {
                    fragmentState.j = fragment.d;
                } else {
                    if (this.w == null) {
                        this.w = new Bundle();
                    }
                    fragment.b(this.w);
                    if (!this.w.isEmpty()) {
                        bundle = this.w;
                        this.w = null;
                    } else {
                        bundle = null;
                    }
                    if (fragment.I != null) {
                        b(fragment);
                    }
                    if (fragment.e != null) {
                        if (bundle == null) {
                            bundle = new Bundle();
                        }
                        bundle.putSparseParcelableArray("android:view_state", fragment.e);
                    }
                    if (!fragment.L) {
                        if (bundle == null) {
                            bundle = new Bundle();
                        }
                        bundle.putBoolean("android:user_visible_hint", fragment.L);
                    }
                    fragmentState.j = bundle;
                    if (fragment.i != null) {
                        if (fragment.i.f < 0) {
                            a((RuntimeException) new IllegalStateException("Failure saving state: " + fragment + " has target not in fragment manager: " + fragment.i));
                        }
                        if (fragmentState.j == null) {
                            fragmentState.j = new Bundle();
                        }
                        Bundle bundle2 = fragmentState.j;
                        Fragment fragment2 = fragment.i;
                        if (fragment2.f < 0) {
                            a((RuntimeException) new IllegalStateException("Fragment " + fragment2 + " is not currently in the FragmentManager"));
                        }
                        bundle2.putInt("android:target_state", fragment2.f);
                        if (fragment.k != 0) {
                            fragmentState.j.putInt("android:target_req_state", fragment.k);
                        }
                    }
                }
                if (a) {
                    "Saved state of " + fragment + ": " + fragmentState.j;
                }
                z2 = true;
            } else {
                z2 = z3;
            }
            i2++;
            z3 = z2;
        }
        if (!z3) {
            boolean z4 = a;
            return null;
        }
        if (this.g == null || (size2 = this.g.size()) <= 0) {
            iArr = null;
        } else {
            iArr = new int[size2];
            for (int i3 = 0; i3 < size2; i3++) {
                iArr[i3] = ((Fragment) this.g.get(i3)).f;
                if (iArr[i3] < 0) {
                    a((RuntimeException) new IllegalStateException("Failure saving state: active " + this.g.get(i3) + " has cleared index: " + iArr[i3]));
                }
                if (a) {
                    "saveAllState: adding fragment #" + i3 + ": " + this.g.get(i3);
                }
            }
        }
        if (this.i != null && (size = this.i.size()) > 0) {
            backStackStateArr = new BackStackState[size];
            for (int i4 = 0; i4 < size; i4++) {
                backStackStateArr[i4] = new BackStackState((a) this.i.get(i4));
                if (a) {
                    "saveAllState: adding back stack #" + i4 + ": " + this.i.get(i4);
                }
            }
        }
        FragmentManagerState fragmentManagerState = new FragmentManagerState();
        fragmentManagerState.a = fragmentStateArr;
        fragmentManagerState.b = iArr;
        fragmentManagerState.c = backStackStateArr;
        return fragmentManagerState;
    }

    public final void g() {
        this.s = false;
    }

    public final void h() {
        this.s = false;
        d(1);
    }

    public final void i() {
        this.s = false;
        d(2);
    }

    public final void j() {
        this.s = false;
        d(4);
    }

    public final void k() {
        this.s = false;
        d(5);
    }

    public final void l() {
        d(4);
    }

    public final void m() {
        this.s = true;
        d(3);
    }

    public final void n() {
        d(2);
    }

    public final void o() {
        d(1);
    }

    public final void p() {
        this.t = true;
        d();
        d(0);
        this.o = null;
        this.p = null;
        this.q = null;
    }

    public final void q() {
        if (this.g != null) {
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i3 < this.g.size()) {
                    Fragment fragment = (Fragment) this.g.get(i3);
                    if (fragment != null) {
                        fragment.s();
                    }
                    i2 = i3 + 1;
                } else {
                    return;
                }
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        if (this.q != null) {
            a.a(this.q, sb);
        } else {
            a.a(this.o, sb);
        }
        sb.append("}}");
        return sb.toString();
    }
}
