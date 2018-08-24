package example.com.sunshine.download.Fragment;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import java.util.ArrayList;

import javax.inject.Inject;

import example.com.sunshine.R;
import example.com.sunshine.download.Adapter.pag.BasePagAdapter;
import example.com.sunshine.download.request.CategoryRadioInfo;
import example.com.sunshine.download.request.CnrLog;
import example.com.sunshine.download.request.JsonDataFactory;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class AnchorFragment extends BaseFragment{

    private RecyclerView recyclerView;
    @Inject
    public AnchorFragment() {

    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anchor;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {
        recyclerView =  (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        BaseAdapterExample baseAdapterExample = new BaseAdapterExample(loadData());
        BasePagAdapter basePagAdapter = new BasePagAdapter(loadData(), null);
        recyclerView.setAdapter(basePagAdapter);

    }
    private ArrayList<CategoryRadioInfo> loadData(){

       return JsonDataFactory.getHomeCategoryList(CnrLog.bottonjson);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void OnClick(View v) {

    }

//    noteStateNotSaved


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }
}
