package com.smarterdroid.wififiletransfer.help;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import com.smarterdroid.wftlib.aw;

final class a implements AdapterView.OnItemClickListener {
    final /* synthetic */ HelpScreen a;

    a(HelpScreen helpScreen) {
        this.a = helpScreen;
    }

    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        switch (i) {
            case 0:
                aw.a(this.a.getApplicationContext(), "Help - Connection Problems");
                try {
                    this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.smarterdroid.com/index.php/wifi-file-transfer/troubleshooting#connectionproblems")));
                    return;
                } catch (Exception e) {
                    return;
                }
            case 1:
                aw.a(this.a.getApplicationContext(), "Help - Using WFT");
                try {
                    this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.smarterdroid.com/index.php/wifi-file-transfer/using-wifi-file-transfer")));
                    return;
                } catch (Exception e2) {
                    return;
                }
            case 2:
                aw.a(this.a.getApplicationContext(), "Help - FAQ/Troubleshooting");
                try {
                    this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.smarterdroid.com/index.php/wifi-file-transfer/troubleshooting")));
                    return;
                } catch (Exception e3) {
                    return;
                }
            case 3:
                aw.a(this.a.getApplicationContext(), "Help - Email");
                this.a.showDialog(201);
                return;
            case 4:
                aw.a(this.a.getApplicationContext(), "Help - Privacy");
                this.a.showDialog(202);
                return;
            default:
                return;
        }
    }
}
