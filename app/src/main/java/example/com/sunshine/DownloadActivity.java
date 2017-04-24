/*
package example.com.sunshine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import java.io.IOException;

*/
/**
 * Created by qianxiangsen on 2017/4/19.
 *//*


public class DownloadActivity extends AppCompatActivity implements DownloadManagerListener {

    private ProgressBar mProgressBar;
    private Button mStart;
    private Button mStop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mStart = (Button)findViewById(R.id.button3) ;
        mStop = (Button)findViewById(R.id.button4) ;

        final  DownloadManagerPro dm = new DownloadManagerPro(this);
        dm.init("downloadManager/", 12, this);

        final int taskToken = dm.addTask("save_name", "http://dl.sunshinefm.cn/yinpin_web3/2017/02/17/357d6a1ed30ffab25b49fec62d6c1c5f.3gp", false, false);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dm.startDownload(taskToken);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dm.pauseDownload(taskToken);

            }
        });
    }

    @Override
    public void OnDownloadStarted(long taskId) {
        Log.d("TAG", "OnDownloadStarted: ");

    }

    @Override
    public void OnDownloadPaused(long taskId) {
        Log.d("TAG", "OnDownloadPaused: ");
    }

    @Override
    public void onDownloadProcess(long taskId, double percent, long downloadedLength) {
        int c = (int)((100 * downloadedLength) / percent);
        Log.d("TAG", "onDownloadProcess: "+c);
        mProgressBar.setProgress(c);

    }

    @Override
    public void OnDownloadFinished(long taskId) {
        Log.d("TAG", "OnDownloadFinished: ");
    }

    @Override
    public void OnDownloadRebuildStart(long taskId) {
        Log.d("TAG", "OnDownloadRebuildStart: ");
    }

    @Override
    public void OnDownloadRebuildFinished(long taskId) {
        Log.d("TAG", "OnDownloadRebuildFinished: ");
    }

    @Override
    public void OnDownloadCompleted(long taskId) {
        Log.d("TAG", "OnDownloadCompleted: ");
    }

    @Override
    public void connectionLost(long taskId) {
        Log.d("TAG", "connectionLost: ");
    }
}
*/
