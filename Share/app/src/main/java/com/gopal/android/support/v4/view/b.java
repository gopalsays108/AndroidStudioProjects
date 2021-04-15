package android.support.v4.view;

final class b implements d {
    b() {
    }

    private static int a(int i, int i2, int i3, int i4) {
        boolean z = true;
        boolean z2 = (i2 & 1) != 0;
        int i5 = i3 | i4;
        if ((i5 & 1) == 0) {
            z = false;
        }
        if (!z2) {
            return z ? i & (i2 ^ -1) : i;
        }
        if (!z) {
            return i & (i5 ^ -1);
        }
        throw new IllegalArgumentException("bad arguments");
    }

    private static int c(int i) {
        int i2 = (i & 192) != 0 ? i | 1 : i;
        if ((i2 & 48) != 0) {
            i2 |= 2;
        }
        return i2 & 247;
    }

    public final boolean a(int i) {
        return a(a(c(i) & 247, 1, 64, 128), 2, 16, 32) == 1;
    }

    public final boolean b(int i) {
        return (c(i) & 247) == 0;
    }
}
