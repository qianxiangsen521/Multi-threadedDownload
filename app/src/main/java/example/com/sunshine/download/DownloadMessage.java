package example.com.sunshine.download;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;


import com.golshadi.majid.report.listener.DownloadManagerListener;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by qianxiangsen on 2017/4/20.
 */

public class DownloadMessage {
    private static final String DOWNLOAD_PATH = "/Android/data/com.cnr.fm/download/";

    private final static int MSG_WHAT_BASE = 1000000000;
    private final static int MSG_WHAT_ON_WAITING = MSG_WHAT_BASE + 1;
    private final static int MSG_WHAT_ON_START = MSG_WHAT_BASE + 2;
    private final static int MSG_WHAT_ON_COMPLETE = MSG_WHAT_BASE + 3;
    private final static int MSG_WHAT_ON_ERROR = MSG_WHAT_BASE + 4;
    private final static int MSG_WHAT_ON_PROJRESS = MSG_WHAT_BASE + 5;

    static volatile DownloadMessage singleton = null;
    final Context context;
    private HttpDownloader httpDownloader;

    static final InternalHandler sHandler = new InternalHandler();


    final static class InternalHandler extends Handler {

        private InternalHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.obj == null) {
                throw new IllegalArgumentException("msg must not be null");
            }
            Task taskInfo;
            if (msg.obj instanceof Task) {
                taskInfo = (Task) msg.obj;
            try {
                switch (msg.what) {
                    case MSG_WHAT_ON_WAITING: {

                        break;
                    }
                    case MSG_WHAT_ON_START: {
                        if (taskInfo.getDownloadUiListener() != null){

                            taskInfo.getDownloadUiListener().UiStrat();
                        }
                        break;
                    }
                    case MSG_WHAT_ON_PROJRESS: {
                        Log.d("TAG", "handleMessage: "+taskInfo.getDownloadSize());
                        if (taskInfo.getDownloadUiListener() != null){
                            taskInfo.getDownloadUiListener().UiProgress(taskInfo,taskInfo.getSize(),taskInfo.getDownloadSize());
                        }
                        break;
                    }
                    case MSG_WHAT_ON_ERROR: {
                        break;
                    }
                    case MSG_WHAT_ON_COMPLETE: {
                        if (taskInfo.getDownloadUiListener() != null){
                            taskInfo.getDownloadUiListener().UiFinish(taskInfo);
                        }
                        break;
                    }

                    default: {
                        break;
                    }
                }
            } catch (Throwable ex) {
                taskInfo.setState(taskInfo.STATUS_ERROR);

            }
        }
    }
     }


    DownloadMessage(Context context,HttpDownloader httpDownloader){
        this.context = context;
        this.httpDownloader = httpDownloader;

    }

    public static DownloadMessage init(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        if (singleton == null) {
            synchronized (DownloadMessage.class) {
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }
        return singleton;
    }
    public static class Builder implements DownloadListener{
        private final Context context;
        private MyOpenHelper openHelper;
        private DatabaseHelper databaseHelper;
        private ExecutorService mThreadPool;
        private DownloadManagerListenerModerator downloadManagerListenerModerator;

        public Builder(@NonNull Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        public DownloadMessage build() {
            Context context = this.context;

            if (openHelper == null) {
                openHelper = new MyOpenHelper(context);
            }
            if (databaseHelper == null) {
                databaseHelper = new DatabaseHelper();
                databaseHelper.openDatabase(openHelper);
            }
            if (mThreadPool == null) {
                mThreadPool = Executors.newFixedThreadPool(2);
            }
            if (downloadManagerListenerModerator == null){
                downloadManagerListenerModerator = new DownloadManagerListenerModerator(this);
            }

            HttpDownloader httpDownloader = new HttpDownloader(databaseHelper,mThreadPool,downloadManagerListenerModerator);

            return new DownloadMessage(context,httpDownloader);
        }

        @Override
        public  void onError(Task request) {
            sHandler.obtainMessage(MSG_WHAT_ON_ERROR, request).sendToTarget();
        }


        @Override
        public void onProgress(Task request) {
            sHandler.obtainMessage(MSG_WHAT_ON_PROJRESS, request).sendToTarget();
        }

        @Override
        public  void onComplete(Task request) {
            sHandler.obtainMessage(MSG_WHAT_ON_COMPLETE, request).sendToTarget();
        }

        @Override
        public void onIdie(Task request) {
            sHandler.obtainMessage(MSG_WHAT_ON_WAITING, request).sendToTarget();
        }

        @Override
        public void onStart(Task request) {
            sHandler.obtainMessage(MSG_WHAT_ON_START, request).sendToTarget();
        }
    }

    public  Task addTask(Task task,DownloadUiListener downloadUiListener){
        task.setDownloadSize(0);
        int index = task.getUrl().lastIndexOf("/");
        String destUri = Environment.getExternalStorageDirectory()
                + DOWNLOAD_PATH
                + task.getUrl().substring(index + 1);

        task.setSave_address(destUri);
        task.setSpeed(System.currentTimeMillis());
        task.setDownloadUiListener(downloadUiListener);
        httpDownloader.databaseHelper.insertTask(task);
        return task;
    }

    public synchronized void startDownload(Task token){
       Task task= httpDownloader.databaseHelper.getTaskInfo(token);

        if (task != null && task.getState() != Task.STATUS_IDLE) {
            task.setState(Task.STATUS_IDLE);
            httpDownloader.enqueue(task);

            httpDownloader.databaseHelper.update(task);
        }

    }

    public void pauseDownload(Task token) {
        Task task= httpDownloader.databaseHelper.getTaskInfo(token);

        if (task != null && task.getState() != Task.STATUS_PAUSE) {
            task.setState(Task.STATUS_PAUSE);
            httpDownloader.databaseHelper.update(task);
        }
    }


}
