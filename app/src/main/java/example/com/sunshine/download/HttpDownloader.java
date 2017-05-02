package example.com.sunshine.download;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Intent.ACTION_AIRPLANE_MODE_CHANGED;
import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

/**
 * Created by qianxiangsen on 2017/4/20.
 */

class HttpDownloader {


    final Context context;

    final DatabaseHelper databaseHelper;

    final ExecutorService mThreadPool;

//    final ExecutorService mThreadPool1 = Executors.newFixedThreadPool(2);

    final Map<String, HttpDownHunter> hunterMap;

    final DownloadManagerListenerModerator downloadManagerListenerModerator;

    HttpDownloader(Context context,DatabaseHelper databaseHelper,ExecutorService mThreadPool,DownloadManagerListenerModerator downloadManagerListenerModerator){
        this.context = context;
        this.downloadManagerListenerModerator = downloadManagerListenerModerator;
        this.databaseHelper = databaseHelper;
        this.mThreadPool = mThreadPool;
        this.hunterMap = new LinkedHashMap<>();


    }
    void enqueue(Task request) {
        HttpDownHunter httpDownHunter = new HttpDownHunter(request,databaseHelper,downloadManagerListenerModerator);
        mThreadPool.submit(httpDownHunter);
    }

}
