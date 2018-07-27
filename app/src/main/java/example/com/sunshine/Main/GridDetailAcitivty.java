package example.com.sunshine.Main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import example.com.sunshine.R;
import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.Utils.ImageUtils;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class GridDetailAcitivty extends AppCompatActivity {

    Toolbar mToolbar;

    ImageView image_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_grid);
        Intent mTitle = getIntent();
        String imgUrl = mTitle.getStringExtra("image");
        String title = mTitle.getStringExtra("title");
        mToolbar = (Toolbar)findViewById(R.id.app_bar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        image_view = (ImageView) findViewById(R.id.image_view);
//        int topicWidth = createTopicWidth();
//        image_view.getLayoutParams().width = topicWidth;
//        image_view.getLayoutParams().height = topicWidth;
//        ImageUtils.loadImg(this,imgUrl,R.mipmap.home_recommend_album_bg,image_view);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private int createTopicWidth() {
        int width = Math.min(TLiveApplication.mScreenWidth,
                TLiveApplication.mScreenHeight);
        int margin = (int) (5D * (double) getResources()
                .getDimensionPixelSize(R.dimen.topic_item_margin));
        return (int) ((double) (width - margin) / 3 + (double) (width - margin) % 3);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        ActivityCompat.finishAfterTransition(this);
    }
}
