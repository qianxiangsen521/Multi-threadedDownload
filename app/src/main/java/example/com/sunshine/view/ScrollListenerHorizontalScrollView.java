package example.com.sunshine.view;
 
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
 
public class ScrollListenerHorizontalScrollView extends HorizontalScrollView{
 
    public ScrollListenerHorizontalScrollView(Context context,
            AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
 
    public ScrollListenerHorizontalScrollView(Context context,
            AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
 
    public ScrollListenerHorizontalScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
     
     
    public interface ScrollViewListener {  
           
        void onScrollChanged(ScrollType scrollType);
       
    }  
     
    private Handler mHandler;
    private ScrollViewListener scrollViewListener;
    /**
     * 滚动状态   IDLE 滚动停止  TOUCH_SCROLL 手指拖动滚动         FLING滚动
     * @version XHorizontalScrollViewgallery    
     * @author DZC
     * @Time  2014-12-7 上午11:06:52
     *
     *
     */
   public enum ScrollType{IDLE,TOUCH_SCROLL,FLING}

    /**
     * 记录当前滚动的距离
     */
    private int currentX = -9999999;
    /**
     * 当前滚动状态
     */
    private ScrollType scrollType = ScrollType.IDLE;
    /**
     * 滚动监听间隔
     */
    private int scrollDealy = 50;
    /**
     * 滚动监听runnable
     */
    private Runnable scrollRunnable = new Runnable() {
         
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if(getScrollX()==currentX){
                //滚动停止  取消监听线程
                Log.d("", "停止滚动");
                scrollType = ScrollType.IDLE;
                if(scrollViewListener!=null){
                    scrollViewListener.onScrollChanged(scrollType);
                }
                mHandler.removeCallbacks(this);
                return;
            }else{
                //手指离开屏幕    view还在滚动的时候
                Log.d("", "Fling。。。。。");
                scrollType = ScrollType.FLING;
                if(scrollViewListener!=null){
                    scrollViewListener.onScrollChanged(scrollType);
                }
            }
            currentX = getScrollX();
            mHandler.postDelayed(this, scrollDealy);
        }
    };
 
 
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
        case MotionEvent.ACTION_MOVE:
            this.scrollType = ScrollType.TOUCH_SCROLL;
            scrollViewListener.onScrollChanged(scrollType);
            //手指在上面移动的时候   取消滚动监听线程
            mHandler.removeCallbacks(scrollRunnable);
            break;
        case MotionEvent.ACTION_UP:
            //手指移动的时候
            mHandler.post(scrollRunnable);
            break;
        }
        return super.onTouchEvent(ev);
    }
 
    /**
     * 必须先调用这个方法设置Handler  不然会出错
     *  2014-12-7 下午3:55:39 
     * @author DZC
     * @return void
     * @param handler   
     * @TODO
     */
    public void setHandler(Handler handler){
        this.mHandler = handler;
    }
    /**
     * 设置滚动监听
     *  2014-12-7 下午3:59:51 
     * @author DZC
     * @return void
     * @param listener      
     * @TODO
     */
    public void setOnScrollStateChangedListener(ScrollViewListener listener){
        this.scrollViewListener = listener;
    }
 
}