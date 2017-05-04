package example.com.sunshine.download.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import javax.inject.Inject;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class UserFragment extends BaseFragment{



    @Inject
    public UserFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
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
