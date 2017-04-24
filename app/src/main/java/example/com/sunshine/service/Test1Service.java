package example.com.sunshine.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import example.com.sunshine.Process.MainActivity;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/4/1.
 */

public class Test1Service extends Service {

    public static final int ONGOING_NOTIFICATION_ID = 12345;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "Test1--Service"+"onCreate: ");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d("TAG", "Test1--Service"+"onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("TAG", "Test1--Service"+"onDestroy: ");
    }

}
