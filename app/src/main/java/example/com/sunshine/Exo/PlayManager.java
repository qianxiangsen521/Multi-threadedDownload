package example.com.sunshine.Exo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;

import org.greenrobot.eventbus.EventBus;

import example.com.sunshine.Exo.E.MessageEvent;
import example.com.sunshine.Exo.E.NextEvent;
import example.com.sunshine.Exo.E.PlayEvent;
import example.com.sunshine.IRemoteService;
import example.com.sunshine.IRemoteServiceCallback;
import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.Home.Main111Activity;

/**
 * Created by qianxiangsen on 2017/7/11.
 */

public class PlayManager  {


    IRemoteService mService = null;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            mService =   IRemoteService.Stub.asInterface(service);

            try {
                mService.registerCallback(mCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }


        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub(){
        @Override
        public void NextEvent(boolean isSeekable, boolean enablePrevious, boolean enableNext) throws RemoteException {

            EventBus.getDefault().post(new NextEvent(isSeekable,enablePrevious,enableNext));

        }

        @Override
        public void MessageEvent(long mCurrentPosition, long mDuration, long mBufferedPosition) throws RemoteException {

            EventBus.getDefault().post(new MessageEvent(mCurrentPosition,
                    mDuration
                    ,mBufferedPosition));

        }

        @Override
        public void PlayEvent(boolean value) throws RemoteException {

            EventBus.getDefault().post(new PlayEvent(value));
        }
    };

    private static PlayManager gPlayManager;
    public static PlayManager getInstance() {
        if (gPlayManager == null) {
            gPlayManager = new PlayManager();
        }
        return gPlayManager;
    }

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


    public void bindPlayService() {
        Context c = TLiveApplication.getInstance();
        if (c != null) {
            Intent intent = new Intent(c, ExoService.class);
            intent.setAction(IRemoteService.class.getName());
            c.startService(intent);
            c.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public void unBindPlayService() {
        Context c = TLiveApplication.getInstance();
        if (c != null) {
            c.unbindService(mConnection);
            c.stopService(new Intent(c, ExoService.class));
        }
    }
    public static void exitApp(Context context) {
        Intent intent = new Intent(context, Main111Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Main111Activity.ParamExit, true);
        context.startActivity(intent);
    }


    public static void destoryInstance() {
        if (gPlayManager != null) {
            gPlayManager.release();
        }
    }
    private void release() {
        // 退出程序，记住当前文件的播放位置

        unBindPlayService();

        gPlayManager = null;


    }

    public void stop() {
        SRVstop(TLiveApplication.getInstance());
    }

    public static void SRVstop(Context context) {
        Intent i = new Intent(context, ExoService.class);
        i.setAction(ExoConstants.ACTION_PAUSE);
        startPlayService(context, i);
    }
    public static void Exit(Activity activity) {

        if (activity != null){
            activity.finish();
        }
        PlayManager.destoryInstance();

    }

}
