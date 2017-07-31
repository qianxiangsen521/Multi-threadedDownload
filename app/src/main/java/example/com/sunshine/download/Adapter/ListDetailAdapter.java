package example.com.sunshine.download.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import example.com.sunshine.Exo.Utils.MusicInfo;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/7/31.
 */

public class ListDetailAdapter extends BaseQuickAdapter<MusicInfo,BaseViewHolder> {

    public ListDetailAdapter(ArrayList<MusicInfo> artistList){
        super(R.layout.fragment_musci_common_item,artistList);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicInfo item) {

        helper.setText(R.id.viewpager_list_toptext,item.musicName);
       helper.setText(R.id.viewpager_list_bottom_text,item.artist);
    }
}
