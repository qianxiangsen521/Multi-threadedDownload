package example.com.sunshine.download.request;


import java.util.List;

import example.com.sunshine.download.Http.entity.BaseResponse;

/**
 * 首页更多返回Response
 * Created by xingzhiqiao on 16/5/6.
 */
public class HomeMoreResponse extends BaseResponse {

    private List<CommonHomeMoreAndCategoryInfo> commonListInfo;


    public List<CommonHomeMoreAndCategoryInfo> getCommonListInfo() {
        return commonListInfo;
    }

    public void setCommonListInfo(List<CommonHomeMoreAndCategoryInfo> commonListInfo) {
        this.commonListInfo = commonListInfo;
    }
}
