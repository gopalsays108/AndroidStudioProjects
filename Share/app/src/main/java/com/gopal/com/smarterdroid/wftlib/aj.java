package com.smarterdroid.wftlib;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.text.format.Formatter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Enumeration;
import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public final class aj {
    public static int a = 1;
    public static boolean b = false;
    private static Context e = null;
    private static int h = 0;
    private static InetAddress l;
    private static boolean m;
    private StatFs c;
    private Context d;
    private WifiManager f;
    /* access modifiers changed from: private */
    public int g;
    private boolean i = true;
    private String j;
    private NetworkInterface k;

    static {
        boolean z = true;
        m = false;
        try {
            l = InetAddress.getByName("192.168.43.1");
        } catch (UnknownHostException e2) {
        }
        m = false;
        try {
            boolean z2 = false;
            boolean z3 = false;
            for (Method method : ((WifiManager) e.getSystemService("wifi")).getClass().getDeclaredMethods()) {
                if (method.getName().equals("getWifiApConfiguration")) {
                    z3 = true;
                }
                if (method.getName().equals("isWifiApEnabled")) {
                    z2 = true;
                }
            }
            if (!z3 || !z2) {
                z = false;
            }
            m = z;
        } catch (Exception e3) {
            m = false;
        }
    }

    public aj(Context context) {
        this.d = context;
        e = this.d;
        this.i = PreferenceManager.getDefaultSharedPreferences(this.d).getBoolean("emMode", false);
        this.c = new StatFs(Environment.getExternalStorageDirectory().getPath());
        if (!this.i) {
            this.f = (WifiManager) this.d.getSystemService("wifi");
        }
    }

    public static String a(boolean z) {
        return z ? "https" : "http";
    }

    public static void a(Context context) {
        e = context;
    }

    public static int b() {
        return a;
    }

    public static void c() {
        a = 366;
    }

    public static Context h() {
        return e;
    }

    private static String j() {
        String str = "0.0.0.0";
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                "intf " + nextElement.getDisplayName();
                Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress nextElement2 = inetAddresses.nextElement();
                    "intf " + nextElement2.getHostAddress();
                    if (!nextElement2.isLoopbackAddress() && nextElement2.getHostAddress().indexOf(":") < 0 && nextElement2.getHostAddress().endsWith(".1")) {
                        str = nextElement2.getHostAddress();
                    }
                }
            }
            return str;
        } catch (Exception e2) {
            return str;
        }
    }

    private boolean k() {
        boolean z;
        if (!m) {
            try {
                z = NetworkInterface.getByInetAddress(InetAddress.getByName("192.168.43.1")) != null;
            } catch (Exception e2) {
                z = false;
            }
            return z;
        }
        WifiManager wifiManager = (WifiManager) this.d.getSystemService("wifi");
        Method[] declaredMethods = wifiManager.getClass().getDeclaredMethods();
        int length = declaredMethods.length;
        int i2 = 0;
        while (i2 < length) {
            Method method = declaredMethods[i2];
            if (method.getName().equals("isWifiApEnabled")) {
                try {
                    return ((Boolean) method.invoke(wifiManager, new Object[0])).booleanValue();
                } catch (Exception e3) {
                    return false;
                }
            } else {
                i2++;
            }
        }
        return false;
    }

    private ServerSocketFactory l() {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            KeyManagerFactory instance2 = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            KeyStore instance3 = KeyStore.getInstance("bks");
            instance3.load(this.d.getAssets().open("wftfiles/wififiletransfer.bks"), "wftkey".toCharArray());
            instance2.init(instance3, "wftkey".toCharArray());
            instance.init(instance2.getKeyManagers(), (TrustManager[]) null, (SecureRandom) null);
            return instance.getServerSocketFactory();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final int a() {
        this.d.registerReceiver(new ak(this), new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        return this.g;
    }

    public final long a(String str) {
        this.c = new StatFs(str);
        return ((long) this.c.getBlockSize()) * ((long) this.c.getAvailableBlocks());
    }

    public final ServerSocket a(int i2, boolean z) {
        if (this.i) {
            try {
                return new ServerSocket(i2, 10);
            } catch (IOException e2) {
            }
        } else if (z) {
            try {
                return l().createServerSocket(i2, 10, InetAddress.getByName(e()));
            } catch (IOException e3) {
            }
        } else {
            try {
                return new ServerSocket(i2, 10, InetAddress.getByName(e()));
            } catch (IOException e4) {
            }
        }
        return null;
    }

    public final String d() {
        if (this.i) {
            return "Emulator";
        }
        if (!k()) {
            return this.f.getConnectionInfo().getSSID();
        }
        try {
            WifiManager wifiManager = (WifiManager) this.d.getSystemService("wifi");
            for (Method method : wifiManager.getClass().getDeclaredMethods()) {
                if (method.getName().equals("getWifiApConfiguration")) {
                    return ((WifiConfiguration) method.invoke(wifiManager, new Object[0])).SSID;
                }
            }
            return "Hotspot Mode";
        } catch (Exception e2) {
            return "Hotspot Mode";
        }
    }

    @TargetApi(3)
    public final String e() {
        boolean z = false;
        if (this.i) {
            return "127.0.0.1";
        }
        this.j = Formatter.formatIpAddress(this.f.getConnectionInfo().getIpAddress());
        if (this.j.equals("0.0.0.0")) {
            if (m) {
                try {
                    if (k()) {
                        this.j = j();
                    }
                } catch (Exception e2) {
                    this.j = "0.0.0.0";
                }
            } else {
                try {
                    this.k = NetworkInterface.getByInetAddress(l);
                    if (this.k != null) {
                        z = true;
                    }
                } catch (Exception e3) {
                }
                if (z) {
                    this.j = "192.168.43.1";
                } else {
                    try {
                        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
                        while (networkInterfaces.hasMoreElements()) {
                            NetworkInterface nextElement = networkInterfaces.nextElement();
                            "intf " + nextElement.getDisplayName();
                            Enumeration<InetAddress> inetAddresses = nextElement.getInetAddresses();
                            while (inetAddresses.hasMoreElements()) {
                                InetAddress nextElement2 = inetAddresses.nextElement();
                                "intf " + nextElement2.getHostAddress();
                                if (!nextElement2.isLoopbackAddress() && nextElement2.getHostAddress().indexOf(":") < 0 && nextElement2.getHostAddress().endsWith(".1")) {
                                    this.j = nextElement2.getHostAddress();
                                }
                            }
                        }
                    } catch (Exception e4) {
                    }
                }
            }
        }
        return this.j;
    }

    public final boolean f() {
        return this.i || this.f.getConnectionInfo().getSupplicantState() == SupplicantState.COMPLETED || k();
    }

    public final Context g() {
        return this.d;
    }

    public final boolean i() {
        if (h > 0) {
            return false;
        }
        h++;
        return PreferenceManager.getDefaultSharedPreferences(this.d).getBoolean("redirect", false);
    }
}
