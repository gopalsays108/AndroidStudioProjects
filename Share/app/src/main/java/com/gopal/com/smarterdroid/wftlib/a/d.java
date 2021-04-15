package com.smarterdroid.wftlib.a;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

@TargetApi(7)
public final class d {
    protected static c a = null;
    private static ArrayList b = null;
    private static boolean c = false;
    private static a d = null;
    private static b e = null;

    static {
        c();
    }

    public static BroadcastReceiver a(Context context) {
        if (b == null) {
            c();
        }
        e eVar = new e();
        if (b == null) {
            c();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_BAD_REMOVAL");
        intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_REMOVED");
        intentFilter.addAction("android.intent.action.MEDIA_SHARED");
        intentFilter.addDataScheme("file");
        context.registerReceiver(eVar, intentFilter);
        return eVar;
    }

    public static String a(a aVar, String str, String str2, String str3) {
        return !aVar.d() ? str : aVar.b().toLowerCase(Locale.US).contains("usb") ? str3 : aVar.b().toLowerCase(Locale.US).contains("sd") ? str2 : String.valueOf(Character.toUpperCase(aVar.c().charAt(0))) + aVar.c().substring(1);
    }

    public static void a() {
        Iterator it = b.iterator();
        while (it.hasNext()) {
            ((a) it.next()).f();
        }
        a.f();
    }

    private static boolean a(String str) {
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(' ');
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(Environment.getRootDirectory().getAbsolutePath()) + "/etc/" + str), 2048);
            boolean z = true;
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    String.valueOf(str) + " gelesen; Geräte gefunden: " + b.size();
                    return true;
                }
                simpleStringSplitter.setString(readLine.trim());
                String next = simpleStringSplitter.next();
                if ("dev_mount".equals(next)) {
                    b bVar = new b(simpleStringSplitter);
                    if (TextUtils.equals(a.b(), bVar.b())) {
                        if (Build.VERSION.SDK_INT < 9) {
                            a.c = true;
                        }
                        while (simpleStringSplitter.hasNext()) {
                            if (simpleStringSplitter.next().contains("nonremovable")) {
                                a.c = false;
                                Log.w("Environment2", "isExternStorageRemovable overwrite ('nonremovable') auf false");
                            }
                        }
                        z = false;
                    } else {
                        b.add(bVar);
                    }
                } else if (z && "discard".equals(next)) {
                    simpleStringSplitter.next();
                    String next2 = simpleStringSplitter.next();
                    if ("disable".equals(next2)) {
                        a.c = false;
                        Log.w("Environment2", "isExternStorageRemovable overwrite ('discard=disable') auf false");
                    } else if ("enable".equals(next2)) {
                        Log.w("Environment2", "isExternStorageRemovable overwrite overwrite ('discard=enable'), bleibt auf " + a.c);
                    } else {
                        Log.w("Environment2", "disable-Eintrag unverständlich: " + next2);
                    }
                }
            }
        } catch (Exception e2) {
            "kann " + str + " nicht lesen: " + e2.getMessage();
            return false;
        }
    }

    public static a[] b() {
        boolean z;
        ArrayList arrayList = new ArrayList(b.size() + 2);
        if (a.e()) {
            arrayList.add(a);
        }
        Iterator it = b.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (aVar.e()) {
                String b2 = aVar.b();
                Iterator it2 = arrayList.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (b2.equals(((a) it2.next()).b())) {
                            z = true;
                            break;
                        }
                    } else {
                        z = false;
                        break;
                    }
                }
                if (!z) {
                    arrayList.add(aVar);
                }
            }
        }
        return (a[]) arrayList.toArray(new a[arrayList.size()]);
    }

    @TargetApi(11)
    private static void c() {
        b = new ArrayList(10);
        a = new c();
        if (!a("vold.fstab")) {
            a("vold.conf");
        }
        if (Build.VERSION.SDK_INT >= 11) {
            c = Environment.isExternalStorageEmulated();
        } else {
            c = false;
        }
        if (b.size() == 0) {
            e = null;
            return;
        }
        b bVar = (b) b.get(0);
        e = bVar;
        if (bVar.c().contains("usb")) {
            e = null;
            return;
        }
        e.c = "SD-Card";
        if (a.c) {
            Log.w("Environment2", "isExternStorageRemovable overwrite (secondary sd found) auf false");
        }
        a.c = false;
    }
}
