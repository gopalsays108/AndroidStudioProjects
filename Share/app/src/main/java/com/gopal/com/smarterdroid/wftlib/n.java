package com.smarterdroid.wftlib;

public final class n {
    private byte[] a = new byte[this.b];
    private int b;

    public n(int i) {
        this.b = i;
        a();
    }

    private void a() {
        for (int i = 0; i < this.a.length; i++) {
            this.a[i] = 0;
        }
    }

    public final void a(byte[] bArr, int i) {
        int i2 = this.b - 1;
        int i3 = (this.b + i) - 1;
        if (i >= this.b) {
            for (int i4 = 0; i4 < i && i2 >= 0; i4++) {
                this.a[i2] = bArr[i3];
                i2--;
                i3--;
            }
            return;
        }
        byte[] bArr2 = new byte[this.b];
        int i5 = i2;
        int i6 = i3;
        int i7 = 0;
        while (i7 < i) {
            bArr2[i5] = bArr[i6];
            i6--;
            i7++;
            i5--;
        }
        int i8 = this.b - 1;
        while (i5 >= 0) {
            bArr2[i5] = this.a[i8];
            i8--;
            i5--;
        }
        this.a = bArr2;
    }

    public final byte[] a(byte[] bArr) {
        for (int i = 0; i < this.b; i++) {
            bArr[i] = this.a[i];
        }
        return bArr;
    }
}
