package example.com.sunshine.download;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import example.com.sunshine.Main.GridDetailAcitivty;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class DownloadFinishAdapter extends RecyclerView.Adapter<DownloadFinishAdapter.mViewHolder>{


    private Context mActivity;
    private List<Task> taskList;
    public DownloadFinishAdapter(Context mActivity, List<Task> taskList){
        this.mActivity = mActivity;
        this.taskList = taskList;

    }
    public static class mViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;
        ImageView Image;
        public mViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.color_title);
            Image = (ImageView) view.findViewById(R.id.color_item);
        }

    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mViewHolder(View.inflate(mActivity,R.layout.item_download_finish,null));
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
        Task content =  taskList.get(position);
        Picasso.with(mActivity).load(content.getIamgeUrl()).into(holder.Image);
        holder.mTextView.setText(content.getName());

    }

}
