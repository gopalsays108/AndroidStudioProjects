package com.smarterdroid.wififiletransfer;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class WifiReceiver extends BroadcastReceiver {
    @TargetApi(14)
    public void onReceive(Context context, Intent intent) {
        try {
            if (intent.getAction().equals("android.net.wifi.supplicant.CONNECTION_CHANGE")) {
                if (!intent.getBooleanExtra("connected", false) && WFTService.e()) {
                    context.stopService(new Intent(context, WFTService.class));
                }
            } else if (intent.getAction().equals("android.net.wifi.STATE_CHANGE") && ((NetworkInfo) intent.getParcelableExtra("networkInfo")).isConnected()) {
                String string = PreferenceManager.getDefaultSharedPreferences(context).getString("autostartSSID", "autostartoff");
                WifiInfo connectionInfo = Build.VERSION.SDK_INT >= 14 ? (WifiInfo) intent.getParcelableExtra("wifiInfo") : ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
                String str = (connectionInfo == null || connectionInfo.getSSID().equals("")) ? "" : String.valueOf(connectionInfo.getSSID().replaceAll("^\"|\"$", "")) + "||" + connectionInfo.getNetworkId();
                "saved: " + string + ", current: " + str;
                if (string.equals(str)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("action", 2006);
                    Intent intent2 = new Intent(context, WFTService.class);
                    intent2.putExtras(bundle);
                    context.startService(intent2);
                }
                "connected to " + intent.getStringExtra("bssid");
            }
        } catch (Exception e) {
        }
    }
}
