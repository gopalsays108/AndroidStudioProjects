package com.google.analytics.tracking.android;

import android.app.IntentService;
import android.content.Intent;
import java.io.FileOutputStream;
import java.io.IOException;

public final class CampaignTrackingService extends IntentService {
    public CampaignTrackingService() {
        super("CampaignIntentService");
    }

    public CampaignTrackingService(String str) {
        super(str);
    }

    /* access modifiers changed from: protected */
    public final void onHandleIntent(Intent intent) {
        String stringExtra = intent.getStringExtra("referrer");
        try {
            FileOutputStream openFileOutput = openFileOutput("gaInstallData", 0);
            openFileOutput.write(stringExtra.getBytes());
            openFileOutput.close();
        } catch (IOException e) {
            Log.b("Error storing install campaign.");
        }
    }
}
