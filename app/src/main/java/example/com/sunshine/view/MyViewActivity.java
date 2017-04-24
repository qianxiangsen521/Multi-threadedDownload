package example.com.sunshine.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/4/5.
 */

public class MyViewActivity extends AppCompatActivity {


    TabLayout tabLayout;
    private Handler handler;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actcivity_view);

        setupTablayout();

    }

    private void setupTablayout() {

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.obj = "test";
                message.what = 1;
                handler.sendMessage(message);
            }
        });

        new Thread(new mRunnable()).start();

    }


    class mRunnable implements Runnable{

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler(Looper.myLooper()){
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1){
                        Log.d("TAG", "handleMessage: "+msg.obj);
                    }
                }
            };
            Looper.loop();
        }
    }
}
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //按下
//        if (MotionEvent.ACTION_DOWN == event.ACTION_DOWN){
//
//            Log.d("TAG", "ACTION_DOWN: "+"---->按下");
//            return true;
//            //移动
//        }else if (MotionEvent.ACTION_MOVE == event.ACTION_MOVE){
//            Log.d("TAG", "ACTION_MOVE: "+"---->移动");
//            return true;
//            //抬起
//        }else if (MotionEvent.ACTION_UP == event.ACTION_UP){
//            Log.d("TAG", "ACTION_UP: "+"---->抬起");
//            return true;
//            //取消手势
//        }else if (MotionEvent.ACTION_CANCEL == event.ACTION_CANCEL){
//            Log.d("TAG", "ACTION_CANCEL: "+"---->取消手势");
//            return true;
//            //滑出屏幕
//        }else if (MotionEvent.ACTION_OUTSIDE == event.ACTION_OUTSIDE){
//            Log.d("TAG", "ACTION_OUTSIDE: "+"---->滑出屏幕");
//            return true;
//        }
//
//        return false;
//    }
//}
