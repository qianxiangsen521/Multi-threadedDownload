package example.com.sunshine.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by qianxiangsen on 2017/4/5.
 */

public class MView  extends View{

    public MView(Context context) {
        super(context);
    }

    //当含有视图窗口的可见度已经改变时调用。
    @Override
    protected void onVisibilityChanged(@NonNull View changedView,  int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    //当画面从它的窗口分离调用。
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    //当视图是附加到窗口调用。
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    //当含有视图所得或者窗口失去焦点时调用。
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    //当调用视图获得或失去焦点。
    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    //当触摸屏运动事件发生时调用。
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    //当跟踪球运动事件发生时调用。
    @Override
    public boolean onTrackballEvent(MotionEvent event) {
        return super.onTrackballEvent(event);
    }

    //当一个关键事件发生时调用。
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    //当一个新的关键事件发生时调用
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    //当调用视图应该呈现它的内容。
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    //当这种view的大小已更改时调用。
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    //当调用这个view应该分配的大小和位置，所有的孩子。
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    //调用，以确定该view及其所有子项的大小要求。
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
