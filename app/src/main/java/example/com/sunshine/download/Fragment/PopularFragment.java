package example.com.sunshine.download.Fragment;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.sunshine.Exo.ExoConstants;
import example.com.sunshine.Exo.PlayActivity;
import example.com.sunshine.Main.GridActivity;
import example.com.sunshine.Main.GridDetailAcitivty;
import example.com.sunshine.R;
import example.com.sunshine.download.Adapter.HeaderPagerAdapter;
import example.com.sunshine.download.Adapter.HomeGridViewAdapter;
import example.com.sunshine.download.Adapter.HomePageGVAdapter;
import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.Home.PopularDetailsActivity;
import example.com.sunshine.download.Http.Configuration;
import example.com.sunshine.download.Http.SystemUtils;
import example.com.sunshine.download.Http.entity.BaseResponse;
import example.com.sunshine.download.Http.entity.RadioInfo;
import example.com.sunshine.download.Utils.DetailsTransition;
import example.com.sunshine.download.Utils.Font;
import example.com.sunshine.download.Utils.MyGridView;
import example.com.sunshine.download.request.CategoryRadioInfo;
import example.com.sunshine.download.request.HomeBottomResponse;
import example.com.sunshine.download.request.HomeTopResponse;
import example.com.sunshine.download.request.HttpManger;
import example.com.sunshine.util.Util;
import example.com.sunshine.view.ScrollListenerHorizontalScrollView;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class PopularFragment extends BaseFragment {

    private String url = "http://dl.sunshinefm.cn/yinpin_web3/2017/07/10/fa36fdc3c9f082756e9b0f249857d6a0.3gp";
    private String url1 = "http://dl.sunshinefm.cn/yinpin_web3/2017/06/30/d5ebc8d78a5a927af9a122111d4e32cc.3gp";

    private String[] strings = new String[]{url1,url, ExoConstants.PLAY_URL_NAME};
    private ArrayList<CategoryRadioInfo> categoryRadioInfos;
    @BindView(R.id.homepagev_listview)
    LinearLayout mContainer;
    @BindView(R.id.scrollview)
    ScrollView scrollView;

    View homeView;

    private boolean isTimer;//轮播图是否已经开始

    private View topicView;

    private HomePageGVAdapter homeBottomGvAdapter;

    private ViewPager headerViewPager;

    private HeaderPagerAdapter headerPagerAdapter;


    private TextView homeCategoryName;

    private LinearLayout homeCategoryLLMore;

    public ImageView[] arrayOfImageView;

    private MyGridView homeBottomGv;


    // 焦点图下面推荐专辑
    private GridView homeGridView;

    ImageView scrollImg;

    private ArrayList<RadioInfo> headerRadioInfos;

    // 推荐专辑下面的推荐分类
    private HomeGridViewAdapter homeGridViewAdapter;

    private LinearLayout dotLinearLayout;
    private Handler pagerHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            headerViewPager.setCurrentItem(msg.what);
        }
    };
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case Configuration.HOME_BOTTOM_FLAG:
                    ArrayList<CategoryRadioInfo> radioInfos = (ArrayList<CategoryRadioInfo>) msg.obj;
                    setBottomView(radioInfos);

                    break;
            }
        }
    };

    @Inject
    public PopularFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {
        ButterKnife.bind(this, rootView);
        homeView = this.getView();
        topicView = LayoutInflater.from(this.getActivity()).inflate(
                R.layout.home_top_part, null);

        headerViewPager = (ViewPager) topicView
                .findViewById(R.id.home_header_pager);

        dotLinearLayout = (LinearLayout) topicView
                .findViewById(R.id.dot_linearlayout);

        headerPagerAdapter = new HeaderPagerAdapter(this);
        headerViewPager.setAdapter(headerPagerAdapter);
        headerViewPager.setOnTouchListener(new HeaderViewOnTouchListener());
        headerViewPager.setCurrentItem(1);
        headerViewPager
                .setOnPageChangeListener(new HeaderViewOnPageChangeListener());
        int width = createTopicWidth() + 50;
        homeGridView = (GridView) topicView
                .findViewById(R.id.home_center_gridview);
        scrollImg = (ImageView) topicView
                .findViewById(R.id.home_center_scroll_img);
        RelativeLayout rlCenter = (RelativeLayout) topicView
                .findViewById(R.id.rl_center);
        scrollImg.getLayoutParams().height = width;
        rlCenter.getLayoutParams().height = width;
        ScrollListenerHorizontalScrollView horizontalScrollView = (ScrollListenerHorizontalScrollView) topicView
                .findViewById(R.id.hor_view);

        horizontalScrollView.setHandler(mHandler);
        horizontalScrollView
                .setOnScrollStateChangedListener(new ScrollListenerHorizontalScrollView.ScrollViewListener() {

                    @Override
                    public void onScrollChanged(ScrollListenerHorizontalScrollView.ScrollType scrollType) {
                        // TODO Auto-generated method stub
                        if (scrollType == ScrollListenerHorizontalScrollView.ScrollType.IDLE) {
                            scrollImg.setVisibility(View.VISIBLE);
                        } else if (scrollType == ScrollListenerHorizontalScrollView.ScrollType.TOUCH_SCROLL) {
                            scrollImg.setVisibility(View.INVISIBLE);
                        }
                    }
                });
        homeGridView.getLayoutParams().height = createTopicWidth();
        homeGridViewAdapter = new HomeGridViewAdapter(this.getActivity());
        homeGridView.setAdapter(homeGridViewAdapter);
        addHeaderView(mContainer);
        homeGridView.setOnItemClickListener(new HomeGvListener());

    }

    class HomeGvListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            RadioInfo radioInfo =(RadioInfo) parent.getAdapter().getItem(position);
//            ImageView imageView = (ImageView)view.findViewById(R.id.home_center_img);
//            Intent intent = new Intent(getActivity(), PopularDetailsActivity.class);
//
//            intent.putExtra("title", radioInfo.getAlbum_name());
//            intent.putExtra("image",radioInfo.getImgUrl());
//                Bundle bundle = ActivityOptionsCompat
//                    .makeSceneTransitionAnimation(getActivity(),
//                            imageView,
//                            imageView.getTransitionName())
//                    .toBundle();
//
//            if (Build.VERSION.SDK_INT >= 21) {
//                getActivity().startActivity(intent, bundle);
//            } else {
//                intent.putExtras(bundle);
//               getActivity().startActivity(intent);
//            }
            startActivity(GridActivity.class);
        }
    }

    class HomeBottomGvListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            RadioInfo radioInfo =(RadioInfo) parent.getAdapter().getItem(position);

            GridDetailFragment kittenDetails = GridDetailFragment.newInstance(
                    "transition" + position
                    ,radioInfo.getImgUrl()
            );
            // Note that we need the API version check here because the actual transition classes (e.g. Fade)
            // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
            // ARE available in the support library (though they don't do anything on API < 21)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                kittenDetails.setSharedElementEnterTransition(new DetailsTransition());
                kittenDetails.setEnterTransition(new Fade());
                setExitTransition(new Fade());
                kittenDetails.setSharedElementReturnTransition(new DetailsTransition());
            }

            ImageView imageView = (ImageView)view.findViewById(R.id.gv_radio_img);
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addSharedElement(imageView,
                            imageView.getTransitionName()
                            )
                    .replace(R.id.fragment_full, kittenDetails)
                    .addToBackStack(null)
                    .commit();


        }
    }

    // 焦点图点的处理
    class HeaderViewOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onPageSelected(int position) {
            int pageCount = headerPagerAdapter.radioInfos.size(); // 7
            if (position == 0) {
                currentImageItem = pageCount - 2;// 5
            } else if (position == pageCount - 1) {
                currentImageItem = 1;// 1
            } else {
                currentImageItem = position;
            }
            headerViewPager.setCurrentItem(currentImageItem, false);
            if (null == arrayOfImageView)
                return;
            int showViewId = position % arrayOfImageView.length;
            for (int i = 0; i < arrayOfImageView.length; i++) {
                if (i == showViewId) {
                    arrayOfImageView[showViewId]
                            .setImageResource(R.mipmap.advertis_tit_point_sl);
                } else {
                    arrayOfImageView[i]
                            .setImageResource(R.mipmap.advertis_tit_point_usl);
                }
            }
        }
    }

    private void addHeaderView(LinearLayout mContainer) {
        if (mContainer == null)
            return;

        if (topicView != null) {
            mContainer.addView(topicView);

        }
    }

    // headerVIew滑动效果
    class HeaderViewOnTouchListener implements View.OnTouchListener {
        private long time;

        private float x;

        private float y;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            PointF downP = new PointF();
            PointF curP = new PointF();
            int act = event.getAction();
            if (act == MotionEvent.ACTION_DOWN) {
                time = System.currentTimeMillis();
                x = event.getX();
                y = event.getY();
            }
            if (act == MotionEvent.ACTION_DOWN
                    || act == MotionEvent.ACTION_MOVE) {
                // 滑动时把计时器移除
                pauseTimer();
                ((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
                if (downP.x == curP.x && downP.y == curP.y) {
                    return false;
                }
            } else if (act == MotionEvent.ACTION_UP) {
                // 离开触摸时间添加计时器
                timerOfHeader();
                long timeDx = System.currentTimeMillis() - time;
                float dx = Math.abs(event.getX() - x);
                float dy = Math.abs(event.getY() - y);
                if (timeDx < 500 && dx < 25 && dy < 25) {
                    Object obj = ((HeaderPagerAdapter) ((ViewPager) v)
                            .getAdapter()).radioInfos.get(((ViewPager) v)
                            .getCurrentItem());

                    if (obj == null) {
                        return false;
                    }
                }
            }
            return false;
        }
    }

    private Timer timer = null;

    private int currentImageItem = 0;

    // ViewPager自动滑动定时器
    private void timerOfHeader() {
        isTimer = true;
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                synchronized (headerViewPager) {
                    currentImageItem = (currentImageItem + 1);
                    int pageCount = headerPagerAdapter.radioInfos.size(); // 7
                    if (currentImageItem == 0) {
                        currentImageItem = pageCount - 2;// 5
                    } else if (currentImageItem == pageCount - 1) {
                        // currentImageItem = 1;// 1
                    } else {
                        currentImageItem = currentImageItem;
                    }
                    Message msg = new Message();
                    msg.what = currentImageItem;

                    pagerHandler.sendMessage(msg);
                }
            }
        }, 3000, 3000);
    }

    // 停止定时器
    private void pauseTimer() {
        if (timer == null) {
            return;
        }
        timer.cancel();
        timer.purge();
        timer = null;
    }

    // 加载顶部焦点图数据
    private void loadHomeHeaderData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("version", SystemUtils.getAppVersionName(getActivity()));
        HttpManger.getInstance(getActivity(), this).sendPostRequest(Configuration.HOME_HEADER_RUL, map, true);
    }

    // 加载底部分类数据
    private void loadHomeBottomData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sn", SystemUtils.getMd5UniqueID(this.getActivity()));
        HttpManger.getInstance(getActivity(), this).sendPostRequest(Configuration.HOME_BOTTOM_URL, map, true);
    }

    @Override
    public void onDataReady(BaseResponse response) {


        if (response instanceof HomeTopResponse) {
            HomeTopResponse topResponse = (HomeTopResponse) response;
            headerRadioInfos = (ArrayList) topResponse.getRadioInfos();

            setTopView(headerRadioInfos);
            return;
        }
        if (response instanceof HomeBottomResponse) {
            if (mContainer.getChildCount() != 1) {//如果不是1说明是缓存数据，先remove掉
                mContainer.removeViews(1, mContainer.getChildCount() - 1);
            }
            HomeBottomResponse bottomResponse = (HomeBottomResponse) response;
            setBottomView(bottomResponse.getCategoryRadioInfos());
            refreshView();
        }
    }

    private void setBottomView(List<CategoryRadioInfo> categoryInfos) {
        categoryRadioInfos = (ArrayList) categoryInfos;

        if (categoryRadioInfos != null) {
            for (int i = 0; i < categoryRadioInfos.size(); i++) {
                CategoryRadioInfo cateRadioInfo = categoryRadioInfos
                        .get(i);
                if (cateRadioInfo != null
                        && cateRadioInfo.getIndexType() == 1) {
                    if (cateRadioInfo.getRadioInfos() != null) {
                        homeGridView.setVisibility(View.VISIBLE);
                        homeGridViewAdapter.radioInfos = cateRadioInfo
                                .getRadioInfos();
                        setGridView(homeGridView, createTopicWidth(),
                                cateRadioInfo.getRadioInfos().size());
                        homeGridViewAdapter.notifyDataSetChanged();
                    }
                } else if (cateRadioInfo.getIndexType() == 2) {

                    String moreUrl = cateRadioInfo.getMoreContent();
                    if (cateRadioInfo.getRadioInfos() == null
                            || cateRadioInfo.getRadioInfos().size() == 0) {
                        continue;
                    }
                    int isFee = cateRadioInfo.getRadioInfos().get(0)
                            .getIs_fee();
                    RelativeLayout homelin = (RelativeLayout) LayoutInflater.from(getActivity())
                            .inflate(R.layout.home_category_top, null);
                    if (!TextUtils.isEmpty(moreUrl)) {
                        homeCategoryLLMore = (LinearLayout) homelin
                                .findViewById(R.id.home_ll_more);
                        homeCategoryLLMore.setVisibility(View.VISIBLE);
                        TextView moreText = (TextView) homelin
                                .findViewById(R.id.text_more);
                        moreText.setTypeface(Font.getFont());
                        moreText.getPaint().setFakeBoldText(true);
                        int recomm_position_id = cateRadioInfo
                                .getRadioInfos().get(0)
                                .getRecommend_pos_id();
                        homeCategoryLLMore
                                .setOnClickListener(new HomeMoreClickListener(
                                        moreUrl,
                                        isFee,
                                        cateRadioInfo.getTitle(),
                                        String.valueOf(recomm_position_id)));
                        if (cateRadioInfo.getRadioInfos().size() != 3) {// 说明是推荐，隐藏更多按钮
                            homeCategoryLLMore.setVisibility(View.GONE);
                        }
                    }
                    homeCategoryName = (TextView) homelin
                            .findViewById(R.id.home_category_title);
                    homeCategoryName.setTypeface(Font.getFont());
                    homeCategoryName.getPaint().setFakeBoldText(true);
                    homeBottomGv = (MyGridView) LayoutInflater.from(getActivity()).inflate(
                            R.layout.home_category_bottom_gv, null);
                    homeBottomGvAdapter = new HomePageGVAdapter(
                            cateRadioInfo.getRadioInfos(),
                            getActivity());
                    homeCategoryName.setText(cateRadioInfo.getTitle());
                    homeBottomGv.setAdapter(homeBottomGvAdapter);
                    mContainer.addView(homelin);
                    mContainer.addView(homeBottomGv);
                    homeBottomGv
                            .setOnItemClickListener(new HomeBottomGvListener());
                }
            }
        }
    }

    class HomeMoreClickListener implements View.OnClickListener {
        private String url;

        private int isFee;

        private String titleName;

        private String recommend_pos_id;

        public HomeMoreClickListener(String url, int isFee, String titleName,
                                     String recommend_pos_id) {
            this.url = url;
            this.isFee = isFee;
            this.titleName = titleName;
            this.recommend_pos_id = recommend_pos_id;
        }

        @Override
        public void onClick(View v) {

//            if (isFee != Configuration.TYPE_FEE) {
//                Intent intent = new Intent(HomeFragment.this.getActivity(),
//                        CommonHomeMoreAndFirCategoryActivity.class);
//                intent.putExtra("url", url);
//                startActivity(intent);
//            } else {
//                Intent intent = new Intent(HomeFragment.this.getActivity(),
//                        CategoryMoreActivity.class);
//                intent.putExtra("url", url);
//                intent.putExtra("title_name", titleName);
//                intent.putExtra("is_fee", isFee);
//                intent.putExtra("recommend_pos_id", recommend_pos_id);
//                startActivity(intent);
//            }
        }

    }

    private void setGridView(GridView gv, int length, int size) {

        DisplayMetrics dm = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        float density = dm.density;
        // int gridviewWidth = (int) (size * (length + 4) * density);
        // int itemWidth = (int) (length * density);
        int itemWidth = length;
        int gridviewWidth = size * (length + 50);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gv.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gv.setColumnWidth(itemWidth); // 设置列表项宽
        gv.setHorizontalSpacing(50); // 设置列表项水平间距
        gv.setStretchMode(GridView.NO_STRETCH);
        gv.setNumColumns(size); // 设置列数量=列表集合数

    }

    private void setTopView(ArrayList<RadioInfo> radioInfos) {
        if (!isTimer) {
            timerOfHeader();
        }
        headerRadioInfos = radioInfos;
        if (headerRadioInfos != null) {
            if (headerRadioInfos.size() > 0) {
                headerPagerAdapter.radioInfos = new ArrayList<RadioInfo>();
                headerPagerAdapter.radioInfos.add(headerRadioInfos
                        .get(headerRadioInfos.size() - 1));
                for (int i = 0; i < headerRadioInfos.size(); i++) {
                    headerPagerAdapter.radioInfos.add(headerRadioInfos
                            .get(i));

                }
                headerPagerAdapter.radioInfos.add(headerRadioInfos
                        .get(0));
            }
            //第一次进入，根据服务器返回值判断是否存在有道广告。如果有，留位；如果没有，则跳过


            headerPagerAdapter.notifyDataSetChanged();
            initDotLinearLayout(headerRadioInfos);
        }


    }

    // 初始化焦点图“点”
    public void initDotLinearLayout(ArrayList<RadioInfo> list) {
        if (null == this)
            return;
        if (list == null || list.size() <= 0) {
            return;
        }
        arrayOfImageView = new ImageView[list.size()];
        RadioInfo[] radioInfos = new RadioInfo[list.size()];
        dotLinearLayout.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            arrayOfImageView[i] = new ImageView(PopularFragment.this.getActivity());
            arrayOfImageView[i].setPadding(5, 5, 0, 5);
            arrayOfImageView[i]
                    .setImageResource(R.mipmap.advertis_tit_point_usl);// advertis_tit_point_usl
            dotLinearLayout.addView(arrayOfImageView[i]);

            radioInfos[i] = list.get(i);
        }
        arrayOfImageView[0].setImageResource(R.mipmap.advertis_tit_point_sl);// advertis_tit_point_sl
    }

    @Override
    protected void initData() {
        showLoading();
        loadHomeHeaderData();
        loadHomeBottomData();

    }

    @Override
    protected void OnClick(View v) {

    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }


    private int createTopicWidth() {
        int width = Math.min(TLiveApplication.mScreenWidth,
                TLiveApplication.mScreenHeight);
        int margin = (int) (5D * (double) this.getActivity().getResources()
                .getDimensionPixelSize(R.dimen.topic_item_margin));
        return (int) ((double) (width - margin) / 4.6 + (double) (width - margin) % 4.6);
    }

    @Override
    public void reLoadData() {
        super.reLoadData();
        initData();
    }

    @Override
    protected View getLoadingTargetView() {
        return scrollView;
    }

}
