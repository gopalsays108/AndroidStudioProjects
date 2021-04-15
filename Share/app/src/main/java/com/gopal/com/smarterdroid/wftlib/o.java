package com.smarterdroid.wftlib;

import java.io.OutputStream;

public final class o extends OutputStream {
    private OutputStream a;

    public o(OutputStream outputStream) {
        this.a = outputStream;
    }

    public final void close() {
        byte[] bytes = (String.valueOf(Integer.toHexString(0)) + "\r\n\r\n").getBytes("US-ASCII");
        this.a.write(bytes, 0, bytes.length);
        this.a.flush();
        this.a.close();
        this.a = null;
    }

    public final void flush() {
        this.a.flush();
    }

    public final void write(int i) {
        write(new byte[]{(byte) i}, 0, 1);
    }

    public final void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    public final void write(byte[] bArr, int i, int i2) {
        if (i2 > 0) {
            String str = String.valueOf(Integer.toHexString(i2)) + "\r\n";
            this.a.write(str.getBytes("US-ASCII"), 0, str.getBytes("US-ASCII").length);
            this.a.write(bArr, i, i2);
            this.a.write("\r\n".getBytes("US-ASCII"), 0, "\r\n".getBytes("US-ASCII").length);
        }
    }
}
