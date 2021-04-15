package com.google.analytics.tracking.android;

import android.content.Context;
import java.lang.Thread;
import java.util.ArrayList;

public class ExceptionReporter implements Thread.UncaughtExceptionHandler {
    private final Thread.UncaughtExceptionHandler a;
    private final Tracker b;
    private final ServiceManager c;
    private ExceptionParser d;

    public ExceptionReporter(Tracker tracker, ServiceManager serviceManager, Thread.UncaughtExceptionHandler uncaughtExceptionHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        } else if (serviceManager == null) {
            throw new NullPointerException("serviceManager cannot be null");
        } else {
            this.a = uncaughtExceptionHandler;
            this.b = tracker;
            this.c = serviceManager;
            this.d = new StandardExceptionParser(context, new ArrayList());
            Log.d("ExceptionReporter created, original handler is " + (uncaughtExceptionHandler == null ? "null" : uncaughtExceptionHandler.getClass().getName()));
        }
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String str = "UncaughtException";
        if (this.d != null) {
            str = this.d.a(thread != null ? thread.getName() : null, th);
        }
        Log.d("Tracking Exception: " + str);
        this.b.d(str);
        this.c.c();
        if (this.a != null) {
            Log.d("Passing exception to original handler.");
            this.a.uncaughtException(thread, th);
        }
    }
}
