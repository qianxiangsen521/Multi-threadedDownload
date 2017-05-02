package example.com.sunshine.download;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Callable;

import example.com.sunshine.R;
import example.com.sunshine.download.Rxjava.SomeType;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qianxiangsen on 2017/4/20.
 */

public class DownloadActivity extends AppCompatActivity /*implements DownloadUiListener,View.OnClickListener*/{

//    private DownloadMessage downlaod;
    int permsRequestCode = 200;
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
    //    private final CompositeDisposable disposables = new CompositeDisposable();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout,new DownFragment()).commit();
        Log.d("DownloadActivity", "---------------------------------------onCreate: ");
//        SomeType someType = new SomeType();
//        someType.setValue("name");
//        io.reactivex.Observable<String> observable = someType.justObservable();
//        observable.subscribe(new Consumer<String>(){
//            @Override
//            public void accept(@io.reactivex.annotations.NonNull String s) throws Exception {
//                Log.d("TAG", "accept: "+s+"----"+ Thread.currentThread().getName());
//            }
//        });
//        onRunSchedulerExampleButtonClicked();

        PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
        findViewById(R.id.down_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadActivity.this,DownloadFinishActivity.class);
                startActivity(intent);
            }
        });



//        ProgressBar = (ProgressBar) findViewById(R.id.progressBar);
//        textView = (TextView) findViewById(R.id.textView);
//        ProgressBar1 = (ProgressBar) findViewById(R.id.progressBar1);

//        mButton3= (TextView) findViewById(R.id.button3);
//        mButton5 = (TextView) findViewById(R.id.button5);
//
//        size= (TextView) findViewById(R.id.textView_size);
//        size1 = (TextView) findViewById(R.id.textView_size1);
//
//        mButton4 = (Button) findViewById(R.id.button4);
//        mButton6 = (Button) findViewById(R.id.button6);
//        speed= (TextView) findViewById(R.id.textView_speed);
//        speed1 = (TextView) findViewById(R.id.textView_speed1);
//        mButton3.setOnClickListener(this);
//        mButton4.setOnClickListener(this);
//        mButton5.setOnClickListener(this);
//        mButton6.setOnClickListener(this);

//
//        final Task task = new Task();
//        task.setId(10);
//        task.setmUniquely_id("111");
//        task.setUrl(Constants.URL);
//        taskId = downlaod.addTask(task, new DownloadUiListener() {
//            @Override
//            public void UiStrat() {
//
//            }
//
//            @Override
//            public void UiProgress(Task task, long TotalSize, int downloadSize) {
//                Log.d("TAG", "UiProgress------------------->: "+downloadSize);
//                size.setText(Utlis.formatSize(downloadSize)+"/"+Utlis.formatSize(TotalSize));
//                textView1.setText(Utlis.formatPercent(downloadSize,TotalSize));
//                speed.setText(task.getSpeed()+
//                         "Kb/s");
//                ProgressBar1.setMax((int) TotalSize);
//                ProgressBar1.setProgress(downloadSize);
//            }
//
//            @Override
//            public void UiFinish(Task task) {
//
//            }
//        });
//
//        final Task t = new Task();
//        t.setId(11);
//        t.setmUniquely_id("111");
//        t.setUrl(Constants.URL1);
//
//        tas = downlaod.addTask(t, new DownloadUiListener() {
//            @Override
//            public void UiStrat() {
//
//            }
//            @Override
//            public void UiProgress(Task task, long TotalSize, int downloadSize) {
//                size1.setText(Utlis.formatSize(downloadSize)+"/"+Utlis.formatSize(TotalSize));
//                speed1.setText(task.getSpeed()+
//                        "Kb/s");
//                Log.d("TAG", "UiProgress------------------->: "+downloadSize);
//                textView.setText(Utlis.formatPercent(downloadSize,TotalSize));
//                ProgressBar.setMax((int) TotalSize);
//                ProgressBar.setProgress(downloadSize);
//            }
//
//            @Override
//            public void UiFinish(Task task) {
//                mButton3.setText("安装");
//                mButton5.setText("安装");
//            }
//        });
    }
//    void onRunSchedulerExampleButtonClicked() {
//        disposables.add(sampleObservable().
//                subscribeOn(Schedulers.io()).
//                observeOn(AndroidSchedulers.mainThread()).
//                subscribeWith(new DisposableObserver<String>(){
//
//                    @Override
//                    protected void onStart() {
//                        super.onStart();
//
//                    }
//
//                    @Override
//                    public void onNext(@io.reactivex.annotations.NonNull String o) {
//                        Log.d("TAG", "main: "+Thread.currentThread().getName()+"-----"+o);
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//
//                    }
//                }));
//
//    }
//
//    static io.reactivex.Observable<String> sampleObservable(){
//        return io.reactivex.Observable.defer(new Callable<ObservableSource<? extends String>>() {
//            @Override
//            public ObservableSource<? extends String> call() throws Exception {
//                SystemClock.sleep(5000);
//                Log.d("TAG", "call: "+Thread.currentThread().getName());
//                return io.reactivex.Observable.just("itme");
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.button3:
//
//                if (tas.getState().equals(Task.STATUS_START) ){
//                    downlaod.pauseDownload(tas);
//                    mButton3.setText("继续");
//                }else{
//                    downlaod.startDownload(tas);
//                    mButton3.setText("暂停");
//
//                }
//
//                break;
//            case R.id.button5:
//                if (taskId.getState().equals(Task.STATUS_START)){
//                    downlaod.pauseDownload(taskId);
//                    mButton5.setText("继续");
//                }else{
//                    downlaod.startDownload(taskId);
//                    mButton5.setText("暂停");
//                }
//
//                break;
//            case R.id.button4:
//
//                break;
//            case R.id.button6:
//
//
//                break;
//
//        }
//
//    }
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

    @Override protected void onDestroy() {
        super.onDestroy();
        Log.d("DownloadActivity", "---------------------------------------onDestroy: ");
//        disposables.clear();
//        downlaod.shutdown();
    }

}
