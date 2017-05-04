package example.com.sunshine.download.Fragment;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import javax.inject.Inject;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class AnchorFragment extends BaseFragment{



    @Inject
    public AnchorFragment() {

    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anchor;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {


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
