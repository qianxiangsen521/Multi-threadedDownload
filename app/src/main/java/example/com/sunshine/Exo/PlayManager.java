package example.com.sunshine.Exo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;

/**
 * Created by qianxiangsen on 2017/7/11.
 */

public class PlayManager  {


    public static void play(@NonNull Context context, PlayInfo playInfo){
        Intent i = new Intent(context, ExoService.class);
        i.setAction(ExoConstants.ACTION_PLAY);
        i.putExtra(ExoConstants.PLAY_URL,playInfo.getPlayUrl());
        startPlayService(context,i);

    }
    public static void seek(@NonNull Context context,PlayInfo playInfo){
        Intent i = new Intent(context, ExoService.class);
        i.setAction(ExoConstants.ACTION_SEEK);
        i.putExtra(ExoConstants.PLAY_POSTITION,playInfo.getPosition());
        startPlayService(context,i);
    }
    public static void pause(@NonNull Context context,PlayInfo playInfo){
        Intent i = new Intent(context, ExoService.class);
        i.setAction(ExoConstants.ACTION_PAUSE);
        startPlayService(context,i);
    }
    public static void next(@NonNull Context context,PlayInfo playInfo){
        Intent i = new Intent(context, ExoService.class);
        i.setAction(ExoConstants.ACTION_NEXT);
        startPlayService(context,i);
    }
    public static void previous(@NonNull Context context ,PlayInfo playInfo){
        Intent i = new Intent(context, ExoService.class);
        i.setAction(ExoConstants.ACTION_PREVIONS);
        startPlayService(context,i);
    }
    public static void restart(@NonNull Context context ,PlayInfo playInfo){
        Intent i = new Intent(context, ExoService.class);
        i.setAction(ExoConstants.ACTIION_RESTART);
        startPlayService(context,i);
    }
    public static void startPlayService(Context context, Intent i) {
        context.startService(i);
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void bindPlayService(Context c) {
        if (c != null) {
            Intent intent = new Intent(c, ExoService.class);
            c.startService(intent);
            c.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    private void unBindPlayService(Context c) {
        if (c != null) {
            c.unbindService(mConnection);
            c.stopService(new Intent(c, ExoService.class));
        }
    }
}
