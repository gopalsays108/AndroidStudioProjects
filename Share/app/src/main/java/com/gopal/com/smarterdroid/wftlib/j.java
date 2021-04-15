package com.smarterdroid.wftlib;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import java.io.OutputStream;
import java.util.Properties;

public final class j implements a {
    private Properties a;
    private Context b;
    private String c;
    private String d;
    private String e = "image";

    public j(aj ajVar, Properties properties) {
        this.b = ajVar.g();
        this.a = properties;
        this.c = this.a.getProperty("image", "");
        this.d = this.a.getProperty("kind", "");
        this.e = this.a.getProperty("type", "image");
    }

    public final void a(OutputStream outputStream) {
        Cursor cursor;
        Cursor cursor2;
        try {
            ContentResolver contentResolver = this.b.getContentResolver();
            if (this.e.equals("video")) {
                Cursor query = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, (String[]) null, "_data = " + DatabaseUtils.sqlEscapeString(this.c), (String[]) null, (String) null);
                if (query != null) {
                    try {
                        if (query.getCount() > 0) {
                            query.moveToFirst();
                            int i = query.getInt(query.getColumnIndex("_id"));
                            Bitmap thumbnail = !this.d.equals("micro") ? MediaStore.Video.Thumbnails.getThumbnail(contentResolver, (long) i, 1, (BitmapFactory.Options) null) : null;
                            Bitmap thumbnail2 = thumbnail == null ? MediaStore.Video.Thumbnails.getThumbnail(contentResolver, (long) i, 3, (BitmapFactory.Options) null) : thumbnail;
                            if (thumbnail2 != null) {
                                thumbnail2.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                                thumbnail2.recycle();
                            }
                        }
                    } catch (Exception e2) {
                        cursor = cursor2;
                        if (cursor != null) {
                            cursor.close();
                            return;
                        }
                        return;
                    }
                }
                if (query != null) {
                    query.close();
                    return;
                }
                return;
            }
            Cursor query2 = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, (String[]) null, "_data = " + DatabaseUtils.sqlEscapeString(this.c), (String[]) null, (String) null);
            if (query2 != null) {
                if (query2.getCount() > 0) {
                    query2.moveToFirst();
                    int i2 = query2.getInt(query2.getColumnIndex("_id"));
                    Bitmap thumbnail3 = !this.d.equals("micro") ? MediaStore.Images.Thumbnails.getThumbnail(contentResolver, (long) i2, 1, (BitmapFactory.Options) null) : null;
                    Bitmap thumbnail4 = thumbnail3 == null ? MediaStore.Images.Thumbnails.getThumbnail(contentResolver, (long) i2, 3, (BitmapFactory.Options) null) : thumbnail3;
                    if (thumbnail4 != null) {
                        thumbnail4.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                        thumbnail4.recycle();
                    }
                }
            }
            if (query2 != null) {
                query2.close();
            }
        } catch (Exception e3) {
            cursor = null;
        }
    }
}
