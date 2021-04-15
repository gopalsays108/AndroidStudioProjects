package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.a.a;
import android.support.v4.c.c;
import android.util.Log;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

final class v extends t {
    static boolean a = false;
    final c b;
    final c c;
    final String d;
    g e;
    boolean f;
    boolean g;

    /* access modifiers changed from: package-private */
    public final void a(g gVar) {
        this.e = gVar;
    }

    public final void a(String str, PrintWriter printWriter) {
        if (this.b.a() > 0) {
            printWriter.print(str);
            printWriter.println("Active Loaders:");
            String str2 = str + "    ";
            for (int i = 0; i < this.b.a(); i++) {
                w wVar = (w) this.b.b(i);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.b.a(i));
                printWriter.print(": ");
                printWriter.println(wVar.toString());
                wVar.a(str2, printWriter);
            }
        }
        if (this.c.a() > 0) {
            printWriter.print(str);
            printWriter.println("Inactive Loaders:");
            String str3 = str + "    ";
            for (int i2 = 0; i2 < this.c.a(); i2++) {
                w wVar2 = (w) this.c.b(i2);
                printWriter.print(str);
                printWriter.print("  #");
                printWriter.print(this.c.a(i2));
                printWriter.print(": ");
                printWriter.println(wVar2.toString());
                wVar2.a(str3, printWriter);
            }
        }
    }

    public final boolean a() {
        int a2 = this.b.a();
        boolean z = false;
        for (int i = 0; i < a2; i++) {
            w wVar = (w) this.b.b(i);
            z |= wVar.h && !wVar.f;
        }
        return z;
    }

    /* access modifiers changed from: package-private */
    public final void b() {
        if (a) {
            "Starting in " + this;
        }
        if (this.f) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("LoaderManager", "Called doStart when already started: " + this, runtimeException);
            return;
        }
        this.f = true;
        for (int a2 = this.b.a() - 1; a2 >= 0; a2--) {
            w wVar = (w) this.b.b(a2);
            if (wVar.i && wVar.j) {
                wVar.h = true;
            } else if (!wVar.h) {
                wVar.h = true;
                if (a) {
                    "  Starting: " + wVar;
                }
                if (wVar.d == null && wVar.c != null) {
                    u uVar = wVar.c;
                    int i = wVar.a;
                    Bundle bundle = wVar.b;
                    wVar.d = uVar.a();
                }
                if (wVar.d == null) {
                    continue;
                } else if (!wVar.d.getClass().isMemberClass() || Modifier.isStatic(wVar.d.getClass().getModifiers())) {
                    if (!wVar.m) {
                        a aVar = wVar.d;
                        int i2 = wVar.a;
                        if (aVar.b != null) {
                            throw new IllegalStateException("There is already a listener registered");
                        }
                        aVar.b = wVar;
                        aVar.a = i2;
                        wVar.m = true;
                    }
                    a aVar2 = wVar.d;
                    aVar2.c = true;
                    aVar2.e = false;
                    aVar2.d = false;
                } else {
                    throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + wVar.d);
                }
            } else {
                continue;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void c() {
        if (a) {
            "Stopping in " + this;
        }
        if (!this.f) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("LoaderManager", "Called doStop when not started: " + this, runtimeException);
            return;
        }
        for (int a2 = this.b.a() - 1; a2 >= 0; a2--) {
            ((w) this.b.b(a2)).a();
        }
        this.f = false;
    }

    /* access modifiers changed from: package-private */
    public final void d() {
        if (a) {
            "Retaining in " + this;
        }
        if (!this.f) {
            RuntimeException runtimeException = new RuntimeException("here");
            runtimeException.fillInStackTrace();
            Log.w("LoaderManager", "Called doRetain when not started: " + this, runtimeException);
            return;
        }
        this.g = true;
        this.f = false;
        for (int a2 = this.b.a() - 1; a2 >= 0; a2--) {
            w wVar = (w) this.b.b(a2);
            if (a) {
                "  Retaining: " + wVar;
            }
            wVar.i = true;
            wVar.j = wVar.h;
            wVar.h = false;
            wVar.c = null;
        }
    }

    /* access modifiers changed from: package-private */
    public final void e() {
        if (this.g) {
            if (a) {
                "Finished Retaining in " + this;
            }
            this.g = false;
            for (int a2 = this.b.a() - 1; a2 >= 0; a2--) {
                w wVar = (w) this.b.b(a2);
                if (wVar.i) {
                    if (a) {
                        "  Finished Retaining: " + wVar;
                    }
                    wVar.i = false;
                    if (wVar.h != wVar.j && !wVar.h) {
                        wVar.a();
                    }
                }
                if (wVar.h && wVar.e && !wVar.k) {
                    wVar.a(wVar.d, wVar.g);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void f() {
        for (int a2 = this.b.a() - 1; a2 >= 0; a2--) {
            ((w) this.b.b(a2)).k = true;
        }
    }

    /* access modifiers changed from: package-private */
    public final void g() {
        for (int a2 = this.b.a() - 1; a2 >= 0; a2--) {
            w wVar = (w) this.b.b(a2);
            if (wVar.h && wVar.k) {
                wVar.k = false;
                if (wVar.e) {
                    wVar.a(wVar.d, wVar.g);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void h() {
        if (!this.g) {
            if (a) {
                "Destroying Active in " + this;
            }
            for (int a2 = this.b.a() - 1; a2 >= 0; a2--) {
                ((w) this.b.b(a2)).b();
            }
        }
        if (a) {
            "Destroying Inactive in " + this;
        }
        for (int a3 = this.c.a() - 1; a3 >= 0; a3--) {
            ((w) this.c.b(a3)).b();
        }
        this.c.b();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        android.support.v4.c.a.a(this.e, sb);
        sb.append("}}");
        return sb.toString();
    }
}
