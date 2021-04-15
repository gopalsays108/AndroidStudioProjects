package com.smarterdroid.wififiletransfer.help;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.smarterdroid.wftlib.aw;
import com.smarterdroid.wififiletransfer.C0000R;

public class HelpScreen extends ListActivity {
    static final String[] a = {"Connection problems", "How to use WiFi File Transfer", "Frequently asked questions", "Get email support"};

    private String a(int i) {
        return getApplicationContext().getString(i);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().setFormat(1);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(C0000R.layout.help_main);
        setListAdapter(new ArrayAdapter(this, C0000R.layout.list_item, new String[]{a(C0000R.string.connectionproblems), a(C0000R.string.howtousewft), a(C0000R.string.frequentlyaskedquestions), a(C0000R.string.getemailsupport), a(C0000R.string.privacy)}));
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new a(this));
        aw.a(getApplicationContext(), "Help");
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int i) {
        switch (i) {
            case 201:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(a(C0000R.string.pleaseemailany));
                builder.setTitle(a(C0000R.string.appname));
                builder.setIcon(C0000R.drawable.ic_wft2_m);
                builder.setPositiveButton(a(C0000R.string.ok), new b(this));
                return builder.create();
            case 202:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setMessage(a(C0000R.string.thisappusesanalytics));
                builder2.setTitle(a(C0000R.string.appname));
                builder2.setIcon(C0000R.drawable.ic_wft2_m);
                builder2.setPositiveButton(a(C0000R.string.ok), new c(this));
                return builder2.create();
            default:
                return null;
        }
    }
}
