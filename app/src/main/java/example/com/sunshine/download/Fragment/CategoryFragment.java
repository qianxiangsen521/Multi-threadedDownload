package example.com.sunshine.download.Fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.com.sunshine.R;
import example.com.sunshine.download.Adapter.CategoryAdapter;
import example.com.sunshine.download.Http.Configuration;
import example.com.sunshine.download.Http.SystemUtils;
import example.com.sunshine.download.Http.entity.BaseResponse;
import example.com.sunshine.download.request.CategoryInfo;
import example.com.sunshine.download.request.CategoryInfoList;
import example.com.sunshine.download.request.CategoryResponse;
import example.com.sunshine.download.request.HttpManger;
import example.com.sunshine.download.request.StringTool;
import example.com.sunshine.util.ToastyUtils;

/**
 * Created by Hywel on 2017/5/3.
 */

public class CategoryFragment extends BaseFragment {

    List<CategoryInfo> data = new ArrayList<>();
    private CategoryAdapter cateoryAdapter;

    @Bind(R.id.category_recyclerview)
    RecyclerView recyclerView;

    @Inject
    public CategoryFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {

        ButterKnife.bind(this, rootView);
        cateoryAdapter = new CategoryAdapter(data);
//        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), GridLayoutManager.HORIZONTAL));
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        cateoryAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(cateoryAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastyUtils.getInstance(mContext).showSuccessToasty("pos: " + position);
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        loadCategoryData();
    }

    @Override
    protected void OnClick(View v) {

    }

    // 加载底部分类数据
    private void loadCategoryData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sn", SystemUtils.getMd5UniqueID(this.getActivity()));
        HttpManger.getInstance(getActivity(), this).sendPostRequest(Configuration.CATEGORY_INDEX, map, true);
    }

    @Override
    public void onDataReady(BaseResponse response) {
        if (response instanceof CategoryResponse) {
            CategoryResponse topResponse = (CategoryResponse) response;
            ArrayList<CategoryInfoList> categoryList = topResponse.getCategoryList();
            if (StringTool.isListValidate(categoryList)) {
                data.clear();
                for (int i = 0; i < categoryList.size(); i++) {
                    CategoryInfoList infoList = categoryList.get(i);
                    data.addAll(infoList.getCategoryList());
                }
                //绑定节目数据
                cateoryAdapter.setNewData(data);
            }
            refreshView();
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return recyclerView;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }
}
