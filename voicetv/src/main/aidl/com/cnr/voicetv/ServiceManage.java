package com.cnr.voicetv;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * Created by qianxiangsen on 18-2-5.
 */

public class ServiceManage {
    public static final String NAME_GESTURE_DETECTOR_REMOTE_SERVICE = "com.cnr.voicetv.service.CnrService" ;
    public static final String PACKAGE_GESTURE_DETECTOR_REMOTE_SERVICE = "com.cnr.voicetv" ;
    private CnrAidlInterface mCnrAidlInterface;
    private boolean mBound;
    private Context mContext;
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

    public ServiceManage(Context mContext) {
        this.mContext = mContext;
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
        intent .setComponent (componentName );
        mContext.bindService(intent, mServiceConnection, mContext.BIND_AUTO_CREATE);
    }

    public void stopService(){
        if(mBound){
            mContext.unbindService(mServiceConnection);
            mBound = false;
        }
    }
    public void sendMessage(String message){
        if(mCnrAidlInterface != null){
            try {
                if (!TextUtils.isEmpty(message)){
                    mCnrAidlInterface.send(message);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
