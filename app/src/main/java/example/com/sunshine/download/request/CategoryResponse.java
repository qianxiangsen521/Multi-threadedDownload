package example.com.sunshine.download.request;


import java.util.ArrayList;

import example.com.sunshine.download.Http.entity.BaseResponse;

/**
 * Created by xingzhiqiao on 16/4/27.
 */
public class CategoryResponse extends BaseResponse {

    private ArrayList<CategoryInfoList> categoryList;

    public ArrayList<CategoryInfoList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<CategoryInfoList> categoryList) {
        this.categoryList = categoryList;
    }
}
