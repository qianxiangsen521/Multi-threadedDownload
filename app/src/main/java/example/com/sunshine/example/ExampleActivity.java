package example.com.sunshine.example;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import example.com.sunshine.Main.DynamicSurfacesActivity;
import example.com.sunshine.Main.GridActivity;
import example.com.sunshine.Main.InterpolatorActivity;
import example.com.sunshine.Main.LoginActivity;
import example.com.sunshine.Main.AnimatorActivity;
import example.com.sunshine.Main.SceneActivity;
import example.com.sunshine.Main.SlideActivity;
import example.com.sunshine.Main.TransitionActivity;
import example.com.sunshine.Main.main;
import example.com.sunshine.R;

/**
 * Created by qianxiangsen on 2017/3/24.
 */

public class ExampleActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.bt1)
    Button bt1;
    @Bind(R.id.bt2)
    Button bt2;
    @Bind(R.id.bt3)
    Button bt3;
    @Bind(R.id.bt4)
    Button bt4;
    @Bind(R.id.bt5)
    Button bt5;
    @Bind(R.id.bt6)
    Button bt6;
    @Bind(R.id.bt7)
    Button bt7;
    @Bind(R.id.bt8)
    Button bt8;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        ButterKnife.bind(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt1:


//                AnimationSet animationSet = new AnimationSet(ExampleActivity.this,null);
//                animationSet.addAnimation(new AlphaAnimation(1f,0f));
//                animationSet.addAnimation(new TranslateAnimation(0,0,0,-bt1.getHeight()));
//                animationSet.setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime));
//
//                animationSet.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                    }
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        startActivity(new Intent(ExampleActivity.this,LoginActivity.class));
////                overridePendingTransition();
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });
//                bt1.setAnimation(animationSet);
//                startActivity(new Intent(this, DynamicSurfacesActivity.class));
                break;
            case R.id.bt2:
                startActivity(new Intent(this, AnimatorActivity.class));
                break;
            case R.id.bt3:
                startActivity(new Intent(this, InterpolatorActivity.class));
                break;
            case R.id.bt4:
                startActivity(new Intent(this, SlideActivity.class));
                break;
            case R.id.bt5:
                startActivity(new Intent(this, TransitionActivity.class));
                break;
            case R.id.bt6:
                startActivity(new Intent(this, SceneActivity.class));
                break;
            case R.id.bt7:
                startActivity(new Intent(this, main.class));
                break;
            case R.id.bt8:
                startActivity(new Intent(this, GridActivity.class));
                break;
        }
    }
}
