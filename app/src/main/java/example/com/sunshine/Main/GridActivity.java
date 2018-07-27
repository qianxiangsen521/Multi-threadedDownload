package example.com.sunshine.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import example.com.sunshine.GridAdapter;
import example.com.sunshine.R;
import example.com.sunshine.download.Home.BaseActivity;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class GridActivity extends BaseActivity{

    private RecyclerView recyclerView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setRequestAdapter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grid;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void OnClick(View v) {

    }


    private void setRequestAdapter(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(new GridAdapter(this));

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}
