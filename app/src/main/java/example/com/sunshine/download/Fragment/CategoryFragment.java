package example.com.sunshine.download.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

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
import example.com.sunshine.download.Http.entity.ProgramInfoEntity;
import example.com.sunshine.download.Http.entity.RadioInfo;
import example.com.sunshine.download.Utils.Helpers;
import example.com.sunshine.download.request.CategoryRadioInfo;
import example.com.sunshine.download.request.HomeBottomResponse;
import example.com.sunshine.download.request.HomeTopResponse;
import example.com.sunshine.download.request.HttpManger;
import example.com.sunshine.download.request.StringTool;

/**
 * Created by qianxiangsen on 2017/5/3.
 */

public class CategoryFragment extends BaseFragment{

    List<RadioInfo> data = new ArrayList<>();
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

        ButterKnife.bind(this,rootView);
//        cateoryAdapter = new CategoryAdapter(data);
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.setAdapter(cateoryAdapter);
//
    }

    @Override
    protected void initData() {
//        loadHomeHeaderData();
//        loadHomeBottomData();
    }

    @Override
    protected void OnClick(View v) {

    }


    // 加载底部分类数据
    private void loadHomeBottomData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("sn", SystemUtils.getMd5UniqueID(this.getActivity()));
        HttpManger.getInstance(getActivity(), this).sendPostRequest(Configuration.HOME_BOTTOM_URL, map, true);
    }

    // 加载顶部焦点图数据
    private void loadHomeHeaderData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("version", SystemUtils.getAppVersionName(getActivity()));

        HttpManger.getInstance(getActivity(), this).sendPostRequest(Configuration.HOME_HEADER_RUL, map, true);
    }

    @Override
    public void onDataReady(BaseResponse response) {
        if (response instanceof HomeTopResponse) {
            HomeTopResponse topResponse = (HomeTopResponse) response;
            ArrayList<RadioInfo>  headerRadioInfos = (ArrayList) topResponse.getRadioInfos();

            if (StringTool.isListValidate(headerRadioInfos)){

                data.addAll(headerRadioInfos);

                if (StringTool.isListValidate(data)) {
                    //绑定节目数据
                    cateoryAdapter.addData(data);
                }

            }

            return;
        }
        if (response instanceof HomeBottomResponse){
            HomeBottomResponse topResponse = (HomeBottomResponse) response;
            List<CategoryRadioInfo> categoryRadioInfo  = topResponse.getCategoryRadioInfos();
            if (StringTool.isListValidate(topResponse.getCategoryRadioInfos())){
                for (int i = 0; i < categoryRadioInfo.size(); i++){

                    data.addAll(topResponse.getCategoryRadioInfos().get(i).getRadioInfos());

                    if (StringTool.isListValidate(data)) {
                        //绑定节目数据
                        cateoryAdapter.addData(data);
                    }

                }
            }
        }
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return null;
    }
}
