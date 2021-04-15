package com.smarterdroid.wftlib;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.net.SocketTimeoutException;
import java.util.Stack;
import org.apache.http.util.EncodingUtils;

public final class ah extends DataInputStream {
    byte[] a = new byte[8192];
    Stack b = new Stack();

    public ah(InputStream inputStream) {
        super(inputStream);
    }

    private String b() {
        StringBuilder sb = new StringBuilder(80);
        boolean z = false;
        while (true) {
            try {
                int read = this.in.read();
                switch (read) {
                    case -1:
                        if (sb.length() != 0 || z) {
                            return sb.toString();
                        }
                        return null;
                    case 10:
                        return sb.toString();
                    case 13:
                        if (!z) {
                            z = true;
                            if (this.in.getClass() == PushbackInputStream.class) {
                                break;
                            } else {
                                this.in = new PushbackInputStream(this.in);
                                break;
                            }
                        } else {
                            ((PushbackInputStream) this.in).unread(read);
                            return sb.toString();
                        }
                    default:
                        if (!z) {
                            sb.append((char) read);
                            break;
                        } else {
                            ((PushbackInputStream) this.in).unread(read);
                            return sb.toString();
                        }
                }
            } catch (Exception e) {
                return sb.toString();
            }
        }
    }

    public final String a() {
        boolean z;
        StringBuilder sb = new StringBuilder(this.b.size());
        while (true) {
            if (this.b.size() <= 0) {
                z = false;
                break;
            }
            byte byteValue = ((Byte) this.b.pop()).byteValue();
            if (byteValue == 13) {
                if (this.b.size() > 0 && ((Byte) this.b.peek()).byteValue() == 10) {
                    ((Byte) this.b.pop()).byteValue();
                    z = true;
                    break;
                }
                sb.append(new String(new byte[]{byteValue}));
            } else {
                sb.append(new String(new byte[]{byteValue}));
            }
        }
        if (!z && super.available() > 0) {
            try {
                sb.append(b());
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }
        }
        return new String(EncodingUtils.getBytes(sb.toString(), "iso-8859-1"));
    }

    public final void a(byte[] bArr, int i, int i2) {
        for (int i3 = (i + i2) - 1; i3 >= i; i3--) {
            this.b.push(Byte.valueOf(bArr[i3]));
        }
    }

    public final int available() {
        return this.b.size() > 0 ? this.b.size() : super.available();
    }

    public final void b(byte[] bArr, int i, int i2) {
        if (this.b.size() > 0) {
            for (int i3 = 0; i3 < i2; i3++) {
                bArr[i + i3] = ((Byte) this.b.pop()).byteValue();
            }
            return;
        }
        super.readFully(bArr, i, i2);
    }
}
