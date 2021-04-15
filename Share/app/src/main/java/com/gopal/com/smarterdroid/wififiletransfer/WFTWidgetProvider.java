package com.smarterdroid.wififiletransfer;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class WFTWidgetProvider extends AppWidgetProvider {
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if ("com.smarterdroid.wififiletransferpro.WIDGET_CLICK".equals(action)) {
            int d = WFTService.d();
            "Widget clicked, Status " + d;
            if (d > 4002) {
                Bundle bundle = new Bundle();
                bundle.putInt("action", 2003);
                Intent intent2 = new Intent(context, WFTService.class);
                intent2.putExtras(bundle);
                context.startService(intent2);
            } else if (!context.stopService(new Intent(context, WFTService.class))) {
                WFTService.f();
            }
            AppWidgetManager instance = AppWidgetManager.getInstance(context);
            onUpdate(context, instance, instance.getAppWidgetIds(new ComponentName(context, WFTWidgetProvider.class)));
        } else if ("com.smarterdroid.wififiletransferpro.UI_UPDATE".equals(action)) {
            AppWidgetManager instance2 = AppWidgetManager.getInstance(context);
            onUpdate(context, instance2, instance2.getAppWidgetIds(new ComponentName(context, WFTWidgetProvider.class)));
        } else {
            super.onReceive(context, intent);
        }
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] iArr) {
        for (int i : appWidgetManager.getAppWidgetIds(new ComponentName(context, WFTWidgetProvider.class))) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), C0000R.layout.widgetlayout);
            int d = WFTService.d();
            if (d <= 4002) {
                switch (d) {
                    case 4000:
                        remoteViews.setTextViewText(C0000R.id.urltext, context.getString(C0000R.string.nowificonnection));
                        break;
                    case 4001:
                        remoteViews.setTextViewText(C0000R.id.urltext, WFTService.a());
                        break;
                    case 4005:
                        remoteViews.setTextViewText(C0000R.id.urltext, context.getString(C0000R.string.licensecheckfailed));
                        break;
                }
                remoteViews.setImageViewResource(C0000R.id.widget_image, C0000R.drawable.ic_wft2_h);
            } else {
                remoteViews.setTextViewText(C0000R.id.urltext, "");
                remoteViews.setImageViewResource(C0000R.id.widget_image, C0000R.drawable.ic_wft2_h_off);
            }
            Intent intent = new Intent(context, WFTWidgetProvider.class);
            intent.setAction("com.smarterdroid.wififiletransferpro.WIDGET_CLICK");
            intent.putExtra("appWidgetIds", iArr);
            remoteViews.setOnClickPendingIntent(C0000R.id.widget_image, PendingIntent.getBroadcast(context, 0, intent, 134217728));
            appWidgetManager.updateAppWidget(i, remoteViews);
        }
    }
}
