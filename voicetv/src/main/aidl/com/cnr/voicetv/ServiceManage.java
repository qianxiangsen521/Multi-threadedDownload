package com.cnr.voicetv;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;

/**
 * Created by qianxiangsen on 18-2-5.
 */

public class ServiceManage {
    public static final String NAME_GESTURE_DETECTOR_REMOTE_SERVICE = "com.cnr.voicetv.service.CnrService" ;
    public static final String PACKAGE_GESTURE_DETECTOR_REMOTE_SERVICE = "com.cnr.voicetv" ;
    private CnrAidlInterface mCnrAidlInterface;
    private boolean mBound;
    private final Context context;
    static volatile ServiceManage singleton = null;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mCnrAidlInterface = CnrAidlInterface.Stub.asInterface(service);
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mCnrAidlInterface = null;
            mBound =false;
        }
    };

    public ServiceManage(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context must not be null.");
        }
        this.context = context.getApplicationContext();
    }
    public static ServiceManage init(@NonNull Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        if (singleton == null) {
            synchronized (ServiceManage.class) {
                if (singleton == null) {
                    singleton = new ServiceManage(context);
                }
            }
        }
        return singleton;
    }

    public void bindService(){
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(
                PACKAGE_GESTURE_DETECTOR_REMOTE_SERVICE ,
                NAME_GESTURE_DETECTOR_REMOTE_SERVICE);
        intent .setComponent (componentName);
        context.bindService(intent, mServiceConnection, context.BIND_AUTO_CREATE);
    }

    public void unbindService(){
        if(mBound){
            context.unbindService(mServiceConnection);
            mBound = false;
        }
    }
    public void sendMessage(@NonNull String message){
        if (message.trim().length() == 0) {
            throw new IllegalArgumentException("message must not be empty.");
        }
        if(mCnrAidlInterface != null){
            try {
                 mCnrAidlInterface.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
