package com.smarterdroid.wftlib;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class az {
    private File a;
    private String b;

    public az(File file, String str) {
        this.a = file;
        this.b = str;
        if (!this.b.endsWith("/")) {
            this.b = String.valueOf(this.b) + "/";
        }
    }

    public final void a() {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(this.a), 16384));
            byte[] bArr = new byte[8192];
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    File file = new File(this.b, nextEntry.getName());
                    if (nextEntry.isDirectory()) {
                        file.mkdirs();
                    } else {
                        new File(file.getParent()).mkdirs();
                        try {
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), 16384);
                            while (true) {
                                int read = zipInputStream.read(bArr);
                                if (read <= 0) {
                                    break;
                                }
                                bufferedOutputStream.write(bArr, 0, read);
                                ab.a();
                            }
                            bufferedOutputStream.close();
                            at.a(aj.h(), file);
                        } catch (FileNotFoundException e) {
                        }
                        try {
                            zipInputStream.closeEntry();
                        } catch (Exception e2) {
                            zipInputStream.closeEntry();
                            "UTF " + e2.getMessage();
                            e2.printStackTrace();
                        }
                    }
                } else {
                    zipInputStream.close();
                    return;
                }
            }
        } catch (Exception e3) {
        }
    }
}
