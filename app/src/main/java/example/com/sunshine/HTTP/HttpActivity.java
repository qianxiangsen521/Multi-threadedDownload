package example.com.sunshine.HTTP;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/4/5.
 */

public class HttpActivity extends AppCompatActivity {

    static volatile boolean isRunnable = false;

    static volatile int  intRunnable = 0;


    final StringBuilder sb = new StringBuilder();
    ArrayList<DownloadInfo> mList = new ArrayList<>();

    ExecutorService mExecoutor = Executors.newFixedThreadPool(3);

    ProgressBar bar1;

    Button btn;

    HttpAdapter adapter;
    RecyclerView recyclerView;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1){
                Log.d("TAG", "handleMessage: "+(int)msg.obj);
                bar1.setProgress((int)msg.obj);
                adapter.notifyDataSetChanged();
            }
        }
    };

    String[] name = {"1","2","3","4","5","6","7","8","9","10","11","12"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);


        Log.d("TAG", "onCreate: "+Math.random());
        Log.d("TAG", "onCreate: "+(int)Math.random() * name.length);
        Log.d("TAG", "onCreate: "+name[(int)Math.random() * name.length]);

        getCacheDir();

        Log.d("TAG", "getCacheDir: --->"+getCacheDir());
        Log.d("TAG", "getFilesDir: --->"+getFilesDir());

        Animal [] animals = new Animal[5];
        animals[0] = new Dag();

        animals[1] = new Cat();
        animals[2] = new Wolf();
        animals[3] = new Hippo();
        animals[4] = new Lion();

        for (int i = 0; i < animals.length; i++){
            animals[i].eat();
            animals[i].roam();
        }

        int x = 0;
        int z = ++x;
        int xn = 0;
        int xx = xn++;
        Log.d("TAG", "onCreate: x"+x+"----"+z);
        Log.d("TAG", "onCreate: xn"+xn+"----"+xx);
        Log.d("TAG", "onCreate: ");
        bar1 = (ProgressBar) findViewById(R.id.bar1);

        btn = (Button) findViewById(R.id.button);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadInfo info = new DownloadInfo();
                info.setId(1);
                info.setName("name");
                info.setUrl("http://dl.sunshinefm.cn/yinpin_web3/2017/02/17/357d6a1ed30ffab25b49fec62d6c1c5f.3gp");
                mList.add(info);
                mExecoutor.execute(new mRunnable2(info));

                    adapter = new HttpAdapter(mList);
                    recyclerView.setAdapter(adapter);
            }
        });
//        Log.d("TAG", "onCreate: "+Utils.calculateMemoryCacheSize(this));


//        HashMap<Integer,StringBuilder> map =
//                new HashMap <Integer,StringBuilder>();
//        StringBuilder builder = new StringBuilder();
//        builder.append("foo");
//        map.put(1,builder);
//        ImmutableHashMap <Integer,StringBuilder> immutableMap =
//                new ImmutableHashMap <>(map);
//        builder.append("bar");
//        System.out.println(immutableMap.get(1));
//
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new mRunnable()).start();
//            }
//        });




//        new Thread(new mRunnable()).start();
//        new Thread(new mRunnable1()).start();
//        new Thread(new Animator()).start();
//
//        Thread t = new Thread(runner);
//        t.start();
//        try{
//            t.join();
//        }catch (InterruptedException e){
//
//        }
//
//        FinalizableObject fo = new FinalizableObject(1);
//        fo.setJ(2);
//        FinalizableObject.k = 3;

    }

    //大多数Android应用程序的一个常见任务是连接到Internet。
    // 大多数网络连接的Android应用都使用HTTP发送和接收数据。
    // 本文介绍如何编写连接到Internet的简单应用程序，发送HTTP GET请求并显示响应。

    //目标
    // 如何向Web服务器发送HTTP GET请求并显示响应？
    //如何检查网络连接？
    //如何使用AsyncTask在单独的线程上执行网络操作？


    private void go(){
        int y = 7;
        for (int x = 1; x <  8; x++){
            y ++;
            if (x > 4){
            }
        }
    }


    class mRunnable2 implements Runnable{

        private DownloadInfo info;
        public mRunnable2(DownloadInfo info){
            this. info = info;

        }
        @Override
        public void run() {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(info.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                Log.d("TAG", "run: "+connection.getResponseCode());
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//                    return "Server returned HTTP " + connection.getResponseCode()
//                            + " " + connection.getResponseMessage();
                    return;
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                String filePath = Utils.getDiskCacheDirDownload(HttpActivity.this, "name").getPath();
                Log.d("TAG", "run: "+filePath);
                output = new FileOutputStream(filePath);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
//                    // allow canceling with back button
//                    if (isCancelled()) {
//                        input.close();
//                        return;
//                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0); // only if total length is known
                    // Update the progress bar
                    Message msg = Message.obtain();
                    msg.what =1;
                    msg.obj = (int) (total * 100 / fileLength);
                    mHandler.dispatchMessage(msg);
                    info.setLen((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return;
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return;
        }
    }

    class mRunnable implements  Runnable{

        @Override
        public void run() {
            sb.append("a");
            intRunnable = 42;
            isRunnable = true;
        }
    }

    class mRunnable1 implements  Runnable{

        @Override
        public void run() {
            sb.append("b");
            if (isRunnable){
                Log.d("TAG", "mRunnable1 run: "+intRunnable);
            }

        }
    }
    class Animator implements Runnable {

        private volatile boolean stop = false;
        public void stop() { stop = true; }
        public void run() {
            while (!stop) {
                oneStep();
                try{
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        private void oneStep() { /*...*/ }
    }


    Runnable runner = new Runnable() {
        @Override public void run() {
            sb.append("c");
            Log.d("TAG", "run: "+sb.toString());
        }
    };



    static class HttpAdapter extends RecyclerView.Adapter<HttpAdapter.mHoalder> {

        private ArrayList<DownloadInfo> mList;
        public HttpAdapter(ArrayList<DownloadInfo> mList){
            this.mList = mList;

        }
        static class mHoalder extends RecyclerView.ViewHolder{

            ProgressBar bar;
            Button btn;
            public mHoalder(View view){
                super(view);
                bar = (ProgressBar)view.findViewById(R.id.bar1);
                btn = (Button)view.findViewById(R.id.button);
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }


        @Override
        public HttpAdapter.mHoalder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new HttpAdapter.mHoalder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_http,null));
        }

        @Override
        public void onBindViewHolder(HttpAdapter.mHoalder holder, int position) {
            DownloadInfo info = mList.get(position);
            holder.bar.setProgress(info.getLen());
            holder.btn.setText(info.getName());

        }
    }

}
