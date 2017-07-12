package example.com.sunshine.download.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.com.sunshine.R;
import example.com.sunshine.download.Http.entity.RadioInfo;
import example.com.sunshine.download.request.CategoryRadioInfo;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.mViewHolder>{


    private ArrayList<RadioInfo> radioInfos;
    private Context context;
    public RecommendAdapter(Context context,ArrayList<RadioInfo> radioInfos){
        this.radioInfos = radioInfos;
        this.context = context;

    }
    public static class mViewHolder extends RecyclerView.ViewHolder{

        ImageView play_flag;
        TextView tv_title;
        TextView tv_subtitle;
        public mViewHolder(View view){
            super(view);
            play_flag =(ImageView) view.findViewById(R.id.iv_cover);
            tv_title =(TextView) view.findViewById(R.id.tv_title);
            tv_subtitle =(TextView) view.findViewById(R.id.tv_subtitle);
        }

    }

    @Override
    public int getItemCount() {
        return radioInfos.size();
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mViewHolder(View.inflate(parent.getContext(),R.layout.item_recommend_flow,null));
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
       RadioInfo radioInfo =  radioInfos.get(position);
        holder.tv_title.setText(radioInfo.getName());
        holder.tv_subtitle.setText(radioInfo.getCurrentPlay());
        Picasso.with(context).load(radioInfo.getImgUrl()).into(holder.play_flag);


    }

}
