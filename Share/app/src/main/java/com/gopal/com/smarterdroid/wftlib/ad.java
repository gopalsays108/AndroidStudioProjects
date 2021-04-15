package com.smarterdroid.wftlib;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import java.util.Locale;

public final class ad {
    private static int[] a = {com.smarterdroid.wftlib.z.js_progressOverall, com.smarterdroid.wftlib.z.js_currentTitle, z.js_currentFile, z.js_currentProgress, z.js_fileName, z.js_remove, z.js_removeTitle, z.js_fileError, z.js_duplicate, z.js_sizeLimitMin, z.js_sizeLimitMax, z.js_fileListMax, z.js_fileListSizeMax, z.js_httpStatus, z.js_securityError, z.js_ioError, z.js_delAlltxt, z.js_confirmDeltxt1, z.js_WFT_ok, z.js_WFT_cancel, z.js_confirmInstalltxt1, z.js_confirmInstalltxt, z.js_confirmRentxt1, z.js_confirmCopytxt1, z.js_confirmUnziptxt, z.js_confirmZiptxt1, z.js_makeDirtxt1, z.js_noFilesSelectedtxt1, z.js_WFT_download, z.js_WFT_delete, z.js_WFT_zip, z.js_WFT_move, z.js_WFT_install, z.js_WFT_copy, z.js_WFT_rename, z.js_WFT_unzip, z.js_downAlltxt1, z.js_WFT_individual_files, z.js_WFT_one_zip_archive, z.js_zipAlltxt1, z.js_moveAlltxt1, z.js_WFT_MacMSG, z.js_WFT_FlashMSG, z.js_WFT_Uploading, z.js_uploadFailed, z.js_pl_selectfiles, z.js_pl_selectfolder, z.js_pl_addfilestoqueue, z.js_pl_filename, z.js_pl_status, z.js_pl_size, z.js_pl_addfiles, z.js_pl_stopcurrentupload, z.js_pl_startuploadingqueue, z.js_pl_uploadedfiles, z.js_pl_na, z.js_pl_dragfileshere, z.js_pl_fileextensionerror, z.js_pl_filesizerror, z.js_pl_initerror, z.js_pl_httperror, z.js_pl_securityerror, z.js_pl_genericerror, z.js_pl_ioerror, z.js_pl_stopupload, z.js_pl_startupload, z.js_pl_filesqueued, z.js_pl_yourbrowserdoesnotsupport};

    public static String a(Context context) {
        StringBuffer stringBuffer = new StringBuffer(4000);
        stringBuffer.append("jQuery(document).ready(function(){i18n_dict = {\r\n");
        int[] iArr = a;
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            int i2 = iArr[i];
            StringBuilder sb = new StringBuilder("'");
            Configuration configuration = new Configuration(context.getResources().getConfiguration());
            Locale locale = configuration.locale;
            configuration.locale = new Locale("en_US");
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            String string = new Resources(context.getAssets(), displayMetrics, configuration).getString(i2);
            configuration.locale = locale;
            new Resources(context.getAssets(), displayMetrics, configuration);
            stringBuffer.append(sb.append(a(string)).append("' : '").append((i2 != z.js_sizeLimitMax || !aj.b) ? a(context.getString(i2)) : a(context.getString(i2).replace("<a href=\"https://market.android.com/details?id=com.smarterdroid.wififiletransferpro\">WiFi File Transfer Pro</a>", "<a href=\"#\" onClick=\"return confirmUpgrade();\">WiFi File Transfer Pro</a>"))).append("',\r\n").toString());
        }
        stringBuffer.append("};jQuery.i18n.setDictionary(i18n_dict);});");
        return stringBuffer.toString();
    }

    private static String a(String str) {
        return str.contains("'") ? str.replaceAll("'", "\\\\'") : str;
    }
}
