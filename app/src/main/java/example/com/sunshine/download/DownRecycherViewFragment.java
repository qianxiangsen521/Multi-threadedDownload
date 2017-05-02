package example.com.sunshine.download;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import example.com.sunshine.HTTP.Utils;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/4/28.
 */

public class DownRecycherViewFragment extends Fragment {
    private DownloadMessage downlaod;
    private List<Task> taskList;
    private RecyclerView recyclerView;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_recycher_download,null);

        downlaod = DownloadMessage.init(getActivity());

        taskList = DataSource.getInstance().getData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerView = (RecyclerView) view.findViewById(R.id.listview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ADAPTER(getActivity(),downlaod,taskList));
    }

    private static class ADAPTER extends RecyclerView.Adapter<ADAPTER.DownViewHolder>{

        Context context;
        DownloadMessage downlaod;
        LayoutInflater inflater;
        List<Task> taskList;
        public ADAPTER(Context context,DownloadMessage downlaod,List<Task> taskList){
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.downlaod = downlaod;
            this.taskList = taskList;
        }

        @Override
        public int getItemCount() {
            return taskList != null ? taskList.size() : null;
        }

        @Override
        public void onBindViewHolder(final DownViewHolder holder, int position) {

            final Task tas = taskList.get(position);

            Picasso.with(context).load(tas.getIamgeUrl()).into(holder.Image);
            final Task taskId = downlaod.addTask(taskList.get(position), new DownloadUiListener() {
                @Override
                public void UiStrat() {
                    Log.d("TAG", "UiStrat: ");
                    holder. speed.setText("下载中");
                }

                @Override
                public void UiProgress(Task task,long TotalSize ,int downloadSize) {
                    Log.d("TAG", "UiProgress: "+Utlis.formatSize(TotalSize));
                    Log.d("TAG", "UiProgress: "+Utlis.formatSize(downloadSize));
                    holder.size.setText(Utlis.formatSize(downloadSize)+"/"+Utlis.formatSize(TotalSize));

                    holder. text.setText(Utlis.formatPercent(downloadSize,TotalSize));
                    holder. p.setMax((int) TotalSize);
                    holder.p.setProgress(downloadSize);

                }

                @Override
                public void UiFinish(Task task) {
                    Log.d("TAG", "UiFinish: ");
                    holder.down.setText("安装");
                    holder.speed.setText("下载完成");

                }
            });
            if (taskId.getState().equals(Task.STATUS_START)){
                downlaod.startDownload(taskId);
                holder.down.setText("暂停");
            }else if (taskId.getState() .equals(Task.STATUS_COMPLETE)) {
                holder.down.setText("安裝");
                holder.speed.setText(taskId.getName());
                holder.p.setMax((int) taskId.getSize());
                holder.p.setProgress(taskId.getDownloadSize());
                holder.size.setText(Utlis.formatSize(taskId.getDownloadSize())+"/"+Utlis.formatSize(taskId.getSize()));
                holder.text.setText(Utlis.formatPercent(taskId.getDownloadSize(),taskId.getSize()));
            }else{
                holder.speed.setText(taskId.getName());
                holder. p.setMax((int) taskId.getSize());
                holder.p.setProgress(taskId.getDownloadSize());
                holder.size.setText(Utlis.formatSize(taskId.getDownloadSize())+"/"+Utlis.formatSize(taskId.getSize()));
                holder.text.setText(Utlis.formatPercent(taskId.getDownloadSize(),taskId.getSize()));
            }

            holder.down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (taskId.getState() .equals(Task.STATUS_COMPLETE)){
                        Utils.installApp(context, new File(taskId.getSave_address()));
                    } else if (taskId.getState().equals(Task.STATUS_START) ){
                        downlaod.pauseDownload(taskId);
                        holder. down.setText("继续");
                    }else {
                        downlaod.startDownload(taskId);
                        holder.down.setText("暂停");
                    }
                }
            });
        }

        @Override
        public DownViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DownViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download,null,false));
        }

        static class DownViewHolder extends RecyclerView.ViewHolder{

            ImageView Image;
            ProgressBar p;
            TextView  down;
            TextView speed;
            TextView  text;
            TextView  size;
            public DownViewHolder(View convertView){
                super(convertView);
                Image = (ImageView) convertView.findViewById(R.id.ivIcon);
                 p = (ProgressBar) convertView.findViewById(R.id.progressBar);
                down = (TextView) convertView.findViewById(R.id.btnDownload);
                speed = (TextView) convertView.findViewById(R.id.tvName);
                text = (TextView) convertView.findViewById(R.id.tvStatus);
                size = (TextView) convertView.findViewById(R.id.textView_size1);

            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TAG", "onPause: ");
        for (Task task : taskList) {
            if (task.isPriority() == true) {
                task.setPriority(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        downlaod.shutdown();
    }
}
