package com.smarterdroid.wftlib.b;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Vector;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;

public final class b extends a {
    private String d;
    private final Vector e = new Vector();
    private int f = 8;
    private int g = -1;
    private ByteArrayOutputStream h = new ByteArrayOutputStream();
    private ZipEntry i;
    private final CRC32 j = new CRC32();
    private int k = 0;
    private int l = 0;
    private int m;
    private byte[] n;

    public b(OutputStream outputStream) {
        super(outputStream, new Deflater(-1, true));
    }

    private static int a(OutputStream outputStream, int i2) {
        outputStream.write(i2 & 255);
        outputStream.write((i2 >> 8) & 255);
        return i2;
    }

    private static int a(String str) {
        int i2 = 0;
        int length = str.length();
        while (true) {
            length--;
            if (length < 0) {
                return i2;
            }
            char charAt = str.charAt(length);
            i2 = charAt < 128 ? i2 + 1 : charAt < 2048 ? i2 + 2 : i2 + 3;
        }
    }

    private static long a(OutputStream outputStream, long j2) {
        outputStream.write((int) (255 & j2));
        outputStream.write(((int) (j2 >> 8)) & 255);
        outputStream.write(((int) (j2 >> 16)) & 255);
        outputStream.write(((int) (j2 >> 24)) & 255);
        return j2;
    }

    private static byte[] a(String str, int i2) {
        byte[] bArr = new byte[i2];
        int length = bArr.length;
        int length2 = str.length();
        while (true) {
            length2--;
            if (length2 < 0) {
                return bArr;
            }
            char charAt = str.charAt(length2);
            if (charAt < 128) {
                length--;
                bArr[length] = (byte) charAt;
            } else if (charAt < 2048) {
                int i3 = length - 1;
                bArr[i3] = (byte) ((charAt & '?') | 128);
                length = i3 - 1;
                bArr[length] = (byte) ((charAt >> 6) | 192);
            } else {
                int i4 = length - 1;
                bArr[i4] = (byte) ((charAt & '?') | 128);
                int i5 = i4 - 1;
                bArr[i5] = (byte) (((charAt >> 6) & 63) | 128);
                length = i5 - 1;
                bArr[length] = (byte) ((charAt >> 12) | 224);
            }
        }
    }

    private void c() {
        if (this.h == null) {
            throw new IOException("Stream is closed");
        }
    }

    public final void a() {
        if (this.out == null) {
            throw new IOException("Stream is closed");
        } else if (this.h != null) {
            if (this.e.size() == 0) {
                throw new ZipException("No entries");
            }
            if (this.i != null) {
                try {
                    b();
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                } catch (SecurityException e3) {
                    e3.printStackTrace();
                } catch (IllegalAccessException e4) {
                    e4.printStackTrace();
                } catch (NoSuchFieldException e5) {
                    e5.printStackTrace();
                }
            }
            int size = this.h.size();
            a((OutputStream) this.h, 101010256);
            a((OutputStream) this.h, 0);
            a((OutputStream) this.h, 0);
            a((OutputStream) this.h, this.e.size());
            a((OutputStream) this.h, this.e.size());
            a((OutputStream) this.h, (long) size);
            a((OutputStream) this.h, (long) this.k);
            if (this.d != null) {
                a((OutputStream) this.h, this.d.length());
                this.h.write(this.d.getBytes());
            } else {
                a((OutputStream) this.h, 0);
            }
            this.out.write(this.h.toByteArray());
            this.h = null;
        }
    }

    public final void a(int i2) {
        if (i2 < -1 || i2 > 9) {
            throw new IllegalArgumentException();
        }
        this.g = i2;
    }

    public final void a(ZipEntry zipEntry) {
        Field declaredField = ZipEntry.class.getDeclaredField("time");
        declaredField.setAccessible(true);
        Field declaredField2 = ZipEntry.class.getDeclaredField("modDate");
        declaredField2.setAccessible(true);
        if (this.i != null) {
            b();
        }
        if (zipEntry.getMethod() == 0 || (this.f == 0 && zipEntry.getMethod() == -1)) {
            if (zipEntry.getCrc() == -1) {
                throw new ZipException("CRC mismatch");
            } else if (zipEntry.getSize() == -1 && zipEntry.getCompressedSize() == -1) {
                throw new ZipException("Size mismatch");
            } else if (!(zipEntry.getSize() == zipEntry.getCompressedSize() || zipEntry.getCompressedSize() == -1 || zipEntry.getSize() == -1)) {
                throw new ZipException("Size mismatch");
            }
        }
        c();
        if (this.e.contains(zipEntry.getName())) {
            throw new ZipException("Entry already exists: " + zipEntry.getName());
        }
        this.m = a(zipEntry.getName());
        if (this.m > 65535) {
            throw new IllegalArgumentException("Name too long: " + this.m + " UTF-8 bytes");
        }
        this.b.setLevel(this.g);
        this.i = zipEntry;
        this.e.add(this.i.getName());
        if (this.i.getMethod() == -1) {
            this.i.setMethod(this.f);
        }
        int i2 = this.i.getMethod() == 0 ? 0 : 8;
        a(this.out, 67324752);
        a(this.out, 20);
        a(this.out, i2 | 2048);
        a(this.out, this.i.getMethod());
        if (this.i.getTime() == -1) {
            this.i.setTime(System.currentTimeMillis());
        }
        a(this.out, declaredField.getInt(this.i));
        a(this.out, declaredField2.getInt(this.i));
        if (this.i.getMethod() == 0) {
            if (this.i.getSize() == -1) {
                this.i.setSize(this.i.getCompressedSize());
            } else if (this.i.getSize() == -1) {
                this.i.setCompressedSize(this.i.getSize());
            }
            a(this.out, this.i.getCrc());
            a(this.out, this.i.getSize());
            a(this.out, this.i.getSize());
        } else {
            a(this.out, 0);
            a(this.out, 0);
            a(this.out, 0);
        }
        a(this.out, this.m);
        if (this.i.getExtra() != null) {
            a(this.out, this.i.getExtra().length);
        } else {
            a(this.out, 0);
        }
        this.n = a(this.i.getName(), this.m);
        this.out.write(this.n);
        if (this.i.getExtra() != null) {
            this.out.write(this.i.getExtra());
        }
    }

    public final void b() {
        c();
        if (this.i != null) {
            if (this.i.getMethod() == 8) {
                super.a();
            }
            Field declaredField = ZipEntry.class.getDeclaredField("time");
            declaredField.setAccessible(true);
            Field declaredField2 = ZipEntry.class.getDeclaredField("modDate");
            declaredField2.setAccessible(true);
            Field declaredField3 = CRC32.class.getDeclaredField("tbytes");
            declaredField3.setAccessible(true);
            if (this.i.getMethod() == 0) {
                if (this.j.getValue() != this.i.getCrc()) {
                    throw new ZipException("CRC mismatch");
                } else if (this.i.getSize() != declaredField3.getLong(this.j)) {
                    throw new ZipException("Size mismatch");
                }
            }
            this.l = 30;
            if (this.i.getMethod() != 0) {
                this.l += 16;
                a(this.out, 134695760);
                this.i.setCrc(this.j.getValue());
                a(this.out, this.i.getCrc());
                this.i.setCompressedSize((long) this.b.getTotalOut());
                a(this.out, this.i.getSize());
                this.i.setSize((long) this.b.getTotalIn());
                a(this.out, this.i.getSize());
            }
            int i2 = this.i.getMethod() == 0 ? 0 : 8;
            a((OutputStream) this.h, 33639248);
            a((OutputStream) this.h, 20);
            a((OutputStream) this.h, 20);
            a((OutputStream) this.h, i2 | 2048);
            a((OutputStream) this.h, this.i.getMethod());
            a((OutputStream) this.h, declaredField.getInt(this.i));
            a((OutputStream) this.h, declaredField2.getInt(this.i));
            a((OutputStream) this.h, this.j.getValue());
            if (this.i.getMethod() == 8) {
                this.l = (int) (((long) this.l) + a((OutputStream) this.h, (long) this.b.getTotalOut()));
                a((OutputStream) this.h, (long) this.b.getTotalIn());
            } else {
                this.l = (int) (((long) this.l) + a((OutputStream) this.h, declaredField3.getLong(this.j)));
                a((OutputStream) this.h, declaredField3.getLong(this.j));
            }
            this.l += a((OutputStream) this.h, this.m);
            if (this.i.getExtra() != null) {
                this.l += a((OutputStream) this.h, this.i.getExtra().length);
            } else {
                a((OutputStream) this.h, 0);
            }
            String comment = this.i.getComment();
            if (comment != null) {
                a((OutputStream) this.h, comment.length());
            } else {
                a((OutputStream) this.h, 0);
            }
            a((OutputStream) this.h, 0);
            a((OutputStream) this.h, 0);
            a((OutputStream) this.h, 0);
            a((OutputStream) this.h, (long) this.k);
            this.h.write(this.n);
            this.n = null;
            if (this.i.getExtra() != null) {
                this.h.write(this.i.getExtra());
            }
            this.k += this.l;
            if (comment != null) {
                this.h.write(comment.getBytes());
            }
            this.i = null;
            this.j.reset();
            this.b.reset();
            this.c = false;
        }
    }

    public final void close() {
        if (this.out != null) {
            a();
            this.out.close();
            this.out = null;
        }
    }

    public final void write(byte[] bArr, int i2, int i3) {
        if (i2 < 0 || i3 < 0 || i2 > bArr.length || bArr.length - i2 < i3) {
            throw new IndexOutOfBoundsException();
        } else if (this.i == null) {
            throw new ZipException("No active entry");
        } else {
            if (this.i.getMethod() == 0) {
                this.out.write(bArr, i2, i3);
            } else {
                super.write(bArr, i2, i3);
            }
            this.j.update(bArr, i2, i3);
        }
    }
}
