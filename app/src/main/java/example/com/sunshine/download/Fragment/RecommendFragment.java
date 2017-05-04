package example.com.sunshine.download.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.com.sunshine.R;
import example.com.sunshine.download.Adapter.RecommendAdapter;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class RecommendFragment extends BaseFragment{


    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    @Inject
    RecommendAdapter recommendAdapter;

    @Inject
    public RecommendFragment() {
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recommend;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this,rootView);

        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(recommendAdapter);


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
