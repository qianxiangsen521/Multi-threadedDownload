package example.com.sunshine.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import example.com.sunshine.Process.MainActivity;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/4/1.
 */

public class TestService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG", "TestService"+"onCreate: ");
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d("TAG", "TestService"+"onStartCommand: ");

        final NotificationManager mNotifyManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final NotificationCompat.Builder   mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.mipmap.ic_launcher);
            // Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr+=5) {
                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            mBuilder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            mNotifyManager.notify(0, mBuilder.build());
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 5 seconds
                                Thread.sleep(5*1000);
                            } catch (InterruptedException e) {
                                Log.d("TAG", "sleep failure");
                            }
                        }
                        // When the loop is finished, updates the notification
                        mBuilder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0,0,false);
                        mNotifyManager.notify(1, mBuilder.build());
                    }
                }
        // Starts the thread by calling the run() method in its Runnable
        ).start();



        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("TAG", "TestService"+"onDestroy: ");
    }
}
