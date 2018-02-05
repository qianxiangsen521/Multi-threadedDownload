package example.com.sunshine.Exo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import example.com.sunshine.ISecondary;
import example.com.sunshine.R;

public class MainActivity extends AppCompatActivity {
    static final int MSG_SAY_HELLO = 1;
    public static final String NAME_GESTURE_DETECTOR_REMOTE_SERVICE = "com.example.qianxiangsen.ffmpeg.AIDLService" ;
    public static final String PACKAGE_GESTURE_DETECTOR_REMOTE_SERVICE = "com.example.qianxiangsen.ffmpeg" ;
    private boolean mBound;
    private Button mBtnAidl;
    private ISecondary mIMyAidlInterface;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMyAidlInterface = ISecondary.Stub.asInterface(service);
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIMyAidlInterface = null;
            mBound =false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnAidl = (Button) findViewById(R.id.test);
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(
                PACKAGE_GESTURE_DETECTOR_REMOTE_SERVICE ,
                NAME_GESTURE_DETECTOR_REMOTE_SERVICE);
        intent .setComponent (componentName );
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
        mBtnAidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIMyAidlInterface != null){
                    try {
//                        int name = mIMyAidlInterface.getPid();
//                        double v1 = mIMyAidlInterface.doCalculate(22, 11);
                        mIMyAidlInterface.basicTypes(1,1,true,1,1,"dd");

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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
