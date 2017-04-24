package example.com.sunshine.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by qianxiangsen on 2017/4/5.
 */

public class MView  extends AppCompatButton{

    public MView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //按下
        if (MotionEvent.ACTION_DOWN == event.ACTION_DOWN){

            Log.d("TAG", "ACTION_DOWN: "+"---->按下");
        //移动
        }else if (MotionEvent.ACTION_MOVE == event.ACTION_MOVE){
            Log.d("TAG", "ACTION_MOVE: "+"---->移动");
        //抬起
        }else if (MotionEvent.ACTION_UP == event.ACTION_UP){
            Log.d("TAG", "ACTION_UP: "+"---->抬起");
            //取消手势
        }else if (MotionEvent.ACTION_CANCEL == event.ACTION_CANCEL){
            Log.d("TAG", "ACTION_CANCEL: "+"---->取消手势");
            //滑出屏幕
        }else if (MotionEvent.ACTION_OUTSIDE == event.ACTION_OUTSIDE){
            Log.d("TAG", "ACTION_OUTSIDE: "+"---->滑出屏幕");
        }

        return super.onTouchEvent(event);
    }
}
