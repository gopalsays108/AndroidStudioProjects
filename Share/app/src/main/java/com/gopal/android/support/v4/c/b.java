package android.support.v4.c;

import java.io.Writer;

public final class b extends Writer {
    private final String a;
    private StringBuilder b = new StringBuilder(128);

    public b(String str) {
        this.a = str;
    }

    private void a() {
        if (this.b.length() > 0) {
            String str = this.a;
            this.b.toString();
            this.b.delete(0, this.b.length());
        }
    }

    public final void close() {
        a();
    }

    public final void flush() {
        a();
    }

    public final void write(char[] cArr, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            char c = cArr[i + i3];
            if (c == 10) {
                a();
            } else {
                this.b.append(c);
            }
        }
    }
}
