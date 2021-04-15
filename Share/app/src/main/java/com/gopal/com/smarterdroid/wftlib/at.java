package com.smarterdroid.wftlib;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.File;
import java.util.LinkedList;

public final class at implements MediaScannerConnection.MediaScannerConnectionClient {
    private static final String[] d = {"_id", "_data"};
    private static LinkedList e = new LinkedList();
    private MediaScannerConnection a;
    private String b;
    private String c;

    @TargetApi(11)
    public static void a(Context context) {
        while (e != null && !e.isEmpty()) {
            try {
                if (Build.VERSION.SDK_INT < 17) {
                    e.clear();
                    context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                } else {
                    String[] strArr = (String[]) e.toArray(new String[e.size()]);
                    e.clear();
                    if (strArr != null && strArr.length != 0) {
                        StringBuffer stringBuffer = new StringBuffer(30000);
                        int length = strArr.length;
                        for (int i = 0; i < length; i++) {
                            if (stringBuffer.length() != 0) {
                                stringBuffer.append(" OR ");
                            }
                            stringBuffer.append("_data=?");
                        }
                        Cursor query = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), d, stringBuffer.toString(), strArr, (String) null);
                        query.moveToFirst();
                        while (!query.isAfterLast()) {
                            "removing " + query.getString(query.getColumnIndex("_data"));
                            context.getContentResolver().delete(ContentUris.withAppendedId(MediaStore.Files.getContentUri("external"), (long) query.getInt(query.getColumnIndex("_id"))), (String) null, (String[]) null);
                            query.moveToNext();
                        }
                        query.close();
                    } else {
                        return;
                    }
                }
            } catch (Exception e2) {
                return;
            }
        }
    }

    public static void a(Context context, File file) {
        try {
            if (file.isDirectory()) {
                "scanning directory " + file.getAbsolutePath() + " " + file.listFiles().length;
                for (File a2 : file.listFiles()) {
                    a(context, a2);
                }
                return;
            }
            "scanning " + file.getAbsolutePath();
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
        } catch (Exception e2) {
        }
    }

    @TargetApi(11)
    public static void a(Context context, File file, File file2) {
        if (Build.VERSION.SDK_INT < 17) {
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + Environment.getExternalStorageDirectory())));
            return;
        }
        String absolutePath = file.getAbsolutePath();
        String absolutePath2 = file2.getAbsolutePath();
        Cursor query = context.getContentResolver().query(MediaStore.Files.getContentUri("external"), d, "_data LIKE " + DatabaseUtils.sqlEscapeString(String.valueOf(absolutePath) + "%"), (String[]) null, (String) null);
        if (query.getCount() > 0) {
            query.moveToFirst();
            while (!query.isAfterLast()) {
                int i = query.getInt(query.getColumnIndex("_id"));
                String replace = query.getString(query.getColumnIndex("_data")).replace(absolutePath, absolutePath2);
                "updating " + query.getString(query.getColumnIndex("_data")) + " to " + replace;
                Uri withAppendedId = ContentUris.withAppendedId(MediaStore.Files.getContentUri("external"), (long) i);
                if (!replace.contains("/.")) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_data", replace);
                    "updated " + context.getContentResolver().update(withAppendedId, contentValues, (String) null, (String[]) null) + " rows ";
                } else {
                    "deleted " + context.getContentResolver().delete(withAppendedId, (String) null, (String[]) null) + " rows ";
                }
                query.moveToNext();
            }
        } else {
            a(context, file2);
        }
        query.close();
    }

    @TargetApi(4)
    public static void a(File file) {
        if (Build.VERSION.SDK_INT > 16 && e.size() > 500) {
            a(aj.h());
        }
        if (file.isDirectory()) {
            for (File a2 : file.listFiles()) {
                a(a2);
            }
            return;
        }
        e.add(file.getAbsolutePath());
    }

    public final void onMediaScannerConnected() {
        this.a.scanFile(this.b, this.c);
    }

    public final void onScanCompleted(String str, Uri uri) {
        this.a.disconnect();
    }
}
