package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public final class g extends Activity {
    final Handler a;
    final l b;
    final j c;
    boolean d;
    boolean e;
    boolean f;
    boolean g;
    boolean h;
    boolean i;
    boolean j;
    boolean k;
    HashMap l;
    v m;

    private static String a(View view) {
        String str;
        char c2 = 'F';
        char c3 = '.';
        StringBuilder sb = new StringBuilder(128);
        sb.append(view.getClass().getName());
        sb.append('{');
        sb.append(Integer.toHexString(System.identityHashCode(view)));
        sb.append(' ');
        switch (view.getVisibility()) {
            case 0:
                sb.append('V');
                break;
            case 4:
                sb.append('I');
                break;
            case 8:
                sb.append('G');
                break;
            default:
                sb.append('.');
                break;
        }
        sb.append(view.isFocusable() ? 'F' : '.');
        sb.append(view.isEnabled() ? 'E' : '.');
        sb.append(view.willNotDraw() ? '.' : 'D');
        sb.append(view.isHorizontalScrollBarEnabled() ? 'H' : '.');
        sb.append(view.isVerticalScrollBarEnabled() ? 'V' : '.');
        sb.append(view.isClickable() ? 'C' : '.');
        sb.append(view.isLongClickable() ? 'L' : '.');
        sb.append(' ');
        if (!view.isFocused()) {
            c2 = '.';
        }
        sb.append(c2);
        sb.append(view.isSelected() ? 'S' : '.');
        if (view.isPressed()) {
            c3 = 'P';
        }
        sb.append(c3);
        sb.append(' ');
        sb.append(view.getLeft());
        sb.append(',');
        sb.append(view.getTop());
        sb.append('-');
        sb.append(view.getRight());
        sb.append(',');
        sb.append(view.getBottom());
        int id = view.getId();
        if (id != -1) {
            sb.append(" #");
            sb.append(Integer.toHexString(id));
            Resources resources = view.getResources();
            if (!(id == 0 || resources == null)) {
                switch (-16777216 & id) {
                    case 16777216:
                        str = "android";
                        break;
                    case 2130706432:
                        str = "app";
                        break;
                    default:
                        try {
                            str = resources.getResourcePackageName(id);
                            break;
                        } catch (Resources.NotFoundException e2) {
                            break;
                        }
                }
                String resourceTypeName = resources.getResourceTypeName(id);
                String resourceEntryName = resources.getResourceEntryName(id);
                sb.append(" ");
                sb.append(str);
                sb.append(":");
                sb.append(resourceTypeName);
                sb.append("/");
                sb.append(resourceEntryName);
            }
        }
        sb.append("}");
        return sb.toString();
    }

    private void a(String str, PrintWriter printWriter, View view) {
        ViewGroup viewGroup;
        int childCount;
        printWriter.print(str);
        if (view == null) {
            printWriter.println("null");
            return;
        }
        printWriter.println(a(view));
        if ((view instanceof ViewGroup) && (childCount = viewGroup.getChildCount()) > 0) {
            String str2 = str + "  ";
            for (int i2 = 0; i2 < childCount; i2++) {
                a(str2, printWriter, (viewGroup = (ViewGroup) view).getChildAt(i2));
            }
        }
    }

    private void a(boolean z) {
        if (!this.g) {
            this.g = true;
            this.h = z;
            this.a.removeMessages(1);
            if (this.k) {
                this.k = false;
                if (this.m != null) {
                    if (!this.h) {
                        this.m.c();
                    } else {
                        this.m.d();
                    }
                }
            }
            this.b.n();
        }
    }

    public static void b() {
    }

    public final void a() {
        if (Build.VERSION.SDK_INT >= 11) {
            invalidateOptionsMenu();
        } else {
            this.i = true;
        }
    }

    /* access modifiers changed from: package-private */
    public final void a(String str) {
        v vVar;
        if (this.l != null && (vVar = (v) this.l.get(str)) != null && !vVar.g) {
            vVar.h();
            this.l.remove(str);
        }
    }

    /* access modifiers changed from: package-private */
    public final v b(String str) {
        if (this.l == null) {
            this.l = new HashMap();
        }
        v vVar = (v) this.l.get(str);
        if (vVar != null) {
            vVar.a(this);
        }
        return vVar;
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i2 = Build.VERSION.SDK_INT;
        printWriter.print(str);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mCreated=");
        printWriter.print(this.d);
        printWriter.print("mResumed=");
        printWriter.print(this.e);
        printWriter.print(" mStopped=");
        printWriter.print(this.f);
        printWriter.print(" mReallyStopped=");
        printWriter.println(this.g);
        printWriter.print(str2);
        printWriter.print("mLoadersStarted=");
        printWriter.println(this.k);
        if (this.m != null) {
            printWriter.print(str);
            printWriter.print("Loader Manager ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this.m)));
            printWriter.println(":");
            this.m.a(str + "  ", printWriter);
        }
        this.b.a(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.println("View Hierarchy:");
        a(str + "  ", printWriter, getWindow().getDecorView());
    }

    /* access modifiers changed from: protected */
    public final void onActivityResult(int i2, int i3, Intent intent) {
        this.b.g();
        int i4 = i2 >> 16;
        if (i4 != 0) {
            int i5 = i4 - 1;
            if (this.b.f == null || i5 < 0 || i5 >= this.b.f.size()) {
                Log.w("FragmentActivity", "Activity result fragment index out of range: 0x" + Integer.toHexString(i2));
            } else if (((Fragment) this.b.f.get(i5)) == null) {
                Log.w("FragmentActivity", "Activity result no fragment exists for index: 0x" + Integer.toHexString(i2));
            } else {
                Fragment.f();
            }
        } else {
            super.onActivityResult(i2, i3, intent);
        }
    }

    public final void onBackPressed() {
        if (!this.b.c()) {
            finish();
        }
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.b.a(configuration);
    }

    /* access modifiers changed from: protected */
    public final void onCreate(Bundle bundle) {
        this.b.a(this, this.c, (Fragment) null);
        if (getLayoutInflater().getFactory() == null) {
            getLayoutInflater().setFactory(this);
        }
        super.onCreate(bundle);
        i iVar = (i) getLastNonConfigurationInstance();
        if (iVar != null) {
            this.l = iVar.e;
        }
        if (bundle != null) {
            this.b.a(bundle.getParcelable("android:support:fragments"), iVar != null ? iVar.d : null);
        }
        this.b.h();
    }

    public final boolean onCreatePanelMenu(int i2, Menu menu) {
        if (i2 != 0) {
            return super.onCreatePanelMenu(i2, menu);
        }
        boolean onCreatePanelMenu = super.onCreatePanelMenu(i2, menu) | this.b.a(menu, getMenuInflater());
        if (Build.VERSION.SDK_INT >= 11) {
            return onCreatePanelMenu;
        }
        return true;
    }

    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        Fragment fragment = null;
        if (!"fragment".equals(str)) {
            return super.onCreateView(str, context, attributeSet);
        }
        String attributeValue = attributeSet.getAttributeValue((String) null, "class");
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, h.a);
        if (attributeValue == null) {
            attributeValue = obtainStyledAttributes.getString(0);
        }
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        String string = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        if (resourceId != -1) {
            fragment = this.b.a(resourceId);
        }
        if (fragment == null && string != null) {
            fragment = this.b.a(string);
        }
        if (fragment == null) {
            fragment = this.b.a(0);
        }
        if (l.a) {
            "onCreateView: id=0x" + Integer.toHexString(resourceId) + " fname=" + attributeValue + " existing=" + fragment;
        }
        if (fragment == null) {
            Fragment a2 = Fragment.a((Context) this, attributeValue);
            a2.o = true;
            a2.w = resourceId != 0 ? resourceId : 0;
            a2.x = 0;
            a2.y = string;
            a2.p = true;
            a2.s = this.b;
            Bundle bundle = a2.d;
            a2.h();
            this.b.a(a2, true);
            fragment = a2;
        } else if (fragment.p) {
            throw new IllegalArgumentException(attributeSet.getPositionDescription() + ": Duplicate id 0x" + Integer.toHexString(resourceId) + ", tag " + string + ", or parent id 0x" + Integer.toHexString(0) + " with another fragment for " + attributeValue);
        } else {
            fragment.p = true;
            if (!fragment.C) {
                Bundle bundle2 = fragment.d;
                fragment.h();
            }
            this.b.a(fragment);
        }
        if (fragment.I == null) {
            throw new IllegalStateException("Fragment " + attributeValue + " did not create a view.");
        }
        if (resourceId != 0) {
            fragment.I.setId(resourceId);
        }
        if (fragment.I.getTag() == null) {
            fragment.I.setTag(string);
        }
        return fragment.I;
    }

    /* access modifiers changed from: protected */
    public final void onDestroy() {
        super.onDestroy();
        a(false);
        this.b.p();
        if (this.m != null) {
            this.m.h();
        }
    }

    public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (Build.VERSION.SDK_INT >= 5 || i2 != 4 || keyEvent.getRepeatCount() != 0) {
            return super.onKeyDown(i2, keyEvent);
        }
        onBackPressed();
        return true;
    }

    public final void onLowMemory() {
        super.onLowMemory();
        this.b.q();
    }

    public final boolean onMenuItemSelected(int i2, MenuItem menuItem) {
        if (super.onMenuItemSelected(i2, menuItem)) {
            return true;
        }
        switch (i2) {
            case 0:
                return this.b.a(menuItem);
            case 6:
                return this.b.b(menuItem);
            default:
                return false;
        }
    }

    /* access modifiers changed from: protected */
    public final void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.b.g();
    }

    public final void onPanelClosed(int i2, Menu menu) {
        switch (i2) {
            case 0:
                this.b.b(menu);
                break;
        }
        super.onPanelClosed(i2, menu);
    }

    /* access modifiers changed from: protected */
    public final void onPause() {
        super.onPause();
        this.e = false;
        if (this.a.hasMessages(2)) {
            this.a.removeMessages(2);
            this.b.k();
        }
        this.b.l();
    }

    /* access modifiers changed from: protected */
    public final void onPostResume() {
        super.onPostResume();
        this.a.removeMessages(2);
        this.b.k();
        this.b.d();
    }

    public final boolean onPreparePanel(int i2, View view, Menu menu) {
        if (i2 != 0 || menu == null) {
            return super.onPreparePanel(i2, view, menu);
        }
        if (this.i) {
            this.i = false;
            menu.clear();
            onCreatePanelMenu(i2, menu);
        }
        return (super.onPreparePanel(i2, view, menu) || this.b.a(menu)) && menu.hasVisibleItems();
    }

    /* access modifiers changed from: protected */
    public final void onResume() {
        super.onResume();
        this.a.sendEmptyMessage(2);
        this.e = true;
        this.b.d();
    }

    public final Object onRetainNonConfigurationInstance() {
        boolean z;
        if (this.f) {
            a(true);
        }
        ArrayList e2 = this.b.e();
        if (this.l != null) {
            v[] vVarArr = new v[this.l.size()];
            this.l.values().toArray(vVarArr);
            z = false;
            for (v vVar : vVarArr) {
                if (vVar.g) {
                    z = true;
                } else {
                    vVar.h();
                    this.l.remove(vVar.d);
                }
            }
        } else {
            z = false;
        }
        if (e2 == null && !z) {
            return null;
        }
        i iVar = new i();
        iVar.a = null;
        iVar.b = null;
        iVar.c = null;
        iVar.d = e2;
        iVar.e = this.l;
        return iVar;
    }

    /* access modifiers changed from: protected */
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Parcelable f2 = this.b.f();
        if (f2 != null) {
            bundle.putParcelable("android:support:fragments", f2);
        }
    }

    /* access modifiers changed from: protected */
    public final void onStart() {
        super.onStart();
        this.f = false;
        this.g = false;
        this.a.removeMessages(1);
        if (!this.d) {
            this.d = true;
            this.b.i();
        }
        this.b.g();
        this.b.d();
        if (!this.k) {
            this.k = true;
            if (this.m != null) {
                this.m.b();
            } else if (!this.j) {
                boolean z = this.k;
                this.m = b((String) null);
                if (this.m != null && !this.m.f) {
                    this.m.b();
                }
            }
            this.j = true;
        }
        this.b.j();
        if (this.l != null) {
            v[] vVarArr = new v[this.l.size()];
            this.l.values().toArray(vVarArr);
            for (v vVar : vVarArr) {
                vVar.e();
                vVar.g();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void onStop() {
        super.onStop();
        this.f = true;
        this.a.sendEmptyMessage(1);
        this.b.m();
    }

    public final void startActivityForResult(Intent intent, int i2) {
        if (i2 == -1 || (-65536 & i2) == 0) {
            super.startActivityForResult(intent, i2);
            return;
        }
        throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
    }
}
