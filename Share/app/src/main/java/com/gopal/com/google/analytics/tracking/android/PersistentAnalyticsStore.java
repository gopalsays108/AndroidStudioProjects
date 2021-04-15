package com.google.analytics.tracking.android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

class PersistentAnalyticsStore implements AnalyticsStore {
    /* access modifiers changed from: private */
    public static final String a = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", new Object[]{"hits2", "hit_id", "hit_time", "hit_url", "hit_string", "hit_app_id"});
    private final AnalyticsDatabaseHelper b;
    private volatile Dispatcher c;
    private final AnalyticsStoreStateListener d;
    /* access modifiers changed from: private */
    public final Context e;
    /* access modifiers changed from: private */
    public final String f;
    private long g;
    /* access modifiers changed from: private */
    public Clock h;

    class AnalyticsDatabaseHelper extends SQLiteOpenHelper {
        private boolean b;
        private long c = 0;

        AnalyticsDatabaseHelper(Context context, String str) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, 1);
        }

        /* JADX WARNING: Removed duplicated region for block: B:16:0x0044  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static boolean a(java.lang.String r10, android.database.sqlite.SQLiteDatabase r11) {
            /*
                r8 = 0
                r9 = 0
                java.lang.String r1 = "SQLITE_MASTER"
                r0 = 1
                java.lang.String[] r2 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0026, all -> 0x0041 }
                r0 = 0
                java.lang.String r3 = "name"
                r2[r0] = r3     // Catch:{ SQLiteException -> 0x0026, all -> 0x0041 }
                java.lang.String r3 = "name=?"
                r0 = 1
                java.lang.String[] r4 = new java.lang.String[r0]     // Catch:{ SQLiteException -> 0x0026, all -> 0x0041 }
                r0 = 0
                r4[r0] = r10     // Catch:{ SQLiteException -> 0x0026, all -> 0x0041 }
                r5 = 0
                r6 = 0
                r7 = 0
                r0 = r11
                android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ SQLiteException -> 0x0026, all -> 0x0041 }
                boolean r0 = r1.moveToFirst()     // Catch:{ SQLiteException -> 0x004f, all -> 0x0048 }
                if (r1 == 0) goto L_0x0025
                r1.close()
            L_0x0025:
                return r0
            L_0x0026:
                r0 = move-exception
                r0 = r9
            L_0x0028:
                java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x004b }
                java.lang.String r2 = "error querying for table "
                r1.<init>(r2)     // Catch:{ all -> 0x004b }
                java.lang.StringBuilder r1 = r1.append(r10)     // Catch:{ all -> 0x004b }
                java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x004b }
                com.google.analytics.tracking.android.Log.f(r1)     // Catch:{ all -> 0x004b }
                if (r0 == 0) goto L_0x003f
                r0.close()
            L_0x003f:
                r0 = r8
                goto L_0x0025
            L_0x0041:
                r0 = move-exception
            L_0x0042:
                if (r9 == 0) goto L_0x0047
                r9.close()
            L_0x0047:
                throw r0
            L_0x0048:
                r0 = move-exception
                r9 = r1
                goto L_0x0042
            L_0x004b:
                r1 = move-exception
                r9 = r0
                r0 = r1
                goto L_0x0042
            L_0x004f:
                r0 = move-exception
                r0 = r1
                goto L_0x0028
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.analytics.tracking.android.PersistentAnalyticsStore.AnalyticsDatabaseHelper.a(java.lang.String, android.database.sqlite.SQLiteDatabase):boolean");
        }

        public SQLiteDatabase getWritableDatabase() {
            if (!this.b || this.c + 3600000 <= PersistentAnalyticsStore.this.h.a()) {
                SQLiteDatabase sQLiteDatabase = null;
                this.b = true;
                this.c = PersistentAnalyticsStore.this.h.a();
                try {
                    sQLiteDatabase = super.getWritableDatabase();
                } catch (SQLiteException e) {
                    PersistentAnalyticsStore.this.e.getDatabasePath(PersistentAnalyticsStore.this.f).delete();
                }
                if (sQLiteDatabase == null) {
                    sQLiteDatabase = super.getWritableDatabase();
                }
                this.b = false;
                return sQLiteDatabase;
            }
            throw new SQLiteException("Database creation failed");
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            FutureApis.a(sQLiteDatabase.getPath());
        }

        /* JADX INFO: finally extract failed */
        public void onOpen(SQLiteDatabase sQLiteDatabase) {
            boolean z = false;
            if (Build.VERSION.SDK_INT < 15) {
                Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", (String[]) null);
                try {
                    rawQuery.moveToFirst();
                } finally {
                    rawQuery.close();
                }
            }
            if (!a("hits2", sQLiteDatabase)) {
                sQLiteDatabase.execSQL(PersistentAnalyticsStore.a);
                return;
            }
            Cursor rawQuery2 = sQLiteDatabase.rawQuery("SELECT * FROM hits2 WHERE 0", (String[]) null);
            HashSet hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery2.getColumnNames();
                for (String add : columnNames) {
                    hashSet.add(add);
                }
                rawQuery2.close();
                if (!hashSet.remove("hit_id") || !hashSet.remove("hit_url") || !hashSet.remove("hit_string") || !hashSet.remove("hit_time")) {
                    throw new SQLiteException("Database column missing");
                }
                if (!hashSet.remove("hit_app_id")) {
                    z = true;
                }
                if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                } else if (z) {
                    sQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id");
                }
            } catch (Throwable th) {
                rawQuery2.close();
                throw th;
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    PersistentAnalyticsStore(AnalyticsStoreStateListener analyticsStoreStateListener, Context context) {
        this(analyticsStoreStateListener, context, "google_analytics_v2.db");
    }

    private PersistentAnalyticsStore(AnalyticsStoreStateListener analyticsStoreStateListener, Context context, String str) {
        this.e = context.getApplicationContext();
        this.f = str;
        this.d = analyticsStoreStateListener;
        this.h = new Clock() {
            public final long a() {
                return System.currentTimeMillis();
            }
        };
        this.b = new AnalyticsDatabaseHelper(this.e, this.f);
        this.c = new SimpleNetworkDispatcher(new HttpClientFactory() {
            public final HttpClient a() {
                return new DefaultHttpClient();
            }
        }, this.e);
        this.g = 0;
    }

    private SQLiteDatabase a(String str) {
        try {
            return this.b.getWritableDatabase();
        } catch (SQLiteException e2) {
            Log.f(str);
            return null;
        }
    }

    private static String a(Map map) {
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry entry : map.entrySet()) {
            arrayList.add(((String) entry.getKey()) + "=" + HitBuilder.a((String) entry.getValue()));
        }
        return TextUtils.join("&", arrayList);
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0113  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0169  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.List a(int r16) {
        /*
            r15 = this;
            java.lang.String r1 = "Error opening database for peekHits"
            android.database.sqlite.SQLiteDatabase r1 = r15.a((java.lang.String) r1)
            if (r1 != 0) goto L_0x000e
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
        L_0x000d:
            return r1
        L_0x000e:
            r10 = 0
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.lang.String r2 = "hits2"
            r3 = 3
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x00ec, all -> 0x0110 }
            r4 = 0
            java.lang.String r5 = "hit_id"
            r3[r4] = r5     // Catch:{ SQLiteException -> 0x00ec, all -> 0x0110 }
            r4 = 1
            java.lang.String r5 = "hit_time"
            r3[r4] = r5     // Catch:{ SQLiteException -> 0x00ec, all -> 0x0110 }
            r4 = 2
            java.lang.String r5 = "hit_url"
            r3[r4] = r5     // Catch:{ SQLiteException -> 0x00ec, all -> 0x0110 }
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.String r8 = "%s ASC, %s ASC"
            r9 = 2
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ SQLiteException -> 0x00ec, all -> 0x0110 }
            r11 = 0
            java.lang.String r12 = "hit_url"
            r9[r11] = r12     // Catch:{ SQLiteException -> 0x00ec, all -> 0x0110 }
            r11 = 1
            java.lang.String r12 = "hit_id"
            r9[r11] = r12     // Catch:{ SQLiteException -> 0x00ec, all -> 0x0110 }
            java.lang.String r8 = java.lang.String.format(r8, r9)     // Catch:{ SQLiteException -> 0x00ec, all -> 0x0110 }
            java.lang.String r9 = java.lang.Integer.toString(r16)     // Catch:{ SQLiteException -> 0x00ec, all -> 0x0110 }
            android.database.Cursor r11 = r1.query(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLiteException -> 0x00ec, all -> 0x0110 }
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            r10.<init>()     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            boolean r2 = r11.moveToFirst()     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            if (r2 == 0) goto L_0x0072
        L_0x0052:
            com.google.analytics.tracking.android.Hit r2 = new com.google.analytics.tracking.android.Hit     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            r3 = 0
            long r3 = r11.getLong(r3)     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            r5 = 1
            long r5 = r11.getLong(r5)     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            r2.<init>(r3, r5)     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            r3 = 2
            java.lang.String r3 = r11.getString(r3)     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            r2.b(r3)     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            r10.add(r2)     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            boolean r2 = r11.moveToNext()     // Catch:{ SQLiteException -> 0x0180, all -> 0x017a }
            if (r2 != 0) goto L_0x0052
        L_0x0072:
            if (r11 == 0) goto L_0x0077
            r11.close()
        L_0x0077:
            r12 = 0
            java.lang.String r2 = "hits2"
            r3 = 2
            java.lang.String[] r3 = new java.lang.String[r3]     // Catch:{ SQLiteException -> 0x0178 }
            r4 = 0
            java.lang.String r5 = "hit_id"
            r3[r4] = r5     // Catch:{ SQLiteException -> 0x0178 }
            r4 = 1
            java.lang.String r5 = "hit_string"
            r3[r4] = r5     // Catch:{ SQLiteException -> 0x0178 }
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.String r8 = "%s ASC"
            r9 = 1
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ SQLiteException -> 0x0178 }
            r13 = 0
            java.lang.String r14 = "hit_id"
            r9[r13] = r14     // Catch:{ SQLiteException -> 0x0178 }
            java.lang.String r8 = java.lang.String.format(r8, r9)     // Catch:{ SQLiteException -> 0x0178 }
            java.lang.String r9 = java.lang.Integer.toString(r16)     // Catch:{ SQLiteException -> 0x0178 }
            android.database.Cursor r2 = r1.query(r2, r3, r4, r5, r6, r7, r8, r9)     // Catch:{ SQLiteException -> 0x0178 }
            boolean r1 = r2.moveToFirst()     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            if (r1 == 0) goto L_0x00e4
            r3 = r12
        L_0x00a8:
            boolean r1 = r2 instanceof android.database.sqlite.SQLiteCursor     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            if (r1 == 0) goto L_0x0117
            r0 = r2
            android.database.sqlite.SQLiteCursor r0 = (android.database.sqlite.SQLiteCursor) r0     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            r1 = r0
            android.database.CursorWindow r1 = r1.getWindow()     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            int r1 = r1.getNumRows()     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            if (r1 > 0) goto L_0x0117
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            java.lang.String r1 = "hitString for hitId "
            r4.<init>(r1)     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            java.lang.Object r1 = r10.get(r3)     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            com.google.analytics.tracking.android.Hit r1 = (com.google.analytics.tracking.android.Hit) r1     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            long r5 = r1.b()     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            java.lang.StringBuilder r1 = r4.append(r5)     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            java.lang.String r4 = " too large.  Hit will be deleted."
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            java.lang.String r1 = r1.toString()     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            com.google.analytics.tracking.android.Log.f(r1)     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
        L_0x00dc:
            int r1 = r3 + 1
            boolean r3 = r2.moveToNext()     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            if (r3 != 0) goto L_0x0184
        L_0x00e4:
            if (r2 == 0) goto L_0x00e9
            r2.close()
        L_0x00e9:
            r1 = r10
            goto L_0x000d
        L_0x00ec:
            r1 = move-exception
            r2 = r10
        L_0x00ee:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x017d }
            java.lang.String r4 = "error in peekHits fetching hitIds: "
            r3.<init>(r4)     // Catch:{ all -> 0x017d }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x017d }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ all -> 0x017d }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x017d }
            com.google.analytics.tracking.android.Log.f(r1)     // Catch:{ all -> 0x017d }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x017d }
            r1.<init>()     // Catch:{ all -> 0x017d }
            if (r2 == 0) goto L_0x000d
            r2.close()
            goto L_0x000d
        L_0x0110:
            r1 = move-exception
        L_0x0111:
            if (r10 == 0) goto L_0x0116
            r10.close()
        L_0x0116:
            throw r1
        L_0x0117:
            java.lang.Object r1 = r10.get(r3)     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            com.google.analytics.tracking.android.Hit r1 = (com.google.analytics.tracking.android.Hit) r1     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            r4 = 1
            java.lang.String r4 = r2.getString(r4)     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            r1.a(r4)     // Catch:{ SQLiteException -> 0x0126, all -> 0x0175 }
            goto L_0x00dc
        L_0x0126:
            r1 = move-exception
            r11 = r2
        L_0x0128:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0166 }
            java.lang.String r3 = "error in peekHits fetching hitString: "
            r2.<init>(r3)     // Catch:{ all -> 0x0166 }
            java.lang.String r1 = r1.getMessage()     // Catch:{ all -> 0x0166 }
            java.lang.StringBuilder r1 = r2.append(r1)     // Catch:{ all -> 0x0166 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0166 }
            com.google.analytics.tracking.android.Log.f(r1)     // Catch:{ all -> 0x0166 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0166 }
            r2.<init>()     // Catch:{ all -> 0x0166 }
            r1 = 0
            java.util.Iterator r4 = r10.iterator()     // Catch:{ all -> 0x0166 }
            r3 = r1
        L_0x0149:
            boolean r1 = r4.hasNext()     // Catch:{ all -> 0x0166 }
            if (r1 == 0) goto L_0x016d
            java.lang.Object r1 = r4.next()     // Catch:{ all -> 0x0166 }
            com.google.analytics.tracking.android.Hit r1 = (com.google.analytics.tracking.android.Hit) r1     // Catch:{ all -> 0x0166 }
            java.lang.String r5 = r1.a()     // Catch:{ all -> 0x0166 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ all -> 0x0166 }
            if (r5 == 0) goto L_0x0162
            if (r3 != 0) goto L_0x016d
            r3 = 1
        L_0x0162:
            r2.add(r1)     // Catch:{ all -> 0x0166 }
            goto L_0x0149
        L_0x0166:
            r1 = move-exception
        L_0x0167:
            if (r11 == 0) goto L_0x016c
            r11.close()
        L_0x016c:
            throw r1
        L_0x016d:
            if (r11 == 0) goto L_0x0172
            r11.close()
        L_0x0172:
            r1 = r2
            goto L_0x000d
        L_0x0175:
            r1 = move-exception
            r11 = r2
            goto L_0x0167
        L_0x0178:
            r1 = move-exception
            goto L_0x0128
        L_0x017a:
            r1 = move-exception
            r10 = r11
            goto L_0x0111
        L_0x017d:
            r1 = move-exception
            r10 = r2
            goto L_0x0111
        L_0x0180:
            r1 = move-exception
            r2 = r11
            goto L_0x00ee
        L_0x0184:
            r3 = r1
            goto L_0x00a8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.analytics.tracking.android.PersistentAnalyticsStore.a(int):java.util.List");
    }

    private void a(Collection collection) {
        SQLiteDatabase a2;
        boolean z = false;
        if (collection == null) {
            throw new NullPointerException("hits cannot be null");
        } else if (!collection.isEmpty() && (a2 = a("Error opening database for deleteHit")) != null) {
            String[] strArr = new String[collection.size()];
            String format = String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))});
            Iterator it = collection.iterator();
            int i = 0;
            while (it.hasNext()) {
                strArr[i] = Long.toString(((Hit) it.next()).b());
                i++;
            }
            try {
                a2.delete("hits2", format, strArr);
                AnalyticsStoreStateListener analyticsStoreStateListener = this.d;
                if (d() == 0) {
                    z = true;
                }
                analyticsStoreStateListener.a(z);
            } catch (SQLiteException e2) {
                Log.f("Error deleting hit " + collection);
            }
        }
    }

    private int d() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase a2 = a("Error opening database for requestNumHitsPending");
        if (a2 != null) {
            try {
                Cursor rawQuery = a2.rawQuery("SELECT COUNT(*) from hits2", (String[]) null);
                if (rawQuery.moveToFirst()) {
                    i = (int) rawQuery.getLong(0);
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
            } catch (SQLiteException e2) {
                Log.f("Error getting numStoredHits");
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
        return i;
    }

    public final void a() {
        boolean z = true;
        SQLiteDatabase a2 = a("Error opening database for clearHits");
        if (a2 != null) {
            if (0 == 0) {
                a2.delete("hits2", (String) null, (String[]) null);
            } else {
                Long l = 0L;
                a2.delete("hits2", "hit_app_id = ?", new String[]{l.toString()});
            }
            AnalyticsStoreStateListener analyticsStoreStateListener = this.d;
            if (d() != 0) {
                z = false;
            }
            analyticsStoreStateListener.a(z);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00e4  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ff  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.util.Map r9, long r10, java.lang.String r12, java.util.Collection r13) {
        /*
            r8 = this;
            r0 = 1
            r1 = 0
            com.google.analytics.tracking.android.Clock r2 = r8.h
            long r2 = r2.a()
            long r4 = r8.g
            r6 = 86400000(0x5265c00, double:4.2687272E-316)
            long r4 = r4 + r6
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x0042
            r8.g = r2
            java.lang.String r2 = "Error opening database for deleteStaleHits"
            android.database.sqlite.SQLiteDatabase r2 = r8.a((java.lang.String) r2)
            if (r2 == 0) goto L_0x0042
            com.google.analytics.tracking.android.Clock r3 = r8.h
            long r3 = r3.a()
            r5 = 2592000000(0x9a7ec800, double:1.280618154E-314)
            long r3 = r3 - r5
            java.lang.String r5 = "hits2"
            java.lang.String r6 = "HIT_TIME < ?"
            java.lang.String[] r7 = new java.lang.String[r0]
            java.lang.String r3 = java.lang.Long.toString(r3)
            r7[r1] = r3
            r2.delete(r5, r6, r7)
            com.google.analytics.tracking.android.AnalyticsStoreStateListener r2 = r8.d
            int r3 = r8.d()
            if (r3 != 0) goto L_0x00ea
        L_0x003f:
            r2.a(r0)
        L_0x0042:
            java.util.Iterator r1 = r13.iterator()
        L_0x0046:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x006f
            java.lang.Object r0 = r1.next()
            com.google.android.gms.analytics.internal.Command r0 = (com.google.android.gms.analytics.internal.Command) r0
            java.lang.String r2 = r0.a()
            java.lang.String r3 = "appendVersion"
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x0046
            java.lang.String r1 = r0.c()
            java.lang.String r2 = r0.b()
            if (r1 != 0) goto L_0x00ed
            java.lang.String r0 = ""
        L_0x006a:
            if (r2 == 0) goto L_0x006f
            r9.put(r2, r0)
        L_0x006f:
            int r0 = r8.d()
            int r0 = r0 + -2000
            int r0 = r0 + 1
            if (r0 <= 0) goto L_0x009c
            java.util.List r0 = r8.a((int) r0)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Store full, deleting "
            r1.<init>(r2)
            int r2 = r0.size()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = " hits to make room"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            com.google.analytics.tracking.android.Log.g(r1)
            r8.a((java.util.Collection) r0)
        L_0x009c:
            java.lang.String r0 = "Error opening database for putHit"
            android.database.sqlite.SQLiteDatabase r3 = r8.a((java.lang.String) r0)
            if (r3 == 0) goto L_0x00e9
            android.content.ContentValues r4 = new android.content.ContentValues
            r4.<init>()
            java.lang.String r0 = "hit_string"
            java.lang.String r1 = a((java.util.Map) r9)
            r4.put(r0, r1)
            java.lang.String r0 = "hit_time"
            java.lang.Long r1 = java.lang.Long.valueOf(r10)
            r4.put(r0, r1)
            r1 = 0
            java.lang.String r0 = "AppUID"
            boolean r0 = r9.containsKey(r0)
            if (r0 == 0) goto L_0x00fd
            java.lang.String r0 = "AppUID"
            java.lang.Object r0 = r9.get(r0)     // Catch:{ NumberFormatException -> 0x00fc }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ NumberFormatException -> 0x00fc }
            long r0 = java.lang.Long.parseLong(r0)     // Catch:{ NumberFormatException -> 0x00fc }
        L_0x00d1:
            java.lang.String r2 = "hit_app_id"
            java.lang.Long r0 = java.lang.Long.valueOf(r0)
            r4.put(r2, r0)
            if (r12 != 0) goto L_0x00de
            java.lang.String r12 = "http://www.google-analytics.com/collect"
        L_0x00de:
            int r0 = r12.length()
            if (r0 != 0) goto L_0x00ff
            java.lang.String r0 = "empty path: not sending hit"
            com.google.analytics.tracking.android.Log.f(r0)
        L_0x00e9:
            return
        L_0x00ea:
            r0 = r1
            goto L_0x003f
        L_0x00ed:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            goto L_0x006a
        L_0x00fc:
            r0 = move-exception
        L_0x00fd:
            r0 = r1
            goto L_0x00d1
        L_0x00ff:
            java.lang.String r0 = "hit_url"
            r4.put(r0, r12)
            java.lang.String r0 = "hits2"
            r1 = 0
            r3.insert(r0, r1, r4)     // Catch:{ SQLiteException -> 0x0111 }
            com.google.analytics.tracking.android.AnalyticsStoreStateListener r0 = r8.d     // Catch:{ SQLiteException -> 0x0111 }
            r1 = 0
            r0.a(r1)     // Catch:{ SQLiteException -> 0x0111 }
            goto L_0x00e9
        L_0x0111:
            r0 = move-exception
            java.lang.String r0 = "Error storing hit"
            com.google.analytics.tracking.android.Log.f(r0)
            goto L_0x00e9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.analytics.tracking.android.PersistentAnalyticsStore.a(java.util.Map, long, java.lang.String, java.util.Collection):void");
    }

    public final void b() {
        Log.e("dispatch running...");
        if (this.c.a()) {
            List a2 = a(40);
            if (a2.isEmpty()) {
                Log.e("...nothing to dispatch");
                this.d.a(true);
                return;
            }
            int a3 = this.c.a(a2);
            Log.e("sent " + a3 + " of " + a2.size() + " hits");
            a((Collection) a2.subList(0, Math.min(a3, a2.size())));
            if (a3 == a2.size() && d() > 0) {
                GAServiceManager.a().c();
            }
        }
    }
}
