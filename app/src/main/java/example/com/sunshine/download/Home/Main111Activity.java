package example.com.sunshine.download.Home;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.sunshine.Exo.E.ExitEvent;
import example.com.sunshine.Exo.E.PlayEvent;
import example.com.sunshine.Exo.ExoConstants;
import example.com.sunshine.Exo.PlayManager;
import example.com.sunshine.R;
import example.com.sunshine.dagger.ActivityMobule;
import example.com.sunshine.dagger.DaggerActivityComponent;
import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.Fragment.DiscoverFragment;
import example.com.sunshine.download.Fragment.HomeFragment;
import example.com.sunshine.download.Fragment.SubscriptionFragment;
import example.com.sunshine.download.Fragment.UserFragment;
import example.com.sunshine.util.Util;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class Main111Activity extends BaseActivity implements View.OnClickListener , EasyPermissions.PermissionCallbacks {

    //当前tab位置
    public static int mFragCurrentIndex = 0;

    // 创建Fragment管理器
    private FragmentManager fragmentManager;

    @BindView(R.id.fragment_title)
    RadioGroup radioGroup;

    @BindView(R.id.find)
    RadioButton radioButton1;

    @BindView(R.id.custom)
    RadioButton radioButton2;

    @BindView(R.id.square)
    RadioButton radioButton3;

    @BindView(R.id.myspace)
    RadioButton radioButton4;

    @Inject
    HomeFragment homeFragment;
    @Inject
    SubscriptionFragment subscriptionFragment;
    @Inject
    DiscoverFragment discoverFragment;
    @Inject
    UserFragment hotPlayFragment;

    @BindView(R.id.common_playing_player)
    ImageView openPlayer;

    private Animation operatingAnim;

    public static final String ParamExit = "Exit";

    private boolean playing;

    String[] perms = {Manifest.permission.READ_PHONE_STATE};

    private static final int RC_LOCATION_CONTACTS_PERM = 1001;

    private boolean mIsFirstReqPermission = false;

    @Override
    protected int getLayoutId() {
        return R.layout.act_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        checkPermissions();
        if (this instanceof Main111Activity){
            DaggerActivityComponent.builder().
                    activityMobule(new ActivityMobule(this)).
                    build().inject(this);
        }

        ButterKnife.bind(this);
        PlayManager.getInstance().bindPlayService();
        if (savedInstanceState != null) {
            mFragCurrentIndex = savedInstanceState.getInt("curChoice", 0);
        }
        //移除fragment覆盖部分
        getWindow().setBackgroundDrawable(null);

        switchFragment(mFragCurrentIndex, true);

        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_play);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        int topicWidth = createTopicWidth();
        openPlayer.getLayoutParams().width = topicWidth;
        openPlayer.getLayoutParams().height = topicWidth;
        Glide.with(this).load(ExoConstants.IMAGE_URL).
                bitmapTransform(new CropCircleTransformation(this)).
                crossFade().into(openPlayer);

        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        radioButton3.setOnClickListener(this);
        radioButton4.setOnClickListener(this);
        openPlayer.setOnClickListener(this);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void OnClick(View v) {

    }


    private void startPlayAnimation() {

        openPlayer.startAnimation(operatingAnim);
    }

    private void checkPermissions() {
        if (EasyPermissions.hasPermissions(this, perms)) {

        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.permission_required),
                    RC_LOCATION_CONTACTS_PERM, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d("BaseActivity", "onPermissionsGranted");
        mIsFirstReqPermission = true;

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("权限申请")
                    .setRationale(R.string.permission_read_phone_hint)
                    .setNegativeButton("取消").build().show();
        }
    }


    private void stopPlayAnimation() {

        openPlayer.clearAnimation();
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
            case R.id.common_playing_player:
                Util.setIntnetPlay(getSupportFragmentManager(),
                        R.id.fragment_play,Util.PLAY_TAG_FRAGMENT);
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
    @Override
    protected void onNewIntent(Intent intent) {
        initIntent(intent);
        super.onNewIntent(intent);

    }

    private void initIntent(Intent intent) {
        boolean exit = intent.getBooleanExtra(ParamExit,false);
        if (exit){
            Util.setIntnetPlay(getSupportFragmentManager(),
                    R.id.fragment_play,Util.PLAY_TAG_FRAGMENT);
        }
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
    private int createTopicWidth() {
        int width = Math.min(TLiveApplication.mScreenWidth,
                TLiveApplication.mScreenHeight);
        int margin = (int) (3D * (double) getResources()
                .getDimensionPixelSize(R.dimen.topic_item_margin));
        return (int) ((double) (width - margin) / 6 + (double) (width - margin) % 4);
    }
    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayEvent(PlayEvent event) {

        playing = event.isPlayWhenReady();
        if (playing){
            startPlayAnimation();
        }else {
            stopPlayAnimation();
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onExitEvent(ExitEvent exitEvent){
        destoryInstance();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getSupportFragmentManager().getBackStackEntryCount() >= 1) {
                getSupportFragmentManager().popBackStack();
            } else {
                moveTaskToBack(true);
            }
            return true;

        }
        return false;
    }
    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
        moveTaskToBack();


    }
    private void moveTaskToBack(){
        if (playing){
            PlayManager.getInstance().stop();
        }
    }
    private void destoryInstance(){
        moveTaskToBack();
        PlayManager.Exit(this);
        System.exit(0);
    }

}
