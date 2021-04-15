package com.smarterdroid.wififiletransfer;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.smarterdroid.wftlib.aj;

public class WFTPanel extends Activity {
    public static boolean a = false;
    private TextView b;
    private View.OnClickListener c;
    /* access modifiers changed from: private */
    public aj d;
    /* access modifiers changed from: private */
    public Context e;
    /* access modifiers changed from: private */
    public ToggleButton f;
    /* access modifiers changed from: private */
    public boolean g = false;
    private boolean h = true;
    private TextView i;
    private TextView j;
    private TextView k;
    private BroadcastReceiver l = new d(this);
    private boolean m = false;

    /* access modifiers changed from: private */
    public String a(int i2) {
        return getApplicationContext().getString(i2);
    }

    private void a() {
        if (d()) {
            switch (WFTService.d()) {
                case 4000:
                    this.b.setText(a((int) C0000R.string.nowificonnection));
                    a(true);
                    break;
                case 4001:
                    if (WFTService.c()) {
                        this.b.setText(String.valueOf(WFTService.a()) + "\n" + a((int) C0000R.string.or) + "\n" + WFTService.b());
                    } else {
                        this.b.setText(WFTService.a());
                    }
                    a(true);
                    if (this.h && this.m) {
                        this.m = false;
                        showDialog(0);
                        break;
                    }
            }
            this.f.setChecked(true);
            return;
        }
        this.b.setText(a((int) C0000R.string.pressstart));
        a(false);
        this.f.setChecked(false);
    }

    private void a(boolean z) {
        if (z) {
            this.j.setVisibility(8);
            this.i.setVisibility(0);
            this.b.setVisibility(0);
            this.k.setVisibility(4);
            b().post(new o(this));
            return;
        }
        this.j.setVisibility(0);
        this.i.setVisibility(8);
        this.b.setVisibility(4);
        this.k.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public ScrollView b() {
        return (ScrollView) findViewById(C0000R.id.scrollview_main);
    }

    private void b(Intent intent) {
        if (intent.getAction() != null) {
            String action = intent.getAction();
            if (action.equals("com.smarterdroid.wififiletransferpro.LICENSE_ERROR")) {
                try {
                    dismissDialog(0);
                } catch (Exception e2) {
                }
                showDialog(5);
                c();
            } else if (action.equals("com.smarterdroid.wififiletransferpro.SDCARD_NOT_WRITEABLE")) {
                showDialog(5);
                c();
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void b(boolean z) {
        this.f.setChecked(true);
        this.m = z;
        if (!d()) {
            Bundle bundle = new Bundle();
            bundle.putInt("action", 2005);
            Intent intent = new Intent(this, WFTService.class);
            intent.putExtras(bundle);
            startService(intent);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void c() {
        this.f.setChecked(false);
        if (d()) {
            stopService(new Intent(this, WFTService.class));
        }
    }

    static /* synthetic */ boolean c(WFTPanel wFTPanel) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) wFTPanel.getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if ("com.smarterdroid.wififiletransfernone.WFTService".equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private boolean d() {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if ("com.smarterdroid.wififiletransfer.WFTService".equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final void a(Intent intent) {
        int intExtra = intent.getIntExtra("message", 3000);
        "processIntent: " + intExtra;
        switch (intExtra) {
            case 3001:
                a();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 43522432 && i3 == -1) {
            intent.getExtras().get("chosenDir");
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    public void onCreate(Bundle bundle) {
        int i2;
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(C0000R.layout.main);
        this.h = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("showStartDialog", true);
        this.e = getApplicationContext();
        this.d = new aj(this.e);
        this.f = (ToggleButton) findViewById(C0000R.id.button_start);
        this.k = (TextView) findViewById(C0000R.id.spacer);
        this.j = (TextView) findViewById(C0000R.id.textview_pressstart);
        this.i = (TextView) findViewById(C0000R.id.textview_url_label);
        this.b = (TextView) findViewById(C0000R.id.textview_url);
        this.c = new n(this);
        this.f.setOnClickListener(this.c);
        this.b.setOnClickListener(this.c);
        ((Button) findViewById(C0000R.id.button_upgrade)).setOnClickListener(this.c);
        ((Button) findViewById(C0000R.id.button_help)).setOnClickListener(this.c);
        ((Button) findViewById(C0000R.id.button_settings)).setOnClickListener(this.c);
        aj.a(getApplicationContext());
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try {
            i2 = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e2) {
            i2 = 0;
        }
        if (defaultSharedPreferences.getInt("changeLog", i2) < i2) {
            showDialog(8);
        }
        if (i2 > 0) {
            SharedPreferences.Editor edit = defaultSharedPreferences.edit();
            edit.putInt("changeLog", i2);
            edit.commit();
        }
        getWindow().addFlags(128);
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int i2) {
        switch (i2) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(String.valueOf(a((int) C0000R.string.youcannowaccess)) + "\n\n" + WFTService.a() + "\n\n" + a((int) C0000R.string.willruninbackground));
                builder.setTitle(a((int) C0000R.string.appname));
                builder.setIcon(C0000R.drawable.ic_wft2_m);
                builder.setPositiveButton(a((int) C0000R.string.ok), new q(this));
                return builder.create();
            case 1:
                if (WFTService.d() != 4001) {
                    return null;
                }
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setTitle(a((int) C0000R.string.sendlinkvia));
                builder2.setIcon(C0000R.drawable.ic_wft2_m);
                builder2.setItems(new CharSequence[]{"Email"}, new p(this));
                return builder2.create();
            case 2:
                AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                builder3.setMessage(String.valueOf(a((int) C0000R.string.wifinotconnected)) + "\n\n" + a((int) C0000R.string.openwifistettings));
                builder3.setTitle(a((int) C0000R.string.appname));
                builder3.setIcon(C0000R.drawable.ic_wft2_m);
                builder3.setPositiveButton(a((int) C0000R.string.ok), new e(this));
                builder3.setNegativeButton(a((int) C0000R.string.cancel), new f(this));
                return builder3.create();
            case 3:
                AlertDialog.Builder builder4 = new AlertDialog.Builder(this);
                builder4.setMessage(String.valueOf(a((int) C0000R.string.memorycardnotreadable)) + "\n\n" + a((int) C0000R.string.ifyourphoneis));
                builder4.setTitle(a((int) C0000R.string.appname));
                builder4.setIcon(C0000R.drawable.ic_wft2_m);
                builder4.setPositiveButton(a((int) C0000R.string.ok), new g(this));
                return builder4.create();
            case 4:
                AlertDialog.Builder builder5 = new AlertDialog.Builder(this);
                builder5.setMessage("It is recommended that you uninstall the free version of WiFi File Transfer before proceeding. Would you like to do this now?");
                builder5.setTitle(a((int) C0000R.string.appname));
                builder5.setIcon(C0000R.drawable.ic_wft2_m);
                builder5.setNegativeButton("No", new h(this));
                builder5.setPositiveButton("Yes", new i(this));
                return builder5.create();
            case 5:
                AlertDialog.Builder builder6 = new AlertDialog.Builder(this);
                builder6.setMessage(a((int) C0000R.string.licensecheckfailed));
                builder6.setTitle(a((int) C0000R.string.appname));
                builder6.setIcon(C0000R.drawable.ic_wft2_m);
                builder6.setNegativeButton(a((int) C0000R.string.cancel), new j(this));
                builder6.setNeutralButton(a((int) C0000R.string.buyapp), new k(this));
                builder6.setPositiveButton(a((int) C0000R.string.retry), new l(this));
                return builder6.create();
            case 6:
                AlertDialog.Builder builder7 = new AlertDialog.Builder(this);
                builder7.setMessage(String.valueOf(a((int) C0000R.string.freeversionrunning)) + "\n\n" + a((int) C0000R.string.needstobestopped));
                builder7.setTitle(a((int) C0000R.string.appname));
                builder7.setIcon(C0000R.drawable.ic_wft2_m);
                builder7.setNegativeButton(a((int) C0000R.string.no), new r(this));
                builder7.setPositiveButton(a((int) C0000R.string.yes), new s(this));
                return builder7.create();
            case 7:
                AlertDialog.Builder builder8 = new AlertDialog.Builder(this);
                builder8.setMessage(a((int) C0000R.string.pleaseemailany));
                builder8.setTitle(a((int) C0000R.string.appname));
                builder8.setIcon(C0000R.drawable.ic_wft2_m);
                builder8.setPositiveButton(a((int) C0000R.string.ok), new t(this));
                return builder8.create();
            case 8:
                AlertDialog.Builder builder9 = new AlertDialog.Builder(this);
                builder9.setMessage("Version 1.0.9:\n\n-Deleting files fixed for Android 4.4\n-Translation update\n\nVersion 1.0.8:\n\n-Fixed problem with Select Files button in Internet Explorer\n-Added SSL encryption for use on public networks\n-Social media buttons now optional\n-More reliable hotspot detection\n-Fixed progress bars for folder upload\n-Fixed duplicate filename bug for folder upload\n-Corrected Google Analytics description in settings\n\nVersion 1.0.7:\n\n-Improved browser compatibility\n-Reduced installed app size\n-Added Google Analytics support\n\nVersion 1.0.6:\n\n-Increased upload file size limit to 5 MB\n-Added folder upload (Google Chrome only)\n-App should now work reliably when device is in hotspot mode\n-Added home screen and lock screen widgets\n-Added option to start app automatically when on home wifi\n-Revised app interface\n-Added option to display folder size\n-Better detection of external SD cards and USB storage devices\n-Improved download speeds when transferring folders/multiple files\n-Added Jelly Bean notifications\n-Added drag and drop uploading\n-Downloading folders or multiple files in a ZIP file should now work in all mobile browsers\n-Much quicker page loading in folders with a large number of images/videos\n-Added device and folder name to website title\n-Fixed encoding issue with Google Chrome\n-Added Korean language\n-Added Turkish language\n");
                builder9.setTitle(a((int) C0000R.string.whatsnew));
                builder9.setIcon(C0000R.drawable.ic_wft2_m);
                builder9.setPositiveButton(a((int) C0000R.string.ok), new u(this));
                return builder9.create();
            default:
                return null;
        }
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onNewIntent(Intent intent) {
        b(intent);
    }

    /* access modifiers changed from: protected */
    public void onPrepareDialog(int i2, Dialog dialog) {
        switch (i2) {
            case 0:
                ((AlertDialog) dialog).setMessage(String.valueOf(a((int) C0000R.string.youcannowaccess)) + "\n\n" + WFTService.a() + "\n\n" + a((int) C0000R.string.willruninbackground));
                return;
            default:
                return;
        }
    }

    public void onResume() {
        super.onResume();
        b(getIntent());
    }

    public void onStart() {
        super.onStart();
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.h = defaultSharedPreferences.getBoolean("showStartDialog", true);
        if (defaultSharedPreferences.getString("port", "").equals("")) {
            SharedPreferences.Editor edit = defaultSharedPreferences.edit();
            edit.putString("port", "1234");
            edit.commit();
        }
        if (defaultSharedPreferences.getString("sslport", "").equals("")) {
            SharedPreferences.Editor edit2 = defaultSharedPreferences.edit();
            edit2.putString("sslport", "2345");
            edit2.commit();
        }
        boolean z = defaultSharedPreferences.getBoolean("googleAnalytics", false);
        GoogleAnalytics a2 = GoogleAnalytics.a((Context) this);
        a2.a((GoogleAnalytics.AppOptOutCallback) new m(this));
        a2.b(z);
        if (a) {
            a = false;
            if (d()) {
                c();
                b(false);
            }
        }
        a();
        registerReceiver(this.l, new IntentFilter("com.smarterdroid.wififiletransfer.MSG_EVENT"));
        EasyTracker.a().a((Activity) this);
    }

    public void onStop() {
        EasyTracker.a().b(this);
        try {
            unregisterReceiver(this.l);
        } catch (Exception e2) {
        }
        super.onStop();
    }
}
