package com.google.analytics.tracking.android;

import java.util.SortedSet;
import java.util.TreeSet;

class GAUsage {
    private static final GAUsage d = new GAUsage();
    private SortedSet a = new TreeSet();
    private StringBuilder b = new StringBuilder();
    private boolean c = false;

    public enum Field {
        TRACK_VIEW,
        TRACK_VIEW_WITH_APPSCREEN,
        TRACK_EVENT,
        TRACK_TRANSACTION,
        TRACK_EXCEPTION_WITH_DESCRIPTION,
        TRACK_EXCEPTION_WITH_THROWABLE,
        BLANK_06,
        TRACK_TIMING,
        TRACK_SOCIAL,
        GET,
        SET,
        SEND,
        SET_START_SESSION,
        BLANK_13,
        SET_APP_NAME,
        BLANK_15,
        SET_APP_VERSION,
        BLANK_17,
        SET_APP_SCREEN,
        GET_TRACKING_ID,
        SET_ANONYMIZE_IP,
        GET_ANONYMIZE_IP,
        SET_SAMPLE_RATE,
        GET_SAMPLE_RATE,
        SET_USE_SECURE,
        GET_USE_SECURE,
        SET_REFERRER,
        SET_CAMPAIGN,
        SET_APP_ID,
        GET_APP_ID,
        SET_EXCEPTION_PARSER,
        GET_EXCEPTION_PARSER,
        CONSTRUCT_TRANSACTION,
        CONSTRUCT_EXCEPTION,
        CONSTRUCT_RAW_EXCEPTION,
        CONSTRUCT_TIMING,
        CONSTRUCT_SOCIAL,
        SET_DEBUG,
        GET_DEBUG,
        GET_TRACKER,
        GET_DEFAULT_TRACKER,
        SET_DEFAULT_TRACKER,
        SET_APP_OPT_OUT,
        REQUEST_APP_OPT_OUT,
        DISPATCH,
        SET_DISPATCH_PERIOD,
        BLANK_48,
        REPORT_UNCAUGHT_EXCEPTIONS,
        SET_AUTO_ACTIVITY_TRACKING,
        SET_SESSION_TIMEOUT,
        CONSTRUCT_EVENT,
        CONSTRUCT_ITEM,
        SET_APP_INSTALLER_ID,
        GET_APP_INSTALLER_ID
    }

    private GAUsage() {
    }

    public static GAUsage a() {
        return d;
    }

    public final synchronized void a(Field field) {
        if (!this.c) {
            this.a.add(field);
            this.b.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".charAt(field.ordinal()));
        }
    }

    public final synchronized void a(boolean z) {
        this.c = z;
    }

    public final synchronized String b() {
        StringBuilder sb;
        sb = new StringBuilder();
        int i = 6;
        int i2 = 0;
        while (this.a.size() > 0) {
            Field field = (Field) this.a.first();
            this.a.remove(field);
            int ordinal = field.ordinal();
            while (ordinal >= i) {
                sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".charAt(i2));
                i += 6;
                i2 = 0;
            }
            i2 += 1 << (field.ordinal() % 6);
        }
        if (i2 > 0 || sb.length() == 0) {
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".charAt(i2));
        }
        this.a.clear();
        return sb.toString();
    }

    public final synchronized String c() {
        String sb;
        if (this.b.length() > 0) {
            this.b.insert(0, ".");
        }
        sb = this.b.toString();
        this.b = new StringBuilder();
        return sb;
    }
}
