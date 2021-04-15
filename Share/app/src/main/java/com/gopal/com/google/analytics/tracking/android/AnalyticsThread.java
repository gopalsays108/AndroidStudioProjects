package com.google.analytics.tracking.android;

import com.google.analytics.tracking.android.GoogleAnalytics;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

interface AnalyticsThread {

    public interface ClientIdCallback {
        void a(String str);
    }

    void a();

    void a(ClientIdCallback clientIdCallback);

    void a(GoogleAnalytics.AppOptOutCallback appOptOutCallback);

    void a(Map map);

    void a(boolean z);

    LinkedBlockingQueue b();

    Thread c();
}
