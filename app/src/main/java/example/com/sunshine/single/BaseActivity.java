package example.com.sunshine.single;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("TAG", "*****onCreate()方法******");
        Log.i("TAG", "onCreate：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        dumpTaskAffinity();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("TAG", "*****onNewIntent()方法*****");
        Log.i("TAG", "onNewIntent：" + getClass().getSimpleName() + " TaskId: " + getTaskId() + " hasCode:" + this.hashCode());
        dumpTaskAffinity();
    }

    protected void dumpTaskAffinity(){
        try {
            ActivityInfo info = this.getPackageManager()
                    .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            Log.i("TAG", "taskAffinity:"+info.taskAffinity);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}