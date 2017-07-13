package example.com.sunshine.download;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/4/20.
 */

public class DownloadActivity extends AppCompatActivity {
    public static final class TYPE {
        public static final int TYPE_LISTVIEW = 0;
        public static final int TYPE_RECYCLERVIEW = 1;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);


        findViewById(R.id.down_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadActivity.this, DownloadFinishActivity.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        int type = intent.getIntExtra("EXTRA_TYPE", TYPE.TYPE_LISTVIEW);

        if (savedInstanceState == null) {
            Fragment fragment =
                    type == TYPE.TYPE_LISTVIEW ?
                            new DownFragment() : new DownRecycherViewFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.framelayout, fragment)
                    .commit();
        }

    }
}
