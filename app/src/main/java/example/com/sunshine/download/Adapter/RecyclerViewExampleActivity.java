package example.com.sunshine.download.Adapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import example.com.sunshine.R;
import example.com.sunshine.download.request.CategoryRadioInfo;
import example.com.sunshine.download.request.CnrLog;
import example.com.sunshine.download.request.HomeBottomResponse;
import example.com.sunshine.download.request.JsonDataFactory;

public class RecyclerViewExampleActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<CategoryRadioInfo> categoryRadioInfos;
    private RecyclerViewAdapterExample recyclerViewAdapterExample;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_example);
        loadData();
        recycler = (RecyclerView)findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapterExample = new RecyclerViewAdapterExample(this,categoryRadioInfos);
        recycler.setAdapter(recyclerViewAdapterExample);

    }

    private void loadData() {

        categoryRadioInfos = JsonDataFactory.getHomeCategoryList(CnrLog.bottonjson);

    }


}
