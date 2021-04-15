package com.smarterdroid.wififiletransfer;

import android.content.Context;
import com.smarterdroid.wftlib.a;
import com.smarterdroid.wftlib.ab;
import com.smarterdroid.wftlib.ah;
import com.smarterdroid.wftlib.aj;
import com.smarterdroid.wftlib.av;
import com.smarterdroid.wftlib.ax;
import com.smarterdroid.wftlib.o;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Stack;
import java.util.StringTokenizer;

public final class af implements Runnable {
    final int a = 8192;
    private Socket b = null;
    private av c = new av();
    private Properties d = new Properties();
    private Properties e = new Properties();
    private ah f = null;
    private Stack g = new Stack();
    private Context h;
    private Properties i;
    private String j;
    private aj k;
    private boolean l;

    public af(Socket socket, Context context, String str, aj ajVar, boolean z) {
        this.h = context;
        this.b = socket;
        this.j = str;
        this.k = ajVar;
        this.l = z;
    }

    private Properties a(ah ahVar) {
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(ahVar.readLine());
            Properties properties = new Properties();
            if (stringTokenizer.hasMoreTokens()) {
                properties.put("method", stringTokenizer.nextToken().toLowerCase(Locale.US));
            } else {
                this.c.e = 13;
                this.c.a = 400;
                this.c.b = "<html><head></head><body><h1>400 Bad Request</h1></body></html>";
                a();
            }
            if (stringTokenizer.hasMoreTokens()) {
                String replaceAll = stringTokenizer.nextToken().replaceAll("\\[", "%5B").replaceAll("\\]", "%5D").replaceAll("\\<", "%3C").replaceAll("\\>", "%3E").replaceAll("\\|", "%7C");
                URI uri = new URI(replaceAll);
                properties.put("uri", uri.getPath());
                "uri: " + uri.getPath() + " " + replaceAll;
                if (uri.getHost() != null) {
                    properties.put("host", uri.getHost());
                    "host: " + uri.getHost();
                }
                String rawQuery = uri.getRawQuery();
                if (rawQuery != null) {
                    StringTokenizer stringTokenizer2 = new StringTokenizer(rawQuery, "&");
                    while (stringTokenizer2.hasMoreTokens()) {
                        String nextToken = stringTokenizer2.nextToken();
                        int indexOf = nextToken.indexOf(61);
                        if (indexOf >= 0) {
                            this.d.put(URLDecoder.decode(nextToken.substring(0, indexOf), "UTF-8").trim(), URLDecoder.decode(nextToken.substring(indexOf + 1), "UTF-8"));
                            "param: " + URLDecoder.decode(nextToken.substring(0, indexOf)).trim() + " " + URLDecoder.decode(nextToken.substring(indexOf + 1), "UTF-8");
                        }
                    }
                }
            } else {
                this.c.e = 13;
                this.c.a = 400;
                this.c.b = "<html><head></head><body><h1>400 Bad Request</h1></body></html>";
                a();
            }
            if (this.l) {
                this.d.put("sslmode", "true");
            } else {
                this.d.put("sslmode", "false");
            }
            String readLine = ahVar.readLine();
            while (readLine.trim().length() > 0) {
                int indexOf2 = readLine.indexOf(58);
                "READ: " + readLine;
                properties.put(readLine.substring(0, indexOf2).trim().toLowerCase(Locale.US), readLine.substring(indexOf2 + 1).trim());
                readLine = ahVar.readLine();
            }
            return properties;
        } catch (Exception e2) {
            this.c.e = 13;
            this.c.a = 400;
            this.c.b = "<html><head></head><body><h1>400 Bad Request</h1></body></html>";
            a();
            return null;
        }
    }

    private void a() {
        PrintWriter printWriter;
        boolean z;
        Throwable th;
        OutputStream outputStream;
        boolean z2 = false;
        PrintWriter printWriter2 = null;
        try {
            OutputStream bufferedOutputStream = new BufferedOutputStream(this.b.getOutputStream(), 65536);
            printWriter = new PrintWriter(bufferedOutputStream);
            try {
                av avVar = this.c;
                printWriter.print(avVar.a == 400 ? "HTTP/1.1 400 Bad Request\r\n" : avVar.a == 403 ? "HTTP/1.1 403 Forbidden\r\n" : avVar.a == 404 ? "HTTP/1.1 404 Not Found\r\n" : avVar.a == 302 ? "HTTP/1.1 302 Moved Temporarily\r\n" : "HTTP/1.1 200 OK\r\n");
                printWriter.print("Connection: Close\r\n");
                switch (this.c.e) {
                    case 11:
                        printWriter.print("Date: " + ax.a.format(new Date()) + "\r\n");
                        if (this.d.containsKey("lm")) {
                            printWriter.print("Expires: " + ax.a.format(new Date(new Date().getTime() + 15552000000L)) + "\r\n");
                            printWriter.print("Cache-Control: public, max-age=15552000\r\n");
                        }
                        printWriter.print("Content-Type: " + ax.a(this.c.c.getName()) + "\r\n");
                        printWriter.print("Content-Length: " + this.c.a() + "\r\n\r\n");
                        printWriter.flush();
                        z2 = true;
                        break;
                    case 15:
                        printWriter.print("Date: " + ax.a.format(new Date()) + "\r\n");
                        printWriter.print("Content-Type: application/json\r\n");
                        printWriter.print("Content-Length: " + this.c.a() + "\r\n\r\n");
                        printWriter.flush();
                        break;
                    case 16:
                        printWriter.print("Date: " + ax.a.format(new Date()) + "\r\n");
                        printWriter.print("Content-Type: unknown/unknown\r\n");
                        printWriter.print("Content-Disposition: attachment; filename=\"" + this.c.c.getName() + "\"\r\n");
                        printWriter.print("Content-Length: " + this.c.a() + "\r\n\r\n");
                        printWriter.flush();
                        z2 = true;
                        break;
                    case 17:
                        printWriter.print("Date: " + ax.a.format(new Date()) + "\r\n");
                        if (this.d.containsKey("lm")) {
                            printWriter.print("Expires: " + ax.a.format(new Date(new Date().getTime() + 15552000000L)) + "\r\n");
                            printWriter.print("Cache-Control: public, max-age=15552000\r\n");
                        }
                        printWriter.print("Content-Type: image/jpeg\r\n");
                        printWriter.print("\r\n");
                        printWriter.flush();
                        z2 = true;
                        break;
                    case 18:
                        printWriter.print("Date: " + ax.a.format(new Date()) + "\r\n");
                        printWriter.print("Content-Type: text/html\r\n\r\n");
                        printWriter.flush();
                        this.c.f.a(bufferedOutputStream);
                        break;
                    case 19:
                        printWriter.print("Date: " + ax.a.format(new Date()) + "\r\n");
                        printWriter.print(this.c.d);
                        printWriter.print("\r\n");
                        printWriter.flush();
                        z2 = true;
                        break;
                    default:
                        printWriter.print("Date: " + ax.a.format(new Date()) + "\r\n");
                        printWriter.print("Content-Type: text/html\r\n");
                        printWriter.print(this.c.d);
                        printWriter.print("Content-Length: " + this.c.a() + "\r\n\r\n");
                        printWriter.flush();
                        break;
                }
                if (!z2) {
                    printWriter.print(String.valueOf(this.c.b) + "\r\n");
                    printWriter.flush();
                } else {
                    printWriter.flush();
                    if (this.c.e == 17 || this.c.e == 19) {
                        a aVar = this.c.f;
                        outputStream = this.c.d.contains("Transfer-Encoding: chunked") ? new o(bufferedOutputStream) : bufferedOutputStream;
                        aVar.a(outputStream);
                    } else {
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(this.c.c), 8192);
                        byte[] bArr = new byte[4096];
                        while (true) {
                            ab.a();
                            int read = bufferedInputStream.read(bArr, 0, bArr.length);
                            if (read >= 0) {
                                bufferedOutputStream.write(bArr, 0, read);
                            } else {
                                outputStream = bufferedOutputStream;
                            }
                        }
                    }
                    outputStream.flush();
                    outputStream.close();
                }
                if (!z2) {
                    printWriter.flush();
                    printWriter.close();
                }
            } catch (IOException e2) {
            } catch (Throwable th2) {
                Throwable th3 = th2;
                printWriter2 = printWriter;
                z = z2;
                th = th3;
                if (printWriter2 != null && !z) {
                    printWriter2.flush();
                    printWriter2.close();
                }
                throw th;
            }
        } catch (IOException e3) {
            printWriter = null;
            if (printWriter != null && !z2) {
                printWriter.flush();
                printWriter.close();
            }
        } catch (Throwable th4) {
            Throwable th5 = th4;
            z = false;
            th = th5;
            printWriter2.flush();
            printWriter2.close();
            throw th;
        }
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 166 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r12 = this;
            r11 = 400(0x190, float:5.6E-43)
            r10 = 13
            java.net.Socket r0 = r12.b     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r1 = 15000(0x3a98, float:2.102E-41)
            r0.setSoTimeout(r1)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.net.Socket r0 = r12.b     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r1 = 1
            r2 = 5
            r0.setSoLinger(r1, r2)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.io.BufferedInputStream r0 = new java.io.BufferedInputStream     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.net.Socket r1 = r12.b     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.io.InputStream r1 = r1.getInputStream()     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r2 = 32768(0x8000, float:4.5918E-41)
            r0.<init>(r1, r2)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            com.smarterdroid.wftlib.ah r1 = new com.smarterdroid.wftlib.ah     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r1.<init>(r0)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r12.f = r1     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            com.smarterdroid.wftlib.ah r0 = r12.f     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.util.Properties r0 = r12.a(r0)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r12.i = r0     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.net.Socket r0 = r12.b     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r1 = 2000(0x7d0, float:2.803E-42)
            r0.setSoTimeout(r1)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            com.smarterdroid.wftlib.ag r3 = new com.smarterdroid.wftlib.ag     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.lang.String r0 = r12.j     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r3.<init>(r0)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.util.Properties r0 = r12.i     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            if (r0 == 0) goto L_0x0072
            java.util.Properties r0 = r12.i     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.lang.String r1 = "method"
            java.lang.String r0 = r0.getProperty(r1)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.lang.String r1 = "get"
            boolean r0 = r0.equals(r1)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            if (r0 == 0) goto L_0x0072
            com.smarterdroid.wififiletransfer.a r0 = new com.smarterdroid.wififiletransfer.a     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.util.Properties r1 = r12.i     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.util.Properties r2 = r12.d     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            android.content.Context r4 = r12.h     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            com.smarterdroid.wftlib.aj r5 = r12.k     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r0.<init>(r1, r2, r3, r4, r5)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            com.smarterdroid.wftlib.av r0 = r0.a()     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r12.c = r0     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r12.a()     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
        L_0x0067:
            com.smarterdroid.wftlib.ah r0 = r12.f     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r0.close()     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.net.Socket r0 = r12.b     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r0.close()     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
        L_0x0071:
            return
        L_0x0072:
            java.util.Properties r0 = r12.i     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            if (r0 == 0) goto L_0x00ea
            java.util.Properties r0 = r12.i     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.lang.String r1 = "method"
            java.lang.String r0 = r0.getProperty(r1)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.lang.String r1 = "post"
            boolean r0 = r0.equals(r1)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            if (r0 == 0) goto L_0x00ea
            java.util.Properties r0 = r12.d     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.lang.String r1 = "data_uri"
            java.util.Properties r2 = r12.i     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.lang.String r4 = "uri"
            java.lang.String r2 = r2.getProperty(r4)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r0.put(r1, r2)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            com.smarterdroid.wififiletransfer.b r4 = new com.smarterdroid.wififiletransfer.b     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.util.Properties r5 = r12.i     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.util.Properties r6 = r12.d     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            com.smarterdroid.wftlib.ah r7 = r12.f     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            android.content.Context r9 = r12.h     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r8 = r3
            r4.<init>(r5, r6, r7, r8, r9)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.util.Stack r0 = r12.g     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.util.Properties r1 = r12.e     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.net.Socket r2 = r12.b     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            com.smarterdroid.wftlib.av r0 = r4.a(r0, r1)     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r12.c = r0     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r12.a()     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            goto L_0x0067
        L_0x00b3:
            r0 = move-exception
            java.util.Properties r0 = r12.d
            java.lang.String r1 = "json"
            boolean r0 = r0.containsKey(r1)
            if (r0 == 0) goto L_0x0129
            java.util.Properties r0 = r12.d
            java.lang.String r1 = "json"
            java.lang.String r0 = r0.getProperty(r1)
            java.lang.String r1 = "true"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0129
            com.smarterdroid.wftlib.av r0 = r12.c
            r1 = 15
            r0.e = r1
            com.smarterdroid.wftlib.av r0 = r12.c
            java.lang.String r1 = "{\"status\":\"0\",\"name\":\"file\",\"hash\":\"md5\"}\r\n"
            r0.b = r1
            r12.a()
        L_0x00dd:
            com.smarterdroid.wftlib.ah r0 = r12.f     // Catch:{ IOException -> 0x00e8 }
            r0.close()     // Catch:{ IOException -> 0x00e8 }
            java.net.Socket r0 = r12.b     // Catch:{ IOException -> 0x00e8 }
            r0.close()     // Catch:{ IOException -> 0x00e8 }
            goto L_0x0071
        L_0x00e8:
            r0 = move-exception
            goto L_0x0071
        L_0x00ea:
            com.smarterdroid.wftlib.av r0 = r12.c     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r1 = 13
            r0.e = r1     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            com.smarterdroid.wftlib.av r0 = r12.c     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r1 = 400(0x190, float:5.6E-43)
            r0.a = r1     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            com.smarterdroid.wftlib.av r0 = r12.c     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            java.lang.String r1 = "<html><head></head><body><h1>400 Bad Request</h1></body></html>"
            r0.b = r1     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            r12.a()     // Catch:{ InterruptedIOException -> 0x00b3, IOException -> 0x0101, Exception -> 0x0197 }
            goto L_0x0067
        L_0x0101:
            r0 = move-exception
        L_0x0102:
            java.util.Stack r0 = r12.g
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x013b
            com.smarterdroid.wftlib.ah r0 = r12.f
            if (r0 == 0) goto L_0x0113
            com.smarterdroid.wftlib.ah r0 = r12.f     // Catch:{ IOException -> 0x0199 }
            r0.close()     // Catch:{ IOException -> 0x0199 }
        L_0x0113:
            java.net.Socket r0 = r12.b
            if (r0 == 0) goto L_0x0071
            java.net.Socket r0 = r12.b
            boolean r0 = r0.isClosed()
            if (r0 != 0) goto L_0x0071
            java.net.Socket r0 = r12.b     // Catch:{ IOException -> 0x0126 }
            r0.close()     // Catch:{ IOException -> 0x0126 }
            goto L_0x0071
        L_0x0126:
            r0 = move-exception
            goto L_0x0071
        L_0x0129:
            com.smarterdroid.wftlib.av r0 = r12.c
            r0.e = r10
            com.smarterdroid.wftlib.av r0 = r12.c
            r0.a = r11
            com.smarterdroid.wftlib.av r0 = r12.c
            java.lang.String r1 = "<html><head></head><body><h1>400 Bad Request</h1></body></html>"
            r0.b = r1
            r12.a()
            goto L_0x00dd
        L_0x013b:
            java.util.Stack r0 = r12.g
            java.lang.Object r0 = r0.peek()
            java.io.File r0 = (java.io.File) r0
            boolean r0 = r0.exists()
            if (r0 == 0) goto L_0x0102
            java.util.Stack r0 = r12.g
            java.lang.Object r0 = r0.pop()
            java.io.File r0 = (java.io.File) r0
            r0.delete()
            goto L_0x0102
        L_0x0155:
            java.util.Stack r0 = r12.g
            java.lang.Object r0 = r0.peek()
            java.io.File r0 = (java.io.File) r0
            boolean r0 = r0.exists()
            if (r0 == 0) goto L_0x016e
            java.util.Stack r0 = r12.g
            java.lang.Object r0 = r0.pop()
            java.io.File r0 = (java.io.File) r0
            r0.delete()
        L_0x016e:
            java.util.Stack r0 = r12.g
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0155
            com.smarterdroid.wftlib.ah r0 = r12.f
            if (r0 == 0) goto L_0x017f
            com.smarterdroid.wftlib.ah r0 = r12.f     // Catch:{ IOException -> 0x0195 }
            r0.close()     // Catch:{ IOException -> 0x0195 }
        L_0x017f:
            java.net.Socket r0 = r12.b
            if (r0 == 0) goto L_0x0071
            java.net.Socket r0 = r12.b
            boolean r0 = r0.isClosed()
            if (r0 != 0) goto L_0x0071
            java.net.Socket r0 = r12.b     // Catch:{ IOException -> 0x0192 }
            r0.close()     // Catch:{ IOException -> 0x0192 }
            goto L_0x0071
        L_0x0192:
            r0 = move-exception
            goto L_0x0071
        L_0x0195:
            r0 = move-exception
            goto L_0x017f
        L_0x0197:
            r0 = move-exception
            goto L_0x016e
        L_0x0199:
            r0 = move-exception
            goto L_0x0113
        */
        throw new UnsupportedOperationException("Method not decompiled: com.smarterdroid.wififiletransfer.af.run():void");
    }
}
