package example.com.sunshine.download.Adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import example.com.sunshine.R;
import example.com.sunshine.download.Utils.ImageUtils;
import example.com.sunshine.download.request.CategoryInfo;

/**
 * Created by Hywel on 2017/7/7.
 */

public class CategoryAdapter extends BaseQuickAdapter<CategoryInfo, BaseViewHolder> {

    public CategoryAdapter(List<CategoryInfo> data) {
        super(R.layout.item_recommend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryInfo item) {

        helper.setText(R.id.tv_name, item.getName());
        helper.setText(R.id.tv_description, item.getDetailUrl());
        ImageView imageView = helper.getView(R.id.iv_album_complete);
        ImageUtils.loadImg(mContext, item.getImgUrl(), R.mipmap.background_card, imageView);
    }
}
