package example.com.sunshine.Exo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;

import example.com.sunshine.R;

public class MessengerActivity extends Activity {
    static final int MSG_SAY_HELLO = 1;
    public static final String NAME_GESTURE_DETECTOR_REMOTE_SERVICE = "com.example.qianxiangsen.ffmpeg.MessengerService" ;
    public static final String PACKAGE_GESTURE_DETECTOR_REMOTE_SERVICE = "com.example.qianxiangsen.ffmpeg" ;
    private boolean mBound;
    private Messenger mMessenger;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMessenger = null;
            mBound = false;
        }
    };

    public void sayHello(View v){
        if(!mBound){
            return;
        }
        Message msg = Message.obtain();
        msg.what = MSG_SAY_HELLO;
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(
                PACKAGE_GESTURE_DETECTOR_REMOTE_SERVICE ,
                NAME_GESTURE_DETECTOR_REMOTE_SERVICE);
        intent .setComponent (componentName );
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound){
            unbindService(mServiceConnection);
            mBound = false;
        }
    }
}
