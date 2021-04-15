package com.google.analytics.tracking.android;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class StandardExceptionParser implements ExceptionParser {
    private final TreeSet a = new TreeSet();

    public StandardExceptionParser(Context context, Collection collection) {
        a(context, collection);
    }

    private static Throwable a(Throwable th) {
        while (th.getCause() != null) {
            th = th.getCause();
        }
        return th;
    }

    private void a(Context context, Collection collection) {
        this.a.clear();
        HashSet<String> hashSet = new HashSet<>();
        if (collection != null) {
            hashSet.addAll(collection);
        }
        if (context != null) {
            try {
                String packageName = context.getApplicationContext().getPackageName();
                this.a.add(packageName);
                ActivityInfo[] activityInfoArr = context.getApplicationContext().getPackageManager().getPackageInfo(packageName, 15).activities;
                if (activityInfoArr != null) {
                    for (ActivityInfo activityInfo : activityInfoArr) {
                        hashSet.add(activityInfo.packageName);
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.c("No package found");
            }
        }
        for (String str : hashSet) {
            Iterator it = this.a.iterator();
            boolean z = true;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                String str2 = (String) it.next();
                if (str.startsWith(str2)) {
                    z = false;
                } else if (str2.startsWith(str)) {
                    this.a.remove(str2);
                }
            }
            if (z) {
                this.a.add(str);
            }
        }
    }

    public final String a(String str, Throwable th) {
        StackTraceElement stackTraceElement;
        Throwable a2 = a(th);
        StackTraceElement[] stackTrace = a(th).getStackTrace();
        if (stackTrace != null && stackTrace.length != 0) {
            int length = stackTrace.length;
            int i = 0;
            loop0:
            while (true) {
                if (i >= length) {
                    stackTraceElement = stackTrace[0];
                    break;
                }
                StackTraceElement stackTraceElement2 = stackTrace[i];
                String className = stackTraceElement2.getClassName();
                Iterator it = this.a.iterator();
                while (it.hasNext()) {
                    if (className.startsWith((String) it.next())) {
                        stackTraceElement = stackTraceElement2;
                        break loop0;
                    }
                }
                i++;
            }
        } else {
            stackTraceElement = null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(a2.getClass().getSimpleName());
        if (stackTraceElement != null) {
            String[] split = stackTraceElement.getClassName().split("\\.");
            String str2 = "unknown";
            if (split != null && split.length > 0) {
                str2 = split[split.length - 1];
            }
            sb.append(String.format(" (@%s:%s:%s)", new Object[]{str2, stackTraceElement.getMethodName(), Integer.valueOf(stackTraceElement.getLineNumber())}));
        }
        if (str != null) {
            sb.append(String.format(" {%s}", new Object[]{str}));
        }
        return sb.toString();
    }
}
