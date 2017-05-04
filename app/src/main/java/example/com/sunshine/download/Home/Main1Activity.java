package example.com.sunshine.download.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.com.sunshine.R;
import example.com.sunshine.download.Fragment.CategoryFragment;
import example.com.sunshine.download.Fragment.DiscoverFragment;
import example.com.sunshine.download.Fragment.HomeFragment;
import example.com.sunshine.download.Fragment.SubscriptionFragment;
import example.com.sunshine.download.Fragment.UserFragment;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class Main1Activity extends BaseActivity implements View.OnClickListener{

    //当前tab位置
    public static int mFragCurrentIndex = 0;

    // 创建Fragment管理器
    private FragmentManager fragmentManager;

    @Bind(R.id.fragment_title)
    RadioGroup radioGroup;

    @Bind(R.id.find)
    RadioButton radioButton1;

    @Bind(R.id.custom)
    RadioButton radioButton2;

    @Bind(R.id.square)
    RadioButton radioButton3;

    @Bind(R.id.myspace)
    RadioButton radioButton4;

    @Inject
    HomeFragment homeFragment;
    @Inject
    SubscriptionFragment subscriptionFragment;
    @Inject
    DiscoverFragment discoverFragment;
    @Inject
    UserFragment hotPlayFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mFragCurrentIndex = savedInstanceState.getInt("curChoice", 0);
        }

        //移除fragment覆盖部分
        getWindow().setBackgroundDrawable(null);

        switchFragment(mFragCurrentIndex, true);

        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton4.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void OnClick(View v) {

    }


    /**
     * fragment切换
     *
     * @param index
     */
    private void switchFragment(int index, boolean isFirst) {
        setSelected(index, true);
        if (mFragCurrentIndex == index && !isFirst) {
            return;
        }

        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }
        Fragment fragmentNow = fragmentManager.findFragmentByTag("home"
                + mFragCurrentIndex);
        if (fragmentNow != null) {

            fragmentManager.beginTransaction().hide(fragmentNow).commit();
        }
        Fragment fragment = fragmentManager.findFragmentByTag("home" + index);
        if (fragment == null) {
            fragment = createMainFragment(index);
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment,
                            "home" + index).commit();
        } else {
            fragmentManager.beginTransaction().show(fragment).commit();
        }
        mFragCurrentIndex = index;
    }
    @Override
    public void onClick(View v) {
        setSelected(mFragCurrentIndex, false);
        switch (v.getId()) {
            case R.id.find:
                switchFragment(0, false);
                break;
            case R.id.custom:
                switchFragment(1, false);
                break;
            case R.id.square:
                switchFragment(2, false);
                break;
            case R.id.myspace:
                switchFragment(3, false);
                break;
        }
    }
    // 把显示的Fragment隐藏
    private void setSelected(int pos, boolean isSelected) {
        if (pos == 0) {
            radioButton1.setSelected(isSelected);
        } else if (pos == 1) {
            radioButton2.setSelected(isSelected);
        } else if (pos == 2) {
            radioButton3.setSelected(isSelected);
        } else {
            radioButton4.setSelected(isSelected);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mFragCurrentIndex);
    }
    /**
     * @param index
     */
    private Fragment createMainFragment(int index) {
        Fragment fragment = null;
        switch (index) {
            case 0:
                fragment = homeFragment;
                break;
            case 1:
                fragment = subscriptionFragment;
                break;
            case 2:
                fragment = discoverFragment;
                break;
            case 3:
                fragment = hotPlayFragment;
                break;
            default:
                break;
        }
        return fragment;
    }
}
