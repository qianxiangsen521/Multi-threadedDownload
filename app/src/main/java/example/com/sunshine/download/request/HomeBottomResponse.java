package example.com.sunshine.download.request;


import java.util.List;

import example.com.sunshine.download.Http.entity.BaseResponse;

/**
 * 首页底部response
 * Created by xingzhiqiao on 16/4/26.
 */
public class HomeBottomResponse extends BaseResponse {

    private List<CategoryRadioInfo> categoryRadioInfos;

    public List<CategoryRadioInfo> getCategoryRadioInfos() {
        return categoryRadioInfos;
    }

    public void setCategoryRadioInfos(List<CategoryRadioInfo> categoryRadioInfos) {
        this.categoryRadioInfos = categoryRadioInfos;
    }
}
