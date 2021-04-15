package com.smarterdroid.wftlib;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

public final class i implements a {
    public InputStream a = null;
    public String b;
    private aj c;
    private Properties d;
    private AssetManager e;
    private String f = "none";

    public i(Properties properties, aj ajVar) {
        this.c = ajVar;
        this.d = properties;
        this.e = this.c.g().getAssets();
        this.f = this.d.getProperty("f", "none");
        "filename: " + this.f;
        try {
            this.a = this.e.open("wftfiles/" + this.f, 3);
        } catch (Exception e2) {
            this.a = null;
            e2.printStackTrace();
        }
        if (this.a != null) {
            this.b = "Content-Type: " + ax.a(this.f) + "\r\nExpires: " + ax.a.format(new Date(new Date().getTime() + 86400000)) + "\r\nCache-Control: public, max-age=86400\r\nTransfer-Encoding: chunked\r\n";
        }
    }

    public final void a(OutputStream outputStream) {
        try {
            if (this.a != null) {
                byte[] bArr = new byte[16384];
                while (true) {
                    ab.a();
                    int read = this.a.read(bArr, 0, 16384);
                    if (read >= 0) {
                        outputStream.write(bArr, 0, read);
                    }
                }
                outputStream.flush();
                this.a.close();
            }
            outputStream.flush();
            try {
                this.a.close();
            } catch (IOException e2) {
            }
        } catch (Exception e3) {
            try {
                this.a.close();
            } catch (IOException e4) {
            }
        } catch (Throwable th) {
            try {
                this.a.close();
            } catch (IOException e5) {
            }
            throw th;
        }
    }
}
