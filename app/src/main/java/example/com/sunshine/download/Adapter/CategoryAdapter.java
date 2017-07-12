package example.com.sunshine.download.Adapter;

import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import example.com.sunshine.R;
import example.com.sunshine.download.Http.entity.ProgramInfoEntity;
import example.com.sunshine.download.Http.entity.RadioInfo;

/**
 * Created by qianxiangsen on 2017/7/7.
 */

public class CategoryAdapter extends BaseMultiItemQuickAdapter<RadioInfo, BaseViewHolder> {


    public CategoryAdapter(List<RadioInfo> data){
        super(data);
        addItemType(ProgramInfoEntity.PROGRAM_TYPE_BANNER, R.layout.item_recommend_banner);
        addItemType(ProgramInfoEntity.PROGRAM_TYPE_ARTIST,R.layout.item_recommend_selection_horizontal);

        addItemType(ProgramInfoEntity.PROGRAM_TYPE_THEATRE,R.layout.item_recommend);
    }

    @Override
    protected void convert(BaseViewHolder helper, RadioInfo item) {

        switch (item.getItemType()){
            case ProgramInfoEntity.PROGRAM_TYPE_BANNER:
                Log.d("TAG", "convertoooo: "+item.getName());
                break;
            case ProgramInfoEntity.PROGRAM_TYPE_ARTIST:
                Log.d("TAG", "convert1111: "+item.getName());
                break;
            case ProgramInfoEntity.PROGRAM_TYPE_THEATRE:
                Log.d("TAG", "convert2222: "+item.getName());
                break;
        }
    }
}
