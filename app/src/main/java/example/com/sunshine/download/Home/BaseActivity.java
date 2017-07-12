package example.com.sunshine.download.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import example.com.sunshine.download.Http.HttpCallback;
import example.com.sunshine.download.Http.entity.BaseResponse;
import example.com.sunshine.download.Utils.ToastUtil;
import example.com.sunshine.download.loading.BaseView;
import example.com.sunshine.download.loading.VaryViewHelperController;


/**
 * Created by QianXiangSen on 2016/10/10.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener,BaseView,HttpCallback {



    private SplashActivity splashActivity;

    protected abstract void initView(Bundle savedInstanceState);

    //获取布局文件ID
    protected abstract int getLayoutId();

    //网络请求
    protected abstract void initData();

    /**
     * View点击
     **/
    protected abstract void OnClick(View v);

    protected long lastClick = 0;

    protected VaryViewHelperController mVaryViewHelperController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getLayoutId() > 0) {
            setContentView(getLayoutId());

            initView(savedInstanceState);
            if (null != getLoadingTargetView()) {
                mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
            }
            initData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * toast提示
     */
    public void toast(String str) {
        ToastUtil.showToast(this, str);
    }

    public void toast(int id) {
        toast(getResources().getString(id));
    }

    /**
     * Exception提示
     */
    public void toastException(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    public final <T extends View> T findById(int id) {
        try {
            return (T) findViewById(id);
        } catch (ClassCastException ex) {
            throw ex;
        }
    }


    /**
     * [防止快速点击]
     *
     * @return
     */
    private boolean fastClick() {

        if (System.currentTimeMillis() - lastClick <= 500) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (fastClick()) {
            OnClick(v);
        }
    }

//    private void setTransition(Intent intent){
//        if (Build.VERSION.SDK_INT >= 21) {
//            getWindow().setExitTransition(new Explode());
//        }
//        Bundle bundle = ActivityOptionsCompat
//                .makeSceneTransitionAnimation(this)
//                .toBundle();
//        if (Build.VERSION.SDK_INT >= 21) {
//            startActivity(intent, bundle);
//        } else {
//            intent.putExtras(bundle);
//            startActivity(intent);
//        }
//    }
    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */

    protected final void startActivity(@NonNull Class<?> targetActivity) {

        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }

    protected final void startActivity(Bundle mBundle, @NonNull Class<?> targetActivity) {

        Intent intent = new Intent(this, targetActivity);
        if (null != mBundle) {
            intent.putExtras(mBundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转到指定的Activity
     *
     * @param extraName      要传递的值的键名称
     * @param extraValue     要传递的String类型值
     * @param targetActivity 要跳转的目标Activity
     */
    public final void startActivity(@NonNull String extraName, @NonNull String extraValue, @NonNull Class<?> targetActivity) {
        if (!TextUtils.isEmpty(extraName)) {

            final Intent intent = new Intent(getApplicationContext(), targetActivity);
            intent.putExtra(extraName, extraValue);

            startActivity(intent);
        } else {
            throw new NullPointerException("传递的值的键名称为null或空");
        }
    }


    public final void startActivityForResult(String context, int action, @NonNull Class<?> targetActivity) {
        Intent mIntent = new Intent(this, targetActivity);
        mIntent.putExtra("content", context);
        startActivityForResult(mIntent, action);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    protected View getLoadingTargetView() {
        return null;
    }


    @Override
    public void showLoading() {
        if (mVaryViewHelperController != null) {
            mVaryViewHelperController.showLoading();
        }
    }

    @Override
    public void showEmptyView(String msg) {
        if (mVaryViewHelperController != null) {
            mVaryViewHelperController.showEmpty(msg);
        }
    }

    @Override
    public void showNetError() {
        if (mVaryViewHelperController != null) {
            mVaryViewHelperController.showNetworkError(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reLoadData();
                }
            });
        }
    }

    /**
     * 重新请求数据
     */
    public void reLoadData() {
        showLoading();
    }

    @Override
    public void refreshView() {
        if (mVaryViewHelperController != null) {
            mVaryViewHelperController.restore();
        }
    }

    @Override
    public void UiStart(BaseResponse baseResponse) {

    }

    @Override
    public void UiEnd() {

    }

    @Override
    public void UiError() {

    }
}
