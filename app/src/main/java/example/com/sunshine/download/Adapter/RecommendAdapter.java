package example.com.sunshine.download.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.com.sunshine.R;
import example.com.sunshine.download.Http.entity.RadioInfo;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class RecommendAdapter extends BaseQuickAdapter<RadioInfo,BaseViewHolder> {



    public RecommendAdapter( ArrayList<RadioInfo> radioInfos) {
        super(R.layout.item_recommend_flow,radioInfos);

    }

    @Override
    protected void convert(BaseViewHolder helper,final RadioInfo item) {

        helper.setText(R.id.tv_title,item.getName());
        helper.setText(R.id.tv_subtitle,item.getCurrentPlay());
        ImageView play_flag = helper.getView(R.id.iv_play_flag);
        ImageView del = helper.getView(R.id.iv_del);
        Glide.with(mContext)
                .load(item.getImgUrl())
                .crossFade()
                .into(play_flag);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData.size() > 1) {
                    mData.remove(item);
                    RecommendAdapter.this.notifyDataSetChanged();
                } else {
                    Toast.makeText(mContext, "只剩一条了，别 TM 删了！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
