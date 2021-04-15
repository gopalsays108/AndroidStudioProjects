package com.google.analytics.tracking.android;

import java.util.HashMap;
import java.util.Map;

class MetaModel {
    private Map a = new HashMap();

    public interface Formatter {
        String a(String str);
    }

    public class MetaInfo {
        private final String a;
        private final String b;
        private final Formatter c;

        public MetaInfo(String str, String str2, Formatter formatter) {
            this.a = str;
            this.b = str2;
            this.c = formatter;
        }

        public final String a() {
            return this.b;
        }

        public final String a(String str) {
            if (!str.contains("*")) {
                return this.a;
            }
            String str2 = this.a;
            String[] split = str.split("\\*");
            if (split.length <= 1) {
                return null;
            }
            try {
                return str2 + Integer.parseInt(split[1]);
            } catch (NumberFormatException e) {
                Log.f("Unable to parse slot for url parameter " + str2);
                return null;
            }
        }

        public final Formatter b() {
            return this.c;
        }
    }

    MetaModel() {
    }

    /* access modifiers changed from: package-private */
    public final MetaInfo a(String str) {
        if (str.startsWith("&")) {
            return new MetaInfo(str.substring(1), (String) null, (Formatter) null);
        }
        if (str.contains("*")) {
            str = str.substring(0, str.indexOf("*"));
        }
        return (MetaInfo) this.a.get(str);
    }

    public final void a(String str, String str2, String str3, Formatter formatter) {
        this.a.put(str, new MetaInfo(str2, str3, formatter));
    }
}
