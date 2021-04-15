package com.smarterdroid.wftlib;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.smarterdroid.wftlib.a.a;
import com.smarterdroid.wftlib.a.d;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class DirectoryPicker extends ListActivity {
    /* access modifiers changed from: private */
    public File a;
    /* access modifiers changed from: private */
    public boolean b = false;
    /* access modifiers changed from: private */
    public boolean c = true;

    private ArrayList a() {
        a[] b2 = d.b();
        ArrayList arrayList = new ArrayList();
        for (a b3 : b2) {
            File file = new File(b3.b());
            if ((!this.c || file.isDirectory()) && (this.b || !file.isHidden())) {
                arrayList.add(file);
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private static ArrayList a(File[] fileArr, boolean z, boolean z2) {
        ArrayList arrayList = new ArrayList();
        for (File file : fileArr) {
            if ((!z || file.isDirectory()) && (z2 || !file.isHidden())) {
                arrayList.add(file);
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        Intent intent = new Intent();
        intent.putExtra("chosenDir", str);
        setResult(-1, intent);
        finish();
    }

    private static String[] a(ArrayList arrayList) {
        String[] strArr = new String[arrayList.size()];
        int i = 0;
        Iterator it = arrayList.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return strArr;
            }
            strArr[i2] = ((File) it.next()).getName();
            i = i2 + 1;
        }
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 43522432 && i2 == -1) {
            a((String) intent.getExtras().get("chosenDir"));
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        this.a = new File("/Storage Devices:");
        if (extras != null) {
            String string = extras.getString("startDir");
            this.b = extras.getBoolean("showHidden", false);
            this.c = extras.getBoolean("onlyDirs", true);
            if (string != null) {
                File file = new File(string);
                if (file.isDirectory()) {
                    this.a = file;
                }
            }
        }
        setContentView(y.chooser_list);
        setTitle(this.a.getAbsolutePath());
        Button button = (Button) findViewById(x.btnChoose);
        String name = this.a.getName();
        if (name.length() == 0) {
            name = "/";
        }
        button.setText("Choose '" + name + "'");
        button.setOnClickListener(new p(this));
        ListView listView = getListView();
        listView.setTextFilterEnabled(true);
        if (this.a.getAbsolutePath().equalsIgnoreCase("/Storage Devices:")) {
            button.setEnabled(false);
            ArrayList a2 = a();
            setListAdapter(new ArrayAdapter(this, y.list_item, a(a2)));
            listView.setOnItemClickListener(new q(this, a2));
            return;
        }
        button.setEnabled(true);
        if (!this.a.canRead()) {
            Toast.makeText(getApplicationContext(), "Could not read folder contents.", 1).show();
            return;
        }
        ArrayList a3 = a(this.a.listFiles(), this.c, this.b);
        setListAdapter(new ArrayAdapter(this, y.list_item, a(a3)));
        listView.setOnItemClickListener(new r(this, a3));
    }
}
