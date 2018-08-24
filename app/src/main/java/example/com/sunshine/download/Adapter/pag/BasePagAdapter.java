package example.com.sunshine.download.Adapter.pag;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import java.util.List;

import example.com.sunshine.R;
import example.com.sunshine.download.Adapter.BaseAdapter;
import example.com.sunshine.download.Adapter.BaseViewHolder;
import example.com.sunshine.download.request.CategoryRadioInfo;

public class BasePagAdapter extends BaseAdapter<CategoryRadioInfo,BaseViewHolder> {


    public BasePagAdapter(@Nullable List<CategoryRadioInfo> data,
                          @NonNull DiffUtil.ItemCallback<CategoryRadioInfo> diffCallback) {
        super(R.layout.item_recycler_info, data, diffCallback);
    }

    @Override
    public void onBaseViewHolder(BaseViewHolder holder, CategoryRadioInfo item) {
        holder.setText(R.id.imageView2_info,item.getTitle());
    }
}
