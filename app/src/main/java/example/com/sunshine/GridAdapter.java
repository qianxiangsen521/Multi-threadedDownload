package example.com.sunshine;

import android.app.Activity;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

import example.com.sunshine.Main.GridDetailAcitivty;
import example.com.sunshine.Main.TransitionDetailActivity;

/**
 * Created by qianxiangsen on 2017/3/27.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.mViewHolder>{

    private ArrayList<String> xContent = new ArrayList<>();

    private Activity mActivity;
    public GridAdapter(Activity mActivity){
        this.mActivity = mActivity;

    }
    public static class mViewHolder extends RecyclerView.ViewHolder{

        ImageView mIamgeView;
        TextView mTextView;
        View view;
        public mViewHolder(View view){
            super(view);
            this.view = view;
//            mIamgeView =(ImageView)  view.findViewById(R.id.item_image);
            mTextView = (TextView) view.findViewById(R.id.item_text);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new mViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download,null));
    }

    @Override
    public void onBindViewHolder(final mViewHolder holder, final int position) {
        final ArrayList<String> content =  getContent();
        holder.mTextView.setText(content.get(position));
//        holder.view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mActivity, GridDetailAcitivty.class);
//                intent.putExtra("title", content.get(position));
//
//                Bundle bundle = ActivityOptionsCompat
//                        .makeSceneTransitionAnimation(mActivity, holder.mIamgeView,holder. mIamgeView.getTransitionName())
//                        .toBundle();
//
//                if (Build.VERSION.SDK_INT >= 21) {
//                    mActivity.startActivity(intent, bundle);
//                } else {
//                    intent.putExtras(bundle);
//                    mActivity.startActivity(intent);
//                }
//            }
//        });
    }
    private ArrayList<String> getContent(){

        for (int i = 0; i < 10; i++){
            xContent.add("item"+i);

        }
        return xContent;
    }
}
