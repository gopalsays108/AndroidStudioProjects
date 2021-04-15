package com.smarterdroid.wftlib;

import java.io.File;
import java.io.FilenameFilter;

public final class c implements FilenameFilter {
    final /* synthetic */ b a;

    public c(b bVar) {
        this.a = bVar;
    }

    public final boolean accept(File file, String str) {
        return str.startsWith("DeleteMe");
    }
}
