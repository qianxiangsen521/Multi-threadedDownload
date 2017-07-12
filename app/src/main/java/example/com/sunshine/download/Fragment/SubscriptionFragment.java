package example.com.sunshine.download.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;

import javax.inject.Inject;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class SubscriptionFragment extends BaseFragment{

    @Inject
    public SubscriptionFragment() {

    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_subscription;
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
