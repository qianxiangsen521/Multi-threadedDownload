package example.com.sunshine.aldl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.widget.Toast;

import example.com.sunshine.IRemoteService;

/**
 * Created by qianxiangsen on 2017/7/17.
 */

public class Binding  extends Activity {

//    IRemoteService mService = null;
//    private ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mService = IRemoteService.Stub.asInterface(service);
//
//            try {
//                mService.registerCallback(mCallback);
//            } catch (RemoteException e) {
//
//            }
//
//            // As part of the sample, tell the user what happened.
//            Toast.makeText(Binding.this, "onServiceConnected",
//                    Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            mService = null;
//            mKillButton.setEnabled(false);
//            mCallbackText.setText("Disconnected.");
//
//            // As part of the sample, tell the user what happened.
//            Toast.makeText(Binding.this, "onServiceDisconnected",
//                    Toast.LENGTH_SHORT).show();
//        }
//    };
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

}
