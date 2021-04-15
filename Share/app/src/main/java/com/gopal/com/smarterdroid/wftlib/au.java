package com.smarterdroid.wftlib;

import android.content.Context;
import java.io.File;
import java.util.Properties;
import java.util.Stack;

public final class au {
    private Context a;
    private boolean b;

    public au(Context context) {
        this.b = false;
        this.b = false;
        this.a = context;
    }

    private static File a(File file) {
        File file2;
        int i = 1;
        String absolutePath = file.getAbsolutePath();
        String str = "";
        String name = file.getName();
        if (name.lastIndexOf(".") > 0 && name.length() - name.lastIndexOf(".") > 0) {
            str = "." + name.substring(name.lastIndexOf(".") + 1, name.length());
        }
        String substring = absolutePath.substring(0, absolutePath.length() - str.length());
        do {
            file2 = new File(String.valueOf(substring) + "(" + Integer.toString(i) + ")" + str);
            i++;
            if (!file2.exists()) {
                break;
            }
        } while (i < 64000);
        if (i < 64000) {
            return file2;
        }
        return null;
    }

    private static Stack a(Properties properties) {
        Stack stack = new Stack();
        for (String str : properties.keySet()) {
            if (!str.equals("action") && !str.startsWith("data_") && !str.startsWith("button_") && !str.equals("selectall1") && !str.equals("selectall2")) {
                stack.push(str);
            }
        }
        return stack;
    }

    private boolean a(long j) {
        return this.b || j <= 5242880;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00be A[SYNTHETIC, Splitter:B:59:0x00be] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00c3 A[SYNTHETIC, Splitter:B:62:0x00c3] */
    /* JADX WARNING: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.io.File r8, java.io.File r9) {
        /*
            r7 = this;
            r3 = 0
            r1 = 1
            r0 = 0
            boolean r2 = r8.isDirectory()
            if (r2 == 0) goto L_0x002c
            java.io.File[] r2 = r8.listFiles()
            r9.mkdirs()
            java.lang.String r3 = r9.getAbsolutePath()
        L_0x0014:
            int r4 = r2.length
            if (r0 < r4) goto L_0x0019
            r0 = r1
        L_0x0018:
            return r0
        L_0x0019:
            r4 = r2[r0]
            java.io.File r5 = new java.io.File
            r6 = r2[r0]
            java.lang.String r6 = r6.getName()
            r5.<init>(r3, r6)
            r7.a(r4, r5)
            int r0 = r0 + 1
            goto L_0x0014
        L_0x002c:
            java.io.File r2 = r9.getParentFile()
            r2.mkdirs()
            boolean r2 = r9.exists()
            if (r2 != 0) goto L_0x0018
            java.io.File r2 = r9.getParentFile()
            boolean r2 = r2.canWrite()
            if (r2 == 0) goto L_0x0018
            boolean r2 = r8.canRead()
            if (r2 == 0) goto L_0x0018
            java.io.File r2 = new java.io.File
            java.lang.String r4 = r9.getParent()
            r2.<init>(r4)
        L_0x0052:
            boolean r4 = r2.exists()
            if (r4 == 0) goto L_0x009c
            r9.delete()
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x00d6, all -> 0x00ba }
            r2.<init>(r8)     // Catch:{ IOException -> 0x00d6, all -> 0x00ba }
        L_0x0060:
            boolean r4 = r9.exists()     // Catch:{ IOException -> 0x00a4, all -> 0x00d1 }
            if (r4 == 0) goto L_0x00a0
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00a4, all -> 0x00d1 }
            r4.<init>(r9)     // Catch:{ IOException -> 0x00a4, all -> 0x00d1 }
            r3 = 8192(0x2000, float:1.14794E-41)
            byte[] r3 = new byte[r3]     // Catch:{ IOException -> 0x00b7, all -> 0x00d3 }
        L_0x006f:
            int r5 = r2.read(r3)     // Catch:{ IOException -> 0x00b7, all -> 0x00d3 }
            r6 = -1
            if (r5 != r6) goto L_0x00b2
            r4.close()     // Catch:{ IOException -> 0x00cd }
        L_0x0079:
            r2.close()     // Catch:{ IOException -> 0x00cf }
        L_0x007c:
            boolean r2 = r9.exists()
            if (r2 == 0) goto L_0x0018
            boolean r2 = r9.isFile()
            if (r2 == 0) goto L_0x0094
            long r2 = r9.length()
            long r4 = r8.length()
            int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r2 != 0) goto L_0x0018
        L_0x0094:
            android.content.Context r0 = r7.a
            com.smarterdroid.wftlib.at.a(r0, r9)
            r0 = r1
            goto L_0x0018
        L_0x009c:
            r9.mkdirs()
            goto L_0x0052
        L_0x00a0:
            r9.createNewFile()     // Catch:{ IOException -> 0x00a4, all -> 0x00d1 }
            goto L_0x0060
        L_0x00a4:
            r4 = move-exception
        L_0x00a5:
            if (r3 == 0) goto L_0x00aa
            r3.close()     // Catch:{ IOException -> 0x00c7 }
        L_0x00aa:
            if (r2 == 0) goto L_0x007c
            r2.close()     // Catch:{ IOException -> 0x00b0 }
            goto L_0x007c
        L_0x00b0:
            r2 = move-exception
            goto L_0x007c
        L_0x00b2:
            r6 = 0
            r4.write(r3, r6, r5)     // Catch:{ IOException -> 0x00b7, all -> 0x00d3 }
            goto L_0x006f
        L_0x00b7:
            r3 = move-exception
            r3 = r4
            goto L_0x00a5
        L_0x00ba:
            r0 = move-exception
            r2 = r3
        L_0x00bc:
            if (r3 == 0) goto L_0x00c1
            r3.close()     // Catch:{ IOException -> 0x00c9 }
        L_0x00c1:
            if (r2 == 0) goto L_0x00c6
            r2.close()     // Catch:{ IOException -> 0x00cb }
        L_0x00c6:
            throw r0
        L_0x00c7:
            r3 = move-exception
            goto L_0x00aa
        L_0x00c9:
            r1 = move-exception
            goto L_0x00c1
        L_0x00cb:
            r1 = move-exception
            goto L_0x00c6
        L_0x00cd:
            r3 = move-exception
            goto L_0x0079
        L_0x00cf:
            r2 = move-exception
            goto L_0x007c
        L_0x00d1:
            r0 = move-exception
            goto L_0x00bc
        L_0x00d3:
            r0 = move-exception
            r3 = r4
            goto L_0x00bc
        L_0x00d6:
            r2 = move-exception
            r2 = r3
            goto L_0x00a5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.smarterdroid.wftlib.au.a(java.io.File, java.io.File):boolean");
    }

    private boolean a(File file, File file2, boolean z) {
        boolean z2;
        if (!z) {
            File file3 = new File(file2, file.getName());
            boolean renameTo = (file3.exists() || !file.canWrite()) ? false : file.renameTo(file3);
            if (!renameTo) {
                return renameTo;
            }
            String.valueOf(file.getAbsolutePath()) + " renamed to " + file3.getAbsolutePath();
            try {
                at.a(this.a, file, file3);
                return renameTo;
            } catch (Exception e) {
                return renameTo;
            }
        } else {
            if (file.isFile()) {
                z2 = a(file, new File(file2, file.getName()));
            } else if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                File file4 = new File(file2, file.getName());
                file4.mkdirs();
                z2 = true;
                for (File a2 : listFiles) {
                    if (!a(a2, file4, z)) {
                        z2 = false;
                    }
                }
            } else {
                z2 = false;
            }
            if (!z2 || !file.delete()) {
                return false;
            }
            at.a(file);
            return true;
        }
    }

    private boolean b(File file) {
        if (file.isDirectory()) {
            c(file);
            return !file.exists();
        } else if (!file.delete()) {
            return false;
        } else {
            at.a(file);
            return true;
        }
    }

    private void c(File file) {
        String[] list = file.list();
        for (String file2 : list) {
            File file3 = new File(file, file2);
            if (file3.isDirectory()) {
                c(file3);
            } else if (file3.delete()) {
                at.a(file3);
            }
        }
        file.delete();
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 283 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(java.util.LinkedList r13, java.util.Properties r14, java.util.Properties r15) {
        /*
            r12 = this;
            r9 = 268435456(0x10000000, float:2.5243549E-29)
            r4 = 1
            r2 = 0
            java.util.Hashtable r0 = new java.util.Hashtable
            r0.<init>()
            java.lang.String r1 = "delete"
            r3 = 31
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "rename"
            r3 = 32
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "copy"
            r3 = 33
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "unzip"
            r3 = 34
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "zip"
            r3 = 35
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "mkdir"
            r3 = 36
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "multidelete"
            r3 = 37
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "multizip"
            r3 = 38
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "multimove"
            r3 = 39
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "install"
            r3 = 40
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "upgrade"
            r3 = 41
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.put(r1, r3)
            java.lang.String r1 = "action"
            boolean r1 = r14.containsKey(r1)
            if (r1 == 0) goto L_0x0560
            java.lang.String r1 = "action"
            java.lang.String r1 = r14.getProperty(r1)
            if (r1 == 0) goto L_0x0560
            java.lang.String r1 = "action"
            java.lang.String r1 = r14.getProperty(r1)
            int r1 = r1.length()
            if (r1 <= 0) goto L_0x0560
            java.lang.String r1 = "action"
            java.lang.String r1 = r14.getProperty(r1)
            boolean r1 = r0.containsKey(r1)
            if (r1 == 0) goto L_0x0560
            java.lang.String r1 = "action"
            java.lang.String r1 = r14.getProperty(r1)
            java.lang.Object r0 = r0.get(r1)
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            r1 = r0
        L_0x00bb:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "action: "
            r0.<init>(r3)
            java.lang.StringBuilder r0 = r0.append(r1)
            r0.toString()
            java.lang.String r0 = "overwrite"
            boolean r0 = r14.containsKey(r0)
            if (r0 == 0) goto L_0x055d
            java.lang.String r0 = "overwrite"
            java.lang.String r0 = r14.getProperty(r0)
            java.lang.String r3 = "true"
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x055d
            r3 = r4
        L_0x00e0:
            int r0 = r13.size()
            if (r0 > 0) goto L_0x00f4
            java.util.Stack r3 = a((java.util.Properties) r14)
            java.lang.String r0 = "data_uri"
            java.lang.String r5 = r14.getProperty(r0)
            switch(r1) {
                case 31: goto L_0x017c;
                case 32: goto L_0x019d;
                case 33: goto L_0x01ed;
                case 34: goto L_0x0226;
                case 35: goto L_0x025b;
                case 36: goto L_0x0337;
                case 37: goto L_0x0432;
                case 38: goto L_0x043f;
                case 39: goto L_0x04b8;
                case 40: goto L_0x0364;
                case 41: goto L_0x03ff;
                default: goto L_0x00f3;
            }
        L_0x00f3:
            return
        L_0x00f4:
            java.lang.Object r0 = r13.removeFirst()
            java.lang.String r0 = (java.lang.String) r0
            java.io.File r5 = new java.io.File
            r5.<init>(r0)
            java.io.File r6 = new java.io.File
            java.lang.String r0 = r15.getProperty(r0)
            r6.<init>(r0)
            boolean r0 = r5.exists()
            if (r0 == 0) goto L_0x0160
            if (r3 == 0) goto L_0x012f
            long r7 = r6.length()
            boolean r0 = r12.a((long) r7)
            if (r0 == 0) goto L_0x012b
            r5.delete()
            boolean r0 = r6.renameTo(r5)
            if (r0 != 0) goto L_0x0126
            r12.a(r6, r5)
        L_0x0126:
            android.content.Context r0 = r12.a
            com.smarterdroid.wftlib.at.a(r0, r5)
        L_0x012b:
            r6.delete()
            goto L_0x00e0
        L_0x012f:
            java.io.File r0 = a((java.io.File) r5)
            if (r0 == 0) goto L_0x012b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r7 = "free: "
            r5.<init>(r7)
            java.lang.String r7 = r0.getAbsolutePath()
            java.lang.StringBuilder r5 = r5.append(r7)
            r5.toString()
            long r7 = r6.length()
            boolean r5 = r12.a((long) r7)
            if (r5 == 0) goto L_0x012b
            boolean r5 = r6.renameTo(r0)
            if (r5 != 0) goto L_0x015a
            r12.a(r6, r0)
        L_0x015a:
            android.content.Context r5 = r12.a
            com.smarterdroid.wftlib.at.a(r5, r0)
            goto L_0x012b
        L_0x0160:
            r5.delete()
            long r7 = r6.length()
            boolean r0 = r12.a((long) r7)
            if (r0 == 0) goto L_0x012b
            boolean r0 = r6.renameTo(r5)
            if (r0 != 0) goto L_0x0176
            r12.a(r6, r5)
        L_0x0176:
            android.content.Context r0 = r12.a
            com.smarterdroid.wftlib.at.a(r0, r5)
            goto L_0x012b
        L_0x017c:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "data_file"
            java.lang.String r1 = r14.getProperty(r1)
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x0196
            boolean r1 = r0.canWrite()
            if (r1 == 0) goto L_0x0196
            r12.b(r0)
        L_0x0196:
            android.content.Context r0 = r12.a
            com.smarterdroid.wftlib.at.a((android.content.Context) r0)
            goto L_0x00f3
        L_0x019d:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "data_file"
            java.lang.String r1 = r14.getProperty(r1)
            r0.<init>(r1)
            java.io.File r1 = new java.io.File
            java.lang.String r3 = "data_filepath"
            java.lang.String r3 = r14.getProperty(r3)
            r1.<init>(r3)
            boolean r3 = r1.exists()
            if (r3 != 0) goto L_0x01c3
            boolean r3 = r0.canWrite()
            if (r3 == 0) goto L_0x01c3
            boolean r2 = r0.renameTo(r1)
        L_0x01c3:
            if (r2 == 0) goto L_0x00f3
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = r0.getAbsolutePath()
            java.lang.String r3 = java.lang.String.valueOf(r3)
            r2.<init>(r3)
            java.lang.String r3 = " renamed to "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = r1.getAbsolutePath()
            java.lang.StringBuilder r2 = r2.append(r3)
            r2.toString()
            android.content.Context r2 = r12.a     // Catch:{ Exception -> 0x01ea }
            com.smarterdroid.wftlib.at.a(r2, r0, r1)     // Catch:{ Exception -> 0x01ea }
            goto L_0x00f3
        L_0x01ea:
            r0 = move-exception
            goto L_0x00f3
        L_0x01ed:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "data_file"
            java.lang.String r1 = r14.getProperty(r1)
            r0.<init>(r1)
            java.lang.String r1 = "data_filepath"
            java.lang.String r1 = r14.getProperty(r1)
            java.io.File r2 = new java.io.File
            r2.<init>(r1)
            r2.mkdirs()
            boolean r3 = r2.exists()
            if (r3 == 0) goto L_0x00f3
            boolean r2 = r2.canWrite()
            if (r2 == 0) goto L_0x00f3
            java.io.File r2 = new java.io.File
            java.lang.String r3 = r0.getName()
            r2.<init>(r1, r3)
            boolean r1 = r2.exists()
            if (r1 != 0) goto L_0x00f3
            r12.a(r0, r2)
            goto L_0x00f3
        L_0x0226:
            java.lang.String r0 = "data_file"
            boolean r0 = r14.containsKey(r0)
            if (r0 == 0) goto L_0x00f3
            java.lang.String r0 = "data_file"
            java.lang.String r0 = r14.getProperty(r0)
            if (r0 == 0) goto L_0x00f3
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "data_file"
            java.lang.String r1 = r14.getProperty(r1)
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x00f3
            boolean r1 = r0.canRead()
            if (r1 == 0) goto L_0x00f3
            com.smarterdroid.wftlib.az r1 = new com.smarterdroid.wftlib.az
            java.lang.String r2 = r0.getParent()
            r1.<init>(r0, r2)
            r1.a()
            goto L_0x00f3
        L_0x025b:
            java.lang.String r0 = "data_file"
            boolean r0 = r14.containsKey(r0)
            if (r0 == 0) goto L_0x00f3
            java.lang.String r0 = "data_file"
            java.lang.String r0 = r14.getProperty(r0)
            if (r0 == 0) goto L_0x00f3
            java.io.File r3 = new java.io.File
            java.lang.String r0 = "data_file"
            java.lang.String r0 = r14.getProperty(r0)
            r3.<init>(r0)
            boolean r0 = r3.exists()
            if (r0 == 0) goto L_0x00f3
            boolean r0 = r3.canRead()
            if (r0 == 0) goto L_0x00f3
            java.util.LinkedList r4 = new java.util.LinkedList
            r4.<init>()
            java.lang.String r0 = "data_filepath"
            boolean r0 = r14.containsKey(r0)
            if (r0 == 0) goto L_0x031f
            java.lang.String r0 = "data_filepath"
            java.lang.String r0 = r14.getProperty(r0)
            if (r0 == 0) goto L_0x031f
            java.lang.String r0 = "data_filepath"
            java.lang.String r0 = r14.getProperty(r0)
            int r0 = r0.length()
            if (r0 <= 0) goto L_0x031f
            java.lang.String r0 = "data_filepath"
            java.lang.String r0 = r14.getProperty(r0)
            java.lang.String r1 = ".zip"
            boolean r1 = r0.endsWith(r1)
            if (r1 != 0) goto L_0x02c4
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r1.<init>(r0)
            java.lang.String r0 = ".zip"
            java.lang.StringBuilder r0 = r1.append(r0)
            java.lang.String r0 = r0.toString()
        L_0x02c4:
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r0 = r1.exists()
            if (r0 == 0) goto L_0x02d2
            r1.delete()
        L_0x02d2:
            r1.createNewFile()     // Catch:{ IOException -> 0x0557 }
        L_0x02d5:
            boolean r0 = r1.canWrite()
            if (r0 != 0) goto L_0x055a
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r0 = r0.getAbsolutePath()
            java.lang.String r1 = "/"
            boolean r1 = r0.endsWith(r1)
            if (r1 == 0) goto L_0x02f5
            int r1 = r0.length()
            int r1 = r1 + -1
            java.lang.String r0 = r0.substring(r2, r1)
        L_0x02f5:
            java.io.File r1 = new java.io.File
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r2.<init>(r0)
            java.lang.String r0 = "archive.zip"
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            r0 = r1
        L_0x030e:
            r4.add(r3)
            com.smarterdroid.wftlib.ay r1 = new com.smarterdroid.wftlib.ay
            java.lang.String r2 = r3.getParent()
            r1.<init>(r0, r2, r4)
            r1.a()
            goto L_0x00f3
        L_0x031f:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = r3.getAbsolutePath()
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r0.<init>(r1)
            java.lang.String r1 = ".zip"
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            goto L_0x02c4
        L_0x0337:
            java.io.File r0 = new java.io.File
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "data_filepath"
            java.lang.String r2 = r14.getProperty(r2)
            java.lang.String r2 = java.lang.String.valueOf(r2)
            r1.<init>(r2)
            java.lang.String r2 = "data_file"
            java.lang.String r2 = r14.getProperty(r2)
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            boolean r1 = r0.exists()
            if (r1 != 0) goto L_0x00f3
            r0.mkdir()
            goto L_0x00f3
        L_0x0364:
            java.io.File r0 = new java.io.File
            java.lang.String r1 = "data_file"
            java.lang.String r1 = r14.getProperty(r1)
            r0.<init>(r1)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "install "
            r1.<init>(r2)
            java.lang.String r2 = "data_file"
            java.lang.String r2 = r14.getProperty(r2)
            java.lang.StringBuilder r1 = r1.append(r2)
            boolean r2 = r0.exists()
            java.lang.StringBuilder r1 = r1.append(r2)
            boolean r2 = r0.canRead()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = r0.getName()
            java.util.Locale r3 = java.util.Locale.US
            java.lang.String r2 = r2.toLowerCase(r3)
            java.lang.String r3 = ".apk"
            boolean r2 = r2.endsWith(r3)
            java.lang.StringBuilder r1 = r1.append(r2)
            r1.toString()
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x00f3
            boolean r1 = r0.canRead()
            if (r1 == 0) goto L_0x00f3
            java.lang.String r1 = r0.getName()
            java.util.Locale r2 = java.util.Locale.US
            java.lang.String r1 = r1.toLowerCase(r2)
            java.lang.String r2 = ".apk"
            boolean r1 = r1.endsWith(r2)
            if (r1 == 0) goto L_0x00f3
            android.content.Intent r1 = new android.content.Intent
            java.lang.String r2 = "android.intent.action.VIEW"
            r1.<init>(r2)
            android.net.Uri r0 = android.net.Uri.fromFile(r0)
            java.lang.String r2 = "application/vnd.android.package-archive"
            r1.setDataAndType(r0, r2)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "install "
            r0.<init>(r2)
            java.lang.String r2 = r1.getDataString()
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r2 = " "
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r2 = r1.getType()
            java.lang.StringBuilder r0 = r0.append(r2)
            r0.toString()
            r1.addFlags(r9)
            android.content.Context r0 = r12.a
            r0.startActivity(r1)
            goto L_0x00f3
        L_0x03ff:
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "android.intent.action.VIEW"
            java.lang.String r2 = "market://details?id=com.smarterdroid.wififiletransferpro&referrer=utm_source%3Dacer%26utm_medium%3Dwebbtn"
            android.net.Uri r2 = android.net.Uri.parse(r2)
            r0.<init>(r1, r2)
            r0.addFlags(r9)
            android.content.Context r1 = com.smarterdroid.wftlib.aj.h()     // Catch:{ Exception -> 0x0418 }
            r1.startActivity(r0)     // Catch:{ Exception -> 0x0418 }
            goto L_0x00f3
        L_0x0418:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00f3
        L_0x041e:
            java.io.File r1 = new java.io.File
            java.lang.Object r0 = r3.pop()
            java.lang.String r0 = (java.lang.String) r0
            r1.<init>(r5, r0)
            boolean r0 = r1.exists()
            if (r0 == 0) goto L_0x0432
            r12.b(r1)
        L_0x0432:
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x041e
        L_0x0438:
            android.content.Context r0 = r12.a
            com.smarterdroid.wftlib.at.a((android.content.Context) r0)
            goto L_0x00f3
        L_0x043f:
            java.util.LinkedList r1 = new java.util.LinkedList
            r1.<init>()
        L_0x0444:
            boolean r0 = r3.isEmpty()
            if (r0 == 0) goto L_0x04a3
            java.lang.String r0 = "archive.zip"
            java.lang.String r2 = "data_file"
            boolean r2 = r14.containsKey(r2)
            if (r2 == 0) goto L_0x0489
            java.lang.String r2 = "data_file"
            java.lang.String r2 = r14.getProperty(r2)
            if (r2 == 0) goto L_0x0489
            java.lang.String r2 = "data_file"
            java.lang.String r2 = r14.getProperty(r2)
            int r2 = r2.length()
            if (r2 <= 0) goto L_0x0489
            java.lang.String r0 = "data_file"
            java.lang.String r0 = r14.getProperty(r0)
            java.lang.String r2 = ".zip"
            boolean r2 = r0.endsWith(r2)
            if (r2 != 0) goto L_0x0489
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r2.<init>(r0)
            java.lang.String r0 = ".zip"
            java.lang.StringBuilder r0 = r2.append(r0)
            java.lang.String r0 = r0.toString()
        L_0x0489:
            java.io.File r2 = new java.io.File
            r2.<init>(r5, r0)
            boolean r0 = r2.exists()
            if (r0 == 0) goto L_0x0497
            r2.delete()
        L_0x0497:
            com.smarterdroid.wftlib.ay r0 = new com.smarterdroid.wftlib.ay
            java.lang.String r3 = ""
            r0.<init>(r2, r3, r1)
            r0.a()
            goto L_0x00f3
        L_0x04a3:
            java.io.File r2 = new java.io.File
            java.lang.Object r0 = r3.pop()
            java.lang.String r0 = (java.lang.String) r0
            r2.<init>(r5, r0)
            boolean r0 = r2.exists()
            if (r0 == 0) goto L_0x0444
            r1.add(r2)
            goto L_0x0444
        L_0x04b8:
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()
            java.lang.String r0 = r0.getAbsolutePath()
            java.lang.String r1 = "data_file"
            boolean r1 = r14.containsKey(r1)
            if (r1 == 0) goto L_0x0438
            java.lang.String r1 = "data_file"
            java.lang.String r1 = r14.getProperty(r1)
            if (r1 == 0) goto L_0x0438
            java.lang.String r1 = "data_file"
            java.lang.String r1 = r14.getProperty(r1)
            int r1 = r1.length()
            if (r1 <= 0) goto L_0x0438
            java.lang.String r1 = "data_file"
            java.lang.String r0 = r14.getProperty(r1, r0)
            java.io.File r1 = new java.io.File
            r1.<init>(r0)
            boolean r0 = r1.exists()
            if (r0 != 0) goto L_0x04f6
            boolean r0 = r1.isDirectory()
            if (r0 != 0) goto L_0x04f6
            r1.mkdirs()
        L_0x04f6:
            java.io.File r0 = new java.io.File
            r0.<init>(r5)
            android.os.StatFs r6 = new android.os.StatFs
            java.lang.String r0 = r0.getAbsolutePath()
            r6.<init>(r0)
            int r0 = r6.getBlockSize()
            long r7 = (long) r0
            int r0 = r6.getAvailableBlocks()
            long r9 = (long) r0
            long r6 = r7 * r9
            android.os.StatFs r0 = new android.os.StatFs
            java.lang.String r8 = r1.getAbsolutePath()
            r0.<init>(r8)
            int r8 = r0.getBlockSize()
            long r8 = (long) r8
            int r0 = r0.getAvailableBlocks()
            long r10 = (long) r0
            long r8 = r8 * r10
            int r0 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r0 == 0) goto L_0x0555
        L_0x0528:
            boolean r0 = r1.exists()
            if (r0 == 0) goto L_0x0438
            boolean r0 = r1.isDirectory()
            if (r0 == 0) goto L_0x0438
            boolean r0 = r1.canWrite()
            if (r0 == 0) goto L_0x0438
        L_0x053a:
            boolean r0 = r3.isEmpty()
            if (r0 != 0) goto L_0x0438
            java.io.File r2 = new java.io.File
            java.lang.Object r0 = r3.pop()
            java.lang.String r0 = (java.lang.String) r0
            r2.<init>(r5, r0)
            boolean r0 = r2.exists()
            if (r0 == 0) goto L_0x053a
            r12.a((java.io.File) r2, (java.io.File) r1, (boolean) r4)
            goto L_0x053a
        L_0x0555:
            r4 = r2
            goto L_0x0528
        L_0x0557:
            r0 = move-exception
            goto L_0x02d5
        L_0x055a:
            r0 = r1
            goto L_0x030e
        L_0x055d:
            r3 = r2
            goto L_0x00e0
        L_0x0560:
            r1 = r2
            goto L_0x00bb
        */
        throw new UnsupportedOperationException("Method not decompiled: com.smarterdroid.wftlib.au.a(java.util.LinkedList, java.util.Properties, java.util.Properties):void");
    }
}
