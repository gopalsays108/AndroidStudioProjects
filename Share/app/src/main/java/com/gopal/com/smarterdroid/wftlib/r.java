package com.smarterdroid.wftlib;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import java.io.File;
import java.util.ArrayList;

final class r implements AdapterView.OnItemClickListener {
    final /* synthetic */ DirectoryPicker a;
    private final /* synthetic */ ArrayList b;

    r(DirectoryPicker directoryPicker, ArrayList arrayList) {
        this.a = directoryPicker;
        this.b = arrayList;
    }

    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        if (((File) this.b.get(i)).isDirectory()) {
            String absolutePath = ((File) this.b.get(i)).getAbsolutePath();
            Intent intent = new Intent(this.a, DirectoryPicker.class);
            intent.putExtra("startDir", absolutePath);
            intent.putExtra("showHidden", this.a.b);
            intent.putExtra("onlyDirs", this.a.c);
            this.a.startActivityForResult(intent, 43522432);
        }
    }
}
