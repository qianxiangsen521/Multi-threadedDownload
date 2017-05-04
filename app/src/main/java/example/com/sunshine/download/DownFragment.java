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
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import example.com.sunshine.HTTP.Utils;
import example.com.sunshine.R;
import example.com.sunshine.download.Fragment.BaseFragment;

/**
 * Created by qianxiangsen on 2017/4/28.
 */

public class DownFragment extends BaseFragment {
    private DownloadMessage downlaod;
    private List<Task> taskList;
    private ListView list;
    private View view;

    @Inject
    public DownFragment() {
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_download;
    }

    @Override
    protected void initView(Bundle savedInstanceState, View rootView) {
        view = rootView;
        downlaod = DownloadMessage.init(getActivity());

        taskList = DataSource.getInstance().getData();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void OnClick(View v) {

    }

    @Override
    public void onResume() {
        super.onResume();
        list = (ListView) view.findViewById(R.id.listview);
        list.setAdapter(new ADAPTER(getActivity(),downlaod,taskList));
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
            final  ImageView Image = (ImageView) convertView.findViewById(R.id.ivIcon);
            final ProgressBar p = (ProgressBar) convertView.findViewById(R.id.progressBar);
            final  TextView  mButton3 = (TextView) convertView.findViewById(R.id.btnDownload);
            final  TextView speed = (TextView) convertView.findViewById(R.id.tvName);
            final TextView  text = (TextView) convertView.findViewById(R.id.tvStatus);
            final  TextView  size = (TextView) convertView.findViewById(R.id.textView_size1);

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

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }
}
