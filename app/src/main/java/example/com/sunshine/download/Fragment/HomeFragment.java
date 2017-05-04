package example.com.sunshine.download.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.com.sunshine.R;
import example.com.sunshine.download.DownFragment;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class HomeFragment extends BaseFragment{

    @Bind(R.id.ib_download)
    TextView ib_download;
    @Bind(R.id.viewpager)
    ViewPager viewPager;

    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Inject
    public HomeFragment() {
    }
    @Inject
    DownFragment downFragment;

    @Inject
    RecommendFragment recommendFragment;

    @Inject
    PopularFragment popularFragment;

    @Inject
    CategoryFragment categoryFragment;

    @Inject
    ListFragment listFragment;

    @Inject
    AnchorFragment anchorFragment;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this,rootView);
        ib_download.setOnClickListener(this);

        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void OnClick(View v) {

        switch (v.getId()){
            case R.id.ib_download:
                addFragment(R.id.fragment_full,downFragment);
                break;
        }

    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(recommendFragment, "推荐");
        adapter.addFragment(popularFragment, "热门");
        adapter.addFragment(categoryFragment, "分类");
        adapter.addFragment(listFragment, "榜单");
        adapter.addFragment(anchorFragment, "主播");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
