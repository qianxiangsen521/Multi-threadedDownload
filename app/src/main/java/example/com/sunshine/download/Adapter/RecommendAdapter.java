package example.com.sunshine.download.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import example.com.sunshine.R;
import example.com.sunshine.download.Task;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.mViewHolder>{


    @Inject
    public RecommendAdapter(){

    }
    public static class mViewHolder extends RecyclerView.ViewHolder{

        public mViewHolder(View view){
            super(view);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mViewHolder(View.inflate(parent.getContext(),R.layout.item_recommend_flow,null));
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {


    }

}
