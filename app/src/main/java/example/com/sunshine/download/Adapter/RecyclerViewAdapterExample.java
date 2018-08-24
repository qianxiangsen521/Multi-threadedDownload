package example.com.sunshine.download.Adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.sunshine.R;
import example.com.sunshine.download.Application.TLiveApplication;
import example.com.sunshine.download.Http.entity.RadioInfo;
import example.com.sunshine.download.Utils.ImageUtils;
import example.com.sunshine.download.request.CategoryRadioInfo;

public class RecyclerViewAdapterExample extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
    private List<CategoryRadioInfo> list;

    public RecyclerViewAdapterExample(Context context,List<CategoryRadioInfo> list) {
        this.context = context.getApplicationContext();
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 1){
            return new RecyclerViewViewHolder(inflater.inflate(R.layout.item_recycler_example,null));
        }else {
            return new RecyclerViewViewHolder1(inflater.inflate(R.layout.item_recycler_example1,null));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecyclerViewViewHolder){
            RecyclerViewViewHolder recyclerViewViewHolder = (RecyclerViewViewHolder) holder;
            recyclerViewViewHolder.textView.setText(list.get(position).getTitle()+"-"+position);
            recyclerViewViewHolder.recycler_example.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,true));
            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(
                    list.get(position).getRadioInfos(),context);
            recyclerViewViewHolder.recycler_example.setAdapter(recyclerViewAdapter);
        }else {
            RecyclerViewViewHolder1 viewViewHolder1 = (RecyclerViewViewHolder1) holder;
            viewViewHolder1.textView3.setText(list.get(position).getTitle()+"-"+position);
            viewViewHolder1.recycler_info1.setLayoutManager(
                    new GridLayoutManager(context,3));
            RecyclerViewAdapter1 recyclerViewAdapter1 = new RecyclerViewAdapter1(
                    list.get(position).getRadioInfos(),context);
            viewViewHolder1.recycler_info1.setAdapter(recyclerViewAdapter1);
        }
    }


    @Override
    public int getItemViewType(int position) {
        return list.get(position).getIndexType();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static final class RecyclerViewViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        RecyclerView recycler_example;
        public RecyclerViewViewHolder(View itemView) {
            super(itemView);
            textView =(TextView) itemView.findViewById(R.id.textView2);
            recycler_example = (RecyclerView) itemView.findViewById(R.id.recycler_example);
        }
    }
    static final class RecyclerViewViewHolder1 extends RecyclerView.ViewHolder{
        TextView textView3;
        RecyclerView recycler_info1;
        public RecyclerViewViewHolder1(View itemView) {
            super(itemView);
            textView3 =(TextView) itemView.findViewById(R.id.textView3);
            recycler_info1 = (RecyclerView)itemView.findViewById(R.id.recycler_info1);
        }
    }

    static final class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewViewHolderRedioInfos1>{
        private ArrayList<RadioInfo> radioInfos;
        private Context context;
        public RecyclerViewAdapter(ArrayList<RadioInfo> radioInfos,Context context) {
            this.radioInfos = radioInfos;
            this.context = context;
        }

        @Override
        public RecyclerViewViewHolderRedioInfos1 onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerViewViewHolderRedioInfos1(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_info_example1,null));
        }

        @Override
        public void onBindViewHolder(RecyclerViewViewHolderRedioInfos1 holder, int position) {
            int topicWidth = createTopicWidth();
            holder.imageView.getLayoutParams().width = topicWidth;
            holder.imageView.getLayoutParams().height = topicWidth;
            ImageUtils.loadImg(context,radioInfos.get(position).
                    getImgUrl(),R.mipmap.fox,holder.imageView);
        }

        @Override
        public int getItemCount() {
            return radioInfos.size();
        }

        private int createTopicWidth() {
            int width = Math.min(TLiveApplication.mScreenWidth,
                    TLiveApplication.mScreenHeight);
            int margin = (int) (5D * (double) context.getResources()
                    .getDimensionPixelSize(R.dimen.topic_item_margin));
            return (int) ((double) (width - margin) / 3 + (double) (width - margin) % 3);
        }
    }
    final static class RecyclerViewViewHolderRedioInfos1 extends RecyclerView.ViewHolder{

        ImageView imageView;
        public RecyclerViewViewHolderRedioInfos1(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView2_info);
        }
    }


    static final class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewViewHolderRedioInfos2>{
        private ArrayList<RadioInfo> radioInfos;
        private Context context;
        public RecyclerViewAdapter1(ArrayList<RadioInfo> radioInfos,Context context) {
            this.radioInfos = radioInfos;
            this.context = context;
        }
        @Override
        public RecyclerViewViewHolderRedioInfos2 onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerViewViewHolderRedioInfos2(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_recycler_info_example2,null));
        }

        @Override
        public void onBindViewHolder(RecyclerViewViewHolderRedioInfos2 holder, int position) {
            int topicWidth = createTopicWidth();
            holder.imageView.getLayoutParams().width = topicWidth;
            holder.imageView.getLayoutParams().height = topicWidth;
            ImageUtils.loadImg(context,radioInfos.get(position).
                    getImgUrl(),R.mipmap.fox,holder.imageView);
            holder.textView5_info5.setText(radioInfos.get(position).getAlbum_name());
        }

        @Override
        public int getItemCount() {
            return radioInfos.size();
        }
        private int createTopicWidth() {
            int width = Math.min(TLiveApplication.mScreenWidth,
                    TLiveApplication.mScreenHeight);
            int margin = (int) (5D * (double) context.getResources()
                    .getDimensionPixelSize(R.dimen.topic_item_margin));
            return (int) ((double) (width - margin) / 3 + (double) (width - margin) % 3);
        }

    }

    final static class RecyclerViewViewHolderRedioInfos2 extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView5_info5;
        public RecyclerViewViewHolderRedioInfos2(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView2_info1);
            textView5_info5 = (TextView) itemView.findViewById(R.id.textView5_info5);
        }
    }


}

