package example.com.sunshine;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qianxiangsen on 2017/4/1.
 */

public class MyActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.button2)
    Button button;

    @Bind(R.id.root_view)
    LinearLayout linear;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actcivity);
        ButterKnife.bind(this);

        setExpose();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,MyOtherActivity.class);
        if (Build.VERSION.SDK_INT >= 21){
            getWindow().setExitTransition(new Explode());
            startActivity(intent, ActivityOptions
                    .makeSceneTransitionAnimation(this).toBundle());
        }else{
            startActivity(intent);
        }
    }
    //揭露效果
    private void setExpose(){
        int cx =  ( linear.getLeft() + linear.getRight()) / 2;
        int cy = (linear.getTop() + linear.getBottom()) / 2;
        int finalRadius = Math.max(linear.getWidth(),linear.getHeight());
        Log.d("TAG", "x--"+cx + "y--"+cy + "Math.max--"+finalRadius);
        if (Build.VERSION.SDK_INT >= 21){

            Animator anim = ViewAnimationUtils.createCircularReveal(button,cx,cy,0,finalRadius);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    linear.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        }
    }

}
