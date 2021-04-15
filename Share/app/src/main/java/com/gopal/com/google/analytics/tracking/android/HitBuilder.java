package com.google.analytics.tracking.android;

import com.google.analytics.tracking.android.MetaModel;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

class HitBuilder {
    HitBuilder() {
    }

    static String a(Hit hit, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append(hit.a());
        if (hit.c() > 0) {
            long c = j - hit.c();
            if (c >= 0) {
                sb.append("&qt=").append(c);
            }
        }
        sb.append("&z=").append(hit.b());
        return sb.toString();
    }

    static String a(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("URL encoding failed for: " + str);
        }
    }

    static Map a(MetaModel metaModel, Map map) {
        String a;
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            MetaModel.MetaInfo a2 = metaModel.a((String) entry.getKey());
            if (!(a2 == null || (a = a2.a((String) entry.getKey())) == null)) {
                String str = (String) entry.getValue();
                if (a2.b() != null) {
                    str = a2.b().a(str);
                }
                if (str != null && !str.equals(a2.a())) {
                    hashMap.put(a, str);
                }
            }
        }
        return hashMap;
    }
}
