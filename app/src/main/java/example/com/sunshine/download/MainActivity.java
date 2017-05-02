package example.com.sunshine.download;

import android.app.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import example.com.sunshine.R;


public class MainActivity extends Activity implements View.OnClickListener{

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (Build.VERSION.SDK_INT >= 21) {
            PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
        }
        findViewById(R.id.btnListView).setOnClickListener(this);
        findViewById(R.id.btnRecyclerView).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DownloadActivity.class);
        switch (v.getId()) {
            case R.id.btnListView:
                intent.putExtra("EXTRA_TYPE", DownloadActivity.TYPE.TYPE_LISTVIEW);
                break;
            case R.id.btnRecyclerView:
                intent.putExtra("EXTRA_TYPE", DownloadActivity.TYPE.TYPE_RECYCLERVIEW);
                break;
            default:
                return;
        }
        startActivity(intent);
    }
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_SMS:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_READ_SMS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_BATTERY_STATS:
                    Toast.makeText(MainActivity.this, "Result Permission Grant CODE_BATTERY_STATS", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
