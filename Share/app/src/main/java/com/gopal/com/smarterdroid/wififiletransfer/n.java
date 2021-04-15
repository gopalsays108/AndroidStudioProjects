package com.smarterdroid.wififiletransfer;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import com.smarterdroid.wififiletransfer.help.HelpScreen;
import java.util.Timer;

final class n implements View.OnClickListener {
    final /* synthetic */ WFTPanel a;

    n(WFTPanel wFTPanel) {
        this.a = wFTPanel;
    }

    public final void onClick(View view) {
        switch (view.getId()) {
            case C0000R.id.button_start /*2131361798*/:
                if (!this.a.g) {
                    this.a.g = true;
                    if (!this.a.f.isChecked()) {
                        this.a.c();
                    } else if (WFTPanel.c(this.a)) {
                        this.a.showDialog(6);
                        this.a.f.setChecked(false);
                    } else if (!this.a.d.f() || this.a.d.e().equals("0.0.0.0")) {
                        this.a.showDialog(2);
                        this.a.f.setChecked(false);
                    } else if (Environment.getExternalStorageState().equals("mounted") || Environment.getExternalStorageState().equals("mounted_ro")) {
                        this.a.b(true);
                    } else {
                        this.a.showDialog(3);
                        this.a.f.setChecked(false);
                    }
                    new Timer().schedule(new v(this.a), 1000);
                    return;
                }
                this.a.f.toggle();
                return;
            case C0000R.id.textview_url /*2131361800*/:
                if (WFTService.d() == 4001) {
                    this.a.showDialog(1);
                    return;
                }
                return;
            case C0000R.id.button_settings /*2131361802*/:
                this.a.startActivity(new Intent(this.a.e, WFTPreferences.class));
                return;
            case C0000R.id.button_help /*2131361803*/:
                this.a.startActivity(new Intent(this.a.e, HelpScreen.class));
                return;
            case C0000R.id.button_upgrade /*2131361804*/:
                if (WFTService.g()) {
                    try {
                        this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.smarterdroid.wififiletransferpro&referrer=utm_source%3Dacer%26utm_medium%3Dupgbtn")));
                        return;
                    } catch (Exception e) {
                        return;
                    }
                } else {
                    try {
                        this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.smarterdroid.wififiletransferpro&referrer=utm_source%3Dfree%26utm_medium%3Dupgbtn")));
                        return;
                    } catch (Exception e2) {
                        return;
                    }
                }
            default:
                return;
        }
    }
}
