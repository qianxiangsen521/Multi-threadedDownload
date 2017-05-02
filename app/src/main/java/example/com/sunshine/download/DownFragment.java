package example.com.sunshine.download;

import android.content.Context;
import android.content.Intent;
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
import java.util.ArrayList;
import java.util.List;

import example.com.sunshine.HTTP.Utils;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/4/28.
 */

public class DownFragment extends Fragment {
    private DownloadMessage downlaod;
    //    private ArrayList<Task> taskList = new ArrayList<>();
    private List<Task> taskList;
    private ListView list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view =  inflater.inflate(R.layout.fragment_download,null);

        downlaod = DownloadMessage.init(getActivity());
        taskList = DataSource.getInstance().getData();
//        final Task task1 = new Task();
//        task1.setName("任务1");
//        task1.setIamgeUrl(Constants.IMG);
//        task1.setId(1);
//        task1.setmUniquely_id("102");
//        task1.setUrl(Constants.URL);
//        taskList.add(task1);
//
//        final Task task2 = new Task();
//        task2.setName("任务2");
//        task2.setIamgeUrl(Constants.IMG1);
//        task2.setId(2);
//        task2.setmUniquely_id("112");
//        task2.setUrl(Constants.URL1);
//        taskList.add(task2);
//
//        final Task task3 = new Task();
//        task3.setName("任务3");
//        task3.setIamgeUrl(Constants.IMG2);
//        task3.setId(3);
//        task3.setmUniquely_id("111");
//        task3.setUrl(Constants.URL2);
//        taskList.add(task3);
//
//        final Task task4 = new Task();
//        task4.setId(4);
//        task4.setIamgeUrl(Constants.IMG3);
//        task4.setName("任务4");
//        task4.setmUniquely_id("113");
//        task4.setUrl(Constants.URL3);
//        taskList.add(task4);

        list = (ListView) view.findViewById(R.id.listview);
        list.setAdapter(new ADAPTER(getActivity(),downlaod,taskList));
        return view;
    }

    private static class ADAPTER extends BaseAdapter{

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
        public int getCount() {
            return taskList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return taskList.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download,null,false);
            final ImageView Image = (ImageView) convertView.findViewById(R.id.ivIcon);


            final  ProgressBar p = (ProgressBar) convertView.findViewById(R.id.progressBar);

            final  TextView mButton3 = (TextView) convertView.findViewById(R.id.btnDownload);
            final TextView speed = (TextView) convertView.findViewById(R.id.tvName);
            final TextView text = (TextView) convertView.findViewById(R.id.tvStatus);
            final  TextView size = (TextView) convertView.findViewById(R.id.textView_size1);
            final Task tas = taskList.get(position);

            Picasso.with(context).load(tas.getIamgeUrl()).into(Image);
            final Task taskId = downlaod.addTask(taskList.get(position), new DownloadUiListener() {
                @Override
                public void UiStrat() {
                    Log.d("TAG", "UiStrat: ");
                    speed.setText("下载中");
                }

                @Override
                public void UiProgress(Task task,long TotalSize ,int downloadSize) {
                    Log.d("TAG", "UiProgress: "+Utlis.formatSize(TotalSize));
                    Log.d("TAG", "UiProgress: "+Utlis.formatSize(downloadSize));
                    size.setText(Utlis.formatSize(downloadSize)+"/"+Utlis.formatSize(TotalSize));
//                    speed.setText(task.getSpeed()+
//                            "Kb/s");
                    text.setText(Utlis.formatPercent(downloadSize,TotalSize));
                    p.setMax((int) TotalSize);
                    p.setProgress(downloadSize);

                }

                @Override
                public void UiFinish(Task task) {
                    Log.d("TAG", "UiFinish: ");
                    mButton3.setText("安装");
                    speed.setText("下载完成");

                }
            });
            if (taskId.getState().equals(Task.STATUS_START)){
                downlaod.startDownload(taskId);
                mButton3.setText("暂停");
            }else if (taskId.getState() .equals(Task.STATUS_COMPLETE)) {
                mButton3.setText("安裝");
                speed.setText(taskId.getName());
                p.setMax((int) taskId.getSize());
                p.setProgress(taskId.getDownloadSize());
                size.setText(Utlis.formatSize(taskId.getDownloadSize())+"/"+Utlis.formatSize(taskId.getSize()));
                text.setText(Utlis.formatPercent(taskId.getDownloadSize(),taskId.getSize()));
            }else{
                speed.setText(taskId.getName());
                p.setMax((int) taskId.getSize());
                p.setProgress(taskId.getDownloadSize());
                size.setText(Utlis.formatSize(taskId.getDownloadSize())+"/"+Utlis.formatSize(taskId.getSize()));
                text.setText(Utlis.formatPercent(taskId.getDownloadSize(),taskId.getSize()));
            }

            mButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (taskId.getState() .equals(Task.STATUS_COMPLETE)){
                        Utils.installApp(context, new File(taskId.getSave_address()));
                    } else if (taskId.getState().equals(Task.STATUS_START) ){
                        downlaod.pauseDownload(taskId);
                        mButton3.setText("继续");
                    }else {
                        downlaod.startDownload(taskId);
                        mButton3.setText("暂停");
                    }
                }
            });

            return convertView;
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
