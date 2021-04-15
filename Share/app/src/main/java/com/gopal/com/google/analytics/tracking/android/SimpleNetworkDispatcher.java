package com.google.analytics.tracking.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;

class SimpleNetworkDispatcher implements Dispatcher {
    private final String a;
    private final HttpClientFactory b;
    private final Context c;

    SimpleNetworkDispatcher(HttpClientFactory httpClientFactory, Context context) {
        this(httpClientFactory, context, (byte) 0);
    }

    private SimpleNetworkDispatcher(HttpClientFactory httpClientFactory, Context context, byte b2) {
        this.c = context.getApplicationContext();
        this.a = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", new Object[]{"GoogleAnalytics", "2.0", Build.VERSION.RELEASE, Utils.a(Locale.getDefault()), Build.MODEL, Build.ID});
        this.b = httpClientFactory;
    }

    private static URL a(Hit hit) {
        if (TextUtils.isEmpty(hit.d())) {
            return null;
        }
        try {
            return new URL(hit.d());
        } catch (MalformedURLException e) {
            try {
                return new URL("http://www.google-analytics.com/collect");
            } catch (MalformedURLException e2) {
                return null;
            }
        }
    }

    private HttpEntityEnclosingRequest a(String str, String str2) {
        BasicHttpEntityEnclosingRequest basicHttpEntityEnclosingRequest;
        if (TextUtils.isEmpty(str)) {
            Log.f("Empty hit, discarding.");
            return null;
        }
        String str3 = str2 + "?" + str;
        if (str3.length() < 2036) {
            basicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("GET", str3);
        } else {
            basicHttpEntityEnclosingRequest = new BasicHttpEntityEnclosingRequest("POST", str2);
            try {
                basicHttpEntityEnclosingRequest.setEntity(new StringEntity(str));
            } catch (UnsupportedEncodingException e) {
                Log.f("Encoding error, discarding hit");
                return null;
            }
        }
        basicHttpEntityEnclosingRequest.addHeader("User-Agent", this.a);
        return basicHttpEntityEnclosingRequest;
    }

    private static void a(boolean z, HttpEntityEnclosingRequest httpEntityEnclosingRequest) {
        int available;
        if (z) {
            StringBuffer stringBuffer = new StringBuffer();
            for (Header obj : httpEntityEnclosingRequest.getAllHeaders()) {
                stringBuffer.append(obj.toString()).append("\n");
            }
            stringBuffer.append(httpEntityEnclosingRequest.getRequestLine().toString()).append("\n");
            if (httpEntityEnclosingRequest.getEntity() != null) {
                try {
                    InputStream content = httpEntityEnclosingRequest.getEntity().getContent();
                    if (content != null && (available = content.available()) > 0) {
                        byte[] bArr = new byte[available];
                        content.read(bArr);
                        stringBuffer.append("POST:\n");
                        stringBuffer.append(new String(bArr)).append("\n");
                    }
                } catch (IOException e) {
                    Log.f("Error Writing hit to log...");
                }
            }
            Log.c(stringBuffer.toString());
        }
    }

    public final int a(List list) {
        int min = Math.min(list.size(), 40);
        int i = 0;
        int i2 = 0;
        while (i < min) {
            HttpClient a2 = this.b.a();
            Hit hit = (Hit) list.get(i);
            URL a3 = a(hit);
            if (a3 != null) {
                HttpHost httpHost = new HttpHost(a3.getHost(), a3.getPort(), a3.getProtocol());
                String path = a3.getPath();
                String a4 = TextUtils.isEmpty(hit.a()) ? "" : HitBuilder.a(hit, System.currentTimeMillis());
                HttpEntityEnclosingRequest a5 = a(a4, path);
                if (a5 == null) {
                    continue;
                } else {
                    a5.addHeader("Host", httpHost.toHostString());
                    a(Log.a(), a5);
                    if (a4.length() > 8192) {
                        Log.f("Hit too long (> 8192 bytes)--not sent");
                    } else {
                        try {
                            HttpResponse execute = a2.execute(httpHost, a5);
                            if (execute.getStatusLine().getStatusCode() != 200) {
                                Log.f("Bad response: " + execute.getStatusLine().getStatusCode());
                                return i2;
                            }
                        } catch (ClientProtocolException e) {
                            Log.f("ClientProtocolException sending hit; discarding hit...");
                        } catch (IOException e2) {
                            Log.f("Exception sending hit: " + e2.getClass().getSimpleName());
                            Log.f(e2.getMessage());
                            return i2;
                        }
                    }
                }
            } else if (Log.a()) {
                Log.f("No destination: discarding hit: " + hit.a());
            } else {
                Log.f("No destination: discarding hit.");
            }
            i++;
            i2++;
        }
        return i2;
    }

    public final boolean a() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.c.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        Log.e("...no network connectivity");
        return false;
    }
}
