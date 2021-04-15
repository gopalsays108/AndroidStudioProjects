package com.smarterdroid.wftlib;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class k implements a {
    public File a;
    private OutputStream b;

    public k(File file) {
        this.a = file;
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
                    zipOutputStream.putNextEntry(new ZipEntry(file2.getPath().replaceAll(str2, "")));
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read == -1) {
                            break;
                        }
                        zipOutputStream.write(bArr, 0, read);
                        ab.a();
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

    public final void a(OutputStream outputStream) {
        this.b = outputStream;
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(this.b));
            a(this.a.getAbsolutePath(), this.a.getParent(), zipOutputStream);
            zipOutputStream.finish();
            zipOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
