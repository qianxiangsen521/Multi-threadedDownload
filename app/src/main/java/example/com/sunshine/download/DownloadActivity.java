package example.com.sunshine.download;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/4/20.
 */

public class DownloadActivity extends AppCompatActivity implements /*DownloadUiListener,*/View.OnClickListener{

    private DownloadMessage downlaod;
    int permsRequestCode = 200;
    private  ArrayList<Task> taskList = new ArrayList<>();
    private String[] perms = {"android.permission. WRITE_EXTERNAL_STORAGE"};

    private ProgressBar ProgressBar;

    private TextView textView;

    private ProgressBar ProgressBar1;

    private TextView textView1;

    private TextView mButton3,mButton5;
    private TextView speed,speed1;
    private TextView size,size1;
    private Button mButton4,mButton6;
    private Task tas;
    private Task taskId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
        downlaod = DownloadMessage.init(this);
//        final Task task = new Task();
//        task.setId(10);
//        task.setmUniquely_id("101");
//        task.setUrl("http://dl.sunshinefm.cn/yinpin_web1/2015/10/26/33613ee1a0a64dc21b131763a6830bf5.3gp");
//        taskList.add(task);
//        final Task t = new Task();
//        t.setId(11);
//        t.setmUniquely_id("111");
//        t.setUrl("http://dl.sunshinefm.cn/yinpin_web3/2017/02/17/357d6a1ed30ffab25b49fec62d6c1c5f.3gp");
//        taskList.add(t);
        ProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
        ProgressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        textView1 = (TextView) findViewById(R.id.textView1);
//        ListView list = (ListView) findViewById(R.id.listview);
//        list.setAdapter(new ADAPTER(this,downlaod,taskList));

        mButton3= (TextView) findViewById(R.id.button3);
        mButton5 = (TextView) findViewById(R.id.button5);

        size= (TextView) findViewById(R.id.textView_size);
        size1 = (TextView) findViewById(R.id.textView_size1);

        mButton4 = (Button) findViewById(R.id.button4);
        mButton6 = (Button) findViewById(R.id.button6);
        speed= (TextView) findViewById(R.id.textView_speed);
        speed1 = (TextView) findViewById(R.id.textView_speed1);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);
        mButton6.setOnClickListener(this);


        final Task task = new Task();
        task.setId(10);
        task.setmUniquely_id("111");
        task.setUrl(Constants.URL);
        taskId = downlaod.addTask(task, new DownloadUiListener() {
            @Override
            public void UiStrat() {

            }

            @Override
            public void UiProgress(Task task, long TotalSize, int downloadSize) {
                Log.d("TAG", "UiProgress------------------->: "+downloadSize);
                size.setText(Utlis.formatSize(downloadSize)+"/"+Utlis.formatSize(TotalSize));
                textView1.setText(Utlis.formatPercent(downloadSize,TotalSize));
                speed.setText(task.getSpeed()+
                         "Kb/s");
                ProgressBar1.setMax((int) TotalSize);
                ProgressBar1.setProgress(downloadSize);
            }

            @Override
            public void UiFinish(Task task) {

            }
        });

        final Task t = new Task();
        t.setId(11);
        t.setmUniquely_id("111");
        t.setUrl(Constants.URL1);

        tas = downlaod.addTask(t, new DownloadUiListener() {
            @Override
            public void UiStrat() {

            }
            @Override
            public void UiProgress(Task task, long TotalSize, int downloadSize) {
                size1.setText(Utlis.formatSize(downloadSize)+"/"+Utlis.formatSize(TotalSize));
                speed1.setText(task.getSpeed()+
                        "Kb/s");
                Log.d("TAG", "UiProgress------------------->: "+downloadSize);
                textView.setText(Utlis.formatPercent(downloadSize,TotalSize));
                ProgressBar.setMax((int) TotalSize);
                ProgressBar.setProgress(downloadSize);
            }

            @Override
            public void UiFinish(Task task) {
                mButton3.setText("安装");
                mButton5.setText("安装");
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button3:

                if (tas.getState().equals(Task.STATUS_START) ){
                    downlaod.pauseDownload(tas);
                    mButton3.setText("继续");
                }else{
                    downlaod.startDownload(tas);
                    mButton3.setText("暂停");

                }

                break;
            case R.id.button5:
                if (taskId.getState().equals(Task.STATUS_START)){
                    downlaod.pauseDownload(taskId);
                    mButton5.setText("继续");
                }else{
                    downlaod.startDownload(taskId);
                    mButton5.setText("暂停");
                }

                break;
            case R.id.button4:

                break;
            case R.id.button6:


                break;

        }

    }
//    @Override
//    public void UiFinish(Task task) {
//        Log.d("TAG", "UiFinish: ");
//    }
//
//    @Override
//    public void UiProgress(Task task,long TotalSize, int downloadSize) {
//
//
//
//            Log.d("TAG", "UiProgress------------------->: "+downloadSize);
//            textView1.setText(Utlis.formatPercent(downloadSize,TotalSize));
//            ProgressBar1.setMax((int) TotalSize);
//            ProgressBar1.setProgress(downloadSize);
//    }
//
//    @Override
//    public void UiStrat() {
//        Log.d("TAG", "UiStrat: ");
//
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == permsRequestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // save file
                Toast.makeText(getApplicationContext(), "save file", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_RECORD_AUDIO", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_GET_ACCOUNTS:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_GET_ACCOUNTS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_PHONE_STATE:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_READ_PHONE_STATE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CALL_PHONE:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_CALL_PHONE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_CAMERA:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_CAMERA", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_ACCESS_FINE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_ACCESS_COARSE_LOCATION", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_EXTERNAL_STORAGE:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_WRITE_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_READ_SMS:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_READ_SMS", Toast.LENGTH_SHORT).show();
                    break;
                case PermissionUtils.CODE_BATTERY_STATS:
                    Toast.makeText(DownloadActivity.this, "Result Permission Grant CODE_BATTERY_STATS", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    private static class ADAPTER extends BaseAdapter{


        Context context;
        DownloadMessage downlaod;
        ArrayList<Task> taskList;
        public ADAPTER(Context context,DownloadMessage downlaod,ArrayList<Task> task){
            this.context = context;
            this.downlaod = downlaod;
            taskList = task;

        }

        @Override
        public int getCount() {
            return taskList.size();
        }

        @Override
        public Object getItem(int position) {
            return taskList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView =   View.inflate(context,R.layout.item_download,null);

            final ProgressBar p = (ProgressBar) convertView.findViewById(R.id.progressBar);
            final TextView text = (TextView) convertView.findViewById(R.id.textView);

//            convertView.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    final int taskId = downlaod.addTask(taskList.get(position), new DownloadUiListener() {
//                        @Override
//                        public void UiStrat() {
//                            Log.d("TAG", "UiStrat: ");
//                        }
//
//                        @Override
//                        public void UiProgress(Task task,long TotalSize ,int downloadSize) {
//                            Log.d("TAG", "UiProgress------------------->: "+downloadSize);
//                            text.setText(Utlis.formatPercent(downloadSize,TotalSize));
//                            p.setMax((int) TotalSize);
//                            p.setProgress(downloadSize);
//                        }
//
//                        @Override
//                        public void UiFinish(Task task) {
//                            Log.d("TAG", "UiFinish: ");
//
//                        }
//                    });
//                    downlaod.startDownload(taskId);
//                }
//            });
            return convertView;
        }
    }
}
