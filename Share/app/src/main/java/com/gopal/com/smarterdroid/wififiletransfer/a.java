package com.smarterdroid.wififiletransfer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import com.smarterdroid.wftlib.aa;
import com.smarterdroid.wftlib.ag;
import com.smarterdroid.wftlib.aj;
import com.smarterdroid.wftlib.av;
import com.smarterdroid.wftlib.aw;
import com.smarterdroid.wftlib.b;
import com.smarterdroid.wftlib.d;
import com.smarterdroid.wftlib.e;
import com.smarterdroid.wftlib.f;
import com.smarterdroid.wftlib.h;
import com.smarterdroid.wftlib.i;
import com.smarterdroid.wftlib.j;
import com.smarterdroid.wftlib.k;
import com.smarterdroid.wftlib.l;
import com.smarterdroid.wftlib.m;
import java.io.File;
import java.util.Properties;

public final class a {
    private Properties a;
    private Properties b;
    private ag c;
    private Context d;
    private aj e;

    public a(Properties properties, Properties properties2, ag agVar, Context context, aj ajVar) {
        this.a = properties;
        this.c = agVar;
        this.b = properties2;
        this.d = context;
        this.e = ajVar;
    }

    public final av a() {
        boolean z = true;
        av avVar = new av();
        if (this.c.a(this.a) || this.a.getProperty("uri").equals("/login.wft")) {
            "GET: " + this.a.getProperty("uri") + " Params: " + this.b;
            if (this.a.getProperty("uri").contains("../") || this.a.getProperty("uri").contains("/..")) {
                avVar.e = 13;
                avVar.a = 403;
                avVar.b = "<html><head></head><body><h1>403 Forbidden</h1></body></html>";
            } else if (this.a.getProperty("uri").equals("/login.wft")) {
                String str = "/";
                if (this.b.containsKey("req") && this.b.getProperty("req") != null) {
                    str = this.b.getProperty("req", "/");
                }
                avVar.e = 12;
                h hVar = new h(false, str, this.b);
                aw.a(this.d, "Login Screen");
                avVar.b = hVar.a();
                if (this.b.containsKey("action") && this.b.getProperty("action") != null && this.b.getProperty("action").equals("logout") && this.c.a(this.a)) {
                    ag.b();
                }
            } else if (this.a.getProperty("uri").equals("/gallery.wft")) {
                avVar.e = 12;
                e eVar = new e(this.e, this.b);
                aw.a(this.d, "Gallery Screen");
                avVar.b = eVar.a();
            } else if (this.a.getProperty("uri").equals("/thumbnail.wft")) {
                j jVar = new j(this.e, this.b);
                avVar.e = 17;
                avVar.f = jVar;
            } else if (this.a.getProperty("uri").equals("/dirsize.wft")) {
                avVar.e = 18;
                avVar.f = new b(this.b, this.e);
            } else if (this.a.getProperty("uri").equals("/getdirsize.wft")) {
                avVar.e = 19;
                f fVar = new f(this.b, this.e);
                avVar.f = fVar;
                avVar.d = fVar.a();
            } else if (this.a.getProperty("uri").equals("/lang.wft")) {
                avVar.e = 19;
                avVar.f = new m(this.b, this.e);
                avVar.d = m.a();
            } else if (this.a.getProperty("uri").equals("/zipdir.wft")) {
                File file = new File("/", this.b.getProperty("dir", "nofile"));
                if (!file.exists() || !file.isDirectory()) {
                    avVar.e = 13;
                    avVar.a = 403;
                    avVar.b = "<html><head></head><body><h1>403 Forbidden</h1></body></html>";
                } else {
                    avVar.e = 19;
                    k kVar = new k(file);
                    avVar.f = kVar;
                    avVar.d = "Content-Type: unknown/unknown\r\nContent-Disposition: attachment; filename=\"" + kVar.a.getName() + ".zip\"\r\nTransfer-Encoding: chunked\r\n";
                }
            } else if (this.a.getProperty("uri").equals("/wftres.wft")) {
                i iVar = new i(this.b, this.e);
                if (iVar.a == null) {
                    z = false;
                }
                if (z) {
                    avVar.e = 19;
                    avVar.f = iVar;
                    avVar.d = iVar.b;
                } else {
                    avVar.e = 13;
                    avVar.a = 404;
                    avVar.b = "<html><head></head><body><h1>404 Not Found</h1></body></html>";
                }
            } else if (this.a.getProperty("uri").endsWith("/dontaskagain.wft")) {
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(new aa(this.d).a).edit();
                edit.putBoolean("dontaskagain", true);
                edit.commit();
                avVar.e = 13;
                avVar.a = 403;
                avVar.b = "<html><head></head><body><h1>403 Forbidden</h1></body></html>";
            } else {
                File file2 = new File("/", this.a.getProperty("uri"));
                if (file2.isDirectory()) {
                    if (!this.e.i() || !file2.getAbsolutePath().equals(File.separator)) {
                        avVar.e = 18;
                        d dVar = new d(this.e, file2, this.b, this.a);
                        aw.a(this.d, "File Browser Screen");
                        avVar.f = dVar;
                    } else {
                        avVar.e = 14;
                        avVar.a = 302;
                        avVar.a(String.valueOf(aj.a(this.b.getProperty("sslmode", "false").equals("true"))) + "://" + this.a.getProperty("host") + Environment.getExternalStorageDirectory());
                    }
                } else if (this.b.containsKey("cd") && this.b.getProperty("cd") != null && this.b.getProperty("cd").equals("mzip")) {
                    avVar.e = 19;
                    l lVar = new l(file2, this.b.getProperty("path", "/"), this.b.getProperty("files", ""));
                    avVar.f = lVar;
                    avVar.d = lVar.a;
                } else if (file2.isFile()) {
                    if (!this.b.containsKey("cd") || this.b.getProperty("cd") == null || !this.b.getProperty("cd").equals("at")) {
                        avVar.e = 11;
                    } else {
                        avVar.e = 16;
                    }
                    avVar.c = file2;
                } else {
                    avVar.e = 13;
                    avVar.a = 404;
                    avVar.b = "<html><head></head><body><h1>404 Not Found</h1></body></html>";
                }
            }
        } else {
            avVar.e = 14;
            avVar.a = 302;
            avVar.a(String.valueOf(aj.a(this.b.getProperty("sslmode", "false").equals("true"))) + "://" + this.a.getProperty("host") + "/login.wft?req=" + this.a.getProperty("uri", "/"));
        }
        return avVar;
    }
}
