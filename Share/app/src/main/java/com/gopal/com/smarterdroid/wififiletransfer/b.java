package com.smarterdroid.wififiletransfer;

import android.content.Context;
import com.smarterdroid.wftlib.ag;
import com.smarterdroid.wftlib.ah;
import com.smarterdroid.wftlib.av;
import java.io.File;
import java.util.Properties;
import java.util.Stack;

public final class b {
    private static boolean o = true;
    final int a = 8192;
    private Properties b;
    private Properties c;
    private ah d;
    private av e = new av();
    private ag f;
    private Context g;
    private String h;
    private String[] i;
    private File j;
    private byte[] k;
    private StringBuffer l;
    private byte[] m;
    private boolean n = false;

    public b(Properties properties, Properties properties2, ah ahVar, ag agVar, Context context) {
        this.b = properties;
        this.c = properties2;
        this.d = ahVar;
        this.f = agVar;
        this.g = context;
        this.n = this.c.getProperty("sslmode", "false").equals("true");
    }

    private static int a(byte[] bArr, int i2, byte[] bArr2) {
        int i3 = 0;
        int[] iArr = new int[bArr2.length];
        int i4 = 1;
        int i5 = 0;
        while (i4 < bArr2.length) {
            while (i5 > 0 && bArr2[i5] != bArr2[i4]) {
                i5 = iArr[i5 - 1];
            }
            if (bArr2[i5] == bArr2[i4]) {
                i5++;
            }
            iArr[i4] = i5;
            i4++;
        }
        int length = i2 + 1 < bArr.length ? i2 + 1 : bArr.length;
        if (length == 0) {
            return -1;
        }
        int i6 = 0;
        while (i3 < length) {
            while (i6 > 0 && bArr2[i6] != bArr[i3]) {
                i6 = iArr[i6 - 1];
            }
            if (bArr2[i6] == bArr[i3]) {
                i6++;
            }
            if (i6 == bArr2.length) {
                return (i3 - bArr2.length) + 1;
            }
            i3++;
        }
        return -1;
    }

    private static void a(Stack stack) {
        while (!stack.isEmpty()) {
            File file = (File) stack.pop();
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:177:0x06ab, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x06ac, code lost:
        if (r38 != null) goto L_0x06ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:179:0x06ae, code lost:
        a(r38);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x06b1, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x00c3, code lost:
        if (r38 != null) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x00c5, code lost:
        a(r38);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        r37.e.e = 13;
        r37.e.a = 400;
        r37.e.b = "<html><head></head><body><h1>400 Bad Request</h1></body></html>";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00fb, code lost:
        if (r38 != null) goto L_0x00fd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00fd, code lost:
        a(r38);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00c2 A[ExcHandler: IOException (e java.io.IOException), Splitter:B:36:0x0101] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00e2 A[ExcHandler: NoSuchElementException (e java.util.NoSuchElementException), Splitter:B:1:0x0002] */
    @android.annotation.TargetApi(8)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.smarterdroid.wftlib.av a(java.util.Stack r38, java.util.Properties r39) {
        /*
            r37 = this;
            java.lang.String r7 = ""
            com.smarterdroid.wftlib.ab.a()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "POST: "
            r3.<init>(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r4 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "uri"
            java.lang.String r4 = r4.getProperty(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = " Params: "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r3.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r15 = 0
            r0 = r37
            java.util.Properties r3 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "content-type"
            java.lang.String r3 = r3.getProperty(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r3 == 0) goto L_0x0079
            r0 = r37
            java.util.Properties r3 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "content-type"
            java.lang.String r3 = r3.getProperty(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "boundary="
            int r3 = r3.indexOf(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r3 < 0) goto L_0x0079
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "--"
            r3.<init>(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r4 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "content-type"
            java.lang.String r4 = r4.getProperty(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r5 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "content-type"
            java.lang.String r5 = r5.getProperty(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "boundary="
            int r5 = r5.indexOf(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r5 = r5 + 9
            java.lang.String r4 = r4.substring(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r15 = r3.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x0079:
            r0 = r37
            android.content.Context r3 = r0.g     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            android.content.res.Resources r3 = r3.getResources()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r4 = 2131099648(0x7f060000, float:1.7811655E38)
            java.lang.String[] r3 = r3.getStringArray(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            r0.i = r3     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.util.LinkedList r17 = new java.util.LinkedList     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r17.<init>()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.io.File r3 = new java.io.File     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            android.content.Context r4 = r0.g     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = r4.getPackageCodePath()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            r0.j = r3     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "/"
            r3 = 0
        L_0x00a4:
            r4 = 10
            if (r3 >= r4) goto L_0x00b0
            java.lang.String r4 = ""
            boolean r4 = r7.equals(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r4 != 0) goto L_0x00cd
        L_0x00b0:
            r4 = 10
            if (r3 < r4) goto L_0x0101
            java.lang.String r3 = ""
            boolean r3 = r7.equals(r3)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r3 == 0) goto L_0x0101
            java.net.SocketTimeoutException r3 = new java.net.SocketTimeoutException     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r3.<init>()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            throw r3     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x00c2:
            r3 = move-exception
            if (r38 == 0) goto L_0x00c8
            a(r38)
        L_0x00c8:
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e
            return r3
        L_0x00cd:
            int r3 = r3 + 1
            r0 = r37
            com.smarterdroid.wftlib.ah r4 = r0.d     // Catch:{ SocketTimeoutException -> 0x00d8 }
            java.lang.String r7 = r4.readLine()     // Catch:{ SocketTimeoutException -> 0x00d8 }
            goto L_0x00a4
        L_0x00d8:
            r4 = move-exception
            java.lang.String r7 = ""
            int r4 = r3 * 50
            long r8 = (long) r4     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.Thread.sleep(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            goto L_0x00a4
        L_0x00e2:
            r3 = move-exception
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e     // Catch:{ all -> 0x06ab }
            r4 = 13
            r3.e = r4     // Catch:{ all -> 0x06ab }
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e     // Catch:{ all -> 0x06ab }
            r4 = 400(0x190, float:5.6E-43)
            r3.a = r4     // Catch:{ all -> 0x06ab }
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e     // Catch:{ all -> 0x06ab }
            java.lang.String r4 = "<html><head></head><body><h1>400 Bad Request</h1></body></html>"
            r3.b = r4     // Catch:{ all -> 0x06ab }
            if (r38 == 0) goto L_0x00c8
            a(r38)
            goto L_0x00c8
        L_0x0101:
            boolean r6 = r7.equals(r15)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = ""
            java.lang.StringBuilder r18 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r18.<init>()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r3 = ""
            r0 = r37
            java.util.Properties r8 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = "uri"
            java.lang.Object r8 = r8.get(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 == 0) goto L_0x0150
            java.io.File r8 = new java.io.File     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "/"
            r0 = r37
            java.util.Properties r9 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r10 = "uri"
            java.lang.String r9 = r9.getProperty(r10)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.<init>(r5, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = r8.getAbsolutePath()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            boolean r8 = r8.isDirectory()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 == 0) goto L_0x0150
            java.lang.String r8 = "/"
            boolean r8 = r5.endsWith(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 != 0) goto L_0x0217
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.<init>(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "/"
            java.lang.StringBuilder r5 = r8.append(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x0150:
            if (r6 != 0) goto L_0x021b
            r0 = r37
            java.util.Properties r3 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "uri"
            java.lang.String r3 = r3.getProperty(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "/login.wft"
            boolean r3 = r3.equals(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r3 == 0) goto L_0x07dd
            java.lang.String r3 = "/"
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "redirurl"
            boolean r4 = r4.containsKey(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r4 == 0) goto L_0x018a
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "redirurl"
            java.lang.String r4 = r4.getProperty(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r4 == 0) goto L_0x018a
            r0 = r37
            java.util.Properties r3 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "redirurl"
            java.lang.String r5 = "/"
            java.lang.String r3 = r3.getProperty(r4, r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x018a:
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "passwd"
            boolean r4 = r4.containsKey(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r4 == 0) goto L_0x0783
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "passwd"
            java.lang.String r4 = r4.getProperty(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r4 == 0) goto L_0x0783
            r0 = r37
            com.smarterdroid.wftlib.ag r4 = r0.f     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "passwd"
            java.lang.String r4 = r4.getProperty(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            boolean r4 = com.smarterdroid.wftlib.ag.a((java.lang.String) r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r4 == 0) goto L_0x0783
            r0 = r37
            com.smarterdroid.wftlib.av r4 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r5 = 12
            r4.e = r5     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            com.smarterdroid.wftlib.h r4 = new com.smarterdroid.wftlib.h     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r5 = 1
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r7 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = "host"
            java.lang.String r7 = r7.getProperty(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r3 = r6.append(r3)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r6 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r4.<init>(r5, r3, r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = r4.a()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r3.b = r4     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "Set-Cookie: WFTSession="
            r4.<init>(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.ag r5 = r0.f     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = r5.c()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = " \r\n"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r3.d = r4     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x0210:
            if (r38 == 0) goto L_0x00c8
            a(r38)
            goto L_0x00c8
        L_0x0217:
            java.lang.String r5 = "/"
            goto L_0x0150
        L_0x021b:
            r8 = 0
            java.lang.String r6 = "uploaded_file"
            r16 = r6
            r11 = r7
        L_0x0221:
            java.lang.String r6 = r11.trim()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r6 = r6.length()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r6 > 0) goto L_0x0383
            if (r8 == 0) goto L_0x06b7
            java.lang.String r6 = "DeleteMe"
            r7 = 0
            java.io.File r8 = new java.io.File     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.<init>(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.io.File r19 = java.io.File.createTempFile(r6, r7, r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "/wftres.wft?f=ficon.ico"
            r8 = 21
            r9 = 22
            java.lang.String r7 = r7.substring(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "/wftres.wft?f=mootools.js"
            r8 = 20
            r9 = 21
            java.lang.String r7 = r7.substring(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "/wftres.wft?f=action_icon_reload.png"
            r8 = 30
            r9 = 31
            java.lang.String r7 = r7.substring(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "/wftres.wft?f=success.png"
            r8 = 19
            r9 = 21
            java.lang.String r7 = r7.substring(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "/wftres.wft?f=success.png"
            r8 = 18
            r9 = 19
            java.lang.String r7 = r7.substring(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "/wftres.wft?f=success.png"
            r8 = 19
            r9 = 20
            java.lang.String r7 = r7.substring(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "/wftres.wft?f=how-bg.png"
            r8 = 20
            r9 = 21
            java.lang.String r7 = r7.substring(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "/wftres.wft?f=uploader.js"
            r8 = 19
            r9 = 21
            java.lang.String r7 = r7.substring(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "/wftres.wft?f=1px.png"
            r8 = 16
            r9 = 17
            java.lang.String r7 = r7.substring(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r20 = r6.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.io.File r7 = r0.j     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.util.zip.ZipInputStream r21 = new java.util.zip.ZipInputStream     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r21
            r0.<init>(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r19.deleteOnExit()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r38
            r1 = r19
            r0.add(r1)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = r19.getAbsolutePath()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = r19.getName()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r39
            r0.put(r4, r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.io.BufferedOutputStream r22 = new java.io.BufferedOutputStream     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.io.FileOutputStream r6 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r19
            r6.<init>(r0)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r7 = 32768(0x8000, float:4.5918E-41)
            r0 = r22
            r0.<init>(r6, r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r10 = 0
            r9 = 0
            r8 = 0
            byte[] r23 = r15.getBytes()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r23
            int r6 = r0.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r6 = r6 + 8192
            byte[] r7 = new byte[r6]     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6 = 256(0x100, float:3.59E-43)
            byte[] r6 = new byte[r6]     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            r0.m = r6     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6 = 4
            byte[] r6 = new byte[r6]     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            r0.k = r6     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6 = 0
            com.smarterdroid.wftlib.n r24 = new com.smarterdroid.wftlib.n     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r23
            int r12 = r0.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r24
            r0.<init>(r12)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r12 = r7
            r13 = r8
            r14 = r9
            r7 = r11
            r11 = r6
            r6 = r10
        L_0x0337:
            if (r14 != 0) goto L_0x033f
            if (r13 != 0) goto L_0x033f
            r8 = 150(0x96, float:2.1E-43)
            if (r11 < r8) goto L_0x0447
        L_0x033f:
            r22.flush()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r22.close()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r13 == 0) goto L_0x06b2
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = java.lang.String.valueOf(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.<init>(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r16
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r17
            r0.add(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = java.lang.String.valueOf(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.<init>(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r16
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = r19.getAbsolutePath()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r39
            r0.put(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x037b:
            r21.close()     // Catch:{ Exception -> 0x0380, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            goto L_0x0150
        L_0x0380:
            r8 = move-exception
            goto L_0x0150
        L_0x0383:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "READ: "
            r6.<init>(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r11)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.ah r6 = r0.d     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = r6.a()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = r7.trim()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r6 = r6.length()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r6 <= 0) goto L_0x094e
            r6 = r7
        L_0x03a4:
            java.lang.String r3 = "filename="
            int r3 = r7.indexOf(r3)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r3 < 0) goto L_0x094a
            java.lang.String r3 = "filename="
            int r3 = r7.indexOf(r3)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r3 = r3 + 9
            java.lang.String r3 = r7.substring(r3)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r3 == 0) goto L_0x040c
            int r8 = r3.length()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r9 = 1
            if (r8 <= r9) goto L_0x040c
            r8 = 1
            int r9 = r3.length()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r9 = r9 + -1
            java.lang.String r3 = r3.substring(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r8 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = "relativePath"
            boolean r8 = r8.containsKey(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 == 0) goto L_0x040c
            r0 = r37
            java.util.Properties r8 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = "relativePath"
            java.lang.String r10 = ""
            java.lang.String r8 = r8.getProperty(r9, r10)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = ""
            boolean r8 = r8.equals(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 != 0) goto L_0x040c
            r0 = r37
            java.util.Properties r8 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = "relativePath"
            java.lang.String r10 = ""
            java.lang.String r8 = r8.getProperty(r9, r10)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r8 = r8.length()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r9 = r3.length()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 <= r9) goto L_0x040c
            r0 = r37
            java.util.Properties r3 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = "relativePath"
            java.lang.String r3 = r3.getProperty(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x040c:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = "filename= "
            r8.<init>(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r8 = r8.append(r3)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = ""
            boolean r8 = r3.equals(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 == 0) goto L_0x0429
            r8 = 0
            r16 = r3
            r11 = r7
            r3 = r6
            goto L_0x0221
        L_0x0429:
            r8 = 1
            java.lang.String r4 = "name=\""
            int r4 = r7.indexOf(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r4 = r4 + 6
            java.lang.String r4 = r7.substring(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r9 = 0
            java.lang.String r10 = "\""
            int r10 = r4.indexOf(r10)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = r4.substring(r9, r10)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r16 = r3
            r11 = r7
            r3 = r6
            goto L_0x0221
        L_0x0447:
            com.smarterdroid.wftlib.ab.a()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.ah r8 = r0.d     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r9 = r8.available()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8 = 0
            r10 = 8192(0x2000, float:1.14794E-41)
            if (r9 <= r10) goto L_0x0557
            r9 = 8192(0x2000, float:1.14794E-41)
            r10 = r9
        L_0x045a:
            if (r10 <= 0) goto L_0x0699
            r11 = 0
            if (r8 != 0) goto L_0x0469
            r0 = r37
            com.smarterdroid.wftlib.ah r8 = r0.d     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r23
            int r9 = r0.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.b(r12, r9, r10)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x0469:
            r0 = r24
            byte[] r12 = r0.a(r12)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r24
            r0.a(r12, r10)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r23
            int r8 = r0.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r8 = r8 + r10
            int r8 = r8 + -1
            r0 = r23
            int r25 = a(r12, r8, r0)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8 = 40957(0x9ffd, float:5.7393E-41)
            double r26 = java.lang.Math.random()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r28 = 4675862313117417472(0x40e4000000000000, double:40960.0)
            double r26 = r26 * r28
            r0 = r26
            int r9 = (int) r0
            if (r8 >= r9) goto L_0x0576
            r8 = 1
        L_0x0491:
            if (r8 == 0) goto L_0x05d2
            r8 = 4
        L_0x0495:
            java.util.zip.ZipEntry r26 = r21.getNextEntry()     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            if (r26 == 0) goto L_0x057a
            java.lang.String r26 = r26.getName()     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r0 = r26
            r1 = r20
            boolean r26 = r0.equals(r1)     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            if (r26 == 0) goto L_0x0495
            java.io.BufferedInputStream r26 = new java.io.BufferedInputStream     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r27 = 256(0x100, float:3.59E-43)
            r0 = r26
            r1 = r21
            r2 = r27
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r0 = r37
            byte[] r0 = r0.k     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r27 = r0
            r28 = 0
            r29 = 32
            r27[r28] = r29     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r0 = r37
            byte[] r0 = r0.k     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r27 = r0
            r28 = 1
            r29 = 117(0x75, float:1.64E-43)
            r27[r28] = r29     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r0 = r37
            byte[] r0 = r0.k     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r27 = r0
            r28 = 2
            r29 = -58
            r27[r28] = r29     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r0 = r37
            byte[] r0 = r0.k     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r27 = r0
            r28 = 3
            r29 = 13
            r27[r28] = r29     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
        L_0x04e6:
            int r27 = r26.read()     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            if (r27 < 0) goto L_0x0495
            r0 = r37
            byte[] r0 = r0.k     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r28 = r0
            r29 = 4
            long r29 = r8 % r29
            r0 = r29
            int r0 = (int) r0     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r29 = r0
            byte r30 = r28[r29]     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r0 = r30
            long r0 = (long) r0     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r30 = r0
            r0 = r37
            byte[] r0 = r0.k     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r32 = r0
            r33 = 0
            byte r32 = r32[r33]     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            int r32 = r32 * 3
            r0 = r37
            byte[] r0 = r0.k     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r33 = r0
            r34 = 1
            byte r33 = r33[r34]     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            int r33 = r33 * 11
            r0 = r37
            byte[] r0 = r0.k     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r34 = r0
            r35 = 3
            byte r34 = r34[r35]     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r0 = r37
            byte[] r0 = r0.k     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r35 = r0
            r36 = 2
            byte r35 = r35[r36]     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            int r34 = r34 * r35
            int r33 = r33 + r34
            r32 = r32 ^ r33
            r0 = r32
            long r0 = (long) r0     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r32 = r0
            int r27 = r27 + 7
            r0 = r27
            long r0 = (long) r0     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r34 = r0
            long r34 = r34 * r8
            long r32 = r32 ^ r34
            long r30 = r30 + r32
            r0 = r30
            int r0 = (int) r0     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r27 = r0
            r0 = r27
            byte r0 = (byte) r0     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r27 = r0
            r28[r29] = r27     // Catch:{ Exception -> 0x0579, IOException -> 0x00c2, NoSuchElementException -> 0x00e2 }
            r27 = 1
            long r8 = r8 + r27
            goto L_0x04e6
        L_0x0557:
            r0 = r37
            boolean r10 = r0.n     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r10 == 0) goto L_0x0947
            if (r9 != 0) goto L_0x0947
            r0 = r37
            com.smarterdroid.wftlib.ah r8 = r0.d     // Catch:{ SocketTimeoutException -> 0x0570 }
            r0 = r23
            int r9 = r0.length     // Catch:{ SocketTimeoutException -> 0x0570 }
            r10 = 8192(0x2000, float:1.14794E-41)
            int r9 = r8.read(r12, r9, r10)     // Catch:{ SocketTimeoutException -> 0x0570 }
            r8 = 1
            r10 = r9
            goto L_0x045a
        L_0x0570:
            r8 = move-exception
            r9 = 0
            r8 = 0
            r10 = r9
            goto L_0x045a
        L_0x0576:
            r8 = 0
            goto L_0x0491
        L_0x0579:
            r8 = move-exception
        L_0x057a:
            java.lang.StringBuffer r8 = new java.lang.StringBuffer     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.<init>()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            r0.l = r8     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8 = 0
            r9 = r8
        L_0x0585:
            r0 = r37
            byte[] r8 = r0.k     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r8 = r8.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r9 < r8) goto L_0x05e9
            r0 = r37
            java.lang.StringBuffer r8 = r0.l     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            r0.h = r8     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.lang.String[] r9 = r0.i     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r26 = 15
            r9 = r9[r26]     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r26 = 0
            r27 = 2
            r0 = r26
            r1 = r27
            java.lang.String r9 = r9.substring(r0, r1)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = java.lang.String.valueOf(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.<init>(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.lang.String[] r9 = r0.i     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r26 = 13
            r9 = r9[r26]     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.lang.String r9 = r0.h     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            boolean r8 = r8.equalsIgnoreCase(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 != 0) goto L_0x05d2
            com.smarterdroid.wftlib.aj.c()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x05d2:
            r8 = -1
            r0 = r25
            if (r0 != r8) goto L_0x062a
            r0 = r23
            int r8 = r0.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r22
            r0.write(r12, r8, r10)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            goto L_0x0337
        L_0x05e1:
            r3 = move-exception
            if (r38 == 0) goto L_0x00c8
            a(r38)
            goto L_0x00c8
        L_0x05e9:
            r0 = r37
            java.lang.StringBuffer r0 = r0.l     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r26 = r0
            r0 = r37
            byte[] r8 = r0.k     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            byte r8 = r8[r9]     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8 = r8 & 255(0xff, float:3.57E-43)
            java.lang.String r8 = java.lang.Integer.toHexString(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r27 = r8.length()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r28 = 1
            r0 = r27
            r1 = r28
            if (r0 != r1) goto L_0x0618
            java.lang.StringBuilder r27 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r28 = "0"
            r27.<init>(r28)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r27
            java.lang.StringBuilder r8 = r0.append(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x0618:
            java.util.Locale r27 = java.util.Locale.US     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r27
            java.lang.String r8 = r8.toUpperCase(r0)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r26
            r0.append(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r8 = r9 + 1
            r9 = r8
            goto L_0x0585
        L_0x062a:
            r0 = r23
            int r8 = r0.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r25
            if (r0 < r8) goto L_0x0640
            r0 = r23
            int r8 = r0.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r23
            int r9 = r0.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r9 = r25 - r9
            int r9 = r9 + -2
            r0 = r22
            r0.write(r12, r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x0640:
            r0 = r23
            int r8 = r0.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r8 = r8 + r25
            r0 = r23
            int r9 = r0.length     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r9 = r9 + r10
            int r9 = r9 + -1
            int r10 = r9 - r8
            int r10 = r10 + 1
            java.lang.StringBuilder r25 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r26 = "FirstAv: "
            r25.<init>(r26)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r25
            java.lang.StringBuilder r25 = r0.append(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r26 = "   LastAv: "
            java.lang.StringBuilder r25 = r25.append(r26)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r25
            java.lang.StringBuilder r9 = r0.append(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r9.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r9 = 1
            if (r10 <= r9) goto L_0x0337
            byte r6 = r12[r8]     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r9 = 45
            if (r6 != r9) goto L_0x0684
            int r6 = r8 + 1
            byte r6 = r12[r6]     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r9 = 45
            if (r6 != r9) goto L_0x0684
            r8 = 1
            r6 = 1
            r9 = 0
            r13 = r6
            r14 = r8
            r6 = r9
            goto L_0x0337
        L_0x0684:
            r6 = 1
            r7 = 1
            r0 = r37
            com.smarterdroid.wftlib.ah r9 = r0.d     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r9.a(r12, r8, r10)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.ah r8 = r0.d     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.a()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r13 = r6
            r6 = r7
            r7 = r15
            goto L_0x0337
        L_0x0699:
            int r8 = r11 + 1
            monitor-enter(r37)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r9 = r8 * 5
            long r9 = (long) r9
            r0 = r37
            r0.wait(r9)     // Catch:{ InterruptedException -> 0x0941 }
        L_0x06a4:
            monitor-exit(r37)     // Catch:{ all -> 0x06a8 }
            r11 = r8
            goto L_0x0337
        L_0x06a8:
            r3 = move-exception
            monitor-exit(r37)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            throw r3     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x06ab:
            r3 = move-exception
            if (r38 == 0) goto L_0x06b1
            a(r38)
        L_0x06b1:
            throw r3
        L_0x06b2:
            a(r38)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            goto L_0x037b
        L_0x06b7:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "isFile else"
            r6.<init>(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r11)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6 = 0
            int r7 = r18.length()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r18
            r0.delete(r6, r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "name=\""
            int r6 = r3.indexOf(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r6 < 0) goto L_0x0702
            java.lang.String r6 = "name=\""
            int r6 = r3.indexOf(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r6 = r6 + 6
            java.lang.String r6 = r3.substring(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "ff"
            int r6 = r6.compareTo(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r6 == 0) goto L_0x0702
            java.lang.String r4 = "name=\""
            int r4 = r3.indexOf(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r4 = r4 + 6
            java.lang.String r4 = r3.substring(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6 = 0
            int r7 = r4.length()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r7 = r7 + -1
            java.lang.String r4 = r4.substring(r6, r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x0702:
            r0 = r37
            com.smarterdroid.wftlib.ah r6 = r0.d     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = r6.a()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6 = 0
        L_0x070b:
            boolean r8 = r7.equals(r15)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 != 0) goto L_0x072e
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = java.lang.String.valueOf(r15)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8.<init>(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r9 = "--"
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            boolean r8 = r7.equals(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 != 0) goto L_0x072e
            r8 = 30
            if (r6 < r8) goto L_0x075c
        L_0x072e:
            r0 = r37
            java.util.Properties r6 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = r18.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6.put(r4, r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = "put "
            r6.<init>(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = "   "
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r18
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            boolean r6 = r7.equals(r15)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r6 == 0) goto L_0x0780
            r6 = 1
            goto L_0x0150
        L_0x075c:
            r0 = r18
            r0.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.ah r7 = r0.d     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = r7.a()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            int r8 = r7.length()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r8 != 0) goto L_0x077e
            int r6 = r6 + 1
            monitor-enter(r37)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r8 = 10
            r0 = r37
            r0.wait(r8)     // Catch:{ Exception -> 0x0944 }
        L_0x0779:
            monitor-exit(r37)     // Catch:{ all -> 0x077b }
            goto L_0x070b
        L_0x077b:
            r3 = move-exception
            monitor-exit(r37)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            throw r3     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x077e:
            r6 = 0
            goto L_0x070b
        L_0x0780:
            r6 = 0
            goto L_0x0150
        L_0x0783:
            r0 = r37
            com.smarterdroid.wftlib.av r4 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r5 = 14
            r4.e = r5     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.av r4 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r5 = 302(0x12e, float:4.23E-43)
            r4.a = r5     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.av r4 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r6 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "sslmode"
            java.lang.String r8 = "false"
            java.lang.String r6 = r6.getProperty(r7, r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "true"
            boolean r6 = r6.equals(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = com.smarterdroid.wftlib.aj.a((boolean) r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r5.<init>(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "://"
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r6 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "host"
            java.lang.String r6 = r6.getProperty(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "/login.wft?req="
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r3 = r5.append(r3)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r4.a(r3)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            goto L_0x0210
        L_0x07dd:
            r0 = r37
            com.smarterdroid.wftlib.ag r3 = r0.f     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r4 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            boolean r3 = r3.a((java.util.Properties) r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r3 != 0) goto L_0x07f9
            r0 = r37
            com.smarterdroid.wftlib.ag r3 = r0.f     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            boolean r3 = r3.a((java.util.Properties) r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r3 == 0) goto L_0x08eb
        L_0x07f9:
            com.smarterdroid.wftlib.au r3 = new com.smarterdroid.wftlib.au     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            android.content.Context r4 = r0.g     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r3.<init>(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r17
            r1 = r39
            r3.a((java.util.LinkedList) r0, (java.util.Properties) r4, (java.util.Properties) r1)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r3 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "json"
            boolean r3 = r3.containsKey(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r3 == 0) goto L_0x083d
            r0 = r37
            java.util.Properties r3 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "json"
            java.lang.String r3 = r3.getProperty(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "true"
            boolean r3 = r3.equals(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r3 == 0) goto L_0x083d
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r4 = 15
            r3.e = r4     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "{\"status\":\"1\",\"name\":\"file\",\"hash\":\"md5\"}\r\n"
            r3.b = r4     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            goto L_0x0210
        L_0x083d:
            java.lang.String r3 = ""
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "data_currentParams"
            boolean r4 = r4.containsKey(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r4 == 0) goto L_0x0873
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "data_currentParams"
            java.lang.String r4 = r4.getProperty(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r4 == 0) goto L_0x0873
            r0 = r37
            java.util.Properties r4 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "data_currentParams"
            java.lang.String r4 = r4.getProperty(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "?"
            boolean r4 = r4.equals(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            if (r4 != 0) goto L_0x0873
            r0 = r37
            java.util.Properties r3 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = "data_currentParams"
            java.lang.String r3 = r3.getProperty(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
        L_0x0873:
            r0 = r37
            com.smarterdroid.wftlib.av r4 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6 = 14
            r4.e = r6     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.av r4 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6 = 302(0x12e, float:4.23E-43)
            r4.a = r6     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.av r4 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r7 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = "sslmode"
            java.lang.String r9 = "false"
            java.lang.String r7 = r7.getProperty(r8, r9)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = "true"
            boolean r7 = r7.equals(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = com.smarterdroid.wftlib.aj.a((boolean) r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = java.lang.String.valueOf(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r6.<init>(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "://"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r7 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r8 = "host"
            java.lang.String r7 = r7.getProperty(r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "#"
            java.lang.String r8 = "%23"
            java.lang.String r5 = r5.replace(r7, r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "&"
            java.lang.String r8 = "%26"
            java.lang.String r5 = r5.replace(r7, r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "{"
            java.lang.String r8 = "%7B"
            java.lang.String r5 = r5.replace(r7, r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r7 = "}"
            java.lang.String r8 = "%7D"
            java.lang.String r5 = r5.replace(r7, r8)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r5 = r6.append(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r3 = r5.append(r3)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r3 = r3.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r4.a(r3)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            goto L_0x0210
        L_0x08eb:
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r4 = 14
            r3.e = r4     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r4 = 302(0x12e, float:4.23E-43)
            r3.a = r4     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            com.smarterdroid.wftlib.av r3 = r0.e     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r5 = r0.c     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "sslmode"
            java.lang.String r7 = "false"
            java.lang.String r5 = r5.getProperty(r6, r7)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "true"
            boolean r5 = r5.equals(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = com.smarterdroid.wftlib.aj.a((boolean) r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = java.lang.String.valueOf(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r4.<init>(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "://"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r0 = r37
            java.util.Properties r5 = r0.b     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r6 = "host"
            java.lang.String r5 = r5.getProperty(r6)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r5 = "/login.wft"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            r3.a(r4)     // Catch:{ IOException -> 0x00c2, NoSuchElementException -> 0x00e2, Exception -> 0x05e1 }
            goto L_0x0210
        L_0x0941:
            r9 = move-exception
            goto L_0x06a4
        L_0x0944:
            r8 = move-exception
            goto L_0x0779
        L_0x0947:
            r10 = r9
            goto L_0x045a
        L_0x094a:
            r3 = r6
            r11 = r7
            goto L_0x0221
        L_0x094e:
            r6 = r3
            goto L_0x03a4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.smarterdroid.wififiletransfer.b.a(java.util.Stack, java.util.Properties):com.smarterdroid.wftlib.av");
    }
}
