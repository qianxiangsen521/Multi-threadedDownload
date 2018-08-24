package example.com.sunshine.download.Adapter;


import java.util.ArrayList;
import java.util.List;

import example.com.sunshine.R;
import example.com.sunshine.download.request.CategoryRadioInfo;

public class BaseAdapterExample extends BaseAdapter<CategoryRadioInfo,BaseViewHolder>{


    private static final int ITEM_TYPE_INFO1 = 1;
    private static final int ITEM_TYPE_INFO2 = 2;

    private ArrayList<CategoryRadioInfo> categoryRadioInfos;


    public BaseAdapterExample(ArrayList<CategoryRadioInfo> categoryRadioInfos) {
        super(categoryRadioInfos);
        this.categoryRadioInfos =categoryRadioInfos;
        addItemType(ITEM_TYPE_INFO1,R.layout.item_recycler_info);
        addItemType(ITEM_TYPE_INFO2,R.layout.item_recycler_info1);
    }

    public void onBaseViewHolder(BaseViewHolder holder, CategoryRadioInfo item) {
        switch (item.getIndexType()){
            case ITEM_TYPE_INFO1:
                holder.setText(R.id.imageView2_info,item.getTitle()+"----");
                break;
            case ITEM_TYPE_INFO2:
                holder.setText(R.id.imageView2_info1,item.getTitle());
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return categoryRadioInfos.get(position).getIndexType();
    }
}
