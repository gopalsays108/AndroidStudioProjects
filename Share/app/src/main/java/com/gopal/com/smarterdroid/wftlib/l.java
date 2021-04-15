package com.smarterdroid.wftlib;

import android.annotation.TargetApi;
import android.os.Build;
import com.smarterdroid.wftlib.b.b;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@TargetApi(7)
public final class l implements a {
    public String a;
    private OutputStream b;
    private File c;
    private LinkedList d = new LinkedList();
    private String e;

    public l(File file, String str, String str2) {
        this.a = "Content-Type: unknown/unknown\r\nContent-Disposition: attachment; filename=\"" + file.getName() + "\"\r\nTransfer-Encoding: chunked\r\n";
        this.e = str;
        this.c = file;
        StringTokenizer stringTokenizer = new StringTokenizer(str2, ",");
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            if (nextToken.length() > 0) {
                File file2 = new File(nextToken);
                if (file2.exists() && file2.canRead()) {
                    this.d.add(file2);
                }
            }
        }
    }

    private void a(String str, String str2, b bVar) {
        if (!str2.endsWith(System.getProperty("file.separator"))) {
            str2 = String.valueOf(str2) + System.getProperty("file.separator");
        }
        try {
            String[] strArr = {str};
            byte[] bArr = new byte[2156];
            for (String file : strArr) {
                File file2 = new File(file);
                if (!file2.isDirectory()) {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file2), 8624);
                    ZipEntry zipEntry = new ZipEntry(file2.getPath().replaceAll(str2, ""));
                    zipEntry.setTime(file2.lastModified());
                    bVar.a(zipEntry);
                    while (true) {
                        int read = bufferedInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        bVar.write(bArr, 0, read);
                        ab.a();
                    }
                    bufferedInputStream.close();
                    bVar.flush();
                    bVar.b();
                } else if (file2.list().length == 0) {
                    bVar.a(new ZipEntry(String.valueOf(file2.getPath().replaceAll(str2, "")) + System.getProperty("file.separator") + "."));
                    bVar.b();
                } else {
                    String[] list = file2.list();
                    for (int i = 0; i < list.length; i++) {
                        a(String.valueOf(file2.getPath()) + "/" + list[i], str2, bVar);
                    }
                }
            }
        } catch (Exception e2) {
        }
    }

    private void a(String str, String str2, ZipOutputStream zipOutputStream) {
        if (!str2.endsWith(System.getProperty("file.separator"))) {
            str2 = String.valueOf(str2) + System.getProperty("file.separator");
        }
        try {
            String[] strArr = {str};
            byte[] bArr = new byte[2156];
            for (String file : strArr) {
                File file2 = new File(file);
                if (!file2.isDirectory()) {
                    FileInputStream fileInputStream = new FileInputStream(file2);
                    ZipEntry zipEntry = new ZipEntry(file2.getPath().replaceAll(str2, ""));
                    zipEntry.setTime(file2.lastModified());
                    zipOutputStream.putNextEntry(zipEntry);
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        zipOutputStream.write(bArr, 0, read);
                    }
                    fileInputStream.close();
                    zipOutputStream.flush();
                    zipOutputStream.closeEntry();
                } else if (file2.list().length == 0) {
                    zipOutputStream.putNextEntry(new ZipEntry(String.valueOf(file2.getPath().replaceAll(str2, "")) + System.getProperty("file.separator") + "."));
                    zipOutputStream.closeEntry();
                } else {
                    String[] list = file2.list();
                    for (int i = 0; i < list.length; i++) {
                        a(String.valueOf(file2.getPath()) + "/" + list[i], str2, zipOutputStream);
                    }
                }
            }
        } catch (Exception e2) {
        }
    }

    public final void a(OutputStream outputStream) {
        this.b = outputStream;
        try {
            int i = Build.VERSION.SDK_INT;
            if (i <= 6 || i >= 14) {
                ZipOutputStream zipOutputStream = new ZipOutputStream(this.b);
                zipOutputStream.setLevel(0);
                while (!this.d.isEmpty()) {
                    a(((File) this.d.remove()).getAbsolutePath(), this.c.getParent(), zipOutputStream);
                }
                zipOutputStream.finish();
                zipOutputStream.close();
                return;
            }
            b bVar = new b(this.b);
            bVar.a(0);
            while (!this.d.isEmpty()) {
                a(((File) this.d.remove()).getAbsolutePath(), this.c.getParent(), bVar);
            }
            bVar.a();
            bVar.close();
        } catch (FileNotFoundException e2) {
        } catch (IOException e3) {
        }
    }
}
