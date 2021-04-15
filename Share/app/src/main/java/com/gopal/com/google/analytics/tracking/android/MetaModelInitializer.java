package com.google.analytics.tracking.android;

import com.google.analytics.tracking.android.MetaModel;
import java.text.DecimalFormat;

class MetaModelInitializer {
    private static final MetaModel.Formatter a = new MetaModel.Formatter() {
        public final String a(String str) {
            return Utils.c(str) ? "1" : "0";
        }
    };
    private static final MetaModel.Formatter b = new MetaModel.Formatter() {
        private final DecimalFormat a = new DecimalFormat("0.##");

        public final String a(String str) {
            return this.a.format(Utils.b(str));
        }
    };

    private MetaModelInitializer() {
    }

    public static void a(MetaModel metaModel) {
        metaModel.a("apiVersion", "v", (String) null, (MetaModel.Formatter) null);
        metaModel.a("libraryVersion", "_v", (String) null, (MetaModel.Formatter) null);
        metaModel.a("anonymizeIp", "aip", "0", a);
        metaModel.a("trackingId", "tid", (String) null, (MetaModel.Formatter) null);
        metaModel.a("hitType", "t", (String) null, (MetaModel.Formatter) null);
        metaModel.a("sessionControl", "sc", (String) null, (MetaModel.Formatter) null);
        metaModel.a("adSenseAdMobHitId", "a", (String) null, (MetaModel.Formatter) null);
        metaModel.a("usage", "_u", (String) null, (MetaModel.Formatter) null);
        metaModel.a("title", "dt", (String) null, (MetaModel.Formatter) null);
        metaModel.a("referrer", "dr", (String) null, (MetaModel.Formatter) null);
        metaModel.a("language", "ul", (String) null, (MetaModel.Formatter) null);
        metaModel.a("encoding", "de", (String) null, (MetaModel.Formatter) null);
        metaModel.a("page", "dp", (String) null, (MetaModel.Formatter) null);
        metaModel.a("screenColors", "sd", (String) null, (MetaModel.Formatter) null);
        metaModel.a("screenResolution", "sr", (String) null, (MetaModel.Formatter) null);
        metaModel.a("viewportSize", "vp", (String) null, (MetaModel.Formatter) null);
        metaModel.a("javaEnabled", "je", "1", a);
        metaModel.a("flashVersion", "fl", (String) null, (MetaModel.Formatter) null);
        metaModel.a("clientId", "cid", (String) null, (MetaModel.Formatter) null);
        metaModel.a("campaignName", "cn", (String) null, (MetaModel.Formatter) null);
        metaModel.a("campaignSource", "cs", (String) null, (MetaModel.Formatter) null);
        metaModel.a("campaignMedium", "cm", (String) null, (MetaModel.Formatter) null);
        metaModel.a("campaignKeyword", "ck", (String) null, (MetaModel.Formatter) null);
        metaModel.a("campaignContent", "cc", (String) null, (MetaModel.Formatter) null);
        metaModel.a("campaignId", "ci", (String) null, (MetaModel.Formatter) null);
        metaModel.a("gclid", "gclid", (String) null, (MetaModel.Formatter) null);
        metaModel.a("dclid", "dclid", (String) null, (MetaModel.Formatter) null);
        metaModel.a("gmob_t", "gmob_t", (String) null, (MetaModel.Formatter) null);
        metaModel.a("eventCategory", "ec", (String) null, (MetaModel.Formatter) null);
        metaModel.a("eventAction", "ea", (String) null, (MetaModel.Formatter) null);
        metaModel.a("eventLabel", "el", (String) null, (MetaModel.Formatter) null);
        metaModel.a("eventValue", "ev", (String) null, (MetaModel.Formatter) null);
        metaModel.a("nonInteraction", "ni", "0", a);
        metaModel.a("socialNetwork", "sn", (String) null, (MetaModel.Formatter) null);
        metaModel.a("socialAction", "sa", (String) null, (MetaModel.Formatter) null);
        metaModel.a("socialTarget", "st", (String) null, (MetaModel.Formatter) null);
        metaModel.a("appName", "an", (String) null, (MetaModel.Formatter) null);
        metaModel.a("appVersion", "av", (String) null, (MetaModel.Formatter) null);
        metaModel.a("description", "cd", (String) null, (MetaModel.Formatter) null);
        metaModel.a("appId", "aid", (String) null, (MetaModel.Formatter) null);
        metaModel.a("appInstallerId", "aiid", (String) null, (MetaModel.Formatter) null);
        metaModel.a("transactionId", "ti", (String) null, (MetaModel.Formatter) null);
        metaModel.a("transactionAffiliation", "ta", (String) null, (MetaModel.Formatter) null);
        metaModel.a("transactionShipping", "ts", (String) null, (MetaModel.Formatter) null);
        metaModel.a("transactionTotal", "tr", (String) null, (MetaModel.Formatter) null);
        metaModel.a("transactionTax", "tt", (String) null, (MetaModel.Formatter) null);
        metaModel.a("currencyCode", "cu", (String) null, (MetaModel.Formatter) null);
        metaModel.a("itemPrice", "ip", (String) null, (MetaModel.Formatter) null);
        metaModel.a("itemCode", "ic", (String) null, (MetaModel.Formatter) null);
        metaModel.a("itemName", "in", (String) null, (MetaModel.Formatter) null);
        metaModel.a("itemCategory", "iv", (String) null, (MetaModel.Formatter) null);
        metaModel.a("itemQuantity", "iq", (String) null, (MetaModel.Formatter) null);
        metaModel.a("exDescription", "exd", (String) null, (MetaModel.Formatter) null);
        metaModel.a("exFatal", "exf", "1", a);
        metaModel.a("timingVar", "utv", (String) null, (MetaModel.Formatter) null);
        metaModel.a("timingValue", "utt", (String) null, (MetaModel.Formatter) null);
        metaModel.a("timingCategory", "utc", (String) null, (MetaModel.Formatter) null);
        metaModel.a("timingLabel", "utl", (String) null, (MetaModel.Formatter) null);
        metaModel.a("sampleRate", "sf", "100", b);
        metaModel.a("hitTime", "ht", (String) null, (MetaModel.Formatter) null);
        metaModel.a("customDimension", "cd", (String) null, (MetaModel.Formatter) null);
        metaModel.a("customMetric", "cm", (String) null, (MetaModel.Formatter) null);
        metaModel.a("contentGrouping", "cg", (String) null, (MetaModel.Formatter) null);
    }
}
