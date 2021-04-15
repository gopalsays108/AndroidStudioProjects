package com.smarterdroid.wftlib;

import android.annotation.TargetApi;
import android.os.Build;
import com.smarterdroid.wftlib.b.b;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@TargetApi(7)
public final class ay {
    private File a;
    private String b;
    private LinkedList c;

    public ay(File file, String str, LinkedList linkedList) {
        this.a = file;
        this.b = str;
        if (!this.b.endsWith("/")) {
            this.b = String.valueOf(this.b) + "/";
        }
        this.c = linkedList;
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
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file2), 16384);
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
        } catch (Exception e) {
            e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void a() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(this.a);
            int i = Build.VERSION.SDK_INT;
            if (i <= 6 || i >= 14) {
                ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));
                zipOutputStream.setLevel(1);
                while (!this.c.isEmpty()) {
                    if (!((File) this.c.peek()).getAbsolutePath().equalsIgnoreCase(this.a.getAbsolutePath())) {
                        a(((File) this.c.remove()).getAbsolutePath(), this.a.getParent(), zipOutputStream);
                    } else {
                        this.c.remove();
                    }
                }
                zipOutputStream.finish();
                zipOutputStream.close();
                at.a(aj.h(), this.a);
                return;
            }
            b bVar = new b(new BufferedOutputStream(fileOutputStream, 16384));
            bVar.a(1);
            while (!this.c.isEmpty()) {
                if (!((File) this.c.peek()).getAbsolutePath().equalsIgnoreCase(this.a.getAbsolutePath())) {
                    a(((File) this.c.remove()).getAbsolutePath(), this.a.getParent(), bVar);
                } else {
                    this.c.remove();
                }
            }
            bVar.a();
            bVar.close();
            at.a(aj.h(), this.a);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
