package example.com.sunshine.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class GridDetailAcitivty extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_grid);
        Intent mTitle = getIntent();
        String title = mTitle.getStringExtra("title");
        mToolbar = (Toolbar)findViewById(R.id.app_bar);
        mToolbar.setTitle(title);
    }
}
