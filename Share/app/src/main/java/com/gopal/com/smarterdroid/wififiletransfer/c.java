package com.smarterdroid.wififiletransfer;

import android.content.Context;
import com.smarterdroid.wftlib.aj;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class c extends Thread {
    private ServerSocket a = null;
    private int b = 0;
    private String c;
    private boolean d;
    private Context e;
    private aj f;
    private ExecutorService g = new ThreadPoolExecutor(2, 12, 60, TimeUnit.SECONDS, new SynchronousQueue(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    private boolean h;

    public c(aj ajVar, boolean z) {
        this.f = ajVar;
        this.h = z;
    }

    public final void a() {
        this.d = false;
        if (this.a != null && !this.a.isClosed()) {
            try {
                this.a.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        if (!this.g.isShutdown()) {
            this.g.shutdownNow();
        }
    }

    public final void a(int i) {
        this.b = i;
    }

    public final void a(Context context) {
        this.e = context;
    }

    public final void a(String str) {
        this.c = str;
    }

    public final synchronized void run() {
        this.a = this.f.a(this.b, this.h);
        this.d = true;
        while (this.d) {
            try {
                if (this.a != null) {
                    Socket accept = this.a.accept();
                    if (this.d) {
                        this.g.execute(new af(accept, this.e, this.c, this.f, this.h));
                    }
                }
            } catch (IOException e2) {
            }
        }
    }
}
