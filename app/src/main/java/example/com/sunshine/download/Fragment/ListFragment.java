package example.com.sunshine.download.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import example.com.sunshine.Exo.ExoConstants;
import example.com.sunshine.Exo.ExoService;
import example.com.sunshine.Exo.PlayActivity;
import example.com.sunshine.Exo.PlayManager;
import example.com.sunshine.Exo.Utils.IConstants;
import example.com.sunshine.Exo.Utils.MusicInfo;
import example.com.sunshine.Exo.Utils.MusicUtils;
import example.com.sunshine.HTTP.HttpMessage;
import example.com.sunshine.R;
import example.com.sunshine.download.Adapter.ListDetailAdapter;
import example.com.sunshine.download.Adapter.RecyclerViewAdapterExample;
import example.com.sunshine.download.Http.Configuration;
import example.com.sunshine.download.request.CategoryRadioInfo;
import example.com.sunshine.download.request.CnrLog;
import example.com.sunshine.download.request.JsonDataFactory;
import example.com.sunshine.download.request.VolleySingleton;
import example.com.sunshine.entity.HomeEntity;
import example.com.sunshine.util.Util;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class ListFragment extends BaseFragment{

    private RecyclerView recycler;
    private List<CategoryRadioInfo> categoryRadioInfos;
    private RecyclerViewAdapterExample recyclerViewAdapterExample;
    @Inject
    public ListFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {
        loadData();
        recycler = (RecyclerView)rootView.findViewById(R.id.recyclerview);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapterExample = new RecyclerViewAdapterExample(getActivity(),categoryRadioInfos);
        recycler.setAdapter(recyclerViewAdapterExample);

    }


    private void loadData() {

        categoryRadioInfos = JsonDataFactory.getHomeCategoryList(CnrLog.bottonjson);

    }


    @Override
    protected void initData() {
    }

    @Override
    protected void OnClick(View v) {

    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }
}
