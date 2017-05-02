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

    static final int NETWORK_STATE_CHANGE = 9;

    static final int AIRPLANE_MODE_CHANGE = 10;

    private static final int AIRPLANE_MODE_ON = 1;

    private static final int AIRPLANE_MODE_OFF = 0;

    private static final String DISPATCHER_THREAD_NAME = "Dispatcher";

    final Context context;

    final DatabaseHelper databaseHelper;

    final ExecutorService mThreadPool;


    final ExecutorService mThreadPool1 = Executors.newFixedThreadPool(2);

//    final Handler handler;
//
//    final NetworkBroadcastReceiver receiver;
//
//    final boolean scansNetworkChanges;
//
//    final DispatcherThread dispatcherThread;

    final Map<String, HttpDownHunter> hunterMap;

    final DownloadManagerListenerModerator downloadManagerListenerModerator;

    HttpDownloader(Context context,DatabaseHelper databaseHelper,ExecutorService mThreadPool,DownloadManagerListenerModerator downloadManagerListenerModerator){
//        this.dispatcherThread = new DispatcherThread();
//        this.dispatcherThread.start();
//        this.handler = new DispatcherHandler(dispatcherThread.getLooper(), this);
        this.context = context;
        this.downloadManagerListenerModerator = downloadManagerListenerModerator;
        this.databaseHelper = databaseHelper;
        this.mThreadPool = mThreadPool;
        this.hunterMap = new LinkedHashMap<>();
//        this.scansNetworkChanges = Utlis.hasPermission(context, Manifest.permission.ACCESS_NETWORK_STATE);
//        this.receiver = new NetworkBroadcastReceiver(this);
//        receiver.register();


    }
    void enqueue(Task request) {

        mThreadPool1.submit(new HttpDownHunter(request,databaseHelper,downloadManagerListenerModerator));
    }
//    static class DispatcherThread extends HandlerThread {
//        DispatcherThread() {
//            super(Utlis.THREAD_PREFIX + DISPATCHER_THREAD_NAME, THREAD_PRIORITY_BACKGROUND);
//        }
//    }


//
//    static class NetworkBroadcastReceiver extends BroadcastReceiver {
//        static final String EXTRA_AIRPLANE_STATE = "state";
//
//        private final HttpDownloader mHttpDownloader;
//
//        NetworkBroadcastReceiver(HttpDownloader mHttpDownloader) {
//            this.mHttpDownloader = mHttpDownloader;
//        }
//
//        void register() {
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(ACTION_AIRPLANE_MODE_CHANGED);
//            if (mHttpDownloader.scansNetworkChanges) {
//                filter.addAction(CONNECTIVITY_ACTION);
//            }
//            mHttpDownloader.context.registerReceiver(this, filter);
//        }
//
//        void unregister() {
//            mHttpDownloader.context.unregisterReceiver(this);
//        }
//
//        @Override public void onReceive(Context context, Intent intent) {
//            // On some versions of Android this may be called with a null Intent,
//            // also without extras (getExtras() == null), in such case we use defaults.
//            if (intent == null) {
//                return;
//            }
//            final String action = intent.getAction();
//            if (ACTION_AIRPLANE_MODE_CHANGED.equals(action)) {
//                if (!intent.hasExtra(EXTRA_AIRPLANE_STATE)) {
//                    return; // No airplane state, ignore it. Should we query Utils.isAirplaneModeOn?
//                }
//                mHttpDownloader.dispatchAirplaneModeChange(intent.getBooleanExtra(EXTRA_AIRPLANE_STATE, false));
//            } else if (CONNECTIVITY_ACTION.equals(action)) {
//                ConnectivityManager connectivityManager = Utlis.getService(context, CONNECTIVITY_SERVICE);
//                mHttpDownloader.dispatchNetworkStateChange(connectivityManager.getActiveNetworkInfo());
//            }
//        }
//    }
//    void dispatchNetworkStateChange(NetworkInfo info) {
//        handler.sendMessage(handler.obtainMessage(NETWORK_STATE_CHANGE, info));
//    }
//
//    void dispatchAirplaneModeChange(boolean airplaneMode) {
//        handler.sendMessage(handler.obtainMessage(AIRPLANE_MODE_CHANGE,
//                airplaneMode ? AIRPLANE_MODE_ON : AIRPLANE_MODE_OFF, 0));
//    }
//    private static class DispatcherHandler extends Handler {
//        private final HttpDownloader dispatcher;
//
//        public DispatcherHandler(Looper looper, HttpDownloader dispatcher) {
//            super(looper);
//            this.dispatcher = dispatcher;
//        }
//
//        @Override
//        public void handleMessage(final Message msg) {
//            switch (msg.what) {
//
//                case NETWORK_STATE_CHANGE: {
//                    NetworkInfo info = (NetworkInfo) msg.obj;
//                    dispatcher.performNetworkStateChange(info);
//                    break;
//                }
//                default:
//                break;
//            }
//        }
//    }
//    void performNetworkStateChange(NetworkInfo info) {
//        if (mThreadPool instanceof HttpExecutorService) {
//            ((HttpExecutorService) mThreadPool).adjustThreadCount(info);
//        }
//    }
//
//    void shutdown() {
//        // Shutdown the thread pool only if it is the one created by Picasso.
//        if (mThreadPool instanceof HttpExecutorService) {
//            mThreadPool.shutdown();
//        }
//        dispatcherThread.quit();
//        // Unregister network broadcast receiver on the main thread.
//        DownloadMessage.sHandler.post(new Runnable() {
//            @Override public void run() {
//                receiver.unregister();
//            }
//        });
//    }
}
