package example.com.sunshine.download;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.com.sunshine.GridAdapter;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class DownloadFinishActivity extends AppCompatActivity{
    private DownloadMessage downlaod;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_finish);
        ButterKnife.bind(this);
        downlaod = DownloadMessage.init(this);

        DownloadFinishAdapter downloadFinishAdapter = new DownloadFinishAdapter(this,downlaod.getmArrayFinish());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(downloadFinishAdapter);
        getSupportActionBar().setTitle("下载完成");

    }



    @Override
    protected void onPause() {
        super.onPause();
    }
}
