package example.com.sunshine.download.Fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;

import javax.inject.Inject;

import example.com.sunshine.GridAdapter;
import example.com.sunshine.R;

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
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,2));
        recyclerView.setAdapter(new GridAdapter(getActivity()));

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
