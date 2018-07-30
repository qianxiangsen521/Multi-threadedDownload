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
import example.com.sunshine.download.Http.Configuration;
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

    private RecyclerView recyclerView;
    private ListDetailAdapter listDetailAdapter;

    private final CompositeDisposable disposables = new CompositeDisposable();


    private ArrayList<MusicInfo> artistList = new ArrayList<>();

    @Inject
    public ListFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        listDetailAdapter = new ListDetailAdapter(artistList);
        recyclerView.setAdapter(listDetailAdapter);
        recyclerView.setHasFixedSize(true);
        setItemDecoration();
        reloadAdapter();
    }

    static Observable<ArrayList<MusicInfo>> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<ArrayList<MusicInfo>>>() {
            @Override public ObservableSource<ArrayList<MusicInfo>> call() throws Exception {
                ArrayList<MusicInfo> artistList = (ArrayList) MusicUtils.
                        queryMusic(mContext, 1 + "", IConstants.START_FROM_ARTIST);

                return Observable.just(artistList);
            }
        });
    }
    public void reloadAdapter() {

        showLoading();
        disposables.add(sampleObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<MusicInfo>>() {
                    @Override public void onComplete() {
                        Log.d("TAG", "onComplete()");
                    }

                    @Override public void onError(Throwable e) {
                        Log.e("TAG", "onError()", e);
                    }

                    @Override public void onNext(ArrayList<MusicInfo> string) {
                        refreshView();
                        listDetailAdapter.addData(string);
                    }
                }));
    }

    private void setItemDecoration() {
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
    }


    @Override
    protected void initData() {
    }

    @Override
    protected void OnClick(View v) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }



    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }
}
