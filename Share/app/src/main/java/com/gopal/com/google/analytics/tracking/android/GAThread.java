package com.google.analytics.tracking.android;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.google.analytics.tracking.android.AnalyticsThread;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.android.gms.analytics.internal.Command;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

class GAThread extends Thread implements AnalyticsThread {
    private static GAThread i;
    private final LinkedBlockingQueue a = new LinkedBlockingQueue();
    private volatile boolean b = false;
    private volatile boolean c = false;
    /* access modifiers changed from: private */
    public volatile boolean d;
    /* access modifiers changed from: private */
    public volatile List e;
    /* access modifiers changed from: private */
    public volatile MetaModel f;
    /* access modifiers changed from: private */
    public volatile String g;
    /* access modifiers changed from: private */
    public volatile String h;
    /* access modifiers changed from: private */
    public volatile ServiceProxy j;
    /* access modifiers changed from: private */
    public final Context k;

    private GAThread(Context context) {
        super("GAThread");
        if (context != null) {
            this.k = context.getApplicationContext();
        } else {
            this.k = context;
        }
        start();
    }

    static GAThread a(Context context) {
        if (i == null) {
            i = new GAThread(context);
        }
        return i;
    }

    private static String a(Throwable th) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    static /* synthetic */ void a(GAThread gAThread, Map map) {
        String str;
        PackageManager packageManager = gAThread.k.getPackageManager();
        String packageName = gAThread.k.getPackageName();
        String installerPackageName = packageManager.getInstallerPackageName(packageName);
        String str2 = null;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(gAThread.k.getPackageName(), 0);
            if (packageInfo != null) {
                str = packageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
                try {
                    str2 = packageInfo.versionName;
                } catch (PackageManager.NameNotFoundException e2) {
                    Log.b("Error retrieving package info: appName set to " + str);
                    a(map, "appName", str);
                    a(map, "appVersion", str2);
                    a(map, "appId", packageName);
                    a(map, "appInstallerId", installerPackageName);
                    map.put("apiVersion", "1");
                }
            } else {
                str = packageName;
            }
        } catch (PackageManager.NameNotFoundException e3) {
            str = packageName;
            Log.b("Error retrieving package info: appName set to " + str);
            a(map, "appName", str);
            a(map, "appVersion", str2);
            a(map, "appId", packageName);
            a(map, "appInstallerId", installerPackageName);
            map.put("apiVersion", "1");
        }
        a(map, "appName", str);
        a(map, "appVersion", str2);
        a(map, "appId", packageName);
        a(map, "appInstallerId", installerPackageName);
        map.put("apiVersion", "1");
    }

    private void a(Runnable runnable) {
        this.a.add(runnable);
    }

    private static void a(Map map, String str, String str2) {
        if (!map.containsKey(str)) {
            map.put(str, str2);
        }
    }

    private boolean a(String str) {
        try {
            FileOutputStream openFileOutput = this.k.openFileOutput("gaClientId", 0);
            openFileOutput.write(str.getBytes());
            openFileOutput.close();
            return true;
        } catch (FileNotFoundException e2) {
            Log.b("Error creating clientId file.");
            return false;
        } catch (IOException e3) {
            Log.b("Error writing to clientId file.");
            return false;
        }
    }

    private static String b(Context context) {
        try {
            FileInputStream openFileInput = context.openFileInput("gaInstallData");
            byte[] bArr = new byte[8192];
            int read = openFileInput.read(bArr, 0, 8192);
            if (openFileInput.available() > 0) {
                Log.b("Too much campaign data, ignoring it.");
                openFileInput.close();
                context.deleteFile("gaInstallData");
                return null;
            }
            openFileInput.close();
            context.deleteFile("gaInstallData");
            if (read <= 0) {
                Log.f("Campaign file is empty.");
                return null;
            }
            String str = new String(bArr, 0, read);
            Log.c("Campaign found: " + str);
            return str;
        } catch (FileNotFoundException e2) {
            Log.c("No campaign data found.");
            return null;
        } catch (IOException e3) {
            Log.b("Error reading campaign data.");
            context.deleteFile("gaInstallData");
            return null;
        }
    }

    static /* synthetic */ void b(GAThread gAThread, Map map) {
        String str = (String) map.get("rawException");
        if (str != null) {
            map.remove("rawException");
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(Utils.e(str)));
                Object readObject = objectInputStream.readObject();
                objectInputStream.close();
                if (readObject instanceof Throwable) {
                    Throwable th = (Throwable) readObject;
                    map.put("exDescription", new StandardExceptionParser(gAThread.k, new ArrayList()).a((String) map.get("exceptionThreadName"), th));
                }
            } catch (IOException e2) {
                Log.f("IOException reading exception");
            } catch (ClassNotFoundException e3) {
                Log.f("ClassNotFoundException reading exception");
            }
        }
    }

    static /* synthetic */ boolean b(Map map) {
        String str;
        if (map.get("sampleRate") != null) {
            double b2 = Utils.b((String) map.get("sampleRate"));
            if (b2 <= 0.0d) {
                return true;
            }
            if (b2 < 100.0d && (str = (String) map.get("clientId")) != null && ((double) (Math.abs(str.hashCode()) % 10000)) >= b2 * 100.0d) {
                return true;
            }
        }
        return false;
    }

    static /* synthetic */ void c(Map map) {
        String d2 = Utils.d((String) map.get("campaign"));
        if (!TextUtils.isEmpty(d2)) {
            Map a2 = Utils.a(d2);
            map.put("campaignContent", a2.get("utm_content"));
            map.put("campaignMedium", a2.get("utm_medium"));
            map.put("campaignName", a2.get("utm_campaign"));
            map.put("campaignSource", a2.get("utm_source"));
            map.put("campaignKeyword", a2.get("utm_term"));
            map.put("campaignId", a2.get("utm_id"));
            map.put("gclid", a2.get("gclid"));
            map.put("dclid", a2.get("dclid"));
            map.put("gmob_t", a2.get("gmob_t"));
        }
    }

    private String d() {
        String str = null;
        try {
            FileInputStream openFileInput = this.k.openFileInput("gaClientId");
            byte[] bArr = new byte[128];
            int read = openFileInput.read(bArr, 0, 128);
            if (openFileInput.available() > 0) {
                Log.b("clientId file seems corrupted, deleting it.");
                openFileInput.close();
                this.k.deleteFile("gaInstallData");
            }
            if (read <= 0) {
                Log.b("clientId file seems empty, deleting it.");
                openFileInput.close();
                this.k.deleteFile("gaInstallData");
            } else {
                String str2 = new String(bArr, 0, read);
                try {
                    openFileInput.close();
                    str = str2;
                } catch (FileNotFoundException e2) {
                    str = str2;
                } catch (IOException e3) {
                    str = str2;
                    Log.b("Error reading clientId file, deleting it.");
                    this.k.deleteFile("gaInstallData");
                } catch (NumberFormatException e4) {
                    str = str2;
                    Log.b("cliendId file doesn't have long value, deleting it.");
                    this.k.deleteFile("gaInstallData");
                }
            }
        } catch (FileNotFoundException e5) {
        } catch (IOException e6) {
            Log.b("Error reading clientId file, deleting it.");
            this.k.deleteFile("gaInstallData");
        } catch (NumberFormatException e7) {
            Log.b("cliendId file doesn't have long value, deleting it.");
            this.k.deleteFile("gaInstallData");
        }
        if (str != null) {
            return str;
        }
        String lowerCase = UUID.randomUUID().toString().toLowerCase();
        return !a(lowerCase) ? "0" : lowerCase;
    }

    static /* synthetic */ String d(Map map) {
        String str = (String) map.get("internalHitUrl");
        return str == null ? (!map.containsKey("useSecure") || Utils.c((String) map.get("useSecure"))) ? "https://ssl.google-analytics.com/collect" : "http://www.google-analytics.com/collect" : str;
    }

    public final void a() {
        a((Runnable) new Runnable() {
            public void run() {
                GAThread.this.j.c();
            }
        });
    }

    public final void a(final AnalyticsThread.ClientIdCallback clientIdCallback) {
        a((Runnable) new Runnable() {
            public void run() {
                clientIdCallback.a(GAThread.this.h);
            }
        });
    }

    public final void a(final GoogleAnalytics.AppOptOutCallback appOptOutCallback) {
        a((Runnable) new Runnable() {
            public void run() {
                appOptOutCallback.a(GAThread.this.d);
            }
        });
    }

    public final void a(Map map) {
        final HashMap hashMap = new HashMap(map);
        final long currentTimeMillis = System.currentTimeMillis();
        hashMap.put("hitTime", Long.toString(currentTimeMillis));
        a((Runnable) new Runnable() {
            public void run() {
                hashMap.put("clientId", GAThread.this.h);
                if (!GAThread.this.d) {
                    GAThread gAThread = GAThread.this;
                    if (!GAThread.b(hashMap)) {
                        if (!TextUtils.isEmpty(GAThread.this.g)) {
                            hashMap.put("campaign", GAThread.this.g);
                            String unused = GAThread.this.g = null;
                        }
                        GAThread.a(GAThread.this, hashMap);
                        GAThread gAThread2 = GAThread.this;
                        GAThread.c(hashMap);
                        GAThread.b(GAThread.this, hashMap);
                        Map a2 = HitBuilder.a(GAThread.this.f, hashMap);
                        ServiceProxy g = GAThread.this.j;
                        long j = currentTimeMillis;
                        GAThread gAThread3 = GAThread.this;
                        g.a(a2, j, GAThread.d(hashMap), GAThread.this.e);
                    }
                }
            }
        });
    }

    public final void a(final boolean z) {
        a((Runnable) new Runnable() {
            public void run() {
                if (GAThread.this.d != z) {
                    if (z) {
                        try {
                            GAThread.this.k.getFileStreamPath("gaOptOut").createNewFile();
                        } catch (IOException e) {
                            Log.f("Error creating optOut file.");
                        }
                        GAThread.this.j.d();
                    } else {
                        GAThread.this.k.deleteFile("gaOptOut");
                    }
                    boolean unused = GAThread.this.d = z;
                }
            }
        });
    }

    public final LinkedBlockingQueue b() {
        return this.a;
    }

    public final Thread c() {
        return this;
    }

    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e2) {
            Log.f("sleep interrupted in GAThread initialize");
        }
        if (this.j == null) {
            this.j = new GAServiceProxy(this.k, this, (byte) 0);
        }
        this.j.e();
        this.e = new ArrayList();
        this.e.add(new Command("appendVersion", "_v", "ma1b5"));
        this.e.add(new Command("appendQueueTime", "qt", (String) null));
        this.e.add(new Command("appendCacheBuster", "z", (String) null));
        this.f = new MetaModel();
        MetaModelInitializer.a(this.f);
        try {
            this.d = this.k.getFileStreamPath("gaOptOut").exists();
            this.h = d();
            this.g = b(this.k);
        } catch (Throwable th) {
            Log.b("Error initializing the GAThread: " + a(th));
            Log.b("Google Analytics will not start up.");
            this.b = true;
        }
        while (!this.c) {
            try {
                Runnable runnable = (Runnable) this.a.take();
                if (!this.b) {
                    runnable.run();
                }
            } catch (InterruptedException e3) {
                Log.c(e3.toString());
            } catch (Throwable th2) {
                Log.b("Error on GAThread: " + a(th2));
                Log.b("Google Analytics is shutting down.");
                this.b = true;
            }
        }
    }
}
