package example.com.sunshine.Main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import example.com.sunshine.GridAdapter;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class GridActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        setRequestAdapter();

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
