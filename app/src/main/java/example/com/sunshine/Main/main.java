package example.com.sunshine.Main;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;

import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/3/24.
 */

public class main extends AppCompatActivity{

    private ScrollView mScrollView;
    boolean mDidAnimateEnter = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mScrollView = (ScrollView) findViewById(R.id.scroll_view_anim);
    }

    @Override
    protected void onResume() {
        super.onResume();

        setupAnimation();
    }

    protected void setupAnimation() {
        // For API level >= 21, we use
        // onEnterAnimationComplete
        if (Build.VERSION.SDK_INT >= 21) {
            return;
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
            }
        }, 100);
    }

    protected void performAnimation() {
        if (mDidAnimateEnter) {
            return;
        }
        mDidAnimateEnter = true;
        final int startScrollPos = getResources()
                .getDimensionPixelSize(R.dimen.scroll_animation_start);

        Animator anim = ObjectAnimator.ofInt(
                mScrollView,
                "scrollY",
                startScrollPos)
                .setDuration(1000);

        anim.start();
    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        performAnimation();
    }
}
