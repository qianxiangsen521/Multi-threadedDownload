package example.com.sunshine.download;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by qianxiangsen on 2017/4/20.
 */

public class DownloadMessage {
    private static final String DOWNLOAD_PATH = "/download/";

    private final static int MSG_WHAT_BASE = 1000000000;
    private final static int MSG_WHAT_ON_WAITING = MSG_WHAT_BASE + 1;
    private final static int MSG_WHAT_ON_START = MSG_WHAT_BASE + 2;
    private final static int MSG_WHAT_ON_COMPLETE = MSG_WHAT_BASE + 3;
    private final static int MSG_WHAT_ON_ERROR = MSG_WHAT_BASE + 4;
    private final static int MSG_WHAT_ON_PROJRESS = MSG_WHAT_BASE + 5;

    private List<Task> mArrayFinish = new ArrayList<>();


    static volatile DownloadMessage singleton = null;
    final Context context;

    private HttpDownloader httpDownloader;

    boolean shutdown;

    static final InternalHandler sHandler = new InternalHandler();

    static final class InternalHandler extends Handler {

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

                            taskInfo.getDownloadUiListener().UiFinish(taskInfo);
                            if (taskInfo.getDownloadUiListener() != null){
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
        private ExecutorService service;
        private DownloadManagerListenerModerator listener;

        public Builder(@NonNull Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }
        public Builder baseHelper(@NonNull DatabaseHelper databaseHelper){
            if (databaseHelper == null) {
                throw new IllegalArgumentException("databaseHelper must not be null.");
            }
            if (this.databaseHelper != null) {
                throw new IllegalStateException("databaseHelper already set.");
            }
            this.databaseHelper = databaseHelper;
            return this;
        }
        public Builder openHelper(@NonNull MyOpenHelper openHelper){
            if (openHelper == null) {
                throw new IllegalArgumentException("openHelper must not be null.");
            }
            if (this.service != null) {
                throw new IllegalStateException("openHelper already set.");
            }
            this.openHelper = openHelper;
            return this;
        }
        public Builder executor(@NonNull ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("Executor service must not be null.");
            }
            if (this.service != null) {
                throw new IllegalStateException("Executor service already set.");
            }
            this.service = executorService;
            return this;
        }
        public Builder listener(@NonNull DownloadManagerListenerModerator listener) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener must not be null.");
            }
            if (this.listener != null) {
                throw new IllegalStateException("Listener already set.");
            }
            this.listener = listener;
            return this;
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
            if (service == null) {
                service = Executors.newFixedThreadPool(2);
            }
            if (listener == null){
                listener = new DownloadManagerListenerModerator(this);
            }

            HttpDownloader httpDownloader = new HttpDownloader(context,databaseHelper,service,listener);

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
        Task t = httpDownloader.databaseHelper.getTaskInfo(task);
        if (t.getDownloadSize() != 0){
            t.setDownloadUiListener(downloadUiListener);
            return t;
        }

        task.setDownloadSize(0);
        int index = task.getUrl().lastIndexOf("/");
        String destUri =FileUtils.getAlbumStorageDir(context)
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
            task.setPriority(true);
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
    public boolean delete(Task task,boolean deleteTaskFile){
        task = httpDownloader.databaseHelper.getTaskInfo(task);
        if (deleteTaskFile) {
            FileUtils.deleteFile(task.getSave_address());
        }
        return httpDownloader.databaseHelper.delete(task.getId());
    }



    public List<Task> getmArrayFinish(){
        List<Task> mTask= httpDownloader.databaseHelper.getUnnotifiedCompleted();
        if (mTask != null){
            for(Task taskName : mTask){
                if (Task.STATUS_COMPLETE.equals(taskName.getState())){
                    if (mArrayFinish.contains(taskName)){
                        mArrayFinish.remove(taskName);
                    }
                    mArrayFinish.add(taskName);
                }
            }
        }
        return mArrayFinish;
    }

//    public void shutdown() {
//        if (this == singleton) {
//            throw new UnsupportedOperationException("Default singleton instance cannot be shutdown.");
//        }
//        if (shutdown) {
//            return;
//        }
//        httpDownloader.shutdown();
//
//        shutdown = true;
//    }
}
