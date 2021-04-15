package com.smarterdroid.wftlib.b;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;

public class a extends FilterOutputStream {
    protected byte[] a;
    protected Deflater b;
    protected boolean c;
    private final boolean d;

    public a(OutputStream outputStream, Deflater deflater) {
        this(outputStream, deflater, (byte) 0);
    }

    private a(OutputStream outputStream, Deflater deflater, byte b2) {
        super(outputStream);
        this.c = false;
        if (outputStream == null || deflater == null) {
            throw new NullPointerException();
        }
        this.b = deflater;
        this.d = false;
        this.a = new byte[4096];
    }

    private void b() {
        do {
            this.out.write(this.a, 0, this.b.deflate(this.a));
        } while (!this.b.needsInput());
    }

    public void a() {
        if (!this.c) {
            this.b.finish();
            while (!this.b.finished()) {
                if (this.b.needsInput()) {
                    this.b.setInput(this.a, 0, 0);
                }
                this.out.write(this.a, 0, this.b.deflate(this.a));
            }
            this.c = true;
        }
    }

    public void close() {
        if (!this.b.finished()) {
            a();
        }
        this.b.end();
        this.out.close();
    }

    public void flush() {
        if (this.d) {
            this.out.write(this.a, 0, this.b.deflate(this.a, 0, this.a.length));
        }
        this.out.flush();
    }

    public void write(int i) {
        write(new byte[]{(byte) i}, 0, 1);
    }

    public void write(byte[] bArr, int i, int i2) {
        if (this.c) {
            throw new IOException("attempt to write after finish");
        } else if (i > bArr.length || i2 < 0 || i < 0 || bArr.length - i < i2) {
            throw new ArrayIndexOutOfBoundsException();
        } else if (!this.b.needsInput()) {
            throw new IOException();
        } else {
            this.b.setInput(bArr, i, i2);
            b();
        }
    }
}
