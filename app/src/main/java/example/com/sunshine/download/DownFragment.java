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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
    private RecyclerView list;
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
        list = (RecyclerView) view.findViewById(R.id.listview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        list.setLayoutManager(linearLayoutManager);
        list.setAdapter(new ADAPTER(getActivity(),downlaod,taskList));
    }

    private static class ADAPTER extends BaseQuickAdapter<Task,BaseViewHolder>{

        Context context;
        DownloadMessage downlaod;
        public ADAPTER(Context context,DownloadMessage downlaod,List<Task> taskList){
            super(R.layout.item_download,taskList);
            this.context = context;
            this.downlaod = downlaod;
        }
        @Override
        protected void convert(BaseViewHolder helper, Task tas) {
            final  ImageView Image = (ImageView) helper.getView(R.id.ivIcon);
            final ProgressBar p = (ProgressBar) helper.getView(R.id.progressBar);
            final  TextView  mButton3 = (TextView) helper.getView(R.id.btnDownload);
            final  TextView speed = (TextView) helper.getView(R.id.tvName);
            final TextView  text = (TextView) helper.getView(R.id.tvStatus);
            final  TextView  size = (TextView) helper.getView(R.id.textView_size1);
            Picasso.with(context).load(tas.getIamgeUrl()).into(Image);

            final Task taskId = downlaod.addTask(tas, new DownloadUiListener() {
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
        }
    }

    @Override
    public void onPause() {
        super.onPause();
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
